package ajedrezmagico;


public class NodoMovimiento {
    private Movimiento valor;
    private NodoMovimiento siguiente;

    public NodoMovimiento(Movimiento valor) {
        this.valor = valor;
        this.siguiente = null;
    }

    // Getters y setters
    public Movimiento getValor() {
        return valor;
    }

    public void setValor(Movimiento valor) {
        this.valor = valor;
    }

    public NodoMovimiento getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(NodoMovimiento siguiente) {
        this.siguiente = siguiente;
    }
}
