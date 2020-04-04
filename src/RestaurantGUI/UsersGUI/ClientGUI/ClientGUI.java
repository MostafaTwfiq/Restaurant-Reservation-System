package RestaurantGUI.UsersGUI.ClientGUI;

import RestaurantGUI.ProjectSystem.StyleMode.StyleMode;
import Restaurant.HandlingData.SaveData;
import Restaurant.Properties.Dishes.Dish;
import Restaurant.Properties.Food;
import Restaurant.Useres.Clients.Bills.Bill;
import Restaurant.Useres.Clients.Client;
import Restaurant.Useres.Clients.Order;
import Restaurant.Vectors.BillsVector;
import Restaurant.Vectors.DishesVector;
import RestaurantGUI.ManageUsers.EditUserGUI;
import RestaurantGUI.ProjectSystem.MessageBox;
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

import java.util.Vector;

public final class ClientGUI {

    private StyleMode styleMode;

    private Stage clientStage;

    private boolean loggingOut = false;

    private TableView<Dish> dishesTable;
    private TableColumn dishTypeCol;
    private TableColumn dishNameCol;
    private TableColumn dishPriceCol;
    private TableColumn dishTaxCol;
    private ObservableList<Dish> dishObservableList = FXCollections.observableArrayList();

    private TableView<Dish> selectedDishesTable;
    private TableColumn selectedDishTypeCol;
    private TableColumn selectedDishNameCol;
    private TableColumn selectedDishPriceCol;
    private TableColumn selectedDishTaxCol;
    private ObservableList<Dish> selectedDishObservableList = FXCollections.observableArrayList();

    private TableView<Dish> showSelectedDishesTable;
    private TableColumn showSelectedDishTypeCol;
    private TableColumn showSelectedDishNameCol;
    private TableColumn showSelectedDishPriceCol;
    private TableColumn showSelectedDishTaxCol;

    private Label titleLabel;

    private TextArea billArea;

    private Button orderB;
    private Button editUserB;
    private Button logOutB;
    private Button payTheBillB;

    private HBox titleLabelLayout;
    private HBox tablesLayout;
    private HBox buttonsLayout;
    private HBox billAreaLayout;
    private VBox parentLayout;
    private VBox rootParentLayout;

    private Scene scene;

    public ClientGUI(StyleMode styleMode){
        this.styleMode = styleMode;
    }

    public void show(Client client, Food food, BillsVector billsVector, Vector<Order> orders, SaveData saver){
        //Stage:
        clientStage = setUpStage(saver);

        //Tables view:
        dishTypeCol = setUpTableViewCol("Dish type", 92);
        dishNameCol = setUpTableViewCol("Dish name", 97);
        dishPriceCol = setUpTableViewCol("Dish price", 92);
        dishTaxCol = setUpTableViewCol("Dish tax", 87);
        dishesTable = setUpDishesTable();
        setUpDishesTableDishes(food);
        setUpDishesTableAction();

        selectedDishTypeCol = setUpTableViewCol("Dish type", 92);
        selectedDishNameCol = setUpTableViewCol("Dish name", 97);
        selectedDishPriceCol = setUpTableViewCol("Dish price", 92);
        selectedDishTaxCol = setUpTableViewCol("Dish tax", 87);
        selectedDishesTable = setUpSelectedDishesTable();
        setUpSelectedDishesTableAction();

        showSelectedDishTypeCol = setUpTableViewCol("Dish type", 195);
        showSelectedDishNameCol = setUpTableViewCol("Dish name", 195);
        showSelectedDishPriceCol = setUpTableViewCol("Dish price", 195);
        showSelectedDishTaxCol = setUpTableViewCol("Dish tax", 195);
        showSelectedDishesTable = setUpShowSelectedDishesTable();

        //Label:
        titleLabel = setUpLabel("Click on the dish from the menu to add it, and from the selected dishes to remove it.");

        //Text area:
        billArea = setUpBillArea();

        //Buttons:
        orderB = setUpButtons("Order");
        setUpOrderBAction(client, orders);

        editUserB = setUpButtons("Edit my data");
        setUpEditUserBAction(client);

        logOutB = setUpButtons("Log out");
        setUpLogOutBAction();

        payTheBillB = setUpButtons("Pay the bill");
        setUpPayTheBillBAction(client, billsVector, orders);

        //Layouts:
        titleLabelLayout = setUpTitleLabelLayout();
        tablesLayout = setUpTablesLayout();
        buttonsLayout = setUpButtonsLayout();
        billAreaLayout = setUpBillAreaLayout();
        parentLayout = setUpParentLayout();
        rootParentLayout = setUpRootParentLayout();

        //Scene:
        scene = new Scene(rootParentLayout, 800, 500);

        //ShowStage:
        if (client.getDishes().getLength() != 0) {
            loadSelectedDishes(client);
            showGetBillStage();
        }

        clientStage.setScene(scene);
        clientStage.showAndWait();

    }

