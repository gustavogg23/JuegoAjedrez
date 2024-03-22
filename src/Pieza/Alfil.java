package Pieza;

import ajedrezmagico.GamePanel;
import ajedrezmagico.Tipos;

public class Alfil extends Pieza {
    
    public Alfil(int color, int col, int fila) {
        super(color, col, fila);
        
        tipo = Tipos.ALFIL;
         if(color == GamePanel.WHITE) {
            image = getImage("Images/w-bishop.png");
        }
        else {
            image = getImage("Images/b-bishop.png");
            
        }
    }
    public boolean sePuedeMover(int targetCol, int targetFila) {
        
        if(estaDentroDelTablero(targetCol, targetFila) && mismoEspacio(targetCol, targetFila) == false) {
            
            if(Math.abs(targetCol - preCol) == Math.abs(targetFila - preFila)) {
                if(espacioVacio(targetCol, targetFila) && piezaEnElCaminoDiagonal(targetCol, targetFila) == false) {
                    return true;
                }
            }
        }
        return false;
    }
}
    

