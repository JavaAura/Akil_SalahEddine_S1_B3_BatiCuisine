package org.aura.repository.implementation;

import org.aura.config.DbConnection;
import org.aura.models.Client;
import org.aura.models.Materiel;
import org.aura.models.enums.Etat;
import org.aura.models.Projet;
import org.aura.models.enums.mainDoeuvreType;
import org.aura.models.workforce;
import org.aura.repository.interfaces.projectRepoInterface;
import org.aura.utils.LoggerUtils;

import java.sql.*;
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
        String query = "SELECT p.id as projet_id, p.nomprojet, p.surface, " +
                "c.id as client_id, c.nom as client_nom, c.adresse, " +
                "m.id as materiel_id, m.nom as materiel_nom, m.tauxtva as materiel_tauxtva, " +
                "m.coutunitaire as materiel_coutunitaire, m.quantite as materiel_quantite, " +
                "m.couttransport as materiel_couttransport, m.coefficientqualite as materiel_coefficientqualite, " +
                "mo.id as mo_id, mo.nom as mo_nom, mo.tauxtva as mo_tauxtva, " +
                "mo.tauxhoraire as mo_tauxhoraire, mo.heurestravail as mo_heurestravail, " +
                "mo.productivite as mo_productivite, mo.maintype " +
                "FROM projet p " +
                "JOIN client c ON p.idclient = c.id " +
                "LEFT JOIN materiel m ON p.id = m.projetid " +
                "LEFT JOIN maindoeuvre mo ON p.id = mo.projetid " +
                "WHERE p.id = ?";
        Projet projet = null;
        try (Connection con = getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                if (projet == null) {
                    projet = new Projet();
                    projet.setId(rs.getInt("projet_id"));
                    projet.setNomProjet(rs.getString("nomprojet"));
                    projet.setSurface(rs.getDouble("surface"));

                    Client client = new Client();
                    client.setId(rs.getInt("client_id"));
                    client.setNom(rs.getString("client_nom"));
                    client.setAdresse(rs.getString("adresse"));
                    projet.setClient(client);

                    projet.setMateriels(new ArrayList<>());
                    projet.setWorkforces(new ArrayList<>());
                }

                int materielId = rs.getInt("materiel_id");
                if (!rs.wasNull()) {
                    Materiel materiel = new Materiel(
                            rs.getString("materiel_nom"),
                            rs.getDouble("materiel_tauxtva"),
                            rs.getDouble("materiel_coutunitaire"),
                            rs.getDouble("materiel_quantite"),
                            rs.getDouble("materiel_couttransport"),
                            rs.getDouble("materiel_coefficientqualite")
                    );
                    materiel.setId(materielId);
                    projet.getMateriels().add(materiel);
                }

                int mainDoeuvreId = rs.getInt("mo_id");
                if (!rs.wasNull()) {
                    workforce mainDoeuvre = new workforce(
                            rs.getString("mo_nom"),
                            rs.getDouble("mo_tauxtva"),
                            rs.getDouble("mo_tauxhoraire"),
                            rs.getDouble("mo_heurestravail"),
                            rs.getDouble("mo_productivite"),
                            mainDoeuvreType.valueOf(rs.getString("maintype"))
                    );
                    mainDoeuvre.setId(mainDoeuvreId);
                    projet.getWorkforces().add(mainDoeuvre);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return projet;
    }

    public void updateProject(int projectId, double coutTotal, double margeBeneficiaire) {
        String updateQuery = "UPDATE projet SET couttotal = ?, margebeneficiaire = ? WHERE id = ?";
        try (Connection con = getConnection();
             PreparedStatement stmt = con.prepareStatement(updateQuery)) {
            stmt.setDouble(1, coutTotal);
            stmt.setDouble(2, margeBeneficiaire);
            stmt.setInt(3, projectId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            LoggerUtils.logError("Erreur lors de la mise à jour du projet : " + e.getMessage());
            e.printStackTrace();
        }
    }


    @Override
    public int addProject(Projet projet , int clientId) {
        String query = "INSERT INTO projet (nomProjet,margeBeneficiaire,coutTotal,etatProjet,surface,idclient) VALUES (?,?,?,?::etat,?,?) ";
        try(Connection con = getConnection();
            PreparedStatement stmt = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1,projet.getNomProjet());
            stmt.setDouble(2,projet.getMargeBeneficiaire());
            stmt.setDouble(3,projet.getCoutTotal());
            stmt.setObject(4,projet.getEtatProjet(),java.sql.Types.OTHER);
            stmt.setDouble(5,projet.getSurface());
            stmt.setInt(6,clientId);
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
