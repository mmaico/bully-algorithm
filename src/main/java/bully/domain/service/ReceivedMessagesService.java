package bully.domain.service;


import bully.domain.model.comunication.Request;
import bully.domain.model.comunication.Response;

public interface ReceivedMessagesService {

  Response messageReceived(Request request);
}
