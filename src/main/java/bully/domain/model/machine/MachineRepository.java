package bully.domain.model.machine;


import bully.domain.model.comunication.Response;
import bully.domain.service.language.From;
import bully.domain.service.language.To;

import java.io.IOException;

public interface MachineRepository {

    Response announcyCandidacy(From from, To to);

    void announceTheNewBeloved(Leader leader, To to);

}
