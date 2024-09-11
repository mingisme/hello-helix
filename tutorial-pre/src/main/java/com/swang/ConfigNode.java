package com.swang;

import org.apache.helix.manager.zk.ZKHelixAdmin;
import org.apache.helix.model.InstanceConfig;

public class ConfigNode {
    public static void main(String[] args) {

        String ZK_ADDRESS = "localhost:2199";
        ZKHelixAdmin admin = new ZKHelixAdmin(ZK_ADDRESS);

        String CLUSTER_NAME = "helix-demo";
        admin.addCluster(CLUSTER_NAME);

        int NUM_NODES = 2;
        String hosts[] = {"localhost", "localhost"};
        String ports[] = {"7000","7001"};
        for (int i = 0; i < NUM_NODES; i++){
            InstanceConfig instanceConfig = new InstanceConfig(hosts[i] + "_" + ports[i]);
            instanceConfig.setHostName(hosts[i]);
            instanceConfig.setPort(ports[i]);
            instanceConfig.setInstanceEnabled(true);

            instanceConfig.getRecord().setSimpleField("key1","value1");
            admin.addInstance(CLUSTER_NAME, instanceConfig);
        }
    }
}
