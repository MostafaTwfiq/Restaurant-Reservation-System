package RestaurantGUI.ManageDishes;

import RestaurantGUI.ProjectSystem.StyleMode.StyleMode;
import Restaurant.Properties.Dishes.Dish;
import Restaurant.Properties.Food;
import RestaurantGUI.ProjectSystem.MessageBox;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;

public final class EditDishGUI extends ManageDishes{

    private Dish dish;

    private Button editDish;
    private Button deleteB;

    public EditDishGUI(StyleMode styleMode, Dish dish, Food food){
        super(styleMode);
        this.dish = dish;
        super.food = food;
    }

    public boolean show(){
        editDish = setUpButtons("Edit dish");
        setUpEditDishBAction();

        deleteB = setUpButtons("Delete");
        setUpDeleteBAction();

        setUpObjects("Edit dish");

        dishInputLayout.getChildren().addAll(dishInputPane);
        buttonsLayout.getChildren().addAll(editDish, deleteB, cancelB);

        super.dishNameField.setText(dish.getDishName());
        super.dishPriceField.setText(String.format("%d", dish.getPrice()));

        dishPriceField.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.ENTER)
                editDish.fire();
        });

        stage.showAndWait();
        return thereExistChanges;
    }

    private void setUpEditDishBAction(){
        editDish.setOnAction(e -> {
                String dishName = dishNameField.getText();
                String dishPrice = dishPriceField.getText();
                if (isValidInput(dishName) && isValidPrice(dishPrice)){
                    dish.setDishName(dishName);
                    dish.setPrice(Integer.parseInt(dishPrice));
                    new MessageBox(styleMode).messageBox("Success", "Dish has been edited successfully.");
                    thereExistChanges = true;
                    super.stage.close();
                }

                else
                    new MessageBox(styleMode).messageBox("Alert", "Please enter a valid input.");
        });
    }

    private void setUpDeleteBAction(){
        deleteB.setOnAction(e -> {
            food.deleteDish(food.getDishTypeIndex(dish.getDishType()), dish.getDishName());
            new MessageBox(styleMode).messageBox("Success", "Dish has been deleted.");
            thereExistChanges = true;
            super.stage.close();
        });
    }
}
