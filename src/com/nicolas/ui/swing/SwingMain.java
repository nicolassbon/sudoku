package com.nicolas.ui.swing;

import com.nicolas.app.BoardService;
import com.nicolas.app.GameConfigParser;
import com.nicolas.ui.swing.screen.MainScreen;

import javax.swing.SwingUtilities;
import java.util.Map;

public class SwingMain {

    public static void main(String[] args) {
        try {
            final Map<String, String> config = GameConfigParser.parse(args);
            final BoardService boardService = new BoardService(config);
            SwingUtilities.invokeLater(() -> new MainScreen(boardService).buildMainScreen());
        } catch (IllegalArgumentException exception) {
            System.err.println("No se pudo iniciar el juego. Configuración inválida: " + exception.getMessage());
        }
    }
}
