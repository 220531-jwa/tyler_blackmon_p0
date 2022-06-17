package dev.blackmon.controllers;

import dev.blackmon.models.Account;
import dev.blackmon.services.AccountService;
import io.javalin.http.Context;

import java.util.List;

public class AccountController {
    private static final AccountService accountService = new AccountService();

    public static void getAllAccountsForClient(Context ctx) {
        List<Account> accounts;

        if (ctx.queryParam("amountLessThan") != null && ctx.queryParam("amountGreaterThan") != null) {
            accounts = accountService.getAllAccountsBetween(Integer.parseInt(ctx.pathParam("id")), Integer.parseInt(ctx.queryParam("amountLessThan")), Integer.parseInt(ctx.queryParam("amountGreaterThan")));
        } else {
            accounts = accountService.getAllAccountsForClient(Integer.parseInt(ctx.pathParam("id")));
        }

        if (accounts.size() >= 1) {
            ctx.status(200);
            ctx.json(accounts);
        } else {
            ctx.status(404);
        }
    }

    public static void createAccount(Context ctx) {
        try {
            Account a = ctx.bodyAsClass(Account.class);
            a.setClientId(Integer.parseInt(ctx.pathParam("id")));

            Account result = accountService.createAccount(a);

            if (result != null) {
                ctx.status(201);
                ctx.json(a);
            } else {
                ctx.status(424);
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    public static void getAccountById(Context ctx) {
        Account result = accountService.getAccountById(Integer.parseInt(ctx.pathParam("accountid")));

        if (result != null) {
            ctx.status(200);
            ctx.json(result);
        } else {
            ctx.status(404);
        }
    }

    public static void updateAccount(Context ctx) {
        Account a = ctx.bodyAsClass(Account.class);
        a.setClientId(Integer.parseInt(ctx.pathParam("id")));
        Account result = accountService.updateAccount(a);

        if (result != null) {
            ctx.status(204);
        } else {
            ctx.status(404);
        }
    }

    public static void deleteAccount(Context ctx) {
        Account result = accountService.deleteAccount(Integer.parseInt(ctx.pathParam("accountid")));

        if (result != null) {
            ctx.status(200);
        } else {
            ctx.status(404);
        }
    }

    public static void updateBalance(Context ctx) {
        Account result;
        if (ctx.body().contains("deposit")) {
            result = accountService.depositToAccount(Integer.parseInt(ctx.pathParam("accountid")),
                    Integer.parseInt(ctx.body().replace("{", "").replace("}", "").replace(" ", "").replace("deposit", "").replace(":", "").replace("\"", "").trim()),
                    Integer.parseInt(ctx.pathParam("id")));
            ;
        } else {
            result = accountService.withdrawFromAccount(Integer.parseInt(ctx.pathParam("accountid")),
                    Integer.parseInt(ctx.body().replace("{", "").replace("}", "").replace(" ", "").replace("withdraw", "").replace(":", "").replace("\"", "").trim()),
                    Integer.parseInt(ctx.pathParam("id")));
            ;
        }

        if (result == null) {
            ctx.status(404);
        } else {
            if (result.getId() == -1) {
                ctx.status(422);
            } else {
                ctx.status(200);
            }
        }
    }

    public static void transferFromAccounts(Context ctx) {
        List<Account> accounts = accountService.transferFromAccounts(
                Integer.parseInt(ctx.pathParam("id")),
                Integer.parseInt(ctx.pathParam("accountid")),
                Integer.parseInt(ctx.pathParam("transferid")),
                Integer.parseInt(ctx.body().replace("{", "").replace("}", "").replace(" ", "").replace("amount", "").replace(":", "").replace("\"", "").trim()
                ));

        
        if (accounts == null) {
            ctx.status(404);
        } else {
            if (accounts.size() == 1 && accounts.get(0).getId() == -1) {
                ctx.status(422);
            } else {
                ctx.status(200);
            }
        }

    }
}
