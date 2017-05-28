package bully.domain.model.machine;


import bully.domain.service.language.MyScore;

public class Candidate extends Machine {


    public Candidate(String id, String ip, int port, long score, String alias) {
        super(id, ip, port, score, alias);
    }

    public Boolean hasScoreGreaterThan(MyScore myScore) {
        return super.getScore() > myScore.getScore();
    }

    public static Candidate toCandidate(Machine machine) {
        return new Candidate(machine.getId(), machine.getIp(), machine.getPort(), machine.getScore(), machine.getAlias());
    }
}
