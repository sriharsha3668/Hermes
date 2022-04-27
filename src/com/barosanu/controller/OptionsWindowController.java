package com.barosanu.controller;

import com.barosanu.EmailManager;
import com.barosanu.view.ColorTheme;
import com.barosanu.view.FontSize;
import com.barosanu.view.ViewFactory;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Slider;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.net.URL;
import java.util.ResourceBundle;

import static com.barosanu.view.ColorTheme.values;

public class OptionsWindowController extends BaseController implements Initializable {

    public OptionsWindowController(EmailManager emailManager, ViewFactory viewFactory, String fxmlFile) {
        super(emailManager, viewFactory, fxmlFile);
    }

    @FXML
    private Slider fontSizePicker;

    @FXML
    private ChoiceBox<ColorTheme> themePicker;

    @FXML
    void applyBtnAction() {
        viewFactory.setColorTheme(themePicker.getValue());
        viewFactory.setFontSize(FontSize.values()[(int)(fontSizePicker.getValue())]);
        System.out.println(viewFactory.getColorTheme());
        System.out.println(viewFactory.getFontSize());
        viewFactory.updateStyles();
    }

    @FXML
    void cancelButtonAction() {
        Stage stage = (Stage)fontSizePicker.getScene().getWindow();
        viewFactory.closeStage(stage);
    }

    @Override
    //Used to set up the font size and color theme once the optionsWindow is made.
    //  this method will be called right after the object is created in order to update and to customize it the way we want to see.
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setUpThemePicker();
        setUpFontSizePicker();
    }

    private void setUpFontSizePicker() {
        fontSizePicker.setMin(0);
        fontSizePicker.setMax(FontSize.values().length-1);
        fontSizePicker.setValue(viewFactory.getFontSize().ordinal());
        fontSizePicker.setMajorTickUnit(1);
        fontSizePicker.setMinorTickCount(0);
        fontSizePicker.setBlockIncrement(1);
        fontSizePicker.setSnapToTicks(true);
        fontSizePicker.setShowTickMarks(true);
        fontSizePicker.setShowTickLabels(true);
        // Used to convert 0,1,2 in font size picker to light default and dark
        fontSizePicker.setLabelFormatter(new StringConverter<Double>() {
            @Override
            public String toString(Double object) {
                int i = object.intValue();
                return FontSize.values()[i].toString();
            }

            @Override
            public Double fromString(String s) {
                return null;
            }
        });
        fontSizePicker.valueProperty().addListener((obs,oldVal,newVal) -> {
            fontSizePicker.setValue(newVal.intValue());
        });
    }

    private void setUpThemePicker() {
        themePicker.setItems(FXCollections.observableArrayList(ColorTheme.values())); // sets the options in themePicker component that is Default,Dark,Light.
        // .value gets all the value and put the value into list
        themePicker.setValue(viewFactory.getColorTheme());
    }
}
