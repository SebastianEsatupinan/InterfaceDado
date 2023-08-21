package interfacedado;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLDocumentController implements Initializable {
    
    Timer contador = new Timer();
    
    @FXML
    private Label labelTitulo;

    @FXML
    private Label labelD1;

    @FXML
    private Label labelD2;
    
    
    @FXML
    private TextField ValorD1;
    
    @FXML
    private TextField ValorD2;
     
    @FXML
    private TextArea AreaTextList;
    
    
    @FXML
    private void IniciarTiradas(ActionEvent event) {
        
        
        empezarTirada();
  
    }
    
    @FXML
    private void PararTirada(ActionEvent event) {
        detenerTirada();
    }
    
    @FXML
    private void TraerDatos(ActionEvent event) {
        
    }
    
    @FXML
    private void GuardarDatos(ActionEvent event) {
        
    }
    
    public void empezarTirada(){
        
        Random ran = new Random();
        
        IniciarTiradas(ActionEvent event).setEnabled(false);
        PararTirada(ActionEvent event).setEnabled(true);

        
        
        TimerTask tarea = new TimerTask(){
            @Override
            public void run() {
                
        ValorD1.setText(ran.nextInt(6) + 1);
        ValorD2.setText(ran.nextInt(6) + 1);
            }
        };
      
        contador.scheduleAtFixedRate(tarea, 0, 4000);

    }
    
    public void detenerTirada(){
        IniciarTiradas(ActionEvent event).setEnabled(true);
        PararTirada(ActionEvent event).setEnabled(false);
        contador.cancel();
    }
    
    
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
}
