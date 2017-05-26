package bully.domain.model.machine;


import bully.domain.service.language.MyScore;

public class Candidate extends Machine {


    public Candidate(String ip, int port, long score, String alias) {
        super(ip, port, score);
    }

    public Boolean hasScoreGreaterThan(MyScore myScore) {
        return super.getScore() > myScore.getScore();
    }
}
