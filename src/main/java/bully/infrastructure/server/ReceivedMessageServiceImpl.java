package bully.infrastructure.server;

import bully.domain.model.comunication.Request;
import bully.domain.model.comunication.Response;
import bully.domain.model.electoral.Context;
import bully.domain.model.machine.Leader;
import bully.domain.model.machine.Machine;
import bully.domain.service.ReceivedMessagesService;
import bully.infrastructure.client.BodyExtractorHelper;

import java.util.Optional;

import static bully.domain.model.comunication.Request.RequestEnum.COMMAND_TO_EXECUTE;
import static bully.domain.model.comunication.Request.RequestEnum.IM_A_CANDIDATE;
import static bully.domain.model.comunication.Request.RequestEnum.NEW_LEADER;
import static bully.domain.model.machine.Candidate.toCandidate;


public class ReceivedMessageServiceImpl implements ReceivedMessagesService {

    private final Context context;

    public ReceivedMessageServiceImpl(Context context) {
        this.context = context;
    }

    @Override
    public Response messageReceived(Request request) {

        String origin = request.getHeaders().get(Request.ORIGIN);
        String inClienRequestId = BodyExtractorHelper.extractId(origin);

        Optional<Machine> machine = this.context.getCluster().findOne(inClienRequestId);

        if (!machine.isPresent()) {
            System.out.println("++++++++++++ MACHINE NOT RECOGNIZED IN CLUSTER: " + inClienRequestId);
            return Response.notRecognizedMachine();
        }

        String headerCommand = request.getHeaders().get(Request.MESSAGE_TYPE);

        if (headerCommand == null || headerCommand.isEmpty()) {
            System.out.println("++++++++++++ HEADER NOT RECOGNIZED: " + headerCommand);
            return Response.notRecognizedMachine();
        }

        if (headerCommand.trim().equalsIgnoreCase(IM_A_CANDIDATE.getValue())) {
            return this.context.getElectoralZone().newElections(toCandidate(machine.get()));
        } else if (headerCommand.trim().equalsIgnoreCase(NEW_LEADER.getValue())) {
            final Leader leader = Leader.toLeader(machine.get());
            System.out.println("<3 S3 My "+ context.getMachine().getAlias() +" new beloved Leader: " + leader.getAlias());
            this.context.getElectoralZone().electionResult(leader);

            return Response.withoutResponse();
        } else if (headerCommand.trim().equalsIgnoreCase(COMMAND_TO_EXECUTE.getValue())) {
            return this.context.getFollowerTaskService().execute(context, request);
        }

        return Response.withoutResponse();
    }


}
