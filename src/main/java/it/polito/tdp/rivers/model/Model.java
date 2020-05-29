package it.polito.tdp.rivers.model;

import java.util.List;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import it.polito.tdp.rivers.db.RiversDAO;

public class Model {
	
	Map<Integer, Flow>idMap;
	RiversDAO dao;
	List<Flow> listaMisurazioni;
	double flussoMedio;
	
	Simulator sim;
	public Model() {
		
		idMap= new HashMap<>();
		dao = new RiversDAO();
		flussoMedio = 0.0;
	}
	
	public void simulazione(double k) {
		
		sim = new Simulator();
		//inizializzo i parametri
		sim.setFlussoMedio(flussoMedio);
		sim.setK(k);
		sim.setListaFlussi(listaMisurazioni);
		sim.giornoInizio = this.getDataInizio();
		sim.giornoFine = this.getDataFine();
		
		//eseguo la simulazione
		sim.init();
		sim.run();
		
		
		
	}
	
	
	//carico il flusso medio
	public void setFlussoMedio() {
		double somma = 0.0;
		for(Flow f: listaMisurazioni) {
			somma+= f.getFlow();
			
		}
		double flusso = somma/(listaMisurazioni.size());
		this.flussoMedio = flusso;
	}



	//carico la lista di fiumi
	public List<River> getRivers(){
		
		return dao.getAllRivers();
	}
	//carico le date inizio
	public LocalDate getDataInizio() {
		
		return listaMisurazioni.get(0).getDay();
		
	}
	//carico date di fine
	public LocalDate getDataFine() {
		
		return listaMisurazioni.get(listaMisurazioni.size()-1).getDay();
		
	}

	public void setListaMisurazioni(River river) {
				
		this.listaMisurazioni = dao.getMesuramentByRiver(river);
	}

	public List<Flow> getListaMisurazioni() {
		return listaMisurazioni;
	}


	public double getFlussoMedio() {
		// TODO Auto-generated method stub
		return this.flussoMedio;
	}
	
	public int getNumInsoddisfatti() {
		
		return this.sim.getNumGiorniInsoddisfacenti();
	}
	
	public List<LocalDate> getGiorniInsoddisfacienti(){
		
		return this.sim.getListaGiorni();
	}
	
	public double getCapacitaMedia() {
		
		return this.sim.getCapacitaMedia();
	}
	

}
