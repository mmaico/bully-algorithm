package bully.domain.service;

import bully.domain.model.comunication.Request;
import bully.domain.model.comunication.Response;
import bully.domain.model.electoral.Context;

@FunctionalInterface
public interface FollowerTaskService {

    Response execute(Context context, Request request);
}
