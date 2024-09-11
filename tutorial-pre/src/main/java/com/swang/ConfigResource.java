package com.swang;

import org.apache.helix.HelixDefinedState;
import org.apache.helix.manager.zk.ZKHelixAdmin;
import org.apache.helix.model.InstanceConfig;
import org.apache.helix.model.StateModelDefinition;

public class ConfigResource {
    public static void main(String[] args) {

        String ZK_ADDRESS = "localhost:2199";
        ZKHelixAdmin admin = new ZKHelixAdmin(ZK_ADDRESS);

        String CLUSTER_NAME = "helix-demo";
        admin.addCluster(CLUSTER_NAME);

        String STATE_MODEL_NAME = "LeaderStandby";
        StateModelDefinition.Builder builder = new StateModelDefinition.Builder(STATE_MODEL_NAME);

        String LEADER = "LEADER";
        String STANDBY  = "STANDBY";
        String OFFLINE = "OFFLINE";

        builder.addState(LEADER, 1);
        builder.addState(STANDBY, 2);
        builder.addState(OFFLINE);
        builder.addState(HelixDefinedState.DROPPED.name());


        builder.initialState(OFFLINE);

        builder.addTransition(OFFLINE, STANDBY);
        builder.addTransition(STANDBY, OFFLINE);
        builder.addTransition(STANDBY, LEADER);
        builder.addTransition(LEADER, STANDBY);

        builder.addTransition(OFFLINE, HelixDefinedState.DROPPED.name());

        builder.upperBound(LEADER, 1);

        builder.dynamicUpperBound(STANDBY, "R");

        StateModelDefinition myStateModel = builder.build();
        admin.addStateModelDef(CLUSTER_NAME, STATE_MODEL_NAME, myStateModel);


        String RESOURCE_NAME = "MyDB";
        int NUM_PARTITIONS = 6;
        String MODE = "SEMI_AUTO";
        int NUM_REPLICAS = 2;

        admin.addResource(CLUSTER_NAME, RESOURCE_NAME, NUM_PARTITIONS, STATE_MODEL_NAME, MODE);
        admin.rebalance(CLUSTER_NAME, RESOURCE_NAME, NUM_REPLICAS);
    }
}
