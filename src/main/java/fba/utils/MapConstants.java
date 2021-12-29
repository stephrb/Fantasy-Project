package fba.utils;

import java.util.HashMap;
import java.util.Map;

public final class MapConstants {
  public static final Map<Integer, String> positionMap;

  static {
    positionMap = new HashMap<>();
    positionMap.put(0, "PG");
    positionMap.put(1, "SG");
    positionMap.put(2, "SF");
    positionMap.put(3, "PF");
    positionMap.put(4, "C");
    positionMap.put(5, "G");
    positionMap.put(6, "F");
    positionMap.put(7, "SG/SF");
    positionMap.put(8, "G/F");
    positionMap.put(9, "PF/C");
    positionMap.put(10, "F/C");
    positionMap.put(11, "UT");
    positionMap.put(12, "BE");
    positionMap.put(13, "IR");
    positionMap.put(14, "");
    positionMap.put(15, "Rookie");
  }

  public static final Map<Integer, String> proTeamMap;

  static {
    proTeamMap = new HashMap<>();
    proTeamMap.put(0, "FA");
    proTeamMap.put(1, "ATL");
    proTeamMap.put(2, "BOS");
    proTeamMap.put(3, "NOP");
    proTeamMap.put(4, "CHI");
    proTeamMap.put(5, "CLE");
    proTeamMap.put(6, "DAL");
    proTeamMap.put(7, "DEN");
    proTeamMap.put(8, "DET");
    proTeamMap.put(9, "GSW");
    proTeamMap.put(10, "HOU");
    proTeamMap.put(11, "IND");
    proTeamMap.put(12, "LAC");
    proTeamMap.put(13, "LAL");
    proTeamMap.put(14, "MIA");
    proTeamMap.put(15, "MIL");
    proTeamMap.put(16, "MIN");
    proTeamMap.put(17, "BKN");
    proTeamMap.put(18, "NYK");
    proTeamMap.put(19, "ORL");
    proTeamMap.put(20, "PHI");
    proTeamMap.put(21, "PHX");
    proTeamMap.put(22, "POR");
    proTeamMap.put(23, "SAC");
    proTeamMap.put(24, "SAS");
    proTeamMap.put(25, "OKC");
    proTeamMap.put(26, "UTA");
    proTeamMap.put(27, "WAS");
    proTeamMap.put(28, "TOR");
    proTeamMap.put(29, "MEM");
    proTeamMap.put(30, "CHA");
  }

  public static final Map<Integer, String> statsMap;

  static {
    statsMap = new HashMap<>();
    statsMap.put(0, "PTS");
    statsMap.put(1, "BLK");
    statsMap.put(2, "STL");
    statsMap.put(3, "AST");
    statsMap.put(4, "OREB");
    statsMap.put(5, "DREB");
    statsMap.put(6, "REB");
    statsMap.put(7, "");
    statsMap.put(8, "");
    statsMap.put(9, "PF");
    statsMap.put(10, "");
    statsMap.put(11, "TO");
    statsMap.put(12, "");
    statsMap.put(13, "FGM");
    statsMap.put(14, "FGA");
    statsMap.put(15, "FTM");
    statsMap.put(16, "FTA");
    statsMap.put(17, "3PTM");
    statsMap.put(18, "3PTA");
    statsMap.put(19, "FG%");
    statsMap.put(20, "FT%");
    statsMap.put(21, "3PT%");
    statsMap.put(22, "");
    statsMap.put(23, "");
    statsMap.put(24, "");
    statsMap.put(25, "");
    statsMap.put(26, "");
    statsMap.put(27, "");
    statsMap.put(28, "MPG");
    statsMap.put(29, "");
    statsMap.put(30, "");
    statsMap.put(31, "");
    statsMap.put(32, "");
    statsMap.put(33, "");
    statsMap.put(34, "");
    statsMap.put(35, "");
    statsMap.put(36, "");
    statsMap.put(37, "");
    statsMap.put(38, "");
    statsMap.put(39, "");
    statsMap.put(40, "MIN");
    statsMap.put(41, "GS");
    statsMap.put(42, "GP");
    statsMap.put(43, "");
    statsMap.put(44, "");
    statsMap.put(45, "");
  }

  public static final Map<String, String> statIdMap;

  static {
    statIdMap = new HashMap<>();
    statIdMap.put("00", "Season");
    statIdMap.put("01", "Last 7");
    statIdMap.put("02", "Last 15");
    statIdMap.put("03", "Last 30");
    statIdMap.put("10", "Projected");
  }

  public static final Map<String, Integer> dayOfWeekMap;

  static {
    dayOfWeekMap = new HashMap<>();
    dayOfWeekMap.put("Mon", 0);
    dayOfWeekMap.put("Tue", 1);
    dayOfWeekMap.put("Wed", 2);
    dayOfWeekMap.put("Thu", 3);
    dayOfWeekMap.put("Fri", 4);
    dayOfWeekMap.put("Sat", 5);
    dayOfWeekMap.put("Sun", 6);
  }
}
