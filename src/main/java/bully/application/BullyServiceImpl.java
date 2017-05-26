package bully.application;


import bully.domain.model.cluster.Cluster;
import bully.domain.model.comunication.NetworkAddress;
import bully.domain.model.electoral.Context;
import bully.domain.model.machine.Machine;

import java.net.UnknownHostException;
import java.util.Timer;
import java.util.TimerTask;

public class BullyServiceImpl implements BullyFacade {

  private static Context context;
  private final Timer timer;

  public BullyServiceImpl(int port, String alias, Cluster cluster) {
    context = new Context(cluster, port, alias);
    this.timer = new Timer();
  }

  public void start() {
    this.timer.schedule(new TimerTask() {
      @Override public void run() {
        context.getMachine().startProcess();
      }
    }, 0, 5000);

  }

    public static void main(String[] args) {
      Cluster cluster = new Cluster();
      cluster.addMachine(new Machine(NetworkAddress.getIp(), 4444, System.nanoTime()));
      cluster.addMachine(new Machine(NetworkAddress.getIp(), 5555, System.nanoTime()));
      cluster.addMachine(new Machine(NetworkAddress.getIp(), 6666, System.nanoTime()));

      new Thread(() -> {
        BullyServiceImpl bullyService = new BullyServiceImpl(4444, "machine-one", cluster);
        bullyService.start();
      }).start();

      new Thread(() -> {
        BullyServiceImpl bullyService = new BullyServiceImpl(5555, "machine-two", cluster);
        bullyService.start();
      }).start();

      new Thread(() -> {
        BullyServiceImpl bullyService = new BullyServiceImpl(6666, "machine-three", cluster);
        bullyService.start();
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
