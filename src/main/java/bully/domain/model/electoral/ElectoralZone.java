package bully.domain.model.electoral;


import bully.domain.model.comunication.Response;
import bully.domain.model.machine.Candidate;
import bully.domain.model.machine.Leader;
import bully.domain.service.language.BelovedLeader;
import bully.domain.service.language.ImACandidate;

import static bully.domain.model.comunication.Response.forgetManMyCandidateIsBadassThanYou;
import static bully.domain.model.comunication.Response.waitingElectionResult;
import static bully.domain.model.electoral.ElectoralState.WE_ARE_IN_ELECTION;
import static bully.domain.model.electoral.ElectoralState.WE_ARE_NOT_IN_ELECTION;
import static bully.domain.service.language.BelovedLeader.belovedLeader;
import static bully.domain.service.language.MyScore.my;

public class ElectoralZone {

    private ElectoralState state = WE_ARE_NOT_IN_ELECTION;
    private Context context;


    public ElectoralZone(Context context) {
        this.context = context;
    }

    public void electionResult(Leader leader) {
        this.state = WE_ARE_NOT_IN_ELECTION;
        this.context.getCluster().youHaveANew(belovedLeader(leader));
    }

    public Boolean areYouInElections() {
        return this.state == WE_ARE_IN_ELECTION;
    }

    public Response  newElections(Candidate candidate) {
        this.state = WE_ARE_IN_ELECTION;

        final Candidate myCandidate = this.context.getMyCandidate();
        final long score = myCandidate.getScore();

        if (candidate.hasScoreGreaterThan(my(score))) {
            return waitingElectionResult();
        } else {
            this.context.getCluster().announce(myCandidate).onlyCadidatesWhenScoreGreaterThan(my(score));
            return forgetManMyCandidateIsBadassThanYou();
        }
    }

    public void invokeNewElections(ImACandidate imACandidate) {
        this.state = WE_ARE_IN_ELECTION;
        System.out.println("####### Election: " + this.context.getMachine().getAlias() + " " + this.state);
        final Candidate candidate = imACandidate.get();

        this.context.getCluster().announce(candidate).onlyCadidatesWhenScoreGreaterThan(my(candidate.getScore()));
    }


}
