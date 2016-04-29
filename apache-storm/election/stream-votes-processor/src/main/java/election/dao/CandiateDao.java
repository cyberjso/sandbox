package election.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CandiateDao {
    private List<CandidateEntity> candiates = new ArrayList<CandidateEntity>();

    public CandiateDao() {
        candiates.add(new CandidateEntity("1", "candidate 1"));
        candiates.add(new CandidateEntity("2", "candidate 2"));
        candiates.add(new CandidateEntity("3", "candidate 3"));
        candiates.add(new CandidateEntity("4", "candidate 4"));
        candiates.add(new CandidateEntity("5", "candidate 5"));
    }


    public Optional<List<CandidateEntity>> findById(String id) {
        return Optional.of(candiates.stream().filter(candidate -> candidate.getId().equals(id)).collect(Collectors.toList()));
    }

}
