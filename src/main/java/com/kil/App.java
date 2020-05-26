package com.kil;

import com.google.common.eventbus.EventBus;
import com.kil.service.project.ProjectService;
import com.kil.service.project.ProjectServiceImpl;
import com.kil.view.DialogUtils;
import com.kil.view.controller.MainWindowController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;
import lombok.SneakyThrows;

public class App extends Application {

    @SneakyThrows
    @Override
    public void start(Stage stage) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/mainWindow.fxml"));
        Parent mainWindow = loader.load();
        stage.setScene(new Scene(mainWindow));
        stage.setTitle("Compiler");
        stage.setResizable(false);

        EventBus eventBus = new EventBus();
        ProjectService projectService = new ProjectServiceImpl(eventBus);

        DialogUtils.setPrimaryStage(stage);
        MainWindowController controller = loader.getController();
        controller.setProjectService(projectService);
        controller.setPrimaryStage(stage);
        eventBus.register(controller);

        Window window = stage.getScene().getWindow();
        window.addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, windowEvent -> projectService.checkToSave());

        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
