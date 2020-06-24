package it.polito.tdp.artsmia.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graphs;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;

import it.polito.tdp.artsmia.db.ArtsmiaDAO;


public class Model {
	
	
	ArtsmiaDAO dao;
	SimpleDirectedGraph<Exhibition, DefaultEdge> grafo=null;
	Map<Integer,Exhibition> vertici;
	int numComponentiConnesse;
	
	
	
	
	
	
	public List<Integer> getAnni(){
		dao=new ArtsmiaDAO();
		return dao.anni();
	}
	
	
	public String creaGrafo(Integer anno) {
		String ritornare="";
		dao=new ArtsmiaDAO();
		grafo=new SimpleDirectedGraph<Exhibition, DefaultEdge>(DefaultEdge.class);
		vertici=new HashMap<>();
		vertici=dao.listExhibitions(anno);
		
		Graphs.addAllVertices(grafo, vertici.values());
		
		
		for(Exhibition ex: grafo.vertexSet()) {
			for(Exhibition ex2: grafo.vertexSet()) {
				//System.out.println("DENTRO1");
				if(ex.getId() < ex2.getId()) {
					//System.out.println("DENTRO "+ex.getInizio()+" "+ex2.getInizio());
					if(ex.getInizio()<ex2.getInizio() && ex.getFine()>=ex2.getInizio()) {
						//System.out.println("DENTRO1");
						Graphs.addEdgeWithVertices(grafo, ex, ex2);
					}
					else if(ex.getInizio()>ex2.getInizio() && ex2.getFine()>=ex.getInizio()) {
						//System.out.println("DENTRO2");
						Graphs.addEdgeWithVertices(grafo, ex2, ex);
					}
						
				}
			}
		}
		
		ritornare="GRAFO CREATO "+grafo.vertexSet().size()+" vertici e "+grafo.edgeSet().size()+" archi";
		return ritornare;
	}
	
	public boolean grafoConnesso() {
		
		ConnectivityInspector ispezione=new ConnectivityInspector(grafo);
    	List<Set<Exhibition>> componentiConnesse=new ArrayList<Set<Exhibition>>();
    	componentiConnesse=ispezione.connectedSets();
    	numComponentiConnesse=0;
    	/*
    	//devo trovare in quale componente connessa si trova il mio attore
    	for(Set<Exhibition> s:componentiConnesse) {
    		if(s.contains(a)) {
    			//scorro tutti gli attori nella componente connessa (eccetto il parametro)
    			for(Exhibition c:s) {
        			if(!c.equals(a)) {
        				simili.add(c);
        			}
        		}
    		}
    		
    		
    	}
    	*/
    	numComponentiConnesse=componentiConnesse.size();
    	if(numComponentiConnesse>1) {
    		return false;
    	}
    	else {
    		return true;
    	}
		
	}


	public int getNumComponentiConnesse() {
		return numComponentiConnesse;
	}
	
	public String mostraProlifica() {
		String ritornare="\nLA MOSTRA PIU' PROLIFICA E': ";
		dao=new ArtsmiaDAO();
		
		dao.opereExhibitions(vertici);
		
		List<Exhibition> lista=new ArrayList<>();
		for(Exhibition ex: vertici.values()) {
			ex.setNumeroOpere(ex.getOpere().size());
			lista.add(ex);
		}
		lista.sort(null);
		
		ritornare=ritornare+lista.get(0).getId()+" con opere "+lista.get(0).getNumeroOpere();
		return ritornare;
	}
	
	public String simula(Integer anno, int studenti) {
		Simulatore sim=new Simulatore(this);
		sim.setAnnoInizio(anno);
		sim.setStudenti(studenti);
		sim.init();
		sim.run();
		
		List<List<Integer>> lista=new ArrayList<>();
		lista=sim.studentiOpere;
		List<Integer> opereViste=new ArrayList<>();
		for(List<Integer> l: lista) {
			opereViste.add(l.size());
		}
		opereViste.sort(null);
		String ritornare="SIMULAZIONE: \n\n";
		for(int i=opereViste.size();i>0;i--) {
			
			ritornare=ritornare+opereViste.get(i-1)+"\n";
		}
		
		return ritornare;
	}
	

}
