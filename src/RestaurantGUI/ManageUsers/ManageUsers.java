package RestaurantGUI.ManageUsers;

import RestaurantGUI.ProjectSystem.StyleMode.StyleMode;
import Restaurant.Useres.Users;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.Glow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

public abstract class ManageUsers {

    protected StyleMode styleMode;

    //Stage:
    protected Stage stage;

    //ObservableList:
    protected ObservableList<Users> usersObservableList;

    //Buttons:
    protected Button cancelB;

    //Labels:
    private Label nameLabel;
    private Label userNameLabel;
    private Label passwordLabel;

    //Text fields:
    protected TextField nameField;
    protected TextField userNameField;
    protected TextField passwordField;

    //Radio buttons:
    private ToggleGroup radioButtonsGroup;
    private RadioButton clientRadioB;
    private RadioButton vipClientRadioB;
    private RadioButton managerRadioB;
    private RadioButton cookerRadioB;
    private RadioButton waiterRadioB;

    //Layouts:
    protected GridPane userInputPane;
    protected HBox radioButtonsLayout;
    protected VBox userInputLayout;
    protected VBox buttonsLayout;
    protected VBox parentLayout;

    private Scene scene;

    public ManageUsers(StyleMode styleMode){
        this.styleMode = styleMode;
    }

