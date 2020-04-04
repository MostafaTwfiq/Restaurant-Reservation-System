package RestaurantGUI.ManageTables;

import RestaurantGUI.ProjectSystem.StyleMode.StyleMode;
import Restaurant.Properties.Tables;
import Restaurant.Vectors.TablesVector;
import RestaurantGUI.ProjectSystem.MessageBox;
import javafx.scene.control.Button;

public final class EditTableGUI extends ManageTablesGUI{

    private Tables table;
    private TablesVector tablesVector;
    private Button deleteButton;

    public EditTableGUI(StyleMode styleMode, Tables table, TablesVector tablesVector){
        super(styleMode);
        this.table = table;
        this.tablesVector = tablesVector;
    }

    public void show(){
        super.setUpObjects("Edit table");

        addOrEditButton = setUpButtons("Edit table");
        setUpAddOrEditButtonAction();

        deleteButton = setUpButtons("Delete table");
        setUpDeleteBAction();

        buttonsLayout.getChildren().addAll(addOrEditButton, deleteButton, cancelButton);

        this.stage.setScene(scene);
        this.stage.showAndWait();
    }

    private void setUpAddOrEditButtonAction(){
        addOrEditButton.setOnAction(e -> {
            String tableNOSeats = tableNOSeatsField.getText();
            String isInSmokingArea = getRadioButtonsValue();
            if (isValidInput(isInSmokingArea) && isValidNOSeats(tableNOSeats)) {
                table.setNumberOfSeats(Integer.parseInt(tableNOSeats));
                table.setInSmokingAreas(isInSmokingArea.equals("true"));
                new MessageBox(styleMode).messageBox("Success", "Table has been edited successfully.");
                this.stage.close();
            }
            else
                new MessageBox(styleMode).messageBox("Alert", "Please enter a valid input.");
        });
    }

    private void setUpDeleteBAction(){
        deleteButton.setOnAction(e -> {
            tablesVector.delete(tablesVector.find(table.getTableNumber()));
            new MessageBox(styleMode).messageBox("Success", "Table has been deleted successfully.");
            this.stage.close();
        });
    }

}
