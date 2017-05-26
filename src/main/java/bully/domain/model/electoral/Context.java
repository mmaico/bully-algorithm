package bully.domain.model.electoral;


import bully.domain.model.cluster.Cluster;
import bully.domain.model.machine.Candidate;
import bully.domain.model.machine.Machine;
import bully.domain.service.FollowerTaskService;
import bully.domain.service.LeaderTaskService;
import bully.domain.service.ReceivedMessagesService;
import bully.infrastructure.server.ReceivedMessageServiceImpl;
import bully.infrastructure.server.TCPServer;
import bully.infrastructure.task.FollowerTaskServiceDefaultImpl;
import bully.infrastructure.task.LeaderTaskServiceDefaultImpl;

public class Context {

    private final Cluster cluster;
    private final Machine machine;
    private final ElectoralRegister electoralRegister;
    private final FollowerTaskService followerTaskService;
    private final LeaderTaskService leaderTaskService;
    private final ReceivedMessagesService receivedMessagesService;

    public Context(Cluster cluster, int port, String alias) {
        this.cluster = cluster;
        this.machine = new Machine(port, alias, this);
        this.electoralRegister = new ElectoralRegister(this);
        this.followerTaskService = new FollowerTaskServiceDefaultImpl();
        this.leaderTaskService = new LeaderTaskServiceDefaultImpl();
        this.receivedMessagesService = new ReceivedMessageServiceImpl(this);
        new TCPServer(receivedMessagesService).initServer(port);
    }

    public Context(Cluster cluster, int port, String alias, FollowerTaskService followerTaskService, LeaderTaskService leaderTaskService) {
        this.cluster = cluster;
        this.machine = new Machine(port, alias, this);
        this.electoralRegister = new ElectoralRegister(this);
        this.followerTaskService = followerTaskService;
        this.leaderTaskService = leaderTaskService;
        this.receivedMessagesService = new ReceivedMessageServiceImpl(this);
        new TCPServer(receivedMessagesService).initServer(port);
    }

    public Candidate getMyCandidate() {
        return new Candidate(machine.getIp(), machine.getPort(), machine.getScore(), machine.getAlias());
    }

    public Cluster getCluster() {
        return cluster;
    }

    public Machine getMachine() {
        return machine;
    }

    public ElectoralRegister getElectoralRegister() {
        return electoralRegister;
    }

    public FollowerTaskService getFollowerTaskService() {
        return followerTaskService;
    }

    public LeaderTaskService getLeaderTaskService() {
        return leaderTaskService;
    }
}
