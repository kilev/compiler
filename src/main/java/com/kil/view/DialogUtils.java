package com.kil.view;

import com.kil.common.command.Command;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.web.WebView;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lombok.Setter;
import lombok.experimental.UtilityClass;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Optional;

@UtilityClass
public class DialogUtils {

    @Setter
    private Stage primaryStage;

    public void askUserForSaveProject(Command afterYesCommand, Command afterNoCommand) {
        DialogUtils.showConfirmationDialog(
                "Сохранение...",
                "Сохранить текущий проект?",
                "У вас есть несохраненные изменение",
                afterYesCommand,
                afterNoCommand);
    }

    public Optional<String> getProjectName() {
        return DialogUtils.showInputDialog(
                "Проект...",
                "Введите имя проекта",
                "Для сохранения проекта необходимо чтобы у него было имя.");
    }

    public void showReferenceDialog(String contentPath) {
        showWebViewDialog("Справка", null, contentPath);
    }

    public void showConfirmationDialog(String title, String header, String text, Command afterYesCommand, Command afterNoCommand) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(text);

        ButtonType typeYes = new ButtonType("Да");
        ButtonType typeNo = new ButtonType("Нет");

        alert.getButtonTypes().setAll(typeYes, typeNo);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == typeYes) {
            afterYesCommand.execute();
        } else if (result.get() == typeNo) {
            afterNoCommand.execute();
        }
    }

    public Optional<String> showInputDialog(String title, String header, String text) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle(title);
        dialog.setHeaderText(header);
        dialog.setContentText(text);

        return dialog.showAndWait();
    }

    public void showWebViewDialog(String title, String text, String contentPath) {
        try {
            Dialog dialog = new Dialog();
            dialog.setTitle(title);
            dialog.setContentText(text);
            dialog.getDialogPane().setPrefWidth(Region.USE_COMPUTED_SIZE);
            dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);

            WebView webView = new WebView();
            dialog.getDialogPane().setContent(webView);

            webView.getEngine().load(DialogUtils.class.getResource(contentPath).toExternalForm());
            dialog.showAndWait();
        } catch (Exception e) {
            DialogUtils.showException(e);
        }
    }

    public void showException(Exception e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Exception Dialog");
        alert.setHeaderText(e.toString());

        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        e.printStackTrace(printWriter);
        String exceptionText = stringWriter.toString();

        Label label = new Label("The exception stacktrace was:");

        TextArea textArea = new TextArea(exceptionText);
        textArea.setEditable(false);
        textArea.setWrapText(true);

        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);

        GridPane expContent = new GridPane();
        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.add(label, 0, 0);
        expContent.add(textArea, 0, 1);

        alert.getDialogPane().setExpandableContent(expContent);
        alert.showAndWait();
    }

    public Optional<File> chooseDirectory() {
        File file = new DirectoryChooser().showDialog(primaryStage);
        return Optional.ofNullable(file);
    }

    public Optional<File> chooseFile() {
        File file = new FileChooser().showOpenDialog(primaryStage);
        return Optional.ofNullable(file);
    }

    public void showInfoDialog(String title, String header, String text) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(text);

        alert.showAndWait();
    }

    public String getTextFromInputStream(InputStream inputStream) {
        try {
            StringWriter writer = new StringWriter();
            IOUtils.copy(inputStream, writer, "UTF-8");
            return writer.toString();
        } catch (Exception e) {
            DialogUtils.showException(e);
            return null;
        }
    }
}