    private Stage setUpStage(SaveData saver){
        Stage stage = new Stage();
        stage.setTitle("Client dashboard");
        stage.setResizable(false);
        stage.setOnCloseRequest(e -> {
            if (!loggingOut){
                saver.saveData();
                System.exit(0);
            }
        });
        return stage;
    }

    private TableView setUpDishesTable(){
        TableView<Dish> dishesTable = new TableView<>();
        dishesTable.setPrefWidth(400);
        dishesTable.setCursor(Cursor.HAND);
        dishesTable.setEffect(new DropShadow());
        dishesTable.setStyle(getTableViewStyle());

        dishesTable.getColumns().addAll(dishTypeCol, dishNameCol, dishPriceCol, dishTaxCol);
        dishesTable.setItems(dishObservableList);
        dishTypeCol.setCellValueFactory(new PropertyValueFactory<Dish, String>("dishType"));
        dishNameCol.setCellValueFactory(new PropertyValueFactory<Dish, String>("dishName"));
        dishPriceCol.setCellValueFactory(new PropertyValueFactory<Dish, String>("price"));
        dishTaxCol.setCellValueFactory(new PropertyValueFactory<Dish, String>("taxes"));
        return dishesTable;
    }

    private void setUpDishesTableDishes(Food food){
        for (int i = 0; i < food.getLength(); i++) {
            DishesVector dishes = food.getDishesVector(i);

            for (int j = 0; j < dishes.getLength(); j++)
                dishObservableList.add(dishes.get(j));

        }
    }

    private void setUpDishesTableAction(){
        dishesTable.setOnMouseClicked(e -> {
            Dish dish = dishesTable.getSelectionModel().getSelectedItem();
            if (dish != null)
                selectedDishObservableList.add(dish);
        });
    }

    private TableView setUpSelectedDishesTable(){
        TableView<Dish> selectedDishesTable = new TableView<>();
        selectedDishesTable.setPrefWidth(400);
        selectedDishesTable.setCursor(Cursor.HAND);
        selectedDishesTable.setEffect(new DropShadow());
        selectedDishesTable.setStyle(getTableViewStyle());

        selectedDishesTable.getColumns().addAll(selectedDishTypeCol, selectedDishNameCol,
                selectedDishPriceCol, selectedDishTaxCol);
        selectedDishesTable.setItems(selectedDishObservableList);
        selectedDishTypeCol.setCellValueFactory(new PropertyValueFactory<Dish, String>("dishType"));
        selectedDishNameCol.setCellValueFactory(new PropertyValueFactory<Dish, String>("dishName"));
        selectedDishPriceCol.setCellValueFactory(new PropertyValueFactory<Dish, String>("price"));
        selectedDishTaxCol.setCellValueFactory(new PropertyValueFactory<Dish, String>("taxes"));
        return selectedDishesTable;
    }

    private void setUpSelectedDishesTableAction(){
        selectedDishesTable.setOnMouseClicked(e -> {
            Dish dish = selectedDishesTable.getSelectionModel().getSelectedItem();
            if (dish != null)
                selectedDishObservableList.remove(dish);
        });
    }

    private TableView setUpShowSelectedDishesTable(){
        TableView<Dish> showSelectedDishesTable = new TableView<>();
        showSelectedDishesTable.setPrefWidth(780);
        showSelectedDishesTable.setEffect(new DropShadow());
        showSelectedDishesTable.setStyle(getTableViewStyle());

        showSelectedDishesTable.getColumns().addAll(showSelectedDishTypeCol, showSelectedDishNameCol,
                showSelectedDishPriceCol, showSelectedDishTaxCol);
        showSelectedDishesTable.setItems(selectedDishObservableList);
        showSelectedDishTypeCol.setCellValueFactory(new PropertyValueFactory<Dish, String>("dishType"));
        showSelectedDishNameCol.setCellValueFactory(new PropertyValueFactory<Dish, String>("dishName"));
        showSelectedDishPriceCol.setCellValueFactory(new PropertyValueFactory<Dish, String>("price"));
        showSelectedDishTaxCol.setCellValueFactory(new PropertyValueFactory<Dish, String>("taxes"));
        return showSelectedDishesTable;
    }

    private String getTableViewStyle(){
        return "-fx-control-inner-background: #" + styleMode.getTableViewColor() + "; ";
    }


    private TableColumn setUpTableViewCol(String text, int width){
        TableColumn column = new TableColumn(text);
        column.setPrefWidth(width);
        column.setEditable(false);
        column.setResizable(true);
        column.setStyle("-fx-font-size: 13; ");
        return column;
    }

    private Label setUpLabel(String text){
        Label label = new Label();
        label.setText(text);
        label.setFont(new Font("Cambria", 15));
        label.setStyle("-fx-text-fill: #" + styleMode.getLabelColor() + "; ");
        return label;
    }

