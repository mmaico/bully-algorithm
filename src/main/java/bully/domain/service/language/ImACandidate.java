package bully.domain.service.language;


import bully.domain.model.machine.Candidate;
import bully.domain.model.machine.Machine;

public class ImACandidate {

    private final Machine machine;

    public ImACandidate(Machine machine) {
        this.machine = machine;
    }

    public Candidate get() {
        return new Candidate(machine.getIp(), machine.getPort(), machine.getScore());
    }

    public static ImACandidate imACandidate(Machine machine) {
        return new ImACandidate(machine);
    }


}
