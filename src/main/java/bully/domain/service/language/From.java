package bully.domain.service.language;

import bully.domain.model.machine.Candidate;


public class From {

  private final Candidate candidate;

  public From(Candidate candidate) {
    this.candidate = candidate;
  }

  public Candidate getCandidate() {
    return candidate;
  }

  @Override public String toString() {
    return "From{" +
        "candidate=" + candidate.getAlias() +
        '}';
  }
}
