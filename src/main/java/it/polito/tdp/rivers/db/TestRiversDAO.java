package it.polito.tdp.rivers.db;

import java.util.List;

import it.polito.tdp.rivers.model.River;

public class TestRiversDAO {

	public static void main(String[] args) {
		RiversDAO dao = new RiversDAO();
		List<River> listaFiumi = dao.getAllRivers();
		
		System.out.println(dao.getMesuramentByRiver(listaFiumi.get(2)).size());
	}

}
