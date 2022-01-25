package bully.domain.model.machine;


import bully.domain.model.cluster.Cluster;
import bully.domain.model.comunication.NetworkAddress;
import bully.domain.model.comunication.Request;
import bully.domain.model.electoral.Context;
import bully.domain.model.electoral.ElectoralZone;

import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

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
    private Boolean isAlive = true;
    private final Timer timer;

    public Machine(String id, String ip, int port, long score) {
        this.id = id;
        this.ip = ip;
        this.port = port;
        this.score = score;
        this.alias = "";
        this.timer = new Timer();
    }

    public Machine(String id, String ip, int port, long score, String alias) {
        this.id = id;
        this.ip = ip;
        this.port = port;
        this.score = score;
        this.alias = alias;
        this.timer = new Timer();
    }


    public Machine(int port, String alias, Context context) {
        this.id = UUID.randomUUID().toString();
        this.ip = NetworkAddress.getIp();
        this.port = port;
        this.score = ThreadLocalRandom.current().nextInt(1, 1000);
        this.alias = alias;
        this.context = context;
        this.timer = new Timer();
    }

    public void start() {
        this.timer.schedule(new TimerTask() {
            @Override public void run() {
                startProcess();
            }
        }, 0, 1000);
    }

    public void startProcess() {
        if (!isAlive) {
            System.out.println("++++++++++++++ I'm Dead: " + this.alias + " id: " + this.id + " score: " + this.score);
            return;
        }
        System.out.println("++++++++++++++ Started: " + this.alias + " id: " + this.id + " score: " + this.score);
        final ElectoralZone electoralZone = this.context.getElectoralZone();
        final Cluster cluster = this.context.getCluster();

        if (electoralZone.areYouInElections()) {
            System.out.println("++++++++++++++ " + this.alias + " We are in elections");
            waitingElectionResult();
            return;
        }

        final Leader leader = cluster.takeMeToYourLeader();
        if (leader.belovedLeaderYouAreAlive(this)) {
            System.out.println("######### for the machine: " + this.alias + "  the beloved leader is: " + leader.getId() + " alias: " + leader.getAlias() + " score: " + leader.getScore());
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

    public void setContext(Context context) {
        this.context = context;
    }

    public String getId() {
        return id;
    }
    public void iWantYouToDie() {
        this.isAlive = false;
    }
    public Boolean isAlive() {
        return isAlive;
    }

    public static Machine create(int port, String alias) {
        Machine machine = new Machine(port, alias, null);
        new Context(port, machine);
        return machine;
    }
}
