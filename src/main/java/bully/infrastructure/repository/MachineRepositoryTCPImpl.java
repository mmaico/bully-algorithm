package bully.infrastructure.repository;

import bully.domain.model.comunication.Response;
import bully.domain.model.machine.Leader;
import bully.domain.model.machine.MachineRepository;
import bully.domain.service.language.From;
import bully.domain.service.language.To;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;

import java.io.IOException;


public class MachineRepositoryTCPImpl implements MachineRepository {

  @Override
  public Response announcyCandidacy(From from, To to) {
    String url = "http://" + to.getMachine().getIp() + ":" + to.getMachine().getPort();
    System.out.println("################# Announce Candidacy" + url);
    try {
      org.apache.http.client.fluent.Response teste = Request.Get(url)
          .connectTimeout(2000)
          .socketTimeout(2000)
          .bodyString("teste", ContentType.APPLICATION_JSON)
          .execute();
      System.out.println(teste);
      System.out.println("############### teste");
    } catch (IOException e) {
      e.printStackTrace();
    }
    return Response.ok();
  }

  @Override
  public void announceTheNewBeloved(Leader leader, To to) {
    System.out.println("################# Announce The New Beloved Leader");
  }
}
