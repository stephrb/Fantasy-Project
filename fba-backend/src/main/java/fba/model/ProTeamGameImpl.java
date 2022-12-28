package fba.model;

import java.util.Date;

public class ProTeamGameImpl implements ProTeamGame {
    private int awayProTeamId, homeProTeamId, scoringPeriodId;
    private boolean statsOfficial;
    private Date date;

    public ProTeamGameImpl(int awayProTeamId, int homeProTeamId, int scoringPeriodId, boolean statsOfficial, Date date) {
        this.awayProTeamId = awayProTeamId;
        this.homeProTeamId = homeProTeamId;
        this.scoringPeriodId = scoringPeriodId;
        this.statsOfficial = statsOfficial;
        this.date = date;
    }
}
