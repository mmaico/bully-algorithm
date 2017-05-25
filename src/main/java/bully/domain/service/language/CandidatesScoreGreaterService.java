package bully.domain.service.language;

@FunctionalInterface
public interface CandidatesScoreGreaterService {

    void onlyCadidatesWhenScoreGreaterThan(MyScore myScore);
}
