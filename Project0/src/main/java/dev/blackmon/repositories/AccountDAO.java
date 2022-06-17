package dev.blackmon.repositories;

import dev.blackmon.models.Account;
import dev.blackmon.utils.ConnectionUtil;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO {
    private static ConnectionUtil cu = ConnectionUtil.getConnectionUtil();

    /*public Account createAccount(Account a) {
        String sql = "insert into accounts values(default"
    }*/


    public List<Account> getAllAccountsForClient(int id) {
        List<Account> accounts = new ArrayList<>();

        String sql = "select * from accounts a" +
                " where client_id = ?";

        try (Connection conn = cu.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Account a = new Account(
                        rs.getInt("id"),
                        Double.parseDouble(rs.getString("balance").replace("$", "").replace(",", "")),
                        rs.getInt("client_id"),
                        rs.getString("account_type"));

                accounts.add(a);
            }
            return accounts;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<Account> getAllAccountsBetween(int id, int lessThan, int greaterThan) {
        List<Account> accounts = new ArrayList<>();

        String sql = "select * from accounts a" +
                " left join clients on a.client_id = clients.id" +
                " where client_id = ? and a.balance::numeric < ? and a.balance::numeric > ?";

        try (Connection conn = cu.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, id);
            ps.setBigDecimal(2, BigDecimal.valueOf(lessThan));
            ps.setBigDecimal(3, BigDecimal.valueOf(greaterThan));

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Account a = new Account(
                        rs.getInt("id"),
                        Double.parseDouble(rs.getString("balance").replace("$", "").replace(",", "")),
                        rs.getInt("client_id"),
                        rs.getString("account_type"));

                accounts.add(a);
            }
            return accounts;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Account createAccount(Account a) {
        String sql = "insert into accounts values (default, ?, ?, ?) returning *";

        try (Connection conn = cu.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setBigDecimal(1, BigDecimal.valueOf(a.getBalance()));
            ps.setInt(2, a.getClientId());
            ps.setString(3, a.getTypeOfAccount());

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Account(
                        rs.getInt("id"),
                        Double.parseDouble(rs.getString("balance").replace("$", "").replace(",", "")),
                        rs.getInt("client_id"),
                        rs.getString("account_type")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Account getAccountById(int accountid) {
        String sql = "select * from accounts a" +
                " left join clients on a.client_id = clients.id" +
                " where a.id = ?";

        try (Connection conn = cu.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, accountid);


            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Account(
                        rs.getInt("id"),
                        Double.parseDouble(rs.getString("balance").replace("$", "").replace(",", "")),
                        rs.getInt("client_id"),
                        rs.getString("account_type")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Account updateAccount(Account a) {
        String sql = "update accounts set balance = ?, client_id = ?, account_type = ? where id = ? returning *";

        try (Connection conn = cu.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setBigDecimal(1, BigDecimal.valueOf(a.getBalance()));
            ps.setInt(2, a.getClientId());
            ps.setString(3, a.getTypeOfAccount());
            ps.setInt(4, a.getId());

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Account(
                        rs.getInt("id"),
                        Double.parseDouble(rs.getString("balance").replace("$", "").replace(",", "")),
                        rs.getInt("client_id"),
                        rs.getString("account_type")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Account deleteAccount(int accountId) {
        String sql = "delete from accounts where id = ? returning *";

        try (Connection conn = cu.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, accountId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Account();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public Account depositToAccount(int id, int amount, int clientId) {
        String sql = "update accounts set balance = balance::numeric + ? where id = ? and client_id = ? returning *";

        try (Connection conn = cu.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setBigDecimal(1, BigDecimal.valueOf(amount));
            ps.setInt(2, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Account(
                        rs.getInt("id"),
                        Double.parseDouble(rs.getString("balance").replace("$", "").replace(",", "")),
                        rs.getInt("client_id"),
                        rs.getString("account_type")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Account withdrawFromAccount(int id, int amount, int clientId) {
        String sql = "update accounts set balance = balance::numeric - ? where id = ? and client_id = ? returning *";

        try (Connection conn = cu.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setBigDecimal(1, BigDecimal.valueOf(amount));
            ps.setInt(2, id);
            ps.setInt(3, clientId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Account(
                        rs.getInt("id"),
                        Double.parseDouble(rs.getString("balance").replace("$", "").replace(",", "")),
                        rs.getInt("client_id"),
                        rs.getString("account_type")
                );
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Account a = new Account();
            a.setId(-1);
            return a;
        }
    }

    public List<Account> transferFromAccounts(int id, int secondId, int clientId, int amount) {
        String sql1 = "update accounts set balance = balance::numeric - ? where id = ? and client_id = ? returning *";
        String sql2 = "update accounts set balance = balance::numeric + ? where id = ? and client_id = ? returning *";

        List<Account> accounts = new ArrayList<>();

        try (Connection conn = cu.getConnection()) {
            PreparedStatement ps1 = conn.prepareStatement(sql1);
            PreparedStatement ps2 = conn.prepareStatement(sql2);

            ps1.setBigDecimal(1, BigDecimal.valueOf(amount));
            ps1.setInt(2, id);
            ps1.setInt(3, clientId);

            ResultSet rs1 = ps1.executeQuery();

            while (rs1.next()) {
                Account a = new Account(
                        rs1.getInt("id"),
                        Double.parseDouble(rs1.getString("balance").replace("$", "").replace(",", "")),
                        rs1.getInt("client_id"),
                        rs1.getString("account_type"));
                accounts.add(a);
            }

            if (accounts.size() == 0) {
                return null;
            }

            ps2.setBigDecimal(1, BigDecimal.valueOf(amount));
            ps2.setInt(2, secondId);
            ps2.setInt(3, clientId);

            ResultSet rs2 = ps2.executeQuery();

            while (rs2.next()) {
                accounts.add(new Account(
                        rs2.getInt("id"),
                        Double.parseDouble(rs2.getString("balance").replace("$", "").replace(",", "")),
                        rs2.getInt("client_id"),
                        rs2.getString("account_type")
                ));
            }


            return accounts;
        } catch (SQLException e) {
            e.printStackTrace();
            Account a = new Account(-1, -1, -1, "savings");
            accounts.add(a);
            return accounts;
        }
    }
}
