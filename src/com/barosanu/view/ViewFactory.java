package com.barosanu.view;

import com.barosanu.EmailManager;
import com.barosanu.controller.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class ViewFactory {

    private EmailManager emailManager;
    //View options handling
    private ArrayList<Stage> activeStages; // Keeps record of active stages which would help in updating css
    private boolean mainViewIntialized = false;


    public ViewFactory(EmailManager emailManager){
        this.emailManager = emailManager;
        activeStages = new ArrayList<>();
    }

    public boolean isMainViewIntialized() {
        return mainViewIntialized;
    }

    private ColorTheme colorTheme = ColorTheme.DEFAULT;
    private FontSize fontSize = FontSize.MEDIUM;

    public ColorTheme getColorTheme() {
        return colorTheme;
    }

    public FontSize getFontSize() {
        return fontSize;
    }

    public void setColorTheme(ColorTheme colorTheme) {
        this.colorTheme = colorTheme;
    }

    public void setFontSize(FontSize fontSize) {
        this.fontSize = fontSize;
    }

    public void showLoginWindow(){
        System.out.println("Login");
        BaseController controller = new LoginWindowController(emailManager, this, "LoginWindow.fxml"); //used so this so that fxml file can have full control on Controller files
        initializeStage(controller);
    }

    public void showMainWindow(){
        System.out.println("Main");
        BaseController controller = new MainWindowController(emailManager, this, "MainWindow.fxml");
        initializeStage(controller);
        mainViewIntialized = true;
    }

    public void showOptionsWindow(){
        System.out.println("Optional");
        BaseController controller = new OptionsWindowController(emailManager, this, "OptionsWindow.fxml");
        initializeStage(controller);
    }

    public void showComposeMessageWindow(){
        System.out.println("Compose Message Window");
        BaseController controller = new ComposeMessageController(emailManager, this, "ComposeMessageWindow.fxml");
        initializeStage(controller);
    }

    public void showEmailDetailsWindow(){
        BaseController controller = new EmailDetailsController(emailManager, this, "EmailDetailsWindow.fxml");
        initializeStage(controller);
    }

    private void initializeStage(BaseController baseController){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(baseController.getFxmlName()));
        fxmlLoader.setController(baseController);
        Parent parent;
        try{
            parent = fxmlLoader.load(); //
        }catch (IOException e){
            e.printStackTrace();
            return;
        }

        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
        activeStages.add(stage);
    }

    public void closeStage(Stage stageToClose){
        stageToClose.close(); // once we click on login button it will take us to main window, but login window remains open, so to close it we use this function
        activeStages.remove(stageToClose);
    }

    public void updateStyles() {
            for (Stage stage: activeStages) {
                Scene scene = stage.getScene();
                scene.getStylesheets().clear(); // clears the default stylesheet from the list
                assert ColorTheme.getCssPath(colorTheme) != null;
                scene.getStylesheets().add(getClass().getResource(ColorTheme.getCssPath(colorTheme)).toExternalForm());//puts the css files which we added
                scene.getStylesheets().add(getClass().getResource(FontSize.getCssPath(fontSize)).toExternalForm()); // for font files
            }
    }
}
