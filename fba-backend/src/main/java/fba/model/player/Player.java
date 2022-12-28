package fba.model.player;

import javafx.util.Pair;

import java.util.List;
import java.util.Map;

public interface Player {
  String getFullName();

  String getInjuryStatus();

  void setAcquisitionDate(String acquisitionDate);

  void setLineUpSlotId(int lineUpSlotId);

  String getProTeam();

  Map<String, PlayerStats> getStatsMap();

  String getPlayerId();

  void setPreviousGameScores(List<Double> previousGameScores);

  Pair<Double, Double> calculateVarianceAndMean(int numGames);
}
