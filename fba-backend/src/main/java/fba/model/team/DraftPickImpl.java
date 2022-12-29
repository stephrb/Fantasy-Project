package fba.model.team;

public class DraftPickImpl implements DraftPick {

    final private String playerId;
    final private int roundId;
    final private int teamId;
    final private int roundPickNumber;
    final private int pickNumber;

    public DraftPickImpl(String playerId, int roundId, int teamId, int roundPickNumber, int pickNumber) {
        this.playerId = playerId;
        this.roundId = roundId;
        this.teamId = teamId;
        this.roundPickNumber = roundPickNumber;
        this.pickNumber = pickNumber;
    }

    public String getPlayerId() {
        return playerId;
    }

    public int getTeamId() {
        return teamId;
    }
}
