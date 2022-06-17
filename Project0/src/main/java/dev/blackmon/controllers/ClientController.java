package dev.blackmon.controllers;

import dev.blackmon.models.Client;
import dev.blackmon.services.ClientService;
import io.javalin.http.Context;

import java.util.List;

public class ClientController {
    private static ClientService clientService = new ClientService();

    public static void createNewClient(Context ctx) {
        try {
            Client c = ctx.bodyAsClass(Client.class);
            Client result = clientService.createClient(c);

            if (result != null) {
                ctx.status(201);
            } else {
                ctx.status(424);
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    public static void getAllClients(Context ctx) {
        List<Client> clients = clientService.getAllClients();

        if (clients != null) {
            ctx.status(200);
            ctx.json(clients);
        } else {
            ctx.status(424);
        }
    }

    public static void getClientById(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));

        Client c = null;

        try {
            c = clientService.getClientById(id);
            if (c != null) {
                ctx.status(200);
                ctx.json(c);
            } else {
                ctx.status(404);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void updateClient(Context ctx) {
        Client updatedClient = ctx.bodyAsClass(Client.class);
        updatedClient.setId(Integer.parseInt(ctx.pathParam("id")));

        try {
            Client result = clientService.updateClient(updatedClient);
            if (result != null) {
                ctx.status(200);
                ctx.json(result);
            } else {
                ctx.status(404);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void deleteClient(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));

        boolean result = clientService.deleteClient(id);

        if (result) {
            ctx.status(205);
        } else {
            ctx.status(404);
        }
    }

}
