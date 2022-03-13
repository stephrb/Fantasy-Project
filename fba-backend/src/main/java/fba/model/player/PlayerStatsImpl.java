package fba.model.player;

import java.util.Map;

public class PlayerStatsImpl implements PlayerStats {
  private final Map<String, Double> avg;
  private final Map<String, Double> total;

  public PlayerStatsImpl(
      Map<String, Double> avg, Map<String, Double> total, double avgPoints, double totalPoints) {
    avg.put("FPTS", avgPoints);
    this.avg = avg;
    total.put("FPTS", totalPoints);
    this.total = total;
  }

  public Map<String, Double> getAvg() {
    return avg;
  }

  public Map<String, Double> getTotal() {
    return total;
  }
}
