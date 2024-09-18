package org.aura.repository.implementation;

import org.aura.config.jdbcConnection;
import org.aura.models.Materiel;
import org.aura.models.workforce;
import org.aura.repository.interfaces.mainDoeuvreRepoInterface;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class mainDoeuvreRepoImpl implements mainDoeuvreRepoInterface {

    public mainDoeuvreRepoImpl() {}

    private static Connection getConnection(){
        return jdbcConnection.getConnection();
    }

    @Override
    public workforce findWorkForceById(int id) {
        String query = " SELECT * FROM mainDoeuvre WHERE id = ? ";
        try (Connection con = getConnection();
             PreparedStatement stmt = con.prepareStatement(query)){
            stmt.setInt(1,id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()){
                return new workforce(rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("typeComposant"),
                        rs.getDouble("tauxTVA"),
                        rs.getDouble("tauxHoraire"),
                        rs.getDouble("heuresTravail"),
                        rs.getDouble("productiviteOuvrier"));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void addWorkForce(workforce mainDoeuvre) {
        String query = "INSERT INTO mainDoeuvre (nom,typeComposant,tauxTVA,tauxHoraire,heuresTravail,productiviteOuvrier) VALUES (?,?,?,?,?,?) ";
        try(Connection con = getConnection();
            PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1,mainDoeuvre.getNom());
            stmt.setString(2,mainDoeuvre.getTypeComposant());
            stmt.setDouble(3,mainDoeuvre.getTauxTVA());
            stmt.setDouble(4,mainDoeuvre.getTauxHoraire());
            stmt.setDouble(5,mainDoeuvre.getHeuresTravail());
            stmt.setDouble(6,mainDoeuvre.getProductiviteOuvrier());
            stmt.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
