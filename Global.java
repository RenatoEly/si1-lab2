import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import models.Dados;
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
	private static final String[] interesse = new String[]{"Tocar em banda","Tocar ocasionalmente"};
	private static final int TOTAL_ANUNCIOS = 40;
	private static final int ANUNCIOS_FINALIZADOS = 15;

	@Override
	public void onStart(Application app) {
		Logger.info("Aplicação inicializada...");
		JPA.withTransaction(new play.libs.F.Callback0() {
			@Override
			public void invoke() throws Throwable {
				FileReader arq;
				BufferedReader lerArq;
				Set<Instrumento> x = new LinkedHashSet<Instrumento>();
				Set<Estilo> y = new LinkedHashSet<Estilo>();
				List<Instrumento> instrumentos;
				List<Estilo> estilosgosta;
				List<Estilo> estilosnaogosta;
				Dados dados = new Dados();
				Random r = new Random();
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

				for(int i = 1; i <= TOTAL_ANUNCIOS; i++){
					int aleatorio;
					dados.setNome("Usuário "+i);
					dados.setBairro("Bairro "+i);
					dados.setCidade("Cidade "+i);
					dados.setCodigo("aab"+i);
					dados.setDescricao("Anuncio automático "+i);
					dados.setEmail("email"+i+"@teste.com");
					dados.setFacebook("anuncio"+i+"@face.com");
					dados.setInteresse(interesse[i%2]);
					dados.setTitulo("Título anuncio "+i);

					aleatorio = r.nextInt(3) + 1;
					for(int j = 0; j < aleatorio; j++){
						x.add(dao.findByEntityId(Instrumento.class, (long) r.nextInt(123)+1));
					}
					instrumentos = new ArrayList<Instrumento>(x);
					x.clear();

					aleatorio = r.nextInt(4);
					for(int k = 0; k < aleatorio; k++){
						y.add(dao.findByEntityId(Estilo.class, (long) r.nextInt(63)+1));
					}
					estilosgosta= new ArrayList<Estilo>(y);
					y.clear();

					aleatorio = r.nextInt(4);
					for(int l = 0; l < aleatorio; l++){
						y.add(dao.findByEntityId(Estilo.class, (long) r.nextInt(63)+1));
					}
					estilosnaogosta = new ArrayList<Estilo>(y);
					estilosnaogosta.removeAll(estilosgosta);
					y.clear();
					
					anuncio = new Anuncio(dados,instrumentos,estilosgosta,estilosnaogosta);
					dao.persist(anuncio);
				}
				dao.flush();
				
				for (int z = 1; z <= ANUNCIOS_FINALIZADOS ; z++) {
					Logger.debug(String.valueOf(z));
					anuncio = dao.findByEntityId(Anuncio.class, (long) z);
					anuncio.setFinalizado(true);
					anuncio.setSucesso(true);
					dao.merge(anuncio);
				}
			}
		});
	}
}
