package corbaServer;

import javax.naming.Context;
import javax.naming.InitialContext;
import org.omg.CORBA.ORB;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;
import service.BanqueImpl;

public class BanqueServer {
    public static void main(String[] args) {
        try {
            // a. ORB
            ORB orb = ORB.init(args, null);

            // b. POA
            POA rootPOA = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
            rootPOA.the_POAManager().activate();

            // c. JNDI (lit jndi.properties)
            Context ctx = new InitialContext();

            // d. Servant
            BanqueImpl servant = new BanqueImpl();

            // e. Référence + enregistrement dans l'annuaire
            org.omg.CORBA.Object ref = rootPOA.servant_to_reference(servant);
            ctx.rebind("BanqueService", ref);

            System.out.println("Serveur Banque prêt, en attente des clients...");

            // f. Attente des requêtes
            orb.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}