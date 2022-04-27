package com.barosanu.controller;


import com.barosanu.EmailManager;
import com.barosanu.view.ViewFactory;

public abstract class BaseController {

    protected EmailManager emailManager;
    protected ViewFactory viewFactory;
    private String fxmlFile; //Indication to a fxml file

    public BaseController(EmailManager emailManager, ViewFactory viewFactory, String fxmlFile) {
        this.emailManager = emailManager;
        this.viewFactory = viewFactory;
        this.fxmlFile = fxmlFile;
    }


    public String getFxmlName() {
        return fxmlFile;
    }
}
