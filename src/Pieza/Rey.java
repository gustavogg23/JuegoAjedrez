package Pieza;

import ajedrezmagico.GamePanel;
import ajedrezmagico.Tipos;

public class Rey extends Pieza {
    
    public Rey(int color, int col, int fila) {
        super(color, col, fila);
        
        tipo = Tipos.REY;
        
         if(color == GamePanel.WHITE) {
            image = getImage("Images/w-king.png");
        }
        else {
            image = getImage("Images/b-king.png");
            
        }
    }
    public boolean sePuedeMover(int targetCol, int targetFila) {
        
        if(estaDentroDelTablero(targetCol, targetFila)) {
            if(Math.abs(targetCol - preCol) + Math.abs(targetFila - preFila) == 1 || 
                    Math.abs(targetCol - preCol) * Math.abs(targetFila - preFila) == 1) {
                
                if(espacioVacio(targetCol, targetFila)) {
                    return true;
                }
            }
            
            //en roque
            if (preFila != 0 && preFila != 7) {
                // El rey no está en su posición inicial, no permitir enroque
                return false;
            }

            if (!seMovio) {
            // Enroque a la derecha
                if (targetCol == preCol + 2 && targetFila == preFila && piezaEnElCaminoLineal(targetCol, targetFila) == false) {
                    for (Pieza pieza : GamePanel.simPiezas) {
                        if (pieza.col == preCol + 3 && pieza.fila == preFila && !pieza.seMovio) {
                        GamePanel.enRoque = pieza;
                        return true;
                    }
                }
            }
            // Enroque a la izquierda
             if (targetCol == preCol - 2 && targetFila == preFila && piezaEnElCaminoLineal(targetCol, targetFila) == false) {
                Pieza p[] = new Pieza[2];
                for (Pieza pieza : GamePanel.simPiezas) {
                    if (pieza.col == preCol - 3 && pieza.fila == targetFila && !pieza.seMovio) {
                      p[0] = pieza;
                    }
                    if (pieza.col == preCol - 4 && pieza.fila == targetFila && !pieza.seMovio) {
                      p[1] = pieza;
                    }
                }
                    if (p[0] == null && p[1] != null && !p[1].seMovio) {
                         GamePanel.enRoque = p[1];
                        return true;
                     }   
                }
            }

                 // Si llegamos aquí, el enroque no es válido
                return false;

            }
             return false;
        }
    }
    

