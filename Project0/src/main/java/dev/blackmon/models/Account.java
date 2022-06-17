package dev.blackmon.models;

public class Account {
    private int id;
    private double balance;
    private int clientId;
    private String typeOfAccount;

    public Account() {
        super();
    }

    public Account(int id, double balance, int clientId, String typeOfAccount) {
        super();
        this.id = id;
        this.balance = balance;
        this.clientId = clientId;
        this.typeOfAccount = typeOfAccount;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTypeOfAccount() {
        return typeOfAccount;
    }

    public void setTypeOfAccount(String typeOfAccount) {
        this.typeOfAccount = typeOfAccount;
    }
}
