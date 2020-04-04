package RestaurantGUI.ManageTables;

import RestaurantGUI.ProjectSystem.StyleMode.StyleMode;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.Glow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

public abstract class ManageTablesGUI {

    protected StyleMode styleMode;

    protected Stage stage;

    protected Button addOrEditButton;
    protected Button cancelButton;

    private Label tableNOSeatsLabel;

    protected TextField tableNOSeatsField;

    private ToggleGroup radioButtonsGroup;
    private RadioButton inSmokingAreaRadioB;
    private RadioButton notInSmokingAreaRadioB;

    private GridPane inputPane;
    protected VBox buttonsLayout;
    private VBox parentLayout;

    Scene scene;

    public ManageTablesGUI(StyleMode styleMode){
        this.styleMode = styleMode;
    }

    protected void setUpObjects(String stageText){
        //Stage:
        stage = setUpStage(stageText);

        //Labels:
        tableNOSeatsLabel = setUpLabel("Table number of seats:");

        //Text Fields:
        tableNOSeatsField = setUpTextFields("Enter the number of seats");
        tableNOSeatsField.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.ENTER)
                addOrEditButton.fire();
        });

        //Radio buttons and toggle group:
        inSmokingAreaRadioB = new RadioButton("Is in a smoking area");
        inSmokingAreaRadioB.setCursor(Cursor.HAND);
        inSmokingAreaRadioB.setStyle("-fx-text-fill: #" + styleMode.getLabelColor() + "; ");

        notInSmokingAreaRadioB = new RadioButton("Not in a smoking area");
        notInSmokingAreaRadioB.setCursor(Cursor.HAND);
        notInSmokingAreaRadioB.setStyle("-fx-text-fill: #" + styleMode.getLabelColor() + "; ");

        radioButtonsGroup = new ToggleGroup();
        radioButtonsGroup.getToggles().addAll(inSmokingAreaRadioB, notInSmokingAreaRadioB);

        //Buttons:
        cancelButton = setUpButtons("Cancel");
        setUpCancelBAction();

        //Layouts:
        inputPane = setUpInputPane();
        buttonsLayout = setUpButtonsLayout();
        parentLayout = setUpParentLayout();

        //Scene:
        scene = new Scene(parentLayout, 420, 310);
    }

    private Stage setUpStage(String text){
        Stage stage = new Stage();
        stage.setTitle(text);
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        return stage;
    }

    protected Button setUpButtons(String text){
        Button button = new Button();

        InnerShadow innerShadow = new InnerShadow();
        innerShadow.setColor(Color.LIGHTSKYBLUE);
        Glow glow = new Glow();
        glow.setLevel(0.2);
        button.setEffect(innerShadow);
        button.setOnMouseEntered(e -> button.setEffect(glow));
        button.setOnMouseExited(e -> button.setEffect(innerShadow));

        button.setCursor(Cursor.HAND);
        button.setPrefHeight(30);
        button.setPrefWidth(400);
        button.setText(text);
        button.setStyle(getButtonStyle());
        return button;
    }

    private String getButtonStyle(){
        return "-fx-background-color: #" + styleMode.getButtonsColor() + "; " +
                "-fx-font-weight: bold; " +
                "-fx-font-size: 20; " +
                "-fx-text-fill: " + styleMode.getButtonsTextColor() + "; " +
                "-fx-background-radius: 50; " +
                "-fx-border-radius: 50;" +
                "-fx-border-color: #000000; " +
                "-fx-border-width: 1px; ";
    }

    private Label setUpLabel(String text){
        Label label = new Label();
        label.setText(text);
        label.setFont(new Font("Cambria", 15));
        label.setStyle("-fx-text-fill: #" + styleMode.getLabelColor() + "; ");
        return label;
    }

    private TextField setUpTextFields(String promptText){
        TextField textField = new TextField();
        textField.setPromptText(promptText);
        textField.setPrefWidth(100);
        textField.setPrefWidth(200);
        textField.setStyle(textFieldStyle());
        return textField;
    }

    private String textFieldStyle(){
        return "-fx-background-color: #" + styleMode.getTextFieldColor() + "; " +
                "-fx-background-radius: 50;" +
                "-fx-border-radius: 50;" +
                "-fx-border-color: #000000; -fx-border-width: 0.3px; ";
    }

    private GridPane setUpInputPane(){
        GridPane inputPane = new GridPane();
        inputPane.setHgap(50);
        inputPane.setVgap(10);
        inputPane.setAlignment(Pos.TOP_LEFT);
        inputPane.styleProperty().set("-fx-background-color: #" + styleMode.getLayoutColor() + "; ");
        GridPane.setConstraints(tableNOSeatsLabel, 0, 0);
        GridPane.setConstraints(tableNOSeatsField, 1, 0);
        GridPane.setConstraints(inSmokingAreaRadioB, 0, 1);
        GridPane.setConstraints(notInSmokingAreaRadioB, 1, 1);
        inputPane.getChildren().addAll(tableNOSeatsLabel, tableNOSeatsField,
                inSmokingAreaRadioB, notInSmokingAreaRadioB);
        return inputPane;
    }

    private VBox setUpButtonsLayout(){
        VBox buttonsLayout = new VBox(10);
        buttonsLayout.setAlignment(Pos.CENTER);
        buttonsLayout.styleProperty().set("-fx-background-color: #" + styleMode.getLayoutColor() + "; ");
        return buttonsLayout;
    }

    private VBox setUpParentLayout(){
        VBox parentLayout = new VBox(60);
        parentLayout.setAlignment(Pos.CENTER);
        parentLayout.setPadding(new Insets(10, 10, 10, 10));
        parentLayout.styleProperty().set("-fx-background-color: #" + styleMode.getLayoutColor() + "; ");
        parentLayout.getChildren().addAll(inputPane, buttonsLayout);
        return parentLayout;
    }

    protected String getRadioButtonsValue(){
        if (inSmokingAreaRadioB.isSelected())
            return "true";
        else if (notInSmokingAreaRadioB.isSelected())
            return "false";
        else
            return "";
    }

    protected boolean isValidInput(String input){
        return !input.equals("");
    }

    protected boolean isValidNOSeats(String numberOfSeats){
        if (numberOfSeats.equals("") || numberOfSeats.length() > 3)
            return false;

        for (int i = 0; i < numberOfSeats.length(); i++) {
            if (!(numberOfSeats.charAt(i) >= '0' && numberOfSeats.charAt(i) <= '9'))
                return false;
        }

        if (Integer.parseInt(numberOfSeats) == 0)
            return false;

        return true;
    }

    private void setUpCancelBAction(){
        cancelButton.setOnAction(e -> this.stage.close());
    }

}
