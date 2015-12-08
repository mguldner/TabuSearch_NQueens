
//=======================================
public class Liste {
	//=======================================
	private static final int AUCUNE=2147483647;

	private boolean presence[];
	private int precedante[];
	private int suivante[];
	private int premiere;
	private int nombreDePresentes;

	public Liste( int taille ) {
		//-------------------------------------------------
		// Initialement la liste contient 0, 1, 2, ..., taille-1
		presence=new boolean[taille];
		precedante=new int[taille];
		suivante=new int[taille];
		premiere=0;
		nombreDePresentes=taille;
		for (int i=0 ; i<taille ; i++ ) {
			precedante[i]=i-1;
			suivante[i]=i+1;
			presence[i]=true;
		}
		suivante[taille-1]=AUCUNE;
		precedante[0]=AUCUNE;
	}

	public  int taille() {
		//-------------------------------------------------
		// Retourne le nombre d'entiers actuellement presents dans la liste.
		return this.nombreDePresentes;
	}

	public int supprimerIeme( int indice ) {
		//-------------------------------------------------
		// ENTREE : indice est compris dans [0, taille()[
		// SORTIE : retourne le (indice+1)ieme element de la liste, 
		//          et supprime cet element de la liste.
		if (indice>=this.nombreDePresentes) {
			return AUCUNE;
		}
		int ieme=this.premiere;
		while (indice>0) {
			ieme=this.suivante[ieme];
			indice--;
		}
		this.retirer(ieme);
		return ieme;
	}

	//======================================================
	//======================================================
	//
	// LES METHODES CI-DESSOUS NE VOUS SONT D'AUCUNE UTILITE
	//
	//======================================================
	//======================================================

	public boolean presente( int valeur ) {
		//-------------------------------------------------
		return this.presence[valeur];
	}

	public void vider() {
		//-------------------------------------------------
		for (int i=0; i<this.presence.length; i++) {
			this.presence[i]=false;
		}
		this.premiere=AUCUNE;
		this.nombreDePresentes=0;
	}


	public void ajouter( int valeur ) {
		//-------------------------------------------------
		if ((valeur<presence.length) && (presence[valeur]==false)) {
			this.presence[valeur]=true;
			this.suivante[valeur]=premiere; 
			if (this.premiere!=AUCUNE) {
				this.precedante[this.premiere]=valeur;
			}
			this.premiere=valeur;
			this.nombreDePresentes++;
		}
	}

	public void retirer( int valeur ) {
		//-------------------------------------------------
		if ((valeur<this.presence.length) && (this.presence[valeur]==true)) {
			this.presence[valeur]=false;
			if (this.premiere==valeur) {
				if (this.suivante[valeur]!=AUCUNE) {
					this.precedante[suivante[valeur]]=AUCUNE;
				}
				this.premiere=this.suivante[valeur];
			}
			else {
				if (this.suivante[valeur]!=AUCUNE) {
					this.precedante[suivante[valeur]]=precedante[valeur];
				}
				this.suivante[this.precedante[valeur]]=suivante[valeur];
			}
			this.nombreDePresentes--;
		}
	}

	public int premiere() {
		//-------------------------------------------------
		return this.premiere; 
	}

	public int suivante( int valeur) {
		//-------------------------------------------------
		return this.suivante[valeur];
	}
}
