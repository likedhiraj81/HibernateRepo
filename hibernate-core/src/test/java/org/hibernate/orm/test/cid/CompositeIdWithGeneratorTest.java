/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.orm.test.cid;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.Transaction;

import org.hibernate.testing.TestForIssue;
import org.hibernate.testing.junit4.BaseCoreFunctionalTestCase;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

/**
 * Tests the use of composite-id with a generator.  
 * Test this behavior in all the various entity states (transient, managed, detached) 
 * and the different state transitions.
 * 
 * For HHH-2060.
 * 
 * @author Jacob Robertson
 */
@TestForIssue( jiraKey = "HHH-2060" )
public class CompositeIdWithGeneratorTest extends BaseCoreFunctionalTestCase {
	private DateFormat df = SimpleDateFormat.getDateTimeInstance( DateFormat.LONG, DateFormat.LONG );
	private Calendar cal = new GregorianCalendar(2021, 1, 31, 17, 30, 0);

	@Override
	protected String getBaseForMappings() {
		return "org/hibernate/orm/test/";
	}

	@Override
	public String[] getMappings() {
		return new String[] { "cid/PurchaseRecord.hbm.xml" };
	}

	@Test
	public void testCompositeIdSimple() {
		Session s = openSession();
		Transaction t = s.beginTransaction();

		// persist the record to get the id generated
		PurchaseRecord record = new PurchaseRecord();
		record.setTimestamp( cal.getTime() );
		s.persist(record);

		t.commit();
		s.close();

		// test that the id was generated
		PurchaseRecord.Id generatedId = record.getId();
		Date timestamp = record.getTimestamp();
		assertNotNull(generatedId);
		assertNotNull( generatedId.getPurchaseSequence() );
		assertTrue(generatedId.getPurchaseNumber() > 0);
		
		s = openSession();
		t = s.beginTransaction();
		
		// find the record, and see that the ids match
		PurchaseRecord find = (PurchaseRecord) s.get(PurchaseRecord.class, generatedId);
		assertNotNull(find);
		assertEquals( generatedId, find.getId() );
		assertEquals( df.format(timestamp), df.format(find.getTimestamp()) );

		t.commit();
		s.close();

		s = openSession();
		t = s.beginTransaction();
		
		// generate another new record
		cal.roll( Calendar.SECOND, true );
		PurchaseRecord record2 = new PurchaseRecord();
		record2.setTimestamp( cal.getTime() );
		s.persist(record2);
		
		t.commit();
		s.close();

		PurchaseRecord.Id generatedId2 = record2.getId();
		Date timestamp2 = record2.getTimestamp();

		s = openSession();
		t = s.beginTransaction();
		
		PurchaseRecord find2 = (PurchaseRecord) s.get(PurchaseRecord.class, generatedId2);
		
		t.commit();
		s.close();
		
		// test that the ids are different
		PurchaseRecord.Id id1 = find.getId();
		PurchaseRecord.Id id2 = find2.getId();
		String seq1 = id1.getPurchaseSequence();
		String seq2 = id2.getPurchaseSequence();
		int num1 = id1.getPurchaseNumber();
		int num2 = id2.getPurchaseNumber();
		
		assertEquals( df.format(timestamp2), df.format(find2.getTimestamp()) );
		assertFalse( id1.equals(id2) );
		assertFalse( seq1.equals(seq2) );
		assertFalse(num1 == num2);
	}

	@Test
	public void testDetachedProperty() {
		Session s = openSession();
		Transaction t = s.beginTransaction();

		// persist the record
		PurchaseRecord record = new PurchaseRecord();
		cal.roll( Calendar.SECOND, true );
		record.setTimestamp( cal.getTime() );
		s.persist(record);
		
		// close session so we know the record is detached
		t.commit();
		s.close();

		PurchaseRecord.Id generatedId = record.getId();

		// change a non-id property, but do not persist
		Date persistedTimestamp = record.getTimestamp();
		cal.roll( Calendar.SECOND, true );
		Date newTimestamp = cal.getTime();
		record.setTimestamp(newTimestamp);
		
		s = openSession();
		t = s.beginTransaction();

		PurchaseRecord find = (PurchaseRecord) s.get(PurchaseRecord.class, generatedId);
		
		t.commit();
		s.close();

		// see that we get the original id, and the original timestamp
		assertEquals( generatedId, find.getId() );
		assertEquals( df.format(persistedTimestamp), df.format(find.getTimestamp()) );
		
		s = openSession();
		t = s.beginTransaction();

		// update with the new timestamp
		s.update(record);
		
		t.commit();
		s.close();
		
		// find the newly updated record
		s = openSession();
		t = s.beginTransaction();
		
		PurchaseRecord find2 = (PurchaseRecord) s.get(PurchaseRecord.class, generatedId);
		
		t.commit();
		s.close();

		// see that we get the original id, and the new timestamp
		assertEquals( generatedId, find2.getId() );
		assertEquals( df.format(newTimestamp), df.format(find2.getTimestamp()) );
	}

