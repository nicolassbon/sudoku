package com.nicolas.ui.terminal;

import com.nicolas.app.BoardService;

import java.util.Locale;
import java.util.Scanner;

public final class TerminalGame {

    private final BoardService boardService;
    private final BoardPrinter boardPrinter;
    private final Scanner scanner;

    private boolean started;
    private boolean running;

    public TerminalGame(final BoardService boardService) {
        this.boardService = boardService;
        this.boardPrinter = new BoardPrinter();
        this.scanner = new Scanner(System.in);
        this.started = false;
        this.running = true;
    }

    public void run() {
        while (running) {
            showMenu();
            final String command = readCommand();

            switch (command) {
                case "1", "start" -> startGame();
                case "2", "input" -> inputNumber();
                case "3", "remove" -> removeNumber();
                case "4", "show" -> showCurrentGame();
                case "5", "status" -> showGameStatus();
                case "6", "reset" -> clearGame();
                case "7", "finish" -> finishGame();
                case "8", "exit" -> exit();
                default -> System.out.println("Comando inválido. Usá 1-8 o start/input/remove/show/status/reset/finish/exit.");
            }
        }
    }

    private void showMenu() {
        System.out.println("Seleccioná una opción:");
        System.out.println("1 - Iniciar juego (start)");
        System.out.println("2 - Ingresar número (input)");
        System.out.println("3 - Eliminar número (remove)");
        System.out.println("4 - Mostrar tablero (show)");
        System.out.println("5 - Ver estado (status)");
        System.out.println("6 - Reiniciar progreso (reset)");
        System.out.println("7 - Finalizar juego (finish)");
        System.out.println("8 - Salir (exit)");
    }

    private String readCommand() {
        return scanner.nextLine().trim().toLowerCase(Locale.ROOT);
    }

    private void startGame() {
        if (started) {
            System.out.println("El juego ya está iniciado.");
            return;
        }

        started = true;
        System.out.println("Juego iniciado. ¡A jugar!");
    }

    private void inputNumber() {
        if (!validateStarted()) {
            return;
        }

        final int col = readNumber("Ingresá la columna (0-8):", 0, 8);
        final int row = readNumber("Ingresá la fila (0-8):", 0, 8);
        final int value = readNumber("Ingresá el valor (1-9):", 1, 9);

        if (!boardService.changeValue(col, row, value)) {
            System.out.printf("La posición [%d,%d] es fija y no se puede modificar.%n", col, row);
            return;
        }

        System.out.printf("Valor %d cargado en [%d,%d].%n", value, col, row);
    }

    private void removeNumber() {
        if (!validateStarted()) {
            return;
        }

        final int col = readNumber("Ingresá la columna (0-8):", 0, 8);
        final int row = readNumber("Ingresá la fila (0-8):", 0, 8);

        if (!boardService.clearValue(col, row)) {
            System.out.printf("La posición [%d,%d] es fija y no se puede limpiar.%n", col, row);
            return;
        }

        System.out.printf("Posición [%d,%d] limpiada.%n", col, row);
    }

    private void showCurrentGame() {
        if (!validateStarted()) {
            return;
        }

        System.out.println("Estado actual del tablero:");
        System.out.print(boardPrinter.render(boardService.getSpaces()));
    }

    private void showGameStatus() {
        if (!validateStarted()) {
            return;
        }

        System.out.printf("Estado del juego: %s%n", boardService.getStatus());
        if (boardService.hasErrors()) {
            System.out.println("El tablero tiene errores.");
        } else {
            System.out.println("El tablero no tiene errores.");
        }
    }

    private void clearGame() {
        if (!validateStarted()) {
            return;
        }

        System.out.println("¿Seguro que querés reiniciar el juego? (si/no)");
        while (true) {
            final String answer = readCommand();
            if ("si".equals(answer)) {
                boardService.reset();
                System.out.println("Juego reiniciado.");
                return;
            }

            if ("no".equals(answer)) {
                System.out.println("Reinicio cancelado.");
                return;
            }

            System.out.println("Respuesta inválida. Escribí 'si' o 'no'.");
        }
    }

    private void finishGame() {
        if (!validateStarted()) {
            return;
        }

        if (boardService.gameIsFinished()) {
            System.out.println("¡Felicitaciones! Completaste el juego.");
            System.out.print(boardPrinter.render(boardService.getSpaces()));
            started = false;
            return;
        }

        if (boardService.hasErrors()) {
            System.out.println("El tablero tiene errores. Corregilos antes de finalizar.");
        } else {
            System.out.println("Todavía faltan celdas por completar.");
        }
    }

    private void exit() {
        running = false;
        System.out.println("Saliendo del juego.");
    }

    private boolean validateStarted() {
        if (started) {
            return true;
        }

        System.out.println("El juego no está iniciado. Usá 'start' o '1'.");
        return false;
    }

    private int readNumber(final String prompt, final int min, final int max) {
        while (true) {
            System.out.println(prompt);
            final String value = scanner.nextLine().trim();
            try {
                final int parsed = Integer.parseInt(value);
                if (parsed < min || parsed > max) {
                    System.out.printf("Número fuera de rango. Ingresá un valor entre %d y %d.%n", min, max);
                    continue;
                }
                return parsed;
            } catch (NumberFormatException exception) {
                System.out.printf("Entrada inválida. Ingresá un número entre %d y %d.%n", min, max);
            }
        }
    }
}
