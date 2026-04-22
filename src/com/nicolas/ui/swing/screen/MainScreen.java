package com.nicolas.ui.swing.screen;

import com.nicolas.app.BoardService;
import com.nicolas.domain.Space;
import com.nicolas.ui.swing.button.CheckGameStatusButton;
import com.nicolas.ui.swing.button.FinishGameButton;
import com.nicolas.ui.swing.button.ResetButton;
import com.nicolas.ui.swing.events.NotifierService;
import com.nicolas.ui.swing.frame.MainFrame;
import com.nicolas.ui.swing.input.NumberText;
import com.nicolas.ui.swing.panel.MainPanel;
import com.nicolas.ui.swing.panel.SudokuSector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import static com.nicolas.ui.swing.events.EventType.CLEAR_SPACE;
import static javax.swing.JOptionPane.QUESTION_MESSAGE;
import static javax.swing.JOptionPane.YES_NO_OPTION;
import static javax.swing.JOptionPane.showConfirmDialog;
import static javax.swing.JOptionPane.showMessageDialog;

public class MainScreen {

    private static final Dimension DIMENSION = new Dimension(600, 600);

    private final BoardService boardService;
    private final NotifierService notifierService;

    private JButton checkGameStatusButton;
    private JButton finishGameButton;
    private JButton resetButton;

    public MainScreen(final BoardService boardService) {
        this.boardService = boardService;
        this.notifierService = new NotifierService();
    }

    public void buildMainScreen() {
        final JPanel mainPanel = new MainPanel(DIMENSION);
        final JFrame mainFrame = new MainFrame(DIMENSION, mainPanel);

        for (int row = 0; row < 9; row += 3) {
            final int endRow = row + 2;
            for (int col = 0; col < 9; col += 3) {
                final int endCol = col + 2;
                final JPanel sector = generateSection(boardService.getSpaces(), col, endCol, row, endRow);
                mainPanel.add(sector);
            }
        }

        addResetButton(mainPanel);
        addCheckGameStatusButton(mainPanel);
        addFinishGameButton(mainPanel);

        mainFrame.revalidate();
        mainFrame.repaint();
    }

    private JPanel generateSection(
            final List<List<Space>> spaces,
            final int initCol,
            final int endCol,
            final int initRow,
            final int endRow
    ) {
        final List<NumberText> fields = new ArrayList<>();
        for (int row = initRow; row <= endRow; row++) {
            for (int col = initCol; col <= endCol; col++) {
                final Space space = spaces.get(col).get(row);
                final NumberText field = new NumberText(space, boardService, col, row);
                notifierService.subscribe(CLEAR_SPACE, field);
                fields.add(field);
            }
        }

        return new SudokuSector(fields);
    }

    private void addFinishGameButton(final JPanel mainPanel) {
        finishGameButton = new FinishGameButton(event -> {
            if (boardService.gameIsFinished()) {
                showMessageDialog(null, "¡Felicitaciones! Completaste el juego.");
                resetButton.setEnabled(false);
                checkGameStatusButton.setEnabled(false);
                finishGameButton.setEnabled(false);
                return;
            }

            final String message = boardService.hasErrors()
                    ? "El tablero tiene errores. Ajustalo e intentá de nuevo."
                    : "El tablero todavía está incompleto.";
            showMessageDialog(null, message);
        });
        mainPanel.add(finishGameButton);
    }

    private void addCheckGameStatusButton(final JPanel mainPanel) {
        checkGameStatusButton = new CheckGameStatusButton(event -> {
            final boolean hasErrors = boardService.hasErrors();
            final String message = switch (boardService.getStatus()) {
                case NON_STARTED -> "El juego no fue iniciado";
                case INCOMPLETE -> "El juego está incompleto";
                case COMPLETE -> "El juego está completo";
            };

            showMessageDialog(null, message + (hasErrors ? " y contiene errores" : " y no contiene errores"));
        });
        mainPanel.add(checkGameStatusButton);
    }

    private void addResetButton(final JPanel mainPanel) {
        resetButton = new ResetButton(event -> {
            final int dialogResult = showConfirmDialog(
                    null,
                    "¿Seguro que querés reiniciar el juego?",
                    "Reiniciar juego",
                    YES_NO_OPTION,
                    QUESTION_MESSAGE
            );

            if (dialogResult == JOptionPane.YES_OPTION) {
                boardService.reset();
                notifierService.notify(CLEAR_SPACE);
            }
        });
        mainPanel.add(resetButton);
    }
}
