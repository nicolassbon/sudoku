package com.nicolas;

import com.nicolas.app.BoardService;
import com.nicolas.app.GameConfigParser;
import com.nicolas.ui.swing.screen.MainScreen;
import com.nicolas.ui.terminal.TerminalGame;

import javax.swing.SwingUtilities;
import java.awt.GraphicsEnvironment;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        try {
            final Map<String, String> config = GameConfigParser.parse(args);
            final BoardService boardService = new BoardService(config);

            if (GraphicsEnvironment.isHeadless()) {
                System.out.println("Entorno gráfico no disponible. Iniciando en modo terminal...");
                new TerminalGame(boardService).run();
            } else {
                try {
                    SwingUtilities.invokeLater(() -> new MainScreen(boardService).buildMainScreen());
                } catch (Exception e) {
                    System.err.println("Error al iniciar la UI gráfica. Cayendo en fallback a modo terminal...");
                    new TerminalGame(boardService).run();
                }
            }
        } catch (IllegalArgumentException exception) {
            System.err.println("No se pudo iniciar el juego. Configuración inválida: " + exception.getMessage());
        }
    }
}
