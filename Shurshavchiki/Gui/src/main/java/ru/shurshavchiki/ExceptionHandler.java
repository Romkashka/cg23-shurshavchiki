package ru.shurshavchiki;

import ru.shurshavchiki.businessLogic.exceptions.GeneralPhotoshopException;
import ru.shurshavchiki.businessLogic.exceptions.OpenFileException;
import ru.shurshavchiki.businessLogic.exceptions.WriteFileException;

import javax.swing.*;
import java.io.IOException;
import java.util.Arrays;

public class ExceptionHandler {

    public void handleException(Throwable exp) {
        if (exp instanceof NullPointerException) {
            JOptionPane.showMessageDialog(null,
                    "File Missing" + Arrays.toString(exp.getStackTrace()), "Error 404",
                    JOptionPane.ERROR_MESSAGE);
        } else if (exp instanceof WriteFileException) {
            javax.swing.JOptionPane.showMessageDialog(null,
                    exp.getMessage(), "Write error",
                    JOptionPane.ERROR_MESSAGE);
        } else if (exp instanceof OpenFileException) {
            javax.swing.JOptionPane.showMessageDialog(null,
                    exp.getMessage(), "Open error",
                    JOptionPane.ERROR_MESSAGE);
        } else if (exp instanceof IOException) {
            javax.swing.JOptionPane.showMessageDialog(null,
                    exp.getMessage(), "Input or output error",
                    JOptionPane.ERROR_MESSAGE);
        } else if (exp instanceof OutOfMemoryError) {
            javax.swing.JOptionPane.showMessageDialog(null,
                    exp.getMessage(), "Out of RAM",
                    JOptionPane.ERROR_MESSAGE);
        } else if (exp instanceof IllegalAccessException) {
            javax.swing.JOptionPane.showMessageDialog(null,
                    exp.getMessage(), "Illegal access",
                    JOptionPane.ERROR_MESSAGE);
        } else if (exp instanceof ClassNotFoundException) {
            javax.swing.JOptionPane.showMessageDialog(null,
                    exp.getMessage(), "Class not found",
                    JOptionPane.ERROR_MESSAGE);
        } else if (exp instanceof UnsupportedLookAndFeelException) {
            javax.swing.JOptionPane.showMessageDialog(null,
                    exp.getMessage(), "Unsupported view type",
                    JOptionPane.ERROR_MESSAGE);
        } else if (exp != null) {
            javax.swing.JOptionPane.showMessageDialog(null,
                    exp.getClass() + "\nMessage: " + exp.getMessage(), "Unexpected exception",
                    JOptionPane.ERROR_MESSAGE);
            exp.printStackTrace();
        }
    }
}
