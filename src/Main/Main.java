package Main;

import Restaurant.HandlingData.LoadData;
import Restaurant.HandlingData.SaveData;
import Restaurant.Properties.Food;
import Restaurant.Useres.Clients.Client;
import Restaurant.Useres.Clients.Order;
import Restaurant.Useres.Cooker;
import Restaurant.Useres.Manager;
import Restaurant.Useres.Users;
import Restaurant.Useres.Waiter;
import Restaurant.Vectors.BillsVector;
import Restaurant.Vectors.TablesVector;
import Restaurant.Vectors.UsersVector;
import RestaurantGUI.ProjectSystem.StyleMode.SetStyle;
import RestaurantGUI.ProjectSystem.StyleMode.StyleMode;
import RestaurantGUI.UsersGUI.ClientGUI.ChoosingTableGUI;
import RestaurantGUI.UsersGUI.ClientGUI.ClientGUI;
import RestaurantGUI.LoginGUI.Login;
import RestaurantGUI.UsersGUI.CookerGUI;
import RestaurantGUI.UsersGUI.ManagerGUI;
import RestaurantGUI.UsersGUI.WaiterGUI;
import javafx.application.Application;
import javafx.stage.Stage;

import java.util.Vector;

public class Main extends Application {

    private StyleMode styleMode;
    private Login login;
    private UsersVector usersVector;
    private Food food;
    private TablesVector tablesVector;
    private BillsVector billsVector;
    private Vector<Order> orders;

    @Override
    public void start(Stage primaryStage){
        String path = "src\\Restaurant\\HandlingData\\data.xml";

        //Loading data:
        LoadData loadData = new LoadData(path);
        loadData.loadData();

        styleMode = loadData.getStyleMode();
        usersVector = loadData.getUsers();
        food = loadData.getFood();
        tablesVector = loadData.getTables();
        billsVector = loadData.getBills();
        orders = loadData.getOrders();

        if (styleMode == null)
            styleMode = new SetStyle().show();

        //Initialize data saver:
        SaveData saver = new SaveData(path, styleMode, usersVector, tablesVector, food, billsVector, orders);

        //Go to login user interface:
        login = new Login(styleMode);
        login(saver, orders);
    }

    public void login(SaveData saver, Vector<Order> orders){
        Users user = login.login(usersVector, saver);
        this.styleMode = login.getStyleMode();
        showDashBoard(user, saver, orders);
    }

    private void showDashBoard(Users user, SaveData saver, Vector<Order> orders){
        String role = user.getRole();
        switch (role){
            case "Manager":
                new ManagerGUI(styleMode).managerDashBoard((Manager) user, usersVector, tablesVector, billsVector, saver);
                break;

            case "Cooker":
                new CookerGUI(styleMode).show((Cooker)user, orders, food, saver);
                break;

            case "Waiter":
                new WaiterGUI(styleMode).show((Waiter)user, tablesVector, saver);
                break;

            case "Client":
            case "VipClient":
                if (((Client) user).getTable() == null) {
                    if (new ChoosingTableGUI(styleMode).show(tablesVector, (Client) user, saver))
                        new ClientGUI(styleMode).show((Client) user, food, billsVector, orders, saver);
                }

                else
                    new ClientGUI(styleMode).show((Client) user, food, billsVector, orders, saver);

                break;

            default:
                System.exit(-1);
        }

        login(saver, orders);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
