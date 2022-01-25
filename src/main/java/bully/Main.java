package bully;

import bully.domain.model.machine.Machine;

public class Main {

    public static void main(String[] args) {
        Machine machine4444 = Machine.create(4444, "machine-one");
        Machine machine5555 = Machine.create(5555, "machine-two");
        Machine machine6666 = Machine.create(6666, "machine-three");

        new Thread(() -> {
            machine4444.getContext().getCluster().addMachine(machine4444);
            machine4444.getContext().getCluster().addMachine(machine5555);
            machine4444.getContext().getCluster().addMachine(machine6666);
            machine4444.start();
        }).start();

        new Thread(() -> {
            machine5555.getContext().getCluster().addMachine(machine4444);
            machine5555.getContext().getCluster().addMachine(machine5555);
            machine5555.getContext().getCluster().addMachine(machine6666);
            machine5555.start();
        }).start();

        new Thread(() -> {
            machine6666.getContext().getCluster().addMachine(machine4444);
            machine6666.getContext().getCluster().addMachine(machine5555);
            machine6666.getContext().getCluster().addMachine(machine6666);
            machine6666.start();
        }).start();

        while(true) {
            try {
                System.out.println("System in Execution...");
                Thread.sleep(7000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
