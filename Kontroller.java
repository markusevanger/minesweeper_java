public class Kontroller {

    public int HOYDE;
    public int BREDDE;
    public int SJANSE;

    Rutenett rutenett;
    public MinesweeperGUI gui;
    StartGUI startGUI;

    boolean forsteTrykk = true;

    public static void main(String[] args) {
        new Kontroller();
    }

    public Kontroller(){
        startGUI = new StartGUI(this); // lager ett lite vindu som definerer HOYDE, BREDDE, SJANSE. 
    }

    public void settParametre(int storrelse, int sjanse){ // startGUI tar i bruk denne metoden når bruker trykker start. Da blir parametrene hoyde, bredde osv satt. 
        
        HOYDE = storrelse;
        BREDDE = storrelse;
        SJANSE = sjanse;
        
        rutenett = new Rutenett(HOYDE, BREDDE);
        rutenett.fyllMedRuter(SJANSE);
        gui = new MinesweeperGUI(this, HOYDE, BREDDE);
    }

    public int hentAntallMiner(){
        return rutenett.alleMiner.length;
    }
    public int hentAntallFlagg(){
        return rutenett.tellAntallFlagg();
    }

    public void avlslorCelle(int kol, int rad){

        if (forsteTrykk){
            rutenett.avslorForsteRute(kol, rad);
            forsteTrykk = false;
        }
        else{
            rutenett.avslorRute(kol, rad);
        }   
        gui.tegnRutenett();
    }
    public void flaggRute(int kol, int rad){
         System.out.println(rutenett.flaggRute(kol, rad));
         gui.tegnRutenett();
    }
    public Rute hentRute(int kol, int rad){

        if (rutenett.hentCelle(kol, rad) == null){
            System.out.println("RUTE PÅ KOL: " + kol + ", " + rad + " er NULL.");
            System.exit(1);
        }

        return rutenett.hentCelle(kol, rad);
    }
}
