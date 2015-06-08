import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import models.Instrumento;
import models.Estilo;
import models.dao.GenericDAO;
import play.Application;
import play.GlobalSettings;
import play.Logger;
import play.db.jpa.JPA;


public class Global extends GlobalSettings {
	private static GenericDAO dao = new GenericDAO();
	private static Instrumento instrumento;
	private static Estilo estilo;
	
	@Override
	public void onStart(Application app) {
		Logger.info("Aplicação inicializada...");
		JPA.withTransaction(new play.libs.F.Callback0() {
			@Override
			public void invoke() throws Throwable {
				FileReader arq;
				BufferedReader lerArq;
				
				try { 
					arq = new FileReader("dados/instrumentos.txt"); 
					lerArq = new BufferedReader(arq);
					String linha = lerArq.readLine(); 
					while (linha != null) { 
						instrumento = new Instrumento(linha);
						dao.persist(instrumento);
						linha = lerArq.readLine(); 
					} 
					arq.close(); 
				} catch (IOException e) { 
					System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage()); 
				} 
				dao.flush();
				
				try { 
					arq = new FileReader("dados/estilos.txt"); 
					lerArq = new BufferedReader(arq);
					String linha = lerArq.readLine(); 
					while (linha != null) { 
						estilo = new Estilo(linha);
						dao.persist(estilo);
						linha = lerArq.readLine(); 
					} 
					arq.close(); 
				} catch (IOException e) { 
					System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage()); 
				} 
				dao.flush();
			}
		});
	}
}
