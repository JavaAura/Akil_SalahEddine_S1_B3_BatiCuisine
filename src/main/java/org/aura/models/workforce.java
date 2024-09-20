package org.aura.models;

import org.aura.models.enums.mainDoeuvreType;

public class workforce extends Composants {
    private double tauxHoraire;
    private double heuresTravail;
    private double productiviteOuvrier;
    private mainDoeuvreType mainDoeuvreType;

    public workforce( String nom ,double tauxTVA, double tauxHoraire, double heuresTravail, double productiviteOuvrier,mainDoeuvreType mainDoeuvreType) {
        super(nom,tauxTVA);
        this.tauxHoraire = tauxHoraire;
        this.heuresTravail = heuresTravail;
        this.productiviteOuvrier = productiviteOuvrier;
        this.mainDoeuvreType = mainDoeuvreType;
    }

    public double getTauxHoraire() {
        return tauxHoraire;
    }

    public void setTauxHoraire(double tauxHoraire) {
        this.tauxHoraire = tauxHoraire;
    }

    public double getHeuresTravail() {
        return heuresTravail;
    }

    public void setHeuresTravail(double heuresTravail) {
        this.heuresTravail = heuresTravail;
    }

    public double getProductiviteOuvrier() {
        return productiviteOuvrier;
    }

    public void setProductiviteOuvrier(double productiviteOuvrier) {
        this.productiviteOuvrier = productiviteOuvrier;
    }

    public org.aura.models.enums.mainDoeuvreType getMainDoeuvreType() {
        return mainDoeuvreType;
    }

    public void setMainDoeuvreType(org.aura.models.enums.mainDoeuvreType mainDoeuvreType) {
        this.mainDoeuvreType = mainDoeuvreType;
    }

    }
