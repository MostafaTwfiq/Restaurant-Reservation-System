package RestaurantGUI.UsersGUI;

import RestaurantGUI.ProjectSystem.StyleMode.StyleMode;
import Restaurant.HandlingData.SaveData;
import Restaurant.Properties.Dishes.Dish;
import Restaurant.Properties.Food;
import Restaurant.Properties.Tables;
import Restaurant.Useres.Clients.Order;
import Restaurant.Useres.Cooker;
import Restaurant.Vectors.DishesVector;
import RestaurantGUI.ManageDishes.AddDishGUI;
import RestaurantGUI.ManageDishes.EditDishGUI;
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
import javafx.stage.Stage;

import java.util.Vector;

public final class CookerGUI {

    private StyleMode styleMode;

    private Stage stage;

    private boolean loggingOut = false;

    private TableView<Order> tablesTable;
    private TableColumn tableNumberCol;
    private ObservableList<Order> tablesObservableList = FXCollections.observableArrayList();
    private Order selectedOrder;

    private TableView<Dish> allDishesTable;
    private TableColumn allDishesTypeCol;
    private TableColumn allDishesNameCol;
    private TableColumn allDishesPriceCol;
    private TableColumn allDishesTaxCol;
    private ObservableList<Dish> allDishObservableList = FXCollections.observableArrayList();

    private TableView<Dish> dishesTable;
    private TableColumn dishTypeCol;
    private TableColumn dishNameCol;
    private ObservableList<Dish> dishObservableList = FXCollections.observableArrayList();

    private Button addNewDishB;
    private Button printOrdersB;
    private Button printDishesB;
    private Button logOutB;
    private Button editUserB;

    private HBox tablesLayout;
    private VBox buttonsLayout;
    private VBox parentLayout;

    public CookerGUI(StyleMode styleMode){
        this.styleMode = styleMode;
    }

    public void show(Cooker cooker, Vector<Order> orders, Food food, SaveData saver){
        //Stage:
        stage = setUpStage(saver);

        //Table view:
        tableNumberCol = setUpTableColumn("Table number", 100);
        tablesTable = setUpTablesTable();
        loadBookedTables(orders);
        setUpTablesTableAction();

        dishTypeCol = setUpTableColumn("Dish type", 320);
        dishNameCol = setUpTableColumn("Dish name", 320);
        dishesTable = setUpDishesTable();
        setUpDishesTableAction(orders);

        allDishesTypeCol = setUpTableColumn("Dish type", 92);
        allDishesNameCol = setUpTableColumn("Dish name", 97);
        allDishesPriceCol = setUpTableColumn("Dish price", 92);
        allDishesTaxCol = setUpTableColumn("Dish tax", 87);
        allDishesTable = setUpAllDishesTable();
        setUpAllDishesTableDishes(food);
        setUpAllDishesTableAction(food);

        //Buttons:
        editUserB = setUpButtons("Edit my data");
        setUpEditUserBAction(cooker);

        logOutB = setUpButtons("Log out");
        setUpLogOutBAction();

        addNewDishB = setUpButtons("Add new dish");
        setUpAddNewDishBAction(food);

        printOrdersB = setUpButtons("Print Orders");
        setUpPrintOrdersBAction();

        printDishesB = setUpButtons("Print All dishes");
        setUpPrintDishesBAction();

        //Layouts:
        tablesLayout = setUpTablesLayout();
        buttonsLayout = setUpButtonsLayout();
        parentLayout = setUpParentLayout();

        //Scene:
        Scene scene = new Scene(parentLayout, 800, 500);

        //Show stage:
        stage.setScene(scene);
        stage.showAndWait();
    }

    private Stage setUpStage(SaveData saver){
        Stage stage = new Stage();
        stage.setTitle("Cooker dashboard");
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
        TableView<Order> tableTables = new TableView<>();
        tableTables.setCursor(Cursor.HAND);
        tableTables.setEffect(new DropShadow());
        tableTables.setPrefWidth(100);
        tableTables.setStyle(getTableViewStyle());

        tableTables.getColumns().addAll(tableNumberCol);
        tableTables.setItems(tablesObservableList);
        tableNumberCol.setCellValueFactory(new PropertyValueFactory<Tables, String>("tableNumber"));
        return tableTables;
    }

    private void setUpTablesTableAction(){
        tablesTable.setOnMouseClicked(e -> {
            Order order = tablesTable.getSelectionModel().getSelectedItem();
            if (order != null){
                selectedOrder = order;
                dishObservableList.clear();
                DishesVector dishesVector = order.getDishesVector();

                for (int i = 0; i < dishesVector.getLength(); i++)
                    dishObservableList.add(dishesVector.get(i));
            }
        });
    }

    private TableView setUpDishesTable(){
        TableView<Dish> dishesTable = new TableView<>();
        dishesTable.setPrefWidth(680);
        dishesTable.setCursor(Cursor.HAND);
        dishesTable.setEffect(new DropShadow());
        dishesTable.setStyle(getTableViewStyle());

        dishesTable.getColumns().addAll(dishTypeCol, dishNameCol);
        dishesTable.setItems(dishObservableList);
        dishTypeCol.setCellValueFactory(new PropertyValueFactory<Tables, String>("dishType"));
        dishNameCol.setCellValueFactory(new PropertyValueFactory<Tables, String>("dishName"));
        return dishesTable;
    }

