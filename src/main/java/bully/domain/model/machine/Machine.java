package bully.domain.model.machine;


import bully.domain.model.cluster.Cluster;
import bully.domain.model.electoral.Context;
import bully.domain.model.electoral.ElectoralRegister;

import static bully.domain.model.comunication.Response.waitingElectionResult;

public class Machine {

    private final long score;
    private final String ip;
    private final int port;
    private Context context;

    public Machine(String ip, int port) {
        this.ip = ip;
        this.port = port;
        this.score = System.nanoTime();
    }

    public Machine(String ip, int port, long score) {
        this.ip = ip;
        this.port = port;
        this.score = score;
    }


    public void startProcess() {
        final ElectoralRegister electoralRegister = this.context.getElectoralRegister();
        final Cluster cluster = this.context.getCluster();

        if (electoralRegister.areYouInElections()) {
            waitingElectionResult();
            return;
        }

        final Leader leader = cluster.tellMeWhoIsYourLeader();
        if (leader.belovedLeaderYouAreAlive()) {
            //ifImTheBelovedLeader(this).execute
        } else {
            electoralRegister.invokeNewElections();
        }


    }


    public long getScore() {
        return score;
    }

    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }
}
