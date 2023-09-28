import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class StartGUI extends Assets {
    
    JFrame vindu;
    JPanel vinduInnhold;

    Font font = new Font("sansserif", Font.PLAIN, 17);
    Kontroller kontroller;

    JSlider slider;
    JTextField storrelseFelt;
    JButton startKnapp;     

    public StartGUI(Kontroller k){
        kontroller = k;

        // generelt oppsett. 
        try { UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } 
        catch (Exception e) { 
            System.out.println("Klarte ikke å finne CrossPlatformLookAndFeel");
            System.exit(1); 
        }

        vindu = new JFrame(vinduTittel);
        vindu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // avlsutt program når X trykkes. 
        vindu.setIconImage(flaggIkon.getImage());
        vindu.setBackground(hvit);
        vindu.setSize(400, 200);

        JPanel vinduInnhold = new JPanel();
        
        vinduInnhold.setLayout(new BorderLayout());
        vindu.add(vinduInnhold);

        JPanel storrelse = new JPanel();
        JPanel sjanse = new JPanel();
        JPanel start = new JPanel(); 

        vinduInnhold.add(storrelse, BorderLayout.NORTH);
        vinduInnhold.add(sjanse, BorderLayout.CENTER);
        vinduInnhold.add(start, BorderLayout.SOUTH);

        // storrelse: 
        JLabel sizeTekst = new JLabel("Størrelse: ");
        storrelseFelt = new JTextField("10", 5);
        storrelse.add(sizeTekst);
        storrelse.add(storrelseFelt);

        // sjanse
        JLabel sjanseTekst = new JLabel("% ruter som er miner");
        slider = new JSlider(20, 80, 40);
        slider.setMajorTickSpacing(20);
        slider.setMinorTickSpacing(10);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);

        sjanse.add(slider);
        sjanse.add(sjanseTekst);

        // Start knapp:
        startKnapp = new JButton("Start");
        startKnapp.addMouseListener(new StartListener());
        start.add(startKnapp);

        vindu.setLocationRelativeTo(null);
        //vindu.pack(); // foreløpig(?) skaper problemer med størrelse på vindu. 
        vindu.setVisible(true);
    }

    class StartListener implements MouseListener{
        @Override
        public void mouseClicked(MouseEvent e){
    
            int storrelse = Integer.parseInt(storrelseFelt.getText());
            int sjanse = slider.getValue();
            kontroller.settParametre(storrelse, sjanse);

            vindu.setVisible(false);
            vindu.dispose();
        }
        public void mouseEntered(MouseEvent e) {
            startKnapp.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }  
        public void mouseExited(MouseEvent e) {
            startKnapp.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }  
        public void mousePressed(MouseEvent e) {}  
        public void mouseReleased(MouseEvent e) {}      
    }

}
