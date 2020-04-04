package RestaurantGUI.ManageUsers;

import RestaurantGUI.ProjectSystem.StyleMode.StyleMode;
import RestaurantGUI.ProjectSystem.MessageBox;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

public final class VipClientDiscount {

    private StyleMode styleMode;

    private int discount = 0;

    public VipClientDiscount(StyleMode styleMode){
        this.styleMode = styleMode;
    }

    public int getDiscountInput(){
        //Stage:
        Stage stage = setUpStage();

        //EnterButton:
        Button enterB = setUpEnterB();

        //TextField:
        TextField discountField = setUpDiscountField(enterB);

        //Label:
        Label titleLabel = setUpTitleLabel();

        //Buttons:
        setUpEnterBAction(enterB, discountField, stage);

        //Layouts:
        HBox titleLayout = setupHBoxLayout();
        titleLayout.getChildren().add(titleLabel);

        HBox inputLayout = setupHBoxLayout();
        inputLayout.getChildren().addAll(discountField, enterB);

        VBox parentLayout = setUpParentLayout(titleLayout, inputLayout);

        //Scene:
        Scene scene = new Scene(parentLayout, 420,100);

        //Show stage:
        stage.setScene(scene);
        stage.showAndWait();
        return discount;
    }

    private Stage setUpStage(){
        Stage stage = new Stage();
        stage.setTitle("The amount of discount");
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setOnCloseRequest(e -> {
            if (discount == 0) {
                discount = 50;
                new MessageBox(styleMode).messageBox("Alert", "The discount will initially be 50%");
            }
        });
        return stage;
    }

    private TextField setUpDiscountField(Button enterB){
        TextField discountField = new TextField();
        discountField.setPromptText("Enter the amount of the discount in integer percentage.");
        discountField.setPrefWidth(320);
        discountField.setStyle(discountFieldStyle());

        discountField.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.ENTER)
                enterB.fire();
        });

        return discountField;
    }

    private String discountFieldStyle(){
        return "-fx-background-color: #" + styleMode.getTextFieldColor() + "; " +
                "-fx-background-radius: 18;" +
                "-fx-border-radius: 18;" +
                "-fx-border-color: #000000; -fx-border-width: 0.3px; ";
    }

    private Label setUpTitleLabel(){
        Label titleLabel = new Label();
        titleLabel.setText("Enter the amount of the discount in integer percentage:");
        titleLabel.setFont(new Font("Cambria", 15));
        titleLabel.setStyle("-fx-text-fill: #" + styleMode.getLabelColor() + "; ");
        return titleLabel;
    }

    private Button setUpEnterB(){
        Button enterB = new Button();
        enterB.setText("Enter");
        enterB.setCursor(Cursor.HAND);
        enterB.setPrefHeight(40);
        enterB.setPrefWidth(50);
        enterB.setStyle(getButtonStyle());
        return enterB;
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

    private void setUpEnterBAction(Button enterB, TextField discountField, Stage stage){
        enterB.setOnAction(e -> {
            String discount = discountField.getText();
            if (isValidInput(discount)) {
                this.discount = Integer.parseInt(discount);
                new MessageBox(styleMode).messageBox("Success", "The discount has been added!");
                stage.close();
            }
            else
                new MessageBox(styleMode).messageBox("Alert", "Please enter a valid discount.");
        });
    }

    private boolean isValidInput(String discount){
        if (discount.equals("") || discount.length() > 3)
            return false;

        for (int i = 0; i < discount.length(); i++){
            if (!(discount.charAt(i) >= '0' && discount.charAt(i) <= '9'))
                return false;
        }
        int discountInt = Integer.parseInt(discount);
        return discountInt > 0 && discountInt <= 100;
    }

    private HBox setupHBoxLayout(){
        HBox layout = new HBox(10);
        layout.setAlignment(Pos.CENTER_LEFT);
        layout.styleProperty().set("-fx-background-color: #" + styleMode.getLayoutColor() + "; ");
        return layout;
    }

    private VBox setUpParentLayout(HBox titleLayout, HBox inputLayout){
        VBox parentLayout = new VBox(10);
        parentLayout.setPadding(new Insets(10,10,10,10));
        parentLayout.setAlignment(Pos.CENTER);
        parentLayout.styleProperty().set("-fx-background-color: #" + styleMode.getLayoutColor() + "; ");
        parentLayout.getChildren().addAll(titleLayout, inputLayout);
        return parentLayout;
    }
}
