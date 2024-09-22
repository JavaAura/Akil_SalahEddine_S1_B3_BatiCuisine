package org.aura.repository.implementation;

import org.aura.config.DbConnection;
import org.aura.models.Client;
import org.aura.models.Materiel;
import org.aura.models.enums.Etat;
import org.aura.models.Projet;
import org.aura.models.enums.mainDoeuvreType;
import org.aura.models.workforce;
import org.aura.repository.interfaces.projectRepoInterface;

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
        String query = " SELECT p.*, c.*, m.*,mo.* " +
                " FROM projet p " +
                " JOIN client c ON p.idclient = c.id" +
                " JOIN materiel m ON p.id = m.projetid" +
                " JOIN maindoeuvre mo ON p.id = mo.projetid" +
                " WHERE p.id = ?";
        Projet projet = null;
        try (Connection con = getConnection();
             PreparedStatement stmt = con.prepareStatement(query)){
            stmt.setInt(1,id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                if (projet == null){
                    projet = new Projet();
                    projet.setId(rs.getInt("p.id"));
                    projet.setNomProjet(rs.getString("p.nomProjet"));
                    projet.setSurface(rs.getDouble("p.surface"));
                    Client client = new Client();
                    client.setId(rs.getInt("c.id"));
                    client.setNom(rs.getString("c.nom"));
                    client.setAdresse(rs.getString("c.adresse"));
                    projet.setClient(client);
                    projet.setMateriels(new ArrayList<>());
                    projet.setWorkforces(new ArrayList<>());
                }
                Materiel materiel = null;
                int materielId = rs.getInt("m.id");
                if (!rs.wasNull()){
                    String nom = rs.getString("m.nom");
                    double tauxTVA = rs.getDouble("m.tauxtva");
                    double coutUnitaire = rs.getDouble("m.coutunitaire");
                    double quantite = rs.getDouble("m.quantite");
                    double coutTransport = rs.getDouble("m.couttransport");
                    double coefficientQualite = rs.getDouble("m.coefficientqualite");
                    materiel = new Materiel(nom,tauxTVA,coutUnitaire,quantite,coutTransport,coefficientQualite);
                    materiel.setId(materielId);
                    projet.getMateriels().add(materiel);
                }
                workforce mainDoeuvre = null;
                int mainDoeuvreId = rs.getInt("maindoeuvre_id");
                if (!rs.wasNull()) {
                    mainDoeuvreType mainType = null ;
                    String valeur = rs.getString("mo.maintype");
                    mainType = mainDoeuvreType.valueOf(valeur);
                    String nom = rs.getString("mo.nom");
                    double tauxTVA = rs.getDouble("mo.tauxtva");
                    double tauxHoraire = rs.getDouble("mo.tauxhoraire");
                    double heuresTravaillees = rs.getDouble("mo.heurestravail");
                    double productivite = rs.getDouble("mo.productivite");

                    mainDoeuvre = new workforce(nom, tauxTVA, tauxHoraire, heuresTravaillees, productivite,mainType);
                    mainDoeuvre.setId(mainDoeuvreId);
                    projet.getWorkforces().add(mainDoeuvre);
                }
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return projet;
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
