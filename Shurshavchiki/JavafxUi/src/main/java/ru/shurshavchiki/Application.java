package ru.shurshavchiki;

import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import ru.shurshavchiki.businessLogic.services.FileService;
import ru.shurshavchiki.controllers.ErrorController;
import ru.shurshavchiki.exceptionHandling.GuiExceptionHandler;
import ru.shurshavchiki.util.ManualControllerMediator;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

public class Application extends javafx.application.Application {
    @Override
    public void start(Stage stage) {
        try {
            Thread.setDefaultUncaughtExceptionHandler(Application::showError);
            ManualControllerMediator.getInstance().setFileService(new FileService());

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("MainPage.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Shurshavchiki");
            stage.show();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        launch();
    }

    private static void showError(Thread t, Throwable e) {
        System.err.println("***Default exception handler***");
        if (Platform.isFxApplicationThread()) {
            showErrorDialog(e);
        } else {
            System.err.println("An unexpected error occurred in "+t);

        }
    }

    private static void showErrorDialog(Throwable e) {
        System.out.println("Show error dialog");
        StringWriter errorMsg = new StringWriter();
        e.printStackTrace(new PrintWriter(errorMsg));
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        FXMLLoader loader = new FXMLLoader(Application.class.getClassLoader().getResource("Error.fxml"));
        try {
            System.out.println("aa");
            Parent tmp = loader.load();
            System.out.println("aaaaa");
            ((ErrorController)loader.getController()).setErrorText(getInitialException(e).getMessage());
            dialog.setScene(new Scene(tmp, 250, 400));

            System.out.println("aboba");
            dialog.show();
        } catch (IOException exc) {
            System.err.println("exception");;
            exc.printStackTrace();
        }
    }

    private static Throwable getInitialException(Throwable throwable) {
        while (throwable.getCause() != null) {
            throwable = throwable.getCause();
        }

        return throwable;
    }
}
