package ajedrezmagico;

import java.util.List;
import java.util.Random;

public class JugadorCPU {
    private final int maxProfundidad;
    private final Random random;
    
    public JugadorCPU(int maxProfundidad) {
        this.maxProfundidad = maxProfundidad;
        this.random = new Random();
        
    }
    
    public Movimiento elegirMovimiento(List<Movimiento> movimientos) {
        
        
        if(!movimientos.isEmpty()) {
            int randomIndex = random.nextInt(movimientos.size());
            return movimientos.get(randomIndex);
            
        }
        return null; //no hay movimientos legales disponibles
    }
    
    public Movimiento calcularMejorMovimiento(GamePanel gamepanel) {
        // Implement minimax search with alpha-beta pruning
        // Evaluate positions using an evaluation function
        // Return the best move found
        // You'll need to recursively explore the game tree
        // based on the search depth.
        // Remember to consider the current player's color (WHITE or BLACK).
        // You can use the provided gamePanel to access the current position.
        
        //Placeholder: Choose a random move for now
        List<Movimiento> movimientosLegales = GamePanel.generarMovimientos();
        return elegirMovimiento(movimientosLegales);
    }
}
