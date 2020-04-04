package RestaurantGUI.UsersGUI;

import RestaurantGUI.ProjectSystem.StyleMode.StyleMode;
import Restaurant.HandlingData.SaveData;
import Restaurant.Properties.Dishes.Dish;
import Restaurant.Properties.Tables;
import Restaurant.Useres.Clients.Bills.Bill;
import Restaurant.Useres.Clients.Bills.EGPCurrency;
import Restaurant.Useres.Manager;
import Restaurant.Useres.Users;
import Restaurant.Vectors.BillsVector;
import Restaurant.Vectors.DishesVector;
import Restaurant.Vectors.TablesVector;
import Restaurant.Vectors.UsersVector;
import RestaurantGUI.ManageTables.AddTableGUI;
import RestaurantGUI.ManageTables.EditTableGUI;
import RestaurantGUI.ManageUsers.AddUserGUI;
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
import javafx.scene.effect.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public final class ManagerGUI {

    private StyleMode styleMode;

    //Stage:
    private Stage managerStage;

    //Boolean:
    private boolean loggingOut = false;

    //Tables view:
    private TableView<Users> usersTable;
    private TableColumn nameCol;
    private TableColumn userNameCol;
    private TableColumn passwordCol;
    private TableColumn roleCol;
    private ObservableList<Users> usersObservableList = FXCollections.observableArrayList();

    private TableView<Bill> billsTable;
    private TableColumn billClientNameCol;
    private TableColumn billDateCol;
    private TableColumn billAmountCol;
    private ObservableList<Bill> billsObservableList = FXCollections.observableArrayList();

    private TableView<Dish> dishesTable;
    private TableColumn dishTypeCol;
    private TableColumn dishNameCol;
    private TableColumn dishPriceCol;
    private TableColumn dishTaxCol;
    private ObservableList<Dish> billDishesObservableList = FXCollections.observableArrayList();

    private TableView<Tables> tablesTable;
    private TableColumn tableNumberCol;
    private TableColumn tableNOSeatsCol;
    private TableColumn isSmokingAreaCol;
    private TableColumn isBookedCol;
    private TableColumn tableClientNameCol;
    private ObservableList<Tables> tablesObservableList = FXCollections.observableArrayList();

    //Buttons:
    private Button printClientsB;
    private Button printVipClientsB;
    private Button printManagersB;
    private Button printCookersB;
    private Button printWaitersB;
    private Button printTablesB;
    private Button printBillsB;
    private Button printTodayBillsB;
    private Button addNewUserB;
    private Button addNewTableB;
    private Button logOutB;

    //Layouts:
    private HBox usersTableLayout;
    private HBox billsTableLayout;
    private HBox tablesTableLayout;
    private GridPane buttonsPane;
    private VBox parentLayout;

    //Scene:
    private Scene scene;

    public ManagerGUI(StyleMode styleMode){
        this.styleMode = styleMode;
    }

    public void managerDashBoard(Manager manager, UsersVector usersVector,
                                 TablesVector tablesVector, BillsVector bills, SaveData saver){
        //Stage:
        managerStage = setUpManagerStage(saver);

        //LabelView:
        nameCol = setUpTableColumn("Name", 195);
        userNameCol = setUpTableColumn("User name", 195);
        passwordCol = setUpTableColumn("Password", 195);
        roleCol = setUpTableColumn("Role", 195);
        usersTable = setUpUsersTable();
        setUpUsersTableAction(usersVector);

        billClientNameCol = setUpTableColumn("Client name", 133);
        billDateCol = setUpTableColumn("Date", 133);
        billAmountCol = setUpTableColumn("Amount", 133);
        billsTable = setUpBillsTable();
        setUpBillsTableAction();

        dishTypeCol = setUpTableColumn("Dish type", 95);
        dishNameCol = setUpTableColumn("Dish name", 95);
        dishPriceCol = setUpTableColumn("Dish price", 95);
        dishTaxCol = setUpTableColumn("Tax", 95);
        dishesTable = setUpDishesTable();

        tableNumberCol = setUpTableColumn("Table number", 148);
        tableNOSeatsCol = setUpTableColumn("Number of seats", 148);
        isSmokingAreaCol = setUpTableColumn("Smoking area", 148);
        isBookedCol = setUpTableColumn("Booked", 148);
        tableClientNameCol = setUpTableColumn("Client name", 148);
        tablesTable = setUpTablesTable();
        setUpTablesTableAction(tablesVector);

        //Buttons:
        printClientsB = setUpButton("Print Clients");
        setUpPrintClientsBAction(usersVector);

        printVipClientsB = setUpButton("Print Vip Clients");
        setUpPrintVipClientsBAction(usersVector);

        printManagersB = setUpButton("Print Managers");
        setUpPrintManagersBAction(usersVector);

        printCookersB = setUpButton("Print Cookers");
        setUpPrintCookersBAction(usersVector);

        printWaitersB = setUpButton("Print Waiters");
        setUpPrintWaitersBAction(usersVector);

        printTablesB = setUpButton("Print Tables");
        setUpPrintTablesBAction(tablesVector);

        printBillsB = setUpButton("Print Bills");
        setUpPrintBillsBAction(bills);

        printTodayBillsB = setUpButton("Print today's bills");
        setUpPrintTodayBillsB(bills);

        addNewUserB = setUpButton("Add new user");
        setUpAddNewUserBAction(usersVector);

        addNewTableB = setUpButton("Add new table");
        setUpAddNewTableBAction(tablesVector);

        logOutB = setUpButton("Log out");
        setUpLogOutBAction();

        //Layouts:
        usersTableLayout = setUpUsersTableLayout();
        billsTableLayout = setUpBillsTableLayout();
        tablesTableLayout = setUpTablesTableLayout();
        buttonsPane = setUpButtonsPane();
        parentLayout = setUpParentLayout();

        //Scene:
        scene = new Scene(parentLayout, 800, 500);

        //Show Stage:
        managerStage.setScene(scene);
        managerStage.showAndWait();
    }

    private Stage setUpManagerStage(SaveData saver){
        Stage managerStage = new Stage();
        managerStage.setTitle("Manager dashboard");
        managerStage.setResizable(false);
        managerStage.setOnCloseRequest(e -> {
            if (!loggingOut) {
                saver.saveData();
                System.exit(0);
            }
        });
        return managerStage;
    }

    private TableColumn setUpTableColumn(String text, int width){
        TableColumn column = new TableColumn(text);
        column.setPrefWidth(width);
        column.setEditable(false);
        column.setResizable(true);
        column.setStyle("-fx-font-size: 13; ");
        return column;
    }

    private TableView setUpUsersTable(){
        TableView<Users> usersTable = new TableView<>();
        usersTable.setPrefWidth(780);
        usersTable.getColumns().addAll(nameCol, userNameCol, passwordCol, roleCol);
        usersTable.setCursor(Cursor.HAND);
        usersTable.setEffect(new DropShadow());
        usersTable.setStyle(getTableViewStyle());

        usersTable.setItems(usersObservableList);
        nameCol.setCellValueFactory(new PropertyValueFactory<Users,String>("name"));
        userNameCol.setCellValueFactory(new PropertyValueFactory<Users,String>("userName"));
        passwordCol.setCellValueFactory(new PropertyValueFactory<Users,String>("password"));
        roleCol.setCellValueFactory(new PropertyValueFactory<Users,String>("role"));
        return usersTable;
    }

    private TableView setUpBillsTable(){
        TableView<Bill> billsTable = new TableView<>();
        billsTable.setPrefWidth(400);
        billsTable.setCursor(Cursor.HAND);
        billsTable.setEffect(new DropShadow());
        billsTable.setStyle(getTableViewStyle());
        billsTable.getColumns().addAll(billClientNameCol, billDateCol, billAmountCol);

        billsTable.setItems(billsObservableList);
        billClientNameCol.setCellValueFactory(new PropertyValueFactory<Bill, String>("clientName"));
        billDateCol.setCellValueFactory(new PropertyValueFactory<Bill, String>("date"));
        billAmountCol.setCellValueFactory(new PropertyValueFactory<Bill, String>("bill"));
        return billsTable;
    }

    private TableView setUpDishesTable(){
        TableView<Dish> dishesTable = new TableView<>();
        dishesTable.setPrefWidth(380);
        dishesTable.setEffect(new DropShadow());
        dishesTable.setStyle(getTableViewStyle());
        dishesTable.getColumns().addAll(dishTypeCol, dishNameCol, dishPriceCol, dishTaxCol);

        dishesTable.setItems(billDishesObservableList);
        dishTypeCol.setCellValueFactory(new PropertyValueFactory<Dish, String>("dishType"));
        dishNameCol.setCellValueFactory(new PropertyValueFactory<Dish, String>("dishName"));
        dishPriceCol.setCellValueFactory(new PropertyValueFactory<Dish, String>("price"));
        dishTaxCol.setCellValueFactory(new PropertyValueFactory<Dish, String>("taxes"));
        return dishesTable;
    }

    private TableView setUpTablesTable(){
        TableView<Tables> tablesTable = new TableView<>();
        tablesTable.setPrefWidth(780);
        tablesTable.setCursor(Cursor.HAND);
        tablesTable.setEffect(new DropShadow());
        tablesTable.setStyle(getTableViewStyle());
        tablesTable.getColumns().addAll(tableNumberCol, tableNOSeatsCol,
                isSmokingAreaCol, isBookedCol, tableClientNameCol);

        tablesTable.setItems(tablesObservableList);
        tableNumberCol.setCellValueFactory(new PropertyValueFactory<Tables, String>("tableNumber"));
        tableNOSeatsCol.setCellValueFactory(new PropertyValueFactory<Tables, String>("numberOfSeats"));
        isBookedCol.setCellValueFactory(new PropertyValueFactory<Tables, String>("booked"));
        isSmokingAreaCol.setCellValueFactory(new PropertyValueFactory<Tables, String>("inSmokingAreas"));
        tableClientNameCol.setCellValueFactory(new PropertyValueFactory<Tables, String>("clientName"));
        return tablesTable;
    }

    private void setUpTablesTableAction(TablesVector tablesVector){
        tablesTable.setOnMouseClicked(e -> {
            Tables table = tablesTable.getSelectionModel().getSelectedItem();
            if (table != null) {
                if (!table.isBooked()) {
                    new EditTableGUI(styleMode, table, tablesVector).show();
                    printTables(tablesVector);
                }

                else
                    new MessageBox(styleMode).messageBox("Alert", "This table is booked you can't edit it.");
            }
        });
    }

    private String getTableViewStyle(){
        return "-fx-control-inner-background: #" + styleMode.getTableViewColor() + "; ";
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

    private HBox setUpUsersTableLayout(){
        HBox usersTableLayout = new HBox(10);
        usersTableLayout.setAlignment(Pos.CENTER);
        usersTableLayout.styleProperty().set("-fx-background-color: #" + styleMode.getLayoutColor() + "; ");
        usersTableLayout.getChildren().add(usersTable);
        return usersTableLayout;
    }

    private HBox setUpBillsTableLayout(){
        HBox billsTableLayout = new HBox(5);
        billsTableLayout.setAlignment(Pos.CENTER);
        billsTableLayout.styleProperty().set("-fx-background-color: #" + styleMode.getLayoutColor() + "; ");
        billsTableLayout.getChildren().addAll(billsTable, dishesTable);
        return billsTableLayout;
    }

    private HBox setUpTablesTableLayout(){
        HBox tablesTableLayout = new HBox(5);
        tablesTableLayout.setAlignment(Pos.CENTER);
        tablesTableLayout.styleProperty().set("-fx-background-color: #" + styleMode.getLayoutColor() + "; ");
        tablesTableLayout.getChildren().addAll(tablesTable);
        return tablesTableLayout;
    }

    private GridPane setUpButtonsPane(){
        GridPane buttonsPane = new GridPane();
        buttonsPane.setHgap(10);
        buttonsPane.setVgap(10);
        buttonsPane.setAlignment(Pos.CENTER);
        buttonsPane.styleProperty().set("-fx-background-color: #" + styleMode.getLayoutColor() + "; ");
        GridPane.setConstraints(printClientsB, 0, 0);
        GridPane.setConstraints(printVipClientsB, 1, 0);
        GridPane.setConstraints(printManagersB, 2, 0);
        GridPane.setConstraints(printCookersB, 0, 1);
        GridPane.setConstraints(printWaitersB, 1, 1);
        GridPane.setConstraints(printBillsB, 2, 1);
        GridPane.setConstraints(printTablesB, 0, 2);
        GridPane.setConstraints(addNewUserB, 1, 2);
        GridPane.setConstraints(addNewTableB, 1, 2);
        GridPane.setConstraints(printTodayBillsB, 1, 2);
        GridPane.setConstraints(logOutB, 2, 2);
        buttonsPane.getChildren().addAll(printClientsB, printVipClientsB, printManagersB,
                printCookersB, printWaitersB, printBillsB,
                printTablesB, addNewUserB, logOutB);
        return buttonsPane;

    }

    private VBox setUpParentLayout(){
        VBox parentLayout = new VBox(10);
        parentLayout.setAlignment(Pos.CENTER);
        parentLayout.setPadding(new Insets(10,10,10,10));
        parentLayout.styleProperty().set("-fx-background-color: #" + styleMode.getLayoutColor() + "; ");
        parentLayout.getChildren().addAll(usersTableLayout, buttonsPane);
        return parentLayout;
    }

    private void showAddNewUserB(){
        if (buttonsPane.getChildren().indexOf(addNewTableB) != -1){
            buttonsPane.getChildren().removeAll(addNewTableB);
            buttonsPane.getChildren().add(addNewUserB);
        }

        else if (buttonsPane.getChildren().indexOf(printTodayBillsB) != -1) {
            buttonsPane.getChildren().removeAll(printTodayBillsB);
            buttonsPane.getChildren().add(addNewUserB);
        }
    }

    private void setUpPrintClientsBAction(UsersVector usersVector){
        printClientsB.setOnAction(e -> {
            showAddNewUserB();
            showUsersTableLayout();
            printUsers(usersVector.getClientsVector());
        });
    }

    private void setUpPrintVipClientsBAction(UsersVector usersVector){
        printVipClientsB.setOnAction(e -> {
            showAddNewUserB();
            showUsersTableLayout();
            printUsers(usersVector.getVipClientsVector());
        });
    }

    private void setUpPrintManagersBAction(UsersVector usersVector){
        printManagersB.setOnAction(e -> {
            showAddNewUserB();
            showUsersTableLayout();
            printUsers(usersVector.getMangersVector());
        });
    }

    private void setUpPrintCookersBAction(UsersVector usersVector){
        printCookersB.setOnAction(e -> {
            showAddNewUserB();
            showUsersTableLayout();
            printUsers(usersVector.getCookersVector());
        });
    }

    private void setUpPrintWaitersBAction(UsersVector usersVector){
        printWaitersB.setOnAction(e -> {
            showAddNewUserB();
            showUsersTableLayout();
            printUsers(usersVector.getWaitersVector());
        });
    }

    private void printUsers(UsersVector usersToPrintVector){
        usersObservableList.clear();
        for (int i = 0; i < usersToPrintVector.getLength(); i++)
            usersObservableList.add(usersToPrintVector.get(i));
    }

    private void setUpPrintTodayBillsB(BillsVector billsVector){
        printTodayBillsB.setOnAction(e -> {
            parentLayout.getChildren().clear();
            parentLayout.getChildren().addAll(billsTableLayout, buttonsPane);
            printTodayBills(billsVector);
        });
    }

    private void printTodayBills(BillsVector billsVector){
        float totalTodayBills = 0;
        Bill bill;
        BillsVector todayBills = billsVector.getBillsOfToday();
        billDishesObservableList.clear();
        billsObservableList.clear();

        for (int i = todayBills.getLength() - 1; i >= 0 ; i--) {
            bill = todayBills.get(i);
            totalTodayBills += bill.getFloatBill();
            billsObservableList.add(bill);
        }

        new MessageBox(styleMode).messageBox("Statistics",
                "Total profit of today is " + String.format("%.2f", totalTodayBills) + new EGPCurrency().toString());
    }

    private void setUpPrintBillsBAction(BillsVector billsVector){
        printBillsB.setOnAction(e -> {
            if (buttonsPane.getChildren().indexOf(addNewTableB) != -1){
                buttonsPane.getChildren().removeAll(addNewTableB);
                buttonsPane.getChildren().add(printTodayBillsB);
            }

            else if (buttonsPane.getChildren().indexOf(addNewUserB) != -1) {
                buttonsPane.getChildren().removeAll(addNewUserB);
                buttonsPane.getChildren().add(printTodayBillsB);
            }
            parentLayout.getChildren().clear();
            parentLayout.getChildren().addAll(billsTableLayout, buttonsPane);
            printBills(billsVector);
        });
    }

    private void printBills(BillsVector billsVector){
        billDishesObservableList.clear();
        billsObservableList.clear();
        for (int i = billsVector.getLength() - 1; i >= 0 ; i--)
            billsObservableList.add(billsVector.get(i));
    }

    private void setUpPrintTablesBAction(TablesVector tablesVector){
        printTablesB.setOnAction(e -> {
            parentLayout.getChildren().clear();
            parentLayout.getChildren().addAll(tablesTableLayout, buttonsPane);

            if (buttonsPane.getChildren().indexOf(addNewUserB) != -1) {
                buttonsPane.getChildren().removeAll(addNewUserB);
                buttonsPane.getChildren().add(addNewTableB);
            }

            else if (buttonsPane.getChildren().indexOf(printTodayBillsB) != -1) {
                buttonsPane.getChildren().removeAll(printTodayBillsB);
                buttonsPane.getChildren().add(addNewTableB);
            }
            printTables(tablesVector);
        });
    }

    private void printTables(TablesVector tablesVector){
        tablesObservableList.clear();

        for (int i = 0; i < tablesVector.getLength(); i++)
            tablesObservableList.add(tablesVector.get(i));
    }

    private void showUsersTableLayout(){
        parentLayout.getChildren().clear();
        parentLayout.getChildren().addAll(usersTableLayout, buttonsPane);
    }

    private void setUpBillsTableAction(){
        billsTable.setOnMouseClicked(e -> {
            Bill bill = billsTable.getSelectionModel().getSelectedItem();
            if (bill != null)
                printBillDishes(bill.getDishes());
        });
    }

    private void printBillDishes(DishesVector billDishes){
        billDishesObservableList.clear();
        for (int i = 0; i < billDishes.getLength(); i++)
            billDishesObservableList.add(billDishes.get(i));
    }

    private void setUpUsersTableAction(UsersVector usersVector){
        usersTable.setOnMouseClicked(e -> {
            Users user = usersTable.getSelectionModel().getSelectedItem();

            if (user != null)
                new EditUserGUI(styleMode, user, usersVector, usersObservableList).show();
        });
    }

    private void setUpLogOutBAction(){
        logOutB.setOnAction(e -> {
            loggingOut = true;
            this.managerStage.close();
        });
    }

    private void setUpAddNewUserBAction(UsersVector usersVector){
        addNewUserB.setOnAction(e -> new AddUserGUI(styleMode, usersVector, usersObservableList).show());
    }

    private void setUpAddNewTableBAction(TablesVector tablesVector){
        addNewTableB.setOnAction(e -> {
            new AddTableGUI(styleMode, tablesVector).show();
            printTables(tablesVector);
        });
    }
}