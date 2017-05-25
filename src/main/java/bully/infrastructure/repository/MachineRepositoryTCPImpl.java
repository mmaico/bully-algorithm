package bully.infrastructure.repository;

import bully.domain.model.comunication.Response;
import bully.domain.model.machine.Leader;
import bully.domain.model.machine.MachineRepository;
import bully.domain.service.language.From;
import bully.domain.service.language.To;


public class MachineRepositoryTCPImpl implements MachineRepository {

  @Override
  public Response announcyCandidacy(From from, To to) {
    return null;
  }

  @Override
  public void announceTheNewBeloved(Leader leader, To to) {

  }
}
