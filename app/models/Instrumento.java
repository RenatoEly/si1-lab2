package models;

import javax.persistence.*;

@Entity
public class Instrumento {
	
	@Id
	@GeneratedValue
	private long id;
	
	@Column
	private String descricao;
	
	private Instrumento(){
		
	}

	public Instrumento(String descricao){
		setDescricao(descricao);
	}
	
	
	public long getId() {
		return id;
	}

	private void setId(long id) {
		this.id = id;
	}
	
	public String getDescricao(){
		return descricao;
	}
	
	private void setDescricao(String descricao){
		this.descricao = descricao;
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
		Instrumento other = (Instrumento) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
}
