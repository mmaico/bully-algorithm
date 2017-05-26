package bully.domain.service;

import bully.domain.model.comunication.Responses;
import bully.domain.service.language.MyScore;

@FunctionalInterface
public interface CandidatesScoreGreaterService {

    void onlyCadidatesWhenScoreGreaterThan(MyScore myScore);
}
