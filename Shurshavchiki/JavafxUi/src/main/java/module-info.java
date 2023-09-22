module ru.shurshvchiki.JavafxUi {
    requires javafx.controls;
    requires javafx.fxml;
    requires Shurshavchiki.BusinessLogic;
    exports ru.shurshavchiki;
    exports ru.shurshavchiki.controllers;
    opens ru.shurshavchiki.controllers to javafx.fxml;
}