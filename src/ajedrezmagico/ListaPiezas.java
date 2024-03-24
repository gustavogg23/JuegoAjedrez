package ajedrezmagico;

import Pieza.Pieza;

public class ListaPiezas {
    // Atributos 
    private NodoPieza cabeza;
    private int tamano;
    
    // Método Constructor
    public ListaPiezas() {
        this.cabeza = null;
        this.tamano = 0;
    }
    
    // Método para verificar si la lista está vacía
    public boolean estaVacia() {
        return cabeza == null;
    }
    
    // Método para agregar un nuevo nodo al final de la lista
    public void agregar(Pieza valor) {
        
        NodoPieza nuevo = new NodoPieza(valor); // Se crea nuevo nodo
        
        if (estaVacia()) { // Si la lista está vacía, el nuevo nodo será la cabeza
            cabeza = nuevo;
        }
        else { // Si la lista no está vacía, se recorre la lista hasta llegar al final y se agrega el nuevo nodo
            
            NodoPieza temp = cabeza; // Se crea un nodo temporal para recorrer la lista
            
            while (temp.getSiguiente() != null) { // MIentras el nodo temporal no sea el último se sigue recorriendo la lista
                temp = temp.getSiguiente();
            }
            
            temp.setSiguiente(nuevo); // Se agrega el nodo al final de la lista
        }
        
        tamano++; // Se aumenta el tamaño de la lista
    }
    
    public Pieza get(int index) {
        if (index < 0 || index >= tamano) {
            throw new IndexOutOfBoundsException("Invalid index");
        }

        NodoPieza temp = cabeza;
            for (int i = 0; i < index; i++) {
             temp = temp.getSiguiente();
            }
        return temp.getValor();
    }

    public int getTamano() {
        return tamano;
    }


}
