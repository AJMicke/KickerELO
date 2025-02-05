package org.kickerelo.kickerelo.model;

public class ResultInfo1vs1 {
    float oldEloWinner;
    float oldEloLoser;
    float newEloWinner;
    float newEloLoser;
    short loserGoals;

    public ResultInfo1vs1(float oldEloWinner, short loserGoals, float oldEloLoser) {
        this.oldEloWinner = oldEloWinner;
        this.loserGoals = loserGoals;
        this.oldEloLoser = oldEloLoser;
    }

    public float getOldEloWinner() {
        return oldEloWinner;
    }

    public void setOldEloWinner(float oldEloWinner) {
        this.oldEloWinner = oldEloWinner;
    }

    public float getOldEloLoser() {
        return oldEloLoser;
    }

    public void setOldEloLoser(float oldEloLoser) {
        this.oldEloLoser = oldEloLoser;
    }

    public float getNewEloWinner() {
        return newEloWinner;
    }

    public void setNewEloWinner(float newEloWinner) {
        this.newEloWinner = newEloWinner;
    }

    public float getNewEloLoser() {
        return newEloLoser;
    }

    public void setNewEloLoser(float newEloLoser) {
        this.newEloLoser = newEloLoser;
    }

    public short getLoserGoals() {
        return loserGoals;
    }

    public void setLoserGoals(short loserGoals) {
        this.loserGoals = loserGoals;
    }
}
