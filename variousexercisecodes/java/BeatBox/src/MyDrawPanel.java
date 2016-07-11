import java.awt.*;
import javax.swing.*;
import javax.sound.midi.*;
/**
 *
 * @author Christophe
 */
public class MyDrawPanel extends JPanel implements ControllerEventListener {
    boolean msg = false;
    
    public void controlChange(ShortMessage event) {
        msg = true;
        repaint();
    }
    
    public void paintComponent(Graphics g) {
        if (msg) {
            // cast to use something Graphics don't have
            Graphics2D g2 = (Graphics2D) g;
        
            int r =  (int) (Math .random() * 250); 
            int gr = (int) (Math.random() * 250); 
            int b = (int) (Math .random() * 250); 
        
            g2.setColor(new Color(r,gr,b));
            
            int ht = (int) ((Math.random()*120)+10);
            int width = (int) ((Math.random()*120)+10);
            int x = (int) ((Math.random()*40)+10);
            int y = (int) ((Math.random()*40)+10);
            
            g2.fillRect(x, y, ht, width);
            msg = false;
        }        
    }
}
