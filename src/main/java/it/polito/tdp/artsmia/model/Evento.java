package it.polito.tdp.artsmia.model;

public class Evento implements Comparable<Evento>{
	
	public enum Tipo{
		VISITA
	}
	
	private int time;
	private Tipo type;
	private Exhibition mostra;
	private int studente;
	
	public Evento(int time, Tipo type, Exhibition mostra, int studente) {
		super();
		this.time = time;
		this.type = type;
		this.mostra = mostra;
		this.studente = studente;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public Tipo getType() {
		return type;
	}

	public void setType(Tipo type) {
		this.type = type;
	}

	public Exhibition getMostra() {
		return mostra;
	}

	public void setMostra(Exhibition mostra) {
		this.mostra = mostra;
	}

	public int getStudente() {
		return studente;
	}

	public void setStudente(int studente) {
		this.studente = studente;
	}

	@Override
	public int compareTo(Evento o) {
		// TODO Auto-generated method stub
		return this.getTime()-o.getTime();
	}
	
	
	

}
