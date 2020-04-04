package RestaurantGUI.ManageUsers;

import RestaurantGUI.ProjectSystem.StyleMode.StyleMode;
import Restaurant.Useres.Clients.Client;
import Restaurant.Useres.Users;
import Restaurant.Vectors.UsersVector;
import RestaurantGUI.ProjectSystem.MessageBox;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;

public final class EditUserGUI extends ManageUsers{

    private Users user;
    private UsersVector usersVector;

    private boolean withDeleteB;

    private Button editUserB;
    private Button deleteUserB;


    public EditUserGUI(StyleMode styleMode, Users user, UsersVector usersVector, ObservableList<Users> usersObservableList){
        super(styleMode);
        this.user = user;
        this.usersVector = usersVector;
        super.usersObservableList =  usersObservableList;
        this.withDeleteB = true;
    }

    public EditUserGUI(StyleMode styleMode, Users user){
        super(styleMode);
        this.user = user;
        this.usersVector = null;
        super.usersObservableList =  null;
        this.withDeleteB = false;
    }

    public void show(){
        editUserB = setUpButtons("Edit user");
        deleteUserB = setUpButtons("Delete user");

        setUpObjects("Edit user");

        setUpEditUserBAction();
        setUpDeleteBAction();

        buttonsLayout.getChildren().add(editUserB);

        if (withDeleteB)
            buttonsLayout.getChildren().add(deleteUserB);

        else
            parentLayout.setSpacing(80);

        buttonsLayout.getChildren().add(cancelB);

        userInputLayout.getChildren().addAll(userInputPane);

        super.nameField.setText(user.getName());
        super.userNameField.setText(user.getUserName());
        super.passwordField.setText(user.getPassword());

        passwordField.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.ENTER)
                editUserB.fire();
        });

        stage.showAndWait();
    }

    private void setUpEditUserBAction(){
        editUserB.setOnAction(e -> {
            String name = nameField.getText();
            String userName = userNameField.getText();
            String password = passwordField.getText();
            if (isValidInput(name) && isValidInput(userName) && isValidInput(password)){
                user.setName(name);
                user.setUserName(userName);
                user.setPassword(password);
                new MessageBox(styleMode).messageBox("Success", "User has been edited successfully.");

                if (usersObservableList != null)
                    usersObservableList.clear();

                this.stage.close();
            }
            else
                new MessageBox(styleMode).messageBox("Alert", "Please enter a valid input.");
        });
    }

    private void setUpDeleteBAction(){
        deleteUserB.setOnAction(e -> {
            if ( (user.getRole().equals("Client") || user.getRole().equals("VipClient"))
                    && ((Client) user).getTable() != null ) {
                new MessageBox(styleMode).messageBox("Alert", "This client is reserving a table.");
            }

            else {
                usersVector.delete(usersVector.find(user.getUserName(), user.getPassword()));
                new MessageBox(styleMode).messageBox("Success", "User has been deleted successfully.");

                if (usersObservableList != null)
                    usersObservableList.clear();

                this.stage.close();
            }
        });
    }

}
