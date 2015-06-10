package unidade;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import models.Anuncio;
import models.Dados;
import models.Estilo;
import models.Instrumento;

import org.junit.Assert;
import org.junit.Test;

public class TestAnuncio {
	List<Estilo> estilosnaogosta;
	List<Instrumento> instrumentos;
	List<Estilo> estilosgosta;
	Dados dados;
	Anuncio anuncio;
	
	@Test
	public void criarAnuncioComDadosFaltando() {
		dados = new Dados();
		dados.setBairro("");
		dados.setCidade("");
		dados.setCodigo("");
		dados.setDescricao("");
		dados.setEmail("");
		dados.setFacebook("");
		dados.setInteresse("");
		dados.setNome("");
		dados.setTitulo("");
		instrumentos = new ArrayList<Instrumento>();
		instrumentos.add(new Instrumento("teste"));
		estilosgosta = new ArrayList<Estilo>();
		estilosnaogosta = new ArrayList<Estilo>();
		
		try{
			anuncio = new Anuncio(dados,instrumentos,estilosgosta,estilosnaogosta);
			fail("Devia ter lançado exceção");
		}catch(IllegalArgumentException e){
			Assert.assertTrue(e.getMessage().equals("O campo Nome não pode estar vazio!"));
		}
		
		dados = new Dados();
		dados.setBairro("a");
		dados.setCidade("b");
		dados.setCodigo("c");
		dados.setDescricao("d");
		dados.setEmail("e");
		dados.setFacebook("f");
		dados.setInteresse("g");
		dados.setNome("h");
		dados.setTitulo("");
		
		try{
			anuncio = new Anuncio(dados,instrumentos,estilosgosta,estilosnaogosta);
			fail("Devia ter lançado exceção");
		}catch(IllegalArgumentException e){
			Assert.assertTrue(e.getMessage().equals("O campo Título não pode estar vazio!"));
		}
		
		dados = new Dados();
		dados.setBairro("a");
		dados.setCidade("b");
		dados.setCodigo("c");
		dados.setDescricao("d");
		dados.setEmail("");
		dados.setFacebook("");
		dados.setInteresse("g");
		dados.setNome("h");
		dados.setTitulo("i");
		
		try{
			anuncio = new Anuncio(dados,instrumentos,estilosgosta,estilosnaogosta);
			fail("Devia ter lançado exceção");
		}catch(IllegalArgumentException e){
			Assert.assertTrue(e.getMessage().equals("Informe ao menos uma forma de contato!"));
		}
		
		dados = new Dados();
		dados.setBairro("a");
		dados.setCidade("b");
		dados.setCodigo("c");
		dados.setDescricao("d");
		dados.setEmail("a");
		dados.setFacebook("");
		dados.setInteresse("g");
		dados.setNome("h");
		dados.setTitulo("i");
		
		try{
			anuncio = new Anuncio(dados,instrumentos,estilosgosta,estilosnaogosta);
			fail("Devia ter lançado exceção");
		}catch(IllegalArgumentException e){
			Assert.assertTrue(e.getMessage().equals("Você deve informar ao menos um instrumento!"));
		}
		
		dados = new Dados();
		dados.setBairro("a");
		dados.setCidade("b");
		dados.setCodigo("c");
		dados.setDescricao("d");
		dados.setEmail("");
		dados.setFacebook("a");
		dados.setInteresse("g");
		dados.setNome("h");
		dados.setTitulo("i");
		
		try{
			anuncio = new Anuncio(dados,instrumentos,estilosgosta,estilosnaogosta);
			fail("Devia ter lançado exceção");
		}catch(IllegalArgumentException e){
			Assert.assertTrue(e.getMessage().equals("Você deve informar ao menos um instrumento!"));
		}
	}
	
	@Test
	public void criarAnuncioSemInstrumento(){
		dados = new Dados();
		dados.setBairro("a");
		dados.setCidade("b");
		dados.setCodigo("c");
		dados.setDescricao("d");
		dados.setEmail("e");
		dados.setFacebook("f");
		dados.setInteresse("g");
		dados.setNome("h");
		dados.setTitulo("a");
		instrumentos = new ArrayList<Instrumento>();
		estilosgosta = new ArrayList<Estilo>();
		estilosnaogosta = new ArrayList<Estilo>();
		
		try{
			anuncio = new Anuncio(dados,instrumentos,estilosgosta,estilosnaogosta);
			fail("Devia ter lançado exceção");
		}catch(IllegalArgumentException e){
			Assert.assertTrue(e.getMessage().equals("Você deve informar ao menos um instrumento!"));
		}
	}
	
	@Test
	public void criarAnuncioSemEstilos(){
		dados = new Dados();
		dados.setBairro("a");
		dados.setCidade("b");
		dados.setCodigo("c");
		dados.setDescricao("d");
		dados.setEmail("e");
		dados.setFacebook("f");
		dados.setInteresse("g");
		dados.setNome("h");
		dados.setTitulo("a");
		instrumentos = new ArrayList<Instrumento>();
		instrumentos.add(new Instrumento("teste"));
		estilosgosta = new ArrayList<Estilo>();
		estilosnaogosta = new ArrayList<Estilo>();
	}
}
