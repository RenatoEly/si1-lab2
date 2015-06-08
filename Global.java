import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import models.Instrumento;
import models.Estilo;
import models.Anuncio;
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
				List<Instrumento> instrumentos = new ArrayList<Instrumento>();
				List<Estilo> estilosgosta = new ArrayList<Estilo>();
				List<Estilo> estilosnaogosta = new ArrayList<Estilo>();
				List<String> dados = new ArrayList<>();
				Anuncio anuncio;
				
				
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
				
				dados.add("Renato");
				dados.add("Fortaleza");
				dados.add("Mondubim");
				dados.add("Super músico");
				dados.add("Toco muito bem. Pode me chamar que eu vou!");
				dados.add("Tocar em banda");
				dados.add("renato@teste.com");
				dados.add("renato@face.com");
				instrumentos.add(dao.findByEntityId(Instrumento.class, (long) 1)); //Sanfona
				instrumentos.add(dao.findByEntityId(Instrumento.class, (long) 6)); //Baixo
				estilosgosta.add(dao.findByEntityId(Estilo.class, (long) 1)); //Axé
				
				anuncio = new Anuncio(dados, instrumentos, estilosgosta, estilosnaogosta);
				Logger.debug(instrumentos.get(0).getDescricao()+" - "+anuncio.getInstrumentos().get(0).getDescricao());
				Logger.debug(estilosgosta.get(0).getDescricao()+" - "+anuncio.getGosta().get(0).getDescricao());
				dao.persist(anuncio);
				dao.flush();
				
				dados.clear();
				instrumentos = new ArrayList<Instrumento>();
				estilosgosta = new ArrayList<Estilo>();
				estilosnaogosta  = new ArrayList<Estilo>();
				dados.add("Fernando");
				dados.add("Campina Grande");
				dados.add("Catolé");
				dados.add("Vem que vem!");
				dados.add("Eai galera se vc ta procurando um cara pra sua banda pode parar de procurar pq vc achou!");
				dados.add("Tocar em banda");
				dados.add("fernando@teste.com");
				dados.add("fernando@face.com");
				instrumentos.add(dao.findByEntityId(Instrumento.class, (long) 115)); //Violão
				instrumentos.add(dao.findByEntityId(Instrumento.class, (long) 6)); //Baixo
				estilosgosta.add(dao.findByEntityId(Estilo.class, (long) 37)); //Pop/Rock
				estilosnaogosta.add(dao.findByEntityId(Estilo.class, (long) 2)); //Baião
				
				anuncio = new Anuncio(dados, instrumentos, estilosgosta, estilosnaogosta);
				Logger.debug(instrumentos.get(0).getDescricao()+" - "+anuncio.getInstrumentos().get(0).getDescricao());
				Logger.debug(estilosgosta.get(0).getDescricao()+" - "+anuncio.getGosta().get(0).getDescricao());
				dao.persist(anuncio);
				dao.flush();
				
			}
		});
	}
}
