package bully.domain.model.electoral;


import bully.domain.model.cluster.Cluster;
import bully.domain.model.machine.Candidate;
import bully.domain.model.machine.Machine;

public class Context {

    private Cluster cluster;
    private Machine machine;
    private ElectoralRegister electoralRegister;

    public Candidate getMyCandidate() {
        return new Candidate(machine.getIp(), machine.getPort(), machine.getScore());
    }

    public Cluster getCluster() {
        return cluster;
    }

    public void setCluster(Cluster cluster) {
        this.cluster = cluster;
    }

    public Machine getMachine() {
        return machine;
    }

    public void setMachine(Machine machine) {
        this.machine = machine;
    }

    public ElectoralRegister getElectoralRegister() {
        return electoralRegister;
    }

    public void setElectoralRegister(ElectoralRegister electoralRegister) {
        this.electoralRegister = electoralRegister;
    }
}
