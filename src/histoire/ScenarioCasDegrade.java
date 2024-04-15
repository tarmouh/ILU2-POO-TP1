package histoire;

import personnages.Gaulois;
import villagegaulois.Etal;

public class ScenarioCasDegrade {

	public static void main(String[] args) {
		Etal etal = new Etal();
		Gaulois asterix  = new Gaulois("astérix",4);
		int quantite = 0;
		etal.libererEtal();
		System.out.println("Fin libérer étal");
		
		try {
			etal.acheterProduit(quantite, asterix);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		}
		catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		
		
		System.out.println("Fin acheter produit");
		System.out.println("Fin du test");
	}
	
	
}
