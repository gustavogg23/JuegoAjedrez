package ajedrezmagico;

import java.awt.event.ActionEvent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class AjedrezMagico {

    public static void main(String[] args) {
        
        JFrame window = new JFrame("Ajedrez");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        
        
        JMenuBar menuBar = new JMenuBar();

        
        JMenu gameMenu = new JMenu("Game");

        
        JMenuItem nuevoJuego = new JMenuItem("Nuevo Juego");
        JMenuItem cambiarColor = new JMenuItem("Chu es Gay");
        JMenuItem salir = new JMenuItem("Chao");

        
        nuevoJuego.addActionListener((ActionEvent e) -> {
            
            System.out.println("Nuevo Juego");
            
            GamePanel gp = new GamePanel();
            gp.iniciarJuego();//por arreglar
            
        });
        

        cambiarColor.addActionListener((ActionEvent e) -> {
            
            
            System.out.println("Cambio de color seleccionado");
        });

        salir.addActionListener((ActionEvent e) -> {
            
            System.out.println("Exit selected");
            System.exit(0);
        });

        
        gameMenu.add(nuevoJuego);
        gameMenu.add(cambiarColor);
        gameMenu.addSeparator(); 
        gameMenu.add(salir);

        
        menuBar.add(gameMenu);

        window.setJMenuBar(menuBar);

        GamePanel gp = new GamePanel();
        window.add(gp);
        window.pack();
        
        
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        
        gp.iniciarJuego();
        
  
    }
    
}
