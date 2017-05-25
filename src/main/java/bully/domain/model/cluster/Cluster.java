package bully.domain.model.cluster;


import bully.domain.model.machine.Candidate;
import bully.domain.model.machine.Leader;
import bully.domain.service.language.AnnounceNewLeaderService;
import bully.domain.service.language.BelovedLeader;
import bully.domain.service.language.CandidatesScoreGreaterService;

public class Cluster {

    private Leader leader;

    public CandidatesScoreGreaterService announce(Candidate candidate) {
        return myScore -> {

        };
    }

    public AnnounceNewLeaderService youHaveANew(BelovedLeader leader) {
        return () -> {
            this.leader = leader.getMachine();
        };
    }

    public Leader tellMeWhoIsYourLeader() {
        return leader;
    }
}
