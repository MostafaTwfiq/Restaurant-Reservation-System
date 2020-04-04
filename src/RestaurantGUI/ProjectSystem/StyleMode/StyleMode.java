package RestaurantGUI.ProjectSystem.StyleMode;

public abstract class StyleMode {

    private String styleType;
    private String buttonsTextColor;
    private String buttonsColor;
    private String textFieldColor;
    private String tableViewColor;
    private String labelColor;
    private String layoutColor;

    public StyleMode(String styleType, String buttonsTextColor, String buttonsColor, String textFieldColor,
                     String tableViewColor, String labelColor, String layoutColor){

        this.styleType = styleType;
        this.buttonsTextColor = buttonsTextColor;
        this.buttonsColor = buttonsColor;
        this.textFieldColor = textFieldColor;
        this.tableViewColor = tableViewColor;
        this.labelColor = labelColor;
        this.layoutColor = layoutColor;
    }

    public String getStyleType() {
        return styleType;
    }

    public String getButtonsTextColor() {
        return buttonsTextColor;
    }

    public String getButtonsColor() {
        return buttonsColor;
    }

    public String getTextFieldColor() {
        return textFieldColor;
    }

    public String getTableViewColor() {
        return tableViewColor;
    }

    public String getLabelColor() {
        return labelColor;
    }

    public String getLayoutColor() {
        return layoutColor;
    }
}
