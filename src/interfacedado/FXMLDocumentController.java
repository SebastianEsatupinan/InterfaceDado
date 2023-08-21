package interfacedado;

import dato.Lanzamiento;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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
        
     //IniciarTiradas.setDisable(true);
     //PararTirada.setDisable(false);
     

             
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
        //IniciarTiradas.setDisable(false);
        //PararTirada.setDisable(true);

        // Cancelar el temporizador
        contador.cancel();
        
        AreaTextList.setText(pilaLanzamientos.toString());
        // Crear un nuevo temporizador para futuras tiradas
        contador = new Timer();
    }
    
    @FXML
    private void TraerDatos(ActionEvent event) {
        try (BufferedReader reader = new BufferedReader(new FileReader("lanzamientos.txt"))) {
        String line;
        StringBuilder text = new StringBuilder();

        while ((line = reader.readLine()) != null) {
            text.append(line).append("\n");
        }

        AreaTextList.setText(text.toString());
    } catch (IOException e) {
        e.printStackTrace();
        AreaTextList.setText("Error al leer los datos.");
    }

        
    }
    
    @FXML
    private void GuardarDatos(ActionEvent event) {
            // Abre un FileWriter para escribir en un archivo llamado "lanzamientos.txt"
    try (FileWriter writer = new FileWriter("lanzamientos.txt")) {
        // Recorre la pila de lanzamientos y escribe cada lanzamiento en el archivo
        while (!pilaLanzamientos.estaVacia()) {
            Lanzamiento objL = pilaLanzamientos.desapilar();
            writer.write("Dado 1: " + objL.getDado1() + " - Dado 2: " + objL.getDado2() + "\n");
        }
        
        // Informa al usuario que los datos se han guardado
        AreaTextList.setText("Datos guardados en lanzamientos.txt");
    } catch (IOException e) {
        e.printStackTrace();
        AreaTextList.setText("Error al guardar los datos.");
    }
}
      
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
}
