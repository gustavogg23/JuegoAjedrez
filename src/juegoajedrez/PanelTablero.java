package juegoajedrez;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;

public class PanelTablero extends JPanel {
    private static final int TAMANO_CUADRADO = 50;
    private Color color1;
    private Color color2;

    // Método Constructor
    public PanelTablero(Color color1, Color color2) {
        this.color1 = color1;
        this.color2 = color2;
    }

    // Métodos Getters y Setters
    public Color getColor1() {
        return color1;
    }

    public void setColor1(Color color1) {
        this.color1 = color1;
    }

    public Color getColor2() {
        return color2;
    }

    public void setColor2(Color color2) {
        this.color2 = color2;
    }
    
    /*  
        Método que sobreescribe el método paintComponent de la clase
        JPanel para dibujar componentes en el panel.
        Este método dibuja cada cuadro del tablero de ajedrez
    */
    @Override
    public void paintComponent(Graphics graficos) {
        super.paintComponent(graficos);
        
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Color color;
                if ((i + j) % 2 == 0) { // Verifica si la suma de la fila y columna es par
                    color = color1; // En caso de que lo sea, se establece el color 1
                }
                else { // Caso contrario se establece color 2
                    color = color2; 
                }
                
                // Se calculan las coordenadas de la posición de cada cuadrado para dibujar cada uno en la posición correcta
                int x = j * TAMANO_CUADRADO;
                int y = i * TAMANO_CUADRADO;
                graficos.setColor(color); // Establece el color correspondiente al cuadrado
                graficos.fillRect(x, y, TAMANO_CUADRADO, TAMANO_CUADRADO); // Se dibuja el cuadrado
            }
        }
    }
    
    /*
        Se sobreescribe el método getPreferredSize para poder
        especificar el tamaño de todo el tablero.
        Retorna un objeto Dimension con el tamaño correcto del tablero, 
        es decir, un tablero 8x8 cuadrados
    */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(TAMANO_CUADRADO * 8, TAMANO_CUADRADO * 8); 
    }
}
