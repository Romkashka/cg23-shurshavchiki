package ru.shurshavchiki;

import ru.shurshavchiki.businessLogic.exceptions.GeneralPhotoshopException;
import ru.shurshavchiki.businessLogic.exceptions.OpenFileException;
import ru.shurshavchiki.businessLogic.exceptions.WriteFileException;

import javax.swing.*;
import java.io.IOException;

public class ExceptionHandler {

    public void handleException(Exception exp) {
        if (exp instanceof NullPointerException) {
            JOptionPane.showMessageDialog(null,
                    "File Missing", "File Missing",
                    JOptionPane.ERROR_MESSAGE);
        } else if (exp instanceof WriteFileException) {
            javax.swing.JOptionPane.showMessageDialog(null,
                    "Test1", "Test2",
                    JOptionPane.ERROR_MESSAGE);
        } else if (exp instanceof OpenFileException) {
            javax.swing.JOptionPane.showMessageDialog(null,
                    "Test1", "Test2",
                    JOptionPane.ERROR_MESSAGE);
        } else if (exp instanceof IOException) {
            javax.swing.JOptionPane.showMessageDialog(null,
                    "Test1", "Test2",
                    JOptionPane.ERROR_MESSAGE);
        } else if (exp != null) {
            javax.swing.JOptionPane.showMessageDialog(null,
                    exp.getClass(), "Unexpected exception",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
