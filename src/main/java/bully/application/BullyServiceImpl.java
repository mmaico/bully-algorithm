package bully.application;


import bully.domain.model.electoral.Context;

import java.util.Timer;
import java.util.TimerTask;

public class BullyServiceImpl implements BullyFacade {

  private Context context;
  private final Timer timer;

  public BullyServiceImpl(int port, String alias) {
    context = new Context(port, alias);
    this.timer = new Timer();
  }

  public void start() {
    this.timer.schedule(new TimerTask() {
      @Override public void run() {
        context.getMachine().startProcess();
      }
    }, 0, 5000);

  }

  public Context getContext() {
    return context;
  }

  public static void main(String[] args) {

    BullyServiceImpl bullyServiceOne = new BullyServiceImpl(4444, "machine-one");
    BullyServiceImpl bullyServiceTwo = new BullyServiceImpl(5555, "machine-two");
    BullyServiceImpl bullyServiceThree = new BullyServiceImpl(6666, "machine-three");


    new Thread(() -> {
      bullyServiceOne.getContext().getCluster().addMachine(bullyServiceOne.getContext().getMachine());
      bullyServiceOne.getContext().getCluster().addMachine(bullyServiceTwo.getContext().getMachine());
      bullyServiceOne.getContext().getCluster().addMachine(bullyServiceThree.getContext().getMachine());

      bullyServiceOne.start();
    }).start();

    new Thread(() -> {
      bullyServiceTwo.getContext().getCluster().addMachine(bullyServiceOne.getContext().getMachine());
      bullyServiceTwo.getContext().getCluster().addMachine(bullyServiceTwo.getContext().getMachine());
      bullyServiceTwo.getContext().getCluster().addMachine(bullyServiceThree.getContext().getMachine());

      bullyServiceTwo.start();
    }).start();

    new Thread(() -> {
      bullyServiceThree.getContext().getCluster().addMachine(bullyServiceOne.getContext().getMachine());
      bullyServiceThree.getContext().getCluster().addMachine(bullyServiceTwo.getContext().getMachine());
      bullyServiceThree.getContext().getCluster().addMachine(bullyServiceThree.getContext().getMachine());

      bullyServiceThree.start();
    }).start();

    while(true) {
      try {
        System.out.println("System in Execution...");
        Thread.sleep(5000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }

    }

//  public static void main(String[] args) {
//    Timer timer = new Timer();
//
//    timer.schedule(new TimerTask() {
//      @Override public void run() {
//        System.out.println("Teste ....");
//      }
//    }, 0, 1000);
//
//    while(true) {
//      try {
//        System.out.println("System in Execution...");
//        Thread.sleep(5000);
//      } catch (InterruptedException e) {
//        e.printStackTrace();
//      }
//    }
//
//  }

}
