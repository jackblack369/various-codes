import javax.swing.*;
import java.awt.*;
/**
 *
 * @author Christophe
 */
public class SimpleTestGui {
    int x = 70;
    int y = 70;
    
    public static void main (String args[]) {
        SimpleTestGui gui = new SimpleTestGui();
        gui.go();
    }
    
    public void go() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JPanel drawPanel = new MyDrawPanel();
        
        // quit the program as soon as the window is closed
         
        frame.getContentPane().add(drawPanel);
        frame.setSize(300,300);
        frame.setVisible(true);
        
        for (int i = 0; i < 130; i++) {
            x++;
            y++;
            drawPanel.repaint();
            
            try{
                Thread.sleep(50);
            } catch (Exception ex) {
                
            }
        }
    }
    
    class MyDrawPanel extends JPanel {
        public void paintComponent(Graphics g) {
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, this.getWidth(), this.getWidth());
            g.setColor(Color.green);
            g.fillOval(x, y, 40, 40);
        }
    }
}
    
