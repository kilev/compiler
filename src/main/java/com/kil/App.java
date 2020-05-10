package com.kil;

import com.google.common.eventbus.EventBus;
import com.kil.service.project.ProjectService;
import com.kil.service.project.ProjectServiceImpl;
import com.kil.service.search.SearchServiceImpl;
import com.kil.view.DialogUtils;
import com.kil.view.controller.MainWindowController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
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
        controller.setSearchService(new SearchServiceImpl());
        controller.setPrimaryStage(stage);
        eventBus.register(controller);

        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
