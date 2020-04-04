package RestaurantGUI.UsersGUI.ClientGUI;

import RestaurantGUI.ProjectSystem.StyleMode.StyleMode;
import Restaurant.HandlingData.SaveData;
import Restaurant.Properties.Tables;
import Restaurant.Useres.Clients.Client;
import Restaurant.Vectors.TablesVector;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.control.Label;

public final class ChoosingTableGUI {

    private StyleMode styleMode;

    private boolean thereIsTableBooked = false;

    private Tables table;

    private Stage stage;

    private TableView<Tables> tablesTable;
    private TableColumn tableNumberCol;
    private TableColumn tableNOSeatsCol;
    private TableColumn isSmokingAreaCol;
    private ObservableList<Tables> tablesObservableList = FXCollections.observableArrayList();

    private Label titleLabel;

    private Button logOutB;

    private HBox titleLabelLayout;
    private HBox tablesTableLayout;
    private VBox parentLayout;

    private Scene scene;

    public ChoosingTableGUI(StyleMode styleMode){
        this.styleMode = styleMode;
    }

    public boolean show(TablesVector tablesVector, Client client, SaveData saver){
        //Stage:
        stage = setUpStage(saver);

        //Table View:
        tableNumberCol = setUpTableColumn("Table number", 148);
        tableNOSeatsCol = setUpTableColumn("Number of seats", 148);
        isSmokingAreaCol = setUpTableColumn("Smoking area", 148);
        tablesTable = setUpTablesTable();
        loadUnBookedTables(tablesVector);
        setUpTablesTableOnAction(client);

        //Label:
        titleLabel = setUpTitleLabel();

        //Buttons:
        logOutB = setUpButtons("Log out");
        setUpLogOutBAction();

        //Layouts:
        titleLabelLayout = setUpTitleLabelLayout();
        tablesTableLayout = setUpTablesTableLayout();
        parentLayout = setUpParentLayout();

        //Scene:
        scene = new Scene(parentLayout, 800, 500);

        //Show Stage:
        stage.setScene(scene);
        stage.showAndWait();

        return thereIsTableBooked;
    }

    private Stage setUpStage(SaveData saver){
        Stage stage = new Stage();
        stage.setTitle("Book Table");
        stage.setResizable(false);
        stage.setOnCloseRequest(e -> {
            if (table == null) {
                saver.saveData();
                System.exit(0);
            }
        });
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
        button.setPrefWidth(780);
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

    private Label setUpTitleLabel(){
        Label titleLabel = new Label();
        titleLabel.setText("Please choose a table from the following:");
        titleLabel.setFont(new Font("Cambria", 15));
        titleLabel.setStyle("-fx-text-fill: #" + styleMode.getLabelColor() + "; ");
        return titleLabel;
    }

    private HBox setUpTitleLabelLayout(){
        HBox titleLabelLayout = new HBox();
        titleLabelLayout.setAlignment(Pos.CENTER_LEFT);
        titleLabelLayout.styleProperty().set("-fx-background-color: #" + styleMode.getLayoutColor() + "; ");
        titleLabelLayout.getChildren().addAll(titleLabel);
        return titleLabelLayout;
    }

    private HBox setUpTablesTableLayout(){
        HBox tablesTableLayout = new HBox();
        tablesTableLayout.setAlignment(Pos.CENTER);
        tablesTableLayout.styleProperty().set("-fx-background-color: #" + styleMode.getLayoutColor() + "; ");
        tablesTableLayout.getChildren().addAll(tablesTable);
        return tablesTableLayout;
    }

    private VBox setUpParentLayout(){
        VBox parentLayout = new VBox(10);
        parentLayout.setAlignment(Pos.CENTER_LEFT);
        parentLayout.setPadding(new Insets(10,10,10,10));
        parentLayout.styleProperty().set("-fx-background-color: #" + styleMode.getLayoutColor() + "; ");
        parentLayout.getChildren().addAll(titleLabelLayout, tablesTableLayout, logOutB);
        return parentLayout;
    }

    private TableView setUpTablesTable(){
        TableView<Tables> tablesTable = new TableView<>();
        tablesTable.setPrefHeight(430);
        tablesTable.setPrefWidth(800);
        tablesTable.setCursor(Cursor.HAND);
        tablesTable.setEffect(new DropShadow());
        tablesTable.setStyle(getTableViewStyle());

        tablesTable.getColumns().addAll(tableNumberCol, tableNOSeatsCol,
                isSmokingAreaCol);
        tablesTable.setItems(tablesObservableList);
        tableNumberCol.setCellValueFactory(new PropertyValueFactory<Tables, String>("tableNumber"));
        tableNOSeatsCol.setCellValueFactory(new PropertyValueFactory<Tables, String>("numberOfSeats"));
        isSmokingAreaCol.setCellValueFactory(new PropertyValueFactory<Tables, String>("inSmokingAreas"));
        return tablesTable;
    }

    private String getTableViewStyle(){
        return "-fx-control-inner-background: #" + styleMode.getTableViewColor() + "; ";
    }

    private TableColumn setUpTableColumn(String text, int width){
        TableColumn column = new TableColumn(text);
        column.setPrefWidth(width);
        column.setEditable(false);
        column.setResizable(true);
        column.setStyle("-fx-font-size: 13; ");
        return column;
    }

    private void loadUnBookedTables(TablesVector tablesVector){
        Tables table;
        for (int i = 0; i < tablesVector.getLength(); i++) {
            table = tablesVector.get(i);
            if (!table.isBooked())
                tablesObservableList.add(table);

        }
    }

    private void setUpTablesTableOnAction(Client client){
        tablesTable.setOnMouseClicked(e -> {
            table = tablesTable.getSelectionModel().getSelectedItem();

            if (table != null) {
                table.setClientName(client.getName());
                table.setBooked(true);
                client.setTable(table);
                thereIsTableBooked = true;
                this.stage.close();
            }
        });
    }

    private void setUpLogOutBAction(){
        logOutB.setOnAction(e -> stage.close());
    }

}
