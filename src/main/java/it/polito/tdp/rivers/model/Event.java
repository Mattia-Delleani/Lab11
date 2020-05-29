package it.polito.tdp.rivers.model;

import java.time.LocalDate;

public class Event implements Comparable<Event> {
	
	public enum EventType{
		IN, OUT, TRACIMAZIONE
	}
	
	LocalDate time;
	EventType type;
	double flusso;
	/**
	 * @param time
	 * @param type
	 */
	public Event(LocalDate time, EventType type, double flusso) {
		super();
		this.time = time;
		this.type = type;
		this.flusso = flusso;
	}
	
	
	public LocalDate getTime() {
		return time;
	}
	public EventType getType() {
		return type;
	}
	
	public double getFlusso() {
		return flusso;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((time == null) ? 0 : time.hashCode());
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
		Event other = (Event) obj;
		if (time == null) {
			if (other.time != null)
				return false;
		} else if (!time.equals(other.time))
			return false;
		return true;
	}


	@Override
	public int compareTo(Event other) {
		// TODO Auto-generated method stub
		return this.time.compareTo(other.getTime());
	}
	
	

}
