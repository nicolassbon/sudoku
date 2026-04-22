package com.nicolas.ui.swing.button;

import javax.swing.JButton;
import java.awt.event.ActionListener;

public class ResetButton extends JButton {

    public ResetButton(final ActionListener actionListener) {
        this.setText("Reiniciar juego");
        this.addActionListener(actionListener);
    }
}
