Exemple 8.6 : Retire.java

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.applet.*;
import java.util.*;
import java.text.*;
import java.io.*;
import javax.swing.*;

/**
   Cette applet affiche une calculatrice pour la 
   retraite. L'interface utilisateur est affichée en 
   anglais, français et chinois.
*/
public class Retire extends JApplet
{
   public void init()
   {
      setLayout(new GridBagLayout());
      add(languageLabel, new GBC(0, 0).setAnchor(GBC.EAST));
      add(savingsLabel, new GBC(0, 1).setAnchor(GBC.EAST));
      add(contribLabel, new GBC(2, 1).setAnchor(GBC.EAST));
      add(incomeLabel, new GBC(4, 1).setAnchor(GBC.EAST));
      add(currentAgeLabel, new GBC(0, 2).setAnchor(GBC.EAST));
      add(retireAgeLabel, new GBC(2, 2).setAnchor(GBC.EAST));
      add(deathAgeLabel, new GBC(4, 2).setAnchor(GBC.EAST));
      add(inflationPercentLabel, new GBC(0, 3).setAnchor(GBC.EAST));
      add(investPercentLabel, new GBC(2, 3).setAnchor(GBC.EAST));
      add(localeCombo, new GBC(1, 0));
      add(savingsField, new GBC(1, 1).setWeight(
         100, 0).setFill(GBC.HORIZONTAL));
      add(contribField, new GBC(3, 1).setWeight(
         100, 0).setFill(GBC.HORIZONTAL));
      add(incomeField, new GBC(5, 1).setWeight(
         100, 0).setFill(GBC.HORIZONTAL));
      add(currentAgeField, new GBC(1, 2).setWeight(
         100, 0).setFill(GBC.HORIZONTAL));
      add(retireAgeField, new GBC(3, 2).setWeight(
         100, 0).setFill(GBC.HORIZONTAL));
      add(deathAgeField, new GBC(5, 2).setWeight(
         100, 0).setFill(GBC.HORIZONTAL));
      add(inflationPercentField, new GBC(1, 3).setWeight(
         100, 0).setFill(GBC.HORIZONTAL));
      add(investPercentField, new GBC(3, 3).setWeight(
         100, 0).setFill(GBC.HORIZONTAL));
      add(retireCanvas, new GBC(0, 4, 4, 1).setWeight(
         0, 100).setFill(GBC.BOTH));
      add(new JScrollPane(retireText), new GBC(4, 4, 2, 1).setWeight(
         0, 100).setFill(GBC.BOTH));

      computeButton.setName("computeButton");
      computeButton.addActionListener(new
         ActionListener()
         {
            public void actionPerformed(ActionEvent event)
            {
               getInfo();
               updateData();
               updateGraph();
            }
         });
      add(computeButton, new GBC(5, 3));

      retireText.setEditable(false);
      retireText.setFont(new Font("Monospaced", Font.PLAIN, 10));

      info.setSavings(0);
      info.setContrib(9000);
      info.setIncome(60000);
      info.setCurrentAge(35);
      info.setRetireAge(65);
      info.setDeathAge(85);
      info.setInvestPercent(0.1);
      info.setInflationPercent(0.05);

      int localeIndex = 0; // Paramètres régional US par défaut
      for (int i = 0; i < locales.length; i++) // si le paramètre actuel
             // est l'un des choix, le sélectionner
         if (getLocale().equals(locales[i])) localeIndex = i;
      setCurrentLocale(locales[localeIndex]);

      localeCombo.addActionListener(new
         ActionListener()
         {
            public void actionPerformed(ActionEvent event)
            {
               setCurrentLocale((Locale)
                  localeCombo.getSelectedIndex());
            }
         });
   }

   /**
      Définit le paramétrage régional actuel.
      @param locale le paramétrage régional souhaité
   */
   public void setCurrentLocale(Locale locale)
   {
      currentLocale = locale;
      localeCombo.setSelectedItem(currentLocale);
      localeCombo.setLocale(currentLocale);

      res = ResourceBundle.getBundle("RetireResources",
         currentLocale);
      resStrings = ResourceBundle.getBundle("RetireStrings",
         currentLocale);
      currencyFmt
         = NumberFormat.getCurrencyInstance(currentLocale);
      numberFmt
         = NumberFormat.getNumberInstance(currentLocale);
      percentFmt
         = NumberFormat.getPercentInstance(currentLocale);

      updateDisplay();
      updateInfo();
      updateData();
      updateGraph();
   }

   /**
      Met à jour toutes les étiquettes dans l'affichage.
   */
   public void updateDisplay()
   {
      languageLabel.setText(res.getString("language"));
      savingsLabel.setText(res.getString("savings"));
      contribLabel.setText(res.getString("contrib"));
      incomeLabel.setText(res.getString("income"));
      currentAgeLabel.setText(res.getString("currentAge"));
      retireAgeLabel.setText(res.getString("retireAge"));
      deathAgeLabel.setText(res.getString("deathAge"));
      inflationPercentLabel.setText
         (res.getString("inflationPercent"));
      investPercentLabel.setText
         (res.getString("investPercent"));
      computeButton.setText(res.getString("computeButton"));

      validate();
   }

