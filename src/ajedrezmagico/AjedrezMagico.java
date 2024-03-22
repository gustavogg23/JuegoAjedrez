package ajedrezmagico;

import javax.swing.JFrame;

public class AjedrezMagico {

    public static void main(String[] args) {
        
        JFrame window = new JFrame("Ajedrez");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        
        //agrega gamepanel a la ventana
        GamePanel gp = new GamePanel();
        window.add(gp);
        window.pack();
        
        
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        
        gp.iniciarJuego();
        
  
    }
    
}
