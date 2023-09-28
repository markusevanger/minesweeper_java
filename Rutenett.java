import java.util.Random;


public class Rutenett {

    int bredde;
    int hoyde;
    int sjanse;

    Rute[][] rutenett;
    Random random;
    public boolean game_over = false;
    public Mine[] alleMiner = new Mine[0];
    Rute[] alleVanlig = new Rute[0];

    
    public Rutenett(int hoyde, int bredde){
        this.bredde = bredde;
        this.hoyde = hoyde;
        rutenett = new Rute[hoyde][bredde];

        random = new Random();
    }

    public void fyllMedRuter(int sjanse) { // fyller rutenettet med ruter 
        
        for (int kol = 0; kol < rutenett.length; kol++){
            for (int rad = 0; rad < rutenett[kol].length; rad++){

                this.sjanse = sjanse;
                if (random.nextInt(100) > sjanse){ // vis tall 0 < 100, er større en valgt sjanse f.eks 20, lag vanlig rute. 
                    Rute rute = new Rute();

                    // oppdatere array alleVanlig for å senere sjekke om noen vanlige miner er flagget. 
                    rutenett[kol][rad] = rute;
                    Rute[] nyAlleVanlig = new Rute[alleVanlig.length+1];
                    for (int i = 0; i < alleVanlig.length; i++){
                        nyAlleVanlig[i] = alleVanlig[i];
                    }
                    nyAlleVanlig[alleVanlig.length] = rute;
                    alleVanlig = nyAlleVanlig;
                }
                else {
                    Mine mine = new Mine();

                    // Oppdatere array alleMiner for å senere sjekke om noen vanlige miner ikke er flagget. 
                    // all my homies hate ArrayLister. 
                    rutenett[kol][rad] = mine;
                    Mine[] nyAlleMiner = new Mine[alleMiner.length+1];
                    for (int i = 0; i < alleMiner.length; i++){
                        nyAlleMiner[i] = alleMiner[i];
                    }
                    nyAlleMiner[alleMiner.length] = mine;
                    alleMiner = nyAlleMiner;
                }


            }
        }
        tellNaboer();
    }

    private void tellNaboer(){

        //iterer gjennom hver rute, med kordinater
        for (int kol = 0; kol < rutenett.length; kol++){
            for (int rad = 0; rad < rutenett[kol].length; rad++){

                Rute rute = rutenett[kol][rad];
                Rute[] naboer = new Rute[0];

                // iterer gjennom rutes naboer
                int mineTeller = 0;
                for (int kol_offset = -1; kol_offset < 2; kol_offset++){
                    for (int rad_offset = -1; rad_offset < 2; rad_offset++){
                        
                        try{
                           
                            Rute nabo = rutenett[kol + kol_offset][rad + rad_offset]; 
                            Rute[] nyNaboer = new Rute[naboer.length+1];
                            int i;
                            for (i = 0; i < naboer.length; i++){
                                nyNaboer[i] = naboer[i];
                            }
                            
                            naboer = nyNaboer;
                            naboer[i] = nabo;

                            if (nabo instanceof Mine){
                                mineTeller++;
                            }
                        }

                        catch (IndexOutOfBoundsException e){}
                    }
                }

                rute.settAntallMiner(mineTeller);
                rute.naboer = naboer;
            
            }
        }
    }

    public void avlslorAlleRuter(){
        for (Rute[] kol : rutenett){
            for (Rute rad : kol){
                rad.avslort = true;
            }
        }
    }
    
    public void avslorForsteRute(int kol, int rad){

        if (kol < hoyde && rad < bredde){

            Rute rute = rutenett[kol][rad];

            // bygger nytt rutenett om du trykker på Mine første klikk. 
            if (rute instanceof Mine){ // du trykkte på en Mine lmaooo
                while (rute instanceof Mine){
                    rutenett = new Rute[hoyde][bredde];
                    fyllMedRuter(sjanse);
                    rute = rutenett[kol][rad];
                }
            }
            
            int MAKS_ANTALL = (bredde*hoyde)/30;
            int HOYESTE_NABO_SOM_OPNES = 8;
            rute.avslor(0, MAKS_ANTALL, HOYESTE_NABO_SOM_OPNES);

        }

    }


    public void avslorRute(int kol, int rad){

        if (kol < hoyde && rad < bredde){
            Rute rute = rutenett[kol][rad];
            int MAKS_ANTALL = (bredde*hoyde); // 
            int HOYESTE_NABO_SOM_OPNES = 1;
            rute.avslor(0, MAKS_ANTALL, HOYESTE_NABO_SOM_OPNES) ;

            if (rute instanceof Mine){ // du trykkte på en Mine lmaooo
                avlslorAlleRuter();
                game_over = true;
            }
        }
    }

    public boolean erSpilletVunnet(){

        // sjekk om en Mine IKKE er flagget. 
       
        for (Mine mine : alleMiner){
            System.out.print(mine + ", ");
            if (!mine.erFlagget){
                return false;
            }
        }

        // sjekk om en vanlig rute ER flagget
        for (Rute rute : alleVanlig){
            if (rute.erFlagget || !rute.avslort){
                return false;
            }
        }

        return true; // alleMiner = flagget && alleMiner != avlsort && alleVanlig == avslort && alleVanlig != flagget
    }

    public Rute hentCelle(int kol, int rad){
        return rutenett[kol][rad];
    }

    // flagger eller fjerner flagg fra en rute. 
    public String flaggRute(int kol, int rad){
        if (kol < hoyde && rad < bredde){
            Rute rute = rutenett[kol][rad];
            if (!rute.avslort){  // kan ikke flagge en avslort rute. 
                rute.flagg();
                return "";
            }
            else {
                return "Du kan ikke flagge en avslort rute!";
            }
        }
        return "Du kan ikke flagge en rute utenfor rutenettet!";
    }

    public int tellAntallFlagg(){
        int teller = 0;
        for (Rute[] kol : rutenett){
            for (Rute rad :kol){
                if (rad.erFlagget){
                    teller++;
                }
            }
        }
        return teller;
    }

    public String toString(){

        // kordinat system over: 
        System.out.print("* ");
        for (int i = 0; i < rutenett.length; i++){
            System.out.print(i + " ");
        }
        System.out.println();

        int i = 0;
        for (Rute[] kol : rutenett){
            System.out.print(i + " ");
            i++;
            for (Rute rad : kol){
                System.out.print(rad); // skriv ut rad uten linjeskift
            }
            System.out.println(); // linjeskift for hver kol. 
        }

        return "";
    }

}
