package bowling.domain;

public enum Mark {
    STRIKE("X"),
    SPARE("/"),
    GUTTER("-");

    private static final int PIN_COUNT_MAX = 10;

    private final String mark;

    Mark(String mark) {
        this.mark = mark;
    }

    protected static String firstMark(PinCount pinCount) {
        if (pinCount.isStrike()) {
            return STRIKE.mark;
        }

        if (pinCount.isGutter()) {
            return GUTTER.mark;
        }

        return String.valueOf(pinCount.value());
    }

    protected static String secondMark(PinCount firstPinCount, PinCount secondPinCount) {

        if (firstPinCount.isStrike() && secondPinCount.isStrike()) {
            return STRIKE.mark;
        }

        if (!firstPinCount.isStrike() && firstPinCount.plus(secondPinCount) == PIN_COUNT_MAX) {
            return SPARE.mark;
        }

        if (secondPinCount.isGutter()) {
            return GUTTER.mark;
        }

        return String.valueOf(secondPinCount.value());
    }

    protected static String thirdMark(PinCount firstPinCount, PinCount secondPinCount, PinCount thirdPinCount) {

        if (thirdPinCount.isStrike()) {
            return STRIKE.mark;
        }

        if (isThirdSpare(firstPinCount, secondPinCount, thirdPinCount)) {
            return SPARE.mark;
        }

        if (thirdPinCount.isGutter()) {
            return GUTTER.mark;
        }

        return String.valueOf(thirdPinCount.value());
    }

    private static boolean isThirdSpare(PinCount firstPinCount, PinCount secondPinCount, PinCount thirdPinCount) {
        return firstPinCount.isStrike() && !secondPinCount.isStrike() && secondPinCount.plus(thirdPinCount) == PIN_COUNT_MAX;
    }
}