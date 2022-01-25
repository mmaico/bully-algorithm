package bully.domain.model.comunication;


import java.util.HashMap;
import java.util.Map;

public class Response {
    public static final String KEY = "response-type";

    public enum ResponseEnum {

        NOT_RECOGNIZED_MACHINE("not-recognized-machine-id"),
        WITHOUT_RESPONSE("without-response"),
        IM_BADASS_THAN_YOU("im-badass-than-you"),
        ILL_WAIT_FOR_THE_RESULT("ill-wait-for-the-result"),
        WELCOME_MY_BELOVED_LEADER("welcome-my-beloved-leader"),
        COMMAND_EXECUTING_OK("follower-command-executing-ok"),
        COMMAND_EXECUTING_ERROR("follower-command-executing-error"),
        IS_ALIVE("is-alive"),
        IM_DEAD("im-dead-man");

        private String value;

        ResponseEnum(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public enum Status {
        OK(200), BAD_REQUEST(400), INTERNAL_ERROR(500), SERVICE_UNAVAILABLE(503);
        int code;
        Status(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }
    }

    private final Map<String, String> headers = new HashMap<>();
    private String content;
    private Status status = Status.OK;

    public Response() {}
    public Response(String content) {
        this.content = content;
    }

    public Response(ResponseEnum response, Status status) {
        this.headers.put(KEY, response.value);
        this.status = status;
        this.content = "";
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public static Response waitingElectionResult() {
        return new Response(ResponseEnum.ILL_WAIT_FOR_THE_RESULT, Status.OK);
    }

    public static Response forgetManMyCandidateIsBadassThanYou() {
        return new Response(ResponseEnum.IM_BADASS_THAN_YOU, Status.OK);
    }

    public static Response withoutResponse() {
        return new Response(ResponseEnum.WITHOUT_RESPONSE, Status.OK);
    }

    public static Response itsOK() {
        return new Response(ResponseEnum.IS_ALIVE, Status.OK);
    }

    public static Response imDead() {
        return new Response(ResponseEnum.IM_DEAD, Status.SERVICE_UNAVAILABLE);
    }

    public static Response notRecognizedMachine() {
        return new Response(ResponseEnum.NOT_RECOGNIZED_MACHINE, Status.BAD_REQUEST);
    }

    public static Response welcomeMyBelovedLeader() {
        return new Response(ResponseEnum.WELCOME_MY_BELOVED_LEADER, Status.OK);
    }

    public static Response commandExecutingOk() {
        return new Response(ResponseEnum.COMMAND_EXECUTING_OK, Status.OK);
    }

    public static Response commandExecutingError() {
        return new Response(ResponseEnum.COMMAND_EXECUTING_ERROR, Status.OK);
    }

    public Map<String, String> getHeaders() {
        return headers;
    }
}
