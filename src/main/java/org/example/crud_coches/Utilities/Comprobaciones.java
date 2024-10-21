package org.example.crud_coches.Utilities;

import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import javax.swing.*;

public class Comprobaciones {
    public static boolean textosVacios(ComboBox<?> combobox, TextField... textFields) {
        for (TextField textFieldActual : textFields) {
            if (textFieldActual.getText().trim().isEmpty()) {
                return false;
            }
            if (combobox.getSelectionModel().getSelectedItem() == null) {
                return false;
            }
        }
        return true;
    }
}
