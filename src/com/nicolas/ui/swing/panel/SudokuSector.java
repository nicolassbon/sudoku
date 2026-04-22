package com.nicolas.ui.swing.panel;

import com.nicolas.ui.swing.input.NumberText;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import java.awt.Dimension;
import java.util.List;

import static java.awt.Color.BLACK;

public class SudokuSector extends JPanel {

    public SudokuSector(final List<NumberText> textFields) {
        final Dimension dimension = new Dimension(170, 170);
        this.setSize(dimension);
        this.setPreferredSize(dimension);
        this.setBorder(new LineBorder(BLACK, 2, true));
        this.setVisible(true);
        textFields.forEach(this::add);
    }
}
