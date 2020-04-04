package RestaurantGUI.ManageUsers;

import RestaurantGUI.ProjectSystem.StyleMode.StyleMode;
import Restaurant.Useres.Clients.Client;
import Restaurant.Useres.Clients.VipClient;
import Restaurant.Useres.Cooker;
import Restaurant.Useres.Manager;
import Restaurant.Useres.Users;
import Restaurant.Useres.Waiter;
import Restaurant.Vectors.UsersVector;
import RestaurantGUI.ProjectSystem.MessageBox;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;

public final class AddUserGUI extends ManageUsers {

    private UsersVector usersVector;

    private Button addUserB;

    public AddUserGUI(StyleMode styleMode, UsersVector usersVector, ObservableList<Users> usersObservableList) {
        super(styleMode);
        this.usersVector = usersVector;
        super.usersObservableList = usersObservableList;
    }

    public void show(){

        addUserB = setUpButtons("Add user");
        setUpAddUserBAction();

        setUpObjects("Add new user");

        buttonsLayout.getChildren().addAll(addUserB, cancelB);
        userInputLayout.getChildren().addAll(userInputPane, radioButtonsLayout);
        parentLayout.setSpacing(55);

        passwordField.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.ENTER)
                addUserB.fire();
        });

        stage.showAndWait();
    }

    private void setUpAddUserBAction(){
        addUserB.setOnAction(e -> {
            String name = nameField.getText();
            String userName = userNameField.getText();
            String password = passwordField.getText();
            String role = getRoleFromRadioButtons();
            if (!role.equals("") && isValidInput(name) &&
                    isValidInput(userName) && isValidInput(password)){
                usersVector.add(getNewUserType(name, userName, password, role));
                new MessageBox(styleMode).messageBox("Success", "New user has been added successfully!");
                usersObservableList.clear();
                this.stage.close();
            }

            else
                new MessageBox(styleMode).messageBox("Alert", "Please enter a valid input.");

        });
    }

    private Users getNewUserType(String name, String userName,
                                 String password, String role){
        switch (role){
            case "Client":
                return new Client(name, userName, password);
            case "VipClient":
                return new VipClient(name, userName, password, new VipClientDiscount(styleMode).getDiscountInput());
            case "Manager":
                return new Manager(name, userName, password);
            case "Cooker":
                return new Cooker(name, userName, password);
            case "Waiter":
                return new Waiter(name, userName, password);
        }
        return null;
    }

}
