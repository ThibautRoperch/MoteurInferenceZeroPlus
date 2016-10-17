import java.io.*;

public class Main {

	public static void main(String[] args) {
		Moteur m = new Moteur();


		String chaine="";
		String fichier ="base_de_regles.txt";
		
		//lecture du fichier texte	
		try{
			InputStream ips=new FileInputStream(fichier); 
			InputStreamReader ipsr=new InputStreamReader(ips);
			BufferedReader br=new BufferedReader(ipsr);
			String ligne;
			while ((ligne=br.readLine())!=null){
				System.out.println(ligne);
				chaine+=ligne+"\n";
			}
			br.close(); 
		}		
		catch (Exception e){
			System.out.println(e.toString());
		}


		System.out.println("\n=== Base de Règles ===");
		m.afficher_br();

		String strategie = "1";
		switch (strategie) {
			case "1":
				m.ajouter_fait("action", "glisser");
				m.ajouter_fait("environnement", "neige");
				System.out.println("\n=== Base de Faits AVANT ===");
				m.afficher_bf();
				m.chainage_avant_largeur();
				break;

			case "2":
				m.ajouter_fait("action", "glisser");
				m.ajouter_fait("environnement", "neige");
				System.out.println("\n=== Base de Faits AVANT ===");
				m.afficher_bf();
				m.chainage_avant_profondeur();
				break;

			case "3":
				System.out.println("\n=== Base de Faits AVANT ===");
				m.afficher_bf();
				m.chainage_arriere();
				break;
				
			default:
				break;
		}

		System.out.println("\n=== Base de Faits APRES ===");
		m.afficher_bf();
	}
}