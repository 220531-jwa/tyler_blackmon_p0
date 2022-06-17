package dev.blackmon.services;

import dev.blackmon.models.Account;
import dev.blackmon.repositories.AccountDAO;

import java.util.List;

public class AccountService {
    private AccountDAO ad = new AccountDAO();

    public List<Account> getAllAccountsForClient(int id) {
        return ad.getAllAccountsForClient(id);
    }

    public List<Account> getAllAccountsBetween(int id, int lessThan, int greaterThan) {
        return ad.getAllAccountsBetween(id, lessThan, greaterThan);
    }

    public Account createAccount(Account a) {
        return ad.createAccount(a);
    }

    public Account getAccountById(int accountid) {
        return ad.getAccountById(accountid);
    }

    public Account updateAccount(Account a) {
        return ad.updateAccount(a);
    }

    public Account deleteAccount(int id) {
        return ad.deleteAccount(id);
    }


    public Account withdrawFromAccount(int parseInt, int amount, int clientId) {
        return ad.withdrawFromAccount(parseInt, amount, clientId);
    }

    public Account depositToAccount(int parseInt, int amount, int clientId) {
        return ad.depositToAccount(parseInt, amount, clientId);
    }

    public List<Account> transferFromAccounts(int clientid, int id, int secondId, int amount) {
        return ad.transferFromAccounts(id, secondId, clientid, amount);
    }
}
