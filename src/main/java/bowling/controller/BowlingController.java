package bowling.controller;

import bowling.domain.NumberOfPlayer;
import bowling.domain.Player;
import bowling.domain.Players;
import bowling.view.InputView;
import bowling.view.ResultView;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BowlingController {
    private final InputView inputView;
    private final ResultView resultView;

    public BowlingController(InputView inputView, ResultView resultView) {
        this.inputView = inputView;
        this.resultView = resultView;
    }

    public void run() {
        NumberOfPlayer numberOfPlayer = new NumberOfPlayer(inputView.numberOfPlayer());
        Players players = createPlayers(numberOfPlayer);
        playGame(players);
        inputView.close();
    }

    private Players createPlayers(NumberOfPlayer numberOfPlayer) {
        return new Players(IntStream.range(0, numberOfPlayer.numberOfPlayer())
                .mapToObj(i -> new Player(inputView.playerName(i)))
                .collect(Collectors.toList()));
    }

    protected void playGame(Players players) {
        resultView.printResult(players.names(), players.marks(), players.scores());

        while (!players.isAllFinished()) {
            players.players()
                    .forEach(player -> eachFramePlay(player, players));
        }
    }

    private void eachFramePlay(Player player, Players players) {
        int currentFrameIndex = player.currentFrameIndex();

        while (!player.hasFinishedFrame(currentFrameIndex)) {
            player.throwBowl(inputView.pinCount(player.name()));
            resultView.printResult(players.names(), players.marks(), players.scores());
        }
    }
}
