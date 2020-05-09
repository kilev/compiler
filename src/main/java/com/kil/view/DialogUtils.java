package com.kil.view;

import com.kil.common.command.Command;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextInputDialog;
import javafx.scene.web.WebView;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import java.io.File;
import java.net.URL;
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

    @SneakyThrows
    public void showReferenceDialog(String htmlPath) {
        File file = new File(htmlPath);
        URL url = file.toURI().toURL();
        showWebViewDialog("Справка", null, url.toString());
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

    public void showInfoDialog(String title, String header, String text) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(text);

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

    public void showWebViewDialog(String title, String text, String url) {
        Dialog dialog = new Dialog();
        dialog.setTitle(title);
        dialog.setContentText(text);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);

        WebView webView = new WebView();
        dialog.getDialogPane().setContent(webView);
        webView.getEngine().load(url);
        dialog.showAndWait();
    }

}
