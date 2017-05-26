package bully.infrastructure.task;


import bully.domain.model.electoral.Context;
import bully.domain.service.LeaderTaskService;

public class LeaderTaskServiceDefaultImpl implements LeaderTaskService {

  @Override
  public void execute(Context context) {
    System.out.println("########## ----> Leader task Service");
  }
}
