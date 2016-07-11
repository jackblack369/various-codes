import javax.swing.*;
// layout manager
import java.awt.*;
import java.awt.event.*;
        
/**
 *
 * @author Christophe
 */
public class SimpleGui3C {
    static int count;
    JFrame frame;
    JLabel label;
    
    public static void main(String args[]){
        SimpleGui3C gui = new SimpleGui3C();
        gui.go();
    }
    
    public void go(){
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JButton labelButton = new JButton("Change Label");
        labelButton.addActionListener( new LabelListener());
       
        JButton colorButton = new JButton("Change circle");
        colorButton.addActionListener(new ColorListener());
        
        label = new JLabel("I'm a label");
        MyDrawPanel drawPanel = new MyDrawPanel();
        
        frame.getContentPane().add(BorderLayout.SOUTH,colorButton);
        frame.getContentPane().add(BorderLayout.CENTER,drawPanel);
        frame.getContentPane().add(BorderLayout.EAST,labelButton);
        frame.getContentPane().add(BorderLayout.WEST,label);
        frame.setSize(300,300);
        frame.setVisible(true);
    }
    
    class LabelListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            count++;
            label.setText("Ouch! : " + count);
        }
    }
    
    class ColorListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            frame.repaint();
        }
    }
}
