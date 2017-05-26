package bully.domain.model.cluster;


import bully.domain.model.comunication.Responses;
import bully.domain.model.machine.Candidate;
import bully.domain.model.machine.Leader;
import bully.domain.model.machine.Machine;
import bully.domain.model.machine.Machines;
import bully.domain.service.AnnounceNewLeaderService;
import bully.domain.service.language.BelovedLeader;
import bully.domain.service.CandidatesScoreGreaterService;
import bully.domain.service.language.MyScore;

import static bully.domain.service.language.From.from;

public class Cluster {

    private static Leader leader;
    private static final Machines machines = new Machines();

    static {
        leader = Leader.dead();
    }

    public CandidatesScoreGreaterService announce(Candidate candidate) {
        return (MyScore myScore) -> {
            final Machines strongs = machines.getWithScoreGreaterThan(myScore.getScore());
            final Responses responses = strongs.announceCandidacy(from(candidate));
            if (!responses.nobodyRepliedMe()) {
                BelovedLeader belovedLeader = new BelovedLeader(candidate);
                youHaveANew(belovedLeader);
            }
        };
    }

    public AnnounceNewLeaderService youHaveANew(BelovedLeader belovedLeader) {
        return () -> {
            this.leader = Leader.toLeader(belovedLeader.getMachine());
            machines.announceTheNewBeloved(leader);
        };
    }

    public Leader takeMeToYourLeader() {
        return leader;
    }

    public void addMachine(Machine machine) {
        machines.add(machine);
    }
}
