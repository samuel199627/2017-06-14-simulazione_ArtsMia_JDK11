package it.polito.tdp.artsmia.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;

import it.polito.tdp.artsmia.model.Evento.Tipo;

public class Simulatore {
	
	Model m;
	PriorityQueue<Evento> coda;
	SimpleDirectedGraph<Exhibition, DefaultEdge> grafo=null;
	List<Exhibition> listaVertici;
	Map<Integer,Exhibition> vertici;
	int annoInizio=0;
	int studenti=0;
	List<List<Integer>> studentiOpere;
	List<List<Exhibition>> studentiMostre;
	
	
	public void setStudenti(int studenti) {
		this.studenti = studenti;
	}

	public void setAnnoInizio(int annoInizio) {
		this.annoInizio = annoInizio;
	}

	public Simulatore(Model model) {
		m=model;
	}
	
	public void init() {
		coda=new PriorityQueue<Evento>();
		
		grafo = new SimpleDirectedGraph<Exhibition, DefaultEdge>(DefaultEdge.class);
		this.grafo=m.grafo;
		vertici=new HashMap<>();
		this.vertici=m.vertici;
		listaVertici=new ArrayList<>();
		for(Exhibition ex: vertici.values()) {
			listaVertici.add(ex);
		}
		
		studentiOpere=new ArrayList<>();
		studentiMostre=new ArrayList<>();
		for(int i=0;i<studenti;i++) {
			List<Integer> listaOpereStudente=new ArrayList<>();
			studentiOpere.add(listaOpereStudente);
			List<Exhibition> listaMostreStudente=new ArrayList<>();
			studentiMostre.add(listaMostreStudente);
		}
		
		
		boolean inizio=false;
		
		while(!inizio) {
			double rand=Math.random(); 
			//System.out.println(""+rand);
			int indice= (int) (rand*listaVertici.size());
			//System.out.println(""+rand+" "+indice+" "+annoInizio+" "+listaVertici.get(indice).getInizio());
			if(listaVertici.get(indice).getInizio()==annoInizio) {
				inizio=true;
				for(int i=0;i<studenti;i++) {
					//studentiOpere.get(i)=new ArrayList<Integer>();
					//studentiMostre.get(i)=new ArrayList<>();
					coda.add(new Evento(0,Tipo.VISITA,listaVertici.get(indice),i));
				}
				
			}
		}
	}
	
	public void run() {
		while(!coda.isEmpty()) {
			Evento e=coda.poll();
			ProcessEvent(e);
		}
		
	}
	
	public void ProcessEvent(Evento e) {
		studentiMostre.get(e.getStudente()).add(e.getMostra());
		for(Integer opera: e.getMostra().getOpere()) {
			if(!studentiOpere.get(e.getStudente()).contains(opera)) {
				studentiOpere.get(e.getStudente()).add(opera);
			}
		}
		//prossima visita Studente
		List<Exhibition> vicini=new ArrayList<>();
		vicini=Graphs.neighborListOf(grafo, e.getMostra());
		List<Exhibition> viciniRaggiungibili=new ArrayList<>();
		for(Exhibition ex: vicini) {
			if(!studentiMostre.get(e.getStudente()).contains(ex)) {
				viciniRaggiungibili.add(ex);
			}
		}
		if(viciniRaggiungibili.size()>0) {
			double rand=Math.random(); 
			//System.out.println(""+rand);
			int indice= (int) (rand*viciniRaggiungibili.size());
			coda.add(new Evento(e.getTime()+1,Tipo.VISITA,viciniRaggiungibili.get(indice),e.getStudente()));
		}
		
		
		
	}
	

}
