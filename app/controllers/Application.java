package controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import models.Anuncio;
import models.Dados;
import models.Estilo;
import models.Instrumento;
import models.dao.GenericDAO;
import play.data.DynamicForm;
import play.data.Form;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;

public class Application extends Controller {
	private static final GenericDAO dao = new GenericDAO();
	private static List<Instrumento> instrumentos;
	private static List<Estilo> estilosgosta;
	private static List<Estilo> estilosnaogosta;

	@Transactional
	public static Result index() {
		List<Anuncio> anuncios = dao.getAnuncios();
		List<Estilo> estilos = dao.findAllByClass(Estilo.class);
		List<Instrumento> instrumentos = dao.findAllByClass(Instrumento.class);
		
		return ok(views.html.index.render());
	}

	@Transactional
	public static Result addAnuncio(){
		instrumentos = new ArrayList<Instrumento>();
		estilosgosta = new ArrayList<Estilo>();
		estilosnaogosta = new ArrayList<Estilo>();

        Form<Dados> oForm = Form.form(Dados.class);
        Dados dados = oForm.bindFromRequest().get();
        
		Map<String,String[]> map = request().body().asFormUrlEncoded();
		String[] idinstrumentos = map.get("instrumentos");
		String[] idestilosgosta = map.get("gosta");
		String[] idestilosnaogosta = map.get("naogosta");
		
		if(idinstrumentos != null){
			for (String id : idinstrumentos) {
				instrumentos.add(dao.findByEntityId(Instrumento.class, Long.parseLong(id)));
			}
		}
		if(idestilosgosta != null){
			for (String id : idestilosgosta) {
				estilosgosta.add(dao.findByEntityId(Estilo.class, Long.parseLong(id)));
			}
		}
		if(idestilosnaogosta != null){
			for (String id : idestilosnaogosta) {
				estilosnaogosta.add(dao.findByEntityId(Estilo.class, Long.parseLong(id)));
			}
		}
		
		try{
			Anuncio newanuncio = new Anuncio(dados, instrumentos, estilosgosta, estilosnaogosta);
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
		instrumentos = new ArrayList<Instrumento>();
		estilosgosta = new ArrayList<Estilo>();
		
		DynamicForm filledForm = Form.form().bindFromRequest();
		Map<String,String[]> map = request().body().asFormUrlEncoded();
		String[] idinstrumentos = map.get("instrumento");
		String[] idestilosgosta = map.get("estilo");
		String palavrachave = filledForm.get("palavrachave");
		String interesse = filledForm.get("interesse");
		
		if(idinstrumentos != null){
			for (String id : idinstrumentos) {
				instrumentos.add(dao.findByEntityId(Instrumento.class, Long.parseLong(id)));
			}
		}
		if(idestilosgosta != null){
			for (String id : idestilosgosta) {
				estilosgosta.add(dao.findByEntityId(Estilo.class, Long.parseLong(id)));
			}
		}
		if(palavrachave == null){
			palavrachave = "";
		}
		
		List<Anuncio> anuncios = dao.findAnuncio(instrumentos, estilosgosta, palavrachave, interesse);
		return ok(views.html.index.render(anuncios,dao.findAllByClass(Estilo.class),dao.findAllByClass(Instrumento.class)));
	}
	
	@Transactional
	public static Result finaliza(Long id){
		Anuncio anuncio = dao.findByEntityId(Anuncio.class, id.longValue());
		return ok(views.html.finaliza.render(anuncio));
	}
	
	@Transactional
	public static Result finalizarAnuncio(){
		DynamicForm filledForm = Form.form().bindFromRequest();
		long idanuncio = Long.parseLong(filledForm.get("id"));
		String codigo = filledForm.get("codigo");
		boolean isSucesso = Boolean.parseBoolean(filledForm.get("sucesso"));
		
		try{
			dao.finalizarAnuncio(idanuncio,codigo,isSucesso);
		}catch(IllegalArgumentException e){
			//TODO fazer aparecer mensagem na tela
			return badRequest(views.html.finaliza.render(dao.findByEntityId(Anuncio.class, idanuncio)));
		}
		return redirect(routes.Application.index());
	}
}