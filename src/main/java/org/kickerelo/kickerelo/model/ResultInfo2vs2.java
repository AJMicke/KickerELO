package org.kickerelo.kickerelo.model;

public class ResultInfo2vs2 {
    float oldEloWinnerFront;
    float oldEloWinnerBack;
    float oldEloLoserFront;
    float oldEloLoserBack;
    float newEloWinnerFront;
    float newEloWinnerBack;
    float newEloLoserFront;
    float newEloLoserBack;
    short loserGoals;

    public ResultInfo2vs2(float oldEloWinnerFront, float oldEloWinnerBack, float oldEloLoserFront, float oldEloLoserBack, short loserGoals) {
        this.oldEloWinnerFront = oldEloWinnerFront;
        this.oldEloWinnerBack = oldEloWinnerBack;
        this.oldEloLoserFront = oldEloLoserFront;
        this.oldEloLoserBack = oldEloLoserBack;
        this.loserGoals = loserGoals;
    }

    public float getOldEloWinnerFront() {
        return oldEloWinnerFront;
    }

    public void setOldEloWinnerFront(float oldEloWinnerFront) {
        this.oldEloWinnerFront = oldEloWinnerFront;
    }

    public float getOldEloWinnerBack() {
        return oldEloWinnerBack;
    }

    public void setOldEloWinnerBack(float oldEloWinnerBack) {
        this.oldEloWinnerBack = oldEloWinnerBack;
    }

    public float getOldEloLoserFront() {
        return oldEloLoserFront;
    }

    public void setOldEloLoserFront(float oldEloLoserFront) {
        this.oldEloLoserFront = oldEloLoserFront;
    }

    public float getOldEloLoserBack() {
        return oldEloLoserBack;
    }

    public void setOldEloLoserBack(float oldEloLoserBack) {
        this.oldEloLoserBack = oldEloLoserBack;
    }

    public float getNewEloWinnerFront() {
        return newEloWinnerFront;
    }

    public void setNewEloWinnerFront(float newEloWinnerFront) {
        this.newEloWinnerFront = newEloWinnerFront;
    }

    public float getNewEloWinnerBack() {
        return newEloWinnerBack;
    }

    public void setNewEloWinnerBack(float newEloWinnerBack) {
        this.newEloWinnerBack = newEloWinnerBack;
    }

    public float getNewEloLoserFront() {
        return newEloLoserFront;
    }

    public void setNewEloLoserFront(float newEloLoserFront) {
        this.newEloLoserFront = newEloLoserFront;
    }

    public float getNewEloLoserBack() {
        return newEloLoserBack;
    }

    public void setNewEloLoserBack(float newEloLoserBack) {
        this.newEloLoserBack = newEloLoserBack;
    }

    public short getLoserGoals() {
        return loserGoals;
    }

    public void setLoserGoals(short loserGoals) {
        this.loserGoals = loserGoals;
    }
}
