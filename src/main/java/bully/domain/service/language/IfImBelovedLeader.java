package bully.domain.service.language;


import bully.domain.model.cluster.Cluster;
import bully.domain.model.machine.Leader;
import bully.domain.model.machine.Machine;
import bully.domain.service.LeaderTaskService;

public class IfImBelovedLeader {

  private final Machine machine;

  public IfImBelovedLeader(Machine machine) {
    this.machine = machine;
  }

  public static IfImBelovedLeader ifImBelovedLeader(Machine machine) {
    return new IfImBelovedLeader(machine);
  }

  public void thenExecute(LeaderTaskService leaderTaskService) {
    Cluster cluster = machine.getContext().getCluster();
    Leader leader = cluster.takeMeToYourLeader();
    if (leader.getIp().equals(this.machine.getIp())
        && leader.getPort() == this.machine.getPort()) {
      leaderTaskService.execute(this.machine.getContext());
    }
  }

}
