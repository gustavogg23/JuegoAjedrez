package ajedrezmagico;

import Pieza.Pieza;


public class Movimiento {
    private Pieza pieza;
    private Casilla destino;

    public Movimiento(Pieza pieza, Casilla destino) {
        this.pieza = pieza;
        this.destino = destino;
    }

    // Getters y setters
    public Pieza getPieza() {
        return pieza;
    }

    public void setPieza(Pieza pieza) {
        this.pieza = pieza;
    }

    public Casilla getDestino() {
        return destino;
    }

    public void setDestino(Casilla destino) {
        this.destino = destino;
    }
    
}
