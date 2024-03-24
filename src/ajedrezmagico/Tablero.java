package ajedrezmagico;

import Pieza.Pieza;
import java.awt.Color;
import java.awt.Graphics2D;

public class Tablero {
    
    final int MAX_COL = 8;
    final int MAX_FILA = 8;
    public static final int SQUARE_SIZE = 75;
    public static final int HALF_SQUARE_SIZE = SQUARE_SIZE/2;
    
    public void draw(Graphics2D g2) {
        
        int c = 0;
        
        for(int fila = 0; fila < MAX_FILA; fila++) {
            
            for(int col = 0; col < MAX_COL; col++) {
                
                if(c == 0) {
                    g2.setColor(new Color(128, 128, 128));
                    c = 1;
                }
                else {
                    g2.setColor(new Color(0, 0, 0));
                    c = 0;
                }
                g2.fillRect(col*SQUARE_SIZE, fila*SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
            }
            
            if(c == 0) {
                c = 1;
            }
            else {
                c = 0;
            }
        }
    }
    private Pieza[][] tablero; // Representación del tablero

    // Constructor y otras funciones...

    public int evaluarPosicion() {
        // Lógica para evaluar la posición actual del tablero
        // Devuelve un valor numérico (positivo o negativo)
        // según la ventaja o desventaja de un jugador
        // Ejemplo: suma de valores de piezas en el tablero
        return 0;
    }

    public ListaMovimientos generarMovimientosLegales() {
        // Lógica para generar los movimientos legales disponibles
        // para las piezas en el tablero
        // Ejemplo: iterar sobre las piezas y verificar sus movimientos válidos
        return new ListaMovimientos();
    }

    public int getTamano() {
        // Devuelve el tamaño del tablero (filas o columnas)
        return tablero.length;
    }

    public void aplicarMovimiento(Movimiento movimiento) {
        // Lógica para aplicar el movimiento en el tablero
        // Actualiza el estado del tablero según la pieza movida y la casilla de destino
    }

    public void deshacerMovimiento(Movimiento movimiento) {
        // Lógica para deshacer el movimiento en el tablero
        // Revierte los cambios realizados por el movimiento
    }
}
