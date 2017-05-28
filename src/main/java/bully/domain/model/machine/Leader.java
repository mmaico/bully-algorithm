package bully.domain.model.machine;


public class Leader extends Machine {

    public Leader(String id, String ip, int port, long score) {
        super(id, ip, port, score);
    }

    public static Leader toLeader(Machine machine) {
        return new Leader(machine.getId(), machine.getIp(), machine.getPort(), machine.getScore());
    }

    public Boolean belovedLeaderYouAreAlive() {
        return !(getIp().isEmpty() && getPort() == 0 && getScore() == 0l);
    }

    public static Leader dead() {
        return new Leader("","", 0, 0);
    }


}
