/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * Copyright (c) 2007, Red Hat Middleware LLC or third-party contributors as
 * indicated by the @author tags or express copyright attribution
 * statements applied by the authors.  All third-party contributions are
 * distributed under license by Red Hat Middleware LLC.
 *
 * This copyrighted material is made available to anyone wishing to use, modify,
 * copy, or redistribute it subject to the terms and conditions of the GNU
 * Lesser General Public License, as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License
 * for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this distribution; if not, write to:
 * Free Software Foundation, Inc.
 * 51 Franklin Street, Fifth Floor
 * Boston, MA  02110-1301  USA
 */
package org.hibernate.test.cache.jbc2;

import java.util.Iterator;
import java.util.Set;

import org.hibernate.cache.GeneralDataRegion;
import org.hibernate.cache.QueryResultsRegion;
import org.hibernate.cache.Region;
import org.hibernate.cache.jbc2.JBossCacheRegionFactory;
import org.hibernate.cache.jbc2.MultiplexedJBossCacheRegionFactory;
import org.hibernate.cache.jbc2.builder.MultiplexingCacheInstanceManager;
import org.hibernate.cache.jbc2.util.CacheHelper;
import org.hibernate.cache.jbc2.util.NonLockingDataVersion;
import org.hibernate.cfg.Configuration;
import org.hibernate.test.util.CacheTestUtil;
import org.jboss.cache.Cache;
import org.jboss.cache.Fqn;
import org.jboss.cache.Node;
import org.jboss.cache.NodeSPI;
import org.jboss.cache.transaction.BatchModeTransactionManager;

/**
 * Base class for tests of QueryResultsRegion and TimestampsRegion.
 * 
 * @author <a href="brian.stansberry@jboss.com">Brian Stansberry</a>
 * @version $Revision: 1 $
 */
public abstract class AbstractGeneralDataRegionTestCase extends AbstractRegionImplTestCase {

    protected static final String KEY = "Key";
    protected static final String VALUE1 = "value1";
    protected static final String VALUE2 = "value2";

    public AbstractGeneralDataRegionTestCase(String name) {
        super(name);
    }

    @Override
    protected void putInRegion(Region region, Object key, Object value) {
        ((GeneralDataRegion) region).put(key, value);
    }

    @Override
    protected void removeFromRegion(Region region, Object key) {
        ((GeneralDataRegion) region).evict(key);        
    }  

    /**
     * Test method for {@link QueryResultsRegion#evict(java.lang.Object)}.
     * 
     * FIXME add testing of the "immediately without regard for transaction
     * isolation" bit in the CollectionRegionAccessStrategy API.
     */
    public void testEvictOptimistic() throws Exception {
        evictOrRemoveTest("optimistic-shared");
    }

    /**
     * Test method for {@link QueryResultsRegion#evict(java.lang.Object)}.
     * 
     * FIXME add testing of the "immediately without regard for transaction
     * isolation" bit in the CollectionRegionAccessStrategy API.
     */
    public void testEvictPessimistic() throws Exception {
        evictOrRemoveTest("pessimistic-shared");
    }

    private void evictOrRemoveTest(String configName) throws Exception {
    
        Configuration cfg = createConfiguration(configName);
        JBossCacheRegionFactory regionFactory = CacheTestUtil.startRegionFactory(cfg, getCacheTestSupport());
        Cache localCache = getJBossCache(regionFactory);
        boolean invalidation = CacheHelper.isClusteredInvalidation(localCache);
        
        GeneralDataRegion localRegion = (GeneralDataRegion) createRegion(regionFactory, getStandardRegionName(REGION_PREFIX), cfg.getProperties(), null);
        
        cfg = createConfiguration(configName);
        regionFactory = CacheTestUtil.startRegionFactory(cfg, getCacheTestSupport());
        
        GeneralDataRegion remoteRegion = (GeneralDataRegion) createRegion(regionFactory, getStandardRegionName(REGION_PREFIX), cfg.getProperties(), null);
        
        assertNull("local is clean", localRegion.get(KEY));
        assertNull("remote is clean", remoteRegion.get(KEY));
        
        localRegion.put(KEY, VALUE1);
        assertEquals(VALUE1, localRegion.get(KEY));
        
        // allow async propagation
        sleep(250);
        Object expected = invalidation ? null : VALUE1;
        assertEquals(expected, remoteRegion.get(KEY));
        
        localRegion.evict(KEY);
        
        assertEquals(null, localRegion.get(KEY));
        
        assertEquals(null, remoteRegion.get(KEY));
    }

    protected abstract String getStandardRegionName(String regionPrefix);
    
    /**
     * Test method for {@link QueryResultsRegion#evictAll()}.
     * 
     * FIXME add testing of the "immediately without regard for transaction
     * isolation" bit in the CollectionRegionAccessStrategy API.
     */
    public void testEvictAllOptimistic() throws Exception {
        evictOrRemoveAllTest("optimistic-shared");
    }

