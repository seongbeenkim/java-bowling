package bowling.domain.frame;

import bowling.domain.score.Pins;
import bowling.domain.state.State;

public abstract class Frame {
    public static final int FRAME_INIT = 0;

    public static Frame init() {
        return new NormalFrame(FRAME_INIT);
    }

    public abstract Frame next(int frameNo);

    public abstract int getFrameNo();

    public abstract void bowl(Pins pins);

    public abstract void secondBowl(int frameNo, State state, Pins pins);

    public abstract void thirdBowl(int userIndex, State state, Pins thirdPins);

    public abstract State getState(int userIndex);

    public abstract State getLastState();

    public abstract int getScore(int index);

    public abstract int getFirstScore(int index);
}