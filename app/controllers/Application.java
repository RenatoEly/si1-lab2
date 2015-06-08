package controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import models.Anuncio;
import models.Estilo;
import models.Instrumento;
import models.dao.GenericDAO;
import play.Logger;
import play.data.DynamicForm;
import play.data.Form;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import scala.Array;

public class Application extends Controller {
	private static final GenericDAO dao = new GenericDAO();
	private static List<Instrumento> instruments;
	private static List<Estilo> estilosgosta;
	private static List<Estilo> estilosnaogosta;
	private static List<String> dados;

	@Transactional
	public static Result index() {
		List<Anuncio> anuncios = dao.findAllByClass(Anuncio.class);
		List<Estilo> estilos = dao.findAllByClass(Estilo.class);
		List<Instrumento> instrumentos = dao.findAllByClass(Instrumento.class);
		return ok(views.html.index.render(anuncios,estilos,instrumentos));
	}

	@Transactional
	public static Result addAnuncio(){
		instruments = new ArrayList<Instrumento>();
		estilosgosta = new ArrayList<Estilo>();
		estilosnaogosta = new ArrayList<Estilo>();
		dados = new ArrayList<String>();
		DynamicForm filledForm = Form.form().bindFromRequest();
		Map<String,String[]> map = request().body().asFormUrlEncoded();
		String[] instrumentos = map.get("instrumentos");
		String[] gosta = map.get("gosta");
		String[] naogosta = map.get("naogosta");
		if(instrumentos != null){
			for (String string : instrumentos) {
				instruments.add(dao.findByEntityId(Instrumento.class, Long.parseLong(string)));
			}
		}
		if(gosta != null){
			for (String string : gosta) {
				estilosgosta.add(dao.findByEntityId(Estilo.class, Long.parseLong(string)));
			}
		}
		if(naogosta != null){
			for (String string : naogosta) {
				estilosnaogosta.add(dao.findByEntityId(Estilo.class, Long.parseLong(string)));
			}
		}
		dados.add(filledForm.get("nome"));
		dados.add(filledForm.get("cidade"));
		dados.add(filledForm.get("bairro"));
		dados.add(filledForm.get("titulo"));
		dados.add(filledForm.get("descricao"));
		dados.add(filledForm.get("interesse"));
		dados.add(filledForm.get("email"));
		dados.add(filledForm.get("facebook"));
		
		try{
			Anuncio newanuncio = new Anuncio(dados, instruments, estilosgosta, estilosnaogosta);
			dao.persist(newanuncio);
		} catch(IllegalArgumentException e){
			//TODO colocar mensagem na tela
			return badRequest(views.html.cadastro.render(dao.findAllByClass(Instrumento.class),dao.findAllByClass(Estilo.class)));
		}
		return redirect(routes.Application.index());
	}

	@Transactional
	public static Result anuncio(){
		List<Instrumento> instrumentos = dao.findAllByClass(Instrumento.class);
		List<Estilo> estilos = dao.findAllByClass(Estilo.class);
		return ok(views.html.cadastro.render(instrumentos,estilos));
	}
	
	@Transactional
	public static Result pesquisar(){
		instruments = new ArrayList<Instrumento>();
		estilosgosta = new ArrayList<Estilo>();
		
		DynamicForm filledForm = Form.form().bindFromRequest();
		Map<String,String[]> map = request().body().asFormUrlEncoded();
		String[] instrumentos = map.get("instrumentos");
		String[] estilos = map.get("estilos");
		String palavrachave = filledForm.get("palavrachave");
		String interesse = filledForm.get("interesse");
		
		if(instrumentos != null){
			for (String string : instrumentos) {
				instruments.add(dao.findByEntityId(Instrumento.class, Long.parseLong(string)));
			}
		}
		if(estilos != null){
			for (String string : estilos) {
				estilosgosta.add(dao.findByEntityId(Estilo.class, Long.parseLong(string)));
			}
		}
		if(palavrachave == null){
			palavrachave = "";
		}

		List<Anuncio> anuncios = dao.findAnuncio(instruments, estilosgosta, palavrachave, interesse);
		return ok(views.html.index.render(anuncios,dao.findAllByClass(Estilo.class),dao.findAllByClass(Instrumento.class)));
	}
}