package Restaurant.HandlingData;

import Restaurant.Properties.Dishes.Dish;
import Restaurant.Properties.Tables;
import Restaurant.Useres.Clients.Client;
import Restaurant.Useres.Clients.Order;
import RestaurantGUI.ProjectSystem.StyleMode.StyleMode;
import Restaurant.Properties.Food;
import Restaurant.Useres.Clients.VipClient;
import Restaurant.Vectors.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.Vector;

public final class SaveData {

    private String path;
    private StyleMode styleMode;
    private UsersVector users;
    private BillsVector bills;
    private TablesVector tables;
    private Food food;
    private Vector<Order> orders;

    public SaveData(String path, StyleMode styleMode, UsersVector users, TablesVector tables,
                    Food food, BillsVector bills, Vector<Order> orders){
        this.path = path;
        this.styleMode = styleMode;
        this.users = users;
        this.tables = tables;
        this.food = food;
        this.bills = bills;
        this.orders = orders;
    }

    public void setStyleMode(StyleMode styleMode) {
        this.styleMode = styleMode;
    }

    public void saveData(){
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();

        try {
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document document = dBuilder.newDocument();

            //add elements to Document:
            Element restaurantElement = document.createElementNS("", "restaurant");

            //append root element to document:
            document.appendChild(restaurantElement);

            //create sub childes elements:
            Element styleElement = document.createElement("style_mode");
            Element usersElement = document.createElement("users");
            Element dishesElement = document.createElement("dishes");
            Element tablesElement = document.createElement("tables");
            Element billsElement = document.createElement("bills");
            Element ordersElement = document.createElement("orders");

            //append sub childes elements to root element:
            restaurantElement.appendChild(styleElement);
            restaurantElement.appendChild(usersElement);
            restaurantElement.appendChild(dishesElement);
            restaurantElement.appendChild(tablesElement);
            restaurantElement.appendChild(billsElement);
            restaurantElement.appendChild(ordersElement);

            saveStyle(document, styleElement);
            saveUsers(document, usersElement);
            saveDishes(document, dishesElement);
            saveTables(document, tablesElement);
            saveBills(document, billsElement);
            saveOrders(document, ordersElement);

            //for output to file:
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            //for good print into file:
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(document);

            //write to file:
            StreamResult file = new StreamResult(new File(this.path));

            //write data:
            transformer.transform(source, file);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //-------------------------------------------------------------------------------------

    private void saveStyle(Document document, Element styleElement){
        styleElement.appendChild(getStyle(document));
    }

    private Node getStyle(Document document){
        Element style = document.createElement("style");
        style.appendChild(getTextNode(document, "styleType", styleMode.getStyleType()));
        return style;
    }

    //-------------------------------------------------------------------------------------

    private void saveUsers(Document document, Element usersElement){
        for (int i = 0; i < users.getLength(); i++)
            usersElement.appendChild(getUser(document, i));

    }

    private Node getUser(Document document, int index){
        Element user = document.createElement("user");

        user.appendChild(getTextNode(document, "name", users.get(index).getName()));
        user.appendChild(getTextNode(document, "role", users.get(index).getRole()));
        user.appendChild(getTextNode(document, "username", users.get(index).getUserName()));
        user.appendChild(getTextNode(document, "password", users.get(index).getPassword()));

        if (users.get(index).getRole().equals("VipClient")) {
            user.appendChild(getTextNode(document, "discount",
                    String.format("%d", ((VipClient) users.get(index)).getClientDiscount())));

        }


        if (users.get(index).getRole().equals("VipClient")
                || users.get(index).getRole().equals("Client")) {

            user.appendChild(getClientDishes(document, ((Client) users.get(index)).getDishes()));

            Element clientTable = document.createElement("ClientTable");
            clientTable.appendChild(getClientTable(document, ((Client) users.get(index)).getTable()));
            user.appendChild(clientTable);

        }

        return user;
    }

    private Node getClientDishes(Document document, DishesVector dishesVector){
        Element clientDishes = document.createElement("ClientDishes");

        for (int i = 0; i < dishesVector.getLength(); i++)
            clientDishes.appendChild(getClientDish(document, dishesVector.get(i)));

        return clientDishes;
    }

    private Node getClientDish(Document document, Dish dish){
        Element clientDish = document.createElement("ClientDish");

        clientDish.appendChild(getTextNode(document, "type", dish.getDishType()));
        clientDish.appendChild(getTextNode(document, "name", dish.getDishName()));
        clientDish.appendChild(getTextNode(document, "price",
                String.format("%d", dish.getPrice())));

        return clientDish;
    }

    private Node getClientTable(Document document, Tables table){
        Element clientTable = document.createElement("client_table");

        if (table != null) {
            clientTable.appendChild(getTextNode(document, "tableNumber",
                    String.format("%d", table.getTableNumber())));
        }

        else
            clientTable.appendChild(getTextNode(document, "tableNumber", ""));

        return clientTable;
    }

    //-------------------------------------------------------------------------------------

    private void saveDishes(Document document, Element dishesElement){
        DishesVector dishesVector;
        for (int i = 0; i < food.getLength(); i++) {
            dishesVector = food.getDishesVector(i);

            for (int j = 0; j < dishesVector.getLength(); j++)
                dishesElement.appendChild(getDish(document, dishesVector, j));

        }
    }

    private Node getDish(Document document, DishesVector dishesVector, int dishIndex){
        Element dish = document.createElement("dish");
        dish.appendChild(getTextNode(document, "name", dishesVector.get(dishIndex).getDishName()));

        dish.appendChild(getTextNode(document, "price",
                String.format("%d", dishesVector.get(dishIndex).getPrice())));

        dish.appendChild(getTextNode(document, "type", dishesVector.get(dishIndex).getDishType()));
        return dish;
    }
    //-------------------------------------------------------------------------------------
    private void saveTables(Document document, Element tablesElement){
        for (int i = 0; i < tables.getLength(); i++)
            tablesElement.appendChild(getTable(document, i));

    }

    private Node getTable(Document document, int index) {
        Element table = document.createElement("table");

        table.appendChild(getTextNode(document, "number", String.format("%d", tables.get(index).getTableNumber())));

        table.appendChild(getTextNode(document, "number_of_seats",
                String.format("%d", tables.get(index).getNumberOfSeats())));

        table.appendChild(getTextNode(document, "smoking",
                String.format("%b", tables.get(index).isInSmokingAreas())));

        return table;
    }

    //-------------------------------------------------------------------------------------

    private void saveBills(Document document, Element billsElement){
        for (int i = 0; i < bills.getLength(); i++)
            billsElement.appendChild(getBill(document, i));

    }

    private Node getBill(Document document, int index) {
        Element bill = document.createElement("bill");
        Element dishes = document.createElement("billDishes");

        bill.appendChild(getTextNode(document, "client_name", bills.get(index).getClientName()));

        bill.appendChild(getTextNode(document, "date", bills.get(index).getDate()));

        bill.appendChild(getTextNode(document, "client_bill",
                String.format("%f", bills.get(index).getFloatBill())));

        bill.appendChild(dishes);

        DishesVector billDishes = bills.get(index).getDishes();

        for (int i = 0; i < billDishes.getLength(); i++)
            dishes.appendChild(getBillDish(document, billDishes, i));

        return bill;
    }

    private Node getBillDish(Document document, DishesVector dishesVector, int dishIndex){
        Element dish = document.createElement("billDish");
        dish.appendChild(getTextNode(document, "name", dishesVector.get(dishIndex).getDishName()));

        dish.appendChild(getTextNode(document, "price",
                String.format("%d", dishesVector.get(dishIndex).getPrice())));

        dish.appendChild(getTextNode(document, "type", dishesVector.get(dishIndex).getDishType()));

        return dish;
    }

    //-------------------------------------------------------------------------------------

    private void saveOrders(Document document, Element ordersElement){
        for (int i = 0; i < orders.size(); i++)
            ordersElement.appendChild(getOrder(document, i));

    }

    private Node getOrder(Document document, int index) {
        Element order = document.createElement("order");
        Element dishes = document.createElement("orderDishes");

        order.appendChild(getTextNode(document, "client_name", orders.get(index).getClientName()));

        order.appendChild(getTextNode(document, "tableNumber",
                String.format("%d", orders.get(index).getTableNumber())));

        order.appendChild(dishes);

        DishesVector orderDishes = orders.get(index).getDishesVector();

        for (int i = 0; i < orderDishes.getLength(); i++)
            dishes.appendChild(getOrderDish(document, orderDishes, i));

        return order;
    }

    private Node getOrderDish(Document document, DishesVector dishesVector, int dishIndex){
        Element dish = document.createElement("orderDish");
        dish.appendChild(getTextNode(document, "name", dishesVector.get(dishIndex).getDishName()));

        dish.appendChild(getTextNode(document, "price",
                String.format("%d", dishesVector.get(dishIndex).getPrice())));

        dish.appendChild(getTextNode(document, "type", dishesVector.get(dishIndex).getDishType()));

        return dish;
    }

    //-------------------------------------------------------------------------------------

    private Node getTextNode(Document document, String name, String value) {
        Element node = document.createElement(name);
        node.appendChild(document.createTextNode(value));
        return node;
    }

}
