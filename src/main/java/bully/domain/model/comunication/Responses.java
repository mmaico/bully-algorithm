package bully.domain.model.comunication;


import java.util.Set;

public class Responses {

  private final Set<Response> responses;

  public Responses(Set<Response> responses) {
    this.responses = responses;
  }

  public boolean someoneReplied() {
    return Boolean.TRUE;
  }

  public boolean nobodyRepliedMe() {
    return Boolean.TRUE;
  }
}
