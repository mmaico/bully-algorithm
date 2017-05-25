package bully.domain.service.language;


import bully.domain.model.machine.Machine;

public class BelovedLeader {

    private Machine machine;

    public BelovedLeader(Machine machine) {
        this.machine = machine;
    }

    public static BelovedLeader belovedLeader(Machine machine) {
        return new BelovedLeader(machine);
    }

    public Machine getMachine() {
        return machine;
    }
}
