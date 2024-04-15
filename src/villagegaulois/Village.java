package villagegaulois;

import java.util.Iterator;

import personnages.Chef;
import personnages.Gaulois;
import villagegaulois.exceptions.*;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;
	private Marche marche;

	public Village(String nom, int nbVillageoisMaximum, int nbEtals) {
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
		this.marche = new Marche(nbEtals);
	}

	// Classe interne Marché
	private static class Marche {

		private Etal[] etals;

		public Marche(int nbEtals) {
			etals = new Etal[nbEtals];
			
			for (int i = 0; i < etals.length; i++) {
				etals[i] = new Etal();
			}

		}

		void utiliserEtal(int indiceEtal, Gaulois vendeur, String produit, int nbproduit) {
			etals[indiceEtal].occuperEtal(vendeur, produit, indiceEtal);
		}

		int trouverEtalLibre() {
			for (int i = 0; i < etals.length; i++) {
				if (!etals[i].isEtalOccupe()) {
					return i;
				}
			}
			return -1;
		}

		Etal[] trouverEtals(String produit) {
			int nbEtalsproduits = 0;
			for (int i = 0; i < etals.length; i++) {
				if (etals[i].isEtalOccupe()) {
					if (etals[i].contientProduit(produit)) {
						nbEtalsproduits++;
					}
					
				}
			}
			Etal[] etalsActifs = new Etal[nbEtalsproduits];

			int iEtalsProduits = 0;
			for (int i = 0; i < etals.length; i++) {
				if (etals[i].contientProduit(produit)) {
					etalsActifs[iEtalsProduits] = etals[i];
				}
			}
			return etalsActifs;
		}

		Etal trouverVendeur(Gaulois gaulois) {
			for (int i = 0; i < etals.length; i++) {
				if (etals[i].isEtalOccupe()) {
					if (gaulois.equals(etals[i].getVendeur())) {
						return etals[i];
					}
				}
			}
			return null;
		}

		String afficherMarche() {
			int nbEtalvide = 0;
			StringBuilder resultat = new StringBuilder("");

			for (int i = 0; i < etals.length; i++) {
				if (etals[i].isEtalOccupe()) {
					resultat.append(etals[i].afficherEtal());
				}

			}

			resultat.append("Il reste ").append(nbEtalvide).append(" étals non utilisés dans le marché.\n");

			return resultat.toString();
		}

	}
	
	// Fin de la classe Marche

	public String getNom() {
		return nom;
	}

	public void setChef(Chef chef) {
		this.chef = chef;
	}

	public void ajouterHabitant(Gaulois gaulois) {
		if (nbVillageois < villageois.length) {
			villageois[nbVillageois] = gaulois;
			nbVillageois++;
		}
	}

	public Gaulois trouverHabitant(String nomGaulois) {
		if (nomGaulois.equals(chef.getNom())) {
			return chef;
		}
		for (int i = 0; i < nbVillageois; i++) {
			Gaulois gaulois = villageois[i];
			if (gaulois.getNom().equals(nomGaulois)) {
				return gaulois;
			}
		}
		return null;
	}

	public String afficherVillageois() throws VillageSansChefException {
		
		if (this.chef== null) {
			throw new VillageSansChefException("Ce village n'a pas de chef");
		}
		
		StringBuilder chaine = new StringBuilder();
		if (nbVillageois < 1) {
			chaine.append("Il n'y a encore aucun habitant au village du chef " + chef.getNom() + ".\n");
		} else {
			chaine.append("Au village du chef " + chef.getNom() + " vivent les légendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " + villageois[i].getNom() + "\n");
			}
		}
		return chaine.toString();
	}

	public String installerVendeur(Gaulois vendeur, String produit, int nbProduit) {
		StringBuilder resultat = new StringBuilder(vendeur.getNom()).append(" cherche un endroit pour vendre ")
				.append(nbProduit).append(" ").append(produit).append("\n");

		int etalLibre = marche.trouverEtalLibre();
		if (etalLibre != -1) {
			marche.etals[etalLibre].occuperEtal(vendeur, produit, etalLibre);
			resultat.append("le vendeur ").append(vendeur.getNom()).append(" vend des ").append(produit)
					.append(" à l'étal n°").append(etalLibre + 1).append(".\n");
		}

		return resultat.toString();
	}

	public String rechercherVendeursProduit(String produit) {
		StringBuilder chaine = new StringBuilder();
		chaine.append("Les vendeurs qui proposent des " + produit + " sont : \n");

		for (Etal elem : this.marche.etals) {
			if (elem.contientProduit(produit)) {
				chaine.append("- " + elem.getVendeur().getNom() + "\n");
			}
		}

		return chaine.toString();
	}

	public Etal rechercherEtal(Gaulois vendeur) {
		Etal etalVendeur = null;
		for (Etal elem : this.marche.etals) {
			if (elem.getVendeur().equals(vendeur)) {
				etalVendeur = elem;
				return etalVendeur;
			}
		}
		return etalVendeur;
	}

	public String partirVendeur(Gaulois vendeur) {
		Etal etalVendeur = this.rechercherEtal(vendeur);
		return etalVendeur.libererEtal();
	}

	public String afficherMarche() {
		StringBuilder chaine = new StringBuilder();
		chaine.append("Le marché du village \"");
		chaine.append(this.getNom());
		chaine.append("\" possède plusieurs étals :\n");
		chaine.append(this.marche.afficherMarche());
		return chaine.toString();
	}

}