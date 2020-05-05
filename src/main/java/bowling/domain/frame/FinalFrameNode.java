package bowling.domain.frame;

import static bowling.domain.bowlresult.BonusResult.NULL_RESULT;

import bowling.domain.FrameResult;
import bowling.domain.FrameResults;
import bowling.domain.bowlresult.BonusResult;
import bowling.domain.bowlresult.RegularResult;
import bowling.domain.Round;
import bowling.domain.Score;
import bowling.domain.framestate.FrameState;
import bowling.domain.framestate.Strike;
import bowling.exception.CannotBowlException;

public class FinalFrameNode implements FrameNode {

  private RegularResult regularResult;
  private BonusResult bonusResult;

  public FinalFrameNode() {
    regularResult = new RegularResult();
    bonusResult = NULL_RESULT;
  }

  @Override
  public void addFrameResult(FrameResults frameResults) {
    frameResults.add(new FrameResult(regularResult, bonusResult, calculateScore()));
  }

  @Override
  public FrameNode getNextFrame() {
    return NullFrameNode.of(Round.of(Round.FINAL_ROUND).next());
  }

  @Override
  public FrameNode roll(int pinCount) throws CannotBowlException {
    if (!regularResult.isFinished()) {
      regularResult.roll(pinCount);
      prepareBonusBowl();
      return this;
    }

    bonusResult.roll(pinCount);
    return this;
  }

  private void prepareBonusBowl() {
    if (regularResult.isFinished()) {
      FrameState state = regularResult.getState();
      bonusResult = new BonusResult(state.getBonusBallCount());
    }
  }

  @Override
  public Score calculateBonusScore(int bonusBowlCount) {
    if (bonusBowlCount == MAX_BONUS_BOWL && regularResult.getState() == Strike.getInstance()) {
      return Score.add(Score.of(regularResult.getFirst()), bonusResult.getScore(1));
    }

    return regularResult.getScore(bonusBowlCount);
  }

  @Override
  public Score calculateScore() {
    Score regular = regularResult.getScoreAll();
    Score bonus = bonusResult.getScoreAll();
    return Score.add(regular, bonus);
  }

  @Override
  public boolean isFinished() {
    return regularResult.isFinished() && bonusResult.isFinished();
  }

  @Override
  public boolean isFinalFrame() {
    return true;
  }
}