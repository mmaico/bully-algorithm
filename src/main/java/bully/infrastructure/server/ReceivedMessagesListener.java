package bully.infrastructure.server;


import bully.domain.model.comunication.Request;
import bully.domain.model.comunication.Response;

public interface ReceivedMessagesListener {

  Response messageReceived(Request request);

}
