public abstract class User {
    private String username;
    private String password;
    private Access access;

    public User(String username, String password, Access access) {
        this.username = username;
        this.password = password;
        this.access = access;
    }

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

enum Access {
    ADMIN,
    CLIENT;

    @Override
    public String toString() {
        switch (this) {
            case Access.ADMIN : return "Admin";
            case Access.CLIENT : return "Client";
            default : return null;
        }
    }
}
