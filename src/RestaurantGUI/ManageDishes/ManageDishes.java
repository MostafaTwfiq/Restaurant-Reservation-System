package RestaurantGUI.ManageDishes;

import RestaurantGUI.ProjectSystem.StyleMode.StyleMode;
import Restaurant.Properties.Food;
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

public abstract class ManageDishes {

    protected StyleMode styleMode;

    protected boolean thereExistChanges = false;

    protected Food food;

    protected Stage stage;

    protected Button cancelB;

    private Label nameLabel;
    private Label userNameLabel;
    private Label passwordLabel;
    private Label dishNameLabel;
    private Label dishPriceLabel;

    protected TextField dishNameField;
    protected TextField dishPriceField;

    private ToggleGroup radioButtonsGroup;
    protected RadioButton mainCourseRadioB;
    protected RadioButton appetizerRadioB;
    protected RadioButton desertRadioB;

    protected HBox radioButtonsLayout;
    protected VBox dishInputLayout;
    protected VBox buttonsLayout;
    protected GridPane dishInputPane;
    protected VBox parentLayout;

    private Scene scene;

    public ManageDishes(StyleMode styleMode){
        this.styleMode = styleMode;
    }

    protected void setUpObjects(String stageTitle){

        //Stage:
        stage = setUpStage(stageTitle);

        //Label:
        nameLabel = setUpLabel("Name:");
        userNameLabel = setUpLabel("User name:");
        passwordLabel = setUpLabel("Password:");
        dishNameLabel = setUpLabel("Dish name:");
        dishPriceLabel = setUpLabel("Dish price:");

        //Text field:
        dishNameField = setUpTextFields("Enter the dish name.");
        dishNameField.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.ENTER)
                dishPriceField.requestFocus();
        });

        dishPriceField = setUpTextFields("Enter the dish price.");

        //Radio buttons:
        mainCourseRadioB = new RadioButton("Main Course");
        mainCourseRadioB.setCursor(Cursor.HAND);
        mainCourseRadioB.setStyle("-fx-text-fill: #" + styleMode.getLabelColor() + "; ");

        appetizerRadioB = new RadioButton("Appetizer");
        appetizerRadioB.setCursor(Cursor.HAND);
        appetizerRadioB.setStyle("-fx-text-fill: #" + styleMode.getLabelColor() + "; ");

        desertRadioB = new RadioButton("Dessert");
        desertRadioB.setCursor(Cursor.HAND);
        desertRadioB.setStyle("-fx-text-fill: #" + styleMode.getLabelColor() + "; ");

        radioButtonsGroup = new ToggleGroup();
        radioButtonsGroup.getToggles().addAll(mainCourseRadioB, appetizerRadioB, desertRadioB);

        //Buttons:
        cancelB = setUpButtons("Cancel");
        setUpCancelBAction();

        //Layouts:
        dishInputPane = setUpDishInputPane();
        radioButtonsLayout = setUpRadioButtonsLayout();
        dishInputLayout = setUpDishInputLayout();
        buttonsLayout = setUpButtonsLayout();
        parentLayout = setUpParentLayout();

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

    private Label setUpLabel(String text){
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
        textField.setPrefWidth(305);
        textField.setStyle(textFieldStyle());
        return textField;
    }

    private String textFieldStyle(){
        return "-fx-background-color: #" + styleMode.getTextFieldColor() + "; " +
                "-fx-background-radius: 50;" +
                "-fx-border-radius: 50;" +
                "-fx-border-color: #000000; -fx-border-width: 0.3px; ";
    }

    private HBox setUpRadioButtonsLayout(){
        HBox radioButtonsLayout = new HBox(30);
        radioButtonsLayout.setAlignment(Pos.CENTER_LEFT);
        radioButtonsLayout.styleProperty().set("-fx-background-color: #" + styleMode.getLayoutColor() + "; ");
        radioButtonsLayout.getChildren().addAll(mainCourseRadioB, appetizerRadioB, desertRadioB);
        return radioButtonsLayout;
    }

    private VBox setUpButtonsLayout(){
        VBox buttonsLayout = new VBox(10);
        buttonsLayout.setAlignment(Pos.CENTER);
        buttonsLayout.styleProperty().set("-fx-background-color: #" + styleMode.getLayoutColor() + "; ");
        return buttonsLayout;
    }

    private GridPane setUpDishInputPane(){
        GridPane editDishPane = new GridPane();
        editDishPane.setHgap(20);
        editDishPane.setVgap(10);
        editDishPane.setAlignment(Pos.TOP_LEFT);
        editDishPane.styleProperty().set("-fx-background-color: #" + styleMode.getLayoutColor() + "; ");
        GridPane.setConstraints(dishNameLabel, 0, 0);
        GridPane.setConstraints(dishNameField, 1, 0);
        GridPane.setConstraints(dishPriceLabel, 0, 1);
        GridPane.setConstraints(dishPriceField, 1, 1);
        editDishPane.getChildren().addAll(dishNameLabel, dishNameField, dishPriceLabel, dishPriceField);
        return editDishPane;
    }

    private VBox setUpDishInputLayout(){
        VBox dishInputLayout = new VBox(10);
        dishInputLayout.setAlignment(Pos.TOP_LEFT);
        dishInputLayout.styleProperty().set("-fx-background-color: #" + styleMode.getLayoutColor() + "; ");
        return dishInputLayout;
    }

    private VBox setUpParentLayout(){
        VBox parentLayout = new VBox(70);
        parentLayout.setAlignment(Pos.CENTER);
        parentLayout.setPadding(new Insets(10,10,10,10));
        parentLayout.styleProperty().set("-fx-background-color: #" + styleMode.getLayoutColor() + "; ");
        parentLayout.getChildren().addAll(dishInputLayout, buttonsLayout);
        return parentLayout;
    }

    private void setUpCancelBAction(){
        cancelB.setOnAction(e -> this.stage.close());
    }

    protected boolean isValidInput(String input){
        if (input == null)
            return false;

        return !input.equals("");
    }

    protected boolean isValidPrice(String price){
        if (price.equals("") || price.length() > 6)
            return false;

        for (int i = 0; i < price.length(); i++)
            if (!(price.charAt(i) >= '0' && price.charAt(i) <= '9'))
                return false;

        return true;
    }

}