    protected void setUpObjects(String stageTitle){

        stage = setUpStage(stageTitle);

        cancelB = setUpButtons("Cancel");
        setUpCancelBAction();

        //Labels:
        nameLabel = setUpEditLabels("Name: ");
        userNameLabel = setUpEditLabels("User name: ");
        passwordLabel = setUpEditLabels("Password: ");

        //Text fields:
        nameField = setUpTextFields("Please enter the name.");
        nameField.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.ENTER)
                userNameField.requestFocus();
        });

        userNameField = setUpTextFields("Please enter the user name.");
        userNameField.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.ENTER)
                passwordField.requestFocus();
        });

        passwordField = setUpTextFields("Please enter the password.");

        //Radio buttons:
        clientRadioB = new RadioButton("Client");
        clientRadioB.setCursor(Cursor.HAND);
        clientRadioB.setStyle("-fx-text-fill: #" + styleMode.getLabelColor() + "; ");

        vipClientRadioB = new RadioButton("Vip Client");
        vipClientRadioB.setCursor(Cursor.HAND);
        vipClientRadioB.setStyle("-fx-text-fill: #" + styleMode.getLabelColor() + "; ");

        managerRadioB = new RadioButton("Manager");
        managerRadioB.setCursor(Cursor.HAND);
        managerRadioB.setStyle("-fx-text-fill: #" + styleMode.getLabelColor() + "; ");

        cookerRadioB = new RadioButton("Cooker");
        cookerRadioB.setCursor(Cursor.HAND);
        cookerRadioB.setStyle("-fx-text-fill: #" + styleMode.getLabelColor() + "; ");

        waiterRadioB = new RadioButton("Waiter");
        waiterRadioB.setCursor(Cursor.HAND);
        waiterRadioB.setStyle("-fx-text-fill: #" + styleMode.getLabelColor() + "; ");

        radioButtonsGroup = new ToggleGroup();
        radioButtonsGroup.getToggles().addAll(clientRadioB, vipClientRadioB,
                managerRadioB, cookerRadioB, waiterRadioB);

        //Layouts:
        userInputPane = setUpUserInputPane();
        radioButtonsLayout = setUpRadioButtonsLayouts();
        userInputLayout = setUpUserInputLayout();
        buttonsLayout = setUpButtonsLayout();
        parentLayout = setUpParentLayout();

        //Scene:
        scene = new Scene(parentLayout, 420, 310);

        stage.setScene(scene);
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
        button.setText(text);

        InnerShadow innerShadow = new InnerShadow();
        innerShadow.setColor(Color.LIGHTSKYBLUE);
        Glow glow = new Glow();
        glow.setLevel(0.2);
        button.setEffect(innerShadow);
        button.setOnMouseEntered(e -> button.setEffect(glow));
        button.setOnMouseExited(e -> button.setEffect(innerShadow));

        button.setCursor(Cursor.HAND);
        button.setPrefHeight(40);
        button.setPrefWidth(400);
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

    private Label setUpEditLabels(String text){
        Label editLabel = new Label();
        editLabel.setText(text);
        editLabel.setFont(new Font("Cambria", 15));
        editLabel.setStyle("-fx-text-fill: #" + styleMode.getLabelColor() + "; ");
        return editLabel;
    }

    private TextField setUpTextFields(String promptText){
        TextField textField = new TextField();
        textField.setPromptText(promptText);
        textField.setPrefWidth(100);
        textField.setPrefWidth(270);
        textField.setStyle(textFieldStyle());
        return textField;
    }

    private String textFieldStyle(){
        return "-fx-background-color: #" + styleMode.getTextFieldColor() + "; " +
                "-fx-background-radius: 50;" +
                "-fx-border-radius: 50;" +
                "-fx-border-color: #000000; -fx-border-width: 0.3px; ";
    }

    private GridPane setUpUserInputPane(){
        GridPane editUserPane = new GridPane();
        editUserPane.setHgap(50);
        editUserPane.setVgap(10);
        editUserPane.setAlignment(Pos.TOP_LEFT);
        editUserPane.styleProperty().set("-fx-background-color: #" + styleMode.getLayoutColor() + "; ");
        GridPane.setConstraints(nameLabel, 0, 0);
        GridPane.setConstraints(nameField, 1, 0);
        GridPane.setConstraints(userNameLabel, 0, 1);
        GridPane.setConstraints(userNameField, 1, 1);
        GridPane.setConstraints(passwordLabel, 0, 2);
        GridPane.setConstraints(passwordField, 1, 2);
        editUserPane.getChildren().addAll(nameLabel, nameField, userNameLabel,
                userNameField, passwordLabel, passwordField);
        return editUserPane;
    }

    private HBox setUpRadioButtonsLayouts(){
        HBox radioButtonsLayout = new HBox(20);
        radioButtonsLayout.setAlignment(Pos.CENTER_LEFT);
        radioButtonsLayout.styleProperty().set("-fx-background-color: #" + styleMode.getLayoutColor() + "; ");
        radioButtonsLayout.getChildren().addAll(clientRadioB, vipClientRadioB,
                managerRadioB, cookerRadioB, waiterRadioB);
        return radioButtonsLayout;
    }

    private VBox setUpUserInputLayout(){
        VBox usersInputLayout = new VBox(10);
        usersInputLayout.setAlignment(Pos.CENTER);
        usersInputLayout.styleProperty().set("-fx-background-color: #" + styleMode.getLayoutColor() + "; ");
        return usersInputLayout;
    }

    private VBox setUpButtonsLayout(){
        VBox buttonsLayout = new VBox(10);
        buttonsLayout.setAlignment(Pos.CENTER);
        buttonsLayout.styleProperty().set("-fx-background-color: #" + styleMode.getLayoutColor() + "; ");
        return buttonsLayout;
    }

    private VBox setUpParentLayout(){
        VBox parentLayout = new VBox(30);
        parentLayout.setAlignment(Pos.CENTER);
        parentLayout.setPadding(new Insets(10,10,10,10));
        parentLayout.styleProperty().set("-fx-background-color: #" + styleMode.getLayoutColor() + "; ");
        parentLayout.getChildren().addAll(userInputLayout, buttonsLayout);
        return parentLayout;
    }

    private void setUpCancelBAction(){
        cancelB.setOnAction(e -> this.stage.close());
    }

    protected boolean isValidInput(String input){
        return !input.equals("");
    }

    protected String getRoleFromRadioButtons(){
        if (clientRadioB.isSelected())
            return "Client";

        else if(vipClientRadioB.isSelected())
            return "VipClient";

        else if (managerRadioB.isSelected())
            return "Manager";

        else if (cookerRadioB.isSelected())
            return "Cooker";

        else if (waiterRadioB.isSelected())
            return "Waiter";

        else
            return "";
    }
}
