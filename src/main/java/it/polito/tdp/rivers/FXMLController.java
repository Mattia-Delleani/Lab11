/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.rivers;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import it.polito.tdp.rivers.model.Model;
import it.polito.tdp.rivers.model.River;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="boxRiver"
    private ComboBox<River> boxRiver; // Value injected by FXMLLoader

    @FXML // fx:id="txtStartDate"
    private TextField txtStartDate; // Value injected by FXMLLoader

    @FXML // fx:id="txtEndDate"
    private TextField txtEndDate; // Value injected by FXMLLoader

    @FXML // fx:id="txtNumMeasurements"
    private TextField txtNumMeasurements; // Value injected by FXMLLoader

    @FXML // fx:id="txtFMed"
    private TextField txtFMed; // Value injected by FXMLLoader

    @FXML // fx:id="txtK"
    private TextField txtK; // Value injected by FXMLLoader

    @FXML // fx:id="btnSimula"
    private Button btnSimula; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader
    
    @FXML
    void choice(ActionEvent event) {
    	//pulisco i dati
    	txtStartDate.clear();
    	txtEndDate.clear();
    	txtNumMeasurements.clear();
    	txtFMed.clear();
    	//li inizializzo
    	model.setListaMisurazioni(boxRiver.getValue());
    	model.setFlussoMedio();
    	
    	//li metto in output
    	txtStartDate.setText(model.getDataInizio().toString());
    	txtEndDate.setText(model.getDataFine().toString());
    	txtNumMeasurements.setText(""+(model.getListaMisurazioni().size()+""));
    	txtFMed.setText(""+model.getFlussoMedio()+"");
    	
    	btnSimula.setDisable(false);
    	
    }
    
    @FXML
    void simula(ActionEvent event) {
    	txtResult.clear();
    	double k =0;
    	try {
    		k = Double.parseDouble(txtK.getText());
    	}catch(NumberFormatException nfe) {
    		
    		txtResult.setText("ECCEZIONE! Introdurre un numero!");
    	}
    	if(k>0) {
    		this.model.simulazione(k);
    		txtResult.appendText("Numero di giorni inoddisfatti: "+ this.model.getNumInsoddisfatti());
    		txtResult.appendText("\nCapacità media: "+ this.model.getCapacitaMedia());
    		txtResult.appendText("\n\nGiorni non soddisfatti:");
    		for(LocalDate g: this.model.getGiorniInsoddisfacienti()) {
    			txtResult.appendText("\n"+g.toString());
    		}
    		
    	}
    	

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert boxRiver != null : "fx:id=\"boxRiver\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtStartDate != null : "fx:id=\"txtStartDate\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtEndDate != null : "fx:id=\"txtEndDate\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtNumMeasurements != null : "fx:id=\"txtNumMeasurements\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtFMed != null : "fx:id=\"txtFMed\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtK != null : "fx:id=\"txtK\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnSimula != null : "fx:id=\"btnSimula\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
    }
    
    public void setModel(Model model) {
    	this.model = model;
    	boxRiver.getItems().addAll(model.getRivers());
    	
    	btnSimula.setDisable(true);
    	
    }
}
