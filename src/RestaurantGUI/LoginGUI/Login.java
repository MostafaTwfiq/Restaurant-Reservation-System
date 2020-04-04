package RestaurantGUI.LoginGUI;

import RestaurantGUI.ProjectSystem.StyleMode.SetStyle;
import RestaurantGUI.ProjectSystem.StyleMode.StyleMode;
import Restaurant.HandlingData.SaveData;
import Restaurant.Useres.Users;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.FileInputStream;

public final class Login {

    private StyleMode styleMode;
    private Users user;

    private Stage loginStage;

    private TextField userNameField;
    private PasswordField passwordField;

    private Label userNameLabel;
    private Label passwordLabel;

    private Button  loginB;
    private Button setStyleB;
    private Button newUserB;

    private GridPane loginPane;
    private HBox buttonsLayout;
    private VBox parentLayout;

    private Scene scene;

    public Login(StyleMode styleMode){
        this.styleMode = styleMode;
    }

    public StyleMode getStyleMode() { return styleMode; }

    public Users login(UsersVector users, SaveData saver){
        //Set user as null:
        user = null;

        //Stage:
        loginStage = setUpLoginStage(saver);

        //Text fields:
        userNameField = setUpUserNameField();
        passwordField = setUpPasswordField();

        //Label:
        userNameLabel = setUpLabel("User name:");
        passwordLabel = setUpLabel("Password:");

        //Buttons:
        loginB = setUpButton("Login");
        setUpLoginBAction(users);

        setStyleB = setUpSetStyleB();
        setUpSetStyleBImage();
        setUpSetStyleBAction();

        newUserB = setUpButton("Create new client");
        setUpNewUserBAction(users);

        //Layouts:
        loginPane = setUpLoginPane();
        buttonsLayout = setUpButtonsLayout();
        parentLayout = setUpParentLayout();

        //Scene:
        scene = new Scene(parentLayout, 310,120);

        //Show stage:
        loginStage.setScene(scene);
        loginStage.showAndWait();
        return user;
    }

    private void getNewMode(){
        userNameField.setStyle(textFieldsStyle());
        passwordField.setStyle(textFieldsStyle());

        userNameLabel.setStyle("-fx-text-fill: #" + styleMode.getLabelColor() + "; ");
        passwordLabel.setStyle("-fx-text-fill: #" + styleMode.getLabelColor() + "; ");


        loginB.setStyle(getButtonStyle());

        setStyleB.setStyle(getButtonStyle());
        setUpSetStyleBImage();

        newUserB.setStyle(getButtonStyle());

        loginPane.styleProperty().set("-fx-background-color: #" + styleMode.getLayoutColor() + "; ");

        buttonsLayout.styleProperty().set("-fx-background-color: #" + styleMode.getLayoutColor() + "; ");

        parentLayout.styleProperty().set("-fx-background-color: #" + styleMode.getLayoutColor() + "; ");
    }

    private Stage setUpLoginStage(SaveData saver){
        Stage loginStage = new Stage();
        loginStage.setTitle("Login");
        loginStage.setResizable(false);
        loginStage.setOnCloseRequest(e -> {
            if (user == null) {
                saver.setStyleMode(styleMode);
                saver.saveData();
                System.exit(0);
            }
        });
        return loginStage;
    }

