package com.swang;

import org.apache.helix.manager.zk.ZKHelixAdmin;

/**
 * Hello world!
 */
public class CreateCluster {
    public static void main(String[] args) {

        String ZK_ADDRESS = "localhost:2199";
        ZKHelixAdmin zkHelixAdmin = new ZKHelixAdmin(ZK_ADDRESS);

        String CLUSTER_NAME = "helix-demo";
        zkHelixAdmin.addCluster(CLUSTER_NAME);
    }
}
