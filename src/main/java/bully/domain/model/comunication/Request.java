package bully.domain.model.comunication;


import java.util.HashMap;
import java.util.Map;

public class Request {

  public static final String MESSAGE_TYPE = "message-type";
  public static final String ORIGIN = "origin";
  private final Map<String, String> headers;
  private final String body;

  public enum RequestEnum {

    IM_A_CANDIDATE("im-a-candidate"),
    NEW_LEADER("new-leader"),
    COMMAND_TO_EXECUTE("follower-command-to-execute");

    private String value;

    RequestEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }
  }

  public Request(RequestEnum requestEnum) {
    this.headers = new HashMap<>();
    this.body = "";
  }

  public Request(String body, Map<String, String> headers) {
    this.body = body;
    this.headers = headers;
  }

  public static Request imACandidate() {
    return new Request(RequestEnum.IM_A_CANDIDATE);
  }

  public static Request newLeader() {
    return new Request(RequestEnum.NEW_LEADER);
  }

  public static Request commandToExecute() {
    return new Request(RequestEnum.COMMAND_TO_EXECUTE);
  }

  public String getBody() {
    return body;
  }


  public Map<String, String> getHeaders() {
    return this.headers;
  }

}
