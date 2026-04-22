package com.nicolas.ui.swing.button;

import javax.swing.JButton;
import java.awt.event.ActionListener;

public class CheckGameStatusButton extends JButton {

    public CheckGameStatusButton(final ActionListener actionListener) {
        this.setText("Verificar juego");
        this.addActionListener(actionListener);
    }
}
