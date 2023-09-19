module com.mechanitis.demo.javafxgradle {
    requires javafx.controls;
    requires javafx.fxml;
    exports ru.shurshavchiki;
    exports ru.shurshavchiki.controllers;
    opens ru.shurshavchiki.controllers to javafx.fxml;
}