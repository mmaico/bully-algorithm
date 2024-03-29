package bully.domain.model.electoral;


import bully.domain.model.cluster.Cluster;
import bully.domain.model.machine.Candidate;
import bully.domain.model.machine.Machine;
import bully.domain.service.FollowerTaskService;
import bully.domain.service.LeaderTaskService;
import bully.domain.service.ReceivedMessagesService;
import bully.infrastructure.server.ReceivedMessageServiceImpl;
import bully.infrastructure.server.CustomHTTPServer;
import bully.infrastructure.task.FollowerTaskServiceDefaultImpl;
import bully.infrastructure.task.LeaderTaskServiceDefaultImpl;

public class Context {

    private final Cluster cluster;
    private final Machine machine;
    private final ElectoralZone electoralZone;
    private final FollowerTaskService followerTaskService;
    private final LeaderTaskService leaderTaskService;
    private final ReceivedMessagesService receivedMessagesService;

    public Context(int port, Machine machine) {
        this.cluster = new Cluster(this);
        this.machine = machine;
        this.machine.setContext(this);
        this.electoralZone = new ElectoralZone(this);
        this.followerTaskService = new FollowerTaskServiceDefaultImpl();
        this.leaderTaskService = new LeaderTaskServiceDefaultImpl();
        this.receivedMessagesService = new ReceivedMessageServiceImpl(this);
        new CustomHTTPServer(receivedMessagesService).initServer(port);
    }

    public Candidate getMyCandidate() {
        return new Candidate(machine.getId(), machine.getIp(), machine.getPort(), machine.getScore(), machine.getAlias());
    }

    public Cluster getCluster() {
        return cluster;
    }

    public Machine getMachine() {
        return machine;
    }

    public ElectoralZone getElectoralZone() {
        return electoralZone;
    }

    public FollowerTaskService getFollowerTaskService() {
        return followerTaskService;
    }

    public LeaderTaskService getLeaderTaskService() {
        return leaderTaskService;
    }
}
