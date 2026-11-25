package service;

import corbaBanque.*;
import java.util.HashMap;
import java.util.Map;
public class BanqueImpl extends IBanqueRemotePOA {
    private final Map<Integer, Compte> comptes = new HashMap<>();
    private static final double TAUX_EUR_DT = 3.4; // à adapter
    @Override
    public void creerCompte(Compte cpte) {
        comptes.put(cpte.code, new Compte(cpte.code, cpte.solde));
        System.out.println("Compte créé : " + cpte.code);
    }
    @Override
    public void verser(float mt, int code) {
        Compte c = comptes.get(code);
        if (c != null) c.solde += mt;
    }
    @Override
    public void retirer(float mt, int code) {
        Compte c = comptes.get(code);
        if (c != null && c.solde >= mt) c.solde -= mt;
    }
    @Override
    public Compte getCompte(int code) {
        Compte c = comptes.get(code);
        // CORBA ne peut pas renvoyer null pour une struct
        return (c != null) ? c : new Compte(-1, 0f);
    }
    @Override
    public Compte[] getComptes() {
        return comptes.values().toArray(new Compte[0]);
    }
    @Override
    public double conversion(float mt) {
        return mt * TAUX_EUR_DT;
    }
}