package bully.domain.service;

import bully.domain.model.electoral.Context;

@FunctionalInterface
public interface BusinessTaskService {

    void execute(Context context);
}
