package bully.domain.model.cluster;


import bully.domain.model.comunication.Responses;
import bully.domain.model.electoral.Context;
import bully.domain.model.machine.Candidate;
import bully.domain.model.machine.Leader;
import bully.domain.model.machine.Machine;
import bully.domain.model.machine.Machines;
import bully.domain.service.AnnounceNewLeaderService;
import bully.domain.service.CandidatesScoreGreaterService;
import bully.domain.service.language.BelovedLeader;
import bully.domain.service.language.MyScore;

import java.util.Optional;

import static bully.domain.service.language.From.from;

public class Cluster {

    private Leader leader;
    private final Machines machines = new Machines();
    private final Context context;

    {
        leader = Leader.dead();
    }

    public Cluster(Context context) {
        this.context = context;
    }

    public CandidatesScoreGreaterService announce(Candidate candidate) {
        return (MyScore myScore) -> {
            final Machines strongs = machines.getWithScoreGreaterThan(myScore.getScore());
            final Responses responses = strongs.announceCandidacy(from(candidate));

            if (strongs.size() == 0 || !responses.hasCandidatesBadassThanMe()) {
                this.leader = Leader.toLeader(candidate);
                System.out.println("Im The fucking KING <------ LEADER: " + this.leader.getAlias());
                this.context.getElectoralZone().electionResult(this.leader);
                machines.announceTheNewBeloved(leader);
            }
        };
    }

    public AnnounceNewLeaderService youHaveANew(BelovedLeader belovedLeader) {
        return () -> this.leader = Leader.toLeader(belovedLeader.getMachine());
    }

    public Optional<Machine> findOne(String id) {
        return this.machines.stream()
                .filter(machine -> machine.getId().equals(id)).findFirst();
    }

    public Leader takeMeToYourLeader() {
        return leader;
    }

    public void addMachine(Machine machine) {
        machines.add(machine);
    }

}
