package RestaurantGUI.ManageDishes;

import RestaurantGUI.ProjectSystem.StyleMode.StyleMode;
import Restaurant.Properties.*;
import Restaurant.Properties.Dishes.AppetizerDish;
import Restaurant.Properties.Dishes.DesertDish;
import Restaurant.Properties.Dishes.Dish;
import Restaurant.Properties.Dishes.MainCourseDish;
import RestaurantGUI.ProjectSystem.MessageBox;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;


public final class AddDishGUI extends ManageDishes{

    private Button addDishB;

    public AddDishGUI(StyleMode styleMode, Food food){
        super(styleMode);
        super.food = food;
    }

    public boolean show(){

        addDishB = setUpButtons("Add dish");
        setUpAddBAction();

        setUpObjects("Add dish");

        dishInputLayout.getChildren().addAll(dishInputPane, radioButtonsLayout);

        buttonsLayout.getChildren().addAll(addDishB, cancelB);

        parentLayout.setSpacing(90);

        dishPriceField.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.ENTER)
                addDishB.fire();
        });

        super.stage.showAndWait();
        return thereExistChanges;
    }

    private void setUpAddBAction(){
        addDishB.setOnAction(e -> addDish());
    }

    private void addDish(){
        String dishType = getDishTypeFromRadioButtons();
        String dishName = dishNameField.getText();
        String dishPrice = dishPriceField.getText();

        if (isValidInput(dishType) && isValidInput(dishName) && isValidPrice(dishPrice)
                && !dishAlreadyExists(dishType, dishName, food)) {

            int index = food.getDishTypeIndex(dishType);
            food.addDish(getDishObject(dishType, dishName, Integer.parseInt(dishPrice)), index);
            new MessageBox(styleMode).messageBox("Success", "Dish has been added successfully.");
            super.thereExistChanges = true;
            super.stage.close();
        }

        else if (isValidInput(dishType) && dishAlreadyExists(dishType, dishName, food)){
            new MessageBox(styleMode).messageBox("Alert", "There is already dish with this name.");
        }

        else
            new MessageBox(styleMode).messageBox("Alert", "Please enter a valid input.");
    }

    private String getDishTypeFromRadioButtons(){
        if (mainCourseRadioB.isSelected())
            return "main_course";

        else if (appetizerRadioB.isSelected())
            return "appetizer";

        else if (desertRadioB.isSelected())
            return "desert";

        else
            return "";
    }

    private Dish getDishObject(String dishType, String dishName, int dishPrice){
        switch (dishType){
            case "main_course":
                return new MainCourseDish(dishName, dishPrice);

            case "appetizer":
                return new AppetizerDish(dishName, dishPrice);

            case "desert":
                return new DesertDish(dishName, dishPrice);
        }

        return null;
    }

    private boolean dishAlreadyExists(String dishType, String dishName, Food food){
        return food.getDishesVector(food.getDishTypeIndex(dishType)).find(dishName) != -1;
    }

}