    private TextArea setUpBillArea(){
        TextArea billArea = new TextArea();
        billArea.setEditable(false);
        billArea.setEffect(new DropShadow());
        billArea.setPrefHeight(400);
        billArea.setPrefWidth(790);
        billArea.setStyle("-fx-font-size: 20; " +
                "-fx-background-color: #" + styleMode.getTextFieldColor() + "; ");
        return billArea;
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

        button.setPrefHeight(50);
        button.setPrefWidth(300);
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

    private HBox setUpTitleLabelLayout(){
        HBox titleLabelLayout = new HBox(10);
        titleLabelLayout.setAlignment(Pos.CENTER_LEFT);
        titleLabelLayout.styleProperty().set("-fx-background-color: #" + styleMode.getLayoutColor() + "; ");
        titleLabelLayout.getChildren().add(titleLabel);
        return titleLabelLayout;
    }

    private HBox setUpTablesLayout(){
        HBox tablesLayout = new HBox(10);
        tablesLayout.setAlignment(Pos.CENTER);
        tablesLayout.styleProperty().set("-fx-background-color: #" + styleMode.getLayoutColor() + "; ");
        tablesLayout.getChildren().addAll(dishesTable, selectedDishesTable);
        return tablesLayout;
    }

    private HBox setUpButtonsLayout(){
        HBox buttonsLayout = new HBox(20);
        buttonsLayout.setAlignment(Pos.CENTER);
        buttonsLayout.styleProperty().set("-fx-background-color: #" + styleMode.getLayoutColor() + "; ");
        buttonsLayout.getChildren().addAll(logOutB, editUserB, orderB);
        return buttonsLayout;
    }

    private HBox setUpBillAreaLayout(){
        HBox billAreaLayout = new HBox();
        billAreaLayout.setAlignment(Pos.CENTER);
        billAreaLayout.styleProperty().set("-fx-background-color: #" + styleMode.getLayoutColor() + "; ");
        billAreaLayout.getChildren().addAll(billArea);
        return billAreaLayout;
    }

    private VBox setUpParentLayout(){
        VBox parentLayout = new VBox(20);
        parentLayout.setAlignment(Pos.CENTER);
        parentLayout.setPadding(new Insets(10,10,10,10));
        parentLayout.styleProperty().set("-fx-background-color: #" + styleMode.getLayoutColor() + "; ");
        parentLayout.getChildren().addAll(titleLabelLayout, tablesLayout, buttonsLayout);
        return parentLayout;
    }

    private VBox setUpRootParentLayout(){
        VBox rootParentLayout = new VBox(10);
        rootParentLayout.setAlignment(Pos.CENTER);
        rootParentLayout.setPadding(new Insets(10,10,10,10));
        rootParentLayout.styleProperty().set("-fx-background-color: #" + styleMode.getLayoutColor() + "; ");
        rootParentLayout.getChildren().addAll(parentLayout);
        return rootParentLayout;
    }

    private void setUpOrderBAction(Client client, Vector<Order> orders){
        orderB.setOnAction(e -> {
            if (!selectedDishObservableList.isEmpty()){
                for (Dish dish : selectedDishObservableList)
                    client.addDish(dish);

                Order order = new Order(client.getName(), client.getTable().getTableNumber());
                DishesVector orderDishes = new DishesVector(client.getDishes().getLength());

                for (int i = 0; i < client.getDishes().getLength(); i++)
                    orderDishes.add(client.getDishes().get(i));

                order.setDishesVector(orderDishes);
                orders.add(order);
                showGetBillStage();
            }

            else
                new MessageBox(styleMode).messageBox("Alert", "Please choose some dishes.");

        });
    }

    private void loadSelectedDishes(Client client){
        DishesVector dishes = client.getDishes();

        for (int i = 0; i < dishes.getLength(); i++)
            selectedDishObservableList.add(dishes.get(i));

    }

    private void showGetBillStage(){
        tablesLayout.getChildren().clear();
        tablesLayout.getChildren().add(showSelectedDishesTable);
        buttonsLayout.getChildren().clear();
        buttonsLayout.getChildren().addAll(logOutB, editUserB, payTheBillB);
        titleLabel.setText("Your order has been submitted.");
    }

    private void showPrintedBillStage(){
        parentLayout.getChildren().clear();
        parentLayout.getChildren().addAll(titleLabelLayout, billAreaLayout, buttonsLayout);
        buttonsLayout.getChildren().clear();
        buttonsLayout.getChildren().addAll(logOutB, editUserB);
        titleLabel.setText("Your bill:");
    }

    private void setUpPayTheBillBAction(Client client, BillsVector billsVector, Vector<Order> orders){
        payTheBillB.setOnAction(e -> {
            orders.removeIf(order -> order.getTableNumber() == client.getTable().getTableNumber());
            Bill bill = client.payTheBill();
            billsVector.add(bill);
            billArea.setText(bill.toString());
            buttonsLayout.setSpacing(170);
            showPrintedBillStage();
        });
    }

    private void setUpLogOutBAction(){
        logOutB.setOnAction(e -> {
            loggingOut = true;
            this.clientStage.close();
        });
    }

    private void setUpEditUserBAction(Client client){
        editUserB.setOnAction(e -> new EditUserGUI(styleMode, client).show());
    }

}
