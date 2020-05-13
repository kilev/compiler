package com.kil.view.controller;

import com.google.common.eventbus.Subscribe;
import com.kil.common.event.CodeChangedEvent;
import com.kil.service.project.ProjectService;
import com.kil.service.search.SearchService;
import com.kil.service.search.card.CardSearchResult;
import com.kil.view.DialogUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;
import lombok.Setter;
import lombok.SneakyThrows;

import java.io.File;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class MainWindowController {

    private static final String CODE_PLACE_HOLDER_TEXT = "Write your code here...";

    private static final int IMAGE_WIDTH = 60;
    private static final int IMAGE_HEIGHT = 60;
    private static final boolean IMAGE_PRESERVE_RATIO = true;
    private static final boolean IMAGE_SMOOTH = true;

    @Setter
    private ProjectService projectService;
    @Setter
    private SearchService searchService;
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
        Window window = primaryStage.getScene().getWindow();
        window.fireEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSE_REQUEST));
    }

    @FXML
    void findCard(ActionEvent event) {
        List<CardSearchResult> searchResult = searchService.searchCardsByRegex(codeArea.getText());
        consoleArea.setText(
                searchResult.stream()
                        .map(CardSearchResult::toTextView)
                        .collect(Collectors.joining(System.lineSeparator()))
        );
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
        DialogUtils.showReferenceDialog(getClass().getResourceAsStream("/text/reference.html"));
    }

    @FXML
    @SneakyThrows
    void showSourceCode(ActionEvent event) {
        java.awt.Desktop.getDesktop().browse(new URI("https://github.com/kilev/compiler"));
    }

    @FXML
    void showTaskDescription(ActionEvent event) {

    }

    @FXML
    void initialize() {
        loadImages();

        codeArea.textProperty().addListener(
                (observable, oldValue, newValue) -> {
                    projectService.changeCode(newValue);
                    findCard(null);
                }
        );
    }

    private void loadImages() {
        setImage(buttonNew, "/image/edit-button.png");
        setImage(buttonOpen, "/image/store-files.png");
        setImage(buttonSave, "/image/save-files.png");
        setImage(buttonUndo, "/image/undo-button.png");
        setImage(buttonRedo, "/image/redo-button.png");
        setImage(buttonCut, "/image/cutting-button.png");
        setImage(buttonCopy, "/image/copy-text.png");
        setImage(buttonPaste, "/image/paste-text.png");

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
