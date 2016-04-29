package election.model;

import java.io.Serializable;

public class Vote implements Serializable {
    private String candidateId;
    private String campaingId;
    private String timestamp;

    public Vote() {}

    public Vote(String candidateId, String time, String campaingId) {
        this.candidateId = candidateId;
        this.timestamp = time;
        this.campaingId = campaingId;
    }

    public void setCandidateId(String candidateId) { this.candidateId = candidateId; }

    public String getCandidateId() { return candidateId; }

    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }

    public String getTimestamp() { return timestamp; }

    public String getCampaingId() { return campaingId; }

    public void setCampaingId(String campaingId) { this.campaingId = campaingId; }

}
