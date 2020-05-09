package com.kil.view.controller;

import com.google.common.eventbus.Subscribe;
import com.kil.common.event.CodeChangedEvent;
import com.kil.service.ProjectService;
import com.kil.view.DialogUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import lombok.Setter;

import java.io.File;
import java.util.Optional;

public class MainWindowController {

    private static final String CODE_PLACE_HOLDER_TEXT = "Write your code here...";

    private static final int IMAGE_WIDTH = 60;
    private static final int IMAGE_HEIGHT = 60;
    private static final boolean IMAGE_PRESERVE_RATIO = true;
    private static final boolean IMAGE_SMOOTH = true;

    @Setter
    private ProjectService projectService;
    @Setter
    private Stage primaryStage;

    @FXML
    private Button buttonNew;
    @FXML
    private Button buttonOpen;
    @FXML
    private Button buttonSave;
    @FXML
    private Button buttonUndo;
    @FXML
    private Button buttonRedo;
    @FXML
    private Button buttonCut;
    @FXML
    private Button buttonCopy;
    @FXML
    private Button buttonPaste;

    @FXML
    private TextArea codeArea;
    @FXML
    private TextArea consoleArea;

    @FXML
    void compile(ActionEvent event) {

    }

    @FXML
    void copy(ActionEvent event) {
        codeArea.copy();
    }

    @FXML
    void newProject(ActionEvent event) {
        projectService.checkToSaveBeforeNewProject();
    }

    @FXML
    void cut(ActionEvent event) {
        codeArea.cut();
    }

    @FXML
    void delete(ActionEvent event) {
        codeArea.setText(codeArea.getText().replace(codeArea.getSelectedText(), ""));
    }

    @FXML
    void exit(ActionEvent event) {
        primaryStage.close();
    }

    @FXML
    void findCard(ActionEvent event) {

    }

    @FXML
    void openProject(ActionEvent event) {
        projectService.checkToSaveBeforeOpenProject();
    }

    @FXML
    void paste(ActionEvent event) {
        codeArea.paste();
    }

    @FXML
    void undo(ActionEvent event) {
        codeArea.undo();
    }

    @FXML
    void redo(ActionEvent event) {
        codeArea.redo();
    }

    @FXML
    void saveProject(ActionEvent event) {
        projectService.saveProject();
    }

    @FXML
    void saveProjectAs(ActionEvent event) {
        Optional<File> chosenDirectory = DialogUtils.chooseDirectory();
        chosenDirectory.ifPresent(directory -> {
            Optional<String> chosenName = DialogUtils.getProjectName();
            chosenName.ifPresent(name -> projectService.saveProjectAs(directory, name));
        });
    }

    @FXML
    void selectAll(ActionEvent event) {
        codeArea.selectAll();
    }

    @FXML
    void showAboutApp(ActionEvent event) {

    }

    @FXML
    void showAnalyzeMethod(ActionEvent event) {

    }

    @FXML
    void showErrorDiagnosticAndFix(ActionEvent event) {

    }

    @FXML
    void showExample(ActionEvent event) {

    }

    @FXML
    void showGrammar(ActionEvent event) {

    }

    @FXML
    void showGrammarClassification(ActionEvent event) {

    }

    @FXML
    void showLiteratureList(ActionEvent event) {

    }

    @FXML
    void showReference(ActionEvent event) {
        DialogUtils.showReferenceDialog("src/main/resources/text/reference.html");
    }

    @FXML
    void showSourceCode(ActionEvent event) {

    }

    @FXML
    void showTaskDescription(ActionEvent event) {

    }

    @FXML
    void initialize() {
        loadImages();

        codeArea.textProperty().addListener(
                (observable, oldValue, newValue) -> projectService.changeCode(newValue)
        );
    }

    private void loadImages() {
        setImage(buttonNew, "/image/png/edit-button-1.png");
        setImage(buttonOpen, "/image/png/store-files.png");
        setImage(buttonSave, "/image/png/save-files.png");
        setImage(buttonUndo, "/image/png/undo-button.png");
        setImage(buttonRedo, "/image/png/redo-button.png");
        setImage(buttonCut, "/image/png/cutting-button.png");
        setImage(buttonCopy, "/image/png/copy-text.png");
        setImage(buttonPaste, "/image/png/paste-text.png");

        codeArea.setPromptText(CODE_PLACE_HOLDER_TEXT);
    }

    private void setImage(Button button, String imagePath) {
        button.setGraphic(new ImageView(new Image(
                getClass().getResourceAsStream(imagePath),
                IMAGE_WIDTH, IMAGE_HEIGHT, IMAGE_PRESERVE_RATIO, IMAGE_SMOOTH)));
    }

    @Subscribe
    public void onCodeChanged(CodeChangedEvent event) {
        codeArea.setText(event.getNewCode());
    }

}
