package bully.infrastructure.server;

import bully.domain.model.comunication.Request;
import bully.domain.model.comunication.Response;
import bully.domain.model.electoral.Context;
import bully.domain.service.ReceivedMessagesService;


public class ReceivedMessageServiceImpl implements ReceivedMessagesService {

  private final Context context;

  public ReceivedMessageServiceImpl(Context context) {
    this.context = context;
  }

  @Override
  public Response messageReceived(Request request) {
    this.context.getMachine().leaderCommand(request);
    return Response.ok();
  }
}
