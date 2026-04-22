package com.nicolas.app;

import com.nicolas.domain.Board;
import com.nicolas.domain.GameStatus;
import com.nicolas.domain.Space;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class BoardService {

    private static final int BOARD_LIMIT = 9;

    private final Board board;

    public BoardService(final Map<String, String> config) {
        this.board = new Board(buildSpaces(config));
    }

    public List<List<Space>> getSpaces() {
        return board.getSpaces();
    }

    public boolean changeValue(final int col, final int row, final int value) {
        return board.changeValue(col, row, value);
    }

    public boolean clearValue(final int col, final int row) {
        return board.clearValue(col, row);
    }

    public void reset() {
        board.reset();
    }

    public GameStatus getStatus() {
        return board.getStatus();
    }

    public boolean hasErrors() {
        return board.hasErrors();
    }

    public boolean gameIsFinished() {
        return board.gameIsFinished();
    }

    private List<List<Space>> buildSpaces(final Map<String, String> config) {
        final List<List<Space>> spaces = new ArrayList<>();
        for (int col = 0; col < BOARD_LIMIT; col++) {
            spaces.add(new ArrayList<>());
            for (int row = 0; row < BOARD_LIMIT; row++) {
                final String position = "%s,%s".formatted(col, row);
                final String parsedValue = config.get(position);
                if (parsedValue == null) {
                    throw new IllegalArgumentException("Missing config for position: " + position);
                }

                final String[] valueParts = parsedValue.split(",");
                if (valueParts.length != 2) {
                    throw new IllegalArgumentException("Malformed position config: " + position + "=" + parsedValue);
                }

                final int expected = Integer.parseInt(valueParts[0]);
                final boolean fixed = Boolean.parseBoolean(valueParts[1]);
                spaces.get(col).add(new Space(expected, fixed));
            }
        }
        return spaces;
    }
}
