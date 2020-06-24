package it.polito.tdp.artsmia.model;

import java.util.ArrayList;
import java.util.List;

public class Exhibition implements Comparable<Exhibition>{
	
	private int id;
	private String dipartimento;
	private String titolo;
	private int inizio;
	private int fine;
	private int numeroOpere;
	private List<Integer> opere;
	
	public Exhibition(int id, String dipartimento, String titolo, int inizio, int fine) {
		super();
		this.id = id;
		this.dipartimento = dipartimento;
		this.titolo = titolo;
		this.inizio = inizio;
		this.fine = fine;
		numeroOpere=0;
		opere=new ArrayList<>();
	}
	
	public void addOpera(Integer i) {
		opere.add(i);
	}
	

	public List<Integer> getOpere() {
		return opere;
	}




	public int getNumeroOpere() {
		return numeroOpere;
	}



	public void setNumeroOpere(int numeroOpere) {
		this.numeroOpere = numeroOpere;
	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDipartimento() {
		return dipartimento;
	}

	public void setDipartimento(String dipartimento) {
		this.dipartimento = dipartimento;
	}

	public String getTitolo() {
		return titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	public int getInizio() {
		return inizio;
	}

	public void setInizio(int inizio) {
		this.inizio = inizio;
	}

	public int getFine() {
		return fine;
	}

	public void setFine(int fine) {
		this.fine = fine;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
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
		Exhibition other = (Exhibition) obj;
		if (id != other.id)
			return false;
		return true;
	}



	@Override
	public int compareTo(Exhibition o) {
		// TODO Auto-generated method stub
		return o.getNumeroOpere()-this.getNumeroOpere();
	}
	
	
	
	

}
