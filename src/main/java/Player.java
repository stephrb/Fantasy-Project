import java.util.Map;

public interface Player {
    String getFullName();

    String getInjuryStatus();

    void setAcquisitionDate(String acquisitionDate);

    void setLineUpSlotId(int lineUpSlotId);

    String getProTeam();

    Map<String, PlayerStats> getStatsMap();
}
