package RestaurantGUI.ProjectSystem.StyleMode;

import RestaurantGUI.ProjectSystem.MessageBox;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.Glow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.FileInputStream;

public final class SetStyle {

    StyleMode styleMode = new DarkMode();

    private Stage stage;

    private Label titleLabel;

    private ImageView darkModeImageView;
    private ImageView lightModeImageView;

    private Image darkModeImage;
    private Image lightModeImage;

    private FileInputStream darkImageStream;
    private FileInputStream lightImageStream;

    private ToggleGroup radioButtonsGroup;
    private RadioButton darkModeRadioB;
    private RadioButton lightModeRadioB;

    private Button enterB;

    private VBox darkModeLayout;
    private VBox lightModeLayout;
    private HBox imagesLayout;
    private HBox enterBLayout;
    private VBox parentLayout;

    private Scene scene;

    public StyleMode show(){
        stage = setUpStage();

        titleLabel = setUpLabel("Please choose your preferred mode: ");

        darkModeRadioB = new RadioButton("Dark Mode");
        darkModeRadioB.setStyle("-fx-text-fill: #" + styleMode.getLabelColor() + "; ");
        darkModeRadioB.setSelected(true);
        darkModeRadioB.setCursor(Cursor.HAND);
        setUpDarkModeRadioBAction();

        lightModeRadioB = new RadioButton("Light Mode");
        lightModeRadioB.setStyle("-fx-text-fill: #" + styleMode.getLabelColor() + "; ");
        lightModeRadioB.setCursor(Cursor.HAND);
        setUpLightModeRadioBAction();

        radioButtonsGroup = new ToggleGroup();
        radioButtonsGroup.getToggles().addAll(darkModeRadioB, lightModeRadioB);

        enterB = setUpButton("Next");
        setUpEnterBAction();

        try {
            darkImageStream = new FileInputStream("resources\\darkMode.png");
            darkModeImage = new Image(darkImageStream);
            darkModeImageView = new ImageView(darkModeImage);
            darkModeImageView.setFitHeight(500);
            darkModeImageView.setFitWidth(700);
            darkModeImageView.setCursor(Cursor.HAND);
            darkModeImageView.setOnMouseClicked(e -> {
                darkModeRadioB.setSelected(true);
                styleMode = new DarkMode();
                getNewMode();
            });

            lightImageStream = new FileInputStream("resources\\lightMode.png");
            lightModeImage = new Image(lightImageStream);
            lightModeImageView = new ImageView(lightModeImage);
            lightModeImageView.setFitHeight(500);
            lightModeImageView.setFitWidth(700);
            lightModeImageView.setCursor(Cursor.HAND);
            lightModeImageView.setOnMouseClicked(e -> {
                lightModeRadioB.setSelected(true);
                styleMode = new LightMode();
                getNewMode();
            });

        } catch (Exception e) {
            System.out.println("There is an error happened while loading images.");
        }

        darkModeLayout = setUpVBoxLayout();
        darkModeLayout.getChildren().addAll(darkModeRadioB, darkModeImageView);

        lightModeLayout = setUpVBoxLayout();
        lightModeLayout.getChildren().addAll(lightModeRadioB, lightModeImageView);

        imagesLayout = setUpImagesLayout();

        enterBLayout = setUpEnterBLayout();

        parentLayout = setUpParentLayout();

        scene = new Scene(parentLayout, 1440, 610);
        stage.setScene(scene);
        stage.showAndWait();
        return styleMode;
    }

    private void getNewMode(){
        darkModeRadioB.setStyle("-fx-text-fill: #" + styleMode.getLabelColor() + "; ");

        lightModeRadioB.setStyle("-fx-text-fill: #" + styleMode.getLabelColor() + "; ");

        enterB.setStyle(getButtonStyle());

        darkModeLayout.styleProperty().set("-fx-background-color: #" + styleMode.getLayoutColor() + "; ");

        lightModeLayout.styleProperty().set("-fx-background-color: #" + styleMode.getLayoutColor() + "; ");

        imagesLayout.styleProperty().set("-fx-background-color: #" + styleMode.getLayoutColor() + "; ");

        enterBLayout.styleProperty().set("-fx-background-color: #" + styleMode.getLayoutColor() + "; ");

        parentLayout.styleProperty().set("-fx-background-color: #" + styleMode.getLayoutColor() + "; ");
    }

    private Stage setUpStage(){
        Stage stage = new Stage();
        stage.setTitle("Choosing style mode");
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setOnCloseRequest(e -> {
            if (styleMode instanceof DarkMode)
                new MessageBox(styleMode).messageBox("Alert", "Your mode will be dark mode.");

            else if (styleMode instanceof LightMode)
                new MessageBox(styleMode).messageBox("Alert", "Your mode will be light mode.");
        });
        return stage;
    }

    private Button setUpButton(String text){
        Button button = new Button();

        InnerShadow innerShadow = new InnerShadow();
        innerShadow.setColor(Color.LIGHTSKYBLUE);
        Glow glow = new Glow();
        glow.setLevel(0.2);
        button.setEffect(innerShadow);
        button.setOnMouseEntered(e -> button.setEffect(glow));
        button.setOnMouseExited(e -> button.setEffect(innerShadow));

        button.setText(text);
        button.setPrefHeight(50);
        button.setPrefWidth(265);
        button.setCursor(Cursor.HAND);
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

    private Label setUpLabel(String text){
        Label label = new Label();
        label.setText(text);
        label.setFont(new Font("Cambria", 15));
        label.setStyle("-fx-text-fill: #" + styleMode.getLabelColor() + "; ");
        return label;
    }

    private VBox setUpVBoxLayout(){
        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER_LEFT);
        layout.styleProperty().set("-fx-background-color: #" + styleMode.getLayoutColor() + "; ");
        return layout;
    }

    private HBox setUpImagesLayout(){
        HBox layout = new HBox(10);
        layout.setAlignment(Pos.CENTER_LEFT);
        layout.styleProperty().set("-fx-background-color: #" + styleMode.getLayoutColor() + "; ");
        layout.getChildren().addAll(darkModeLayout, lightModeLayout);
        return layout;
    }

    private HBox setUpEnterBLayout(){
        HBox layout = new HBox();
        layout.setAlignment(Pos.CENTER_RIGHT);
        layout.styleProperty().set("-fx-background-color: #" + styleMode.getLayoutColor() + "; ");
        layout.getChildren().add(enterB);
        return layout;
    }

    private VBox setUpParentLayout(){
        VBox parentLayout = new VBox(10);
        parentLayout.setPadding(new Insets(5,10, 10, 10));
        parentLayout.setAlignment(Pos.CENTER);
        parentLayout.styleProperty().set("-fx-background-color: #" + styleMode.getLayoutColor() + "; ");
        parentLayout.getChildren().addAll(imagesLayout, enterBLayout);
        return parentLayout;
    }

    private void setUpEnterBAction(){
        enterB.setOnAction(e -> this.stage.close());
    }

    private void setUpDarkModeRadioBAction(){
        darkModeRadioB.setOnMouseClicked(e -> {
            styleMode = new DarkMode();
            getNewMode();
        });
    }

    private void setUpLightModeRadioBAction(){
        lightModeRadioB.setOnMouseClicked(e -> {
            styleMode = new LightMode();
            getNewMode();
        });
    }

}
