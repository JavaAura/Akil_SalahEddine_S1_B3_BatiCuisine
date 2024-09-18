package org.aura.repository.implementation;

import org.aura.config.jdbcConnection;
import org.aura.models.Devis;
import org.aura.repository.interfaces.devisRepoInterface;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class devisRepoImpl implements devisRepoInterface {
    private devisRepoImpl() {
    }

    private static Connection getConnection(){
        return jdbcConnection.getConnection();
    }

    @Override
    public Devis findDevisById(int id) {
        String query = " SELECT * FROM devis WHERE id = ? ";
        try (Connection con = getConnection();
             PreparedStatement stmt = con.prepareStatement(query)){
            stmt.setInt(1,id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()){
                LocalDate dateEmission = rs.getDate("dateEmission").toLocalDate();
                LocalDate dateValidite = rs.getDate("dateValidite").toLocalDate();
                return new Devis(rs.getInt("id"),
                        rs.getDouble("montantEstime"),
                        dateEmission,
                        dateValidite,
                        rs.getBoolean("accepte"));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void addDevis(Devis devis) {
        String query = "INSERT INTO devis (montantEstime,dateEmission,dateValidite,accepte) VALUES (?,?,?,?) ";
        try(Connection con = getConnection();
            PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setDouble(1,devis.getMontantEstime());
            stmt.setObject(2,devis.getDateEmission());
            stmt.setObject(3,devis.getDateValidite());
            stmt.setBoolean(4,devis.getAccepte());
            stmt.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}