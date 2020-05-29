package it.polito.tdp.rivers.model;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;

import it.polito.tdp.rivers.model.Event.EventType;

public class Simulator {

	
	//PARAMETRI DI SIMULAZIONE
	List<Flow> listaFlussi;
	double capienzaTotale;
	double flussoMedio;
	double k;
	double F_MIN_OUT;
	double capacitaOccupata;
	double sommaCapacita;
	
	LocalDate giornoInizio;
	LocalDate giornoFine;
	Duration T_ARRIVAL;
	
	//OUTPUT
	List<LocalDate> listaGiorni;
	int numGiorniInsoddisfacenti;
	double capacitaMedia;
	
	//STATO DEL SISTEMA
	double F_OUT;
	double F_IN;
	
	//CODA DEGLI EVENTI
	PriorityQueue<Event> queue = new PriorityQueue<>();
	
	

	//INIZIALIZZAZIONE
	public void init() {
		
		listaGiorni = new ArrayList<>();
		sommaCapacita =0.0;
		capacitaOccupata = 0.0;
		capienzaTotale = k * flussoMedio * 30;
		capacitaOccupata = capienzaTotale/2;
		numGiorniInsoddisfacenti = 0;
		T_ARRIVAL = Duration.ofDays(1);
		F_MIN_OUT = 0.8 * flussoMedio;
		int cont = 0;
		
		while(giornoInizio.isBefore(giornoFine)) {
			Event e = new Event(giornoInizio, EventType.IN, listaFlussi.get(cont).getFlow());
			queue.add(e);
			cont++;
			giornoInizio = listaFlussi.get(cont).getDay();
		}
	}
	
	//RUN
	public void run() {
		while(!this.queue.isEmpty()) {
			Event e = this.queue.poll();
			processEvent(e);
			
		}
		
		
	}

	private void processEvent(Event e) {
		System.out.println("E: "+e.getType());
		switch(e.getType()) {
			
		case IN:
			//aggiungo il flusso e genero un evento OUT nello stesso giorno.
			capacitaOccupata += e.getFlusso();
			Random r = new Random();
			double probability = r.nextDouble();
			if(probability<=0.05) {
				double f_out = 10*F_MIN_OUT;
				this.queue.add(new Event(e.getTime(), EventType.OUT,f_out));
				
			}else {
				this.queue.add(new Event(e.getTime(), EventType.OUT,F_MIN_OUT));

			}
			break;
		case OUT:
			double controllo = capacitaOccupata - e.getFlusso();
			
			if(controllo > capienzaTotale) {
				//mi avanza una parte di acqua.
				capacitaOccupata = capienzaTotale;
				this.queue.add(new Event(e.getTime(), EventType.TRACIMAZIONE, (controllo- capienzaTotale)));
				
			}else if(controllo >= 0 && controllo < capienzaTotale) {
				capacitaOccupata = controllo;
				
			}else {
				capacitaOccupata = 0;
				
				listaGiorni.add(e.getTime());
				numGiorniInsoddisfacenti++;
				
			}
			
			sommaCapacita+=capacitaOccupata;
			break;
		case TRACIMAZIONE:
				System.out.println("TRACIMAZIONE nel giorno "+ e.getTime()+" con una perdita di "+e.getFlusso());
			break;
			
		
		}
		
	}

	public void setListaFlussi(List<Flow> listaFlussi) {
		this.listaFlussi = listaFlussi;
	}

	public void setFlussoMedio(double flussoMedio) {
		this.flussoMedio = flussoMedio;
	}

	public void setK(double k) {
		this.k = k;
	}

	public List<LocalDate> getListaGiorni() {
		return listaGiorni;
	}

	public int getNumGiorniInsoddisfacenti() {
		return numGiorniInsoddisfacenti;
	}

	public double getCapacitaMedia() {
		System.out.println("SOMMA: "+sommaCapacita+", capacitATOR "+capienzaTotale+", flussoMedio "+flussoMedio+",FMIN "+ F_MIN_OUT+" N FLUSSI "+listaFlussi.size());;
		
		return (sommaCapacita/listaFlussi.size());
	}
	
	
	
	
	
	
}
