package com.barosanu.controller;

import com.barosanu.EmailManager;
import com.barosanu.controller.services.LoginService;
import com.barosanu.model.EmailAccount;
import com.barosanu.view.ViewFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginWindowController extends BaseController implements Initializable {
    @FXML
    private TextField emailAddress;

    @FXML
    private Label errorLabel;

    @FXML
    private TextField passwordField;

    public LoginWindowController(EmailManager emailManager, ViewFactory viewFactory, String fxmlFile) {
        super(emailManager, viewFactory, fxmlFile);
    }

    @FXML
    void loginButtonAction() {
        System.out.println("Clicked me");
        if (fieldsAreValid()){
            EmailAccount emailAccount = new EmailAccount(emailAddress.getText(), passwordField.getText());
            LoginService loginService = new LoginService(emailAccount, emailManager);
            loginService.start(); // this will do the background work
            loginService.setOnSucceeded(event -> {
                EmailLoginResult emailLoginResult = loginService.getValue();

                switch (emailLoginResult) {
                    case SUCCESS:
                        System.out.println("login successful!!!" + emailAccount);
                        if (!viewFactory.isMainViewIntialized()) {
                            viewFactory.showMainWindow(); // on click, it changes from login to main window
                        }
                        Stage stage = (Stage)errorLabel.getScene().getWindow(); // this gets the window which has errorLabel
                        viewFactory.closeStage(stage); // then this deletes that stage which has errorLabel as a component
                        return;
                    case FAILED_BY_CREDENTIALS:
                        errorLabel.setText("invalid credentials!");
                        return;
                    case FAILED_BY_UNEXPECTED_ERROR:
                        errorLabel.setText("unexpected error!");
                        return;
                    default:
                        return;
                }
            });
        }
    }

    private boolean fieldsAreValid() {
        if(emailAddress.getText().isEmpty()) {
            errorLabel.setText("Please fill email");
            return false;
        }
        if(passwordField.getText().isEmpty()) {
            errorLabel.setText("Please fill password");
            return false;
        }

        return true;
    }

    //Is called whenever the controller is called
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        emailAddress.setText("testforclg@gmail.com");
        passwordField.setText("test@2024");
    }
}
