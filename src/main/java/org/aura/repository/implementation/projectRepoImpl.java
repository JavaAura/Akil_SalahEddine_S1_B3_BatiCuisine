package org.aura.repository.implementation;

import org.aura.config.DbConnection;
import org.aura.models.enums.Etat;
import org.aura.models.Projet;
import org.aura.repository.interfaces.projectRepoInterface;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class projectRepoImpl implements projectRepoInterface {

    public projectRepoImpl(){}

    private static Connection getConnection(){
        return DbConnection.getInstance().getConnection();
    }

    @Override
    public List<Projet> findAllProjects() {
        List<Projet> list = new ArrayList<>();
        String query = " SELECT * FROM projet ";
        try (Connection con = getConnection();
             PreparedStatement stmt = con.prepareStatement(query)){
            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                String valeur = rs.getString("etat");
                Etat etat1 = null;
                if (valeur != null){
                   etat1 =  Etat.valueOf(valeur);
                }
                Projet projet = new Projet(rs.getInt("id"),
                        rs.getDouble("surface"),
                        rs.getString("nomProjet"),
                        rs.getDouble("margeBeneficiaire"),
                        rs.getDouble("coutTotal"),
                        etat1);
                list.add(projet);
            }
        }catch (SQLException e){
            e.getMessage();
        }
        return list;
    }

    @Override
    public Projet findProjectById(int id) {
        String query = " SELECT * FROM projet WHERE id = ? ";
        try (Connection con = getConnection();
             PreparedStatement stmt = con.prepareStatement(query)){
            stmt.setInt(1,id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()){
                String valeur = rs.getString("etat");
                Etat etat = null;
                if (valeur != null){
                    etat = Etat.valueOf(valeur);
                }
                return new Projet(rs.getInt("id"),
                        rs.getDouble("surface"),
                        rs.getString("nomProjet"),
                        rs.getDouble("margeBeneficiaire"),
                        rs.getDouble("coutTotal"),
                        etat
                                        );
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void addProject(Projet projet) {
        String query = "INSERT INTO projet (nomProjet,margeBeneficiaire,coutTotal,etatProjet,surface) VALUES (?,?,?,?,?) ";
        try(Connection con = getConnection();
            PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1,projet.getNomProjet());
            stmt.setDouble(2,projet.getMargeBeneficiaire());
            stmt.setDouble(3,projet.getCoutTotal());
            stmt.setObject(4,projet.getEtatProjet(),java.sql.Types.OTHER);
            stmt.setDouble(5,projet.getSurface());
            stmt.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
