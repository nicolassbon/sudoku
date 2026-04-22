package com.nicolas.ui.swing.input;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import java.util.List;

public class NumberTextLimit extends PlainDocument {

    private static final List<String> NUMBERS = List.of("1", "2", "3", "4", "5", "6", "7", "8", "9");

    @Override
    public void insertString(final int offset, final String value, final AttributeSet attributeSet) throws BadLocationException {
        if (value == null || !NUMBERS.contains(value)) {
            return;
        }

        if (getLength() + value.length() <= 1) {
            super.insertString(offset, value, attributeSet);
        }
    }
}
