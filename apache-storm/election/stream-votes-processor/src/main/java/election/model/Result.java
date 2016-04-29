package election.model;

public class Result {
    private String candidateId;
    private Integer votes =  new Integer(0);

    public Integer getVotes() {
        return votes;
    }

    public void setVotes(Integer votes) {
        this.votes = votes;
    }

    public String getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(String candidateId) {
        this.candidateId = candidateId;
    }

    @Override
    public String toString() {
        return "Result{" + "candidateId='" + candidateId + '\'' +", votes=" + votes + '}';
    }

}
