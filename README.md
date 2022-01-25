# Bully algorithm implementation

Create instances in the main class, the first parameter is a http port and the second an alias:
        
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

### kill a machine to see elections happening again
Put the port of the machine you want to kill and the kill command in the header 

    curl --location --request GET 'http://localhost:4444' --header 'message-type: kill'
On Postman
![On Postman](src/main/resources/post-print.png?raw=true "On Postman")
