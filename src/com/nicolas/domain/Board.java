package com.nicolas.domain;

import java.util.Collection;
import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class Board {

    private final List<List<Space>> spaces;

    public Board(final List<List<Space>> spaces) {
        this.spaces = spaces;
    }

    public List<List<Space>> getSpaces() {
        return spaces;
    }

    public GameStatus getStatus() {
        if (spaces.stream().flatMap(Collection::stream)
                .noneMatch(space -> !space.isFixed() && nonNull(space.getActual()))) {
            return GameStatus.NON_STARTED;
        }

        return spaces.stream().flatMap(Collection::stream)
                .anyMatch(space -> isNull(space.getActual()))
                ? GameStatus.INCOMPLETE
                : GameStatus.COMPLETE;
    }

    public boolean hasErrors() {
        if (getStatus() == GameStatus.NON_STARTED) {
            return false;
        }

        return spaces.stream().flatMap(Collection::stream)
                .anyMatch(space -> nonNull(space.getActual()) && !space.getActual().equals(space.getExpected()));
    }

    public boolean changeValue(final int col, final int row, final int value) {
        final Space space = spaces.get(col).get(row);
        if (space.isFixed()) {
            return false;
        }

        space.setActual(value);
        return true;
    }

    public boolean clearValue(final int col, final int row) {
        final Space space = spaces.get(col).get(row);
        if (space.isFixed()) {
            return false;
        }

        space.clearSpace();
        return true;
    }

    public void reset() {
        spaces.forEach(column -> column.forEach(Space::clearSpace));
    }

    public boolean gameIsFinished() {
        return !hasErrors() && getStatus() == GameStatus.COMPLETE;
    }
}
