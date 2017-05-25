package bully.domain.service.language;


public class MyScore {

    private final long score;

    public MyScore(long score) {
        this.score = score;
    }

    public static MyScore my(long score) {
        return new MyScore(score);

    }

    public long getScore() {
        return score;
    }
}
