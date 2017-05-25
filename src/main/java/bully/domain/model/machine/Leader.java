package bully.domain.model.machine;


public class Leader extends Machine {

    public Leader(String ip, int port, long score) {
        super(ip, port, score);
    }

    public Boolean belovedLeaderYouAreAlive() {
        return Boolean.FALSE;
    }

}