    private TextField setUpUserNameField(){
        TextField userNameField = new TextField();
        userNameField.setPromptText("Enter your user name.");
        userNameField.setPrefWidth(200);
        userNameField.setStyle(textFieldsStyle());

        userNameField.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.ENTER)
                passwordField.requestFocus();
        });

        return userNameField;
    }

    private PasswordField setUpPasswordField(){
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter your password.");
        passwordField.setPrefWidth(200);
        passwordField.setStyle(textFieldsStyle());

        passwordField.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.ENTER)
                loginB.fire();
        });

        return passwordField;
    }

    private String textFieldsStyle(){
        return "-fx-background-color: #" + styleMode.getTextFieldColor() + "; " +
                "-fx-background-radius: 18;" +
                "-fx-border-radius: 18;" +
                "-fx-border-color: #000000; " +
                "-fx-border-width: 0.3px; ";
    }

    private Button setUpButton(String buttonText){
        Button button = new Button();
        button.setText(buttonText);
        button.setCursor(Cursor.HAND);

        InnerShadow innerShadow = new InnerShadow();
        innerShadow.setColor(Color.LIGHTSKYBLUE);
        Glow glow = new Glow();
        glow.setLevel(0.2);
        button.setEffect(innerShadow);
        button.setOnMouseEntered(e -> button.setEffect(glow));
        button.setOnMouseExited(e -> button.setEffect(innerShadow));

        button.setPrefHeight(25);
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

    private void setUpLoginBAction(UsersVector users){
        loginB.setOnAction(e -> {
            String userName = userNameField.getText();
            String password = passwordField.getText();
            int userIndex = users.find(userName, password);

            if (userIndex != -1) {
                this.user = users.get(userIndex);
                loginStage.close();
            }

            else {
                userNameField.clear();
                passwordField.clear();
                new MessageBox(styleMode).messageBox("Alert", "Invalid user name or password.");
            }
        });
    }

    private void setUpNewUserBAction(UsersVector users){
        newUserB.setOnAction(e -> new NewUserGUI(styleMode).createNewUser(users, loginStage, scene));
    }

    private Label setUpLabel(String labelText){
        Label label = new Label();
        label.setText(labelText);
        label.setFont(new Font("Cambria", 15));
        label.setStyle("-fx-text-fill: #" + styleMode.getLabelColor() + "; ");
        return label;
    }

    private GridPane setUpLoginPane(){
        GridPane loginPane = new GridPane();
        GridPane.setConstraints(userNameLabel, 0, 0);
        GridPane.setConstraints(userNameField, 1, 0);
        GridPane.setConstraints(passwordLabel, 0 ,1);
        GridPane.setConstraints(passwordField, 1, 1);
        loginPane.setHgap(20);
        loginPane.setVgap(10);
        loginPane.setAlignment(Pos.TOP_LEFT);
        loginPane.getChildren().addAll(userNameLabel, userNameField, passwordLabel, passwordField);
        return loginPane;
    }

    private HBox setUpButtonsLayout(){
        HBox buttonsLayout = new HBox(10);
        buttonsLayout.setAlignment(Pos.CENTER);
        buttonsLayout.styleProperty().set("-fx-background-color: #" + styleMode.getLayoutColor() + "; ");
        buttonsLayout.getChildren().addAll(loginB, setStyleB, newUserB);
        return buttonsLayout;
    }

    private VBox setUpParentLayout(){
        VBox parentLayout = new VBox(5);
        parentLayout.setAlignment(Pos.TOP_CENTER);
        parentLayout.setPadding(new Insets(5,5,5,5));
        parentLayout.styleProperty().set("-fx-background-color: #" + styleMode.getLayoutColor() + "; ");
        parentLayout.getChildren().addAll(loginPane, buttonsLayout);
        return parentLayout;
    }

    private Button setUpSetStyleB(){
        Button setStyleB = new Button();
        setStyleB.setCursor(Cursor.HAND);

        InnerShadow innerShadow = new InnerShadow();
        innerShadow.setColor(Color.LIGHTSKYBLUE);
        Glow glow = new Glow();
        glow.setLevel(0.2);
        setStyleB.setEffect(innerShadow);
        setStyleB.setOnMouseEntered(e -> setStyleB.setEffect(glow));
        setStyleB.setOnMouseExited(e -> setStyleB.setEffect(innerShadow));

        setStyleB.setPrefHeight(25);
        setStyleB.setPrefWidth(25);
        setStyleB.setStyle(getButtonStyle());
        return setStyleB;
    }

    private void setUpSetStyleBImage(){
        try {
            FileInputStream imageStream = new FileInputStream("resources\\"
                    + styleMode.getStyleType() + "Reading.png");

            Image buttonImage = new Image(imageStream);
            ImageView buttonImageView = new ImageView(buttonImage);
            buttonImageView.setFitHeight(25);
            buttonImageView.setFitWidth(25);

            setStyleB.setGraphic(buttonImageView);

        } catch (Exception e) {
            System.out.println("There is an error happened while loading images.");
        }

    }

    private void setUpSetStyleBAction(){
        setStyleB.setOnAction(e -> {
            this.styleMode = new SetStyle().show();
            getNewMode();
        });
    }

}
