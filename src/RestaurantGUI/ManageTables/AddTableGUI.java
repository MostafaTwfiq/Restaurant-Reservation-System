package RestaurantGUI.ManageTables;

import RestaurantGUI.ProjectSystem.StyleMode.StyleMode;
import Restaurant.Properties.Tables;
import Restaurant.Vectors.TablesVector;
import RestaurantGUI.ProjectSystem.MessageBox;
import javafx.geometry.Insets;

public final class AddTableGUI extends ManageTablesGUI{

    private TablesVector tablesVector;

    public AddTableGUI(StyleMode styleMode, TablesVector tablesVector){
        super(styleMode);
        this.tablesVector = tablesVector;
    }

    public void show(){
        super.setUpObjects("Add table");

        addOrEditButton = setUpButtons("Add table");
        setUpAddOrEditButtonAction();

        buttonsLayout.getChildren().addAll(addOrEditButton, cancelButton);

        buttonsLayout.setPadding(new Insets(50 , 0, 0, 0));

        this.stage.setScene(scene);
        this.stage.showAndWait();
    }

    private void setUpAddOrEditButtonAction(){
        addOrEditButton.setOnAction(e -> {
            String tableNOSeats = tableNOSeatsField.getText();
            String isInSmokingArea = getRadioButtonsValue();
            if (isValidInput(isInSmokingArea) && isValidNOSeats(tableNOSeats)) {
                Tables newTable = new Tables(tablesVector.getNextTableNumber(),
                        Integer.parseInt(tableNOSeats), isInSmokingArea.equals("true"));

                tablesVector.add(newTable);

                tablesVector.sortTables();

                new MessageBox(styleMode).messageBox("Success", "Table has been added successfully.");
                this.stage.close();
            }
            else
                new MessageBox(styleMode).messageBox("Alert", "Please enter a valid input.");
        });
    }

}
