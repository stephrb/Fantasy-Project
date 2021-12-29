import model.*;
import model.Team.*;
import utils.*;

public class Main {

  public static void main(String[] args) {
    //Application.launch(view.AppLauncher.class);
    Model model = Factory.createModel("1213148421");
    for (Team team : model.getPowerRankings()) {
      System.out.println(team.getName() + "\t" + team.getPowerRankingScore());
    }
  }
}
