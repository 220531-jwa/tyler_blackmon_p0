package dev.blackmon;

import dev.blackmon.controllers.AccountController;
import dev.blackmon.controllers.ClientController;
import io.javalin.Javalin;

import static io.javalin.apibuilder.ApiBuilder.*;

/**
 * Hello world!
 */
public class APIDriver {
    public static void main(String[] args) {
        Javalin app = Javalin.create();
        app.start(8081);

        app.routes(() -> {
            path("/clients", () -> {
                get(ClientController::getAllClients);
                post(ClientController::createNewClient);
                path("/{id}", () -> {
                    get(ClientController::getClientById);
                    put(ClientController::updateClient);
                    delete(ClientController::deleteClient);
                    path("/accounts", () -> {
                        get(AccountController::getAllAccountsForClient);
                        post(AccountController::createAccount);
                        path("/{accountid}", () -> {
                            get(AccountController::getAccountById);
                            put(AccountController::updateAccount);
                            delete(AccountController::deleteAccount);
                            patch(AccountController::updateBalance);
                            path("/transfer/{transferid}", () -> {
                                patch(AccountController::transferFromAccounts);
                            });
                        });
                    });
                });
            });
        });
    }
}
