package bowling.domain.bowlresult;

import bowling.domain.Score;
import bowling.domain.Trial;
import bowling.domain.framestate.FrameState;
import bowling.domain.framestate.Miss;
import bowling.domain.framestate.NotPlayed;
import bowling.domain.framestate.Spare;
import bowling.domain.framestate.Strike;
import bowling.exception.CannotBowlException;

public class BonusResult extends BowlResult {

  public BonusResult(int bonusBallCount) {
    if (bonusBallCount > Strike.BONUS_BOWL || bonusBallCount < Miss.BONUS_BOWL) {
      throw new IllegalArgumentException("보너스 볼은 최소 0개 최대 2개입니다.");
    }

    if (bonusBallCount == Strike.BONUS_BOWL) {
      first = Trial.initialize();
      second = Trial.initialize();
    }

    if (bonusBallCount == Spare.BONUS_BOWL) {
      first = Trial.initialize();
      second = Trial.block();
    }

    if (bonusBallCount == Miss.BONUS_BOWL) {
      first = Trial.block();
      second = Trial.block();
    }
  }

  public BonusResult() { }

  @Override
  public FrameState getState() {
    return NotPlayed.getInstance();
  }

  @Override
  public void roll(int pinCount) throws CannotBowlException {
    if (isFinished()) {
      throw new CannotBowlException("이번 프레임에서 가능한 최대 시도를 넘었습니다.");
    }

    if (first.isNotPlayed()) {
      first.roll(pinCount);
      return;
    }

    second.roll(pinCount);
    if (!first.isMaxPin()) {
      second.validateSecondTrial(first);
    }
  }

  public static BonusResult NULL_RESULT = new BonusResult() {

    @Override
    public Trial getFirst() {
      return Trial.initialize();
    }

    @Override
    public Trial getSecond() {
      return Trial.initialize();
    }

    @Override
    public void roll(int pinCount) {
    }

    @Override
    public Score getScore(int trialCount) {
      return Score.ofNull();
    }

    @Override
    public boolean isFinished() {
      return false;
    }
  };
}