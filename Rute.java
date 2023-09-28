public class Rute {

    public Rute[] naboer;
    public int antallNaboerMiner;
    public boolean erFlagget = false;
    public boolean avslort = false;

    public void settAntallMiner(int ny){
        antallNaboerMiner = ny;
    }

    public void avslor(int antall, int MAKS_ANTALL, int HOYESTE_NABO_SOM_OPNES){
        antall++;
        avslort = true;

        if (antall < MAKS_ANTALL){
            for (Rute nabo : naboer){
                if (!(nabo instanceof Mine) && !nabo.erFlagget && !nabo.avslort && nabo.antallNaboerMiner <= HOYESTE_NABO_SOM_OPNES){
                    nabo.avslor(antall, MAKS_ANTALL, HOYESTE_NABO_SOM_OPNES);
            }
        }

        }

    }

    public void flagg(){
        erFlagget = !erFlagget;
    }

    public String toString(){

        if (avslort){
            return antallNaboerMiner + " "; 
        }
        else if (erFlagget){
            return "F ";
        }
        else {
            //return "\u2588 ";
            return ".";
        }

        
    }

}