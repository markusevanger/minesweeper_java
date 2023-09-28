public class Mine extends Rute {
    @Override
    public String toString(){
        if (avslort){
            return "X ";
        }
        else if (erFlagget){
            return "F ";
        }
        else {
            // return "\u2588 ";
            return "M";
        }
    }
}
