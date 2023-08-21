package interfacedado;

import dato.Lanzamiento;
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
import modelo.Pila;

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
    
    Pila<Lanzamiento> pilaLanzamientos = new Pila<>();
       
    @FXML
    private void IniciarTiradas(ActionEvent event) {   
        
     Random ran = new Random();
        
     IniciarTiradas.setDisable(true);
     PararTirada.setDisable(false);

        
        
        TimerTask tarea = new TimerTask(){
            @Override
            public void run() {

                int valorDado1 = ran.nextInt(6) + 1;
                int valorDado2 = ran.nextInt(6) + 1;
                
                Lanzamiento objD = new Lanzamiento(valorDado1, valorDado2);
                
                pilaLanzamientos.apilar(objD);

                ValorD1.setText(String.valueOf(valorDado1));
                ValorD2.setText(String.valueOf(valorDado2));
            }
            };
      
        contador.scheduleAtFixedRate(tarea, 0, 4000);

    }
    
    @FXML
    private void PararTirada(ActionEvent event) {
        // Habilitar el botón de iniciar y deshabilitar el de parar
        IniciarTiradas.setDisable(false);
        PararTirada.setDisable(true);

        // Cancelar el temporizador
        contador.cancel();
        // Crear un nuevo temporizador para futuras tiradas
        contador = new Timer();
    }
    
    @FXML
    private void TraerDatos(ActionEvent event) {
        
    }
    
    @FXML
    private void GuardarDatos(ActionEvent event) {
        
    }
      
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
}