	@Test
	public void testDetachedId() {
		Session s = openSession();
		Transaction t = s.beginTransaction();

		Date timestamp1 = cal.getTime();
		cal.roll( Calendar.SECOND, true );
		Date timestamp2 = cal.getTime();

		// persist two records
		PurchaseRecord record1 = new PurchaseRecord();
		record1.setTimestamp(timestamp1);
		PurchaseRecord record2 = new PurchaseRecord();
		record2.setTimestamp(timestamp2);
		s.persist(record1);
		s.persist(record2);
		
		// close session so we know the records are detached
		t.commit();
		s.close();

		PurchaseRecord.Id generatedId1 = record1.getId();
		PurchaseRecord.Id generatedId2 = record2.getId();
		
		// change the ids around - effectively making record1 have the same id as record2
		// do not persist yet
		PurchaseRecord.Id toChangeId1 = new PurchaseRecord.Id();
		toChangeId1.setPurchaseNumber( record2.getId().getPurchaseNumber() );
		toChangeId1.setPurchaseSequence( record2.getId().getPurchaseSequence() );
		record1.setId(toChangeId1);
		
		s = openSession();
		t = s.beginTransaction();

		PurchaseRecord find1 = (PurchaseRecord) s.get(PurchaseRecord.class, generatedId1);
		PurchaseRecord find2 = (PurchaseRecord) s.get(PurchaseRecord.class, generatedId2);
		
		t.commit();
		s.close();

		// see that we get the original ids (and timestamps)
		// i.e. weren't changed by changing the detached object
		assertEquals( generatedId1, find1.getId() );
		assertEquals( df.format(timestamp1), df.format(find1.getTimestamp()) );
		assertEquals( generatedId2, find2.getId() );
		assertEquals( df.format(timestamp2), df.format(find2.getTimestamp()) );
		
		s = openSession();
		t = s.beginTransaction();

		// update with the new changed record id
		s.update(record1);
		
		t.commit();
		s.close();

		// test that record1 did not get a new generated id, and kept record2's id
		PurchaseRecord.Id foundId1 = record1.getId();
		assertSame(toChangeId1, foundId1);
		assertEquals( toChangeId1.getPurchaseNumber(), foundId1.getPurchaseNumber() );
		assertEquals( toChangeId1.getPurchaseSequence(), foundId1.getPurchaseSequence() );
		
		// find record 2 and see that it has the timestamp originally found in record 1
		s = openSession();
		t = s.beginTransaction();
		
		find2 = (PurchaseRecord) s.get(PurchaseRecord.class, generatedId2);
		
		t.commit();
		s.close();

		// see that we get the original id (2), and the new timestamp (1)
		assertEquals( df.format(timestamp1), df.format(find2.getTimestamp()) );
		assertEquals( generatedId2, find2.getId() );
	}

	@Test
	public void testSaveOrUpdate() {
		Session s = openSession();
		Transaction t = s.beginTransaction();

		Date timestamp1 = cal.getTime();
		cal.roll( Calendar.SECOND, true );
		Date timestamp2 = cal.getTime();

		// persist the record
		PurchaseRecord record = new PurchaseRecord();
		record.setTimestamp(timestamp1);
		s.saveOrUpdate(record);
		
		t.commit();
		s.close();

		// test that the id was generated
		PurchaseRecord.Id generatedId = record.getId();
		assertNotNull(generatedId);
		assertNotNull( generatedId.getPurchaseSequence() );
		
		// change the timestamp
		record.setTimestamp(timestamp2);
		
		s = openSession();
		t = s.beginTransaction();

		s.saveOrUpdate(record);
		
		t.commit();
		s.close();

		// see that we get the *same* id, and the new timestamp
		assertSame( generatedId, record.getId() );
		assertEquals( df.format(timestamp2), df.format(record.getTimestamp()) );
	}

	@Test
	public void testLoad() {
		Session s = openSession();
		Transaction t = s.beginTransaction();

		// persist the record, then get the id and timestamp back
		PurchaseRecord record = new PurchaseRecord();
		record.setTimestamp( cal.getTime() );
		s.persist(record);
		
		t.commit();
		s.close();

		PurchaseRecord.Id id = record.getId();
		Date timestamp = record.getTimestamp();
		
		// using the given id, load a transient record
		PurchaseRecord toLoad = new PurchaseRecord();
		
		s = openSession();
		t = s.beginTransaction();

		s.load(toLoad, id);
		
		t.commit();
		s.close();
		
		// show that the correct timestamp and ids were loaded
		assertEquals( id, toLoad.getId() );
		assertEquals( df.format(timestamp), df.format(toLoad.getTimestamp()) );
	}

