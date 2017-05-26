package bully.domain.service;

import bully.domain.model.electoral.Context;

@FunctionalInterface
public interface LeaderTaskService {

    void execute(Context context);
}
