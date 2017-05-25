package bully.domain.service.language;

import bully.domain.model.comunication.Responses;

@FunctionalInterface
public interface CandidatesScoreGreaterService {

    void onlyCadidatesWhenScoreGreaterThan(MyScore myScore);
}
