package Restaurant.HandlingData;

import Restaurant.Properties.Dishes.Dish;
import Restaurant.Useres.Clients.Order;
import RestaurantGUI.ProjectSystem.StyleMode.DarkMode;
import RestaurantGUI.ProjectSystem.StyleMode.LightMode;
import RestaurantGUI.ProjectSystem.StyleMode.StyleMode;
import Restaurant.Properties.*;
import Restaurant.Properties.Dishes.AppetizerDish;
import Restaurant.Properties.Dishes.DesertDish;
import Restaurant.Properties.Dishes.MainCourseDish;
import Restaurant.Useres.Clients.Bills.Bill;
import Restaurant.Useres.Clients.Client;
import Restaurant.Useres.Clients.VipClient;
import Restaurant.Useres.Cooker;
import Restaurant.Useres.Manager;
import Restaurant.Useres.Waiter;
import Restaurant.Vectors.BillsVector;
import Restaurant.Vectors.DishesVector;
import Restaurant.Vectors.TablesVector;
import Restaurant.Vectors.UsersVector;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.util.Vector;

public final class LoadData {

    private String path;
    private StyleMode styleMode;
    private UsersVector users;
    private BillsVector bills;
    private TablesVector tables;
    private Food food;
    private Vector<Order> orders;

    public LoadData(String path){
        this.path = path;
        this.styleMode  = null;
        users = new UsersVector(25);
        bills = new BillsVector(25);
        tables = new TablesVector(25);
        food = new Food(3, 25);
        orders = new Vector<>();
    }

    public StyleMode getStyleMode() {
        return styleMode;
    }

    public UsersVector getUsers() {
        return users;
    }

    public BillsVector getBills() {
        return bills;
    }

    public TablesVector getTables() {
        return tables;
    }

    public Food getFood() {
        return food;
    }

