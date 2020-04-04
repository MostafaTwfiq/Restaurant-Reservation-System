package RestaurantGUI.LoginGUI;

import RestaurantGUI.ProjectSystem.StyleMode.StyleMode;
import Restaurant.Useres.Clients.Client;
import Restaurant.Vectors.UsersVector;
import RestaurantGUI.ProjectSystem.MessageBox;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.Glow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public final class NewUserGUI {

    private StyleMode styleMode;

    private Button addB;
    private Button goBackB;

    private TextField nameField;
    private TextField userNameField;
    private PasswordField passwordField;

    private Label nameLabel;
    private Label userNameLabel;
    private Label passwordLabel;

    private GridPane inputPane;
    private HBox buttonsLayout;
    private VBox parentLayout;

    private Scene scene;

    public NewUserGUI(StyleMode styleMode){
        this.styleMode = styleMode;
    }

    public void createNewUser(UsersVector users, Stage stage, Scene prevScene){
        //Text Fields:
        nameField = setUpTextField("Enter your name");
        nameField.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.ENTER)
                userNameField.requestFocus();
        });

        userNameField = setUpTextField("Enter your user name");
        userNameField.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.ENTER)
                passwordField.requestFocus();
        });

        passwordField = setUpPasswordField();

        //Labels:
        nameLabel = setUpLabels("Name:");
        userNameLabel = setUpLabels("User name:");
        passwordLabel = setUpLabels("password:");

        //Buttons:
        addB = setUpButton("Create new client");
        setUpAddBAction(users, stage, prevScene);

        goBackB = setUpButton("Cancel");
        setUpGoBackBAction(stage, prevScene);

        //Layouts:
        inputPane = setUpInputPane();
        buttonsLayout = setUpButtonsLayout();
        parentLayout = setUpParentLayout();

        //Scene:
        scene = new Scene(parentLayout, 310,200);

        //Show stage:
        stage.setScene(scene);
    }

    private TextField setUpTextField(String text){
        TextField field = new TextField();
        field.setPromptText(text);
        field.setPrefWidth(200);
        field.setStyle(textFieldStyle());
        return field;
    }

    private PasswordField setUpPasswordField(){
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter your password");
        passwordField.setPrefWidth(200);
        passwordField.setStyle(textFieldStyle());

        passwordField.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.ENTER)
                addB.fire();
        });

        return passwordField;
    }

    private String textFieldStyle(){
        return "-fx-background-color: #" + styleMode.getTextFieldColor() + "; " +
                "-fx-background-radius: 18;" +
                "-fx-border-radius: 18;" +
                "-fx-border-color: #000000; -fx-border-width: 0.3px; ";
    }

    private Label setUpLabels(String text){
        Label label = new Label();
        label.setText(text);
        label.setFont(new Font("Cambria", 15));
        label.setStyle("-fx-text-fill: #" + styleMode.getLabelColor() + "; ");
        return label;
    }

    private Button setUpButton(String text){
        Button button = new Button();
        button.setText(text);
        button.setCursor(Cursor.HAND);

        InnerShadow innerShadow = new InnerShadow();
        innerShadow.setColor(Color.LIGHTSKYBLUE);
        Glow glow = new Glow();
        glow.setLevel(0.2);
        button.setEffect(innerShadow);
        button.setOnMouseEntered(e -> button.setEffect(glow));
        button.setOnMouseExited(e -> button.setEffect(innerShadow));

        button.setPrefHeight(40);
        button.setPrefWidth(120);
        button.setStyle(getButtonStyle());
        return button;
    }

    private String getButtonStyle(){
        return "-fx-background-color: #" + styleMode.getButtonsColor() + "; " +
                "-fx-font-weight: bold; " +
                "-fx-font-size: 12; " +
                "-fx-text-fill: " + styleMode.getButtonsTextColor() + "; " +
                "-fx-background-radius: 20; " +
                "-fx-border-radius: 20;" +
                "-fx-border-color: #000000; " +
                "-fx-border-width: 1px; ";
    }

    private void setUpAddBAction(UsersVector users, Stage stage, Scene prevScene){
        addB.setOnAction(e -> {
            String name = nameField.getText();
            String userName = userNameField.getText();
            String password = passwordField.getText();
            if (isValidInput(name) && isValidInput(userName) && isValidInput(password)){

                if (users.find(userName, password) != -1)
                    new MessageBox(styleMode).messageBox("Alert",
                            "Please enter a different user name.");

                else {
                    users.add(new Client(name, userName, password));
                    new MessageBox(styleMode).messageBox("Success", "User has been added!");
                    stage.setScene(prevScene);
                }
            }

            else
                new MessageBox(styleMode).messageBox("Alert", "Please enter a valid input.");

        });
    }

    private boolean isValidInput(String input){
        input = input.trim();

        return !input.equals("");
    }

    private void setUpGoBackBAction(Stage stage, Scene prevScene){
        goBackB.setOnAction(e -> stage.setScene(prevScene));
    }

    private GridPane setUpInputPane(){
        GridPane inputPane = new GridPane();
        inputPane.setVgap(20);
        inputPane.setHgap(10);
        inputPane.setAlignment(Pos.TOP_LEFT);
        inputPane.styleProperty().set("-fx-background-color: #" + styleMode.getLayoutColor() + "; ");
        GridPane.setConstraints(nameLabel, 0, 0);
        GridPane.setConstraints(nameField, 1, 0);
        GridPane.setConstraints(userNameLabel, 0, 1);
        GridPane.setConstraints(userNameField, 1, 1);
        GridPane.setConstraints(passwordLabel, 0, 2);
        GridPane.setConstraints(passwordField, 1, 2);
        inputPane.getChildren().addAll(nameLabel, nameField, userNameLabel,
                userNameField, passwordLabel, passwordField);
        return inputPane;
    }

    private HBox setUpButtonsLayout(){
        HBox buttonsLayout = new HBox(35);
        buttonsLayout.styleProperty().set("-fx-background-color: #" + styleMode.getLayoutColor() + "; ");
        buttonsLayout.setAlignment(Pos.CENTER);
        buttonsLayout.getChildren().addAll(goBackB, addB);
        return buttonsLayout;
    }

    private VBox setUpParentLayout(){
        VBox parentLayout = new VBox(10);
        parentLayout.setPadding(new Insets(10,10,10,10));
        parentLayout.setAlignment(Pos.CENTER);
        parentLayout.styleProperty().set("-fx-background-color: #" + styleMode.getLayoutColor() + "; ");
        parentLayout.getChildren().addAll(inputPane, buttonsLayout);
        return parentLayout;
    }
}
