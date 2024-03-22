package Pieza;

import ajedrezmagico.GamePanel;
import ajedrezmagico.Tipos;

public class Caballo extends Pieza {
    
    public Caballo(int color, int col, int fila) {
        super(color, col, fila);
        
        tipo = Tipos.CABALLO;
        
         if(color == GamePanel.WHITE) {
            image = getImage("Images/w-knight.png");
        }
        else {
            image = getImage("Images/b-knight.png");
            
        }
    }
    public boolean sePuedeMover(int targetCol, int targetFila) {
        
        if(estaDentroDelTablero(targetCol, targetFila));{
            //el caballo se va a mover 2:1 o 1:2 simepre
            if(Math.abs(targetCol - preCol) * Math.abs(targetFila - preFila) == 2) {
                if(espacioVacio(targetCol, targetFila)) {
                    return true;
                }
            }
        }
        return false;
    }
}
    

