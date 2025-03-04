class Zadanie01 {

    private String dzialanie = "";
    private static final int BLAD = -2_000_000_000;
    private int wynik_dzialania;
    private int wynik_poprzedniego_dzialania;
    private int total_length = 0;
    private String a;
    private String b;
    private String c;
    private String d;

    public Zadanie01() {

    }

    public void input(int bit) {
        dzialanie += bit;

    }

    public int wynik() {

        wynik_poprzedniego_dzialania = BLAD;  // Inicjalizacja zmiennej do przechowywania wyniku poprzedniego działania

        while (true) {

            if (dzialanie.length() < 10) {
                wynik_dzialania = wynik_poprzedniego_dzialania;
                break;
            }

            //wyodrębniamy z ciagu część a
            a = dzialanie.substring(0, 4);
            int ilosc_bitow = 0;
            int k = 1;
            //przeliczamy ilosc bitow na liczbe
            for (int i = 3; i >= 0; i--) {
                ilosc_bitow += (a.charAt(i) - '0') * k;
                k *= 2;
            }

            //liczymy ile znakow powinno miec cale dzialanie
            total_length = 6 + 2 * ilosc_bitow;

            //sprawdzamy czy część a ma conajmniej 2 bity
            if (ilosc_bitow < 2) {
                wynik_dzialania = BLAD;
                dzialanie = dzialanie.substring(total_length);
                break;
            }

            //Jeśli ciąg jest niepełny, zwracamy wynik poprzedniego dzialania (lub jesli to pierwsze dzialanie to zwracamy BLAD)
              if (dzialanie.length() < total_length) {
                 wynik_dzialania = wynik_poprzedniego_dzialania;
                 break;
              }

            //Przypisujemy do czesci b, c oraz d ich wartosci
            int znak_b; // Znak liczby b
            int znak_d; // Znak liczby d
            b = dzialanie.substring(4, 4 + ilosc_bitow);
            znak_b = (b.charAt(0) - '0' == 1) ? -1 : 1;
            b = b.substring(1); //wartosc bezwzgledna z b (bez znaku)
            c = dzialanie.substring(4 + ilosc_bitow, 6 + ilosc_bitow);
            d = dzialanie.substring(6 + ilosc_bitow, (2 * ilosc_bitow) + 6);
            znak_d = (d.charAt(0) - '0' == 1) ? -1 : 1;
            d = d.substring(1); //wartosc bezwzgledna z d (bez znaku)


            //bład jesli próbie dzielenia przez 0
            if(c == "11" && d == "0"){
                dzialanie = dzialanie.substring(total_length);
                wynik_dzialania = BLAD;
                break;
            }

            //wykonujemy poszczególne działania
            switch (c) {
                case "00": // Dodawanie
                    wynik_dzialania = Integer.parseInt(b, 2) * znak_b + Integer.parseInt(d, 2) * znak_d;
                    break;
                case "01": // Odejmowanie
                    wynik_dzialania = Integer.parseInt(b, 2) * znak_b - Integer.parseInt(d, 2) * znak_d;
                    break;
                case "10": // Mnożenie
                    wynik_dzialania = Integer.parseInt(b, 2) * znak_b * Integer.parseInt(d, 2) * znak_d;
                    break;
                case "11": // Dzielenie
                    wynik_dzialania = Integer.parseInt(b, 2) * znak_b / Integer.parseInt(d, 2) * znak_d;
                    break;
                default:
                    //inne wartosci c niz podane w zadaniu
                    wynik_dzialania = BLAD;
                    break;
            }

            wynik_poprzedniego_dzialania = wynik_dzialania;  // Otrzymany wynik przypisujemy do zmiennej wynik_poprzedniego_dzialania

            // Jeśli pozostałe znaki po obliczeniu są krótsze niż totalLength, zwracamy wynik poprzedniego działania
            if(dzialanie.length() > total_length){
                dzialanie = dzialanie.substring(total_length); // Usuwamy poprzednio obliczone dzialanie
            }

            //Jesli dlugosc dzialania jest rowna ilosci wczytanych znakow to zwracamy wynik tego dzialania i usuwamy dzialanie z ciagu
            if(dzialanie.length() == total_length) {
                wynik_dzialania = wynik_poprzedniego_dzialania;
                dzialanie = dzialanie.substring(total_length);
                break;
            }

          }

        return wynik_dzialania;
    }

}



