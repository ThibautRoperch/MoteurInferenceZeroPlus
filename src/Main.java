import java.io.*;

public class Main {

	public static void main(String[] args) {
		Resolution res = new Resolution();

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

		res.resoudre();
	}
}