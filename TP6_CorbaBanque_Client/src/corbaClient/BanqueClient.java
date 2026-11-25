package corbaClient;

import corbaBanque.*;
import javax.naming.Context;
import javax.naming.InitialContext;

public class BanqueClient {
    public static void main(String[] args) {
        try {
            // a. Propriétés JNDI (jndi.properties)
            Context ctx = new InitialContext();

            // b. Référence de l'objet distant
            org.omg.CORBA.Object obj = (org.omg.CORBA.Object) ctx.lookup("BanqueService");

            // c. Narrow -> Stub
            IBanqueRemote banque = IBanqueRemoteHelper.narrow(obj);

            // d. Test de chaque méthode
            banque.creerCompte(new Compte(1, 1000f));
            banque.creerCompte(new Compte(2, 500f));

            banque.verser(200f, 1);
            System.out.println("Après versement : " + banque.getCompte(1).solde);

            banque.retirer(100f, 1);
            System.out.println("Après retrait   : " + banque.getCompte(1).solde);

            Compte c = banque.getCompte(2);
            System.out.println("Compte " + c.code + " -> solde " + c.solde);

            System.out.println("--- Liste des comptes ---");
            for (Compte cp : banque.getComptes()) {
                System.out.println("Code: " + cp.code + " | Solde: " + cp.solde);
            }

            System.out.println("100 EUR = " + banque.conversion(100f) + " DT");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}