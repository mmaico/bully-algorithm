package bully.domain.model.machine;


import bully.domain.model.cluster.Cluster;
import bully.domain.model.comunication.NetworkAddress;
import bully.domain.model.comunication.Request;
import bully.domain.model.electoral.Context;
import bully.domain.model.electoral.ElectoralRegister;

import static bully.domain.model.comunication.Response.waitingElectionResult;
import static bully.domain.service.language.IfImBelovedLeader.ifImBelovedLeader;
import static bully.domain.service.language.ImACandidate.imACandidate;

public class Machine {

    private final long score;
    private final String ip;
    private final int port;
    private final String alias;
    private Context context;

    public Machine(String ip, int port, long score) {
        this.ip = ip;
        this.port = port;
        this.score = score;
        this.alias = "";
    }

    public Machine(int port, String alias, Context context) {
        this.ip = NetworkAddress.getIp();
        this.port = port;
        this.score = System.nanoTime();
        this.alias = alias;
        this.context = context;
    }

    public void startProcess() {
        System.out.println("++++++++++++++ Started: " + this.alias);
        final ElectoralRegister electoralRegister = this.context.getElectoralRegister();
        final Cluster cluster = this.context.getCluster();

        if (electoralRegister.areYouInElections()) {
            System.out.println("++++++++++++++ " + this.alias + " We are in elections");
            waitingElectionResult();
            return;
        }

        final Leader leader = cluster.takeMeToYourLeader();
        if (leader.belovedLeaderYouAreAlive()) {
            System.out.println("++++++++++++++ " + this.alias + " We have a beloved leader");
            ifImBelovedLeader(this).thenExecute(this.context.getLeaderTaskService());
        } else {
            System.out.println("++++++++++++++ " + this.alias + " call new elections");
            electoralRegister.invokeNewElections(imACandidate(this));
        }
    }

    public void leaderCommand(Request request) {
        this.context.getFollowerTaskService().execute(this.context, request);
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

    public String getAlias() {
        return alias;
    }

    public Context getContext() {
        return context;
    }
}
