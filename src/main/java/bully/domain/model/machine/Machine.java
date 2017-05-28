package bully.domain.model.machine;


import bully.domain.model.cluster.Cluster;
import bully.domain.model.comunication.NetworkAddress;
import bully.domain.model.comunication.Request;
import bully.domain.model.electoral.Context;
import bully.domain.model.electoral.ElectoralZone;

import java.util.UUID;

import static bully.domain.model.comunication.Response.waitingElectionResult;
import static bully.domain.service.language.IfImBelovedLeader.ifImBelovedLeader;
import static bully.domain.service.language.ImACandidate.imACandidate;

public class Machine {

    private final String id;
    private final long score;
    private final String ip;
    private final int port;
    private final String alias;
    private Context context;

    public Machine(String id, String ip, int port, long score) {
        this.id = id;
        this.ip = ip;
        this.port = port;
        this.score = score;
        this.alias = "";
    }

    public Machine(String id, String ip, int port, long score, String alias) {
        this.id = id;
        this.ip = ip;
        this.port = port;
        this.score = score;
        this.alias = alias;
    }


    public Machine(int port, String alias, Context context) {
        this.id = UUID.randomUUID().toString();
        this.ip = NetworkAddress.getIp();
        this.port = port;
        this.score = System.nanoTime();
        this.alias = alias;
        this.context = context;
    }

    public void startProcess() {
        System.out.println("++++++++++++++ Started: " + this.alias + " id: " + this.id);
        final ElectoralZone electoralZone = this.context.getElectoralZone();
        final Cluster cluster = this.context.getCluster();

        if (electoralZone.areYouInElections()) {
            System.out.println("++++++++++++++ " + this.alias + " We are in elections");
            waitingElectionResult();
            return;
        }

        final Leader leader = cluster.takeMeToYourLeader();
        if (leader.belovedLeaderYouAreAlive()) {
            System.out.println("++++++++++++++  We have a beloved leader: " + leader.getId() + " me: " + this.alias);
            ifImBelovedLeader(this).thenExecute(this.context.getLeaderTaskService());
        } else {
            System.out.println("++++++++++++++ " + this.alias + " call new elections");
            electoralZone.invokeNewElections(imACandidate(this));
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

    public String getId() {
        return id;
    }
}
