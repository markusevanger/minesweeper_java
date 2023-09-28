
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MinesweeperGUI extends Assets {

    int HOYDE;
    int BREDDE;
    
    JFrame vindu;
    JPanel toppPanel;
    JPanel bunnPanel;
    JPanel rutenettPanel;
    JButton[][] rutenettKnapper;
    Font font = new Font("sansserif", Font.PLAIN, 17);

    Kontroller kontroller;
    JButton[][] alleKnapper;
    JLabel flagg;
    JButton reset;
    public MinesweeperGUI(Kontroller kontroller, int hoyde, int bredde){

        this.kontroller = kontroller;
        HOYDE = hoyde;
        BREDDE = bredde;

        // GENERELT OPPSETT:
        // Set vinduer osv til platform (Windows, mac) sin stil.
        try { UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } 
        catch (Exception e) { 
            System.out.println("Klarte ikke å finne CrossPlatformLookAndFeel");
            System.exit(1); 
        }

        vindu = new JFrame("vinduTittel");
        vindu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // avlsutt program når X trykkes. 
        vindu.setIconImage(flaggIkon.getImage());
        vindu.setSize(900, 900);

        JPanel vinduInnhold = new JPanel(); // "body" til vindu. 
        vindu.add(vinduInnhold);
        vinduInnhold.setLayout(new BorderLayout());
        vindu.setBackground(hvit);

        // topp panel med knapper og info
        toppPanel = new JPanel();
        bunnPanel = new JPanel(); // bunnpanel til uhhh....'
        
        vinduInnhold.add(toppPanel, BorderLayout.NORTH);
        vinduInnhold.add(bunnPanel, BorderLayout.SOUTH);

        flagg = new JLabel("Trykk på et felt for å starte!");
        reset = new JButton("RESET");
        reset.addMouseListener(new ResetListener());
        toppPanel.add(flagg);
        toppPanel.add(reset);

        // rutenett!
        rutenettPanel = new JPanel();
        rutenettPanel.setLayout(new GridLayout(kontroller.HOYDE, kontroller.BREDDE));
        vinduInnhold.add(rutenettPanel, BorderLayout.CENTER);

        // lag rutenett med knapper: 
        alleKnapper = lagKnappeRutenett();


        vindu.setLocationRelativeTo(null);
        //vindu.pack(); // foreløpig(?) skaper problemer med størrelse på vindu. 
        vindu.setVisible(true);

    }

    public JButton[][] lagKnappeRutenett(){

        JButton[][] knappeRutenett = new JButton[HOYDE][BREDDE];

        for (int kol = 0; kol < HOYDE; kol++){
            for (int rad = 0; rad < BREDDE; rad++){
                knappeRutenett[kol][rad] = new JButton();

                if ((kol+rad) % 2 == 0){
                    knappeRutenett[kol][rad].setBackground(groenn_mork);
                }
                else {
                    knappeRutenett[kol][rad].setBackground(groenn_lys);
                }

                
                knappeRutenett[kol][rad].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                knappeRutenett[kol][rad].addMouseListener(new ruteTrykk(kol, rad));

                rutenettPanel.add(knappeRutenett[kol][rad]);

            }
        }
        return knappeRutenett;
    }

    public void tegnRutenett(){

        flagg.setText("Flagg: " + (kontroller.hentAntallMiner()-kontroller.hentAntallFlagg()));

        for (int kol = 0; kol < HOYDE; kol++){
            for (int rad = 0; rad < BREDDE; rad++){
                Rute rute = kontroller.hentRute(kol, rad);
                JButton knapp = alleKnapper[kol][rad];

                if (rute.avslort){
                    knapp.setBackground(gul);
                    knapp.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                    knapp.setFocusPainted(false);

                    int naboerErMiner = rute.antallNaboerMiner;
                    if (rute instanceof Mine){
                        knapp.setText("*");
                    }
                    else if (naboerErMiner > 0){
                        knapp.setText(Integer.toString(naboerErMiner));
                        knapp.setIcon(null);
                    }
                }
                else if (rute.erFlagget){
                    knapp.setIcon(flaggIkon);
                }

                else {
                    knapp.setText("");
                    knapp.setIcon(null);
                    
                    if ((kol+rad) % 2 == 0){
                        knapp.setBackground(groenn_mork);
                    }
                    else {
                        knapp.setBackground(groenn_lys);
                    }
                }
                
            }
        }

        // Oppdater annet innhold her 
        // :.......
    }

    class ruteTrykk implements MouseListener{ // Finner ut om trykk var høyre klikk eller venstre klikk og dermed oppdaterer rutenettet. 

        int rad, kol;

        public ruteTrykk(int kol, int rad){
            this.kol = kol;
            this.rad = rad;  
        }
        @Override
        public void mouseClicked(MouseEvent e){

            if (SwingUtilities.isRightMouseButton(e)){
                System.out.println("Flagget rute");
                kontroller.flaggRute(kol, rad);
            }
            else {
                System.out.println("Avslort celle");
                kontroller.avlslorCelle(kol, rad);
            }
        }
        public void mouseEntered(MouseEvent e) {
            if (!kontroller.hentRute(kol, rad).avslort){
                alleKnapper[kol][rad].setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
            
        }  
        public void mouseExited(MouseEvent e) {
            alleKnapper[kol][rad].setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        } 
        public void mousePressed(MouseEvent e) {}  
        public void mouseReleased(MouseEvent e) {}       
    }
    class Avlsort implements MouseListener{ //

        @Override
        public void mouseClicked(MouseEvent e){}
        public void mouseEntered(MouseEvent e) {}  
        public void mouseExited(MouseEvent e) {} 
        public void mousePressed(MouseEvent e) {}  
        public void mouseReleased(MouseEvent e) {}       
    }

    class ResetListener implements MouseListener{

        @Override
        public void mouseClicked(MouseEvent e){
            kontroller = new Kontroller();
            vindu.setVisible(false);
            vindu.dispose();
        }
        public void mouseEntered(MouseEvent e) {
            reset.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }  
        public void mouseExited(MouseEvent e) {
            reset.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }    
        public void mousePressed(MouseEvent e) {}  
        public void mouseReleased(MouseEvent e) {}       
    }
}
