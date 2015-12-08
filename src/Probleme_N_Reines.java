import java.util.ArrayList;

public class Probleme_N_Reines {

	private int[] colonne;
	private int nombreReines;

	public Probleme_N_Reines(int nombreReines){
		this.nombreReines = nombreReines;
		this.colonne = new int[nombreReines];
	}

	public int getNombreReines(){
		return this.nombreReines;
	}

	public int[] getColonne(){
		return this.colonne;
	}

	public void tabuSearch(int maxRestart, int maxIter, int sizetabuList){		
		long time = System.currentTimeMillis();
		for(int i = 0; i < maxRestart; i++){
			// Construction d'une configuration aléatoire s
			Liste randomListe = new Liste(this.getNombreReines());
			int[] s = new int[this.getNombreReines()];
			for(int j = 0; j<this.getNombreReines(); j++){
				int randomIndex = (int)(Math.random() * randomListe.taille());
				s[j] = randomListe.supprimerIeme(randomIndex);
			}
//			System.out.println("random s :\n"+solutionToString(s));

			ArrayList<int[]> tabuList = new ArrayList<int[]>();
			int nbIter = 0;

			while(nbFaux(s) > 0 && nbIter < maxIter){
				// Détermination du voisinage de s
				//System.out.println("NbIter = "+nbIter+"\n");
				int[][] voisinage = new int[(int)(this.getNombreReines()/2)][this.getNombreReines()];
				int indexVoisinage = 0;
				Liste listeSwap = new Liste(this.getNombreReines());
				while(listeSwap.taille() > 1){
					int swap1 = listeSwap.supprimerIeme((int)(Math.random() * listeSwap.taille()));
					int swap2 = listeSwap.supprimerIeme((int)(Math.random() * listeSwap.taille()));
					//System.out.println("Swap1 = "+swap1 + "; swap2 = " + swap2);
					for(int j = 0; j<this.getNombreReines(); j++){
						if(j == swap1)
							voisinage[indexVoisinage][j] = s[swap2];
						else if(j == swap2)
							voisinage[indexVoisinage][j] = s[swap1];
						else
							voisinage[indexVoisinage][j] = s[j];
					}
					//System.out.println("Vois : \n"+solutionToString(voisinage[indexVoisinage]));
					indexVoisinage++;
				}
				
				// Recherche du argmin du voisinage
				int minFaux = Integer.MAX_VALUE;
				for(int j=0; j<(int)(this.getNombreReines()/2); j++){
					if(!tabuList.contains(voisinage[j]) && nbFaux(voisinage[j]) < minFaux){
						s = voisinage[j];
					//	System.out.println("Argmin "+j+" avec nbFaux = "+nbFaux(voisinage[j])+": \n"
						//+solutionToString(voisinage[j]));
					}
					if(nbFaux(s) == 0){
						//System.out.println("Solution !!");
						this.colonne = s;
						System.out.println("Temps exec : "+ (System.currentTimeMillis() - time) + 
								"ms pour "+nbIter+" iterations et "+i+" restarts.");
						return;
					}
				}
				
				// Mise à jour de tabuList
				tabuList.add(0,s);
				if(tabuList.size() > sizetabuList){
					tabuList.remove(tabuList.size()-1);
				}
				nbIter++;
			}
		}
	}

	public int nbFaux(int[] colonne){
		int result = 0;
		//System.out.println("Pour \n"+solutionToString(colonne));
		for(int i=0; i<this.getNombreReines(); i++){
			for(int j=i; j<this.getNombreReines(); j++){
				if(i != j && colonne[j] - colonne[i] == i - j){
					result++;
			//		System.out.println("Pb - entre "+i+" et "+j);
				//	System.out.println(""+ (colonne[j] - colonne[i])+ " = " + (i-j));
				}
				else if(i != j && colonne[j] - colonne[i] == j - i){
					result ++;
					//System.out.println("Pb + entre "+i+" et "+j);
					//System.out.println(""+(colonne[j] - colonne[i]) + " = " + (j-i));
				}
			}
		}
		return result;
	}
	
	public static String solutionToString(int[] solution){
		String result = "Solution : \n";
		for(int i=0; i<solution.length; i++){
			result+="Colonne " + i + " : " + solution[i] + "\n";
		}
		return result;
	}

	public static void main(String[] args) {
		Probleme_N_Reines probleme = new Probleme_N_Reines(10);
		probleme.tabuSearch(10, 100, 25);
		System.out.println(solutionToString(probleme.getColonne()));
	}

}
