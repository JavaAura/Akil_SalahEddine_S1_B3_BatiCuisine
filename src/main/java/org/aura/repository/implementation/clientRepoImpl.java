package org.aura.repository.implementation;

import org.aura.config.DbConnection;
import org.aura.repository.interfaces.clientRepoInterface;
import org.aura.models.Client;
import java.sql.*;
import java.util.ArrayList;

public class clientRepoImpl implements clientRepoInterface {

    public clientRepoImpl() {
    }

    private static Connection getConnection(){
        return DbConnection.getInstance().getConnection();
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
        PreparedStatement stmt = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
        stmt.setString(1,client.getNom());
        stmt.setString(2,client.getAdresse());
        stmt.setString(3,client.getTelephone());
        stmt.setBoolean(4,client.getEstProfessionnel());
        stmt.executeUpdate();
        ResultSet rs = stmt.getGeneratedKeys();
        if (rs.next()) {
            return rs.getInt(1);
        }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return -1;
    }

}
