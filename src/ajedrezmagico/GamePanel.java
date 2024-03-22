package ajedrezmagico;


import Pieza.Alfil;
import Pieza.Caballo;
import Pieza.Peon;
import Pieza.Pieza;
import Pieza.Reina;
import Pieza.Rey;
import Pieza.Torre;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import javax.swing.JPanel;

public final class GamePanel extends JPanel implements Runnable{
    
    public static int WIDTH = 1100;
    public static int HEIGHT = 800;
    final int FPS = 60;
    Thread gameThread;
    Tablero tablero = new Tablero();
    Raton raton = new Raton();
    
    public static ArrayList<Pieza> piezas = new ArrayList<>();
    public static ArrayList<Pieza> simPiezas = new ArrayList<>();
    ArrayList<Pieza> promoPiezas = new ArrayList<>();
    Pieza activeP;
    public static Pieza enRoque;
    Pieza jaque;
    
    
    
    //color
    public static final int WHITE = 0;
    public static final int BLACK = 1;
    int currentColor = WHITE;
    
    boolean sePuedeMover;
    boolean espacioValido;
    boolean promocion;
    boolean juegoTerminado;
    boolean ahogado;
    
    public GamePanel() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.black);
        addMouseMotionListener(raton);
        addMouseListener(raton);
        
        
        setPiezas();
        copyPiezas(piezas, simPiezas);
       
    }
    public void iniciarJuego() {
        gameThread = new Thread(this);
        gameThread.start();
    }
    public void setPiezas() {
        
        //los blancos
        piezas.add(new Peon(WHITE,0,6));
        piezas.add(new Peon(WHITE,1,6));
        piezas.add(new Peon(WHITE,2,6));
        piezas.add(new Peon(WHITE,3,6));
        piezas.add(new Peon(WHITE,4,6));
        piezas.add(new Peon(WHITE,5,6));
        piezas.add(new Peon(WHITE,6,6));
        piezas.add(new Peon(WHITE,7,6));
        piezas.add(new Torre(WHITE,0,7));
        piezas.add(new Caballo(WHITE,1,7));
        piezas.add(new Alfil(WHITE,2,7));
        piezas.add(new Reina(WHITE,3,7));
        piezas.add(new Rey(WHITE,4,7));
        piezas.add(new Alfil(WHITE,5,7));
        piezas.add(new Caballo(WHITE,6,7));
        piezas.add(new Torre(WHITE,7,7));
        
        
        
        //los negros
        piezas.add(new Peon(BLACK,0,1));
        piezas.add(new Peon(BLACK,1,1));
        piezas.add(new Peon(BLACK,2,1));
        piezas.add(new Peon(BLACK,3,1));
        piezas.add(new Peon(BLACK,4,1));
        piezas.add(new Peon(BLACK,5,1));
        piezas.add(new Peon(BLACK,6,1));
        piezas.add(new Peon(BLACK,7,1));
        piezas.add(new Torre(BLACK,0,0));
        piezas.add(new Caballo(BLACK,1,0));
        piezas.add(new Alfil(BLACK,2,0));
        piezas.add(new Reina(BLACK,3,0));
        piezas.add(new Rey(BLACK,4,0));
        piezas.add(new Alfil(BLACK,5,0));
        piezas.add(new Caballo(BLACK,6,0));
        piezas.add(new Torre(BLACK,7,0));
        
    }
    private void copyPiezas(ArrayList<Pieza> source, ArrayList<Pieza> Target) {
        
        Target.clear();
        for(int i = 0; i < source.size(); i++) {
            Target.add(source.get(i));
            
            
        }
        
    }
     @Override
    public void run() {
       //loop del juego
       
       double drawInterval = 1000000000/FPS;
       double delta = 0;
       long lastTime = System.nanoTime();
       long currentTime;
       
       while(gameThread != null) {
           
           currentTime = System.nanoTime();
           
           delta += (currentTime - lastTime)/drawInterval;
           lastTime = currentTime;
           
           if(delta >= 1) {
               actualizar();
               repaint();
               delta--;
               
            }
        }
    }
    private void actualizar() {
        
        if(promocion) {
            evolucionando();
            
        }
        else if(juegoTerminado == false && ahogado == false){
            if(raton.presionado) {
                if(activeP == null) {
                 // si activeP es null, se chequea si puede tomar la pieza
                    for(Pieza pieza : simPiezas) {//si la pieza es aliada, la toma como activeP
                        if(pieza.color == currentColor && 
                            pieza.col == raton.x/Tablero.SQUARE_SIZE && 
                            pieza.fila == raton.y/Tablero.SQUARE_SIZE) {
                        
                        activeP = pieza;
                    }
                }
            }
            else{//si el jugador esta sosteniendo la pieza va a simular el movimiento
                simulador();
                
            }
            
        }
            if(raton.presionado == false){
                if(activeP != null) {
                    if(espacioValido) {
                        //movimiento confirmado
                        //se actualiza la lista de piezas en caso de que alguna se haya capturado y se remueve de la simulacion
                        copyPiezas(simPiezas, piezas);
                        activeP.actualizarPos();
                        if(enRoque != null) {
                            enRoque.actualizarPos();
                        }
                        if(reyEnJaque() && jaqueMate()) {
                            juegoTerminado = true; 
                        }
                        else if(esAhogado() && reyEnJaque() == false) {
                            ahogado = true;
                        }
                        else{//el juego puede seguir
                            if(disponiblePromocion()) {
                                 promocion = true;
                        }
                        else{
                            turnos();
                            }
                        }
                    }
                    else {
                        //si el movimiento no es valido se reinicia todo
                        copyPiezas(piezas, simPiezas);
                        activeP.reiniciarPos();
                        activeP = null;
                    }
                }
            }
        }   
    }
    private void simulador () {
        
        sePuedeMover = false;
        espacioValido = false;
        
        //reinicia la lista en cada loop
        //basicamente para restaurar la pieza que fue removida en la simulacion
        copyPiezas(piezas, simPiezas);
        
        //reinicia la posicion de enroque
        if(enRoque != null) {
            enRoque.col = enRoque.preCol;
            enRoque.x = enRoque.getX(enRoque.col);
            enRoque = null;
        }
        
        //si la pieza esta siendo sostenida, actualiza la posicion\
        activeP.x = raton.x - Tablero.HALF_SQUARE_SIZE;
        activeP.y = raton.y - Tablero.HALF_SQUARE_SIZE;
        activeP.col = activeP.getCol(activeP.x);
        activeP.fila = activeP.getFila(activeP.y);
        
        if(activeP.sePuedeMover(activeP.col, activeP.fila)) {
            
            sePuedeMover = true;
            
            if(activeP.capturada != null) {
                simPiezas.remove(activeP.capturada.getIndex());
            }
            funcionEnRoque();
            if(esIlegal(activeP) == false && enemigoPuedeCapturarAlRey() == false){
                espacioValido = true; 
            }
        }
        
    }
    private boolean esIlegal(Pieza rey) {
        
        if(rey.tipo == Tipos.REY) {
            for(Pieza pieza : simPiezas){
                if(pieza != rey && pieza.color != rey.color && pieza.sePuedeMover(rey.col, rey.fila)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    private boolean enemigoPuedeCapturarAlRey() {
        Pieza rey = tomarRey(false);
        
        for(Pieza pieza : simPiezas) {
            if(pieza.color != rey.color && pieza.sePuedeMover(rey.col, rey.fila)) {
                return true;
            }
        }
        return false;
    }
    private boolean reyEnJaque(){
        Pieza rey = tomarRey(true);
        
        if(activeP.sePuedeMover(rey.col, rey.fila)) {
            jaque = activeP;
            return true;
        }
        else {
            jaque = null;
        }
        return false;
    }
    private Pieza tomarRey(boolean oponente) {
        Pieza rey = null;
        
        for(Pieza pieza : simPiezas) {
            if(oponente) {
                if(pieza.tipo == Tipos.REY && pieza.color != currentColor) {
                    rey = pieza;
                }
            }
            else{
                if(pieza.tipo == Tipos.REY && pieza.color == currentColor) {
                    rey = pieza;
                }
            }
        }
        return rey;
    }
    private boolean jaqueMate() {
        Pieza rey = tomarRey(true);
        
        if(reysePuedeMover(rey)) {
            return false;
        }
        else{
            //Si aun hay posibilidad de evitar jaquemate
            //revisa si se puede bloquear el ataque con alguna otra pieza
            
            //chequear la posicion de la pieza haciendo el jaque y el rey que esta en jaque
            int diferenciaCol = Math.abs(jaque.col - rey.col);
            int diferenciaFila = Math.abs(jaque.fila - rey.fila);
            
            if(diferenciaCol == 0) {
                //la pieza que hace jaque esta atacando verticalmente
                if(jaque.fila < rey.fila) {
                    //la pieza esta arriba del rey
                    for(int fila = jaque.fila; fila < rey.fila; fila++) {
                        for(Pieza pieza : simPiezas) {
                            if(pieza != rey && pieza.color != currentColor && pieza.sePuedeMover(jaque.col, fila)) {
                                return false; 
                            }
                        }
                    }
                }
                if(jaque.fila > rey.fila) {
                    //la pieza haciendo jaque esta debajo del rey
                    for(int fila = jaque.fila; fila > rey.fila; fila--) {
                        for(Pieza pieza : simPiezas) {
                            if(pieza != rey && pieza.color != currentColor && pieza.sePuedeMover(jaque.col, fila)) {
                                return false; 
                            }
                        }
                    }
                }
            }
            else if(diferenciaFila == 0) {
                //la pieza haciendo jaque esta atacando horizontalmente
                if(jaque.col < rey.col) {
                    //la pieza haciendo jaque viene de la izquierda
                    for(int col = jaque.col; col < rey.col; col++) {
                        for(Pieza pieza : simPiezas) {
                            if(pieza != rey && pieza.color != currentColor && pieza.sePuedeMover(jaque.fila, col)) {
                                return false; 
                            }
                        }
                    }
                }
                if(jaque.col > rey.col){
                    //la pieza haciendo jaque viene de la derecha
                    for(int col = jaque.col; col > rey.col; col--) {
                        for(Pieza pieza : simPiezas) {
                            if(pieza != rey && pieza.color != currentColor && pieza.sePuedeMover(jaque.fila, col)) {
                                return false; 
                            }
                        }
                    }
                }
            }
            else if(diferenciaCol == diferenciaFila) {
                //la pieza haciendo jaque esta en diagonal
                if(jaque.fila < rey.fila) {
                    //la pieza haciendo jaque viene de arriba 
                    if(jaque.col < rey.col) {
                        //la pieza esta en la esquina superior a la izquierda
                        for(int col = jaque.col, fila = jaque.fila; col < rey.col; col++, fila++) {
                            for(Pieza pieza : simPiezas) {
                                if(pieza != rey && pieza.color != currentColor && pieza.sePuedeMover(col, fila)) {
                                    return false;
                                }
                            }
                        }
                    }
                    if(jaque.col > rey.col) {
                        //la pieza esta atacando desde la esquina superior a la derecha
                        for(int col = jaque.col, fila = jaque.fila; col > rey.col; col--, fila++) {
                            for(Pieza pieza : simPiezas) {
                                if(pieza != rey && pieza.color != currentColor && pieza.sePuedeMover(col, fila)) {
                                    return false;
                                }
                            }
                        }
                    }
                }
                if(jaque.fila > rey.fila) {
                    //la pieza esta por debajo del rey
                    if(jaque.col < rey.col) {
                        //la pieza esta en la esquina inferior a la izquierda
                        for(int col = jaque.col, fila = jaque.fila; col < rey.col; col++, fila--) {
                            for(Pieza pieza : simPiezas) {
                                if(pieza != rey && pieza.color != currentColor && pieza.sePuedeMover(col, fila)) {
                                    return false;
                                }
                            }
                        }
                    }
                    if(jaque.col > rey.col) {
                        //la pieza esta en la esquina inferior a la derecha
                        for(int col = jaque.col, fila = jaque.fila; col > rey.col; col--, fila--) {
                            for(Pieza pieza : simPiezas) {
                                if(pieza != rey && pieza.color != currentColor && pieza.sePuedeMover(col, fila)) {
                                    return false;
                                }
                            }
                        }
                    }
                }
            }
            else {
                //la pieza atacando es el caballo
                
            }
        }
        return true;
    }
    private boolean reysePuedeMover(Pieza rey) {
        
        //simular si hay espacio donde mover al rey
        if(movimientoValido(rey, -1, -1)) {return true;}
        if(movimientoValido(rey, -1, 0)) {return true;}
        if(movimientoValido(rey, -1, 1)) {return true;}
        if(movimientoValido(rey, 1, -1)) {return true;}
        if(movimientoValido(rey, 1, 0)) {return true;}
        if(movimientoValido(rey, 1, 1)) {return true;}
        if(movimientoValido(rey, 0, -1)) {return true;}
        if(movimientoValido(rey,  0, 1)) {return true;}
        return false;
    }
    
    private boolean movimientoValido(Pieza rey, int colPlus, int filaPlus) {
        
        boolean movimientoValido = false;
        
        //actulizamos la posicion del rey
        rey.col += colPlus;
        rey.fila += filaPlus; 
        
        if(rey.sePuedeMover(rey.col, rey.fila)) {
            if(rey.capturada != null) {
                simPiezas.remove(rey.capturada.getIndex());
            }
            if(esIlegal(rey) == false) {
                movimientoValido = true;
            }
        }
        //reinicia la posicion del rey y restaura la pieza que elimino
        rey.reiniciarPos();
        copyPiezas(piezas, simPiezas);
        
        
        return movimientoValido;
    }
    private boolean esAhogado(){
        
        int contador = 0;
        //cuenta el numero de las piezas
        for(Pieza pieza : simPiezas) {
            if(pieza.color != currentColor) {
                contador++;
            }
        } 
        
        //solo si queda una pieza obvio el rey
        if(contador == 1) {
            if(reysePuedeMover(tomarRey(true)) == false) {
                return true;
            }
        }
        return false;
    }
    
    private void funcionEnRoque() {
        
        if(enRoque != null) {
            if(enRoque.col == 0) {
                enRoque.col += 3;
            }
            else if(enRoque.col == 7) {
                enRoque.col -= 2;
            }
            enRoque.x = enRoque.getX(enRoque.col);
        }
    }
    private void turnos() {
        if(currentColor == WHITE){
            currentColor = BLACK;
            //se reinician los dos pasos de las piezas negras
            for(Pieza pieza : piezas) {
                if(pieza.color == BLACK) {
                    pieza.dosPasos = false;
                }
            }
        }
        else {
            currentColor = WHITE;
            //se reinician los dos pasos de las piezas blancas
            for(Pieza pieza : piezas) {
                if(pieza.color == WHITE) {
                    pieza.dosPasos = false;
                }
            }    
        }
        activeP = null;
    }
    private boolean disponiblePromocion() {
        
        if(activeP.tipo == Tipos.PEON) {
            if(currentColor == WHITE && activeP.fila == 0 || currentColor == BLACK && activeP.fila == 7) { 
                promoPiezas.clear();
                promoPiezas.add(new Reina(currentColor, 4, 4));
                promoPiezas.add(new Caballo(currentColor, 3, 4));
                promoPiezas.add(new Alfil(currentColor, 2, 4));
                promoPiezas.add(new Torre(currentColor, 5, 4));
                return true;
        }
    }
        
        return false;
    }
    private void evolucionando() {
        if(raton.presionado){
            for(Pieza pieza : promoPiezas) {
                if(pieza.col == raton.x/Tablero.SQUARE_SIZE && pieza.fila == raton.y/Tablero.SQUARE_SIZE) {
                    switch(pieza.tipo) {
                        case REINA: simPiezas.add(new Reina(currentColor, activeP.col, activeP.fila)); break;
                        case CABALLO: simPiezas.add(new Caballo(currentColor, activeP.col, activeP.fila)); break;
                        case ALFIL: simPiezas.add(new Alfil(currentColor, activeP.col, activeP.fila)); break;
                        case TORRE: simPiezas.add(new Torre(currentColor, activeP.col, activeP.fila)); break;
                        default: break;
                    }
                    simPiezas.remove(activeP.getIndex());
                    copyPiezas(simPiezas, piezas);
                    activeP = null;
                    promocion = false;
                    turnos();
                }
            }
        }
    }
    
   
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        Graphics2D g2 = (Graphics2D)g;
        
        tablero.draw(g2);
        
        for(Pieza P : simPiezas) {
            P.draw(g2);
        }
        if(activeP != null) {
            if(sePuedeMover) {
                if(esIlegal(activeP) || enemigoPuedeCapturarAlRey()) {
                    g2.setColor(Color.red);
                    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f));
                    g2.fillRect(activeP.col*Tablero.SQUARE_SIZE, activeP.fila*Tablero.SQUARE_SIZE, Tablero.SQUARE_SIZE, Tablero.SQUARE_SIZE);
                    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
                }
                else{
                    g2.setColor(Color.green);
                    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f));
                    g2.fillRect(activeP.col*Tablero.SQUARE_SIZE, activeP.fila*Tablero.SQUARE_SIZE, Tablero.SQUARE_SIZE, Tablero.SQUARE_SIZE);
                    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
                }
               
            }
         
            activeP.draw(g2);
        }
        if(promocion) {
            for(Pieza pieza : promoPiezas) {
                g2.drawImage(pieza.image, pieza.getX(pieza.col), pieza.getY(pieza.fila), Tablero.SQUARE_SIZE, Tablero.SQUARE_SIZE, null);
            }
            if(juegoTerminado) {
                //por hacer anuncio de ganador
            }
            if(ahogado) {
                //por hacer 
            }
        }
    }
}


