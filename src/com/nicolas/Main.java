package com.nicolas;

import com.nicolas.app.BoardService;
import com.nicolas.app.GameConfigParser;
import com.nicolas.ui.terminal.TerminalGame;

import java.util.Map;

public class Main {

    public static void main(String[] args) {
        try {
            final Map<String, String> config = GameConfigParser.parse(args);
            final BoardService boardService = new BoardService(config);
            final TerminalGame terminalGame = new TerminalGame(boardService);
            terminalGame.run();
        } catch (IllegalArgumentException exception) {
            System.err.println("No se pudo iniciar el juego. Configuración inválida: " + exception.getMessage());
        }
    }
}