   /**
      Met à jour les informations des champs de texte.
   */
   public void updateInfo()
   {
   savingsField.setText(currencyFmt.format(info.getSavings()));
      contribField.setText(currencyFmt.format(info.getContrib()));
      incomeField.setText(currencyFmt.format(info.getIncome()));
      currentAgeField.setText(numberFmt.format(info.getCurrentAge()));
      retireAgeField.setText(numberFmt.format(info.getRetireAge()));
      deathAgeField.setText(numberFmt.format(info.getDeathAge()));
      investPercentField.setText(percentFmt.format
         (info.getInvestPercent()));
      inflationPercentField.setText(percentFmt.format
         (info.getInflationPercent()));
   }

   /**
      Met à jour les données affichées dans la zone de 
      texte.
   */
   public void updateData()
   {
      retireText.setText("");
      MessageFormat retireMsg = new MessageFormat("");
      retireMsg.setLocale(currentLocale);
      retireMsg.applyPattern(resStrings.getString("retire"));

      for (int i = info.getCurrentAge(); i <= info.getDeathAge(); i++)
      {
         Object[] args = { i, info.getBalance(i) };
         retireText.append(retireMsg.format(args) +  "\n");
      }
   }

   /**
      Met le graphe à jour.
   */
   public void updateGraph()
   {
   retireCanvas.setColorPre((Color) res.getObject("colorPre"));
      retireCanvas.setColorGain((Color) res.getObject("colorGain"));
      retireCanvas.setColorLoss((Color) res.getObject("colorLoss"));
      retireCanvas.setInfo(info);
      repaint();
   }

   /**
      Lit l'entrée de l'utilisateur à partir des champs 
      de texte.
   */
   public void getInfo()
   {
      try
      {
         info.setSavings(currencyFmt.parse
          (savingsField.getText()).doubleValue());
         info.setContrib(currencyFmt.parse
            (contribField.getText()).doubleValue());
         info.setIncome(currencyFmt.parse
            (incomeField.getText()).doubleValue());
         info.setCurrentAge(numberFmt.parse
            (currentAgeField.getText()).intValue());
         info.setRetireAge(numberFmt.parse
            (retireAgeField.getText()).intValue());
         info.setDeathAge(numberFmt.parse
            (deathAgeField.getText()).intValue());
         info.setInvestPercent(percentFmt.parse
            (investPercentField.getText()).doubleValue());
         info.setInflationPercent(percentFmt.parse
            (inflationPercentField.getText()).doubleValue());
      }
      catch (ParseException e)
      {
      }
   }

   private JTextField savingsField = new JTextField(10);
   private JTextField contribField = new JTextField(10);
   private JTextField incomeField = new JTextField(10);
   private JTextField currentAgeField = new JTextField(4);
   private JTextField retireAgeField = new JTextField(4);
   private JTextField deathAgeField = new JTextField(4);
   private JTextField inflationPercentField = new JTextField(6);
   private JTextField investPercentField = new JTextField(6);
   private JTextArea retireText = new JTextArea(10, 25);
   private RetireCanvas retireCanvas = new RetireCanvas();
   private JButton computeButton = new JButton();
   private JLabel languageLabel = new JLabel();
   private JLabel savingsLabel = new JLabel();
   private JLabel contribLabel = new JLabel();
   private JLabel incomeLabel = new JLabel();
   private JLabel currentAgeLabel = new JLabel();
   private JLabel retireAgeLabel = new JLabel();
   private JLabel deathAgeLabel = new JLabel();
   private JLabel inflationPercentLabel = new JLabel();
   private JLabel investPercentLabel = new JLabel();

   private RetireInfo info = new RetireInfo();

   private Locale[] locales = { 
      Locale.US, Locale.CHINA, Locale.GERMANY };
   private Locale currentLocale;
   private JComboBox localeCombo = new LocaleCombo(locales);
   private ResourceBundle res;
   private ResourceBundle resStrings;
   private NumberFormat currencyFmt;
   private NumberFormat numberFmt;
   private NumberFormat percentFmt;
}

/**
   Les informations requises pour calculer les revenus 
   de la retraite.
*/
class RetireInfo
{
   /**
      Extrait le solde disponible pour une année donnée.
      @param year l'année pour laquelle calculer le 
      solde
      @return la somme d'argent disponible (ou exigée) 
      pour cette année
   */
   public double getBalance(int year)
   {
      if (year < currentAge) return 0;
      else if (year == currentAge)
      {
         age = year;
         balance = savings;
         return balance;
      }
      else if (year == age)
         return balance;
      if (year != age + 1)
         getBalance(year - 1);
      age = year;
      if (age < retireAge)
         balance += contrib;
      else
         balance -= income;
      balance = balance
         * (1 +  (investPercent - inflationPercent));
      return balance;
   }

   /**
      Extrait le montant des économies précédentes.
      @return le montant des économies
   */
   public double getSavings() { return savings; }

   /**
      Définit le montant des économies précédentes.
      @param newValue la somme des économies
   */
   public void setSavings(double newValue) { savings = newValue; }
   
