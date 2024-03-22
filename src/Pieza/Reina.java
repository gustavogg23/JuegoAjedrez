package Pieza;

import ajedrezmagico.GamePanel;
import ajedrezmagico.Tipos;

public class Reina extends Pieza {
    
    public Reina(int color, int col, int fila) {
        super(color, col, fila);
        
        tipo = Tipos.REINA;
        
         if(color == GamePanel.WHITE) {
            image = getImage("Images/w-queen.png");
        }
        else {
            image = getImage("Images/b-queen.png");
            
        }
    }
    public boolean sePuedeMover(int targetCol, int targetFila) {
        
        if(estaDentroDelTablero(targetCol, targetFila) && mismoEspacio(targetCol, targetFila) == false){
            
            //vertical y horizontal
            if(targetCol == preCol || targetFila == preFila) {
                if(espacioVacio(targetCol, targetFila) && piezaEnElCaminoLineal(targetCol, targetFila) == false) {
                    return true;
                }
            }
            //Diagonal
            if(Math.abs(targetCol - preCol) == Math.abs(targetFila - preFila)) {
                if(espacioVacio(targetCol, targetFila) && piezaEnElCaminoDiagonal(targetCol, targetFila) == false){
                    return true;
                }
            }
        }
        return false;
    }
}
    
