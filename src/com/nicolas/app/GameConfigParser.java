package com.nicolas.app;

import java.util.HashMap;
import java.util.Map;

public final class GameConfigParser {

    private static final int BOARD_LIMIT = 9;
    private static final int BOARD_SIZE = BOARD_LIMIT * BOARD_LIMIT;

    private GameConfigParser() {
    }

    public static Map<String, String> parse(final String[] args) {
        if (args == null || args.length != BOARD_SIZE) {
            throw new IllegalArgumentException("Expected exactly " + BOARD_SIZE + " entries in format row,col;expected,fixed");
        }

        final Map<String, String> config = new HashMap<>();
        for (String rawEntry : args) {
            final String entry = requireNotBlank(rawEntry);
            final String[] entryParts = entry.split(";");
            if (entryParts.length != 2) {
                throw invalidEntry(entry);
            }

            final String[] position = entryParts[0].split(",");
            final String[] values = entryParts[1].split(",");
            if (position.length != 2 || values.length != 2) {
                throw invalidEntry(entry);
            }

            final int row = parseInt(position[0], entry);
            final int col = parseInt(position[1], entry);
            final int expected = parseInt(values[0], entry);
            final boolean fixed = parseBoolean(values[1], entry);

            validateRange(row, 0, BOARD_LIMIT - 1, "row", entry);
            validateRange(col, 0, BOARD_LIMIT - 1, "col", entry);
            validateRange(expected, 1, BOARD_LIMIT, "expected", entry);

            final String key = "%s,%s".formatted(row, col);
            final String value = "%s,%s".formatted(expected, fixed);
            if (config.putIfAbsent(key, value) != null) {
                throw new IllegalArgumentException("Duplicate position in config: " + key);
            }
        }

        if (config.size() != BOARD_SIZE) {
            throw new IllegalArgumentException("Incomplete board config: expected " + BOARD_SIZE + " unique positions");
        }
        return config;
    }

    private static IllegalArgumentException invalidEntry(final String entry) {
        return new IllegalArgumentException("Malformed config entry: " + entry + ". Expected row,col;expected,fixed");
    }

    private static String requireNotBlank(final String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Malformed config entry: value is blank");
        }
        return value.trim();
    }

    private static int parseInt(final String rawValue, final String entry) {
        try {
            return Integer.parseInt(rawValue.trim());
        } catch (NumberFormatException exception) {
            throw invalidEntry(entry);
        }
    }

    private static boolean parseBoolean(final String rawValue, final String entry) {
        final String normalized = rawValue.trim().toLowerCase();
        if (!normalized.equals("true") && !normalized.equals("false")) {
            throw invalidEntry(entry);
        }
        return Boolean.parseBoolean(normalized);
    }

    private static void validateRange(final int value, final int min, final int max, final String field, final String entry) {
        if (value < min || value > max) {
            throw new IllegalArgumentException("Invalid " + field + " in entry: " + entry);
        }
    }
}
