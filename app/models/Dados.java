package models;

import java.util.ArrayList;
import java.util.List;

public class Dados {
	private String nome;
	private String cidade;
	private String bairro;
	private String titulo;
	private String descricao;
	private String interesse;
	private String codigo;
	private String email;
	private String facebook;
	
	public List<String> getDados(){
		List<String> dados = new ArrayList<String>();
		dados.add(nome);
		dados.add(cidade);
		dados.add(bairro);
		dados.add(titulo);
		dados.add(descricao);
		dados.add(interesse);
		dados.add(codigo);
		dados.add(email);
		dados.add(facebook);
		return dados;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getInteresse() {
		return interesse;
	}

	public void setInteresse(String interesse) {
		this.interesse = interesse;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFacebook() {
		return facebook;
	}

	public void setFacebook(String facebook) {
		this.facebook = facebook;
	}
}