    public String getPath(){
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Vector<Order> getOrders() {
        return orders;
    }

    public void loadData(){
        try {
            DocumentBuilderFactory DBF = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = DBF.newDocumentBuilder();
            Document document = documentBuilder.parse(path);

            NodeList styleList = document.getElementsByTagName("style");
            NodeList usersList = document.getElementsByTagName("user");
            NodeList tablesList = document.getElementsByTagName("table");
            NodeList dishesList = document.getElementsByTagName("dish");
            NodeList billsList = document.getElementsByTagName("bill");
            NodeList ordersList = document.getElementsByTagName("order");

            loadStyle(styleList);
            loadTables(tablesList);
            loadUsers(usersList);
            loadDishes(dishesList);
            loadBills(billsList);
            loadOrders(ordersList);

            this.tables.sortTables();

        } catch (Exception e) {
            System.out.println("There is an error occurred while loading data.");
        }
    }

    private void loadStyle(NodeList styleList){
        Node node;
        Element element;
        String styleType;

        node = styleList.item(0);
        if (node != null && node.getNodeType() == Node.ELEMENT_NODE) {
            element = (Element) node;
            styleType = element.getElementsByTagName("styleType").item(0).getTextContent();
            styleMode = getStyleObject(styleType);
        }
    }

    private StyleMode getStyleObject(String styleType){
        switch (styleType) {
            case "DarkMode":
                return new DarkMode();

            case "LightMode":
                return new LightMode();
        }
        return null;
    }

    private void loadUsers(NodeList usersList){
        Node node;
        Element element;
        String name, userName, password, role;

        for (int i = 0; i < usersList.getLength(); i++) {
            node = usersList.item(i);
            if (node != null &&  node.getNodeType() == Node.ELEMENT_NODE) {
                element = (Element) node;

                role = element.getElementsByTagName("role").item(0).getTextContent();
                name = element.getElementsByTagName("name").item(0).getTextContent();
                userName = element.getElementsByTagName("username").item(0).getTextContent();
                password = element.getElementsByTagName("password").item(0).getTextContent();

                checkTypeOfRole(name, userName, password, role, element);

                if (role.equals("VipClient") || role.equals("Client")) {
                    NodeList clientDishes = element.getElementsByTagName("ClientDish");
                    loadClientDishes(clientDishes, i);

                    NodeList clientTable = element.getElementsByTagName("client_table");
                    loadTable(clientTable, i);
                }

            }
        }
    }

    private void checkTypeOfRole(String name, String userName,
                                 String password, String role,
                                 Element element){
        switch (role) {
            case "Manager":
                users.add(new Manager(name, userName, password));
                break;
            case "Cooker":
                users.add(new Cooker(name, userName, password));
                break;
            case "Waiter":
                users.add(new Waiter(name, userName, password));
                break;
            case "Client":
                users.add(new Client(name, userName, password));
                break;
            case "VipClient":
                String discount = element.getElementsByTagName("discount").item(0).getTextContent();
                users.add(new VipClient(name, userName, password, Integer.parseInt(discount)));
                break;
        }
    }

    private void loadTable(NodeList clientTable, int userIndex){
        Node node;
        Element element;
        String tableNumber;

        node = clientTable.item(0);

        if (node != null && node.getNodeType() == Node.ELEMENT_NODE) {
            element = (Element) node;
            tableNumber = element.getElementsByTagName("tableNumber").item(0).getTextContent();

            if (!tableNumber.equals("")) {
                ((Client) users.get(userIndex)).setTable(tables.get(tables.find(Integer.parseInt(tableNumber))));
                ((Client) users.get(userIndex)).getTable().setBooked(true);
                ((Client) users.get(userIndex)).getTable().setClientName((users.get(userIndex)).getName());
            }

            else
                ((Client) users.get(userIndex)).setTable(null);

        }

    }

    private void loadClientDishes(NodeList clientDishes, int userIndex){
        Node node;
        DishesVector dishesVector = new DishesVector(clientDishes.getLength());

        for (int i = 0; i < clientDishes.getLength(); i++) {
            node = clientDishes.item(i);

            if (node != null &&  node.getNodeType() == Node.ELEMENT_NODE)
                dishesVector.add(getDishObject(node));

        }

        ((Client) users.get(userIndex)).setDishes(dishesVector);
    }

    private void loadTables(NodeList tablesList){
        Node node;
        Element element;
        String tableNumber, numberOfSeats, smokingArea;

        for (int i = 0; i < tablesList.getLength(); i++) {
            node = tablesList.item(i);

            if (node != null &&  node.getNodeType() == Node.ELEMENT_NODE) {
                element = (Element) node;
                tableNumber = element.getElementsByTagName("number").item(0).getTextContent();
                numberOfSeats = element.getElementsByTagName("number_of_seats").item(0).getTextContent();
                smokingArea = element.getElementsByTagName("smoking").item(0).getTextContent();

                tables.add(new Tables(Integer.parseInt(tableNumber), Integer.parseInt(numberOfSeats),
                        smokingArea.equals("true")));

            }

        }
    }

    private void loadDishes(NodeList dishesList){
        Node node;

        for (int i = 0; i < dishesList.getLength(); i++) {
            node = dishesList.item(i);

            if (node != null &&  node.getNodeType() == Node.ELEMENT_NODE) {
                Dish dish = getDishObject(node);

                switch (dish.getDishType()) {
                    case "main_course":
                        food.addDish(dish, 0);
                        break;
                    case "appetizer":
                        food.addDish(dish, 1);
                        break;
                    case "desert":
                        food.addDish(dish, 2);
                        break;
                }

            }

        }

    }

    private void loadBills(NodeList billsList){
        Node node;
        Element element;
        NodeList billDishesList;
        String clientName, date, bill;
        DishesVector billDishes;

        for (int i = 0; i < billsList.getLength(); i++) {
            node = billsList.item(i);
            if (node != null &&  node.getNodeType() == Node.ELEMENT_NODE) {
                billDishes = new DishesVector(25);
                element = (Element) node;
                clientName = element.getElementsByTagName("client_name").item(0).getTextContent();
                date = element.getElementsByTagName("date").item(0).getTextContent();
                bill = element.getElementsByTagName("client_bill").item(0).getTextContent();
                billDishesList = element.getElementsByTagName("billDish");
                getBillDishes(billDishesList, billDishes);
                bills.add(new Bill(clientName, date, Float.parseFloat(bill), billDishes));
            }
        }
    }

    private void getBillDishes(NodeList billDishesList, DishesVector billDishes){
        Node node;

        for (int i = 0; i < billDishesList.getLength(); i++) {
            node = billDishesList.item(i);

            if (node != null &&  node.getNodeType() == Node.ELEMENT_NODE)
                billDishes.add(getDishObject(node));

        }
    }


    private void loadOrders(NodeList ordersList){
        Node node;
        Element element;
        NodeList orderDishesList;
        String clientName, tableNumber;
        DishesVector orderDishes;

        for (int i = 0; i < ordersList.getLength(); i++) {
            node = ordersList.item(i);
            if (node != null &&  node.getNodeType() == Node.ELEMENT_NODE) {
                orderDishes = new DishesVector(25);
                element = (Element) node;
                clientName = element.getElementsByTagName("client_name").item(0).getTextContent();
                tableNumber = element.getElementsByTagName("tableNumber").item(0).getTextContent();

                orderDishesList = element.getElementsByTagName("orderDish");
                getOrderDishes(orderDishesList, orderDishes);
                Order order = new Order(clientName, Integer.parseInt(tableNumber));
                order.setDishesVector(orderDishes);
                orders.add(order);
            }
        }
    }

    private void getOrderDishes(NodeList orderDishesList, DishesVector orderDishes){
        Node node;

        for (int i = 0; i < orderDishesList.getLength(); i++) {
            node = orderDishesList.item(i);

            if (node != null &&  node.getNodeType() == Node.ELEMENT_NODE)
                orderDishes.add(getDishObject(node));

        }
    }

    private Dish getDishObject(Node node){
        String dishName, dishPrice, dishType;
        Element element = (Element) node;
        dishName = element.getElementsByTagName("name").item(0).getTextContent();
        dishPrice = element.getElementsByTagName("price").item(0).getTextContent();
        dishType = element.getElementsByTagName("type").item(0).getTextContent();

        switch (dishType){
            case "main_course":
                return new MainCourseDish(dishName, Integer.parseInt(dishPrice));

            case "appetizer":
                return new AppetizerDish(dishName, Integer.parseInt(dishPrice));

            case "desert":
                return new DesertDish(dishName, Integer.parseInt(dishPrice));
        }

        return null;
    }

}
