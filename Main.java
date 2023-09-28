import java.util.Scanner;

public class Main {

    private static int HOYDE;
    private static int BREDDE;
    private static int SJANSE;

    public static void main(String[] args) {

        HOYDE = 3;
        BREDDE = 3;
        SJANSE = 20;
        boolean avslortMinstEnRute = false;

        Rutenett rtnt = new Rutenett(HOYDE, BREDDE);
        rtnt.fyllMedRuter(SJANSE); // 20 prosent

        Scanner sc = new Scanner(System.in);

        String input = "ssd";
        String info = "";
        while (!input.equals("x") && !rtnt.game_over){
            luft(100);

            System.out.println("MINESWEEPER!");
            System.out.println("Eks: 2 4 for aa avslore | x for aa avslutte. ");
            System.out.println(info);

            luft(1);

            System.out.println(rtnt); // RUTENETT

            System.out.println("Flagget: " + rtnt.tellAntallFlagg() + " / " + rtnt.alleMiner.length);
            
            for (Mine m : rtnt.alleMiner){
                System.out.print(m + ", ");
            }
            System.out.println();
            System.out.println("Hvilken rute vil du avslore?");
            System.out.print(">> ");
            input = sc.nextLine();

            try{
                info="";
                boolean skalFlagges = false;
                String[] kordinaterStr = input.split(" ");
                
                if (kordinaterStr.length == 3){
                    if (kordinaterStr[2].equals("f")){
                        skalFlagges = true;
                    }
                }

                int[] kordinater = new int[2];
                for (int i = 0; i < 2; i++){
                    kordinater[i] = Integer.parseInt(kordinaterStr[i]);
                }
                
                if (kordinater[0] <= HOYDE && kordinater[1] <= BREDDE){
                    if (skalFlagges){
                        info = rtnt.flaggRute(kordinater[0], kordinater[1]);
                    }
                    else{
                        if (avslortMinstEnRute){
                            rtnt.avslorRute(kordinater[0], kordinater[1]);
                        }
                        else{
                            rtnt.avslorForsteRute(kordinater[0], kordinater[1]);
                            avslortMinstEnRute = true;
                        }
                        
                    }
                    
                }
                else {
                    info = "Tallene du skrev inn er utenfor rutenettet!";
                }
                
            }
            catch (Exception e){
                info = "Ugyldig inndata! Skriv tall ettefulgt av annet tall, skilt med mellomrom.";
            }

            if (rtnt.game_over){
                tapte(rtnt);
            }
            else if (rtnt.erSpilletVunnet()){
                vant(rtnt);
                rtnt.game_over = true;
            }
            
        }


        sc.close();
    }

    private static void tapte(Rutenett rtnt){
        luft(100);
        System.out.println(rtnt);
        luft(2);
        System.out.println("Du tapte: " + rtnt.game_over);
    }

    private static void vant(Rutenett rtnt){
        luft(100);
        System.out.println(rtnt);
        luft(2);
        System.out.println("DU VANT!!!!!!!!!!!!!! <333333");
        System.out.println("DU VANT!!!!!!!!!!!!!! <333333");
        System.out.println("DU VANT!!!!!!!!!!!!!! <333333");
        System.out.println("DU VANT!!!!!!!!!!!!!! <333333");
        System.out.println("DU VANT!!!!!!!!!!!!!! <333333");
        System.out.println("DU VANT!!!!!!!!!!!!!! <333333");
        System.out.println("DU VANT!!!!!!!!!!!!!! <333333");

    }


    private static void luft(int ant){
        for (int i = 0; i < ant; i++){
            System.out.println();
        }
    }
}
