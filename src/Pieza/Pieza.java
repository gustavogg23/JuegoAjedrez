package Pieza;

import ajedrezmagico.GamePanel;
import ajedrezmagico.Movimiento;
import ajedrezmagico.Tablero;
import ajedrezmagico.Tipos;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;


public class Pieza  {
    
    public Tipos tipo;
    public BufferedImage image;
    public int x, y; 
    public int col, fila, preCol, preFila;
    public int color;
    public Pieza capturada;
    public boolean seMovio;
    public boolean dosPasos;
    
    
    
    public Pieza(int color, int col, int fila) {
        
        this.color = color; 
        this.col = col;
        this.fila = fila;
        x = getX(col);
        y = getY(fila);
        preCol = col;
        preFila = fila;
}
public BufferedImage getImage(String imagePath){

    BufferedImage image = null;
    
    try {
        image = ImageIO.read(getClass().getResourceAsStream(imagePath));
        
    }catch(IOException e) {
        e.printStackTrace();
    }
    return image;

        
    }
    public int getX(int col) {
        return col * Tablero.SQUARE_SIZE;  
    }
    public int getY(int fila) {
        return fila * Tablero.SQUARE_SIZE;
    }
    public int getCol(int x) {
        return(x + Tablero.HALF_SQUARE_SIZE)/Tablero.SQUARE_SIZE;
    }
    public int getFila(int y) {
        return(y + Tablero.HALF_SQUARE_SIZE)/Tablero.SQUARE_SIZE;
    }
    public int getIndex() {
        for(int index = 0; index < GamePanel.simPiezas.size(); index++) {
            if(GamePanel.simPiezas.get(index) == this) {
                return index;
            }
        } 
        return 0;
    }
    public void actualizarPos(){
        
        // para chequear En passant
        if(tipo == Tipos.PEON) {
            if(Math.abs(fila - preFila) == 2) {
                dosPasos = true;
            }
        }
        x = getX(col);
        y = getY(fila);
        preCol = getCol(x);
        preFila = getFila(y);
        seMovio = true;
    }
    public void reiniciarPos() {
        col = preCol;
        fila = preFila;
        x = getX(col);
        y = getY(fila);
    }
    public boolean sePuedeMover(int targetCol, int targetFila) {
        return false;
    }
    public boolean estaDentroDelTablero(int targetCol, int targetFila) {
        if(targetCol >= 0 && targetCol <= 7 && targetFila >= 0 && targetFila <= 7){
            return true;
        }
        return false;
    }
    public Pieza siendoCapt(int targetCol, int targetFila) {
        for(Pieza pieza : GamePanel.simPiezas) {
            if(pieza.col == targetCol && pieza.fila == targetFila && pieza != this) {
                return pieza;
            }
        }
        return null;
    }
    public boolean mismoEspacio(int targetCol, int targetFila) {
        if(targetCol == preCol && targetFila == preFila) {
            return true;
        }
        return false;
    }
    public boolean espacioVacio(int targetCol, int targetFila) {
        
        capturada = siendoCapt(targetCol, targetFila);
        if(capturada == null) {
            return true;
        }
        else {
            if(capturada.color != this.color) {
                return true;
            }
            else {
                capturada = null;
            }
        }
        return false;
    }
    public boolean piezaEnElCaminoLineal(int targetCol, int targetFila) {
        
        //moviendo la pieza hacia la izquierda
        for(int c = preCol-1; c > targetCol; c--){
            for(Pieza pieza : GamePanel.simPiezas) {
                if(pieza.col == c && pieza.fila == targetFila) {
                    capturada = pieza;
                    return true;
                }
            }
        }
        //moviendo hacia la derecha
        for(int c = preCol+1; c < targetCol; c++){
            for(Pieza pieza : GamePanel.simPiezas) {
                if(pieza.col == c && pieza.fila == targetFila) {
                    capturada = pieza;
                    return true;
                }
            }
        }
        //moviendo hacia arriba
        for(int f = preFila-1; f > targetFila; f--){
            for(Pieza pieza : GamePanel.simPiezas) {
                if(pieza.col == targetCol && pieza.fila == f) {
                    capturada = pieza;
                    return true;
                }
            }
        }
        //moviendose hacia abajo
        for(int f = preFila+1; f < targetFila; f++){
            for(Pieza pieza : GamePanel.simPiezas) {
                if(pieza.col == targetCol && pieza.fila == f) {
                    capturada = pieza;
                    return true;
                }
            }
        }
        return false;
    }
    public boolean piezaEnElCaminoDiagonal(int targetCol, int targetFila) {
        if(targetFila < preFila) {
            //hacia arriba y a la izquierda
            for(int c = preCol-1; c > targetCol; c--) {
            int diferencia = Math.abs(c - preCol);
            for(Pieza pieza : GamePanel.simPiezas) {
                if(pieza.col == c && pieza.fila == preFila - diferencia) {
                    capturada = pieza;
                    return true;
                }
            }
        }
        //hacia arriba y a la derecha
        for(int c = preCol+1; c < targetCol; c++) {
            int diferencia = Math.abs(c - preCol);
            for(Pieza pieza : GamePanel.simPiezas) {
                if(pieza.col == c && pieza.fila == preFila - diferencia) {
                    capturada = pieza;
                    return true;
                }
            }
        }
    }
        if(targetFila > preFila) {
            //hacia abajo y a la izquierda
            for(int c = preCol-1; c > targetCol; c--) {
            int diferencia = Math.abs(c - preCol);
            for(Pieza pieza : GamePanel.simPiezas) {
                if(pieza.col == c && pieza.fila == preFila + diferencia) {
                    capturada = pieza;
                    return true;
                }
            }
        }
            //hacia abajo y a la derecha
            for(int c = preCol+1; c < targetCol; c++) {
            int diferencia = Math.abs(c - preCol);
            for(Pieza pieza : GamePanel.simPiezas) {
                if(pieza.col == c && pieza.fila == preFila + diferencia) {
                    capturada = pieza;
                    return true;
                }
            }
        }
    }
        return false;
    }
    public List<Movimiento> obtenerMovimientosLegales() {
        List<Movimiento> movimientosLegales = new ArrayList<>();
             if (tipo == Tipos.PEON) {
                int valor = (color == GamePanel.WHITE) ? -1 : 1;
                int targetFila1 = fila + valor;
                int targetFila2 = fila + valor * 2;

                if (estaDentroDelTablero(col, targetFila1) && capturada == null) {
                    movimientosLegales.add(new Movimiento(col, fila, col, targetFila1));
                }

                if (!seMovio && estaDentroDelTablero(col, targetFila2) && capturada == null &&
                        !piezaEnElCaminoLineal(col, targetFila2)) {
                    movimientosLegales.add(new Movimiento(col, fila, col, targetFila2));
                }

                int[] diagonalCols = {col - 1, col + 1};
                    for (int diagonalCol : diagonalCols) {
                        if (estaDentroDelTablero(diagonalCol, targetFila1)) {
                            Pieza piezaEnDiagonal = siendoCapt(diagonalCol, targetFila1);
                                if (piezaEnDiagonal != null && piezaEnDiagonal.color != color) {
                                     movimientosLegales.add(new Movimiento(col, fila, diagonalCol, targetFila1, piezaEnDiagonal));
                        }
                    }
                }

                for (Pieza pieza : GamePanel.simPiezas) {
                    if (Math.abs(pieza.col - col) == 1 && pieza.fila == fila && pieza.dosPasos) {
                        movimientosLegales.add(new Movimiento(col, fila, pieza.col, targetFila1, pieza));
                    }
                }
            }
        return movimientosLegales;
    }
    
    public void draw(Graphics2D g2) {
        g2.drawImage(image, x, y, Tablero.SQUARE_SIZE, Tablero.SQUARE_SIZE, null);
    }

}