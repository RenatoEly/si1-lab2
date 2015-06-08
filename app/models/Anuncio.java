package models;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Anuncio {
	private static final String[] DADOS = new String[]{"Nome","Cidade","Bairro","Título","Descrição","Interesse","Email","Facebook"};
	
	@Id
	@GeneratedValue(strategy= GenerationType.SEQUENCE)
	private long id;
	
	@Column
	private String nome;
	
	@Column
	private String titulo;
	
	@Column
	private String descricao;
	
	@Column
	private String cidade;
	
	@Column
	private String bairro;
	
	@ManyToMany
	@JoinTable
	private List<Instrumento> instrumentos;
	
	@ManyToMany
	@JoinTable(name="gosta")
	private List<Estilo> gosta;
	
	@ManyToMany
	@JoinTable(name="naogosta")
	private List<Estilo> naogosta;
	
	@Column
	private String interesse;
	
	@Column
	private String email;
	
	@Column
	private String facebook;
	
	@Column
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date datapublicacao;
	
	private Anuncio(){
		
	}
	
	public Anuncio(List<String> dados, List<Instrumento> instrumentos, List<Estilo> gosta, List<Estilo> naogosta){
		for (int i = 0; i < 6; i++) {
			if(dados.get(i) == null || dados.get(i).equals("")){
				throw new IllegalArgumentException("O campo "+DADOS[i]+" não pode estar vazio!");
			}
		}
		if(dados.get(6).equals("") || dados.get(7).equals("")){
			throw new IllegalArgumentException("Informe uma forma de contato!");
		}
		if(instrumentos.size() == 0){
			throw new IllegalArgumentException("Você deve informar ao menos um instrumento!");
		}
		setNome(dados.get(0));
		setCidade(dados.get(1));
		setBairro(dados.get(2));
		setTitulo(dados.get(3));
		setDescricao(dados.get(4));
		setInteresse(dados.get(5));
		setEmail(dados.get(6));
		setFacebook(dados.get(7));
		setInstrumentos(instrumentos);
		setGosta(gosta);
		setNaogosta(naogosta);
		setDataPublicacao(new Date());
	}
	
	public String getTitulo() {
		return titulo;
	}

	private void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	private void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	private void setCidade(String cidade) {
		this.cidade = cidade;
	}

	private void setBairro(String bairro) {
		this.bairro = bairro;
	}

	private void setInstrumentos(List<Instrumento> instrumentos) {
		this.instrumentos = instrumentos;
	}

	private void setGosta(List<Estilo> gosta) {
		this.gosta = gosta;
	}

	private void setNaogosta(List<Estilo> naogosta) {
		this.naogosta = naogosta;
	}

	private void setInteresse(String interesse) {
		this.interesse = interesse;
	}

	private void setEmail(String email) {
		this.email = email;
	}

	private void setFacebook(String facebook) {
		this.facebook = facebook;
	}
	
	
	private void setDataPublicacao(Date data){
		this.datapublicacao = data;
	}
	
	private void setNome(String nome){
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public String getCidade() {
		return cidade;
	}

	public String getBairro() {
		return bairro;
	}

	public List<Instrumento> getInstrumentos() {
		return instrumentos;
	}

	public List<Estilo> getGosta() {
		return gosta;
	}

	public List<Estilo> getNaogosta() {
		return naogosta;
	}

	public String getInteresse() {
		return interesse;
	}

	public String getEmail() {
		return email;
	}

	public String getFacebook() {
		return facebook;
	}
	
	
	public String getDataPublicacao(){
		return datapublicacao.toString();
	}
	
	public String getNome(){
		return nome;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Anuncio other = (Anuncio) obj;
		if (id != other.id)
			return false;
		return true;
	}
}