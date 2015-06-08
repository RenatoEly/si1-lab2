package funcional;

import static org.fest.assertions.Assertions.assertThat;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import models.Anuncio;
import models.Estilo;
import models.Instrumento;
import models.dao.GenericDAO;

import org.junit.Before;
import org.junit.Test;

import base.AbstractTest;

public class TesteBD extends AbstractTest{
	GenericDAO dao = new GenericDAO();
	Estilo estilo;
	Instrumento instrumento;
	List<Instrumento> instrumentos;
	List<Estilo> estilos;
	Long id = (long) 1;
	Long id2 = (long) 2;
	List<Estilo> listaestilos = new ArrayList<Estilo>();
	List<Estilo> listaestilos2 = new ArrayList<Estilo>();
	List<Estilo> naogosta = new ArrayList<Estilo>();
	List<String> dados = new ArrayList<String>();
	
	@Test
	public void pesquisarPorInstrumento() {
		instrumentos = new ArrayList<Instrumento>();
		instrumento = dao.findByEntityId(Instrumento.class, id2);
		instrumentos.add(instrumento);
		
		List<Anuncio> anuncios = dao.findByInstrumento(instrumentos);
		
		assertThat(anuncios.size()).isEqualTo(0);
		
		instrumento = dao.findByEntityId(Instrumento.class, id);
		instrumentos = new ArrayList<Instrumento>();
		instrumentos.add(instrumento);
		
		anuncios = dao.findByInstrumento(instrumentos);
		assertThat(anuncios.size()).isNotEqualTo(0);
		assertThat(anuncios.get(0).getInstrumentos().equals(instrumentos));
	}
	
	@Test
	public void persquisarPorEstilo(){
		estilos = new ArrayList<Estilo>();
		estilos.add(dao.findByEntityId(Estilo.class, id));
		List<Anuncio> anuncios = dao.findByEstilo(estilos);
		
		assertThat(anuncios.size()).isNotEqualTo(0);
		assertThat(anuncios.get(0).getGosta().equals(estilos));
	}
	
	@Test
	public void pesquisarPorPalavra(){
		List<Anuncio> anuncios = dao.findByPalavraChave("Des");
		
		assertThat(anuncios.size()).isNotEqualTo(0);
		assertThat(anuncios.get(0).getDescricao().contains("Des"));
	}
	
	@Before
	public void invoke() {
		FileReader arq;
		BufferedReader lerArq;
		instrumentos = new ArrayList<Instrumento>();
		
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
		
		instrumentos.add(dao.findByEntityId(Instrumento.class, id));
		dados.add("Autor");
		dados.add("Cidade");
		dados.add("Bairro");
		dados.add("Título");
		dados.add("Descrição");
		dados.add("Interesse");
		dados.add("Email");
		dados.add("Facebook");
		
		dao.persist(new Anuncio(dados,instrumentos,listaestilos,naogosta));
		dao.flush();
		
		instrumentos = new ArrayList<Instrumento>();
		dados = new ArrayList<String>();
		listaestilos = new ArrayList<Estilo>();
		naogosta = new ArrayList<Estilo>();
		instrumentos.add(dao.findByEntityId(Instrumento.class, id));
		dados.add("Autor");
		dados.add("Cidade");
		dados.add("Bairro");
		dados.add("Título");
		dados.add("Descrição");
		dados.add("Interesse");
		dados.add("Email");
		dados.add("Facebook");
		listaestilos.add(dao.findByEntityId(Estilo.class, id));
		
		dao.persist(new Anuncio(dados,instrumentos,listaestilos,naogosta));
		dao.flush();
		
		
	}

}
