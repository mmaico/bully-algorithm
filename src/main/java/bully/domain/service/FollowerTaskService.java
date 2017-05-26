package bully.domain.service;

import bully.domain.model.comunication.Request;
import bully.domain.model.electoral.Context;

@FunctionalInterface
public interface FollowerTaskService {

    void execute(Context context, Request request);
}
