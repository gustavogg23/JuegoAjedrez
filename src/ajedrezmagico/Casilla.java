
package ajedrezmagico;

public class Casilla {
    private int col;
    private int fila;

    public Casilla(int col, int fila) {
        this.col = col;
        this.fila = fila;
    }

    // Getters y setters
    public int getColumna() {
        return col;
    }

    public void setColumna(int columna) {
        this.col = columna;
    }

    public int getFila() {
        return fila;
    }

    public void setFila(int fila) {
        this.fila = fila;
    }
}
