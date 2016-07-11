import java.util.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import java.io.*;
/**
 *
 * @author Christophe
 */
public class QuizCardBuilder {
    private JTextArea question;
    private JTextArea answser;
    private ArrayList<QuizCard> cardList;
    private JFrame frame;
    
    public static void main(String args[]) {
        QuizCardBuilder builder = new QuizCardBuilder();
        builder.go();
    }
    
    public void go() {
        //build gui
        frame = new JFrame("Quiz Card Builder");
        JPanel mainPanel = new JPanel();
        Font bigFont = new Font("sanserif",Font.BOLD,24);
        question = new JTextArea(6,20);
        question.setLineWrap(true);
        question.setWrapStyleWord(true);
        question.setFont(bigFont);
        
        JScrollPane qScroller = new JScrollPane(question);
        qScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        qScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        
        answser = new JTextArea(6, 20);
        answser.setLineWrap(true);
        answser.setLineWrap(true);
        answser.setWrapStyleWord(true);
        answser.setFont(bigFont);
        
        JScrollPane aScroller = new JScrollPane(answser);
        aScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        aScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        
        JButton nextButton = new JButton("Next Card");
        
        cardList = new ArrayList<QuizCard>();
        
        JLabel qLabel = new JLabel("Question:");
        JLabel aLabel = new JLabel("Answer");
        
        mainPanel.add(qLabel);
        mainPanel.add(qScroller);
        mainPanel.add(aLabel);
        mainPanel.add(aScroller);
        mainPanel.add(nextButton);
        
        nextButton.addActionListener(new NextCardListener());
        
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem newMenuItem = new JMenuItem("New");
        JMenuItem saveMenuItem = new JMenuItem("Save");
        
        newMenuItem.addActionListener(new NewMenuListener());
        saveMenuItem.addActionListener(new SaveMenuListener());
        
        fileMenu.add(newMenuItem);
        fileMenu.add(saveMenuItem);
        menuBar.add(fileMenu);
        
        frame.setJMenuBar(menuBar);
        frame.getContentPane().add(BorderLayout.CENTER,mainPanel);
        frame.setSize(500,600);
        frame.setVisible(true);        
    }
    
    public class NextCardListener implements ActionListener{
        public void actionPerformed(ActionEvent ev) {
            QuizCard card = new QuizCard(question.getText(),answser.getText());
            cardList.add(card);
            clearCard();
        }
    }
    
    public class SaveMenuListener implements ActionListener {
        public void actionPerformed(ActionEvent ev) {
            QuizCard card = new QuizCard(question.getText(),answser.getText());
            cardList.add(card);
            
            JFileChooser fileSave = new JFileChooser();
            fileSave.showSaveDialog(frame);
            saveFile(fileSave.getSelectedFile());
        }
    }
    
    public class NewMenuListener implements ActionListener {
        public void actionPerformed(ActionEvent ev) {
            cardList.clear();
            clearCard();
        }
    }
    
    private void clearCard() {
        question.setText("");
        answser.setText("");
        question.requestFocus();
    }
    
    private void saveFile(File file) {
        try {
            BufferedReader writter = new BufferedReader(new FileWriter(file));
            
            for (QuizCard card:cardList) {
                writter.write(card.getQuestion()+"/");
                writter.write(card.getAnswer()+"\n");
            }
            writter.close();
        } catch (IOException ex) {
            System.out.println("couldn't wirte the cardList out");
            ex.printStackTrace();
        }
    }
}
