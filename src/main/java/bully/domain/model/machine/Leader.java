package bully.domain.model.machine;


import bully.infrastructure.repository.MachineRepositoryTCPImpl;

import java.util.HashSet;

public class Leader extends Machine {

    private final MachineRepository repository;


    public Leader(String id, String ip, int port, long score, String alias) {
        super(id, ip, port, score, alias);
        this.repository = new MachineRepositoryTCPImpl();
    }

    public static Leader toLeader(Machine machine) {
        return new Leader(machine.getId(), machine.getIp(), machine.getPort(), machine.getScore(), machine.getAlias());
    }

    public Boolean belovedLeaderYouAreAlive(Machine from) {
        if ((getIp().isEmpty() && getPort() == 0 && getScore() == 0l)) {
            return false;
        }
        return repository.isAlive(from, this);
    }

    public static Leader dead() {
        return new Leader("","", 0, 0, "");
    }


}
