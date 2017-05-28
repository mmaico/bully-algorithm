package bully.domain.model.comunication;


import java.util.Set;

import static bully.domain.model.comunication.Response.ResponseEnum.IM_BADASS_THAN_YOU;

public class Responses {

  private final Set<Response> responses;

  public Responses(Set<Response> responses) {
    this.responses = responses;
  }

  public boolean hasCandidatesBadassThanMe() {
    return responses.stream()
            .filter(response -> {
              String header = response.getHeaders().get(Response.KEY);
              return header != null && IM_BADASS_THAN_YOU.getValue().equals(header);
            }).count() > 0;
  }

  public boolean nobodyRepliedMe() {
    return Boolean.TRUE;
  }
}
