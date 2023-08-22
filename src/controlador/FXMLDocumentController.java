package controlador;

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
import java.util.HashMap;
import java.util.Map;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

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
    private TextArea AreaTextR;
    
    @FXML
    private ImageView marco1;
    
    @FXML
    private ImageView marco2;
    
    //Mapa de Imagenes
    private Map<Integer, String> mapaImagenesDados;
    
    // Lista De Lanzamientos
    Pila<Lanzamiento> pilaLanzamientos = new Pila<>();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        // Carga las imágenes de los dados en el mapa
        mapaImagenesDados = new HashMap<>();
        mapaImagenesDados.put(1, "/ImagenesDado/Dn1.JPG");
        mapaImagenesDados.put(2, "/ImagenesDado/Dn2.JPG");
        mapaImagenesDados.put(3, "/ImagenesDado/Dn3.JPG");
        mapaImagenesDados.put(4, "/ImagenesDado/Dn4.JPG");
        mapaImagenesDados.put(5, "/ImagenesDado/Dn5.JPG");
        mapaImagenesDados.put(6, "/ImagenesDado/Dn6.JPG");
        
    }    
       
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
                
                
                // Actualiza los componentes ImageView con las imágenes de los dados
                marco1.setImage(new Image(mapaImagenesDados.get(valorDado1)));
                marco2.setImage(new Image(mapaImagenesDados.get(valorDado2)));
                
                }
            };
      
        contador.scheduleAtFixedRate(tarea, 0, 4000);

    }
    
    @FXML
    private void PararTirada(ActionEvent event) {
        //Habilitar el botón de iniciar y deshabilitar el de parar
        //IniciarTiradas.setDisable(false);
        //PararTirada.setDisable(true);

        //Cancelar el temporizador
        contador.cancel();
        
         StringBuilder data = new StringBuilder();
    
        // Agregar cada lanzamiento al StringBuilder con una nueva línea
        while (!pilaLanzamientos.estaVacia()) {
            Lanzamiento objL = pilaLanzamientos.desapilar();
            data.append("Dado 1: ").append(objL.getDado1()).append(" - Dado 2: ").append(objL.getDado2()).append("\n");
        }
    
        AreaTextList.setText(data.toString());
        AreaTextR.setText("La probabilidad de sacar exactamente un 6 en 6 tiradas es: " + CalcularProbabilidad());

        // Crear un nuevo temporizador para futuras tiradas
        contador = new Timer();
    }
    
    @FXML
    private void CargarDatos(ActionEvent event) {
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
    private double CalcularProbabilidad() {
    int tiradas = 6;
    int valorObjetivo = 6;
    double probabilidad = calcularPBi(tiradas, valorObjetivo);
    return probabilidad;
    
}

private double calcularPBi(int n, int k) {
    int combinaciones = factorial(n) / (factorial(k) * factorial(n - k));
    double probabilidadUnica = 1.0 / 6.0; // Probabilidad de sacar un 6 en una tirada
    return combinaciones * Math.pow(probabilidadUnica, k) * Math.pow(1 - probabilidadUnica, n - k);
}

private int factorial(int n) {
    if (n == 0 || n == 1) {
        return 1;
    }
    return n * factorial(n - 1);
}
      
    
}
