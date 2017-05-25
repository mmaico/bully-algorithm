package bully.domain.service.language;

import bully.domain.model.machine.Candidate;


public class From {

  private final Candidate candidate;

  public From(Candidate candidate) {
    this.candidate = candidate;
  }

  public static From from(Candidate candidate) {
    return new From(candidate);
  }
}
