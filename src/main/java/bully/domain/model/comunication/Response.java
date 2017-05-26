package bully.domain.model.comunication;


public class Response {

    public enum Status {
        OK, BAD_REQUEST, INTERNAL_ERROR
    }

    private String content;
    private Status status = Status.OK;

    public Response() {}
    public Response(String content) {
        this.content = content;
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
        return new Response();
    }

    public static Response forgetManMyCandidateIsBadassThanYou() {
        return new Response();
    }

    public static Response ok () {
        return new Response("OK");
    }
}
