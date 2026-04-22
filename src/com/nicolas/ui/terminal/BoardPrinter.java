package com.nicolas.ui.terminal;

import com.nicolas.domain.Space;

import java.util.List;

public final class BoardPrinter {

    private static final int BOARD_LIMIT = 9;

    public String render(final List<List<Space>> spaces) {
        final StringBuilder builder = new StringBuilder();
        builder.append("    0 1 2   3 4 5   6 7 8\n");

        for (int row = 0; row < BOARD_LIMIT; row++) {
            if (row > 0 && row % 3 == 0) {
                builder.append("    ------+------+------\n");
            }

            builder.append(row).append(" | ");
            for (int col = 0; col < BOARD_LIMIT; col++) {
                if (col > 0 && col % 3 == 0) {
                    builder.append("| ");
                }

                final Integer value = spaces.get(col).get(row).getActual();
                builder.append(value == null ? "." : value).append(' ');
            }
            builder.append('\n');
        }

        return builder.toString();
    }
}