    /**
     * Test method for {@link QueryResultsRegion#evictAll()}.
     * 
     * FIXME add testing of the "immediately without regard for transaction
     * isolation" bit in the CollectionRegionAccessStrategy API.
     */
    public void testEvictAllPessimistic() throws Exception {
        evictOrRemoveAllTest("pessimistic-shared");
    }

    private void evictOrRemoveAllTest(String configName) throws Exception {
    
        Configuration cfg = createConfiguration(configName);
        JBossCacheRegionFactory regionFactory = CacheTestUtil.startRegionFactory(cfg, getCacheTestSupport());
        Cache localCache = getJBossCache(regionFactory);
        boolean optimistic = "OPTIMISTIC".equals(localCache.getConfiguration().getNodeLockingSchemeString());
        boolean invalidation = CacheHelper.isClusteredInvalidation(localCache);
    
        GeneralDataRegion localRegion = (GeneralDataRegion) createRegion(regionFactory, getStandardRegionName(REGION_PREFIX), cfg.getProperties(), null);
        
        cfg = createConfiguration(configName);
        regionFactory = CacheTestUtil.startRegionFactory(cfg, getCacheTestSupport());
        Cache remoteCache = getJBossCache(regionFactory);
    
        GeneralDataRegion remoteRegion = (GeneralDataRegion) createRegion(regionFactory, getStandardRegionName(REGION_PREFIX), cfg.getProperties(), null);
        Fqn regionFqn = getRegionFqn(getStandardRegionName(REGION_PREFIX), REGION_PREFIX);
        
        Node regionRoot = localCache.getRoot().getChild(regionFqn);
        assertFalse(regionRoot == null);
        Set children = regionRoot.getChildrenNames();
        assertEquals("No children in " + children, 0, children.size());
        assertTrue(regionRoot.isResident());
        
        if (optimistic) {
            assertEquals(NonLockingDataVersion.class, ((NodeSPI) regionRoot).getVersion().getClass());
        }
    
        regionRoot = remoteCache.getRoot().getChild(regionFqn);
        assertFalse(regionRoot == null);
        assertEquals(0, regionRoot.getChildrenNames().size());
        assertTrue(regionRoot.isResident());
        
        if (optimistic) {
            assertEquals(NonLockingDataVersion.class, ((NodeSPI) regionRoot).getVersion().getClass());
        }
        
        assertNull("local is clean", localRegion.get(KEY));
        assertNull("remote is clean", remoteRegion.get(KEY));
        
        localRegion.put(KEY, VALUE1);
        assertEquals(VALUE1, localRegion.get(KEY));     
        
        // Allow async propagation
        sleep(250);
        
        remoteRegion.put(KEY, VALUE1);
        assertEquals(VALUE1, remoteRegion.get(KEY));     
        
        // Allow async propagation
        sleep(250);
        
        if (optimistic) {
            regionRoot = localCache.getRoot().getChild(regionFqn);
            assertEquals(NonLockingDataVersion.class, ((NodeSPI) regionRoot).getVersion().getClass());
            regionRoot = remoteCache.getRoot().getChild(regionFqn);
            assertEquals(NonLockingDataVersion.class, ((NodeSPI) regionRoot).getVersion().getClass());
        }
        
        localRegion.evictAll();
        
        regionRoot = localCache.getRoot().getChild(regionFqn);
        assertFalse(regionRoot == null);
        assertEquals(0, regionRoot.getChildrenNames().size());
        assertTrue(regionRoot.isResident());
    
        regionRoot = remoteCache.getRoot().getChild(regionFqn);
        assertFalse(regionRoot == null);
        if (invalidation) {
            // JBC seems broken: see http://www.jboss.com/index.html?module=bb&op=viewtopic&t=121408
            // FIXME   replace with the following when JBCACHE-1199 and JBCACHE-1200 are done:
            //assertFalse(regionRoot.isValid());
            checkNodeIsEmpty(regionRoot);
        }
        else {
            // Same assertion, just different assertion msg
            assertEquals(0, regionRoot.getChildrenNames().size());
        }        
        assertTrue(regionRoot.isResident());
        
        assertEquals("local is clean", null, localRegion.get(KEY));
        assertEquals("remote is clean", null, remoteRegion.get(KEY));
    }

    private void checkNodeIsEmpty(Node node) {
        assertEquals("Known issue JBCACHE-1200. node " + node.getFqn() + " should not have keys", 0, node.getKeys().size());
        for (Iterator it = node.getChildren().iterator(); it.hasNext(); ) {
            checkNodeIsEmpty((Node) it.next());
        }
    }

    protected Configuration createConfiguration(String configName) {
        Configuration cfg = CacheTestUtil.buildConfiguration("test", MultiplexedJBossCacheRegionFactory.class, false, true);
        cfg.setProperty(MultiplexingCacheInstanceManager.QUERY_CACHE_RESOURCE_PROP, configName);
        cfg.setProperty(MultiplexingCacheInstanceManager.TIMESTAMP_CACHE_RESOURCE_PROP, configName);
        return cfg;
    }

    protected void rollback() {
        try {
            BatchModeTransactionManager.getInstance().rollback();
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        
    }

}