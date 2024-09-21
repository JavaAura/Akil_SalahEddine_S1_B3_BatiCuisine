package org.aura.repository.implementation;

import org.aura.config.DbConnection;
import org.aura.models.enums.mainDoeuvreType;
import org.aura.models.workforce;
import org.aura.repository.interfaces.mainDoeuvreRepoInterface;

import java.sql.*;

public class mainDoeuvreRepoImpl implements mainDoeuvreRepoInterface {

    public mainDoeuvreRepoImpl() {}

    private static Connection getConnection(){
        return DbConnection.getInstance().getConnection();
    }

    @Override
    public workforce findWorkForceById(int id) {
        String query = "SELECT * FROM mainDoeuvre WHERE id = ? ";
        try (Connection con = getConnection();
             PreparedStatement stmt = con.prepareStatement(query)){
            stmt.setInt(1,id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()){
                String valeur =rs.getString("mainType");
                mainDoeuvreType mainDoeuvreType =null;
                if (valeur!=null){
                    mainDoeuvreType = mainDoeuvreType.valueOf(valeur);
                }
                workforce workforce = new workforce(
                        rs.getString("nom"),
                        rs.getDouble("tauxTVA"),
                        rs.getDouble("tauxHoraire"),
                        rs.getDouble("heuresTravail"),
                        rs.getDouble("productiviteOuvrier"),
                        mainDoeuvreType
                );
                workforce.setId(rs.getInt("id"));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void addWorkForce(workforce mainDoeuvre , int idProjet) {
        String query = "INSERT INTO mainDoeuvre (nom,tauxTVA,tauxHoraire,heuresTravail,productiviteOuvrier,mainType,projetid) VALUES (?,?,?,?,?,?::laborType,?) ";
        try(Connection con = getConnection();
            PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1,mainDoeuvre.getNom());
            stmt.setDouble(2,mainDoeuvre.getTauxTVA());
            stmt.setDouble(3,mainDoeuvre.getTauxHoraire());
            stmt.setDouble(4,mainDoeuvre.getHeuresTravail());
            stmt.setDouble(5,mainDoeuvre.getProductiviteOuvrier());
            stmt.setObject(6,mainDoeuvre.getMainDoeuvreType(),java.sql.Types.OTHER);
            stmt.setInt(7,idProjet);
            stmt.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
