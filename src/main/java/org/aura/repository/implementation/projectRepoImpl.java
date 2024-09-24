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
import java.util.HashMap;
import java.util.Map;

public class projectRepoImpl implements projectRepoInterface {

    public projectRepoImpl(){}

    private static Connection getConnection(){
        return DbConnection.getInstance().getConnection();
    }

    @Override
    public Map<Integer, Projet> findAllProjects() {
        Map<Integer, Projet> allProjects = new HashMap<>();

        String queryProjets = "SELECT p.id as projet_id, p.nomprojet, p.surface, p.margebeneficiaire, p.couttotal, p.etatprojet as etatProjet, " +
                "c.id as client_id, c.nom as client_nom, c.adresse " +
                "FROM projet p " +
                "JOIN client c ON p.idclient = c.id";

        try (Connection con = getConnection();
             PreparedStatement stmt = con.prepareStatement(queryProjets);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int projetId = rs.getInt("projet_id");
                Projet projet = new Projet();
                projet.setId(projetId);
                projet.setNomProjet(rs.getString("nomprojet"));
                projet.setSurface(rs.getDouble("surface"));
                projet.setCoutTotal(rs.getDouble("couttotal"));
                projet.setMargeBeneficiaire(rs.getDouble("margebeneficiaire"));
                projet.setEtatProjet(Etat.valueOf(rs.getString("etatProjet")));

                Client client = new Client();
                client.setId(rs.getInt("client_id"));
                client.setNom(rs.getString("client_nom"));
                client.setAdresse(rs.getString("adresse"));
                projet.setClient(client);

                projet.setMateriels(new ArrayList<>());
                projet.setWorkforces(new ArrayList<>());

                allProjects.put(projetId, projet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return allProjects;
        }

        String queryMateriaux = "SELECT * FROM materiel WHERE projetid = ?";
        try (Connection con = getConnection();
             PreparedStatement stmt = con.prepareStatement(queryMateriaux)) {

            for (Projet projet : allProjects.values()) {
                stmt.setInt(1, projet.getId());
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        Materiel materiel = new Materiel(
                                rs.getString("nom"),
                                rs.getDouble("coutunitaire"),
                                rs.getDouble("quantite"),
                                rs.getDouble("couttransport"),
                                rs.getDouble("coefficientqualite")
                        );
                        materiel.setId(rs.getInt("id"));
                        projet.getMateriels().add(materiel);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String queryMainDoeuvre = "SELECT * FROM maindoeuvre WHERE projetid = ?";
        try (Connection con = getConnection();
             PreparedStatement stmt = con.prepareStatement(queryMainDoeuvre)) {

            for (Projet projet : allProjects.values()) {
                stmt.setInt(1, projet.getId());
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        workforce mainDoeuvre = new workforce(
                                rs.getString("nom"),
                                rs.getDouble("tauxhoraire"),
                                rs.getDouble("heurestravail"),
                                rs.getDouble("productivite"),
                                mainDoeuvreType.valueOf(rs.getString("maintype"))
                        );
                        mainDoeuvre.setId(rs.getInt("id"));
                        projet.getWorkforces().add(mainDoeuvre);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return allProjects;
    }

    @Override
    public Projet findProjectById(int id) {
        Projet projet = null;
        String queryProjet = "SELECT p.id as projet_id, p.nomprojet, p.surface, p.margebeneficiaire, p.couttotal, p.etatprojet as etatProjet, " +
                "c.id as client_id, c.nom as client_nom, c.adresse " +
                "FROM projet p " +
                "JOIN client c ON p.idclient = c.id " +
                "WHERE p.id = ?";

        try (Connection con = getConnection();
             PreparedStatement stmt = con.prepareStatement(queryProjet)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                projet = new Projet();
                projet.setId(rs.getInt("projet_id"));
                projet.setNomProjet(rs.getString("nomprojet"));
                projet.setSurface(rs.getDouble("surface"));
                projet.setCoutTotal(rs.getDouble("couttotal"));
                projet.setMargeBeneficiaire(rs.getDouble("margebeneficiaire"));
                projet.setEtatProjet(Etat.valueOf(rs.getString("etatProjet")));

                Client client = new Client();
                client.setId(rs.getInt("client_id"));
                client.setNom(rs.getString("client_nom"));
                client.setAdresse(rs.getString("adresse"));
                projet.setClient(client);

                projet.setMateriels(new ArrayList<>());
                projet.setWorkforces(new ArrayList<>());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        if (projet != null) {
            String queryMateriaux = "SELECT * FROM materiel WHERE projetid = ?";
            try (Connection con = getConnection();
                 PreparedStatement stmt = con.prepareStatement(queryMateriaux)) {
                stmt.setInt(1, id);
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    Materiel materiel = new Materiel(
                            rs.getString("nom"),
                            rs.getDouble("coutunitaire"),
                            rs.getDouble("quantite"),
                            rs.getDouble("couttransport"),
                            rs.getDouble("coefficientqualite")
                    );
                    materiel.setId(rs.getInt("id"));
                    projet.getMateriels().add(materiel);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            String queryMainDoeuvre = "SELECT * FROM maindoeuvre WHERE projetid = ?";
            try (Connection con = getConnection();
                 PreparedStatement stmt = con.prepareStatement(queryMainDoeuvre)) {
                stmt.setInt(1, id);
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    workforce mainDoeuvre = new workforce(
                            rs.getString("nom"),
                            rs.getDouble("tauxhoraire"),
                            rs.getDouble("heurestravail"),
                            rs.getDouble("productivite"),
                            mainDoeuvreType.valueOf(rs.getString("maintype"))
                    );
                    mainDoeuvre.setId(rs.getInt("id"));
                    projet.getWorkforces().add(mainDoeuvre);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return projet;
    }

    @Override
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
    public void updateEtatProject(int projectId, Etat etat) {
        String updateQuery = "UPDATE projet SET etatprojet = ?::etat WHERE id = ?";
        try (Connection con = getConnection();
             PreparedStatement stmt = con.prepareStatement(updateQuery)) {
            stmt.setObject(1, etat.name());
            stmt.setInt(2, projectId);
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
