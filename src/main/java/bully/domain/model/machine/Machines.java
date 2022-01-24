package bully.domain.model.machine;


import bully.domain.CollectionBehavior;
import bully.domain.model.comunication.Response;
import bully.domain.model.comunication.Responses;
import bully.domain.service.language.From;
import bully.infrastructure.repository.MachineRepositoryTCPImpl;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static bully.domain.service.language.To.to;

public class Machines extends CollectionBehavior<Machine> {

  private final Set<Machine> machines;

  private final MachineRepository repository;

  public Machines() {
    this.machines = new HashSet<>();
    this.repository = new MachineRepositoryTCPImpl();
  }

  public Machines(Set<Machine> machines) {
    this();
    this.machines.addAll(machines);
  }

  @Override
  public Collection<Machine> getCollection() {
    return this.machines;
  }

  public Responses announceCandidacy(From from) {

    final Set<Response> responses = machines.stream()
        .map(machine -> repository.announcyCandidacy(from, to(machine)))
        .collect(Collectors.toSet());

    return new Responses(responses);
  }

  public void announceTheNewBeloved(Leader leader) {
    this.machines.stream()
        .forEach(machine -> repository.announceTheNewBeloved(leader, to(machine)));
  }

  public Machines getWithScoreGreaterThan(long score) {
    final Set<Machine> machinesScoreGreater = machines.stream()
         .filter(machine -> machine.getScore() != score)
        .filter(machine -> machine.getScore() < score)
        .collect(Collectors.toSet());

    return new Machines(machinesScoreGreater);
  }
}