	@Test
	public void testEvict() {
		Session s = openSession();
		Transaction t = s.beginTransaction();

		Date timestamp1 = cal.getTime();
		cal.roll( Calendar.SECOND, true );
		Date timestamp2 = cal.getTime();
		
		// persist the record, then evict it, then make changes to it ("within" the session)
		PurchaseRecord record = new PurchaseRecord();
		record.setTimestamp(timestamp1);
		s.persist(record);
		s.flush();
		s.evict(record);
		
		record.setTimestamp(timestamp2);
		
		t.commit();
		s.close();

		PurchaseRecord.Id generatedId = record.getId();
		
		// now, re-fetch the record and show that the timestamp change wasn't persisted
		s = openSession();
		t = s.beginTransaction();

		PurchaseRecord persistent = (PurchaseRecord) s.get(PurchaseRecord.class, generatedId);
		
		t.commit();
		s.close();
		
		assertEquals( generatedId, persistent.getId() );
		assertEquals( df.format(timestamp1), df.format(persistent.getTimestamp()) );
	}
	
	@Test
	public void testMerge() {
		Session s = openSession();
		Transaction t = s.beginTransaction();

		Date timestamp1 = cal.getTime();
		cal.roll( Calendar.SECOND, true );
		Date timestamp2 = cal.getTime();

		// persist the record
		PurchaseRecord record = new PurchaseRecord();
		record.setTimestamp( timestamp1 );
		s.persist(record);
		
		t.commit();
		s.close();

		// test that the id was generated
		PurchaseRecord.Id generatedId = record.getId();
		assertNotNull(generatedId);
		assertNotNull( generatedId.getPurchaseSequence() );
		
		s = openSession();
		t = s.beginTransaction();

		// update detached object, retrieve persistent object, then merge
		PurchaseRecord detached = record;
		detached.setTimestamp(timestamp2);
		PurchaseRecord persistent = (PurchaseRecord) s.get(PurchaseRecord.class, generatedId);
		
		// show that the timestamp hasn't changed
		assertEquals( df.format(timestamp1), df.format(persistent.getTimestamp()) );
		
		s.merge(detached);
		
		t.commit();
		s.close();

		// show that the persistent object was changed only after the session flush
		assertEquals( timestamp2, persistent.getTimestamp() );
		
		// show that the persistent store was updated - not just the in-memory object
		s = openSession();
		t = s.beginTransaction();

		persistent = (PurchaseRecord) s.get(PurchaseRecord.class, generatedId);

		t.commit();
		s.close();
		
		assertEquals( df.format(timestamp2), df.format(persistent.getTimestamp()) );
	}

	@Test
	public void testDelete() {
		Session s = openSession();
		Transaction t = s.beginTransaction();

		// persist the record
		PurchaseRecord record = new PurchaseRecord();
		s.saveOrUpdate(record);
		
		t.commit();
		s.close();

		PurchaseRecord.Id generatedId = record.getId();
		
		// re-fetch, then delete the record
		s = openSession();
		t = s.beginTransaction();

		PurchaseRecord find = (PurchaseRecord) s.get(PurchaseRecord.class, generatedId);
		s.delete(find);
		assertFalse( s.contains(find) );
		
		t.commit();
		s.close();

		// attempt to re-fetch - show it was deleted
		s = openSession();
		t = s.beginTransaction();

		find = (PurchaseRecord) s.get(PurchaseRecord.class, generatedId);

		t.commit();
		s.close();
		
		assertNull(find);
	}
	
	@Test
	public void testGeneratedIdsWithChildren() {
		
		Session s = openSession();
		Transaction t = s.beginTransaction();

		// set up the record and details
		PurchaseRecord record = new PurchaseRecord();
		Set details = record.getDetails();
		details.add( new PurchaseDetail(record, "p@1", 1) );
		details.add( new PurchaseDetail(record, "p@2", 2) );

		s.persist(record);
		
		t.commit();
		s.close();
		
		// show that the ids were generated (non-zero) and come out the same
		int foundPurchaseNumber = record.getId().getPurchaseNumber();
		String foundPurchaseSequence = record.getId().getPurchaseSequence();
		assertNotNull( record.getId() );
		assertTrue(foundPurchaseNumber > 0);
		assertNotNull(foundPurchaseSequence);
		
		// search on detail1 by itself and show it got the parent's id
		s = openSession();
		t = s.beginTransaction();

		// doAfterTransactionCompletion a find to show that it will wire together fine
		PurchaseRecord foundRecord = (PurchaseRecord) s.get(PurchaseRecord.class,
				new PurchaseRecord.Id(foundPurchaseNumber, foundPurchaseSequence)
				);
		
		t.commit();
		s.close();

		// some simple test to see it fetched
		assertEquals( 2, foundRecord.getDetails().size() );
	}

}

