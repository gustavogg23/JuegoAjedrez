package ajedrezmagico;

import Pieza.Pieza;

public class NodoPieza {
    // Atributos
    private Pieza valor;
    private NodoPieza siguiente;
    
    // Método Constructor
    public NodoPieza(Pieza valor) {
        this.valor = valor;
        this.siguiente = null;
    }

    // Métodos Getters y Setters
    public Pieza getValor() {
        return valor;
    }

    public void setValor(Pieza valor) {
        this.valor = valor;
    }

    public NodoPieza getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(NodoPieza siguiente) {
        this.siguiente = siguiente;
    }
    
    
}
