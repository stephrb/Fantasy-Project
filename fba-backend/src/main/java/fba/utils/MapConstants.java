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

  public static final Map<Integer, String> proTeamNumMap;

  static {
    proTeamNumMap = new HashMap<>();
    proTeamNumMap.put(0, "FA");
    proTeamNumMap.put(1, "ATL");
    proTeamNumMap.put(2, "BOS");
    proTeamNumMap.put(3, "NOP");
    proTeamNumMap.put(4, "CHI");
    proTeamNumMap.put(5, "CLE");
    proTeamNumMap.put(6, "DAL");
    proTeamNumMap.put(7, "DEN");
    proTeamNumMap.put(8, "DET");
    proTeamNumMap.put(9, "GSW");
    proTeamNumMap.put(10, "HOU");
    proTeamNumMap.put(11, "IND");
    proTeamNumMap.put(12, "LAC");
    proTeamNumMap.put(13, "LAL");
    proTeamNumMap.put(14, "MIA");
    proTeamNumMap.put(15, "MIL");
    proTeamNumMap.put(16, "MIN");
    proTeamNumMap.put(17, "BKN");
    proTeamNumMap.put(18, "NYK");
    proTeamNumMap.put(19, "ORL");
    proTeamNumMap.put(20, "PHI");
    proTeamNumMap.put(21, "PHX");
    proTeamNumMap.put(22, "POR");
    proTeamNumMap.put(23, "SAC");
    proTeamNumMap.put(24, "SAS");
    proTeamNumMap.put(25, "OKC");
    proTeamNumMap.put(26, "UTA");
    proTeamNumMap.put(27, "WAS");
    proTeamNumMap.put(28, "TOR");
    proTeamNumMap.put(29, "MEM");
    proTeamNumMap.put(30, "CHA");
  }

  public static final Map<String, String> proTeamStringMap;

  static {
    proTeamStringMap = new HashMap<>();
    proTeamStringMap.put("Free Agent", "FA");
    proTeamStringMap.put("Atlanta Hawks", "ATL");
    proTeamStringMap.put("Boston Celtics", "BOS");
    proTeamStringMap.put("New Orleans Pelicans", "NOP");
    proTeamStringMap.put("Chicago Bulls", "CHI");
    proTeamStringMap.put("Cleveland Cavaliers", "CLE");
    proTeamStringMap.put("Dallas Mavericks", "DAL");
    proTeamStringMap.put("Denver Nuggets", "DEN");
    proTeamStringMap.put("Detroit Pistons", "DET");
    proTeamStringMap.put("Golden State Warriors", "GSW");
    proTeamStringMap.put("Houston Rockets", "HOU");
    proTeamStringMap.put("Indiana Pacers", "IND");
    proTeamStringMap.put("Los Angeles Clippers", "LAC");
    proTeamStringMap.put("Los Angeles Lakers", "LAL");
    proTeamStringMap.put("Miami Heat", "MIA");
    proTeamStringMap.put("Milwaukee Bucks", "MIL");
    proTeamStringMap.put("Minnesota Timberwolves", "MIN");
    proTeamStringMap.put("Brooklyn Nets", "BKN");
    proTeamStringMap.put("New York Knicks", "NYK");
    proTeamStringMap.put("Orlando Magic", "ORL");
    proTeamStringMap.put("Philadelphia 76ers", "PHI");
    proTeamStringMap.put("Phoenix Suns", "PHX");
    proTeamStringMap.put("Portland Trail Blazers", "POR");
    proTeamStringMap.put("Sacramento Kings", "SAC");
    proTeamStringMap.put("San Antonio Spurs", "SAS");
    proTeamStringMap.put("Oklahoma City Thunder", "OKC");
    proTeamStringMap.put("Utah Jazz", "UTA");
    proTeamStringMap.put("Washington Wizards", "WAS");
    proTeamStringMap.put("Toronto Raptors", "TOR");
    proTeamStringMap.put("Memphis Grizzlies", "MEM");
    proTeamStringMap.put("Charlotte Hornets", "CHA");
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
