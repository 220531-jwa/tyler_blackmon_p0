package dev.blackmon.models;

public class Client {
    private int id;
    private String username;
    private String password;

    public Client() {
        super();
    }

    public Client(int id, String username, String password) {
        super();
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
