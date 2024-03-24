package ajedrezmagico;

import Pieza.Pieza;

public class JugadorCPU {
      private static final int PROFUNDIDAD_MAXIMA = 3; // Ajusta según la dificultad deseada

     

    public Movimiento seleccionarMovimiento(ListaPiezas listaPiezas) {
        // Llama a Minimax para elegir el mejor movimiento
        ScoredMove mejorMovimiento = minimax(listaPiezas, PROFUNDIDAD_MAXIMA, true);
        return new Movimiento(mejorMovimiento.getPieza(), mejorMovimiento.getCasillaDestino());
    }

    private ScoredMove minimax(ListaPiezas listaPiezas, int profundidad, boolean jugadorMaximizador) {
        if (profundidad == 0 || esFinDelJuego(listaPiezas)) {
            return new ScoredMove(evaluarPos(listaPiezas), null, null);
        }

        ListaMovimientos movimientosLegales = generarMovimientosLegales(listaPiezas);
        ScoredMove mejorMovimiento = jugadorMaximizador
                ? new ScoredMove(Integer.MIN_VALUE, null, null)
                : new ScoredMove(Integer.MAX_VALUE, null, null);

        for (int i = 0; i < movimientosLegales.getTamano(); i++) {
            Movimiento movimiento = movimientosLegales.obtener(i);
            Pieza pieza = movimiento.getPieza();
            Casilla destino = movimiento.getDestino();

            aplicarMovimiento(pieza, destino, listaPiezas);
            ScoredMove movimientoActual = minimax(listaPiezas, profundidad - 1, !jugadorMaximizador);
            deshacerMovimiento(pieza, destino, listaPiezas);

            if ((jugadorMaximizador && movimientoActual.getScore() > mejorMovimiento.getScore())
                    || (!jugadorMaximizador && movimientoActual.getScore() < mejorMovimiento.getScore())) {
                mejorMovimiento = new ScoredMove(movimientoActual.getScore(), pieza, destino);
            }
        }

        // Determina si el juego ha terminado (basado en un valor booleano)
        boolean juegoTerminado = esFinDelJuego(listaPiezas); // Implementa tu lógica aquí

        if (juegoTerminado) {
            // Realiza alguna acción adicional si el juego ha terminado
            // Por ejemplo, muestra un mensaje o registra el resultado
        }

        return mejorMovimiento;
    }

    private boolean esFinDelJuego(ListaPiezas listaPiezas) {
        // Implementa tu lógica para determinar si el juego ha terminado
        // Puedes usar un booleano o cualquier otro criterio específico
        // Ejemplo: verifica si hay jaque mate o empate
        return false; // Cambia esto según tu implementación
    }
    private int evaluarPos(ListaPiezas listaPiezas) {
        
        //logica de evaluacion aqui
        //cosideramos factores como valores de piezas, posicion, estructura de peon
        //ejemplo: asignamos puntaje basasdo en el balance del material, actividad de la pieza
        
        int materialScore = calcularMaterialScore(listaPiezas);
        int posScore = calcularMaterialScore(listaPiezas);
        
        int scoreTotal = materialScore + posScore;
        
        
        
        return scoreTotal;
    }
    private int calcularMaterialScore(ListaPiezas listaPiezas) {
        int materialScore = 0;
        
        int valorPeon = 1;
        int valorCaballo = 3;
        int valorAlfil = 3;
        int valorTorre = 5;
        int valorReina = 9;
        
         for (int i = 0; i < listaPiezas.getTamano(); i++) {
            Pieza piece = listaPiezas.get(i);
            Tipos piezaTipo = piece.getTipo();
            
            switch (piezaTipo) {
             case PEON:
                materialScore += valorPeon;
                break;
            case CABALLO:
                materialScore += valorCaballo;
                break;
            case ALFIL:
                materialScore += valorAlfil;
                break;
            case TORRE:
                materialScore += valorTorre;
                break;
            case REINA:
                materialScore += valorReina;
                break;
            default:
                break;
        }
    }

    return materialScore;
    
                
    }

    // Otros métodos auxiliares (evaluarPosicion, generarMovimientosLegales, aplicarMovimiento, deshacerMovimiento, esFinDelJuego)...


    class ScoredMove {
        private int score;
        private Pieza pieza;
        private Casilla casillaDestino;

    public ScoredMove(int score, Pieza pieza, Casilla casillaDestino) {
        this.score = score;
        this.pieza = pieza;
        this.casillaDestino = casillaDestino;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Pieza getPieza() {
        return pieza;
    }

    public void setPieza(Pieza pieza) {
        this.pieza = pieza;
    }

    public Casilla getCasillaDestino() {
        return casillaDestino;
    }

    public void setCasillaDestino(Casilla casillaDestino) {
        this.casillaDestino = casillaDestino;
    }
}