    private void setUpDishesTableAction(Vector<Order> orders){
        dishesTable.setOnMouseClicked(e -> {
            Dish dish = dishesTable.getSelectionModel().getSelectedItem();
            if (dish != null) {
                dishObservableList.remove(dish);
                DishesVector orderDishes = selectedOrder.getDishesVector();
                orderDishes.delete(orderDishes.find(dish.getDishName()));
                if (dishObservableList.isEmpty()) {
                    orders.remove(selectedOrder);
                    tablesObservableList.remove(selectedOrder);
                    selectedOrder = null;
                }
            }
        });
    }

    private TableView setUpAllDishesTable(){
        TableView<Dish> allDishesTable = new TableView<>();
        allDishesTable.setPrefWidth(780);
        allDishesTable.setCursor(Cursor.HAND);
        allDishesTable.setEffect(new DropShadow());
        allDishesTable.setStyle(getTableViewStyle());

        allDishesTable.getColumns().addAll(allDishesTypeCol, allDishesNameCol, allDishesPriceCol, allDishesTaxCol);
        allDishesTable.setItems(allDishObservableList);
        allDishesTypeCol.setCellValueFactory(new PropertyValueFactory<Dish, String>("dishType"));
        allDishesNameCol.setCellValueFactory(new PropertyValueFactory<Dish, String>("dishName"));
        allDishesPriceCol.setCellValueFactory(new PropertyValueFactory<Dish, String>("price"));
        allDishesTaxCol.setCellValueFactory(new PropertyValueFactory<Dish, String>("taxes"));
        return allDishesTable;
    }

    private void setUpAllDishesTableAction(Food food){
        allDishesTable.setOnMouseClicked(e -> {
            Dish dish = allDishesTable.getSelectionModel().getSelectedItem();
            if (dish != null) {
                if (new EditDishGUI(styleMode, dish, food).show())
                    setUpAllDishesTableDishes(food);
            }
        });
    }

    private void setUpAllDishesTableDishes(Food food){
        allDishObservableList.clear();
        for (int i = 0; i < food.getLength(); i++) {
            DishesVector dishes = food.getDishesVector(i);
            for (int j = 0; j < dishes.getLength(); j++)
                allDishObservableList.add(dishes.get(j));
        }
    }

    private TableColumn setUpTableColumn(String text, int width){
        TableColumn tableCol = new TableColumn(text);
        tableCol.setPrefWidth(width);
        return tableCol;
    }

    private String getTableViewStyle(){
        return "-fx-control-inner-background: #" + styleMode.getTableViewColor() + "; ";
    }

    private void loadBookedTables(Vector<Order> orders){
        for (Order order : orders)
            tablesObservableList.add(order);
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

    private HBox setUpTablesLayout(){
        HBox tablesLayout = new HBox();
        tablesLayout.setAlignment(Pos.CENTER);
        tablesLayout.styleProperty().set("-fx-background-color: #" + styleMode.getLayoutColor() + "; ");
        tablesLayout.getChildren().addAll(tablesTable, dishesTable);
        return tablesLayout;
    }

    private VBox setUpButtonsLayout(){
        VBox buttonsLayout = new VBox(10);
        buttonsLayout.setAlignment(Pos.CENTER);
        buttonsLayout.styleProperty().set("-fx-background-color: #" + styleMode.getLayoutColor() + "; ");
        buttonsLayout.getChildren().addAll(printDishesB, editUserB, logOutB);
        return buttonsLayout;
    }

    private VBox setUpParentLayout(){
        VBox parentLayout = new VBox(10);
        parentLayout.setAlignment(Pos.CENTER);
        parentLayout.setPadding(new Insets(10,10,10,10));
        parentLayout.styleProperty().set("-fx-background-color: #" + styleMode.getLayoutColor() + "; ");
        parentLayout.getChildren().addAll(tablesLayout, buttonsLayout);
        return parentLayout;
    }

    private void setUpPrintOrdersBAction(){
        printOrdersB.setOnAction(e -> showMainStage());
    }

    private void setUpPrintDishesBAction(){
        printDishesB.setOnAction(e -> showPrintDishesStage());
    }

    private void setUpAddNewDishBAction(Food food){
        addNewDishB.setOnAction(e -> {
            if (new AddDishGUI(styleMode, food).show())
                setUpAllDishesTableDishes(food);
        });
    }

    private void setUpEditUserBAction(Cooker cooker){
        editUserB.setOnAction(e -> {
          new EditUserGUI(styleMode, cooker).show();
        });
    }

    private void showMainStage(){
        buttonsLayout.getChildren().clear();
        buttonsLayout.getChildren().addAll(printDishesB, editUserB, logOutB);
        tablesLayout.getChildren().clear();
        tablesLayout.getChildren().addAll(tablesTable, dishesTable);
        parentLayout.getChildren().clear();
        parentLayout.getChildren().addAll(tablesLayout, buttonsLayout);
    }

    private void showPrintDishesStage(){
        buttonsLayout.getChildren().clear();
        buttonsLayout.getChildren().addAll(printOrdersB, addNewDishB, logOutB);
        tablesLayout.getChildren().clear();
        tablesLayout.getChildren().add(allDishesTable);
        parentLayout.getChildren().clear();
        parentLayout.getChildren().addAll(tablesLayout, buttonsLayout);
    }

    private void setUpLogOutBAction(){
        logOutB.setOnAction(e -> {
            loggingOut = true;
            this.stage.close();
        });
    }
}