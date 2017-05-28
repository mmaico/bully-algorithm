package bully.infrastructure.repository;

import bully.domain.model.comunication.Request;
import bully.domain.model.comunication.Response;
import bully.domain.model.machine.Leader;
import bully.domain.model.machine.MachineRepository;
import bully.domain.service.language.From;
import bully.domain.service.language.To;
import bully.infrastructure.client.Client;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;

import java.util.Optional;

import static bully.domain.model.comunication.Request.RequestEnum.IM_A_CANDIDATE;


public class MachineRepositoryTCPImpl implements MachineRepository {


  @Override
  public Response announcyCandidacy(From from, To to) {

    Optional<HttpResponse> httpResponse = Client.post(from.getCandidate(), to, IM_A_CANDIDATE);
    if (!httpResponse.isPresent()) {
      return Response.withoutResponse();
    }

    if (httpResponse.get().getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
      Header result = httpResponse.get().getFirstHeader(Response.KEY);
      if (result != null) {
        if (result.getValue().equals(Response.ResponseEnum.IM_BADASS_THAN_YOU.getValue())) {
          return Response.waitingElectionResult();
        }
      }
    }
    return Response.withoutResponse();
  }

  @Override
  public void announceTheNewBeloved(Leader leader, To to) {
    Client.post(leader, to, Request.RequestEnum.NEW_LEADER);
  }
}
