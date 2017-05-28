package bully.infrastructure.task;

import bully.domain.model.comunication.Request;
import bully.domain.model.comunication.Response;
import bully.domain.model.electoral.Context;
import bully.domain.service.FollowerTaskService;


public class FollowerTaskServiceDefaultImpl implements FollowerTaskService {

  @Override public Response execute(Context context, Request request) {
    System.out.println("########## ----> Follower task invoked: " + context.getMachine().getAlias());

    return Response.commandExecutingOk();
  }

}
