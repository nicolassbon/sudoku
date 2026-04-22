package com.nicolas.ui.swing.input;

import com.nicolas.app.BoardService;
import com.nicolas.domain.Space;
import com.nicolas.ui.swing.events.EventListener;
import com.nicolas.ui.swing.events.EventType;

import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.Dimension;
import java.awt.Font;

import static com.nicolas.ui.swing.events.EventType.CLEAR_SPACE;
import static java.awt.Font.PLAIN;

public class NumberText extends JTextField implements EventListener {

    private final BoardService boardService;
    private final int col;
    private final int row;
    private final boolean fixed;

    private boolean syncing;

    public NumberText(final Space space, final BoardService boardService, final int col, final int row) {
        this.boardService = boardService;
        this.col = col;
        this.row = row;
        this.fixed = space.isFixed();
        this.syncing = false;

        final Dimension dimension = new Dimension(50, 50);
        this.setSize(dimension);
        this.setPreferredSize(dimension);
        this.setVisible(true);
        this.setFont(new Font("Arial", PLAIN, 20));
        this.setHorizontalAlignment(CENTER);
        this.setDocument(new NumberTextLimit());
        this.setEnabled(!fixed);

        final Integer initialValue = space.getActual();
        if (initialValue != null) {
            this.setText(initialValue.toString());
        }

        this.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(final DocumentEvent event) {
                commitEditedValue();
            }

            @Override
            public void removeUpdate(final DocumentEvent event) {
                commitEditedValue();
            }

            @Override
            public void changedUpdate(final DocumentEvent event) {
                commitEditedValue();
            }
        });
    }

    @Override
    public void update(final EventType eventType) {
        if (eventType.equals(CLEAR_SPACE) && this.isEnabled()) {
            setTextWithoutLoop("");
        }
    }

    private void commitEditedValue() {
        if (syncing) {
            return;
        }

        if (fixed) {
            syncFromService();
            return;
        }

        final String text = getText().trim();
        final boolean changed;
        if (text.isEmpty()) {
            changed = boardService.clearValue(col, row);
        } else {
            changed = boardService.changeValue(col, row, Integer.parseInt(text));
        }

        if (!changed) {
            JOptionPane.showMessageDialog(null, "Acción inválida para esta celda.");
            syncFromService();
        }
    }

    private void syncFromService() {
        final Integer actual = boardService.getSpaces().get(col).get(row).getActual();
        setTextWithoutLoop(actual == null ? "" : actual.toString());
    }

    private void setTextWithoutLoop(final String value) {
        syncing = true;
        try {
            setText(value);
        } finally {
            syncing = false;
        }
    }
}
