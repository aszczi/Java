import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import static java.lang.Character.isLetter;

public class StringCompressor extends Compression{

    private List<String> words = new ArrayList<>(); // Zmieniona na ArrayList
    private Map<String, Integer> wordCountMap = new HashMap<>(); // Zmieniona na HashMap
    private String input_global = "";
    private List<String> global_code = new ArrayList<>();

    @Override
    public void input(String input) {
        words.clear();
        String CurrentString = "";
        input_global = input;

        for(int i = 0; i < input.length(); i++)
        {
            if( input.charAt(i) == '.' || input.charAt(i) == ',' || input.charAt(i) == ':' || input.charAt(i) == ';'
                    || input.charAt(i) == '"' || input.charAt(i) == '!' || input.charAt(i) == '(' || input.charAt(i) == ')') {

            }else if(input.charAt(i) == ' ' || input.charAt(i) == '\n') {
               if(!CurrentString.isEmpty()){
                   words.add(CurrentString);


               }
                CurrentString = "";
            }else{
                CurrentString = CurrentString + input.charAt(i);

            }

            if((input.length() - 1) == i ){
                if(!CurrentString.isEmpty()){
                    words.add(CurrentString);


                }
                CurrentString = "";
            }
        }//po tej czesci juz mamy slowa w liscie
    }

    @Override
    public String output() {
        String output = "";
        String input = input_global;
        String currentString = "";
        String znaczki = "";
        List<String> znaczki_i_slowa = new ArrayList<>();

        // Podziel tekst na słowa i znaki
        for (int i = 0; i < input.length(); i++) {
            char currentChar = input.charAt(i);

            if (isLetter(currentChar)) {
                currentString += currentChar;  // Budujemy słowo
                if (!znaczki.isEmpty()) {
                    znaczki_i_slowa.add(znaczki);  // Dodajemy znaki (przedrostki)
                    znaczki = "";
                }
            } else {
                znaczki += currentChar;  // Zbieramy znaki (np. spacje, kropki)
                if (!currentString.isEmpty()) {
                    znaczki_i_slowa.add(currentString);  // Dodajemy zbudowane słowo
                    currentString = "";
                }
            }

            // Dodaj ostatnie słowo lub znak
            if (i == input.length() - 1) {
                if (!currentString.isEmpty()) {
                    znaczki_i_slowa.add(currentString);
                }
                if (!znaczki.isEmpty()) {
                    znaczki_i_slowa.add(znaczki);
                }
            }
        }
        //po tym mamy caly input jako liste

        //teraz jesli w znaczki i slowa bedzie slowo takie samo jak code  to wpisujemy numr
        List<String> wywolujemy_kodzislawa = code();
        int counter = 0;
        for (String kod : wywolujemy_kodzislawa) {
            int counter2 = 0;
            for (String slowo : znaczki_i_slowa) {
                if (kod.equals(slowo)) {
                    znaczki_i_slowa.set(counter2, String.valueOf(counter));
                }
                counter2++;
            }
            counter++;
        }


       output = String.join("", znaczki_i_slowa);  // Łączymy listę na jeden ciąg

        // Teraz zamieniamy numery na binarne
        String wynik = zamienLiczbyNaBinarnyTekst(output);
        return wynik;  // Zwracamy zakodowany ciąg
    }


    public String zamienLiczbyNaBinarnyTekst(String input) {
        // Znajdź wszystkie liczby w tekście
        List<Integer> liczby = Pattern.compile("\\d+")
                .matcher(input)
                .results()
                .map(match -> Integer.parseInt(match.group()))
                .collect(Collectors.toList());

        // Jeśli nie ma liczb, zwróć oryginalny tekst
        if (liczby.isEmpty()) {
            return input;
        }

        // Znajdź największą liczbę
        int maxLiczba = Collections.max(liczby);

        // Oblicz liczbę bitów potrzebnych do reprezentacji binarnej
        int liczbaBitow = Integer.toBinaryString(maxLiczba).length();

        // Zamień liczby na binarne reprezentacje
        String wynik = Pattern.compile("\\d+")
                .matcher(input)
                .replaceAll(match -> {
                    int liczba = Integer.parseInt(match.group());
                    return String.format("%" + liczbaBitow + "s", Integer.toBinaryString(liczba))
                            .replace(' ', '0'); // Zamiana na binarne z zerami
                });

        return wynik;
    }

    @Override
    public Map<String, Integer> histogram() {
        // Inicjalizowanie nowej mapy przy każdym wywołaniu metody
        Map<String, Integer> wordsCountMap = new HashMap<>();

        // Dodawanie słów do mapy
        for (String word : words) {
            // Jeśli słowo już istnieje w mapie, zwiększamy jego liczbę
            wordsCountMap.put(word, wordsCountMap.getOrDefault(word, 0) + 1);
        }
        wordCountMap = wordsCountMap;
        return wordsCountMap;
    }

