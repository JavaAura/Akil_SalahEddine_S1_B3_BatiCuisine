package org.aura.repository.implementation;

import org.aura.config.jdbcConnection;
import org.aura.models.Materiel;
import org.aura.repository.interfaces.materielRepoInterface;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class materielRepoImpl implements materielRepoInterface {

    public materielRepoImpl() {}

    private static Connection getConnection(){
        return jdbcConnection.getConnection();
    }

    @Override
    public Materiel findMaterielById(int id) {
        String query = " SELECT * FROM materiel WHERE id = ? ";
        try (Connection con = getConnection();
             PreparedStatement stmt = con.prepareStatement(query)){
            stmt.setInt(1,id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()){
                return new Materiel(rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("typeComposant"),
                        rs.getDouble("tauxTVA"),
                        rs.getDouble("coutUnitaire"),
                        rs.getDouble("quantite"),
                        rs.getDouble("coutTransport"),
                        rs.getDouble("coefficientQualite"));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void addMateriel(Materiel materiel) {
        String query = "INSERT INTO materiel (nom,typeComposant,tauxTVA,coutUnitaire,quantite,coutTransport,coefficientQualite) VALUES (?,?,?,?,?,?,?) ";
        try(Connection con = getConnection();
            PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1,materiel.getNom());
            stmt.setString(2,materiel.getTypeComposant());
            stmt.setDouble(3,materiel.getTauxTVA());
            stmt.setDouble(4,materiel.getCoutUnitaire());
            stmt.setDouble(5,materiel.getQuantite());
            stmt.setDouble(6,materiel.getCoutTransport());
            stmt.setDouble(7,materiel.getCoefficientQualite());
            stmt.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
