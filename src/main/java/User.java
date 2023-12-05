public abstract class User {
    private String username;
    private String password;
    private Access access;

    public User(String username, String password, Access access) {
        this.username = username;
        this.password = password;
        this.access = access;
    }

    // Used to create a single client user account
    public User(String username, String password) {
        this(username, password, Access.CLIENT);
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Access getAccess() {
        return access;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAccess(Access access) {
        this.access = access;
    }
}

