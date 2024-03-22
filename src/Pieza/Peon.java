
package Pieza;

import ajedrezmagico.GamePanel;
import ajedrezmagico.Tipos;


public class Peon extends Pieza {
    
    public Peon(int color, int col, int fila) {
        super(color, col, fila);
        
        tipo = Tipos.PEON; 
        
        if(color == GamePanel.WHITE) {
            image = getImage("Images/w-pawn.png");
        }
        else {
            image = getImage("Images/b-pawn.png");
            
        }
    }
    public boolean sePuedeMover(int targetCol, int targetFila) {
        
        if(estaDentroDelTablero(targetCol, targetFila) && mismoEspacio(targetCol, targetFila) == false) {
            
            //definimos el valor del movimiento con base al color
            int valor;
            if(color == GamePanel.WHITE) {
                valor = -1;
            }
            else {
                valor = 1;
            }
            //chequea capturada
            capturada = siendoCapt(targetCol, targetFila);
            
            //1 espacio para el movimiento
            if(targetCol == preCol && targetFila == preFila + valor && capturada == null) {
                return true;
            }
            //2 espacios para el movimiento
            if(targetCol == preCol && targetFila == preFila + valor*2 && capturada == null && seMovio == false &&
                    piezaEnElCaminoLineal(targetCol, targetFila) == false) {
                return true;
            }
            //movimento diagonal para capturar siempre y cuando la pieza este en el diagonal del frente para ser secuestrada
            if(Math.abs(targetCol - preCol) == 1 && targetFila == preFila + valor && capturada != null &&
                    capturada.color != color) {
                return true;//mmgvo peon de mierda 
            }
            //En passant
            if(Math.abs(targetCol - preCol) == 1 && targetFila == preFila + valor) {
                for(Pieza pieza : GamePanel.simPiezas){
                    if(pieza.col == targetCol && pieza.fila == preFila && pieza.dosPasos == true) {
                     capturada = pieza;
                     return true;
                    }
                }
            }
        }
        return false;
    }
}
