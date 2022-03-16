package com.qthegamep.admin.config;

import com.hazelcast.config.*;
import com.hazelcast.spi.merge.PutIfAbsentMergePolicy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

@Configuration
public class HazelcastConfig {

    private final Integer eventStoreMapBackupCount;
    private final Integer eventStoreMapMergePolicyConfigBatchSize;
    private final Integer sentNotificationsMapBackupCount;
    private final Integer sentNotificationsMapMergePolicyConfigBatchSize;
    private final String hazelcastJmxConfig;

    public HazelcastConfig(@Value("${event.store.map.backup.count}") Integer eventStoreMapBackupCount,
                           @Value("${event.store.map.merge.policy.config.batch.size}") Integer eventStoreMapMergePolicyConfigBatchSize,
                           @Value("${sent.notifications.map.backup.count}") Integer sentNotificationsMapBackupCount,
                           @Value("${sent.notifications.map.merge.policy.config.batch.size}") Integer sentNotificationsMapMergePolicyConfigBatchSize,
                           @Value("${hazelcast.jmx.config}") String hazelcastJmxConfig) {
        this.eventStoreMapBackupCount = eventStoreMapBackupCount;
        this.eventStoreMapMergePolicyConfigBatchSize = eventStoreMapMergePolicyConfigBatchSize;
        this.sentNotificationsMapBackupCount = sentNotificationsMapBackupCount;
        this.sentNotificationsMapMergePolicyConfigBatchSize = sentNotificationsMapMergePolicyConfigBatchSize;
        this.hazelcastJmxConfig = hazelcastJmxConfig;
    }

    @Bean
    public Config hazelcast() {
        MapConfig eventStoreMap = new MapConfig("spring-boot-admin-event-store")
                .setInMemoryFormat(InMemoryFormat.OBJECT)
                .setBackupCount(eventStoreMapBackupCount)
                .setEvictionConfig(new EvictionConfig().setEvictionPolicy(EvictionPolicy.NONE))
                .setMergePolicyConfig(new MergePolicyConfig(PutIfAbsentMergePolicy.class.getName(), eventStoreMapMergePolicyConfigBatchSize));
        MapConfig sentNotificationsMap = new MapConfig("spring-boot-admin-application-store")
                .setInMemoryFormat(InMemoryFormat.OBJECT)
                .setBackupCount(sentNotificationsMapBackupCount)
                .setEvictionConfig(new EvictionConfig().setEvictionPolicy(EvictionPolicy.LRU))
                .setMergePolicyConfig(new MergePolicyConfig(PutIfAbsentMergePolicy.class.getName(), sentNotificationsMapMergePolicyConfigBatchSize));
        Config config = new Config();
        config.addMapConfig(eventStoreMap);
        config.addMapConfig(sentNotificationsMap);
        config.setProperty("hazelcast.jmx", hazelcastJmxConfig);
        config.getNetworkConfig()
                .getJoin()
                .getMulticastConfig()
                .setEnabled(false);
        TcpIpConfig tcpIpConfig = config.getNetworkConfig()
                .getJoin()
                .getTcpIpConfig();
        tcpIpConfig.setEnabled(true);
        tcpIpConfig.setMembers(Collections.singletonList("127.0.0.1"));
        return config;
    }
}
