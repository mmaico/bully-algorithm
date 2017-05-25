package bully.domain.service.language;


import bully.domain.model.machine.Machine;

public class ImACandidate {

    private final Machine machine;

    public ImACandidate(Machine machine) {
        this.machine = machine;
    }

    public static ImACandidate imACandidate(Machine machine) {
        return new ImACandidate(machine);
    }
}
