package models.dao;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Query;

import models.Anuncio;
import models.Estilo;
import models.Instrumento;
import play.db.jpa.JPA;

/**
 * Camada genérica para acesso ao Banco de Dados
 */
public class GenericDAO {

    /**
     * Salva um objeto no BD.
     */
	public boolean persist(Object e) {
		JPA.em().persist(e);
		return true;
	}

    /**
     * Força que as operações executadas até agora no código sejam
     * executadas no BD. Normalmente, o final de cada bloco @Transactional
     * fará o flush. Porém, se você quer controlar erros, melhor fazer antes.
     */
	public void flush() {
		JPA.em().flush();
	}

    /**
     * Atualiza a informação da entidade do código com a do banco de dados.
     *
     * Mais sobre merge: http://www.arquiteturacomputacional.eti.br/2013/02/entenda-o-ciclo-de-vida-das-entidades.html
     */
	public void merge(Object e) {
		JPA.em().merge(e);
	}

    /**
     * Procura uma certa {@code clazz} pelo seu {@code id}.
     */
	public <T> T findByEntityId(Class<T> clazz, Long id) {
		return JPA.em().find(clazz, id);
	}

    /**
     * Procura todos os objetos de uma certa classe pelo seu {@code className}
     * descrito na Entidade.
     */
	public <T> List<T> findAllByClass(Class clazz) {
		String hql = "FROM " + clazz.getName();
		Query hqlQuery = JPA.em().createQuery(hql);
		return hqlQuery.getResultList();
	}

    /**
     * Deleta do banco de dados uma {@code classe} referenciada pelo seu
     * {@code id}.
     */
	public <T> void removeById(Class<T> classe, Long id) {
		JPA.em().remove(findByEntityId(classe, id));
	}

    /**
     * Remove o respectivo {@code objeto} do banco de dados.
     */
	public void remove(Object objeto) {
		JPA.em().remove(objeto);
	}

    /**
     * Procura uma certa {@code className} pelo seu {@code attributeName}.
     */
	public <T> List<T> findByAttributeName(String className,
			String attributeName, String attributeValue) {
		String hql = "FROM " + className + " c" + " WHERE c." + attributeName
				+ " = '" + attributeValue + "'";
		Query hqlQuery = JPA.em().createQuery(hql);
		return hqlQuery.getResultList();
	}
	
	public List<Anuncio> findByInstrumento(List<Instrumento> instrumentos){
		List<Anuncio> list = findAllByClass(Anuncio.class);
		for (int i = list.size()-1; i >= 0; i--) {
			if(!list.get(i).getInstrumentos().containsAll(instrumentos)){
				list.remove(i);
			}
		}
		return list;
	}
	
	public List<Anuncio> findByEstilo(List<Estilo> estilos){
		List<Anuncio> list = findAllByClass(Anuncio.class);
		for (int i = list.size()-1; i >= 0; i--) {
			if(!list.get(i).getGosta().containsAll(estilos)){
				list.remove(i);
			}
		}
		return list;
	}
	
	public List<Anuncio> findByPalavraChave(String palavra){
		String hql = "FROM Anuncio c WHERE c.descricao LIKE '%"+ palavra + "%'";
		Query hqlQuery = JPA.em().createQuery(hql);
		return hqlQuery.getResultList();
	}
	
	public List<Anuncio> findAnuncio(List<Instrumento> instrumentos, List<Estilo> estilos, String palavra, String interesse){
		List<Anuncio> listinst = findByInstrumento(instrumentos);
		List<Anuncio> listesti = findByEstilo(estilos);
		List<Anuncio> listpal = findByPalavraChave(palavra);
		List<Anuncio> listinte;
		Set<Anuncio> lista = new LinkedHashSet<Anuncio>();
		if(interesse != null){
			Query hqlQuery = JPA.em().createQuery("FROM Anuncio c WHERE c.interesse = '"+ interesse + "'");
			listinte = hqlQuery.getResultList();
		}
		else{
			listinte = findAllByClass(Anuncio.class);
		}
		lista.addAll(listinst);
		lista.addAll(listesti);
		lista.addAll(listpal);
		lista.addAll(listinte);
		listinst.clear();
		listinst.addAll(lista);
		return listinst;
	}

	private Query createQuery(String query) {
		return JPA.em().createQuery(query);
	}
}
