package RestaurantGUI.UsersGUI;

import RestaurantGUI.ProjectSystem.StyleMode.StyleMode;
import Restaurant.HandlingData.SaveData;
import Restaurant.Properties.Tables;
import Restaurant.Useres.Waiter;
import Restaurant.Vectors.TablesVector;
import RestaurantGUI.ManageUsers.EditUserGUI;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public final class WaiterGUI {

    private StyleMode styleMode;

    private Stage stage;

    private boolean loggingOut = false;

    private TableView<Tables> tablesTable;
    private TableColumn tableNumberCol;
    private TableColumn tableClientName;
    private ObservableList<Tables> tablesObservableList = FXCollections.observableArrayList();

    private Button logOutB;
    private Button editUserB;

    private HBox tablesTableLayout;
    private VBox buttonsLayout;
    private VBox parentLayout;

    private Scene scene;

    public WaiterGUI(StyleMode styleMode){
        this.styleMode = styleMode;
    }

    public void show(Waiter waiter, TablesVector tablesVector, SaveData saver){
        //Stage:
        stage = setUpStage(saver);

        //Table view:
        tableNumberCol = setUpTableColumn("Table number");
        tableClientName = setUpTableColumn("Client name");
        tablesTable = setUpTablesTable();
        loadBookedTables(tablesVector);

        //Buttons:
        editUserB = setUpButtons("Edit my data");
        setUpEditUserBAction(waiter);

        logOutB = setUpButtons("Log out");
        setUpLogOutBAction();

        //Layouts:
        tablesTableLayout = setUpTablesTableLayout();
        buttonsLayout = setUpButtonsLayout();
        parentLayout = setUpParentLayout();

        //Scene:
        scene = new Scene(parentLayout, 800, 500);

        //Show stage:
        stage.setScene(scene);
        stage.showAndWait();
    }

    private Stage setUpStage(SaveData saver){
        Stage stage = new Stage();
        stage.setTitle("Waiter dashboard");
        stage.setResizable(false);
        stage.setOnCloseRequest(e -> {
            if (!loggingOut){
                saver.saveData();
                System.exit(0);
            }
        });
        return stage;
    }

    private TableView setUpTablesTable(){
        TableView<Tables> tableTables = new TableView<>();
        tableTables.setStyle(getTableViewStyle());
        tableTables.setEffect(new DropShadow());

        tableTables.getColumns().addAll(tableNumberCol, tableClientName);
        tableTables.setItems(tablesObservableList);
        tableNumberCol.setCellValueFactory(new PropertyValueFactory<Tables, String>("tableNumber"));
        tableClientName.setCellValueFactory(new PropertyValueFactory<Tables, String>("clientName"));
        return tableTables;
    }

    private String getTableViewStyle(){
        return "-fx-control-inner-background: #" + styleMode.getTableViewColor() + "; ";
    }

    private TableColumn setUpTableColumn(String text){
        TableColumn tableCol = new TableColumn(text);
        tableCol.setPrefWidth(400);
        return tableCol;
    }

    private void loadBookedTables(TablesVector tablesVector){
        TablesVector bookedTablesVector = tablesVector.getReservedTables();
        for (int i = 0; i < bookedTablesVector.getLength(); i++)
            tablesObservableList.add(bookedTablesVector.get(i));
    }

    private Button setUpButtons(String text){
        Button button = new Button();
        button.setText(text);

        InnerShadow innerShadow = new InnerShadow();
        innerShadow.setColor(Color.LIGHTSKYBLUE);
        Glow glow = new Glow();
        glow.setLevel(0.2);
        button.setEffect(innerShadow);
        button.setOnMouseEntered(e -> button.setEffect(glow));
        button.setOnMouseExited(e -> button.setEffect(innerShadow));

        button.setPrefHeight(75);
        button.setPrefWidth(780);
        button.setCursor(Cursor.HAND);
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
        label.setStyle("-fx-text-fill: " + styleMode.getLabelColor() + "; ");
        return label;
    }

    private TextField setUpTextFields(String promptText){
        TextField textField = new TextField();
        textField.setPromptText(promptText);
        textField.setPrefWidth(100);
        textField.setPrefWidth(640);
        textField.setStyle(textFieldStyle());
        return textField;
    }

    private String textFieldStyle(){
        return "-fx-background-color: " + styleMode.getTextFieldColor() + "; " +
                "-fx-background-radius: 50;" +
                "-fx-border-radius: 50;" +
                "-fx-border-color: #000000; -fx-border-width: 0.3px; ";
    }

    private HBox setUpTablesTableLayout(){
        HBox tablesTableLayout = new HBox();
        tablesTableLayout.setAlignment(Pos.CENTER);
        tablesTableLayout.styleProperty().set("-fx-background-color: #addde7; ");
        tablesTableLayout.getChildren().add(tablesTable);
        return tablesTableLayout;
    }

    private VBox setUpButtonsLayout(){
        VBox buttonsLayout = new VBox(10);
        buttonsLayout.setAlignment(Pos.CENTER);
        buttonsLayout.styleProperty().set("-fx-background-color: #" + styleMode.getLayoutColor() + "; ");
        buttonsLayout.getChildren().addAll(editUserB, logOutB);
        return buttonsLayout;
    }

    private VBox setUpParentLayout(){
        VBox parentLayout = new VBox(10);
        parentLayout.setAlignment(Pos.CENTER);
        parentLayout.setPadding(new Insets(10,10,10,10));
        parentLayout.styleProperty().set("-fx-background-color: #" + styleMode.getLayoutColor() + "; ");
        parentLayout.getChildren().addAll(tablesTableLayout, buttonsLayout);
        return parentLayout;
    }

    private void setUpEditUserBAction(Waiter waiter){
        editUserB.setOnAction(e -> new EditUserGUI(styleMode, waiter).show());
    }

    private void setUpLogOutBAction(){
        logOutB.setOnAction(e -> {
            loggingOut = true;
            this.stage.close();
        });
    }
}
