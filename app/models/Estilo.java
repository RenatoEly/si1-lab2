package models;

import javax.persistence.*;

@Entity 
public class Estilo {
	
	@Id
	@GeneratedValue
	private long id;
	
	@Column
	private String descricao;

	protected Estilo(){
		
	}
	
	public Estilo(String descricao){
		setDescricao(descricao);
	}
	
	private void setId(long id) {
		this.id = id;
	}

	private void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public long getId() {
		return id;
	}

	public String getDescricao() {
		return descricao;
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
		Estilo other = (Estilo) obj;
		if (id != other.id)
			return false;
		return true;
	}
}
