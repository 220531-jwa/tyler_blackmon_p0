package dev.blackmon.repositories;

import dev.blackmon.models.Client;
import dev.blackmon.utils.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientDAO {
    private static ConnectionUtil cu = ConnectionUtil.getConnectionUtil();

    public Client createClient(Client c) {
        String sql = "insert into clients values (default, ?, ?) returning *";

        try (Connection conn = cu.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, c.getUsername());
            ps.setString(2, c.getPassword());

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Client(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("pass")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Client> getAllClients() {
        List<Client> clients = new ArrayList<>();

        String sql = "select * from clients";

        try (Connection conn = cu.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Client c = new Client(rs.getInt("id"), rs.getString("username"), rs.getString("pass"));
                clients.add(c);
            }
            return clients;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Client getClientById(int id) {
        String sql = "select * from clients where id = ?";

        try (Connection conn = cu.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                return new Client(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("pass")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Client updateClient(Client updatedClient) {
        String sql = "update clients set username = ?, pass = ? where id = ? returning *";

        try (Connection conn = cu.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, updatedClient.getUsername());
            ps.setString(2, updatedClient.getPassword());
            ps.setInt(3, updatedClient.getId());

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                System.out.println();
                return new Client(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("pass")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean deleteClient(int id) {
        String sql = "delete from clients where id = ? returning *";

        try (Connection conn = cu.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
