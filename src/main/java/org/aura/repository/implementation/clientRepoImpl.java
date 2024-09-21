package org.aura.repository.implementation;

import org.aura.config.DbConnection;
import org.aura.repository.interfaces.clientRepoInterface;
import org.aura.models.Client;
import org.aura.utils.LoggerUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class clientRepoImpl implements clientRepoInterface {

    public clientRepoImpl() {
    }

    private static Connection getConnection(){
        return DbConnection.getInstance().getConnection();
    }

    @Override
    public List<Client> findAllClients() {
        List<Client> list = new ArrayList<>();
        String query = " SELECT * FROM client ";
        try (Connection con = getConnection();
             PreparedStatement stmt = con.prepareStatement(query)){
            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                Client client = new Client(rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("adresse"),
                        rs.getString("telephone"),
                        rs.getBoolean("estprofessionnel"));
                list.add(client);
            }
        }catch (SQLException e){
            e.getMessage();
        }
        return list;
    }

    @Override
    public Client findClientById(int id) {
        String query = " SELECT * FROM client WHERE id = ? ";
        try (Connection con = getConnection();
        PreparedStatement stmt = con.prepareStatement(query)){
            stmt.setInt(1,id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()){
                return new Client(rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("adresse"),
                        rs.getString("telephone"),
                        rs.getBoolean("estprofessionnel"));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Client findClientByName(String name) {
        String query = " SELECT * FROM client WHERE nom = ? ";
        try (Connection con = getConnection();
             PreparedStatement stmt = con.prepareStatement(query)){
            stmt.setString(1,name);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()){
                return new Client(rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("adresse"),
                        rs.getString("telephone"),
                        rs.getBoolean("estprofessionnel"));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int addClient(Client client) {
        String query = "INSERT INTO client (nom,adresse,telephone,estprofessionnel) VALUES (?,?,?,?) ";
        try(Connection con = getConnection();
        PreparedStatement stmt = con.prepareStatement(query)) {
        stmt.setString(1,client.getNom());
        stmt.setString(2,client.getAdresse());
        stmt.setString(3,client.getTelephone());
        stmt.setBoolean(4,client.getEstProfessionnel());
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return rs.getInt(1);
        }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return -1;
    }
}
