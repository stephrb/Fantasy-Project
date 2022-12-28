package fba.model.player;

import fba.utils.MapConstants;
import fba.utils.VarianceCalculator;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PlayerImpl implements Player {
    private final String firstName;
    private final String lastName;
    private final String fullName;
    private final String injuryStatus;
    private final int playerId;
    private final int proTeamId;
    private final double percentOwned;
    private final double percentChange;
    private final double percentStarted;
    private final double averageDraftPosition;
    private final int position;
    private final Map<String, PlayerStats> statsMap;
    private final List<Integer> eligibleSLots;
    private String acquisitionDate;
    private int lineupSlotId;
    private List<Double> previousGameScores;


    public PlayerImpl(
            String firstName,
            String lastName,
            String fullName,
            int playerId,
            int proTeamId,
            double percentOwned,
            double percentChange,
            double percentStarted,
            double averageDraftPosition,
            String injuryStatus,
            int position,
            Map<String, PlayerStats> statsMap,
            List<Integer> eligibleSLots) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.fullName = fullName;
        this.proTeamId = proTeamId;
        this.percentOwned = percentOwned;
        this.percentChange = percentChange;
        this.percentStarted = percentStarted;
        this.averageDraftPosition = averageDraftPosition;
        this.injuryStatus = (injuryStatus.equals("null")) ? "ACTIVE" : injuryStatus;
        this.position = position;
        this.statsMap = statsMap;
        this.eligibleSLots = eligibleSLots;
        this.playerId = playerId;
        this.acquisitionDate = "n/a";
        this.previousGameScores = new ArrayList<>();
    }

    @Override
    public String getFullName() {
        return fullName;
    }

    @Override
    public String getInjuryStatus() {
        return injuryStatus;
    }

    public void setAcquisitionDate(String acquisitionDate) {
        this.acquisitionDate = acquisitionDate;
    }

    @Override
    public void setLineUpSlotId(int lineUpSlotId) {
        this.lineupSlotId = lineUpSlotId;
    }

    @Override
    public String getProTeam() {
        return MapConstants.proTeamNumMap.get(proTeamId);
    }

    public Map<String, PlayerStats> getStatsMap() {
        return statsMap;
    }

    @Override
    public String getPlayerId() {
        return String.valueOf(playerId);
    }

    @Override
    public void setPreviousGameScores(List<Double> previousGameScores) {
        this.previousGameScores = previousGameScores;
    }

    @Override
    public Pair<Double, Double> calculateVarianceAndMean(int numGames) {
        numGames = Math.min(numGames, previousGameScores.size());
        if (previousGameScores.isEmpty()) return new Pair<>(0.0, 0.0);
        return VarianceCalculator.calculateVarianceAndMean(previousGameScores.subList(0, numGames));
    }
}
