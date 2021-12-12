import java.util.List;

public interface Team {

  /** @return list of players */
  List<Player> getPlayers();

  /** @param player the player to add to the team */
  void addPlayer(Player player);

  String getName();
}
