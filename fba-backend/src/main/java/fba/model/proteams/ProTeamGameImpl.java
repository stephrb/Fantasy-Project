package fba.model.proteams;

import java.time.Instant;
import java.util.Date;

public class ProTeamGameImpl implements ProTeamGame {
    private final int awayProTeamId, homeProTeamId, scoringPeriodId;
    private boolean statsOfficial;
    private final Date date;

    public ProTeamGameImpl(int awayProTeamId, int homeProTeamId, int scoringPeriodId, boolean statsOfficial, Date date) {
        this.awayProTeamId = awayProTeamId;
        this.homeProTeamId = homeProTeamId;
        this.scoringPeriodId = scoringPeriodId;
        this.statsOfficial = statsOfficial;
        this.date = date;
    }

    @Override
    public boolean hasHappened() {
        return date.before(Date.from(Instant.now()));
    }
}
