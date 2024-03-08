package juegoajedrez;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JFrame;

public class JuegoAjedrez {

    public static void main(String[] args) {
        JFrame ventana = new JFrame("CHESSGAME");
        
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setResizable(false);
        
        PanelTablero pTablero = new PanelTablero(Color.WHITE, Color.BLACK);
        
        ventana.setLayout(new BorderLayout());
        ventana.add(pTablero, BorderLayout.CENTER);
        ventana.pack();
        ventana.setLocationRelativeTo(null);
        ventana.setVisible(true);
    }   
}
