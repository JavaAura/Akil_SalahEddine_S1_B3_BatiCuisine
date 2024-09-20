package org.aura.models;

public abstract class Composants {
    protected int id;
    protected String nom;
    protected double tauxTVA;

    public Composants(String nom, double tauxTVA) {
        this.nom = nom;
        this.tauxTVA = tauxTVA;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public double getTauxTVA() {
        return tauxTVA;
    }

    public void setTauxTVA(double tauxTVA) {
        this.tauxTVA = tauxTVA;
    }

    @Override
    public String toString() {
        return "Composants{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", tauxTVA=" + tauxTVA +
                '}';
    }
}