    public Map<String, Integer> zaoszczedzone_miejsce(Map<String, Integer> mapa, int kodowanie) {
        Map<String, Integer> zaoszczedzone_miejsce_map = new HashMap<>();

        // Iteracja po mapie słów i ich liczbie wystąpień
        for (Map.Entry<String, Integer> entry : wordCountMap.entrySet()) {
            String slowo = entry.getKey();          // Słowo
            int liczbaWystapien = entry.getValue(); // Liczba wystąpień słowa

            // Obliczamy zaoszczędzone miejsce dla tego słowa
            int dlugoscSlowa = slowo.length(); // Długość słowa
            int zaoszczedzoneDlaSlowa = (dlugoscSlowa * (liczbaWystapien)) - (kodowanie * liczbaWystapien) - dlugoscSlowa;

            // Dodajemy słowo i zaoszczędzone miejsce do mapy
            zaoszczedzone_miejsce_map.put(slowo, zaoszczedzoneDlaSlowa);
        }

        return zaoszczedzone_miejsce_map;
    }

    public static Map<String, Integer> sortowansko_mapy(Map<String, Integer> mapa) {

        Map<String, Integer> posortowanaMapa = new LinkedHashMap<>();

        mapa.entrySet()
                .stream()
                .sorted((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue())) // porównanie wartości
                .forEach(entry -> posortowanaMapa.put(entry.getKey(), entry.getValue())); // dodawanie do posortowanej mapy

        return posortowanaMapa;
    }

