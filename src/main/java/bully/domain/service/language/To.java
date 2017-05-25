package bully.domain.service.language;


import bully.domain.model.machine.Machine;

public class To {

  private final Machine machine;

  public To(Machine machine) {
    this.machine = machine;
  }

  public Machine getMachine() {
    return machine;
  }

  public static To to(Machine machine) {
    return new To(machine);
  }
}
