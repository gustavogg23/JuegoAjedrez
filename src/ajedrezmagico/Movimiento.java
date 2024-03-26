package ajedrezmagico;

import Pieza.Pieza;

public class Movimiento {
    private int colDeOrigen;
    private int filaDeOrigen;
    private int targetCol;
    private int targetFila;
    private Pieza piezaCapturada;

    public Movimiento(int colDeOrigen, int filaDeOrigen, int targetCol, int targetFila) {
        this.colDeOrigen = colDeOrigen;
        this.filaDeOrigen = filaDeOrigen;
        this.targetCol = targetCol;
        this.targetFila = targetFila;
        this.piezaCapturada = null;
    }
    public Movimiento(int colDeOrigen, int filaDeOrigen, int targetCol, int targetFila, Pieza piezaCapturada) {
        this.colDeOrigen = colDeOrigen;
        this.filaDeOrigen = filaDeOrigen;
        this.targetCol = targetCol;
        this.targetFila = targetFila;
        this.piezaCapturada = piezaCapturada;
    }
    
    public int getcolDeOrigen() {
        return colDeOrigen;
    }

    public int getfilaDeOrigen() {
        return filaDeOrigen;
    }

    public int getTargetCol() {
        return targetCol;
    }

    public int getTargetFila() {
        return targetFila;
    }

    // Implement any additional methods or logic related to moves here
}