   /**
      Extrait la contribution annuelle du compte de 
      retraite.
      @return la somme de la contribution
   */
   public double getContrib() { return contrib; }

   /**
      Définit la contribution annuelle au compte de 
      retraite.
      @param newValue la somme de la contribution
   */
   public void setContrib(double newValue) { contrib = newValue; }

   /**
      Extrait le revenu annuel.
      @return le montant des revenus
   */
   public double getIncome() { return income; }

   /**
      Définit les revenus annuels.
      @param newValue le montant des revenus
   */
   public void setIncome(double newValue) { income = newValue; }

   /**
      Extrait l'âge actuel.
      @return l'âge
   */
   public int getCurrentAge() { return currentAge; }

   /**
      Définit l'âge actuel.
      @param newValue l'âge
   */
   public void setCurrentAge(int newValue) { currentAge = newValue; }

   /**
      Extrait l'âge souhaité pour la retraite.
      @return l'âge
   */
   public int getRetireAge() { return retireAge; }

   /**
      Définit l'âge souhaité pour la retraite.
      @param newValue l'âge
   */
   public void setRetireAge(int newValue) { retireAge = newValue; }

   /**
      Extrait l'âge de décès attendu.
      @return l'âge
   */
   public int getDeathAge() { return deathAge; }

   /**
      Définit l'âge attendu de décès.
      @param newValue l'âge
   */
   public void setDeathAge(int newValue) { deathAge = newValue; }

   /**
      Extrait le pourcentage d'inflation estimé.
      @return le pourcentage
   */
   public double getInflationPercent() { return inflationPercent; }

   /**
      Définit le pourcentage d'inflation estimé.
      @param newValue le pourcentage
   */
   public void setInflationPercent(double newValue) { 
      inflationPercent = newValue; }

   /**
      Extrait la production estimée de l'investissement.
      @return le pourcentage
   */
   public double getInvestPercent() { return investPercent; }

   /**
      Définit la production estimée de l'investissement.
      @param newValue le pourcentage
   */
   public void setInvestPercent(double newValue) {    
      investPercent = newValue; }

   private double savings;
   private double contrib;
   private double income;
   private int currentAge;
   private int retireAge;
   private int deathAge;
   private double inflationPercent;
   private double investPercent;

   private int age;
   private double balance;
}

/**
   Ce panneau dessine un graphe du résultat de 
   l'investissement.
*/
class RetireCanvas extends JPanel
{
   public RetireCanvas()
   {
      setSize(PANEL_WIDTH, PANEL_HEIGHT);
   }

   /**
      Définit l'information de retraite à dessiner.
      @param newInfo les nouvelles infos de retraite
   */
   public void setInfo(RetireInfo newInfo)
   {
      info = newInfo;
      repaint();
   }

   public void paint(Graphics g)
   {
      Graphics2D g2 = (Graphics2D) g;
      if (info == null) return;

      double minValue = 0;
      double maxValue = 0;
      int i;
      for (i = info.getCurrentAge(); i <= info.getDeathAge(); i++)
      {
         double v = info.getBalance(i);
         if (minValue > v) minValue = v;
         if (maxValue < v) maxValue = v;
      }
      if (maxValue == minValue) return;

      int barWidth = getWidth() / (info.getDeathAge()
         - info.getCurrentAge() + 1);
      double scale = getHeight() / (maxValue - minValue);

      for (i = info.getCurrentAge(); i <= info.getDeathAge(); i++)
      {
         int x1 = (i - info.getCurrentAge()) * barWidth + 1;
         int y1;
         double v = info.getBalance(i);
         int height;
         int yOrigin = (int) (maxValue * scale);

         if (v >= 0)
         {
            y1 = (int) ((maxValue - v) * scale);
            height = yOrigin - y1;
         }
         else
         {
            y1 = yOrigin;
            height = (int) (-v * scale);
         }

         if (i < info.getRetireAge())
            g2.setPaint(colorPre);
         else if (v >= 0)
            g2.setPaint(colorGain);
         else
            g2.setPaint(colorLoss);
         Rectangle2D bar = new Rectangle2D.Double(x1, y1, 
            barWidth - 2, height);
         g2.fill(bar);
         g2.setPaint(Color.black);
         g2.draw(bar);
      }
   }

   /**
      Définit la couleur à utiliser avant la retraite.
      @param color la couleur souhaitée
   */
   public void setColorPre(Color color)
   {
     colorPre = color;
     repaint();
   }

   /**
      Définit la couleur à utiliser après la retraite si 
      le solde du compte est positif.
      @param color la couleur souhaitée
   */
   public void setColorGain(Color color)
   {
      colorGain = color;
      repaint();
   }

   /**
      Définit la couleur à utiliser après la retraite si 
      le solde du compte est négatif.
      @param color la couleur souhaitée
   */
   public void setColorLoss(Color color)
   {
      colorLoss = color;
      repaint();
   }

   private RetireInfo info = null;

   private Color colorPre;
   private Color colorGain;
   private Color colorLoss;
   private static final int PANEL_WIDTH = 400;
   private static final int PANEL_HEIGHT = 200;
}