    @Override
    public List<String> code(){
        List<String> code = new ArrayList<>();
        global_code = new ArrayList<>();
        histogram();
        if (wordCountMap.isEmpty()) {
            return code;
        }

        //sumy zaoszczedzonego miejsca przy poszczegolnych kodowaniach
        int sum0=0;
        int sum2 = 0;
        int sum4 = 0;
        int sum8 = 0;
        int sum16 = 0;
        int sum32 = 0;
        int sum64 = 0;

        int size = wordCountMap.size();

        Map<String, Integer> mapa_posortowana2 = new HashMap<>();
        Map<String, Integer> mapa_posortowana4 = new HashMap<>();
        Map<String, Integer> mapa_posortowana8 = new HashMap<>();
        Map<String, Integer> mapa_posortowana16 = new HashMap<>();
        Map<String, Integer> mapa_posortowana32 = new HashMap<>();
        Map<String, Integer> mapa_posortowana64 = new HashMap<>();

        //porownac jeszcze do pustej listy bez zmian



        int count = 0;
        if( size>=2) {
            Map<String, Integer> zaoszczedzone_miejsce2 = zaoszczedzone_miejsce(wordCountMap, 1);
            mapa_posortowana2 = sortowansko_mapy(zaoszczedzone_miejsce2);
            //zliczanie sumy 2 pierwszych
            //reszte tak samo dla 4 8 16 32 64

            for (Map.Entry<String, Integer> entry : mapa_posortowana2.entrySet()) {
                sum2 += entry.getValue();
                count++;
                if (count == 2) break;  // Zatrzymujemy pętlę po wypisaniu dwóch pierwszych elementów
            }
        }
//pozmieniac to drugie na linkedhashmap
        if( size>=4) {
            Map<String, Integer> zaoszczedzone_miejsce4 = zaoszczedzone_miejsce(wordCountMap, 2);
            mapa_posortowana4 = sortowansko_mapy(zaoszczedzone_miejsce4);
            //zliczanie sumy 4 pierwszych

            count = 0;
            for (Map.Entry<String, Integer> entry : mapa_posortowana4.entrySet()) {
                sum4 += entry.getValue();
                count++;
                if (count == 4) break;  // Zatrzymujemy pętlę po wypisaniu 4 pierwszych elementów
            }
        }
        if( size>=8) {
            Map<String, Integer> zaoszczedzone_miejsce8 = zaoszczedzone_miejsce(wordCountMap, 3);
             mapa_posortowana8 = sortowansko_mapy(zaoszczedzone_miejsce8);
            //zliczanie sumy 8 pierwszych

            count = 0;
            for (Map.Entry<String, Integer> entry : mapa_posortowana8.entrySet()) {
                sum8 += entry.getValue();
                count++;
                if (count == 8) break;  // Zatrzymujemy pętlę po wypisaniu 8 pierwszych elementów
            }
        }

        if( size>=16) {
            Map<String, Integer> zaoszczedzone_miejsce16 = zaoszczedzone_miejsce(wordCountMap, 4);
            mapa_posortowana16 = sortowansko_mapy(zaoszczedzone_miejsce16);
            //zliczanie sumy 16 pierwszych

            count = 0;
            for (Map.Entry<String, Integer> entry : mapa_posortowana16.entrySet()) {
                sum16 += entry.getValue();
                count++;
                if (count == 16) break;  // Zatrzymujemy pętlę po wypisaniu 16 pierwszych elementów
            }
        }
        if( size>=32) {
            Map<String, Integer> zaoszczedzone_miejsce32 = zaoszczedzone_miejsce(wordCountMap, 5);
             mapa_posortowana32 = sortowansko_mapy(zaoszczedzone_miejsce32);
            //zliczanie sumy 32 pierwszych

            count = 0;
            for (Map.Entry<String, Integer> entry : mapa_posortowana32.entrySet()) {
                sum32 += entry.getValue();
                count++;
                if (count == 32) break;  // Zatrzymujemy pętlę po wypisaniu 32 pierwszych elementów
            }
        }
        if( size>=64) {
            Map<String, Integer> zaoszczedzone_miejsce64 = zaoszczedzone_miejsce(wordCountMap, 6);
            mapa_posortowana64 = sortowansko_mapy(zaoszczedzone_miejsce64);
            //zliczanie sumy 64 pierwszych

            count = 0;
            for (Map.Entry<String, Integer> entry : mapa_posortowana64.entrySet()) {
                sum64 += entry.getValue();
                count++;
                if (count == 64) break;  // Zatrzymujemy pętlę po wypisaniu 64 pierwszych elementów
            }
        }
//sprawdzamy, ktora sum jest najwieksza
    int kodowanie = 0;
        if (sum0 >= sum2 && sum0 >= sum4 && sum0 >= sum8 && sum0 >= sum16 && sum0 >= sum32 && sum0 >= sum64) {
           kodowanie = 0;
        } else if (sum2 >= sum0 && sum2 >= sum4 && sum2 >= sum8 && sum2 >= sum16 && sum2 >= sum32 && sum2 >= sum64) {
            kodowanie = 2;
        } else if (sum4 >= sum0 && sum4 >= sum2 && sum4 >= sum8 && sum4 >= sum16 && sum4 >= sum32 && sum4 >= sum64) {
            kodowanie = 4;
        } else if (sum8 >= sum0 && sum8 >= sum2 && sum8 >= sum4 && sum8 >= sum16 && sum8 >= sum32 && sum8 >= sum64) {
            kodowanie = 8;
        } else if (sum16 >= sum0 && sum16 >= sum2 && sum16>= sum4 && sum16 >= sum8 && sum16 >= sum32 && sum16 >= sum64) {
            kodowanie = 16;
        } else if (sum32 >= sum0 && sum32 >= sum2 && sum32 >= sum4 && sum32 >= sum8 && sum32 >= sum16 && sum32 >= sum64) {
            kodowanie = 32;
        } else if (sum64 >= sum0 && sum64 >= sum2 && sum64 >= sum4 && sum64 >= sum8 && sum64 >= sum16 && sum64 >= sum32) {
            kodowanie = 64;
        }

        if(kodowanie == 0){
            code = new ArrayList<>();
        }else  if(kodowanie == 2){
            count = 0;
            for (Map.Entry<String, Integer> entry : mapa_posortowana2.entrySet()) {
                code.add(entry.getKey() );
                count++;
                if (count == 2) break;  // Zatrzymujemy pętlę po wypisaniu dwóch pierwszych elementów
            }
        } else if(kodowanie == 4){
            count = 0;
            for (Map.Entry<String, Integer> entry : mapa_posortowana4.entrySet()) {
                code.add(entry.getKey());
                count++;
                if (count == 4) break;  // Zatrzymujemy pętlę po wypisaniu 4 pierwszych elementów
            }
        } else if(kodowanie == 8){
            count = 0;
            for (Map.Entry<String, Integer> entry : mapa_posortowana8.entrySet()) {
                code.add(entry.getKey() );
                count++;
                if (count == 8) break;  // Zatrzymujemy pętlę po wypisaniu 8 pierwszych elementów
            }
        } else if(kodowanie == 16){
            count = 0;
            for (Map.Entry<String, Integer> entry : mapa_posortowana16.entrySet()) {
                code.add(entry.getKey() );
                count++;
                if (count == 16) break;  // Zatrzymujemy pętlę po wypisaniu 16 pierwszych elementów
            }
        } else if(kodowanie == 32){
            count = 0;
            for (Map.Entry<String, Integer> entry : mapa_posortowana32.entrySet()) {
                code.add(entry.getKey() );
                count++;
                if (count == 32) break;  // Zatrzymujemy pętlę po wypisaniu 32 pierwszych elementów
            }
        } else if(kodowanie == 64){
            count = 0;
            for (Map.Entry<String, Integer> entry : mapa_posortowana64.entrySet()) {
                code.add(entry.getKey() );
                count++;
                if (count == 64) break;  // Zatrzymujemy pętlę po wypisaniu 64 pierwszych elementów
            }
        }

        global_code = code;
        return code;
    }

    @Override
    public String decode(String input, List<String> code) {
        int rozmiar = code.size();
        String output = input;


        int liczbaBitow = Integer.toBinaryString(rozmiar - 1).length();

        // Budowanie mapy zamiany
        Map<String, String> binaryToCodeMap = new HashMap<>();
        for (int i = 0; i < rozmiar; i++) {
            String binaryString = String.format("%" + liczbaBitow + "s", Integer.toBinaryString(i)).replace(' ', '0');
            binaryToCodeMap.put(binaryString, code.get(i));
        }

        // Przetwarzanie ciągu wejściowego
        for (Map.Entry<String, String> entry : binaryToCodeMap.entrySet()) {
            output = output.replace(entry.getKey(), entry.getValue());
        }

        return output;
    }

}
