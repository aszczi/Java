import java.util.ArrayList;
import java.util.List;

class Zadanie02 {

    private static final List<Integer> ERROR = new ArrayList<Integer>();
    private static final int MAX_NUMBER = (int) Math.pow(2, 14) - 1;
    private List<Integer> result = new ArrayList<Integer>();
    private List<Integer> wynik = new ArrayList<Integer>();

    public Zadanie02() {
    }

    public List<Integer> code(int length, int par1, String op, int par2) {
        result.clear();
        int MAX_NUM = (int) Math.pow(2, length-1) - 1; 
        //sprawdzenie czy podane dane mieszcza sie w zakresie
        if (length > 15 || length < 2 || par1 > MAX_NUM || par2 > MAX_NUM || par1 < ((-1)*(MAX_NUM)) || par2 < ((-1)*MAX_NUM)) {
            return ERROR;
        }
        //blad przy dzieleniu przez zero
        if(par2==0 && op=="/"){
            return ERROR;
        }

        int op1 = 0;
        int op2 = 0;

        //przeksztalcamy operator na jego zero-jedynkowy odpowiednik
        switch (op) {
            case "+":
                op1 = 0; op2 = 0; break;
            case "-":
                op1 = 0; op2 = 1; break;
            case "*":
                op1 = 1; op2 = 0; break;
            case "/":
                op1 = 1; op2 = 1; break;
            default:
                return ERROR;
        }

        int abs_par1 = Math.abs(par1);
        int abs_par2 = Math.abs(par2);
        //wyznaczamy znak liczb par1 i par2 (1 jesli ujemny 0 jeli dodatni)
        int znak_par1 = par1 < 0 ? 1 : 0;
        int znak_par2 = par2 < 0 ? 1 : 0;

        //uzupelniamy zerami tyle miejsc ile brakuje by segmenty mialy planowana dlugosc
        String par1_binary = String.format("%" + (length - 1) + "s", Integer.toBinaryString(abs_par1)).replace(' ', '0');
        String par2_binary = String.format("%" + (length - 1) + "s", Integer.toBinaryString(abs_par2)).replace(' ', '0');
        String dlugosc = String.format("%4s", Integer.toBinaryString(length)).replace(' ', '0');

        //dodajemy segmenty do listy
        for (int i = 0; i < 4; i++) {
            result.add(dlugosc.charAt(i) - '0');
        }
        result.add(znak_par1);
        for (int i = 0; i < length - 1; i++) {
            result.add(par1_binary.charAt(i) - '0');
        }
        result.add(op1);
        result.add(op2);
        result.add(znak_par2);
        for (int i = 0; i < length - 1; i++) {
            result.add(par2_binary.charAt(i) - '0');
        }

        return result;
    }

    public List<Integer> code( int par1, String op, int par2 ) {
        int length = 0;
        int i;

        //sprawdzamy ktora liczba bedzie potrzebowala wiecej bitow i na tej podstawie wyliczmy pozniej dlugosc segmentu a
        if(Math.abs(par1) > Math.abs(par2)) {
            i = Math.abs(par1);
        }else {
            i = Math.abs(par2);
        }
        //sprawdzamy czy b i d nie sa za duze
        if( par2 > MAX_NUMBER || par1 > MAX_NUMBER || par1<-MAX_NUMBER || par2<-MAX_NUMBER) {
            return ERROR;
        }
        int dlugosc = 1;
        int ilosc_bitow = 1;

        //wyliczanie dlugosci segmentu a
        while(i >= dlugosc){
            ilosc_bitow++;
            dlugosc = dlugosc * 2;
        }

       wynik.clear();
       //gdy juz mamy dlugosc, wywolujemy metode code z parametrem length
       wynik = code(ilosc_bitow, par1, op, par2);

       return wynik;
    }
}