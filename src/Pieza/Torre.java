package Pieza;

import ajedrezmagico.GamePanel;
import ajedrezmagico.Tipos;


public class Torre extends Pieza {
    
    public Torre(int color, int col, int fila) {
        super(color, col, fila);
        
        tipo = Tipos.TORRE;
        
        if(color == GamePanel.WHITE) {
            image = getImage("Images/w-rook.png");
        }
        else {
            image = getImage("Images/b-rook.png");
            
            
        }
    }
    public boolean sePuedeMover(int targetCol, int targetFila) {
        
        if(estaDentroDelTablero(targetCol, targetFila) && mismoEspacio(targetCol, targetFila) == false) {
            //la torre se puede mover en vertical u horizontal
            if(targetCol == preCol || targetFila == preFila) {
                if(espacioVacio(targetCol, targetFila) && piezaEnElCaminoLineal(targetCol, targetFila) == false) {
                return true;
                }
            }
            
        }
        return false;
    }
}
    

