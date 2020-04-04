package Restaurant.Useres;

public abstract class Users {
    protected String name;
    private String userName;
    private String password;
    protected String role;

    public Users(String name, String userName,
                 String password, String role) {
        this.name = name;
        this.userName = userName;
        this.password = password;
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() { return role; }

    @Override
    public String toString(){
        return "Name: " + name + "\n"
                + "User Name: " + userName + "\n"
                + "Password: " + password + "\n"
                + "Role: " + role;
    }
}
