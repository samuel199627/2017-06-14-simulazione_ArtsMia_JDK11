package it.polito.tdp.artsmia;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.artsmia.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ChoiceBox<Integer> boxAnno;

    @FXML
    private TextField txtFieldStudenti;

    @FXML
    private TextArea txtResult;

    @FXML
    void handleCreaGrafo(ActionEvent event) {
    	
    	txtResult.clear();
    	Integer selezionato=boxAnno.getValue();
    	if(selezionato==null) {
    		txtResult.appendText("DEVI SELEZIONARE UN ANNO");
    		return;
    	}
    	
    	txtResult.appendText(model.creaGrafo(selezionato));
    	txtResult.appendText("\nGRAFO CONNESSO: "+model.grafoConnesso()+"\nCOMPONENTI CONNESSE: "+model.getNumComponentiConnesse());
    	txtResult.appendText(model.mostraProlifica());

    }

    @FXML
    void handleSimula(ActionEvent event) {
    	txtResult.clear();
    	Integer selezionato=boxAnno.getValue();
    	if(selezionato==null) {
    		txtResult.appendText("DEVI SELEZIONARE UN ANNO");
    		return;
    	}
    	
    	int studenti=0;
    	try {
    		studenti=Integer.parseInt(txtFieldStudenti.getText());
    	}
    	catch(NumberFormatException e){
    		txtResult.appendText("PARAMETRO ERRATO");
    		return;
    	}
    	
    	txtResult.appendText(model.simula(selezionato, studenti));
    	
    }

    @FXML
    void initialize() {
        assert boxAnno != null : "fx:id=\"boxAnno\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert txtFieldStudenti != null : "fx:id=\"txtFieldStudenti\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Artsmia.fxml'.";

    }

	public void setModel(Model model) {
		this.model = model;
		boxAnno.getItems().clear();
		boxAnno.getItems().addAll(model.getAnni());
	}
}