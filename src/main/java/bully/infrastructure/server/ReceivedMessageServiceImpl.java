package bully.infrastructure.server;

import bully.domain.model.comunication.Request;
import bully.domain.model.comunication.Response;
import bully.domain.model.electoral.Context;
import bully.domain.model.machine.Leader;
import bully.domain.model.machine.Machine;
import bully.domain.service.ReceivedMessagesService;
import bully.infrastructure.client.BodyExtractorHelper;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static bully.domain.model.comunication.Request.RequestEnum.*;
import static bully.domain.model.machine.Candidate.toCandidate;


public class ReceivedMessageServiceImpl implements ReceivedMessagesService {

    private final Context context;

    private Map<Request.RequestEnum, Command> commands = new HashMap<>();

    public ReceivedMessageServiceImpl(Context context) {
        this.context = context;
        commands.put(IS_ALIVE, (machine, args) -> {
            if (!this.context.getMachine().isAlive()) {
                return Response.imDead();
            }
            return Response.itsOK();
        });
        commands.put(IM_A_CANDIDATE, (machine, args) -> this.context.getElectoralZone().newElections(toCandidate(machine)));

        commands.put(NEW_LEADER, (machine, args) -> {
            final Leader leader = Leader.toLeader(machine);
            System.out.println("<3 S3 My "+ context.getMachine().getAlias() +" new beloved Leader: " + leader.getAlias());
            this.context.getElectoralZone().electionResult(leader);

            return Response.withoutResponse();
        });

        commands.put(COMMAND_TO_EXECUTE, (machine, args) -> {
            Request request = (Request) args[0];
            return this.context.getFollowerTaskService().execute(context, request);
        });

        commands.put(KILL, (machine, args) -> {
            this.context.getMachine().iWantYouToDie();
            return Response.imDead();
        });
    }

    @Override
    public Response messageReceived(Request request) {

        String origin = request.getHeaders().get(Request.ORIGIN);
        String inClienRequestId = BodyExtractorHelper.extractId(origin);
        Request.RequestEnum command = getBy(request.getHeaders().get(Request.MESSAGE_TYPE));
        Optional<Machine> machine = this.context.getCluster().findOne(inClienRequestId);

        if ((KILL != command) && (!machine.isPresent())) {
            System.out.println("++++++++++++ MACHINE NOT RECOGNIZED IN CLUSTER: " + inClienRequestId);
            return Response.notRecognizedMachine();
        }

        if (command == null) {
            System.out.println("++++++++++++ HEADER NOT RECOGNIZED: " + request.getHeaders().get(Request.MESSAGE_TYPE));
            return Response.notRecognizedMachine();
        }

        return this.commands.get(command).execute(machine.orElse(null), request);
    }

    private interface Command {
        Response execute(Machine machine, Object ... args);
    }
}
