package org.example;
//do odsylania usunac package !!!!

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class EKuchnia implements Kuchnia {

    Map<Produkt, Integer> stanSpizarni = new HashMap<Produkt, Integer>();

    @Override
    public void dodajDoSpizarni(Produkt produkt, int gram) {
        // Sprawdzamy, czy produkt już istnieje w spiżarni
        if (stanSpizarni.containsKey(produkt)) {
            // Zwiększamy wartość istniejącego produktu
            int obecnaWartosc = stanSpizarni.get(produkt);
            stanSpizarni.put(produkt, obecnaWartosc + gram);
        } else {
            // Dodajemy nowy produkt
            stanSpizarni.put(produkt, gram);
        }
    }


    @Override
    public Set<Skladnik> przeliczPrzepis(Przepis przepis) {
        Set<Skladnik> skladniki = new HashSet<Skladnik>();
        Set<Skladnik> potrzebneDoPrzepisu = przepis.sklad();
        double aktualnyStosunek = Double.MAX_VALUE;

        for (Skladnik skladnik : potrzebneDoPrzepisu) {
            Produkt produkt = skladnik.produkt();
            int ileTrzeba = skladnik.gramow();

            // Sprawdzamy dostępność składnika w spiżarni
            int ileMamy = stanSpizarni.getOrDefault(produkt, 0);

            if (ileMamy == 0) {
                return new HashSet<>(); // Zwracamy pusty zestaw, bo brakuje nam skladnika
            } else {
                // Obliczamy stosunek
                double stosunek = ileMamy / (double) ileTrzeba;
                aktualnyStosunek = Math.min(aktualnyStosunek, stosunek); // Wybieramy minimalny stosunek
            }
        }

        // Przeliczamy składniki zgodnie z najniższym stosunkiem
        for (Skladnik skladnik : potrzebneDoPrzepisu) {
            Produkt produkt = skladnik.produkt();
            double ileTrzeba = skladnik.gramow() * aktualnyStosunek;
            int intIleTrzeba = (int) Math.floor(ileTrzeba); // Zaokrąglamy ilość w dół

            // Tworzymy nowy składnik i dodajemy go do zestawu
            skladniki.add(new Skladnik(produkt, intIleTrzeba));
        }

        return skladniki;
    }


       @Override
    public boolean wykonaj(Przepis przepis) {
       // Set<Skladnik> potrzebneSkladniki = przeliczPrzepis(przepis);
        Set<Skladnik> potrzebneSkladniki = przepis.sklad();

        if (potrzebneSkladniki.isEmpty()) {
            return false; // Jeśli brak składników w spiżarni, zwracamy false
        }

        // Sprawdzamy, czy mamy wystarczająco składników
        for (Skladnik skladnik : potrzebneSkladniki) {
            Produkt produkt = skladnik.produkt();
            int ileTrzeba = skladnik.gramow();

            if (stanSpizarni.getOrDefault(produkt, 0) < ileTrzeba) {
                return false; // Jeśli któregokolwiek składnika jest za mało
            }
        }

        // Wykonanie przepisu (zmniejszamy ilość składników)
        for (Skladnik skladnik : potrzebneSkladniki) {
            Produkt produkt = skladnik.produkt();
            int obecnaWartosc = stanSpizarni.get(produkt);
            stanSpizarni.put(produkt, obecnaWartosc - skladnik.gramow());
        }

        return true;
    }


    @Override
    public Map<Produkt, Integer> stanSpizarni(){
      Map<Produkt, Integer>  aktualnyStan = new HashMap<Produkt, Integer>();

        for (Map.Entry<Produkt, Integer> entry : stanSpizarni.entrySet()) {
            Produkt produkt = entry.getKey();
            int wartosc = entry.getValue();

            if(wartosc > 0){
                aktualnyStan.put(produkt, wartosc);
            }
        }

        return aktualnyStan;
    }


        public static void main(String[] args) {
          //  EKuchnia kuchnia = new EKuchnia() {}; // Tworzymy instancję klasy abstrakcyjnej

            Przepisy.grzanie();
            Przepisy.grzanie2();
            Przepisy.grzanie3();
            Przepisy.grzanie4();
        }

        public static class Przepisy {
            public static void grzanie() {
                // Wygenerowany kod
                // Przyklad implementacji Kuchni
                Kuchnia kuchnia = new EKuchnia();
                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 1562); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_11a0923808ff4c2bbb457b5d74685626_skladniki = Set.of(
                        new Skladnik(Produkt.MAKA_ZIEMNIACZANA, 1533),
                        new Skladnik(Produkt.CUKIER_WANILIOWY, 1033),
                        new Skladnik(Produkt.PROSZEK_DO_PIECZENIA, 949),
                        new Skladnik(Produkt.DROZDZE, 900),
                        new Skladnik(Produkt.TWAROG, 2414),
                        new Skladnik(Produkt.MARGARYNA, 214),
                        new Skladnik(Produkt.CUKIER, 506)
                );
                Przepis przepis_11a0923808ff4c2bbb457b5d74685626 = () -> przepis_11a0923808ff4c2bbb457b5d74685626_skladniki;



                Set<Skladnik> przeliczony_przepis_11a0923808ff4c2bbb457b5d74685626 = kuchnia.przeliczPrzepis(przepis_11a0923808ff4c2bbb457b5d74685626);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_11a0923808ff4c2bbb457b5d74685626);



                Przepis nowyPrzepis_przepis_11a0923808ff4c2bbb457b5d74685626 = () -> przeliczony_przepis_11a0923808ff4c2bbb457b5d74685626;

                if (przeliczony_przepis_11a0923808ff4c2bbb457b5d74685626.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_11a0923808ff4c2bbb457b5d74685626)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_11a0923808ff4c2bbb457b5d74685626)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }



                // Przyklad implementacji Kuchni
                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 1471); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_daf9ae0a0d4c47b0b4115fcfb625e8a3_skladniki = Set.of(
                        new Skladnik(Produkt.MARGARYNA, 1952),
                        new Skladnik(Produkt.DROZDZE, 790),
                        new Skladnik(Produkt.PROSZEK_DO_PIECZENIA, 1247),
                        new Skladnik(Produkt.CUKIER_PUDER, 543),
                        new Skladnik(Produkt.MASLO, 1167),
                        new Skladnik(Produkt.SOL, 2969)
                );
                Przepis przepis_daf9ae0a0d4c47b0b4115fcfb625e8a3 = () -> przepis_daf9ae0a0d4c47b0b4115fcfb625e8a3_skladniki;




                Set<Skladnik> przeliczony_przepis_daf9ae0a0d4c47b0b4115fcfb625e8a3 = kuchnia.przeliczPrzepis(przepis_daf9ae0a0d4c47b0b4115fcfb625e8a3);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_daf9ae0a0d4c47b0b4115fcfb625e8a3);


                Przepis nowyPrzepis_przepis_daf9ae0a0d4c47b0b4115fcfb625e8a3 = () -> przeliczony_przepis_daf9ae0a0d4c47b0b4115fcfb625e8a3;

                if (przeliczony_przepis_daf9ae0a0d4c47b0b4115fcfb625e8a3.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_daf9ae0a0d4c47b0b4115fcfb625e8a3)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_daf9ae0a0d4c47b0b4115fcfb625e8a3)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }



                // Przyklad implementacji Kuchni
                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 624); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_9e3aa45a554a444c807ddab36d35c35e_skladniki = Set.of(
                        new Skladnik(Produkt.MAKA_PSZENNA, 1790),
                        new Skladnik(Produkt.SOL, 2209),
                        new Skladnik(Produkt.RODZYNKI, 1400),
                        new Skladnik(Produkt.CUKIER, 2194),
                        new Skladnik(Produkt.MARGARYNA, 554),
                        new Skladnik(Produkt.DROZDZE, 963),
                        new Skladnik(Produkt.CUKIER_PUDER, 1522),
                        new Skladnik(Produkt.OLEJ_ROSLINNY, 2814),
                        new Skladnik(Produkt.WODA, 1654)
                );
                Przepis przepis_9e3aa45a554a444c807ddab36d35c35e = () -> przepis_9e3aa45a554a444c807ddab36d35c35e_skladniki;




                Set<Skladnik> przeliczony_przepis_9e3aa45a554a444c807ddab36d35c35e = kuchnia.przeliczPrzepis(przepis_9e3aa45a554a444c807ddab36d35c35e);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_9e3aa45a554a444c807ddab36d35c35e);


                Przepis nowyPrzepis_przepis_9e3aa45a554a444c807ddab36d35c35e = () -> przeliczony_przepis_9e3aa45a554a444c807ddab36d35c35e;

                if (przeliczony_przepis_9e3aa45a554a444c807ddab36d35c35e.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_9e3aa45a554a444c807ddab36d35c35e)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_9e3aa45a554a444c807ddab36d35c35e)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }



                // Przyklad implementacji Kuchni
                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 1426); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_01418275b66546f7b483e3c488018370_skladniki = Set.of(
                        new Skladnik(Produkt.MAKA_RAZOWA, 1486),
                        new Skladnik(Produkt.MASLO, 820),
                        new Skladnik(Produkt.MLEKO, 103),
                        new Skladnik(Produkt.DROZDZE, 122),
                        new Skladnik(Produkt.SOL, 563),
                        new Skladnik(Produkt.RODZYNKI, 616)
                );
                Przepis przepis_01418275b66546f7b483e3c488018370 = () -> przepis_01418275b66546f7b483e3c488018370_skladniki;




                Set<Skladnik> przeliczony_przepis_01418275b66546f7b483e3c488018370 = kuchnia.przeliczPrzepis(przepis_01418275b66546f7b483e3c488018370);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_01418275b66546f7b483e3c488018370);


                Przepis nowyPrzepis_przepis_01418275b66546f7b483e3c488018370 = () -> przeliczony_przepis_01418275b66546f7b483e3c488018370;

                if (przeliczony_przepis_01418275b66546f7b483e3c488018370.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_01418275b66546f7b483e3c488018370)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_01418275b66546f7b483e3c488018370)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }



                // Przyklad implementacji Kuchni
                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 539); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_249d6530840740b192143f918da37cf9_skladniki = Set.of(
                        new Skladnik(Produkt.SOL, 127),
                        new Skladnik(Produkt.MASLO, 86),
                        new Skladnik(Produkt.MAKA_PSZENNA, 259),
                        new Skladnik(Produkt.MAKA_RAZOWA, 181),
                        new Skladnik(Produkt.DROZDZE, 402),
                        new Skladnik(Produkt.PROSZEK_DO_PIECZENIA, 1044),
                        new Skladnik(Produkt.TWAROG, 372)
                );
                Przepis przepis_249d6530840740b192143f918da37cf9 = () -> przepis_249d6530840740b192143f918da37cf9_skladniki;




                Set<Skladnik> przeliczony_przepis_249d6530840740b192143f918da37cf9 = kuchnia.przeliczPrzepis(przepis_249d6530840740b192143f918da37cf9);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_249d6530840740b192143f918da37cf9);


                Przepis nowyPrzepis_przepis_249d6530840740b192143f918da37cf9 = () -> przeliczony_przepis_249d6530840740b192143f918da37cf9;

                if (przeliczony_przepis_249d6530840740b192143f918da37cf9.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_249d6530840740b192143f918da37cf9)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_249d6530840740b192143f918da37cf9)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }



                // Przyklad implementacji Kuchni
                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 113); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_d113b7efb54a4c37acab4f292b6783b2_skladniki = Set.of(
                        new Skladnik(Produkt.WODA, 448),
                        new Skladnik(Produkt.CUKIER_PUDER, 1505),
                        new Skladnik(Produkt.SOL, 940),
                        new Skladnik(Produkt.MAKA_RAZOWA, 467),
                        new Skladnik(Produkt.MARGARYNA, 2251)
                );
                Przepis przepis_d113b7efb54a4c37acab4f292b6783b2 = () -> przepis_d113b7efb54a4c37acab4f292b6783b2_skladniki;




                Set<Skladnik> przeliczony_przepis_d113b7efb54a4c37acab4f292b6783b2 = kuchnia.przeliczPrzepis(przepis_d113b7efb54a4c37acab4f292b6783b2);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_d113b7efb54a4c37acab4f292b6783b2);


                Przepis nowyPrzepis_przepis_d113b7efb54a4c37acab4f292b6783b2 = () -> przeliczony_przepis_d113b7efb54a4c37acab4f292b6783b2;

                if (przeliczony_przepis_d113b7efb54a4c37acab4f292b6783b2.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_d113b7efb54a4c37acab4f292b6783b2)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_d113b7efb54a4c37acab4f292b6783b2)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }



                // Przyklad implementacji Kuchni
                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 855); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_412ba97c4d564b998b1485d289d1f4aa_skladniki = Set.of(
                        new Skladnik(Produkt.MLEKO, 616),
                        new Skladnik(Produkt.PROSZEK_DO_PIECZENIA, 415),
                        new Skladnik(Produkt.RODZYNKI, 282),
                        new Skladnik(Produkt.MAKA_PSZENNA, 83),
                        new Skladnik(Produkt.CUKIER_PUDER, 725),
                        new Skladnik(Produkt.WODA, 1001)
                );
                Przepis przepis_412ba97c4d564b998b1485d289d1f4aa = () -> przepis_412ba97c4d564b998b1485d289d1f4aa_skladniki;




                Set<Skladnik> przeliczony_przepis_412ba97c4d564b998b1485d289d1f4aa = kuchnia.przeliczPrzepis(przepis_412ba97c4d564b998b1485d289d1f4aa);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_412ba97c4d564b998b1485d289d1f4aa);


                Przepis nowyPrzepis_przepis_412ba97c4d564b998b1485d289d1f4aa = () -> przeliczony_przepis_412ba97c4d564b998b1485d289d1f4aa;

                if (przeliczony_przepis_412ba97c4d564b998b1485d289d1f4aa.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_412ba97c4d564b998b1485d289d1f4aa)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_412ba97c4d564b998b1485d289d1f4aa)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }



                // Przyklad implementacji Kuchni
                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 431); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_e71807bc8790436cacc11458e937315c_skladniki = Set.of(
                        new Skladnik(Produkt.WODA, 2254),
                        new Skladnik(Produkt.MARGARYNA, 2995),
                        new Skladnik(Produkt.MAKA_ZIEMNIACZANA, 1459),
                        new Skladnik(Produkt.CUKIER, 972),
                        new Skladnik(Produkt.OLEJ_ROSLINNY, 2296),
                        new Skladnik(Produkt.CUKIER_WANILIOWY, 199),
                        new Skladnik(Produkt.SOL, 123),
                        new Skladnik(Produkt.DROZDZE, 2828),
                        new Skladnik(Produkt.TWAROG, 520)
                );
                Przepis przepis_e71807bc8790436cacc11458e937315c = () -> przepis_e71807bc8790436cacc11458e937315c_skladniki;




                Set<Skladnik> przeliczony_przepis_e71807bc8790436cacc11458e937315c = kuchnia.przeliczPrzepis(przepis_e71807bc8790436cacc11458e937315c);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_e71807bc8790436cacc11458e937315c);


                Przepis nowyPrzepis_przepis_e71807bc8790436cacc11458e937315c = () -> przeliczony_przepis_e71807bc8790436cacc11458e937315c;

                if (przeliczony_przepis_e71807bc8790436cacc11458e937315c.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_e71807bc8790436cacc11458e937315c)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_e71807bc8790436cacc11458e937315c)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }



                // Przyklad implementacji Kuchni
                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 1228); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_e25bcd04f659456d8c9c49787edc4cc1_skladniki = Set.of(
                        new Skladnik(Produkt.WODA, 2088),
                        new Skladnik(Produkt.MASLO, 803),
                        new Skladnik(Produkt.MAKA_RAZOWA, 1742),
                        new Skladnik(Produkt.DROZDZE, 279),
                        new Skladnik(Produkt.MAKA_PSZENNA, 857),
                        new Skladnik(Produkt.CUKIER_PUDER, 744),
                        new Skladnik(Produkt.SOL, 1967),
                        new Skladnik(Produkt.OLEJ_ROSLINNY, 1483)
                );
                Przepis przepis_e25bcd04f659456d8c9c49787edc4cc1 = () -> przepis_e25bcd04f659456d8c9c49787edc4cc1_skladniki;




                Set<Skladnik> przeliczony_przepis_e25bcd04f659456d8c9c49787edc4cc1 = kuchnia.przeliczPrzepis(przepis_e25bcd04f659456d8c9c49787edc4cc1);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_e25bcd04f659456d8c9c49787edc4cc1);


                Przepis nowyPrzepis_przepis_e25bcd04f659456d8c9c49787edc4cc1 = () -> przeliczony_przepis_e25bcd04f659456d8c9c49787edc4cc1;

                if (przeliczony_przepis_e25bcd04f659456d8c9c49787edc4cc1.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_e25bcd04f659456d8c9c49787edc4cc1)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_e25bcd04f659456d8c9c49787edc4cc1)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }



                // Przyklad implementacji Kuchni
                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 239); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_d5bb0ea0a03e46a19e0ee1b6285e4084_skladniki = Set.of(
                        new Skladnik(Produkt.CUKIER_PUDER, 1654),
                        new Skladnik(Produkt.CUKIER_WANILIOWY, 762),
                        new Skladnik(Produkt.MAKA_PSZENNA, 2323),
                        new Skladnik(Produkt.PROSZEK_DO_PIECZENIA, 1851),
                        new Skladnik(Produkt.TWAROG, 2056),
                        new Skladnik(Produkt.SOL, 783),
                        new Skladnik(Produkt.OLEJ_ROSLINNY, 571),
                        new Skladnik(Produkt.CUKIER, 1869)
                );
                Przepis przepis_d5bb0ea0a03e46a19e0ee1b6285e4084 = () -> przepis_d5bb0ea0a03e46a19e0ee1b6285e4084_skladniki;




                Set<Skladnik> przeliczony_przepis_d5bb0ea0a03e46a19e0ee1b6285e4084 = kuchnia.przeliczPrzepis(przepis_d5bb0ea0a03e46a19e0ee1b6285e4084);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_d5bb0ea0a03e46a19e0ee1b6285e4084);


                Przepis nowyPrzepis_przepis_d5bb0ea0a03e46a19e0ee1b6285e4084 = () -> przeliczony_przepis_d5bb0ea0a03e46a19e0ee1b6285e4084;

                if (przeliczony_przepis_d5bb0ea0a03e46a19e0ee1b6285e4084.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_d5bb0ea0a03e46a19e0ee1b6285e4084)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_d5bb0ea0a03e46a19e0ee1b6285e4084)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }



                // Przyklad implementacji Kuchni
                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 166); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_44e7d58bea0845cc8a9f076a35d8ccfe_skladniki = Set.of(
                        new Skladnik(Produkt.MAKA_ZIEMNIACZANA, 1867),
                        new Skladnik(Produkt.OLEJ_ROSLINNY, 1020),
                        new Skladnik(Produkt.DROZDZE, 405),
                        new Skladnik(Produkt.MAKA_RAZOWA, 1299),
                        new Skladnik(Produkt.SOL, 1079),
                        new Skladnik(Produkt.MASLO, 969),
                        new Skladnik(Produkt.TWAROG, 629),
                        new Skladnik(Produkt.MLEKO, 1206)
                );
                Przepis przepis_44e7d58bea0845cc8a9f076a35d8ccfe = () -> przepis_44e7d58bea0845cc8a9f076a35d8ccfe_skladniki;




                Set<Skladnik> przeliczony_przepis_44e7d58bea0845cc8a9f076a35d8ccfe = kuchnia.przeliczPrzepis(przepis_44e7d58bea0845cc8a9f076a35d8ccfe);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_44e7d58bea0845cc8a9f076a35d8ccfe);


                Przepis nowyPrzepis_przepis_44e7d58bea0845cc8a9f076a35d8ccfe = () -> przeliczony_przepis_44e7d58bea0845cc8a9f076a35d8ccfe;

                if (przeliczony_przepis_44e7d58bea0845cc8a9f076a35d8ccfe.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_44e7d58bea0845cc8a9f076a35d8ccfe)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_44e7d58bea0845cc8a9f076a35d8ccfe)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }



                // Przyklad implementacji Kuchni
                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 1553); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_83884b0c4b5b4e88a2e2f910fdbc06ec_skladniki = Set.of(
                        new Skladnik(Produkt.CUKIER_PUDER, 486),
                        new Skladnik(Produkt.MARGARYNA, 1546),
                        new Skladnik(Produkt.TWAROG, 1110),
                        new Skladnik(Produkt.MAKA_PSZENNA, 1402),
                        new Skladnik(Produkt.RODZYNKI, 1893)
                );
                Przepis przepis_83884b0c4b5b4e88a2e2f910fdbc06ec = () -> przepis_83884b0c4b5b4e88a2e2f910fdbc06ec_skladniki;




                Set<Skladnik> przeliczony_przepis_83884b0c4b5b4e88a2e2f910fdbc06ec = kuchnia.przeliczPrzepis(przepis_83884b0c4b5b4e88a2e2f910fdbc06ec);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_83884b0c4b5b4e88a2e2f910fdbc06ec);


                Przepis nowyPrzepis_przepis_83884b0c4b5b4e88a2e2f910fdbc06ec = () -> przeliczony_przepis_83884b0c4b5b4e88a2e2f910fdbc06ec;

                if (przeliczony_przepis_83884b0c4b5b4e88a2e2f910fdbc06ec.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_83884b0c4b5b4e88a2e2f910fdbc06ec)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_83884b0c4b5b4e88a2e2f910fdbc06ec)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }



                // Przyklad implementacji Kuchni
                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 357); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_7fed5a942e9b4797b08a8850cd8a1adc_skladniki = Set.of(
                        new Skladnik(Produkt.SOL, 2447),
                        new Skladnik(Produkt.MARGARYNA, 2248),
                        new Skladnik(Produkt.PROSZEK_DO_PIECZENIA, 619),
                        new Skladnik(Produkt.MASLO, 1022),
                        new Skladnik(Produkt.MAKA_ZIEMNIACZANA, 1013),
                        new Skladnik(Produkt.CUKIER_WANILIOWY, 501)
                );
                Przepis przepis_7fed5a942e9b4797b08a8850cd8a1adc = () -> przepis_7fed5a942e9b4797b08a8850cd8a1adc_skladniki;




                Set<Skladnik> przeliczony_przepis_7fed5a942e9b4797b08a8850cd8a1adc = kuchnia.przeliczPrzepis(przepis_7fed5a942e9b4797b08a8850cd8a1adc);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_7fed5a942e9b4797b08a8850cd8a1adc);


                Przepis nowyPrzepis_przepis_7fed5a942e9b4797b08a8850cd8a1adc = () -> przeliczony_przepis_7fed5a942e9b4797b08a8850cd8a1adc;

                if (przeliczony_przepis_7fed5a942e9b4797b08a8850cd8a1adc.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_7fed5a942e9b4797b08a8850cd8a1adc)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_7fed5a942e9b4797b08a8850cd8a1adc)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }



                // Przyklad implementacji Kuchni
                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 1450); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_cfc7768190b244d6b354f0e65e5cf3c1_skladniki = Set.of(
                        new Skladnik(Produkt.CUKIER_PUDER, 2595),
                        new Skladnik(Produkt.MLEKO, 550),
                        new Skladnik(Produkt.MARGARYNA, 642),
                        new Skladnik(Produkt.MAKA_RAZOWA, 2094),
                        new Skladnik(Produkt.RODZYNKI, 2771),
                        new Skladnik(Produkt.MAKA_ZIEMNIACZANA, 2925),
                        new Skladnik(Produkt.MASLO, 379),
                        new Skladnik(Produkt.SOL, 1037),
                        new Skladnik(Produkt.CUKIER_WANILIOWY, 701),
                        new Skladnik(Produkt.PROSZEK_DO_PIECZENIA, 107)
                );
                Przepis przepis_cfc7768190b244d6b354f0e65e5cf3c1 = () -> przepis_cfc7768190b244d6b354f0e65e5cf3c1_skladniki;




                Set<Skladnik> przeliczony_przepis_cfc7768190b244d6b354f0e65e5cf3c1 = kuchnia.przeliczPrzepis(przepis_cfc7768190b244d6b354f0e65e5cf3c1);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_cfc7768190b244d6b354f0e65e5cf3c1);


                Przepis nowyPrzepis_przepis_cfc7768190b244d6b354f0e65e5cf3c1 = () -> przeliczony_przepis_cfc7768190b244d6b354f0e65e5cf3c1;

                if (przeliczony_przepis_cfc7768190b244d6b354f0e65e5cf3c1.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_cfc7768190b244d6b354f0e65e5cf3c1)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_cfc7768190b244d6b354f0e65e5cf3c1)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }



                // Przyklad implementacji Kuchni
                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 459); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_da67e00e7fb64421b1a40c7f1fbcfc2b_skladniki = Set.of(
                        new Skladnik(Produkt.MARGARYNA, 2592),
                        new Skladnik(Produkt.MAKA_ZIEMNIACZANA, 1413),
                        new Skladnik(Produkt.MAKA_PSZENNA, 2049),
                        new Skladnik(Produkt.OLEJ_ROSLINNY, 1098),
                        new Skladnik(Produkt.MLEKO, 2785),
                        new Skladnik(Produkt.RODZYNKI, 1639),
                        new Skladnik(Produkt.CUKIER_WANILIOWY, 2807)
                );
                Przepis przepis_da67e00e7fb64421b1a40c7f1fbcfc2b = () -> przepis_da67e00e7fb64421b1a40c7f1fbcfc2b_skladniki;




                Set<Skladnik> przeliczony_przepis_da67e00e7fb64421b1a40c7f1fbcfc2b = kuchnia.przeliczPrzepis(przepis_da67e00e7fb64421b1a40c7f1fbcfc2b);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_da67e00e7fb64421b1a40c7f1fbcfc2b);


                Przepis nowyPrzepis_przepis_da67e00e7fb64421b1a40c7f1fbcfc2b = () -> przeliczony_przepis_da67e00e7fb64421b1a40c7f1fbcfc2b;

                if (przeliczony_przepis_da67e00e7fb64421b1a40c7f1fbcfc2b.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_da67e00e7fb64421b1a40c7f1fbcfc2b)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_da67e00e7fb64421b1a40c7f1fbcfc2b)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }



                // Przyklad implementacji Kuchni
                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 1587); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_09dcfb05987747bc95c43187d6ca3fba_skladniki = Set.of(
                        new Skladnik(Produkt.MAKA_PSZENNA, 1087),
                        new Skladnik(Produkt.WODA, 1756),
                        new Skladnik(Produkt.OLEJ_ROSLINNY, 2522),
                        new Skladnik(Produkt.CUKIER, 2520),
                        new Skladnik(Produkt.MLEKO, 2673),
                        new Skladnik(Produkt.MAKA_ZIEMNIACZANA, 1339),
                        new Skladnik(Produkt.DROZDZE, 403),
                        new Skladnik(Produkt.RODZYNKI, 1),
                        new Skladnik(Produkt.MASLO, 920),
                        new Skladnik(Produkt.CUKIER_PUDER, 134)
                );
                Przepis przepis_09dcfb05987747bc95c43187d6ca3fba = () -> przepis_09dcfb05987747bc95c43187d6ca3fba_skladniki;




                Set<Skladnik> przeliczony_przepis_09dcfb05987747bc95c43187d6ca3fba = kuchnia.przeliczPrzepis(przepis_09dcfb05987747bc95c43187d6ca3fba);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_09dcfb05987747bc95c43187d6ca3fba);


                Przepis nowyPrzepis_przepis_09dcfb05987747bc95c43187d6ca3fba = () -> przeliczony_przepis_09dcfb05987747bc95c43187d6ca3fba;

                if (przeliczony_przepis_09dcfb05987747bc95c43187d6ca3fba.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_09dcfb05987747bc95c43187d6ca3fba)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_09dcfb05987747bc95c43187d6ca3fba)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }



                // Przyklad implementacji Kuchni
                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 1083); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_c89a64f8509f4ab2964e42db3f1c5b79_skladniki = Set.of(
                        new Skladnik(Produkt.CUKIER, 2848),
                        new Skladnik(Produkt.SOL, 138),
                        new Skladnik(Produkt.RODZYNKI, 1932),
                        new Skladnik(Produkt.OLEJ_ROSLINNY, 1761),
                        new Skladnik(Produkt.CUKIER_WANILIOWY, 2377),
                        new Skladnik(Produkt.DROZDZE, 2494),
                        new Skladnik(Produkt.MAKA_PSZENNA, 171),
                        new Skladnik(Produkt.WODA, 1625),
                        new Skladnik(Produkt.MLEKO, 1746),
                        new Skladnik(Produkt.MASLO, 692)
                );
                Przepis przepis_c89a64f8509f4ab2964e42db3f1c5b79 = () -> przepis_c89a64f8509f4ab2964e42db3f1c5b79_skladniki;




                Set<Skladnik> przeliczony_przepis_c89a64f8509f4ab2964e42db3f1c5b79 = kuchnia.przeliczPrzepis(przepis_c89a64f8509f4ab2964e42db3f1c5b79);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_c89a64f8509f4ab2964e42db3f1c5b79);


                Przepis nowyPrzepis_przepis_c89a64f8509f4ab2964e42db3f1c5b79 = () -> przeliczony_przepis_c89a64f8509f4ab2964e42db3f1c5b79;

                if (przeliczony_przepis_c89a64f8509f4ab2964e42db3f1c5b79.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_c89a64f8509f4ab2964e42db3f1c5b79)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_c89a64f8509f4ab2964e42db3f1c5b79)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }



                // Przyklad implementacji Kuchni
                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 1931); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_dc9190b5a17546f7bbc748d9936ca4bf_skladniki = Set.of(
                        new Skladnik(Produkt.MASLO, 200),
                        new Skladnik(Produkt.CUKIER, 1351),
                        new Skladnik(Produkt.MLEKO, 1937),
                        new Skladnik(Produkt.CUKIER_PUDER, 1081),
                        new Skladnik(Produkt.RODZYNKI, 1449),
                        new Skladnik(Produkt.TWAROG, 598),
                        new Skladnik(Produkt.MAKA_ZIEMNIACZANA, 1549)
                );
                Przepis przepis_dc9190b5a17546f7bbc748d9936ca4bf = () -> przepis_dc9190b5a17546f7bbc748d9936ca4bf_skladniki;




                Set<Skladnik> przeliczony_przepis_dc9190b5a17546f7bbc748d9936ca4bf = kuchnia.przeliczPrzepis(przepis_dc9190b5a17546f7bbc748d9936ca4bf);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_dc9190b5a17546f7bbc748d9936ca4bf);


                Przepis nowyPrzepis_przepis_dc9190b5a17546f7bbc748d9936ca4bf = () -> przeliczony_przepis_dc9190b5a17546f7bbc748d9936ca4bf;

                if (przeliczony_przepis_dc9190b5a17546f7bbc748d9936ca4bf.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_dc9190b5a17546f7bbc748d9936ca4bf)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_dc9190b5a17546f7bbc748d9936ca4bf)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }



                // Przyklad implementacji Kuchni
                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 1728); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_f9f818cb5290463580f1fc7d1b046b9f_skladniki = Set.of(
                        new Skladnik(Produkt.CUKIER, 1542),
                        new Skladnik(Produkt.MAKA_RAZOWA, 395),
                        new Skladnik(Produkt.RODZYNKI, 2217),
                        new Skladnik(Produkt.MAKA_ZIEMNIACZANA, 2336),
                        new Skladnik(Produkt.PROSZEK_DO_PIECZENIA, 2335)
                );
                Przepis przepis_f9f818cb5290463580f1fc7d1b046b9f = () -> przepis_f9f818cb5290463580f1fc7d1b046b9f_skladniki;




                Set<Skladnik> przeliczony_przepis_f9f818cb5290463580f1fc7d1b046b9f = kuchnia.przeliczPrzepis(przepis_f9f818cb5290463580f1fc7d1b046b9f);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_f9f818cb5290463580f1fc7d1b046b9f);


                Przepis nowyPrzepis_przepis_f9f818cb5290463580f1fc7d1b046b9f = () -> przeliczony_przepis_f9f818cb5290463580f1fc7d1b046b9f;

                if (przeliczony_przepis_f9f818cb5290463580f1fc7d1b046b9f.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_f9f818cb5290463580f1fc7d1b046b9f)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_f9f818cb5290463580f1fc7d1b046b9f)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }



                // Przyklad implementacji Kuchni
                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 426); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_f497f54175f7490dad8d4ad9d2a93d2f_skladniki = Set.of(
                        new Skladnik(Produkt.MASLO, 197),
                        new Skladnik(Produkt.CUKIER, 1187),
                        new Skladnik(Produkt.PROSZEK_DO_PIECZENIA, 2090),
                        new Skladnik(Produkt.MAKA_RAZOWA, 2213),
                        new Skladnik(Produkt.MLEKO, 1929),
                        new Skladnik(Produkt.MARGARYNA, 1985),
                        new Skladnik(Produkt.OLEJ_ROSLINNY, 1522),
                        new Skladnik(Produkt.TWAROG, 116),
                        new Skladnik(Produkt.RODZYNKI, 2086)
                );
                Przepis przepis_f497f54175f7490dad8d4ad9d2a93d2f = () -> przepis_f497f54175f7490dad8d4ad9d2a93d2f_skladniki;




                Set<Skladnik> przeliczony_przepis_f497f54175f7490dad8d4ad9d2a93d2f = kuchnia.przeliczPrzepis(przepis_f497f54175f7490dad8d4ad9d2a93d2f);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_f497f54175f7490dad8d4ad9d2a93d2f);


                Przepis nowyPrzepis_przepis_f497f54175f7490dad8d4ad9d2a93d2f = () -> przeliczony_przepis_f497f54175f7490dad8d4ad9d2a93d2f;

                if (przeliczony_przepis_f497f54175f7490dad8d4ad9d2a93d2f.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_f497f54175f7490dad8d4ad9d2a93d2f)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_f497f54175f7490dad8d4ad9d2a93d2f)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }



                // Przyklad implementacji Kuchni
                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 614); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_c9617c03830449d1bb5cffa98a723f69_skladniki = Set.of(
                        new Skladnik(Produkt.SOL, 1527),
                        new Skladnik(Produkt.OLEJ_ROSLINNY, 2916),
                        new Skladnik(Produkt.TWAROG, 2355),
                        new Skladnik(Produkt.MAKA_ZIEMNIACZANA, 678),
                        new Skladnik(Produkt.CUKIER_PUDER, 1001),
                        new Skladnik(Produkt.MAKA_RAZOWA, 303),
                        new Skladnik(Produkt.DROZDZE, 355)
                );
                Przepis przepis_c9617c03830449d1bb5cffa98a723f69 = () -> przepis_c9617c03830449d1bb5cffa98a723f69_skladniki;




                Set<Skladnik> przeliczony_przepis_c9617c03830449d1bb5cffa98a723f69 = kuchnia.przeliczPrzepis(przepis_c9617c03830449d1bb5cffa98a723f69);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_c9617c03830449d1bb5cffa98a723f69);


                Przepis nowyPrzepis_przepis_c9617c03830449d1bb5cffa98a723f69 = () -> przeliczony_przepis_c9617c03830449d1bb5cffa98a723f69;

                if (przeliczony_przepis_c9617c03830449d1bb5cffa98a723f69.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_c9617c03830449d1bb5cffa98a723f69)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_c9617c03830449d1bb5cffa98a723f69)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }



                // Przyklad implementacji Kuchni
                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 660); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_ab5c65f30a854853b1761d5f93a656a3_skladniki = Set.of(
                        new Skladnik(Produkt.OLEJ_ROSLINNY, 143),
                        new Skladnik(Produkt.CUKIER_WANILIOWY, 715),
                        new Skladnik(Produkt.TWAROG, 2550),
                        new Skladnik(Produkt.SOL, 1774),
                        new Skladnik(Produkt.MLEKO, 2807),
                        new Skladnik(Produkt.CUKIER_PUDER, 2034),
                        new Skladnik(Produkt.WODA, 1178),
                        new Skladnik(Produkt.MAKA_PSZENNA, 244)
                );
                Przepis przepis_ab5c65f30a854853b1761d5f93a656a3 = () -> przepis_ab5c65f30a854853b1761d5f93a656a3_skladniki;




                Set<Skladnik> przeliczony_przepis_ab5c65f30a854853b1761d5f93a656a3 = kuchnia.przeliczPrzepis(przepis_ab5c65f30a854853b1761d5f93a656a3);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_ab5c65f30a854853b1761d5f93a656a3);


                Przepis nowyPrzepis_przepis_ab5c65f30a854853b1761d5f93a656a3 = () -> przeliczony_przepis_ab5c65f30a854853b1761d5f93a656a3;

                if (przeliczony_przepis_ab5c65f30a854853b1761d5f93a656a3.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_ab5c65f30a854853b1761d5f93a656a3)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_ab5c65f30a854853b1761d5f93a656a3)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }



                // Przyklad implementacji Kuchni
                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 1990); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_af59b5cce67248819d2b8aef42b345cc_skladniki = Set.of(
                        new Skladnik(Produkt.OLEJ_ROSLINNY, 112),
                        new Skladnik(Produkt.TWAROG, 871),
                        new Skladnik(Produkt.DROZDZE, 1993),
                        new Skladnik(Produkt.WODA, 1731),
                        new Skladnik(Produkt.CUKIER, 1545),
                        new Skladnik(Produkt.MAKA_RAZOWA, 2074)
                );
                Przepis przepis_af59b5cce67248819d2b8aef42b345cc = () -> przepis_af59b5cce67248819d2b8aef42b345cc_skladniki;




                Set<Skladnik> przeliczony_przepis_af59b5cce67248819d2b8aef42b345cc = kuchnia.przeliczPrzepis(przepis_af59b5cce67248819d2b8aef42b345cc);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_af59b5cce67248819d2b8aef42b345cc);


                Przepis nowyPrzepis_przepis_af59b5cce67248819d2b8aef42b345cc = () -> przeliczony_przepis_af59b5cce67248819d2b8aef42b345cc;

                if (przeliczony_przepis_af59b5cce67248819d2b8aef42b345cc.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_af59b5cce67248819d2b8aef42b345cc)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_af59b5cce67248819d2b8aef42b345cc)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }



                // Przyklad implementacji Kuchni
                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 77); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_5949c971675f49439584314ae4243d22_skladniki = Set.of(
                        new Skladnik(Produkt.TWAROG, 909),
                        new Skladnik(Produkt.MAKA_RAZOWA, 2466),
                        new Skladnik(Produkt.CUKIER_PUDER, 2410),
                        new Skladnik(Produkt.PROSZEK_DO_PIECZENIA, 1616),
                        new Skladnik(Produkt.MLEKO, 664)
                );
                Przepis przepis_5949c971675f49439584314ae4243d22 = () -> przepis_5949c971675f49439584314ae4243d22_skladniki;




                Set<Skladnik> przeliczony_przepis_5949c971675f49439584314ae4243d22 = kuchnia.przeliczPrzepis(przepis_5949c971675f49439584314ae4243d22);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_5949c971675f49439584314ae4243d22);


                Przepis nowyPrzepis_przepis_5949c971675f49439584314ae4243d22 = () -> przeliczony_przepis_5949c971675f49439584314ae4243d22;

                if (przeliczony_przepis_5949c971675f49439584314ae4243d22.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_5949c971675f49439584314ae4243d22)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_5949c971675f49439584314ae4243d22)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }



                // Przyklad implementacji Kuchni
                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 1757); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_47a6baf75e1e434d8305880a36b529b1_skladniki = Set.of(
                        new Skladnik(Produkt.MASLO, 1064),
                        new Skladnik(Produkt.RODZYNKI, 2123),
                        new Skladnik(Produkt.WODA, 1027),
                        new Skladnik(Produkt.MAKA_PSZENNA, 1787),
                        new Skladnik(Produkt.MARGARYNA, 2484),
                        new Skladnik(Produkt.MAKA_ZIEMNIACZANA, 1631),
                        new Skladnik(Produkt.CUKIER, 80),
                        new Skladnik(Produkt.MLEKO, 940),
                        new Skladnik(Produkt.CUKIER_WANILIOWY, 857),
                        new Skladnik(Produkt.OLEJ_ROSLINNY, 1720)
                );
                Przepis przepis_47a6baf75e1e434d8305880a36b529b1 = () -> przepis_47a6baf75e1e434d8305880a36b529b1_skladniki;




                Set<Skladnik> przeliczony_przepis_47a6baf75e1e434d8305880a36b529b1 = kuchnia.przeliczPrzepis(przepis_47a6baf75e1e434d8305880a36b529b1);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_47a6baf75e1e434d8305880a36b529b1);


                Przepis nowyPrzepis_przepis_47a6baf75e1e434d8305880a36b529b1 = () -> przeliczony_przepis_47a6baf75e1e434d8305880a36b529b1;

                if (przeliczony_przepis_47a6baf75e1e434d8305880a36b529b1.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_47a6baf75e1e434d8305880a36b529b1)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_47a6baf75e1e434d8305880a36b529b1)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }



                // Przyklad implementacji Kuchni
                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 864); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_d36907d570f046d4aa7134ac9f58b0e3_skladniki = Set.of(
                        new Skladnik(Produkt.MAKA_RAZOWA, 2684),
                        new Skladnik(Produkt.CUKIER, 1758),
                        new Skladnik(Produkt.MARGARYNA, 2672),
                        new Skladnik(Produkt.PROSZEK_DO_PIECZENIA, 1019),
                        new Skladnik(Produkt.MASLO, 1032),
                        new Skladnik(Produkt.OLEJ_ROSLINNY, 2194),
                        new Skladnik(Produkt.CUKIER_PUDER, 2323),
                        new Skladnik(Produkt.MAKA_ZIEMNIACZANA, 2248)
                );
                Przepis przepis_d36907d570f046d4aa7134ac9f58b0e3 = () -> przepis_d36907d570f046d4aa7134ac9f58b0e3_skladniki;




                Set<Skladnik> przeliczony_przepis_d36907d570f046d4aa7134ac9f58b0e3 = kuchnia.przeliczPrzepis(przepis_d36907d570f046d4aa7134ac9f58b0e3);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_d36907d570f046d4aa7134ac9f58b0e3);


                Przepis nowyPrzepis_przepis_d36907d570f046d4aa7134ac9f58b0e3 = () -> przeliczony_przepis_d36907d570f046d4aa7134ac9f58b0e3;

                if (przeliczony_przepis_d36907d570f046d4aa7134ac9f58b0e3.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_d36907d570f046d4aa7134ac9f58b0e3)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_d36907d570f046d4aa7134ac9f58b0e3)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }



                // Przyklad implementacji Kuchni
                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 1429); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_ff8338fff0664315aaa5afb98db903c4_skladniki = Set.of(
                        new Skladnik(Produkt.MAKA_ZIEMNIACZANA, 761),
                        new Skladnik(Produkt.MARGARYNA, 2185),
                        new Skladnik(Produkt.TWAROG, 2303),
                        new Skladnik(Produkt.RODZYNKI, 2942),
                        new Skladnik(Produkt.PROSZEK_DO_PIECZENIA, 480),
                        new Skladnik(Produkt.CUKIER, 2182),
                        new Skladnik(Produkt.MAKA_PSZENNA, 244),
                        new Skladnik(Produkt.SOL, 853)
                );
                Przepis przepis_ff8338fff0664315aaa5afb98db903c4 = () -> przepis_ff8338fff0664315aaa5afb98db903c4_skladniki;




                Set<Skladnik> przeliczony_przepis_ff8338fff0664315aaa5afb98db903c4 = kuchnia.przeliczPrzepis(przepis_ff8338fff0664315aaa5afb98db903c4);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_ff8338fff0664315aaa5afb98db903c4);


                Przepis nowyPrzepis_przepis_ff8338fff0664315aaa5afb98db903c4 = () -> przeliczony_przepis_ff8338fff0664315aaa5afb98db903c4;

                if (przeliczony_przepis_ff8338fff0664315aaa5afb98db903c4.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_ff8338fff0664315aaa5afb98db903c4)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_ff8338fff0664315aaa5afb98db903c4)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }



                // Przyklad implementacji Kuchni
                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 1920); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_ad50b3f37aa44ffb8abf5d22645d05f6_skladniki = Set.of(
                        new Skladnik(Produkt.DROZDZE, 1141),
                        new Skladnik(Produkt.MAKA_ZIEMNIACZANA, 1212),
                        new Skladnik(Produkt.WODA, 1648),
                        new Skladnik(Produkt.MASLO, 359),
                        new Skladnik(Produkt.TWAROG, 1098),
                        new Skladnik(Produkt.CUKIER, 2041),
                        new Skladnik(Produkt.PROSZEK_DO_PIECZENIA, 1275),
                        new Skladnik(Produkt.OLEJ_ROSLINNY, 2735),
                        new Skladnik(Produkt.SOL, 2014),
                        new Skladnik(Produkt.RODZYNKI, 1880)
                );
                Przepis przepis_ad50b3f37aa44ffb8abf5d22645d05f6 = () -> przepis_ad50b3f37aa44ffb8abf5d22645d05f6_skladniki;




                Set<Skladnik> przeliczony_przepis_ad50b3f37aa44ffb8abf5d22645d05f6 = kuchnia.przeliczPrzepis(przepis_ad50b3f37aa44ffb8abf5d22645d05f6);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_ad50b3f37aa44ffb8abf5d22645d05f6);


                Przepis nowyPrzepis_przepis_ad50b3f37aa44ffb8abf5d22645d05f6 = () -> przeliczony_przepis_ad50b3f37aa44ffb8abf5d22645d05f6;

                if (przeliczony_przepis_ad50b3f37aa44ffb8abf5d22645d05f6.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_ad50b3f37aa44ffb8abf5d22645d05f6)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_ad50b3f37aa44ffb8abf5d22645d05f6)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }



                // Przyklad implementacji Kuchni
                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 1684); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_8222bbef92da443da8df544231b907e5_skladniki = Set.of(
                        new Skladnik(Produkt.TWAROG, 1259),
                        new Skladnik(Produkt.PROSZEK_DO_PIECZENIA, 2202),
                        new Skladnik(Produkt.MLEKO, 143),
                        new Skladnik(Produkt.MAKA_RAZOWA, 650),
                        new Skladnik(Produkt.MARGARYNA, 2771)
                );
                Przepis przepis_8222bbef92da443da8df544231b907e5 = () -> przepis_8222bbef92da443da8df544231b907e5_skladniki;




                Set<Skladnik> przeliczony_przepis_8222bbef92da443da8df544231b907e5 = kuchnia.przeliczPrzepis(przepis_8222bbef92da443da8df544231b907e5);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_8222bbef92da443da8df544231b907e5);


                Przepis nowyPrzepis_przepis_8222bbef92da443da8df544231b907e5 = () -> przeliczony_przepis_8222bbef92da443da8df544231b907e5;

                if (przeliczony_przepis_8222bbef92da443da8df544231b907e5.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_8222bbef92da443da8df544231b907e5)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_8222bbef92da443da8df544231b907e5)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }



                // Przyklad implementacji Kuchni
                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 1849); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_1eeaaed3fc5f46939e54961f13a24bc3_skladniki = Set.of(
                        new Skladnik(Produkt.CUKIER_PUDER, 2052),
                        new Skladnik(Produkt.WODA, 1919),
                        new Skladnik(Produkt.OLEJ_ROSLINNY, 2406),
                        new Skladnik(Produkt.MASLO, 163),
                        new Skladnik(Produkt.TWAROG, 793)
                );
                Przepis przepis_1eeaaed3fc5f46939e54961f13a24bc3 = () -> przepis_1eeaaed3fc5f46939e54961f13a24bc3_skladniki;




                Set<Skladnik> przeliczony_przepis_1eeaaed3fc5f46939e54961f13a24bc3 = kuchnia.przeliczPrzepis(przepis_1eeaaed3fc5f46939e54961f13a24bc3);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_1eeaaed3fc5f46939e54961f13a24bc3);


                Przepis nowyPrzepis_przepis_1eeaaed3fc5f46939e54961f13a24bc3 = () -> przeliczony_przepis_1eeaaed3fc5f46939e54961f13a24bc3;

                if (przeliczony_przepis_1eeaaed3fc5f46939e54961f13a24bc3.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_1eeaaed3fc5f46939e54961f13a24bc3)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_1eeaaed3fc5f46939e54961f13a24bc3)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }



                // Przyklad implementacji Kuchni
                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 1404); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_48533f9d74c644caa8f086380d521fc4_skladniki = Set.of(
                        new Skladnik(Produkt.WODA, 466),
                        new Skladnik(Produkt.MAKA_RAZOWA, 1483),
                        new Skladnik(Produkt.CUKIER_PUDER, 546),
                        new Skladnik(Produkt.CUKIER, 2186),
                        new Skladnik(Produkt.SOL, 2572),
                        new Skladnik(Produkt.OLEJ_ROSLINNY, 2193),
                        new Skladnik(Produkt.MLEKO, 912),
                        new Skladnik(Produkt.PROSZEK_DO_PIECZENIA, 658)
                );
                Przepis przepis_48533f9d74c644caa8f086380d521fc4 = () -> przepis_48533f9d74c644caa8f086380d521fc4_skladniki;




                Set<Skladnik> przeliczony_przepis_48533f9d74c644caa8f086380d521fc4 = kuchnia.przeliczPrzepis(przepis_48533f9d74c644caa8f086380d521fc4);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_48533f9d74c644caa8f086380d521fc4);


                Przepis nowyPrzepis_przepis_48533f9d74c644caa8f086380d521fc4 = () -> przeliczony_przepis_48533f9d74c644caa8f086380d521fc4;

                if (przeliczony_przepis_48533f9d74c644caa8f086380d521fc4.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_48533f9d74c644caa8f086380d521fc4)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_48533f9d74c644caa8f086380d521fc4)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }



                // Przyklad implementacji Kuchni
                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 244); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_56dc4e46c95145b38dc95a40d2a15a98_skladniki = Set.of(
                        new Skladnik(Produkt.CUKIER_WANILIOWY, 1236),
                        new Skladnik(Produkt.MLEKO, 2351),
                        new Skladnik(Produkt.CUKIER_PUDER, 1435),
                        new Skladnik(Produkt.MAKA_ZIEMNIACZANA, 683),
                        new Skladnik(Produkt.MAKA_RAZOWA, 1188)
                );
                Przepis przepis_56dc4e46c95145b38dc95a40d2a15a98 = () -> przepis_56dc4e46c95145b38dc95a40d2a15a98_skladniki;




                Set<Skladnik> przeliczony_przepis_56dc4e46c95145b38dc95a40d2a15a98 = kuchnia.przeliczPrzepis(przepis_56dc4e46c95145b38dc95a40d2a15a98);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_56dc4e46c95145b38dc95a40d2a15a98);


                Przepis nowyPrzepis_przepis_56dc4e46c95145b38dc95a40d2a15a98 = () -> przeliczony_przepis_56dc4e46c95145b38dc95a40d2a15a98;

                if (przeliczony_przepis_56dc4e46c95145b38dc95a40d2a15a98.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_56dc4e46c95145b38dc95a40d2a15a98)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_56dc4e46c95145b38dc95a40d2a15a98)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }



                // Przyklad implementacji Kuchni
                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 1041); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_549824e5444545f09628bd7a99de2c9d_skladniki = Set.of(
                        new Skladnik(Produkt.PROSZEK_DO_PIECZENIA, 2133),
                        new Skladnik(Produkt.MAKA_ZIEMNIACZANA, 518),
                        new Skladnik(Produkt.WODA, 940),
                        new Skladnik(Produkt.MLEKO, 2064),
                        new Skladnik(Produkt.MARGARYNA, 2747),
                        new Skladnik(Produkt.RODZYNKI, 2139),
                        new Skladnik(Produkt.SOL, 2346)
                );
                Przepis przepis_549824e5444545f09628bd7a99de2c9d = () -> przepis_549824e5444545f09628bd7a99de2c9d_skladniki;




                Set<Skladnik> przeliczony_przepis_549824e5444545f09628bd7a99de2c9d = kuchnia.przeliczPrzepis(przepis_549824e5444545f09628bd7a99de2c9d);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_549824e5444545f09628bd7a99de2c9d);


                Przepis nowyPrzepis_przepis_549824e5444545f09628bd7a99de2c9d = () -> przeliczony_przepis_549824e5444545f09628bd7a99de2c9d;

                if (przeliczony_przepis_549824e5444545f09628bd7a99de2c9d.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_549824e5444545f09628bd7a99de2c9d)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_549824e5444545f09628bd7a99de2c9d)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }



                // Przyklad implementacji Kuchni
                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 1755); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_f7f39e922a0944c58a6f0ed82ad16c1d_skladniki = Set.of(
                        new Skladnik(Produkt.MASLO, 1349),
                        new Skladnik(Produkt.TWAROG, 2638),
                        new Skladnik(Produkt.RODZYNKI, 1060),
                        new Skladnik(Produkt.MAKA_ZIEMNIACZANA, 274),
                        new Skladnik(Produkt.WODA, 1139),
                        new Skladnik(Produkt.SOL, 1449)
                );
                Przepis przepis_f7f39e922a0944c58a6f0ed82ad16c1d = () -> przepis_f7f39e922a0944c58a6f0ed82ad16c1d_skladniki;




                Set<Skladnik> przeliczony_przepis_f7f39e922a0944c58a6f0ed82ad16c1d = kuchnia.przeliczPrzepis(przepis_f7f39e922a0944c58a6f0ed82ad16c1d);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_f7f39e922a0944c58a6f0ed82ad16c1d);


                Przepis nowyPrzepis_przepis_f7f39e922a0944c58a6f0ed82ad16c1d = () -> przeliczony_przepis_f7f39e922a0944c58a6f0ed82ad16c1d;

                if (przeliczony_przepis_f7f39e922a0944c58a6f0ed82ad16c1d.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_f7f39e922a0944c58a6f0ed82ad16c1d)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_f7f39e922a0944c58a6f0ed82ad16c1d)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }



                // Przyklad implementacji Kuchni
                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 5); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_53dc1ab10f9a4db3afb27445e6b4ad2c_skladniki = Set.of(
                        new Skladnik(Produkt.MAKA_RAZOWA, 853),
                        new Skladnik(Produkt.MAKA_ZIEMNIACZANA, 914),
                        new Skladnik(Produkt.MLEKO, 2491),
                        new Skladnik(Produkt.TWAROG, 1986),
                        new Skladnik(Produkt.CUKIER_PUDER, 2925),
                        new Skladnik(Produkt.RODZYNKI, 1781),
                        new Skladnik(Produkt.PROSZEK_DO_PIECZENIA, 214),
                        new Skladnik(Produkt.WODA, 2556),
                        new Skladnik(Produkt.MAKA_PSZENNA, 202),
                        new Skladnik(Produkt.MASLO, 1012)
                );
                Przepis przepis_53dc1ab10f9a4db3afb27445e6b4ad2c = () -> przepis_53dc1ab10f9a4db3afb27445e6b4ad2c_skladniki;




                Set<Skladnik> przeliczony_przepis_53dc1ab10f9a4db3afb27445e6b4ad2c = kuchnia.przeliczPrzepis(przepis_53dc1ab10f9a4db3afb27445e6b4ad2c);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_53dc1ab10f9a4db3afb27445e6b4ad2c);


                Przepis nowyPrzepis_przepis_53dc1ab10f9a4db3afb27445e6b4ad2c = () -> przeliczony_przepis_53dc1ab10f9a4db3afb27445e6b4ad2c;

                if (przeliczony_przepis_53dc1ab10f9a4db3afb27445e6b4ad2c.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_53dc1ab10f9a4db3afb27445e6b4ad2c)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_53dc1ab10f9a4db3afb27445e6b4ad2c)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }



                // Przyklad implementacji Kuchni
                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 1656); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_5d5f6eaaf0b64489beefe1e752a85b03_skladniki = Set.of(
                        new Skladnik(Produkt.RODZYNKI, 2368),
                        new Skladnik(Produkt.MAKA_RAZOWA, 645),
                        new Skladnik(Produkt.CUKIER, 1239),
                        new Skladnik(Produkt.MAKA_PSZENNA, 2371),
                        new Skladnik(Produkt.PROSZEK_DO_PIECZENIA, 670),
                        new Skladnik(Produkt.OLEJ_ROSLINNY, 1349)
                );
                Przepis przepis_5d5f6eaaf0b64489beefe1e752a85b03 = () -> przepis_5d5f6eaaf0b64489beefe1e752a85b03_skladniki;




                Set<Skladnik> przeliczony_przepis_5d5f6eaaf0b64489beefe1e752a85b03 = kuchnia.przeliczPrzepis(przepis_5d5f6eaaf0b64489beefe1e752a85b03);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_5d5f6eaaf0b64489beefe1e752a85b03);


                Przepis nowyPrzepis_przepis_5d5f6eaaf0b64489beefe1e752a85b03 = () -> przeliczony_przepis_5d5f6eaaf0b64489beefe1e752a85b03;

                if (przeliczony_przepis_5d5f6eaaf0b64489beefe1e752a85b03.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_5d5f6eaaf0b64489beefe1e752a85b03)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_5d5f6eaaf0b64489beefe1e752a85b03)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }



                // Przyklad implementacji Kuchni
                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 265); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_650222905f08421cbd42e47c1f6da96b_skladniki = Set.of(
                        new Skladnik(Produkt.MARGARYNA, 1083),
                        new Skladnik(Produkt.PROSZEK_DO_PIECZENIA, 1887),
                        new Skladnik(Produkt.MAKA_PSZENNA, 2575),
                        new Skladnik(Produkt.MLEKO, 1319),
                        new Skladnik(Produkt.RODZYNKI, 2570)
                );
                Przepis przepis_650222905f08421cbd42e47c1f6da96b = () -> przepis_650222905f08421cbd42e47c1f6da96b_skladniki;




                Set<Skladnik> przeliczony_przepis_650222905f08421cbd42e47c1f6da96b = kuchnia.przeliczPrzepis(przepis_650222905f08421cbd42e47c1f6da96b);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_650222905f08421cbd42e47c1f6da96b);


                Przepis nowyPrzepis_przepis_650222905f08421cbd42e47c1f6da96b = () -> przeliczony_przepis_650222905f08421cbd42e47c1f6da96b;

                if (przeliczony_przepis_650222905f08421cbd42e47c1f6da96b.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_650222905f08421cbd42e47c1f6da96b)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_650222905f08421cbd42e47c1f6da96b)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }



                // Przyklad implementacji Kuchni
                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 1517); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_245bacf399a54a8483c6011de297dfff_skladniki = Set.of(
                        new Skladnik(Produkt.MAKA_PSZENNA, 143),
                        new Skladnik(Produkt.OLEJ_ROSLINNY, 1324),
                        new Skladnik(Produkt.WODA, 2404),
                        new Skladnik(Produkt.SOL, 327),
                        new Skladnik(Produkt.CUKIER_WANILIOWY, 1756),
                        new Skladnik(Produkt.TWAROG, 1777),
                        new Skladnik(Produkt.MAKA_RAZOWA, 932),
                        new Skladnik(Produkt.MLEKO, 1115),
                        new Skladnik(Produkt.PROSZEK_DO_PIECZENIA, 1844),
                        new Skladnik(Produkt.MASLO, 2833)
                );
                Przepis przepis_245bacf399a54a8483c6011de297dfff = () -> przepis_245bacf399a54a8483c6011de297dfff_skladniki;




                Set<Skladnik> przeliczony_przepis_245bacf399a54a8483c6011de297dfff = kuchnia.przeliczPrzepis(przepis_245bacf399a54a8483c6011de297dfff);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_245bacf399a54a8483c6011de297dfff);


                Przepis nowyPrzepis_przepis_245bacf399a54a8483c6011de297dfff = () -> przeliczony_przepis_245bacf399a54a8483c6011de297dfff;

                if (przeliczony_przepis_245bacf399a54a8483c6011de297dfff.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_245bacf399a54a8483c6011de297dfff)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_245bacf399a54a8483c6011de297dfff)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }



                // Przyklad implementacji Kuchni
                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 1464); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_d126bd72f9e04b61a7d60f82f407c1a3_skladniki = Set.of(
                        new Skladnik(Produkt.CUKIER, 582),
                        new Skladnik(Produkt.RODZYNKI, 991),
                        new Skladnik(Produkt.PROSZEK_DO_PIECZENIA, 594),
                        new Skladnik(Produkt.TWAROG, 892),
                        new Skladnik(Produkt.MAKA_RAZOWA, 2629),
                        new Skladnik(Produkt.OLEJ_ROSLINNY, 2469),
                        new Skladnik(Produkt.MAKA_ZIEMNIACZANA, 862),
                        new Skladnik(Produkt.MARGARYNA, 1107),
                        new Skladnik(Produkt.SOL, 375),
                        new Skladnik(Produkt.MLEKO, 2576)
                );
                Przepis przepis_d126bd72f9e04b61a7d60f82f407c1a3 = () -> przepis_d126bd72f9e04b61a7d60f82f407c1a3_skladniki;




                Set<Skladnik> przeliczony_przepis_d126bd72f9e04b61a7d60f82f407c1a3 = kuchnia.przeliczPrzepis(przepis_d126bd72f9e04b61a7d60f82f407c1a3);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_d126bd72f9e04b61a7d60f82f407c1a3);


                Przepis nowyPrzepis_przepis_d126bd72f9e04b61a7d60f82f407c1a3 = () -> przeliczony_przepis_d126bd72f9e04b61a7d60f82f407c1a3;

                if (przeliczony_przepis_d126bd72f9e04b61a7d60f82f407c1a3.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_d126bd72f9e04b61a7d60f82f407c1a3)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_d126bd72f9e04b61a7d60f82f407c1a3)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }



                // Przyklad implementacji Kuchni
                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 1272); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_bd8d681f4ec14f78bede0a15fb9fd1d7_skladniki = Set.of(
                        new Skladnik(Produkt.CUKIER_PUDER, 1198),
                        new Skladnik(Produkt.CUKIER_WANILIOWY, 2831),
                        new Skladnik(Produkt.MARGARYNA, 1051),
                        new Skladnik(Produkt.WODA, 2641),
                        new Skladnik(Produkt.MAKA_RAZOWA, 2550)
                );
                Przepis przepis_bd8d681f4ec14f78bede0a15fb9fd1d7 = () -> przepis_bd8d681f4ec14f78bede0a15fb9fd1d7_skladniki;




                Set<Skladnik> przeliczony_przepis_bd8d681f4ec14f78bede0a15fb9fd1d7 = kuchnia.przeliczPrzepis(przepis_bd8d681f4ec14f78bede0a15fb9fd1d7);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_bd8d681f4ec14f78bede0a15fb9fd1d7);


                Przepis nowyPrzepis_przepis_bd8d681f4ec14f78bede0a15fb9fd1d7 = () -> przeliczony_przepis_bd8d681f4ec14f78bede0a15fb9fd1d7;

                if (przeliczony_przepis_bd8d681f4ec14f78bede0a15fb9fd1d7.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_bd8d681f4ec14f78bede0a15fb9fd1d7)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_bd8d681f4ec14f78bede0a15fb9fd1d7)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }



                // Przyklad implementacji Kuchni
                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 475); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_6c1248acfdd94ee597578ce3d44e3d07_skladniki = Set.of(
                        new Skladnik(Produkt.PROSZEK_DO_PIECZENIA, 2780),
                        new Skladnik(Produkt.OLEJ_ROSLINNY, 1536),
                        new Skladnik(Produkt.SOL, 2707),
                        new Skladnik(Produkt.RODZYNKI, 1259),
                        new Skladnik(Produkt.DROZDZE, 1562),
                        new Skladnik(Produkt.TWAROG, 2576),
                        new Skladnik(Produkt.MASLO, 638)
                );
                Przepis przepis_6c1248acfdd94ee597578ce3d44e3d07 = () -> przepis_6c1248acfdd94ee597578ce3d44e3d07_skladniki;




                Set<Skladnik> przeliczony_przepis_6c1248acfdd94ee597578ce3d44e3d07 = kuchnia.przeliczPrzepis(przepis_6c1248acfdd94ee597578ce3d44e3d07);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_6c1248acfdd94ee597578ce3d44e3d07);


                Przepis nowyPrzepis_przepis_6c1248acfdd94ee597578ce3d44e3d07 = () -> przeliczony_przepis_6c1248acfdd94ee597578ce3d44e3d07;

                if (przeliczony_przepis_6c1248acfdd94ee597578ce3d44e3d07.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_6c1248acfdd94ee597578ce3d44e3d07)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_6c1248acfdd94ee597578ce3d44e3d07)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }



                // Przyklad implementacji Kuchni
                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 514); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_0f601f53325c42da8e8503c3ad80ca76_skladniki = Set.of(
                        new Skladnik(Produkt.MASLO, 1924),
                        new Skladnik(Produkt.MARGARYNA, 1422),
                        new Skladnik(Produkt.MLEKO, 153),
                        new Skladnik(Produkt.WODA, 1726),
                        new Skladnik(Produkt.PROSZEK_DO_PIECZENIA, 2154),
                        new Skladnik(Produkt.CUKIER_WANILIOWY, 824),
                        new Skladnik(Produkt.TWAROG, 1431),
                        new Skladnik(Produkt.MAKA_ZIEMNIACZANA, 2015),
                        new Skladnik(Produkt.MAKA_RAZOWA, 1888),
                        new Skladnik(Produkt.CUKIER, 1910)
                );
                Przepis przepis_0f601f53325c42da8e8503c3ad80ca76 = () -> przepis_0f601f53325c42da8e8503c3ad80ca76_skladniki;




                Set<Skladnik> przeliczony_przepis_0f601f53325c42da8e8503c3ad80ca76 = kuchnia.przeliczPrzepis(przepis_0f601f53325c42da8e8503c3ad80ca76);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_0f601f53325c42da8e8503c3ad80ca76);


                Przepis nowyPrzepis_przepis_0f601f53325c42da8e8503c3ad80ca76 = () -> przeliczony_przepis_0f601f53325c42da8e8503c3ad80ca76;

                if (przeliczony_przepis_0f601f53325c42da8e8503c3ad80ca76.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_0f601f53325c42da8e8503c3ad80ca76)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_0f601f53325c42da8e8503c3ad80ca76)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }



                // Przyklad implementacji Kuchni
                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 1970); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_a1b4feece346470ba9e8b3550e5ad342_skladniki = Set.of(
                        new Skladnik(Produkt.DROZDZE, 682),
                        new Skladnik(Produkt.MAKA_PSZENNA, 206),
                        new Skladnik(Produkt.MASLO, 1642),
                        new Skladnik(Produkt.CUKIER, 292),
                        new Skladnik(Produkt.CUKIER_PUDER, 2864),
                        new Skladnik(Produkt.WODA, 958),
                        new Skladnik(Produkt.MAKA_RAZOWA, 878),
                        new Skladnik(Produkt.RODZYNKI, 1552),
                        new Skladnik(Produkt.TWAROG, 851)
                );
                Przepis przepis_a1b4feece346470ba9e8b3550e5ad342 = () -> przepis_a1b4feece346470ba9e8b3550e5ad342_skladniki;




                Set<Skladnik> przeliczony_przepis_a1b4feece346470ba9e8b3550e5ad342 = kuchnia.przeliczPrzepis(przepis_a1b4feece346470ba9e8b3550e5ad342);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_a1b4feece346470ba9e8b3550e5ad342);


                Przepis nowyPrzepis_przepis_a1b4feece346470ba9e8b3550e5ad342 = () -> przeliczony_przepis_a1b4feece346470ba9e8b3550e5ad342;

                if (przeliczony_przepis_a1b4feece346470ba9e8b3550e5ad342.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_a1b4feece346470ba9e8b3550e5ad342)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_a1b4feece346470ba9e8b3550e5ad342)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }



                // Przyklad implementacji Kuchni
                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 443); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_c0b53bc8e23c44d1bb3c373fbc3ac209_skladniki = Set.of(
                        new Skladnik(Produkt.PROSZEK_DO_PIECZENIA, 2172),
                        new Skladnik(Produkt.MASLO, 2873),
                        new Skladnik(Produkt.MAKA_PSZENNA, 357),
                        new Skladnik(Produkt.MAKA_ZIEMNIACZANA, 1068),
                        new Skladnik(Produkt.TWAROG, 1740),
                        new Skladnik(Produkt.RODZYNKI, 2761),
                        new Skladnik(Produkt.MAKA_RAZOWA, 2718)
                );
                Przepis przepis_c0b53bc8e23c44d1bb3c373fbc3ac209 = () -> przepis_c0b53bc8e23c44d1bb3c373fbc3ac209_skladniki;




                Set<Skladnik> przeliczony_przepis_c0b53bc8e23c44d1bb3c373fbc3ac209 = kuchnia.przeliczPrzepis(przepis_c0b53bc8e23c44d1bb3c373fbc3ac209);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_c0b53bc8e23c44d1bb3c373fbc3ac209);


                Przepis nowyPrzepis_przepis_c0b53bc8e23c44d1bb3c373fbc3ac209 = () -> przeliczony_przepis_c0b53bc8e23c44d1bb3c373fbc3ac209;

                if (przeliczony_przepis_c0b53bc8e23c44d1bb3c373fbc3ac209.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_c0b53bc8e23c44d1bb3c373fbc3ac209)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_c0b53bc8e23c44d1bb3c373fbc3ac209)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }



                // Przyklad implementacji Kuchni
                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 1431); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_581840061cc54c3c8520e1ec28b90235_skladniki = Set.of(
                        new Skladnik(Produkt.SOL, 2630),
                        new Skladnik(Produkt.MAKA_PSZENNA, 620),
                        new Skladnik(Produkt.WODA, 1759),
                        new Skladnik(Produkt.MASLO, 492),
                        new Skladnik(Produkt.MAKA_ZIEMNIACZANA, 2992),
                        new Skladnik(Produkt.PROSZEK_DO_PIECZENIA, 2942),
                        new Skladnik(Produkt.MARGARYNA, 172),
                        new Skladnik(Produkt.DROZDZE, 1834)
                );
                Przepis przepis_581840061cc54c3c8520e1ec28b90235 = () -> przepis_581840061cc54c3c8520e1ec28b90235_skladniki;




                Set<Skladnik> przeliczony_przepis_581840061cc54c3c8520e1ec28b90235 = kuchnia.przeliczPrzepis(przepis_581840061cc54c3c8520e1ec28b90235);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_581840061cc54c3c8520e1ec28b90235);


                Przepis nowyPrzepis_przepis_581840061cc54c3c8520e1ec28b90235 = () -> przeliczony_przepis_581840061cc54c3c8520e1ec28b90235;

                if (przeliczony_przepis_581840061cc54c3c8520e1ec28b90235.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_581840061cc54c3c8520e1ec28b90235)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_581840061cc54c3c8520e1ec28b90235)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }



                // Przyklad implementacji Kuchni
                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 183); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_7cc277ad16534cbdafe69f0c1b0a8d8a_skladniki = Set.of(
                        new Skladnik(Produkt.MAKA_ZIEMNIACZANA, 1527),
                        new Skladnik(Produkt.CUKIER_WANILIOWY, 2048),
                        new Skladnik(Produkt.CUKIER_PUDER, 125),
                        new Skladnik(Produkt.MARGARYNA, 1763),
                        new Skladnik(Produkt.OLEJ_ROSLINNY, 2135),
                        new Skladnik(Produkt.PROSZEK_DO_PIECZENIA, 1401),
                        new Skladnik(Produkt.DROZDZE, 2125),
                        new Skladnik(Produkt.SOL, 983)
                );
                Przepis przepis_7cc277ad16534cbdafe69f0c1b0a8d8a = () -> przepis_7cc277ad16534cbdafe69f0c1b0a8d8a_skladniki;




                Set<Skladnik> przeliczony_przepis_7cc277ad16534cbdafe69f0c1b0a8d8a = kuchnia.przeliczPrzepis(przepis_7cc277ad16534cbdafe69f0c1b0a8d8a);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_7cc277ad16534cbdafe69f0c1b0a8d8a);


                Przepis nowyPrzepis_przepis_7cc277ad16534cbdafe69f0c1b0a8d8a = () -> przeliczony_przepis_7cc277ad16534cbdafe69f0c1b0a8d8a;

                if (przeliczony_przepis_7cc277ad16534cbdafe69f0c1b0a8d8a.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_7cc277ad16534cbdafe69f0c1b0a8d8a)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_7cc277ad16534cbdafe69f0c1b0a8d8a)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }



                // Przyklad implementacji Kuchni
                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 122); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_62b021e865ba4b0287911564d2e2750c_skladniki = Set.of(
                        new Skladnik(Produkt.SOL, 153),
                        new Skladnik(Produkt.DROZDZE, 1527),
                        new Skladnik(Produkt.MASLO, 893),
                        new Skladnik(Produkt.CUKIER, 839),
                        new Skladnik(Produkt.CUKIER_WANILIOWY, 2829),
                        new Skladnik(Produkt.MAKA_PSZENNA, 714),
                        new Skladnik(Produkt.RODZYNKI, 2067),
                        new Skladnik(Produkt.MAKA_RAZOWA, 2616),
                        new Skladnik(Produkt.TWAROG, 346)
                );
                Przepis przepis_62b021e865ba4b0287911564d2e2750c = () -> przepis_62b021e865ba4b0287911564d2e2750c_skladniki;




                Set<Skladnik> przeliczony_przepis_62b021e865ba4b0287911564d2e2750c = kuchnia.przeliczPrzepis(przepis_62b021e865ba4b0287911564d2e2750c);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_62b021e865ba4b0287911564d2e2750c);


                Przepis nowyPrzepis_przepis_62b021e865ba4b0287911564d2e2750c = () -> przeliczony_przepis_62b021e865ba4b0287911564d2e2750c;

                if (przeliczony_przepis_62b021e865ba4b0287911564d2e2750c.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_62b021e865ba4b0287911564d2e2750c)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_62b021e865ba4b0287911564d2e2750c)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }



                // Przyklad implementacji Kuchni
                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 634); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_6ba7a35d94e146ad8a415871e53808e1_skladniki = Set.of(
                        new Skladnik(Produkt.OLEJ_ROSLINNY, 1520),
                        new Skladnik(Produkt.MASLO, 1444),
                        new Skladnik(Produkt.TWAROG, 1360),
                        new Skladnik(Produkt.DROZDZE, 78),
                        new Skladnik(Produkt.PROSZEK_DO_PIECZENIA, 1077),
                        new Skladnik(Produkt.MAKA_ZIEMNIACZANA, 1396),
                        new Skladnik(Produkt.MAKA_RAZOWA, 2248),
                        new Skladnik(Produkt.WODA, 517),
                        new Skladnik(Produkt.CUKIER_WANILIOWY, 68),
                        new Skladnik(Produkt.RODZYNKI, 2840)
                );
                Przepis przepis_6ba7a35d94e146ad8a415871e53808e1 = () -> przepis_6ba7a35d94e146ad8a415871e53808e1_skladniki;




                Set<Skladnik> przeliczony_przepis_6ba7a35d94e146ad8a415871e53808e1 = kuchnia.przeliczPrzepis(przepis_6ba7a35d94e146ad8a415871e53808e1);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_6ba7a35d94e146ad8a415871e53808e1);


                Przepis nowyPrzepis_przepis_6ba7a35d94e146ad8a415871e53808e1 = () -> przeliczony_przepis_6ba7a35d94e146ad8a415871e53808e1;

                if (przeliczony_przepis_6ba7a35d94e146ad8a415871e53808e1.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_6ba7a35d94e146ad8a415871e53808e1)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_6ba7a35d94e146ad8a415871e53808e1)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }



                // Przyklad implementacji Kuchni
                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 775); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_c7b608a2e2f04f569f46eb33ff7bbf58_skladniki = Set.of(
                        new Skladnik(Produkt.CUKIER, 500),
                        new Skladnik(Produkt.MASLO, 2023),
                        new Skladnik(Produkt.RODZYNKI, 2165),
                        new Skladnik(Produkt.MARGARYNA, 1188),
                        new Skladnik(Produkt.CUKIER_PUDER, 1223),
                        new Skladnik(Produkt.PROSZEK_DO_PIECZENIA, 26)
                );
                Przepis przepis_c7b608a2e2f04f569f46eb33ff7bbf58 = () -> przepis_c7b608a2e2f04f569f46eb33ff7bbf58_skladniki;




                Set<Skladnik> przeliczony_przepis_c7b608a2e2f04f569f46eb33ff7bbf58 = kuchnia.przeliczPrzepis(przepis_c7b608a2e2f04f569f46eb33ff7bbf58);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_c7b608a2e2f04f569f46eb33ff7bbf58);


                Przepis nowyPrzepis_przepis_c7b608a2e2f04f569f46eb33ff7bbf58 = () -> przeliczony_przepis_c7b608a2e2f04f569f46eb33ff7bbf58;

                if (przeliczony_przepis_c7b608a2e2f04f569f46eb33ff7bbf58.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_c7b608a2e2f04f569f46eb33ff7bbf58)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_c7b608a2e2f04f569f46eb33ff7bbf58)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }



                // Przyklad implementacji Kuchni
                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 1439); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_34056c6b268a4c8094b0a3b66a224225_skladniki = Set.of(
                        new Skladnik(Produkt.MAKA_PSZENNA, 2208),
                        new Skladnik(Produkt.MAKA_RAZOWA, 2938),
                        new Skladnik(Produkt.PROSZEK_DO_PIECZENIA, 1158),
                        new Skladnik(Produkt.RODZYNKI, 2535),
                        new Skladnik(Produkt.DROZDZE, 2004),
                        new Skladnik(Produkt.MARGARYNA, 1750),
                        new Skladnik(Produkt.SOL, 1786),
                        new Skladnik(Produkt.TWAROG, 426),
                        new Skladnik(Produkt.OLEJ_ROSLINNY, 2589)
                );
                Przepis przepis_34056c6b268a4c8094b0a3b66a224225 = () -> przepis_34056c6b268a4c8094b0a3b66a224225_skladniki;




                Set<Skladnik> przeliczony_przepis_34056c6b268a4c8094b0a3b66a224225 = kuchnia.przeliczPrzepis(przepis_34056c6b268a4c8094b0a3b66a224225);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_34056c6b268a4c8094b0a3b66a224225);


                Przepis nowyPrzepis_przepis_34056c6b268a4c8094b0a3b66a224225 = () -> przeliczony_przepis_34056c6b268a4c8094b0a3b66a224225;

                if (przeliczony_przepis_34056c6b268a4c8094b0a3b66a224225.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_34056c6b268a4c8094b0a3b66a224225)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_34056c6b268a4c8094b0a3b66a224225)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }



                // Przyklad implementacji Kuchni
                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 1429); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_783f7966c076404f86accf82503e1377_skladniki = Set.of(
                        new Skladnik(Produkt.MAKA_RAZOWA, 1430),
                        new Skladnik(Produkt.RODZYNKI, 2433),
                        new Skladnik(Produkt.MASLO, 578),
                        new Skladnik(Produkt.CUKIER, 869),
                        new Skladnik(Produkt.TWAROG, 2972),
                        new Skladnik(Produkt.MLEKO, 573),
                        new Skladnik(Produkt.DROZDZE, 1462),
                        new Skladnik(Produkt.MAKA_PSZENNA, 2292),
                        new Skladnik(Produkt.CUKIER_PUDER, 740)
                );
                Przepis przepis_783f7966c076404f86accf82503e1377 = () -> przepis_783f7966c076404f86accf82503e1377_skladniki;




                Set<Skladnik> przeliczony_przepis_783f7966c076404f86accf82503e1377 = kuchnia.przeliczPrzepis(przepis_783f7966c076404f86accf82503e1377);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_783f7966c076404f86accf82503e1377);


                Przepis nowyPrzepis_przepis_783f7966c076404f86accf82503e1377 = () -> przeliczony_przepis_783f7966c076404f86accf82503e1377;

                if (przeliczony_przepis_783f7966c076404f86accf82503e1377.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_783f7966c076404f86accf82503e1377)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_783f7966c076404f86accf82503e1377)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }



                // Przyklad implementacji Kuchni
                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 331); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_a95d2a3c9f5a4f8e8a04c25693af77b7_skladniki = Set.of(
                        new Skladnik(Produkt.MAKA_RAZOWA, 957),
                        new Skladnik(Produkt.WODA, 485),
                        new Skladnik(Produkt.TWAROG, 959),
                        new Skladnik(Produkt.PROSZEK_DO_PIECZENIA, 1901),
                        new Skladnik(Produkt.MLEKO, 91)
                );
                Przepis przepis_a95d2a3c9f5a4f8e8a04c25693af77b7 = () -> przepis_a95d2a3c9f5a4f8e8a04c25693af77b7_skladniki;




                Set<Skladnik> przeliczony_przepis_a95d2a3c9f5a4f8e8a04c25693af77b7 = kuchnia.przeliczPrzepis(przepis_a95d2a3c9f5a4f8e8a04c25693af77b7);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_a95d2a3c9f5a4f8e8a04c25693af77b7);


                Przepis nowyPrzepis_przepis_a95d2a3c9f5a4f8e8a04c25693af77b7 = () -> przeliczony_przepis_a95d2a3c9f5a4f8e8a04c25693af77b7;

                if (przeliczony_przepis_a95d2a3c9f5a4f8e8a04c25693af77b7.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_a95d2a3c9f5a4f8e8a04c25693af77b7)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_a95d2a3c9f5a4f8e8a04c25693af77b7)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }



                // Przyklad implementacji Kuchni
                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 1308); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_decff99cdd864db6bdf7eb927d4899d1_skladniki = Set.of(
                        new Skladnik(Produkt.RODZYNKI, 2809),
                        new Skladnik(Produkt.MARGARYNA, 2080),
                        new Skladnik(Produkt.MAKA_RAZOWA, 2529),
                        new Skladnik(Produkt.MAKA_PSZENNA, 2808),
                        new Skladnik(Produkt.CUKIER_PUDER, 1821),
                        new Skladnik(Produkt.SOL, 1049),
                        new Skladnik(Produkt.TWAROG, 2684),
                        new Skladnik(Produkt.CUKIER_WANILIOWY, 2543),
                        new Skladnik(Produkt.CUKIER, 588)
                );
                Przepis przepis_decff99cdd864db6bdf7eb927d4899d1 = () -> przepis_decff99cdd864db6bdf7eb927d4899d1_skladniki;




                Set<Skladnik> przeliczony_przepis_decff99cdd864db6bdf7eb927d4899d1 = kuchnia.przeliczPrzepis(przepis_decff99cdd864db6bdf7eb927d4899d1);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_decff99cdd864db6bdf7eb927d4899d1);


                Przepis nowyPrzepis_przepis_decff99cdd864db6bdf7eb927d4899d1 = () -> przeliczony_przepis_decff99cdd864db6bdf7eb927d4899d1;

                if (przeliczony_przepis_decff99cdd864db6bdf7eb927d4899d1.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_decff99cdd864db6bdf7eb927d4899d1)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_decff99cdd864db6bdf7eb927d4899d1)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }



                // Przyklad implementacji Kuchni
                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 1743); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_80db1268e8734088b322351b83568e37_skladniki = Set.of(
                        new Skladnik(Produkt.CUKIER_PUDER, 1771),
                        new Skladnik(Produkt.MARGARYNA, 482),
                        new Skladnik(Produkt.MLEKO, 2512),
                        new Skladnik(Produkt.RODZYNKI, 1446),
                        new Skladnik(Produkt.PROSZEK_DO_PIECZENIA, 309),
                        new Skladnik(Produkt.CUKIER, 2865),
                        new Skladnik(Produkt.WODA, 1140),
                        new Skladnik(Produkt.SOL, 695),
                        new Skladnik(Produkt.MAKA_PSZENNA, 1486),
                        new Skladnik(Produkt.MAKA_RAZOWA, 991)
                );
                Przepis przepis_80db1268e8734088b322351b83568e37 = () -> przepis_80db1268e8734088b322351b83568e37_skladniki;




                Set<Skladnik> przeliczony_przepis_80db1268e8734088b322351b83568e37 = kuchnia.przeliczPrzepis(przepis_80db1268e8734088b322351b83568e37);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_80db1268e8734088b322351b83568e37);


                Przepis nowyPrzepis_przepis_80db1268e8734088b322351b83568e37 = () -> przeliczony_przepis_80db1268e8734088b322351b83568e37;

                if (przeliczony_przepis_80db1268e8734088b322351b83568e37.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_80db1268e8734088b322351b83568e37)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_80db1268e8734088b322351b83568e37)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }



                // Przyklad implementacji Kuchni
                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 1762); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_a3807d2420374cf2bde538edc1432145_skladniki = Set.of(
                        new Skladnik(Produkt.MAKA_PSZENNA, 1977),
                        new Skladnik(Produkt.DROZDZE, 1136),
                        new Skladnik(Produkt.MASLO, 1153),
                        new Skladnik(Produkt.PROSZEK_DO_PIECZENIA, 2786),
                        new Skladnik(Produkt.CUKIER_PUDER, 2952),
                        new Skladnik(Produkt.MARGARYNA, 2078),
                        new Skladnik(Produkt.MAKA_ZIEMNIACZANA, 1737),
                        new Skladnik(Produkt.OLEJ_ROSLINNY, 1131),
                        new Skladnik(Produkt.RODZYNKI, 632)
                );
                Przepis przepis_a3807d2420374cf2bde538edc1432145 = () -> przepis_a3807d2420374cf2bde538edc1432145_skladniki;




                Set<Skladnik> przeliczony_przepis_a3807d2420374cf2bde538edc1432145 = kuchnia.przeliczPrzepis(przepis_a3807d2420374cf2bde538edc1432145);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_a3807d2420374cf2bde538edc1432145);


                Przepis nowyPrzepis_przepis_a3807d2420374cf2bde538edc1432145 = () -> przeliczony_przepis_a3807d2420374cf2bde538edc1432145;

                if (przeliczony_przepis_a3807d2420374cf2bde538edc1432145.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_a3807d2420374cf2bde538edc1432145)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_a3807d2420374cf2bde538edc1432145)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }



                // Przyklad implementacji Kuchni
                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 956); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_a61a7dca5bc24dcea6caf44fe592c5ff_skladniki = Set.of(
                        new Skladnik(Produkt.CUKIER, 1919),
                        new Skladnik(Produkt.MARGARYNA, 2518),
                        new Skladnik(Produkt.PROSZEK_DO_PIECZENIA, 2184),
                        new Skladnik(Produkt.RODZYNKI, 1601),
                        new Skladnik(Produkt.DROZDZE, 25),
                        new Skladnik(Produkt.CUKIER_PUDER, 155),
                        new Skladnik(Produkt.MAKA_PSZENNA, 2423),
                        new Skladnik(Produkt.MASLO, 2520),
                        new Skladnik(Produkt.CUKIER_WANILIOWY, 891),
                        new Skladnik(Produkt.MAKA_ZIEMNIACZANA, 2004)
                );
                Przepis przepis_a61a7dca5bc24dcea6caf44fe592c5ff = () -> przepis_a61a7dca5bc24dcea6caf44fe592c5ff_skladniki;




                Set<Skladnik> przeliczony_przepis_a61a7dca5bc24dcea6caf44fe592c5ff = kuchnia.przeliczPrzepis(przepis_a61a7dca5bc24dcea6caf44fe592c5ff);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_a61a7dca5bc24dcea6caf44fe592c5ff);


                Przepis nowyPrzepis_przepis_a61a7dca5bc24dcea6caf44fe592c5ff = () -> przeliczony_przepis_a61a7dca5bc24dcea6caf44fe592c5ff;

                if (przeliczony_przepis_a61a7dca5bc24dcea6caf44fe592c5ff.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_a61a7dca5bc24dcea6caf44fe592c5ff)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_a61a7dca5bc24dcea6caf44fe592c5ff)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }



                // Przyklad implementacji Kuchni
                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 1455); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_f0eeb9692f1542e9a5ee7892c8d91168_skladniki = Set.of(
                        new Skladnik(Produkt.RODZYNKI, 1211),
                        new Skladnik(Produkt.CUKIER_PUDER, 413),
                        new Skladnik(Produkt.MAKA_ZIEMNIACZANA, 1340),
                        new Skladnik(Produkt.CUKIER_WANILIOWY, 1103),
                        new Skladnik(Produkt.MAKA_PSZENNA, 1567),
                        new Skladnik(Produkt.MLEKO, 2701),
                        new Skladnik(Produkt.MARGARYNA, 1764)
                );
                Przepis przepis_f0eeb9692f1542e9a5ee7892c8d91168 = () -> przepis_f0eeb9692f1542e9a5ee7892c8d91168_skladniki;




                Set<Skladnik> przeliczony_przepis_f0eeb9692f1542e9a5ee7892c8d91168 = kuchnia.przeliczPrzepis(przepis_f0eeb9692f1542e9a5ee7892c8d91168);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_f0eeb9692f1542e9a5ee7892c8d91168);


                Przepis nowyPrzepis_przepis_f0eeb9692f1542e9a5ee7892c8d91168 = () -> przeliczony_przepis_f0eeb9692f1542e9a5ee7892c8d91168;

                if (przeliczony_przepis_f0eeb9692f1542e9a5ee7892c8d91168.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_f0eeb9692f1542e9a5ee7892c8d91168)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_f0eeb9692f1542e9a5ee7892c8d91168)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }



                // Przyklad implementacji Kuchni
                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 771); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_4036bbb2e2bd4941bfe551d71e59c9f1_skladniki = Set.of(
                        new Skladnik(Produkt.PROSZEK_DO_PIECZENIA, 1385),
                        new Skladnik(Produkt.MAKA_PSZENNA, 2995),
                        new Skladnik(Produkt.SOL, 2881),
                        new Skladnik(Produkt.CUKIER, 579),
                        new Skladnik(Produkt.MARGARYNA, 1108),
                        new Skladnik(Produkt.MAKA_ZIEMNIACZANA, 2430),
                        new Skladnik(Produkt.CUKIER_PUDER, 398),
                        new Skladnik(Produkt.MLEKO, 1750)
                );
                Przepis przepis_4036bbb2e2bd4941bfe551d71e59c9f1 = () -> przepis_4036bbb2e2bd4941bfe551d71e59c9f1_skladniki;




                Set<Skladnik> przeliczony_przepis_4036bbb2e2bd4941bfe551d71e59c9f1 = kuchnia.przeliczPrzepis(przepis_4036bbb2e2bd4941bfe551d71e59c9f1);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_4036bbb2e2bd4941bfe551d71e59c9f1);


                Przepis nowyPrzepis_przepis_4036bbb2e2bd4941bfe551d71e59c9f1 = () -> przeliczony_przepis_4036bbb2e2bd4941bfe551d71e59c9f1;

                if (przeliczony_przepis_4036bbb2e2bd4941bfe551d71e59c9f1.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_4036bbb2e2bd4941bfe551d71e59c9f1)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_4036bbb2e2bd4941bfe551d71e59c9f1)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }



                // Przyklad implementacji Kuchni
                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 512); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_89d226d2cde449fe8441ea9b3b44d8f9_skladniki = Set.of(
                        new Skladnik(Produkt.MAKA_ZIEMNIACZANA, 1627),
                        new Skladnik(Produkt.RODZYNKI, 253),
                        new Skladnik(Produkt.OLEJ_ROSLINNY, 2387),
                        new Skladnik(Produkt.CUKIER, 2039),
                        new Skladnik(Produkt.MARGARYNA, 974),
                        new Skladnik(Produkt.MLEKO, 67),
                        new Skladnik(Produkt.PROSZEK_DO_PIECZENIA, 631),
                        new Skladnik(Produkt.DROZDZE, 1251),
                        new Skladnik(Produkt.CUKIER_WANILIOWY, 455),
                        new Skladnik(Produkt.WODA, 1816)
                );
                Przepis przepis_89d226d2cde449fe8441ea9b3b44d8f9 = () -> przepis_89d226d2cde449fe8441ea9b3b44d8f9_skladniki;




                Set<Skladnik> przeliczony_przepis_89d226d2cde449fe8441ea9b3b44d8f9 = kuchnia.przeliczPrzepis(przepis_89d226d2cde449fe8441ea9b3b44d8f9);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_89d226d2cde449fe8441ea9b3b44d8f9);


                Przepis nowyPrzepis_przepis_89d226d2cde449fe8441ea9b3b44d8f9 = () -> przeliczony_przepis_89d226d2cde449fe8441ea9b3b44d8f9;

                if (przeliczony_przepis_89d226d2cde449fe8441ea9b3b44d8f9.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_89d226d2cde449fe8441ea9b3b44d8f9)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_89d226d2cde449fe8441ea9b3b44d8f9)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }



                // Przyklad implementacji Kuchni
                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 68); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_a12d5c07f0294b3b913133ee4e8173e0_skladniki = Set.of(
                        new Skladnik(Produkt.MAKA_PSZENNA, 1639),
                        new Skladnik(Produkt.DROZDZE, 2336),
                        new Skladnik(Produkt.CUKIER_WANILIOWY, 5),
                        new Skladnik(Produkt.CUKIER, 2039),
                        new Skladnik(Produkt.PROSZEK_DO_PIECZENIA, 16),
                        new Skladnik(Produkt.MAKA_RAZOWA, 2673),
                        new Skladnik(Produkt.MAKA_ZIEMNIACZANA, 909)
                );
                Przepis przepis_a12d5c07f0294b3b913133ee4e8173e0 = () -> przepis_a12d5c07f0294b3b913133ee4e8173e0_skladniki;




                Set<Skladnik> przeliczony_przepis_a12d5c07f0294b3b913133ee4e8173e0 = kuchnia.przeliczPrzepis(przepis_a12d5c07f0294b3b913133ee4e8173e0);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_a12d5c07f0294b3b913133ee4e8173e0);


                Przepis nowyPrzepis_przepis_a12d5c07f0294b3b913133ee4e8173e0 = () -> przeliczony_przepis_a12d5c07f0294b3b913133ee4e8173e0;

                if (przeliczony_przepis_a12d5c07f0294b3b913133ee4e8173e0.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_a12d5c07f0294b3b913133ee4e8173e0)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_a12d5c07f0294b3b913133ee4e8173e0)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }



                // Przyklad implementacji Kuchni
                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 240); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_7ac3fd8e904e4e45bd5ac836ff88b3b0_skladniki = Set.of(
                        new Skladnik(Produkt.OLEJ_ROSLINNY, 2000),
                        new Skladnik(Produkt.RODZYNKI, 404),
                        new Skladnik(Produkt.MAKA_RAZOWA, 721),
                        new Skladnik(Produkt.MARGARYNA, 1640),
                        new Skladnik(Produkt.SOL, 2543),
                        new Skladnik(Produkt.DROZDZE, 1589),
                        new Skladnik(Produkt.PROSZEK_DO_PIECZENIA, 1736),
                        new Skladnik(Produkt.TWAROG, 2706),
                        new Skladnik(Produkt.CUKIER_PUDER, 1515)
                );
                Przepis przepis_7ac3fd8e904e4e45bd5ac836ff88b3b0 = () -> przepis_7ac3fd8e904e4e45bd5ac836ff88b3b0_skladniki;




                Set<Skladnik> przeliczony_przepis_7ac3fd8e904e4e45bd5ac836ff88b3b0 = kuchnia.przeliczPrzepis(przepis_7ac3fd8e904e4e45bd5ac836ff88b3b0);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_7ac3fd8e904e4e45bd5ac836ff88b3b0);


                Przepis nowyPrzepis_przepis_7ac3fd8e904e4e45bd5ac836ff88b3b0 = () -> przeliczony_przepis_7ac3fd8e904e4e45bd5ac836ff88b3b0;

                if (przeliczony_przepis_7ac3fd8e904e4e45bd5ac836ff88b3b0.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_7ac3fd8e904e4e45bd5ac836ff88b3b0)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_7ac3fd8e904e4e45bd5ac836ff88b3b0)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }



                // Przyklad implementacji Kuchni
                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 770); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_0b866c0ebde24e86aa1e5f979adad6cc_skladniki = Set.of(
                        new Skladnik(Produkt.CUKIER, 1504),
                        new Skladnik(Produkt.MAKA_PSZENNA, 2442),
                        new Skladnik(Produkt.MAKA_RAZOWA, 1353),
                        new Skladnik(Produkt.WODA, 2384),
                        new Skladnik(Produkt.CUKIER_PUDER, 2219),
                        new Skladnik(Produkt.OLEJ_ROSLINNY, 376),
                        new Skladnik(Produkt.RODZYNKI, 2860),
                        new Skladnik(Produkt.SOL, 2705),
                        new Skladnik(Produkt.MLEKO, 297)
                );
                Przepis przepis_0b866c0ebde24e86aa1e5f979adad6cc = () -> przepis_0b866c0ebde24e86aa1e5f979adad6cc_skladniki;




                Set<Skladnik> przeliczony_przepis_0b866c0ebde24e86aa1e5f979adad6cc = kuchnia.przeliczPrzepis(przepis_0b866c0ebde24e86aa1e5f979adad6cc);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_0b866c0ebde24e86aa1e5f979adad6cc);


                Przepis nowyPrzepis_przepis_0b866c0ebde24e86aa1e5f979adad6cc = () -> przeliczony_przepis_0b866c0ebde24e86aa1e5f979adad6cc;

                if (przeliczony_przepis_0b866c0ebde24e86aa1e5f979adad6cc.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_0b866c0ebde24e86aa1e5f979adad6cc)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_0b866c0ebde24e86aa1e5f979adad6cc)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }



                // Przyklad implementacji Kuchni
                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 581); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_8647d7dbaf6e43f5b723e132cb164dee_skladniki = Set.of(
                        new Skladnik(Produkt.CUKIER_WANILIOWY, 684),
                        new Skladnik(Produkt.CUKIER_PUDER, 516),
                        new Skladnik(Produkt.WODA, 2580),
                        new Skladnik(Produkt.MAKA_ZIEMNIACZANA, 1030),
                        new Skladnik(Produkt.OLEJ_ROSLINNY, 563),
                        new Skladnik(Produkt.DROZDZE, 2011),
                        new Skladnik(Produkt.PROSZEK_DO_PIECZENIA, 1625),
                        new Skladnik(Produkt.MAKA_PSZENNA, 45),
                        new Skladnik(Produkt.MAKA_RAZOWA, 1891)
                );
                Przepis przepis_8647d7dbaf6e43f5b723e132cb164dee = () -> przepis_8647d7dbaf6e43f5b723e132cb164dee_skladniki;




                Set<Skladnik> przeliczony_przepis_8647d7dbaf6e43f5b723e132cb164dee = kuchnia.przeliczPrzepis(przepis_8647d7dbaf6e43f5b723e132cb164dee);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_8647d7dbaf6e43f5b723e132cb164dee);


                Przepis nowyPrzepis_przepis_8647d7dbaf6e43f5b723e132cb164dee = () -> przeliczony_przepis_8647d7dbaf6e43f5b723e132cb164dee;

                if (przeliczony_przepis_8647d7dbaf6e43f5b723e132cb164dee.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_8647d7dbaf6e43f5b723e132cb164dee)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_8647d7dbaf6e43f5b723e132cb164dee)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }



                // Przyklad implementacji Kuchni
                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 286); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_b8db7ea15b8547259ab77331afe9ba12_skladniki = Set.of(
                        new Skladnik(Produkt.SOL, 189),
                        new Skladnik(Produkt.MAKA_ZIEMNIACZANA, 1185),
                        new Skladnik(Produkt.MARGARYNA, 430),
                        new Skladnik(Produkt.DROZDZE, 365),
                        new Skladnik(Produkt.MAKA_RAZOWA, 2279),
                        new Skladnik(Produkt.MAKA_PSZENNA, 1334),
                        new Skladnik(Produkt.RODZYNKI, 1445),
                        new Skladnik(Produkt.MASLO, 2224),
                        new Skladnik(Produkt.MLEKO, 2056),
                        new Skladnik(Produkt.PROSZEK_DO_PIECZENIA, 2619)
                );
                Przepis przepis_b8db7ea15b8547259ab77331afe9ba12 = () -> przepis_b8db7ea15b8547259ab77331afe9ba12_skladniki;




                Set<Skladnik> przeliczony_przepis_b8db7ea15b8547259ab77331afe9ba12 = kuchnia.przeliczPrzepis(przepis_b8db7ea15b8547259ab77331afe9ba12);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_b8db7ea15b8547259ab77331afe9ba12);


                Przepis nowyPrzepis_przepis_b8db7ea15b8547259ab77331afe9ba12 = () -> przeliczony_przepis_b8db7ea15b8547259ab77331afe9ba12;

                if (przeliczony_przepis_b8db7ea15b8547259ab77331afe9ba12.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_b8db7ea15b8547259ab77331afe9ba12)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_b8db7ea15b8547259ab77331afe9ba12)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }



                // Przyklad implementacji Kuchni
                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 1210); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_8b1473bc5d3246c0bab39a902e72c086_skladniki = Set.of(
                        new Skladnik(Produkt.MAKA_PSZENNA, 1818),
                        new Skladnik(Produkt.CUKIER, 2322),
                        new Skladnik(Produkt.DROZDZE, 2543),
                        new Skladnik(Produkt.WODA, 1713),
                        new Skladnik(Produkt.MARGARYNA, 2029),
                        new Skladnik(Produkt.MAKA_RAZOWA, 2846),
                        new Skladnik(Produkt.MLEKO, 1492)
                );
                Przepis przepis_8b1473bc5d3246c0bab39a902e72c086 = () -> przepis_8b1473bc5d3246c0bab39a902e72c086_skladniki;




                Set<Skladnik> przeliczony_przepis_8b1473bc5d3246c0bab39a902e72c086 = kuchnia.przeliczPrzepis(przepis_8b1473bc5d3246c0bab39a902e72c086);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_8b1473bc5d3246c0bab39a902e72c086);


                Przepis nowyPrzepis_przepis_8b1473bc5d3246c0bab39a902e72c086 = () -> przeliczony_przepis_8b1473bc5d3246c0bab39a902e72c086;

                if (przeliczony_przepis_8b1473bc5d3246c0bab39a902e72c086.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_8b1473bc5d3246c0bab39a902e72c086)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_8b1473bc5d3246c0bab39a902e72c086)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }



                // Przyklad implementacji Kuchni
                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 1353); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_992974a2c9d04905a5da271d6e65d75c_skladniki = Set.of(
                        new Skladnik(Produkt.CUKIER_PUDER, 736),
                        new Skladnik(Produkt.CUKIER, 1638),
                        new Skladnik(Produkt.TWAROG, 1666),
                        new Skladnik(Produkt.MASLO, 1340),
                        new Skladnik(Produkt.MAKA_RAZOWA, 1885),
                        new Skladnik(Produkt.DROZDZE, 1784),
                        new Skladnik(Produkt.SOL, 2833),
                        new Skladnik(Produkt.MARGARYNA, 870),
                        new Skladnik(Produkt.MLEKO, 503),
                        new Skladnik(Produkt.PROSZEK_DO_PIECZENIA, 2546)
                );
                Przepis przepis_992974a2c9d04905a5da271d6e65d75c = () -> przepis_992974a2c9d04905a5da271d6e65d75c_skladniki;




                Set<Skladnik> przeliczony_przepis_992974a2c9d04905a5da271d6e65d75c = kuchnia.przeliczPrzepis(przepis_992974a2c9d04905a5da271d6e65d75c);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_992974a2c9d04905a5da271d6e65d75c);


                Przepis nowyPrzepis_przepis_992974a2c9d04905a5da271d6e65d75c = () -> przeliczony_przepis_992974a2c9d04905a5da271d6e65d75c;

                if (przeliczony_przepis_992974a2c9d04905a5da271d6e65d75c.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_992974a2c9d04905a5da271d6e65d75c)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_992974a2c9d04905a5da271d6e65d75c)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }



                // Przyklad implementacji Kuchni
                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 1409); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_3d245e9a4a444beda15b60a36057c19a_skladniki = Set.of(
                        new Skladnik(Produkt.MAKA_PSZENNA, 1346),
                        new Skladnik(Produkt.MASLO, 2198),
                        new Skladnik(Produkt.MAKA_ZIEMNIACZANA, 1354),
                        new Skladnik(Produkt.DROZDZE, 1918),
                        new Skladnik(Produkt.CUKIER, 879),
                        new Skladnik(Produkt.SOL, 1636)
                );
                Przepis przepis_3d245e9a4a444beda15b60a36057c19a = () -> przepis_3d245e9a4a444beda15b60a36057c19a_skladniki;




                Set<Skladnik> przeliczony_przepis_3d245e9a4a444beda15b60a36057c19a = kuchnia.przeliczPrzepis(przepis_3d245e9a4a444beda15b60a36057c19a);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_3d245e9a4a444beda15b60a36057c19a);


                Przepis nowyPrzepis_przepis_3d245e9a4a444beda15b60a36057c19a = () -> przeliczony_przepis_3d245e9a4a444beda15b60a36057c19a;

                if (przeliczony_przepis_3d245e9a4a444beda15b60a36057c19a.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_3d245e9a4a444beda15b60a36057c19a)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_3d245e9a4a444beda15b60a36057c19a)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }



                // Przyklad implementacji Kuchni
                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 855); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_02ac81d3ca2d4c8ca784845b598cd5bd_skladniki = Set.of(
                        new Skladnik(Produkt.MARGARYNA, 345),
                        new Skladnik(Produkt.MASLO, 271),
                        new Skladnik(Produkt.CUKIER_WANILIOWY, 1343),
                        new Skladnik(Produkt.CUKIER_PUDER, 2986),
                        new Skladnik(Produkt.RODZYNKI, 163),
                        new Skladnik(Produkt.OLEJ_ROSLINNY, 929),
                        new Skladnik(Produkt.MAKA_PSZENNA, 415),
                        new Skladnik(Produkt.MAKA_ZIEMNIACZANA, 910),
                        new Skladnik(Produkt.MAKA_RAZOWA, 1226)
                );
                Przepis przepis_02ac81d3ca2d4c8ca784845b598cd5bd = () -> przepis_02ac81d3ca2d4c8ca784845b598cd5bd_skladniki;




                Set<Skladnik> przeliczony_przepis_02ac81d3ca2d4c8ca784845b598cd5bd = kuchnia.przeliczPrzepis(przepis_02ac81d3ca2d4c8ca784845b598cd5bd);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_02ac81d3ca2d4c8ca784845b598cd5bd);


                Przepis nowyPrzepis_przepis_02ac81d3ca2d4c8ca784845b598cd5bd = () -> przeliczony_przepis_02ac81d3ca2d4c8ca784845b598cd5bd;

                if (przeliczony_przepis_02ac81d3ca2d4c8ca784845b598cd5bd.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_02ac81d3ca2d4c8ca784845b598cd5bd)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_02ac81d3ca2d4c8ca784845b598cd5bd)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }



                // Przyklad implementacji Kuchni
                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 1187); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_9e1534c404654cc8b134004cbb991dcb_skladniki = Set.of(
                        new Skladnik(Produkt.MAKA_PSZENNA, 1845),
                        new Skladnik(Produkt.SOL, 990),
                        new Skladnik(Produkt.OLEJ_ROSLINNY, 1222),
                        new Skladnik(Produkt.WODA, 355),
                        new Skladnik(Produkt.MAKA_RAZOWA, 516),
                        new Skladnik(Produkt.MAKA_ZIEMNIACZANA, 2115),
                        new Skladnik(Produkt.MASLO, 1530),
                        new Skladnik(Produkt.DROZDZE, 2566),
                        new Skladnik(Produkt.CUKIER_PUDER, 2644),
                        new Skladnik(Produkt.MLEKO, 1748)
                );
                Przepis przepis_9e1534c404654cc8b134004cbb991dcb = () -> przepis_9e1534c404654cc8b134004cbb991dcb_skladniki;




                Set<Skladnik> przeliczony_przepis_9e1534c404654cc8b134004cbb991dcb = kuchnia.przeliczPrzepis(przepis_9e1534c404654cc8b134004cbb991dcb);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_9e1534c404654cc8b134004cbb991dcb);


                Przepis nowyPrzepis_przepis_9e1534c404654cc8b134004cbb991dcb = () -> przeliczony_przepis_9e1534c404654cc8b134004cbb991dcb;

                if (przeliczony_przepis_9e1534c404654cc8b134004cbb991dcb.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_9e1534c404654cc8b134004cbb991dcb)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_9e1534c404654cc8b134004cbb991dcb)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }



                // Przyklad implementacji Kuchni
                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 1753); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_a14a1a5b9fcb4a6bb747a21fd0f2e994_skladniki = Set.of(
                        new Skladnik(Produkt.MASLO, 1171),
                        new Skladnik(Produkt.RODZYNKI, 2165),
                        new Skladnik(Produkt.TWAROG, 2175),
                        new Skladnik(Produkt.MARGARYNA, 1319),
                        new Skladnik(Produkt.CUKIER_PUDER, 1476)
                );
                Przepis przepis_a14a1a5b9fcb4a6bb747a21fd0f2e994 = () -> przepis_a14a1a5b9fcb4a6bb747a21fd0f2e994_skladniki;




                Set<Skladnik> przeliczony_przepis_a14a1a5b9fcb4a6bb747a21fd0f2e994 = kuchnia.przeliczPrzepis(przepis_a14a1a5b9fcb4a6bb747a21fd0f2e994);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_a14a1a5b9fcb4a6bb747a21fd0f2e994);


                Przepis nowyPrzepis_przepis_a14a1a5b9fcb4a6bb747a21fd0f2e994 = () -> przeliczony_przepis_a14a1a5b9fcb4a6bb747a21fd0f2e994;

                if (przeliczony_przepis_a14a1a5b9fcb4a6bb747a21fd0f2e994.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_a14a1a5b9fcb4a6bb747a21fd0f2e994)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_a14a1a5b9fcb4a6bb747a21fd0f2e994)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }



                // Przyklad implementacji Kuchni
                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 587); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_579c003b9a6e47fbae0aff18d749074b_skladniki = Set.of(
                        new Skladnik(Produkt.MAKA_RAZOWA, 1995),
                        new Skladnik(Produkt.OLEJ_ROSLINNY, 1296),
                        new Skladnik(Produkt.WODA, 2574),
                        new Skladnik(Produkt.MLEKO, 297),
                        new Skladnik(Produkt.MAKA_PSZENNA, 2873),
                        new Skladnik(Produkt.CUKIER, 1981),
                        new Skladnik(Produkt.CUKIER_PUDER, 893),
                        new Skladnik(Produkt.MASLO, 2261)
                );
                Przepis przepis_579c003b9a6e47fbae0aff18d749074b = () -> przepis_579c003b9a6e47fbae0aff18d749074b_skladniki;




                Set<Skladnik> przeliczony_przepis_579c003b9a6e47fbae0aff18d749074b = kuchnia.przeliczPrzepis(przepis_579c003b9a6e47fbae0aff18d749074b);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_579c003b9a6e47fbae0aff18d749074b);


                Przepis nowyPrzepis_przepis_579c003b9a6e47fbae0aff18d749074b = () -> przeliczony_przepis_579c003b9a6e47fbae0aff18d749074b;

                if (przeliczony_przepis_579c003b9a6e47fbae0aff18d749074b.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_579c003b9a6e47fbae0aff18d749074b)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_579c003b9a6e47fbae0aff18d749074b)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }



                // Przyklad implementacji Kuchni
                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 520); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_f24518fb51d740f9ba02d3ac118d78cf_skladniki = Set.of(
                        new Skladnik(Produkt.MASLO, 1333),
                        new Skladnik(Produkt.WODA, 450),
                        new Skladnik(Produkt.DROZDZE, 2023),
                        new Skladnik(Produkt.CUKIER, 2396),
                        new Skladnik(Produkt.CUKIER_WANILIOWY, 2226),
                        new Skladnik(Produkt.RODZYNKI, 2609)
                );
                Przepis przepis_f24518fb51d740f9ba02d3ac118d78cf = () -> przepis_f24518fb51d740f9ba02d3ac118d78cf_skladniki;




                Set<Skladnik> przeliczony_przepis_f24518fb51d740f9ba02d3ac118d78cf = kuchnia.przeliczPrzepis(przepis_f24518fb51d740f9ba02d3ac118d78cf);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_f24518fb51d740f9ba02d3ac118d78cf);


                Przepis nowyPrzepis_przepis_f24518fb51d740f9ba02d3ac118d78cf = () -> przeliczony_przepis_f24518fb51d740f9ba02d3ac118d78cf;

                if (przeliczony_przepis_f24518fb51d740f9ba02d3ac118d78cf.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_f24518fb51d740f9ba02d3ac118d78cf)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_f24518fb51d740f9ba02d3ac118d78cf)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }



                // Przyklad implementacji Kuchni
                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 1220); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_076e831647a840ceae5f06b1dcf4f17d_skladniki = Set.of(
                        new Skladnik(Produkt.PROSZEK_DO_PIECZENIA, 193),
                        new Skladnik(Produkt.CUKIER, 110),
                        new Skladnik(Produkt.WODA, 1604),
                        new Skladnik(Produkt.MLEKO, 2613),
                        new Skladnik(Produkt.MAKA_ZIEMNIACZANA, 881),
                        new Skladnik(Produkt.OLEJ_ROSLINNY, 1869),
                        new Skladnik(Produkt.MAKA_RAZOWA, 1668),
                        new Skladnik(Produkt.RODZYNKI, 200),
                        new Skladnik(Produkt.MAKA_PSZENNA, 1537)
                );
                Przepis przepis_076e831647a840ceae5f06b1dcf4f17d = () -> przepis_076e831647a840ceae5f06b1dcf4f17d_skladniki;




                Set<Skladnik> przeliczony_przepis_076e831647a840ceae5f06b1dcf4f17d = kuchnia.przeliczPrzepis(przepis_076e831647a840ceae5f06b1dcf4f17d);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_076e831647a840ceae5f06b1dcf4f17d);


                Przepis nowyPrzepis_przepis_076e831647a840ceae5f06b1dcf4f17d = () -> przeliczony_przepis_076e831647a840ceae5f06b1dcf4f17d;

                if (przeliczony_przepis_076e831647a840ceae5f06b1dcf4f17d.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_076e831647a840ceae5f06b1dcf4f17d)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_076e831647a840ceae5f06b1dcf4f17d)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }



                // Przyklad implementacji Kuchni
                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 530); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_3bc63687e2bb4d24a011f5845bb493c4_skladniki = Set.of(
                        new Skladnik(Produkt.WODA, 1772),
                        new Skladnik(Produkt.TWAROG, 1267),
                        new Skladnik(Produkt.MAKA_RAZOWA, 1513),
                        new Skladnik(Produkt.DROZDZE, 1786),
                        new Skladnik(Produkt.CUKIER_WANILIOWY, 2657),
                        new Skladnik(Produkt.OLEJ_ROSLINNY, 1946),
                        new Skladnik(Produkt.PROSZEK_DO_PIECZENIA, 2594),
                        new Skladnik(Produkt.CUKIER, 1438),
                        new Skladnik(Produkt.RODZYNKI, 1480),
                        new Skladnik(Produkt.MASLO, 2166)
                );
                Przepis przepis_3bc63687e2bb4d24a011f5845bb493c4 = () -> przepis_3bc63687e2bb4d24a011f5845bb493c4_skladniki;




                Set<Skladnik> przeliczony_przepis_3bc63687e2bb4d24a011f5845bb493c4 = kuchnia.przeliczPrzepis(przepis_3bc63687e2bb4d24a011f5845bb493c4);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_3bc63687e2bb4d24a011f5845bb493c4);


                Przepis nowyPrzepis_przepis_3bc63687e2bb4d24a011f5845bb493c4 = () -> przeliczony_przepis_3bc63687e2bb4d24a011f5845bb493c4;

                if (przeliczony_przepis_3bc63687e2bb4d24a011f5845bb493c4.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_3bc63687e2bb4d24a011f5845bb493c4)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_3bc63687e2bb4d24a011f5845bb493c4)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }



                // Przyklad implementacji Kuchni
                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 637); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_c00a7386bce04220a2fdb28ecd91b1ca_skladniki = Set.of(
                        new Skladnik(Produkt.MAKA_RAZOWA, 2445),
                        new Skladnik(Produkt.MAKA_PSZENNA, 2584),
                        new Skladnik(Produkt.DROZDZE, 1497),
                        new Skladnik(Produkt.TWAROG, 428),
                        new Skladnik(Produkt.RODZYNKI, 2098),
                        new Skladnik(Produkt.CUKIER, 469),
                        new Skladnik(Produkt.WODA, 2372),
                        new Skladnik(Produkt.MARGARYNA, 322)
                );
                Przepis przepis_c00a7386bce04220a2fdb28ecd91b1ca = () -> przepis_c00a7386bce04220a2fdb28ecd91b1ca_skladniki;




                Set<Skladnik> przeliczony_przepis_c00a7386bce04220a2fdb28ecd91b1ca = kuchnia.przeliczPrzepis(przepis_c00a7386bce04220a2fdb28ecd91b1ca);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_c00a7386bce04220a2fdb28ecd91b1ca);


                Przepis nowyPrzepis_przepis_c00a7386bce04220a2fdb28ecd91b1ca = () -> przeliczony_przepis_c00a7386bce04220a2fdb28ecd91b1ca;

                if (przeliczony_przepis_c00a7386bce04220a2fdb28ecd91b1ca.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_c00a7386bce04220a2fdb28ecd91b1ca)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_c00a7386bce04220a2fdb28ecd91b1ca)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }



                // Przyklad implementacji Kuchni
                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 1280); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_eb848a36d1ba4aef943ada650f39d095_skladniki = Set.of(
                        new Skladnik(Produkt.TWAROG, 1973),
                        new Skladnik(Produkt.DROZDZE, 1259),
                        new Skladnik(Produkt.MARGARYNA, 858),
                        new Skladnik(Produkt.CUKIER_WANILIOWY, 1884),
                        new Skladnik(Produkt.MASLO, 1343),
                        new Skladnik(Produkt.CUKIER, 162)
                );
                Przepis przepis_eb848a36d1ba4aef943ada650f39d095 = () -> przepis_eb848a36d1ba4aef943ada650f39d095_skladniki;




                Set<Skladnik> przeliczony_przepis_eb848a36d1ba4aef943ada650f39d095 = kuchnia.przeliczPrzepis(przepis_eb848a36d1ba4aef943ada650f39d095);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_eb848a36d1ba4aef943ada650f39d095);


                Przepis nowyPrzepis_przepis_eb848a36d1ba4aef943ada650f39d095 = () -> przeliczony_przepis_eb848a36d1ba4aef943ada650f39d095;

                if (przeliczony_przepis_eb848a36d1ba4aef943ada650f39d095.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_eb848a36d1ba4aef943ada650f39d095)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_eb848a36d1ba4aef943ada650f39d095)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }



                // Przyklad implementacji Kuchni
                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 460); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_393e36a23cab4be892a618923313e0ab_skladniki = Set.of(
                        new Skladnik(Produkt.CUKIER_WANILIOWY, 1872),
                        new Skladnik(Produkt.CUKIER_PUDER, 613),
                        new Skladnik(Produkt.MLEKO, 2336),
                        new Skladnik(Produkt.MAKA_RAZOWA, 1907),
                        new Skladnik(Produkt.WODA, 1375),
                        new Skladnik(Produkt.MAKA_ZIEMNIACZANA, 1790),
                        new Skladnik(Produkt.TWAROG, 2403),
                        new Skladnik(Produkt.OLEJ_ROSLINNY, 1539),
                        new Skladnik(Produkt.MAKA_PSZENNA, 523),
                        new Skladnik(Produkt.PROSZEK_DO_PIECZENIA, 1519)
                );
                Przepis przepis_393e36a23cab4be892a618923313e0ab = () -> przepis_393e36a23cab4be892a618923313e0ab_skladniki;




                Set<Skladnik> przeliczony_przepis_393e36a23cab4be892a618923313e0ab = kuchnia.przeliczPrzepis(przepis_393e36a23cab4be892a618923313e0ab);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_393e36a23cab4be892a618923313e0ab);


                Przepis nowyPrzepis_przepis_393e36a23cab4be892a618923313e0ab = () -> przeliczony_przepis_393e36a23cab4be892a618923313e0ab;

                if (przeliczony_przepis_393e36a23cab4be892a618923313e0ab.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_393e36a23cab4be892a618923313e0ab)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_393e36a23cab4be892a618923313e0ab)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }



                // Przyklad implementacji Kuchni
                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 795); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_d40d4471d5f7418bbbff567675ba1b31_skladniki = Set.of(
                        new Skladnik(Produkt.CUKIER_PUDER, 2309),
                        new Skladnik(Produkt.SOL, 139),
                        new Skladnik(Produkt.MAKA_PSZENNA, 2311),
                        new Skladnik(Produkt.CUKIER, 1484),
                        new Skladnik(Produkt.DROZDZE, 2193),
                        new Skladnik(Produkt.CUKIER_WANILIOWY, 1468),
                        new Skladnik(Produkt.MLEKO, 1106),
                        new Skladnik(Produkt.OLEJ_ROSLINNY, 2977),
                        new Skladnik(Produkt.MAKA_RAZOWA, 2211)
                );
                Przepis przepis_d40d4471d5f7418bbbff567675ba1b31 = () -> przepis_d40d4471d5f7418bbbff567675ba1b31_skladniki;




                Set<Skladnik> przeliczony_przepis_d40d4471d5f7418bbbff567675ba1b31 = kuchnia.przeliczPrzepis(przepis_d40d4471d5f7418bbbff567675ba1b31);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_d40d4471d5f7418bbbff567675ba1b31);


                Przepis nowyPrzepis_przepis_d40d4471d5f7418bbbff567675ba1b31 = () -> przeliczony_przepis_d40d4471d5f7418bbbff567675ba1b31;

                if (przeliczony_przepis_d40d4471d5f7418bbbff567675ba1b31.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_d40d4471d5f7418bbbff567675ba1b31)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_d40d4471d5f7418bbbff567675ba1b31)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }



                // Przyklad implementacji Kuchni
                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 1257); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_195c904960424fc08fbf4354f1e8c632_skladniki = Set.of(
                        new Skladnik(Produkt.TWAROG, 24),
                        new Skladnik(Produkt.DROZDZE, 1902),
                        new Skladnik(Produkt.MAKA_PSZENNA, 1454),
                        new Skladnik(Produkt.PROSZEK_DO_PIECZENIA, 1840),
                        new Skladnik(Produkt.CUKIER, 2839)
                );
                Przepis przepis_195c904960424fc08fbf4354f1e8c632 = () -> przepis_195c904960424fc08fbf4354f1e8c632_skladniki;




                Set<Skladnik> przeliczony_przepis_195c904960424fc08fbf4354f1e8c632 = kuchnia.przeliczPrzepis(przepis_195c904960424fc08fbf4354f1e8c632);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_195c904960424fc08fbf4354f1e8c632);


                Przepis nowyPrzepis_przepis_195c904960424fc08fbf4354f1e8c632 = () -> przeliczony_przepis_195c904960424fc08fbf4354f1e8c632;

                if (przeliczony_przepis_195c904960424fc08fbf4354f1e8c632.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_195c904960424fc08fbf4354f1e8c632)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_195c904960424fc08fbf4354f1e8c632)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }



                // Przyklad implementacji Kuchni
                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 246); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_1f3b7d2244814d20961a125fd65a630f_skladniki = Set.of(
                        new Skladnik(Produkt.TWAROG, 2638),
                        new Skladnik(Produkt.MAKA_ZIEMNIACZANA, 2437),
                        new Skladnik(Produkt.RODZYNKI, 696),
                        new Skladnik(Produkt.CUKIER_PUDER, 49),
                        new Skladnik(Produkt.OLEJ_ROSLINNY, 1614),
                        new Skladnik(Produkt.CUKIER_WANILIOWY, 620),
                        new Skladnik(Produkt.CUKIER, 1879),
                        new Skladnik(Produkt.MARGARYNA, 171)
                );
                Przepis przepis_1f3b7d2244814d20961a125fd65a630f = () -> przepis_1f3b7d2244814d20961a125fd65a630f_skladniki;




                Set<Skladnik> przeliczony_przepis_1f3b7d2244814d20961a125fd65a630f = kuchnia.przeliczPrzepis(przepis_1f3b7d2244814d20961a125fd65a630f);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_1f3b7d2244814d20961a125fd65a630f);


                Przepis nowyPrzepis_przepis_1f3b7d2244814d20961a125fd65a630f = () -> przeliczony_przepis_1f3b7d2244814d20961a125fd65a630f;

                if (przeliczony_przepis_1f3b7d2244814d20961a125fd65a630f.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_1f3b7d2244814d20961a125fd65a630f)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_1f3b7d2244814d20961a125fd65a630f)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }



                // Przyklad implementacji Kuchni
                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 697); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_24ec2fed30934f239478c87bf8b639a9_skladniki = Set.of(
                        new Skladnik(Produkt.TWAROG, 1443),
                        new Skladnik(Produkt.RODZYNKI, 2519),
                        new Skladnik(Produkt.DROZDZE, 1532),
                        new Skladnik(Produkt.CUKIER, 116),
                        new Skladnik(Produkt.MAKA_PSZENNA, 668)
                );
                Przepis przepis_24ec2fed30934f239478c87bf8b639a9 = () -> przepis_24ec2fed30934f239478c87bf8b639a9_skladniki;




                Set<Skladnik> przeliczony_przepis_24ec2fed30934f239478c87bf8b639a9 = kuchnia.przeliczPrzepis(przepis_24ec2fed30934f239478c87bf8b639a9);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_24ec2fed30934f239478c87bf8b639a9);


                Przepis nowyPrzepis_przepis_24ec2fed30934f239478c87bf8b639a9 = () -> przeliczony_przepis_24ec2fed30934f239478c87bf8b639a9;

                if (przeliczony_przepis_24ec2fed30934f239478c87bf8b639a9.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_24ec2fed30934f239478c87bf8b639a9)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_24ec2fed30934f239478c87bf8b639a9)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }



                // Przyklad implementacji Kuchni
                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 687); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_3e7213e412684d6e84e9c0e22f96b159_skladniki = Set.of(
                        new Skladnik(Produkt.DROZDZE, 1585),
                        new Skladnik(Produkt.MAKA_PSZENNA, 562),
                        new Skladnik(Produkt.CUKIER_WANILIOWY, 2474),
                        new Skladnik(Produkt.MLEKO, 1397),
                        new Skladnik(Produkt.SOL, 2618),
                        new Skladnik(Produkt.CUKIER, 1268),
                        new Skladnik(Produkt.MAKA_ZIEMNIACZANA, 729),
                        new Skladnik(Produkt.MAKA_RAZOWA, 761),
                        new Skladnik(Produkt.OLEJ_ROSLINNY, 2648),
                        new Skladnik(Produkt.WODA, 214)
                );
                Przepis przepis_3e7213e412684d6e84e9c0e22f96b159 = () -> przepis_3e7213e412684d6e84e9c0e22f96b159_skladniki;




                Set<Skladnik> przeliczony_przepis_3e7213e412684d6e84e9c0e22f96b159 = kuchnia.przeliczPrzepis(przepis_3e7213e412684d6e84e9c0e22f96b159);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_3e7213e412684d6e84e9c0e22f96b159);


                Przepis nowyPrzepis_przepis_3e7213e412684d6e84e9c0e22f96b159 = () -> przeliczony_przepis_3e7213e412684d6e84e9c0e22f96b159;

                if (przeliczony_przepis_3e7213e412684d6e84e9c0e22f96b159.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_3e7213e412684d6e84e9c0e22f96b159)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_3e7213e412684d6e84e9c0e22f96b159)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }



                // Przyklad implementacji Kuchni
                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 1440); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_48c41fe3db684feda2291d911ae791a9_skladniki = Set.of(
                        new Skladnik(Produkt.OLEJ_ROSLINNY, 2400),
                        new Skladnik(Produkt.MAKA_ZIEMNIACZANA, 2914),
                        new Skladnik(Produkt.MLEKO, 1689),
                        new Skladnik(Produkt.MAKA_RAZOWA, 335),
                        new Skladnik(Produkt.MAKA_PSZENNA, 1371),
                        new Skladnik(Produkt.TWAROG, 1657),
                        new Skladnik(Produkt.CUKIER_WANILIOWY, 1193),
                        new Skladnik(Produkt.CUKIER_PUDER, 1342)
                );
                Przepis przepis_48c41fe3db684feda2291d911ae791a9 = () -> przepis_48c41fe3db684feda2291d911ae791a9_skladniki;




                Set<Skladnik> przeliczony_przepis_48c41fe3db684feda2291d911ae791a9 = kuchnia.przeliczPrzepis(przepis_48c41fe3db684feda2291d911ae791a9);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_48c41fe3db684feda2291d911ae791a9);


                Przepis nowyPrzepis_przepis_48c41fe3db684feda2291d911ae791a9 = () -> przeliczony_przepis_48c41fe3db684feda2291d911ae791a9;

                if (przeliczony_przepis_48c41fe3db684feda2291d911ae791a9.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_48c41fe3db684feda2291d911ae791a9)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_48c41fe3db684feda2291d911ae791a9)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }



                // Przyklad implementacji Kuchni
                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 491); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_d601f3aa45d8460195ee26fc4ad4994c_skladniki = Set.of(
                        new Skladnik(Produkt.DROZDZE, 2073),
                        new Skladnik(Produkt.MAKA_PSZENNA, 802),
                        new Skladnik(Produkt.TWAROG, 255),
                        new Skladnik(Produkt.MLEKO, 142),
                        new Skladnik(Produkt.SOL, 1432)
                );
                Przepis przepis_d601f3aa45d8460195ee26fc4ad4994c = () -> przepis_d601f3aa45d8460195ee26fc4ad4994c_skladniki;




                Set<Skladnik> przeliczony_przepis_d601f3aa45d8460195ee26fc4ad4994c = kuchnia.przeliczPrzepis(przepis_d601f3aa45d8460195ee26fc4ad4994c);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_d601f3aa45d8460195ee26fc4ad4994c);


                Przepis nowyPrzepis_przepis_d601f3aa45d8460195ee26fc4ad4994c = () -> przeliczony_przepis_d601f3aa45d8460195ee26fc4ad4994c;

                if (przeliczony_przepis_d601f3aa45d8460195ee26fc4ad4994c.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_d601f3aa45d8460195ee26fc4ad4994c)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_d601f3aa45d8460195ee26fc4ad4994c)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }



                // Przyklad implementacji Kuchni
                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 878); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_1613a398106a4ee389d06adca74d4665_skladniki = Set.of(
                        new Skladnik(Produkt.SOL, 2368),
                        new Skladnik(Produkt.WODA, 1622),
                        new Skladnik(Produkt.PROSZEK_DO_PIECZENIA, 595),
                        new Skladnik(Produkt.MASLO, 1510),
                        new Skladnik(Produkt.RODZYNKI, 1973),
                        new Skladnik(Produkt.CUKIER_PUDER, 1725),
                        new Skladnik(Produkt.CUKIER, 1772),
                        new Skladnik(Produkt.MAKA_ZIEMNIACZANA, 2259),
                        new Skladnik(Produkt.MAKA_RAZOWA, 621),
                        new Skladnik(Produkt.MLEKO, 939)
                );
                Przepis przepis_1613a398106a4ee389d06adca74d4665 = () -> przepis_1613a398106a4ee389d06adca74d4665_skladniki;




                Set<Skladnik> przeliczony_przepis_1613a398106a4ee389d06adca74d4665 = kuchnia.przeliczPrzepis(przepis_1613a398106a4ee389d06adca74d4665);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_1613a398106a4ee389d06adca74d4665);


                Przepis nowyPrzepis_przepis_1613a398106a4ee389d06adca74d4665 = () -> przeliczony_przepis_1613a398106a4ee389d06adca74d4665;

                if (przeliczony_przepis_1613a398106a4ee389d06adca74d4665.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_1613a398106a4ee389d06adca74d4665)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_1613a398106a4ee389d06adca74d4665)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }



                // Przyklad implementacji Kuchni
                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 88); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_70804b93c0c04f79b6c8918881706dbd_skladniki = Set.of(
                        new Skladnik(Produkt.PROSZEK_DO_PIECZENIA, 2313),
                        new Skladnik(Produkt.MLEKO, 1674),
                        new Skladnik(Produkt.MAKA_PSZENNA, 1090),
                        new Skladnik(Produkt.CUKIER, 1404),
                        new Skladnik(Produkt.RODZYNKI, 216)
                );
                Przepis przepis_70804b93c0c04f79b6c8918881706dbd = () -> przepis_70804b93c0c04f79b6c8918881706dbd_skladniki;




                Set<Skladnik> przeliczony_przepis_70804b93c0c04f79b6c8918881706dbd = kuchnia.przeliczPrzepis(przepis_70804b93c0c04f79b6c8918881706dbd);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_70804b93c0c04f79b6c8918881706dbd);


                Przepis nowyPrzepis_przepis_70804b93c0c04f79b6c8918881706dbd = () -> przeliczony_przepis_70804b93c0c04f79b6c8918881706dbd;

                if (przeliczony_przepis_70804b93c0c04f79b6c8918881706dbd.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_70804b93c0c04f79b6c8918881706dbd)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_70804b93c0c04f79b6c8918881706dbd)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }



                // Przyklad implementacji Kuchni
                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 1169); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_b5eb94ae2f6c4b2dad0f0a625e759885_skladniki = Set.of(
                        new Skladnik(Produkt.WODA, 1926),
                        new Skladnik(Produkt.MAKA_ZIEMNIACZANA, 2019),
                        new Skladnik(Produkt.MARGARYNA, 1372),
                        new Skladnik(Produkt.OLEJ_ROSLINNY, 2274),
                        new Skladnik(Produkt.CUKIER, 1568),
                        new Skladnik(Produkt.MAKA_RAZOWA, 2715),
                        new Skladnik(Produkt.PROSZEK_DO_PIECZENIA, 94)
                );
                Przepis przepis_b5eb94ae2f6c4b2dad0f0a625e759885 = () -> przepis_b5eb94ae2f6c4b2dad0f0a625e759885_skladniki;




                Set<Skladnik> przeliczony_przepis_b5eb94ae2f6c4b2dad0f0a625e759885 = kuchnia.przeliczPrzepis(przepis_b5eb94ae2f6c4b2dad0f0a625e759885);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_b5eb94ae2f6c4b2dad0f0a625e759885);


                Przepis nowyPrzepis_przepis_b5eb94ae2f6c4b2dad0f0a625e759885 = () -> przeliczony_przepis_b5eb94ae2f6c4b2dad0f0a625e759885;

                if (przeliczony_przepis_b5eb94ae2f6c4b2dad0f0a625e759885.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_b5eb94ae2f6c4b2dad0f0a625e759885)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_b5eb94ae2f6c4b2dad0f0a625e759885)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }



                // Przyklad implementacji Kuchni
                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 1816); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_be671748350d4456be3624c6ac3fd87c_skladniki = Set.of(
                        new Skladnik(Produkt.MLEKO, 2183),
                        new Skladnik(Produkt.WODA, 764),
                        new Skladnik(Produkt.OLEJ_ROSLINNY, 645),
                        new Skladnik(Produkt.TWAROG, 510),
                        new Skladnik(Produkt.RODZYNKI, 1398)
                );
                Przepis przepis_be671748350d4456be3624c6ac3fd87c = () -> przepis_be671748350d4456be3624c6ac3fd87c_skladniki;




                Set<Skladnik> przeliczony_przepis_be671748350d4456be3624c6ac3fd87c = kuchnia.przeliczPrzepis(przepis_be671748350d4456be3624c6ac3fd87c);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_be671748350d4456be3624c6ac3fd87c);


                Przepis nowyPrzepis_przepis_be671748350d4456be3624c6ac3fd87c = () -> przeliczony_przepis_be671748350d4456be3624c6ac3fd87c;

                if (przeliczony_przepis_be671748350d4456be3624c6ac3fd87c.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_be671748350d4456be3624c6ac3fd87c)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_be671748350d4456be3624c6ac3fd87c)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }



                // Przyklad implementacji Kuchni
                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 703); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_ddd5a7f5dfcd48d0a5ebb97156de4b12_skladniki = Set.of(
                        new Skladnik(Produkt.WODA, 1395),
                        new Skladnik(Produkt.CUKIER_PUDER, 3),
                        new Skladnik(Produkt.DROZDZE, 2958),
                        new Skladnik(Produkt.RODZYNKI, 607),
                        new Skladnik(Produkt.MASLO, 1580),
                        new Skladnik(Produkt.OLEJ_ROSLINNY, 837),
                        new Skladnik(Produkt.CUKIER_WANILIOWY, 1656),
                        new Skladnik(Produkt.MLEKO, 2405),
                        new Skladnik(Produkt.MARGARYNA, 378),
                        new Skladnik(Produkt.PROSZEK_DO_PIECZENIA, 1521)
                );
                Przepis przepis_ddd5a7f5dfcd48d0a5ebb97156de4b12 = () -> przepis_ddd5a7f5dfcd48d0a5ebb97156de4b12_skladniki;




                Set<Skladnik> przeliczony_przepis_ddd5a7f5dfcd48d0a5ebb97156de4b12 = kuchnia.przeliczPrzepis(przepis_ddd5a7f5dfcd48d0a5ebb97156de4b12);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_ddd5a7f5dfcd48d0a5ebb97156de4b12);


                Przepis nowyPrzepis_przepis_ddd5a7f5dfcd48d0a5ebb97156de4b12 = () -> przeliczony_przepis_ddd5a7f5dfcd48d0a5ebb97156de4b12;

                if (przeliczony_przepis_ddd5a7f5dfcd48d0a5ebb97156de4b12.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_ddd5a7f5dfcd48d0a5ebb97156de4b12)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_ddd5a7f5dfcd48d0a5ebb97156de4b12)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }



                // Przyklad implementacji Kuchni
                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 1246); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_722617b9347c422abc4cb396b5f76c35_skladniki = Set.of(
                        new Skladnik(Produkt.CUKIER, 1960),
                        new Skladnik(Produkt.MAKA_ZIEMNIACZANA, 735),
                        new Skladnik(Produkt.CUKIER_WANILIOWY, 463),
                        new Skladnik(Produkt.TWAROG, 829),
                        new Skladnik(Produkt.OLEJ_ROSLINNY, 2322),
                        new Skladnik(Produkt.WODA, 1827),
                        new Skladnik(Produkt.CUKIER_PUDER, 835),
                        new Skladnik(Produkt.MAKA_RAZOWA, 376),
                        new Skladnik(Produkt.DROZDZE, 2575),
                        new Skladnik(Produkt.PROSZEK_DO_PIECZENIA, 806)
                );
                Przepis przepis_722617b9347c422abc4cb396b5f76c35 = () -> przepis_722617b9347c422abc4cb396b5f76c35_skladniki;




                Set<Skladnik> przeliczony_przepis_722617b9347c422abc4cb396b5f76c35 = kuchnia.przeliczPrzepis(przepis_722617b9347c422abc4cb396b5f76c35);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_722617b9347c422abc4cb396b5f76c35);


                Przepis nowyPrzepis_przepis_722617b9347c422abc4cb396b5f76c35 = () -> przeliczony_przepis_722617b9347c422abc4cb396b5f76c35;

                if (przeliczony_przepis_722617b9347c422abc4cb396b5f76c35.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_722617b9347c422abc4cb396b5f76c35)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_722617b9347c422abc4cb396b5f76c35)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }




            }

            public static void grzanie2()
            {
                // Wygenerowany kod
                Kuchnia kuchnia = new EKuchnia();
                // Przyklad implementacji Kuchni
                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 12); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_f163cf9371524deda281c334c2b9eda7_skladniki = Set.of(
                        new Skladnik(Produkt.TWAROG, 1529),
                        new Skladnik(Produkt.DROZDZE, 1037),
                        new Skladnik(Produkt.CUKIER_WANILIOWY, 1349),
                        new Skladnik(Produkt.OLEJ_ROSLINNY, 1634),
                        new Skladnik(Produkt.RODZYNKI, 1048),
                        new Skladnik(Produkt.MAKA_ZIEMNIACZANA, 62),
                        new Skladnik(Produkt.PROSZEK_DO_PIECZENIA, 335),
                        new Skladnik(Produkt.MARGARYNA, 2072),
                        new Skladnik(Produkt.CUKIER, 963),
                        new Skladnik(Produkt.WODA, 2946)
                );
                Przepis przepis_f163cf9371524deda281c334c2b9eda7 = () -> przepis_f163cf9371524deda281c334c2b9eda7_skladniki;




                Set<Skladnik> przeliczony_przepis_f163cf9371524deda281c334c2b9eda7 = kuchnia.przeliczPrzepis(przepis_f163cf9371524deda281c334c2b9eda7);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_f163cf9371524deda281c334c2b9eda7);


                Przepis nowyPrzepis_przepis_f163cf9371524deda281c334c2b9eda7 = () -> przeliczony_przepis_f163cf9371524deda281c334c2b9eda7;

                if (przeliczony_przepis_f163cf9371524deda281c334c2b9eda7.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_f163cf9371524deda281c334c2b9eda7)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_f163cf9371524deda281c334c2b9eda7)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }



                // Przyklad implementacji Kuchni
                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 73); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_bff3bb724db341f590aef94698ae6b23_skladniki = Set.of(
                        new Skladnik(Produkt.RODZYNKI, 2700),
                        new Skladnik(Produkt.MLEKO, 1872),
                        new Skladnik(Produkt.MARGARYNA, 397),
                        new Skladnik(Produkt.WODA, 2272),
                        new Skladnik(Produkt.SOL, 2991),
                        new Skladnik(Produkt.MAKA_ZIEMNIACZANA, 2917),
                        new Skladnik(Produkt.MASLO, 1519),
                        new Skladnik(Produkt.DROZDZE, 1712),
                        new Skladnik(Produkt.CUKIER_PUDER, 2967),
                        new Skladnik(Produkt.MAKA_PSZENNA, 1886)
                );
                Przepis przepis_bff3bb724db341f590aef94698ae6b23 = () -> przepis_bff3bb724db341f590aef94698ae6b23_skladniki;




                Set<Skladnik> przeliczony_przepis_bff3bb724db341f590aef94698ae6b23 = kuchnia.przeliczPrzepis(przepis_bff3bb724db341f590aef94698ae6b23);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_bff3bb724db341f590aef94698ae6b23);


                Przepis nowyPrzepis_przepis_bff3bb724db341f590aef94698ae6b23 = () -> przeliczony_przepis_bff3bb724db341f590aef94698ae6b23;

                if (przeliczony_przepis_bff3bb724db341f590aef94698ae6b23.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_bff3bb724db341f590aef94698ae6b23)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_bff3bb724db341f590aef94698ae6b23)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }



                // Przyklad implementacji Kuchni
                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 57); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_ac506c32198a468dbef5fa12fce035ab_skladniki = Set.of(
                        new Skladnik(Produkt.CUKIER_PUDER, 660),
                        new Skladnik(Produkt.CUKIER, 2008),
                        new Skladnik(Produkt.PROSZEK_DO_PIECZENIA, 2744),
                        new Skladnik(Produkt.MLEKO, 1483),
                        new Skladnik(Produkt.WODA, 1291),
                        new Skladnik(Produkt.MASLO, 2104)
                );
                Przepis przepis_ac506c32198a468dbef5fa12fce035ab = () -> przepis_ac506c32198a468dbef5fa12fce035ab_skladniki;




                Set<Skladnik> przeliczony_przepis_ac506c32198a468dbef5fa12fce035ab = kuchnia.przeliczPrzepis(przepis_ac506c32198a468dbef5fa12fce035ab);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_ac506c32198a468dbef5fa12fce035ab);


                Przepis nowyPrzepis_przepis_ac506c32198a468dbef5fa12fce035ab = () -> przeliczony_przepis_ac506c32198a468dbef5fa12fce035ab;

                if (przeliczony_przepis_ac506c32198a468dbef5fa12fce035ab.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_ac506c32198a468dbef5fa12fce035ab)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_ac506c32198a468dbef5fa12fce035ab)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }



                // Przyklad implementacji Kuchni
                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 8); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_d31eae3ac64240a09446144fb28add96_skladniki = Set.of(
                        new Skladnik(Produkt.MAKA_PSZENNA, 1726),
                        new Skladnik(Produkt.RODZYNKI, 1843),
                        new Skladnik(Produkt.TWAROG, 1177),
                        new Skladnik(Produkt.CUKIER_WANILIOWY, 1672),
                        new Skladnik(Produkt.MAKA_ZIEMNIACZANA, 2406),
                        new Skladnik(Produkt.PROSZEK_DO_PIECZENIA, 144),
                        new Skladnik(Produkt.CUKIER, 276),
                        new Skladnik(Produkt.OLEJ_ROSLINNY, 2274)
                );
                Przepis przepis_d31eae3ac64240a09446144fb28add96 = () -> przepis_d31eae3ac64240a09446144fb28add96_skladniki;




                Set<Skladnik> przeliczony_przepis_d31eae3ac64240a09446144fb28add96 = kuchnia.przeliczPrzepis(przepis_d31eae3ac64240a09446144fb28add96);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_d31eae3ac64240a09446144fb28add96);


                Przepis nowyPrzepis_przepis_d31eae3ac64240a09446144fb28add96 = () -> przeliczony_przepis_d31eae3ac64240a09446144fb28add96;

                if (przeliczony_przepis_d31eae3ac64240a09446144fb28add96.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_d31eae3ac64240a09446144fb28add96)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_d31eae3ac64240a09446144fb28add96)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }



                // Przyklad implementacji Kuchni
                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 40); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_1af8782bb65147a2b6bcc882838a9a8d_skladniki = Set.of(
                        new Skladnik(Produkt.WODA, 418),
                        new Skladnik(Produkt.MAKA_RAZOWA, 1442),
                        new Skladnik(Produkt.DROZDZE, 1106),
                        new Skladnik(Produkt.MASLO, 2059),
                        new Skladnik(Produkt.SOL, 261),
                        new Skladnik(Produkt.MAKA_ZIEMNIACZANA, 2650)
                );
                Przepis przepis_1af8782bb65147a2b6bcc882838a9a8d = () -> przepis_1af8782bb65147a2b6bcc882838a9a8d_skladniki;




                Set<Skladnik> przeliczony_przepis_1af8782bb65147a2b6bcc882838a9a8d = kuchnia.przeliczPrzepis(przepis_1af8782bb65147a2b6bcc882838a9a8d);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_1af8782bb65147a2b6bcc882838a9a8d);


                Przepis nowyPrzepis_przepis_1af8782bb65147a2b6bcc882838a9a8d = () -> przeliczony_przepis_1af8782bb65147a2b6bcc882838a9a8d;

                if (przeliczony_przepis_1af8782bb65147a2b6bcc882838a9a8d.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_1af8782bb65147a2b6bcc882838a9a8d)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_1af8782bb65147a2b6bcc882838a9a8d)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }



                // Przyklad implementacji Kuchni
                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 60); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_f8f673f465b24f1f8b8889e0fae16fd3_skladniki = Set.of(
                        new Skladnik(Produkt.PROSZEK_DO_PIECZENIA, 680),
                        new Skladnik(Produkt.SOL, 2),
                        new Skladnik(Produkt.WODA, 43),
                        new Skladnik(Produkt.MLEKO, 466),
                        new Skladnik(Produkt.RODZYNKI, 1113),
                        new Skladnik(Produkt.TWAROG, 407)
                );
                Przepis przepis_f8f673f465b24f1f8b8889e0fae16fd3 = () -> przepis_f8f673f465b24f1f8b8889e0fae16fd3_skladniki;




                Set<Skladnik> przeliczony_przepis_f8f673f465b24f1f8b8889e0fae16fd3 = kuchnia.przeliczPrzepis(przepis_f8f673f465b24f1f8b8889e0fae16fd3);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_f8f673f465b24f1f8b8889e0fae16fd3);


                Przepis nowyPrzepis_przepis_f8f673f465b24f1f8b8889e0fae16fd3 = () -> przeliczony_przepis_f8f673f465b24f1f8b8889e0fae16fd3;

                if (przeliczony_przepis_f8f673f465b24f1f8b8889e0fae16fd3.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_f8f673f465b24f1f8b8889e0fae16fd3)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_f8f673f465b24f1f8b8889e0fae16fd3)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }



                // Przyklad implementacji Kuchni
                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 80); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_cf0c626bbf5148138e1857b790a43400_skladniki = Set.of(
                        new Skladnik(Produkt.PROSZEK_DO_PIECZENIA, 887),
                        new Skladnik(Produkt.MAKA_ZIEMNIACZANA, 2407),
                        new Skladnik(Produkt.OLEJ_ROSLINNY, 2397),
                        new Skladnik(Produkt.CUKIER_WANILIOWY, 1596),
                        new Skladnik(Produkt.DROZDZE, 2698),
                        new Skladnik(Produkt.MAKA_RAZOWA, 1099)
                );
                Przepis przepis_cf0c626bbf5148138e1857b790a43400 = () -> przepis_cf0c626bbf5148138e1857b790a43400_skladniki;




                Set<Skladnik> przeliczony_przepis_cf0c626bbf5148138e1857b790a43400 = kuchnia.przeliczPrzepis(przepis_cf0c626bbf5148138e1857b790a43400);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_cf0c626bbf5148138e1857b790a43400);


                Przepis nowyPrzepis_przepis_cf0c626bbf5148138e1857b790a43400 = () -> przeliczony_przepis_cf0c626bbf5148138e1857b790a43400;

                if (przeliczony_przepis_cf0c626bbf5148138e1857b790a43400.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_cf0c626bbf5148138e1857b790a43400)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_cf0c626bbf5148138e1857b790a43400)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }



                // Przyklad implementacji Kuchni
                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 6); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_66b53d1395fa4108a55ec196e47ffe7b_skladniki = Set.of(
                        new Skladnik(Produkt.CUKIER, 226),
                        new Skladnik(Produkt.CUKIER_PUDER, 733),
                        new Skladnik(Produkt.MARGARYNA, 1000),
                        new Skladnik(Produkt.MAKA_ZIEMNIACZANA, 2035),
                        new Skladnik(Produkt.SOL, 2019),
                        new Skladnik(Produkt.DROZDZE, 1623)
                );
                Przepis przepis_66b53d1395fa4108a55ec196e47ffe7b = () -> przepis_66b53d1395fa4108a55ec196e47ffe7b_skladniki;




                Set<Skladnik> przeliczony_przepis_66b53d1395fa4108a55ec196e47ffe7b = kuchnia.przeliczPrzepis(przepis_66b53d1395fa4108a55ec196e47ffe7b);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_66b53d1395fa4108a55ec196e47ffe7b);


                Przepis nowyPrzepis_przepis_66b53d1395fa4108a55ec196e47ffe7b = () -> przeliczony_przepis_66b53d1395fa4108a55ec196e47ffe7b;

                if (przeliczony_przepis_66b53d1395fa4108a55ec196e47ffe7b.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_66b53d1395fa4108a55ec196e47ffe7b)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_66b53d1395fa4108a55ec196e47ffe7b)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }



                // Przyklad implementacji Kuchni
                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 45); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_e071e9f71fc7471693d6f685eb657702_skladniki = Set.of(
                        new Skladnik(Produkt.MARGARYNA, 1683),
                        new Skladnik(Produkt.MAKA_RAZOWA, 1970),
                        new Skladnik(Produkt.TWAROG, 2437),
                        new Skladnik(Produkt.SOL, 2330),
                        new Skladnik(Produkt.OLEJ_ROSLINNY, 227),
                        new Skladnik(Produkt.MAKA_PSZENNA, 1469)
                );
                Przepis przepis_e071e9f71fc7471693d6f685eb657702 = () -> przepis_e071e9f71fc7471693d6f685eb657702_skladniki;




                Set<Skladnik> przeliczony_przepis_e071e9f71fc7471693d6f685eb657702 = kuchnia.przeliczPrzepis(przepis_e071e9f71fc7471693d6f685eb657702);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_e071e9f71fc7471693d6f685eb657702);


                Przepis nowyPrzepis_przepis_e071e9f71fc7471693d6f685eb657702 = () -> przeliczony_przepis_e071e9f71fc7471693d6f685eb657702;

                if (przeliczony_przepis_e071e9f71fc7471693d6f685eb657702.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_e071e9f71fc7471693d6f685eb657702)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_e071e9f71fc7471693d6f685eb657702)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }



                // Przyklad implementacji Kuchni
                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 0); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_e71b552b04a74a2389a1d889b94465a0_skladniki = Set.of(
                        new Skladnik(Produkt.TWAROG, 1649),
                        new Skladnik(Produkt.MARGARYNA, 131),
                        new Skladnik(Produkt.MAKA_RAZOWA, 720),
                        new Skladnik(Produkt.WODA, 2514),
                        new Skladnik(Produkt.OLEJ_ROSLINNY, 398),
                        new Skladnik(Produkt.CUKIER_WANILIOWY, 778),
                        new Skladnik(Produkt.PROSZEK_DO_PIECZENIA, 1001)
                );
                Przepis przepis_e71b552b04a74a2389a1d889b94465a0 = () -> przepis_e71b552b04a74a2389a1d889b94465a0_skladniki;




                Set<Skladnik> przeliczony_przepis_e71b552b04a74a2389a1d889b94465a0 = kuchnia.przeliczPrzepis(przepis_e71b552b04a74a2389a1d889b94465a0);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_e71b552b04a74a2389a1d889b94465a0);


                Przepis nowyPrzepis_przepis_e71b552b04a74a2389a1d889b94465a0 = () -> przeliczony_przepis_e71b552b04a74a2389a1d889b94465a0;

                if (przeliczony_przepis_e71b552b04a74a2389a1d889b94465a0.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_e71b552b04a74a2389a1d889b94465a0)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_e71b552b04a74a2389a1d889b94465a0)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }



                // Przyklad implementacji Kuchni
                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 24); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_bef39b546b8a4e2fbac4c2d1bc6ba252_skladniki = Set.of(
                        new Skladnik(Produkt.MAKA_RAZOWA, 1964),
                        new Skladnik(Produkt.DROZDZE, 2657),
                        new Skladnik(Produkt.OLEJ_ROSLINNY, 1555),
                        new Skladnik(Produkt.TWAROG, 726),
                        new Skladnik(Produkt.CUKIER, 410)
                );
                Przepis przepis_bef39b546b8a4e2fbac4c2d1bc6ba252 = () -> przepis_bef39b546b8a4e2fbac4c2d1bc6ba252_skladniki;




                Set<Skladnik> przeliczony_przepis_bef39b546b8a4e2fbac4c2d1bc6ba252 = kuchnia.przeliczPrzepis(przepis_bef39b546b8a4e2fbac4c2d1bc6ba252);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_bef39b546b8a4e2fbac4c2d1bc6ba252);


                Przepis nowyPrzepis_przepis_bef39b546b8a4e2fbac4c2d1bc6ba252 = () -> przeliczony_przepis_bef39b546b8a4e2fbac4c2d1bc6ba252;

                if (przeliczony_przepis_bef39b546b8a4e2fbac4c2d1bc6ba252.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_bef39b546b8a4e2fbac4c2d1bc6ba252)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_bef39b546b8a4e2fbac4c2d1bc6ba252)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }



                // Przyklad implementacji Kuchni
                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 97); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_e61b9286fdfe43dbbb79aaf343efb22b_skladniki = Set.of(
                        new Skladnik(Produkt.TWAROG, 2376),
                        new Skladnik(Produkt.PROSZEK_DO_PIECZENIA, 1868),
                        new Skladnik(Produkt.MAKA_RAZOWA, 2586),
                        new Skladnik(Produkt.RODZYNKI, 2308),
                        new Skladnik(Produkt.MARGARYNA, 842)
                );
                Przepis przepis_e61b9286fdfe43dbbb79aaf343efb22b = () -> przepis_e61b9286fdfe43dbbb79aaf343efb22b_skladniki;




                Set<Skladnik> przeliczony_przepis_e61b9286fdfe43dbbb79aaf343efb22b = kuchnia.przeliczPrzepis(przepis_e61b9286fdfe43dbbb79aaf343efb22b);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_e61b9286fdfe43dbbb79aaf343efb22b);


                Przepis nowyPrzepis_przepis_e61b9286fdfe43dbbb79aaf343efb22b = () -> przeliczony_przepis_e61b9286fdfe43dbbb79aaf343efb22b;

                if (przeliczony_przepis_e61b9286fdfe43dbbb79aaf343efb22b.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_e61b9286fdfe43dbbb79aaf343efb22b)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_e61b9286fdfe43dbbb79aaf343efb22b)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }



                // Przyklad implementacji Kuchni
                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 24); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_a2ff5738909f44a296c28b96451e7a2e_skladniki = Set.of(
                        new Skladnik(Produkt.MARGARYNA, 2749),
                        new Skladnik(Produkt.RODZYNKI, 1238),
                        new Skladnik(Produkt.MASLO, 2571),
                        new Skladnik(Produkt.PROSZEK_DO_PIECZENIA, 2437),
                        new Skladnik(Produkt.MLEKO, 1406),
                        new Skladnik(Produkt.MAKA_ZIEMNIACZANA, 523),
                        new Skladnik(Produkt.CUKIER_WANILIOWY, 125),
                        new Skladnik(Produkt.MAKA_RAZOWA, 164),
                        new Skladnik(Produkt.DROZDZE, 1725),
                        new Skladnik(Produkt.MAKA_PSZENNA, 1781)
                );
                Przepis przepis_a2ff5738909f44a296c28b96451e7a2e = () -> przepis_a2ff5738909f44a296c28b96451e7a2e_skladniki;




                Set<Skladnik> przeliczony_przepis_a2ff5738909f44a296c28b96451e7a2e = kuchnia.przeliczPrzepis(przepis_a2ff5738909f44a296c28b96451e7a2e);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_a2ff5738909f44a296c28b96451e7a2e);


                Przepis nowyPrzepis_przepis_a2ff5738909f44a296c28b96451e7a2e = () -> przeliczony_przepis_a2ff5738909f44a296c28b96451e7a2e;

                if (przeliczony_przepis_a2ff5738909f44a296c28b96451e7a2e.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_a2ff5738909f44a296c28b96451e7a2e)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_a2ff5738909f44a296c28b96451e7a2e)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }



                // Przyklad implementacji Kuchni
                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 73); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_233fec938c2e4236805cbb0b0b134e54_skladniki = Set.of(
                        new Skladnik(Produkt.CUKIER, 923),
                        new Skladnik(Produkt.WODA, 2741),
                        new Skladnik(Produkt.RODZYNKI, 1140),
                        new Skladnik(Produkt.PROSZEK_DO_PIECZENIA, 2152),
                        new Skladnik(Produkt.MARGARYNA, 1164),
                        new Skladnik(Produkt.SOL, 1105),
                        new Skladnik(Produkt.MASLO, 115),
                        new Skladnik(Produkt.CUKIER_WANILIOWY, 1635),
                        new Skladnik(Produkt.MLEKO, 486),
                        new Skladnik(Produkt.MAKA_ZIEMNIACZANA, 2849)
                );
                Przepis przepis_233fec938c2e4236805cbb0b0b134e54 = () -> przepis_233fec938c2e4236805cbb0b0b134e54_skladniki;




                Set<Skladnik> przeliczony_przepis_233fec938c2e4236805cbb0b0b134e54 = kuchnia.przeliczPrzepis(przepis_233fec938c2e4236805cbb0b0b134e54);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_233fec938c2e4236805cbb0b0b134e54);


                Przepis nowyPrzepis_przepis_233fec938c2e4236805cbb0b0b134e54 = () -> przeliczony_przepis_233fec938c2e4236805cbb0b0b134e54;

                if (przeliczony_przepis_233fec938c2e4236805cbb0b0b134e54.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_233fec938c2e4236805cbb0b0b134e54)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_233fec938c2e4236805cbb0b0b134e54)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }



                // Przyklad implementacji Kuchni
                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 98); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_e128cf09ddb344ad8634d8d5bb2851f2_skladniki = Set.of(
                        new Skladnik(Produkt.DROZDZE, 550),
                        new Skladnik(Produkt.PROSZEK_DO_PIECZENIA, 2911),
                        new Skladnik(Produkt.TWAROG, 132),
                        new Skladnik(Produkt.RODZYNKI, 836),
                        new Skladnik(Produkt.OLEJ_ROSLINNY, 1441),
                        new Skladnik(Produkt.MLEKO, 2446),
                        new Skladnik(Produkt.MAKA_PSZENNA, 1804),
                        new Skladnik(Produkt.CUKIER, 281)
                );
                Przepis przepis_e128cf09ddb344ad8634d8d5bb2851f2 = () -> przepis_e128cf09ddb344ad8634d8d5bb2851f2_skladniki;




                Set<Skladnik> przeliczony_przepis_e128cf09ddb344ad8634d8d5bb2851f2 = kuchnia.przeliczPrzepis(przepis_e128cf09ddb344ad8634d8d5bb2851f2);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_e128cf09ddb344ad8634d8d5bb2851f2);


                Przepis nowyPrzepis_przepis_e128cf09ddb344ad8634d8d5bb2851f2 = () -> przeliczony_przepis_e128cf09ddb344ad8634d8d5bb2851f2;

                if (przeliczony_przepis_e128cf09ddb344ad8634d8d5bb2851f2.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_e128cf09ddb344ad8634d8d5bb2851f2)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_e128cf09ddb344ad8634d8d5bb2851f2)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }



                // Przyklad implementacji Kuchni
                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 73); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_b6d40ed8b8ca4cc69d94b8159effd3f0_skladniki = Set.of(
                        new Skladnik(Produkt.MASLO, 1511),
                        new Skladnik(Produkt.MAKA_PSZENNA, 562),
                        new Skladnik(Produkt.CUKIER_WANILIOWY, 1682),
                        new Skladnik(Produkt.WODA, 1964),
                        new Skladnik(Produkt.MAKA_RAZOWA, 2759),
                        new Skladnik(Produkt.TWAROG, 669)
                );
                Przepis przepis_b6d40ed8b8ca4cc69d94b8159effd3f0 = () -> przepis_b6d40ed8b8ca4cc69d94b8159effd3f0_skladniki;




                Set<Skladnik> przeliczony_przepis_b6d40ed8b8ca4cc69d94b8159effd3f0 = kuchnia.przeliczPrzepis(przepis_b6d40ed8b8ca4cc69d94b8159effd3f0);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_b6d40ed8b8ca4cc69d94b8159effd3f0);


                Przepis nowyPrzepis_przepis_b6d40ed8b8ca4cc69d94b8159effd3f0 = () -> przeliczony_przepis_b6d40ed8b8ca4cc69d94b8159effd3f0;

                if (przeliczony_przepis_b6d40ed8b8ca4cc69d94b8159effd3f0.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_b6d40ed8b8ca4cc69d94b8159effd3f0)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_b6d40ed8b8ca4cc69d94b8159effd3f0)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }



                // Przyklad implementacji Kuchni
                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 55); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_9105a53250d84529907348a17e2bfadc_skladniki = Set.of(
                        new Skladnik(Produkt.OLEJ_ROSLINNY, 2631),
                        new Skladnik(Produkt.CUKIER, 2365),
                        new Skladnik(Produkt.MLEKO, 2377),
                        new Skladnik(Produkt.TWAROG, 2566),
                        new Skladnik(Produkt.CUKIER_WANILIOWY, 424),
                        new Skladnik(Produkt.MARGARYNA, 1994),
                        new Skladnik(Produkt.PROSZEK_DO_PIECZENIA, 446),
                        new Skladnik(Produkt.SOL, 2461)
                );
                Przepis przepis_9105a53250d84529907348a17e2bfadc = () -> przepis_9105a53250d84529907348a17e2bfadc_skladniki;




                Set<Skladnik> przeliczony_przepis_9105a53250d84529907348a17e2bfadc = kuchnia.przeliczPrzepis(przepis_9105a53250d84529907348a17e2bfadc);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_9105a53250d84529907348a17e2bfadc);


                Przepis nowyPrzepis_przepis_9105a53250d84529907348a17e2bfadc = () -> przeliczony_przepis_9105a53250d84529907348a17e2bfadc;

                if (przeliczony_przepis_9105a53250d84529907348a17e2bfadc.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_9105a53250d84529907348a17e2bfadc)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_9105a53250d84529907348a17e2bfadc)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }



                // Przyklad implementacji Kuchni
                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 39); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_bd3e0c47582c43dfa18f58037dc76681_skladniki = Set.of(
                        new Skladnik(Produkt.PROSZEK_DO_PIECZENIA, 1935),
                        new Skladnik(Produkt.WODA, 957),
                        new Skladnik(Produkt.RODZYNKI, 2211),
                        new Skladnik(Produkt.MASLO, 450),
                        new Skladnik(Produkt.TWAROG, 15)
                );
                Przepis przepis_bd3e0c47582c43dfa18f58037dc76681 = () -> przepis_bd3e0c47582c43dfa18f58037dc76681_skladniki;




                Set<Skladnik> przeliczony_przepis_bd3e0c47582c43dfa18f58037dc76681 = kuchnia.przeliczPrzepis(przepis_bd3e0c47582c43dfa18f58037dc76681);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_bd3e0c47582c43dfa18f58037dc76681);


                Przepis nowyPrzepis_przepis_bd3e0c47582c43dfa18f58037dc76681 = () -> przeliczony_przepis_bd3e0c47582c43dfa18f58037dc76681;

                if (przeliczony_przepis_bd3e0c47582c43dfa18f58037dc76681.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_bd3e0c47582c43dfa18f58037dc76681)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_bd3e0c47582c43dfa18f58037dc76681)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }



                // Przyklad implementacji Kuchni
                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 29); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_165fd16bf0a64045993c7740c86fd4a4_skladniki = Set.of(
                        new Skladnik(Produkt.TWAROG, 1821),
                        new Skladnik(Produkt.PROSZEK_DO_PIECZENIA, 962),
                        new Skladnik(Produkt.MLEKO, 94),
                        new Skladnik(Produkt.MARGARYNA, 1860),
                        new Skladnik(Produkt.WODA, 1500),
                        new Skladnik(Produkt.MAKA_RAZOWA, 1466),
                        new Skladnik(Produkt.MAKA_PSZENNA, 1299),
                        new Skladnik(Produkt.CUKIER_PUDER, 2561),
                        new Skladnik(Produkt.MASLO, 1261),
                        new Skladnik(Produkt.DROZDZE, 1118)
                );
                Przepis przepis_165fd16bf0a64045993c7740c86fd4a4 = () -> przepis_165fd16bf0a64045993c7740c86fd4a4_skladniki;




                Set<Skladnik> przeliczony_przepis_165fd16bf0a64045993c7740c86fd4a4 = kuchnia.przeliczPrzepis(przepis_165fd16bf0a64045993c7740c86fd4a4);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_165fd16bf0a64045993c7740c86fd4a4);


                Przepis nowyPrzepis_przepis_165fd16bf0a64045993c7740c86fd4a4 = () -> przeliczony_przepis_165fd16bf0a64045993c7740c86fd4a4;

                if (przeliczony_przepis_165fd16bf0a64045993c7740c86fd4a4.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_165fd16bf0a64045993c7740c86fd4a4)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_165fd16bf0a64045993c7740c86fd4a4)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }



                // Przyklad implementacji Kuchni
                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 45); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_d6fc0aa872cf466d83b0481f4c096eeb_skladniki = Set.of(
                        new Skladnik(Produkt.MASLO, 2831),
                        new Skladnik(Produkt.MLEKO, 2681),
                        new Skladnik(Produkt.TWAROG, 548),
                        new Skladnik(Produkt.PROSZEK_DO_PIECZENIA, 909),
                        new Skladnik(Produkt.OLEJ_ROSLINNY, 1020),
                        new Skladnik(Produkt.SOL, 1935),
                        new Skladnik(Produkt.DROZDZE, 295),
                        new Skladnik(Produkt.MAKA_ZIEMNIACZANA, 608),
                        new Skladnik(Produkt.CUKIER_WANILIOWY, 1678),
                        new Skladnik(Produkt.CUKIER, 1963)
                );
                Przepis przepis_d6fc0aa872cf466d83b0481f4c096eeb = () -> przepis_d6fc0aa872cf466d83b0481f4c096eeb_skladniki;




                Set<Skladnik> przeliczony_przepis_d6fc0aa872cf466d83b0481f4c096eeb = kuchnia.przeliczPrzepis(przepis_d6fc0aa872cf466d83b0481f4c096eeb);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_d6fc0aa872cf466d83b0481f4c096eeb);


                Przepis nowyPrzepis_przepis_d6fc0aa872cf466d83b0481f4c096eeb = () -> przeliczony_przepis_d6fc0aa872cf466d83b0481f4c096eeb;

                if (przeliczony_przepis_d6fc0aa872cf466d83b0481f4c096eeb.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_d6fc0aa872cf466d83b0481f4c096eeb)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_d6fc0aa872cf466d83b0481f4c096eeb)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }



                // Przyklad implementacji Kuchni
                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 58); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_fca471baf4e147dab4eb3b823ab883f0_skladniki = Set.of(
                        new Skladnik(Produkt.MAKA_ZIEMNIACZANA, 779),
                        new Skladnik(Produkt.MARGARYNA, 2886),
                        new Skladnik(Produkt.CUKIER_PUDER, 1815),
                        new Skladnik(Produkt.CUKIER_WANILIOWY, 185),
                        new Skladnik(Produkt.RODZYNKI, 1446),
                        new Skladnik(Produkt.TWAROG, 11),
                        new Skladnik(Produkt.CUKIER, 380),
                        new Skladnik(Produkt.OLEJ_ROSLINNY, 1357)
                );
                Przepis przepis_fca471baf4e147dab4eb3b823ab883f0 = () -> przepis_fca471baf4e147dab4eb3b823ab883f0_skladniki;




                Set<Skladnik> przeliczony_przepis_fca471baf4e147dab4eb3b823ab883f0 = kuchnia.przeliczPrzepis(przepis_fca471baf4e147dab4eb3b823ab883f0);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_fca471baf4e147dab4eb3b823ab883f0);


                Przepis nowyPrzepis_przepis_fca471baf4e147dab4eb3b823ab883f0 = () -> przeliczony_przepis_fca471baf4e147dab4eb3b823ab883f0;

                if (przeliczony_przepis_fca471baf4e147dab4eb3b823ab883f0.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_fca471baf4e147dab4eb3b823ab883f0)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_fca471baf4e147dab4eb3b823ab883f0)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }



                // Przyklad implementacji Kuchni
                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 76); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_0e15820934ee4050a21ee7228d983890_skladniki = Set.of(
                        new Skladnik(Produkt.CUKIER, 478),
                        new Skladnik(Produkt.MAKA_PSZENNA, 1475),
                        new Skladnik(Produkt.SOL, 1179),
                        new Skladnik(Produkt.MAKA_ZIEMNIACZANA, 2457),
                        new Skladnik(Produkt.CUKIER_WANILIOWY, 1914),
                        new Skladnik(Produkt.MASLO, 28),
                        new Skladnik(Produkt.PROSZEK_DO_PIECZENIA, 350),
                        new Skladnik(Produkt.MLEKO, 2298)
                );
                Przepis przepis_0e15820934ee4050a21ee7228d983890 = () -> przepis_0e15820934ee4050a21ee7228d983890_skladniki;




                Set<Skladnik> przeliczony_przepis_0e15820934ee4050a21ee7228d983890 = kuchnia.przeliczPrzepis(przepis_0e15820934ee4050a21ee7228d983890);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_0e15820934ee4050a21ee7228d983890);


                Przepis nowyPrzepis_przepis_0e15820934ee4050a21ee7228d983890 = () -> przeliczony_przepis_0e15820934ee4050a21ee7228d983890;

                if (przeliczony_przepis_0e15820934ee4050a21ee7228d983890.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_0e15820934ee4050a21ee7228d983890)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_0e15820934ee4050a21ee7228d983890)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }



                // Przyklad implementacji Kuchni
                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 20); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_fc3e0a4d887b4d148ade66f805e9af13_skladniki = Set.of(
                        new Skladnik(Produkt.MAKA_ZIEMNIACZANA, 841),
                        new Skladnik(Produkt.MASLO, 240),
                        new Skladnik(Produkt.MAKA_RAZOWA, 1362),
                        new Skladnik(Produkt.DROZDZE, 2043),
                        new Skladnik(Produkt.PROSZEK_DO_PIECZENIA, 2941)
                );
                Przepis przepis_fc3e0a4d887b4d148ade66f805e9af13 = () -> przepis_fc3e0a4d887b4d148ade66f805e9af13_skladniki;




                Set<Skladnik> przeliczony_przepis_fc3e0a4d887b4d148ade66f805e9af13 = kuchnia.przeliczPrzepis(przepis_fc3e0a4d887b4d148ade66f805e9af13);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_fc3e0a4d887b4d148ade66f805e9af13);


                Przepis nowyPrzepis_przepis_fc3e0a4d887b4d148ade66f805e9af13 = () -> przeliczony_przepis_fc3e0a4d887b4d148ade66f805e9af13;

                if (przeliczony_przepis_fc3e0a4d887b4d148ade66f805e9af13.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_fc3e0a4d887b4d148ade66f805e9af13)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_fc3e0a4d887b4d148ade66f805e9af13)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }



                // Przyklad implementacji Kuchni
                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 34); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_27e885f748a443e7a27e4afe7197f44e_skladniki = Set.of(
                        new Skladnik(Produkt.SOL, 1146),
                        new Skladnik(Produkt.CUKIER_PUDER, 1161),
                        new Skladnik(Produkt.CUKIER, 1052),
                        new Skladnik(Produkt.MARGARYNA, 2400),
                        new Skladnik(Produkt.MAKA_ZIEMNIACZANA, 1253),
                        new Skladnik(Produkt.MASLO, 1257),
                        new Skladnik(Produkt.PROSZEK_DO_PIECZENIA, 873),
                        new Skladnik(Produkt.OLEJ_ROSLINNY, 454)
                );
                Przepis przepis_27e885f748a443e7a27e4afe7197f44e = () -> przepis_27e885f748a443e7a27e4afe7197f44e_skladniki;




                Set<Skladnik> przeliczony_przepis_27e885f748a443e7a27e4afe7197f44e = kuchnia.przeliczPrzepis(przepis_27e885f748a443e7a27e4afe7197f44e);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_27e885f748a443e7a27e4afe7197f44e);


                Przepis nowyPrzepis_przepis_27e885f748a443e7a27e4afe7197f44e = () -> przeliczony_przepis_27e885f748a443e7a27e4afe7197f44e;

                if (przeliczony_przepis_27e885f748a443e7a27e4afe7197f44e.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_27e885f748a443e7a27e4afe7197f44e)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_27e885f748a443e7a27e4afe7197f44e)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }



                // Przyklad implementacji Kuchni
                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 28); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_e5b92afaf8734ba69789f4136084757b_skladniki = Set.of(
                        new Skladnik(Produkt.MAKA_ZIEMNIACZANA, 1133),
                        new Skladnik(Produkt.CUKIER, 1918),
                        new Skladnik(Produkt.CUKIER_WANILIOWY, 363),
                        new Skladnik(Produkt.CUKIER_PUDER, 2208),
                        new Skladnik(Produkt.RODZYNKI, 541),
                        new Skladnik(Produkt.MASLO, 1439),
                        new Skladnik(Produkt.MAKA_RAZOWA, 2334),
                        new Skladnik(Produkt.PROSZEK_DO_PIECZENIA, 1841),
                        new Skladnik(Produkt.OLEJ_ROSLINNY, 2242)
                );
                Przepis przepis_e5b92afaf8734ba69789f4136084757b = () -> przepis_e5b92afaf8734ba69789f4136084757b_skladniki;




                Set<Skladnik> przeliczony_przepis_e5b92afaf8734ba69789f4136084757b = kuchnia.przeliczPrzepis(przepis_e5b92afaf8734ba69789f4136084757b);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_e5b92afaf8734ba69789f4136084757b);


                Przepis nowyPrzepis_przepis_e5b92afaf8734ba69789f4136084757b = () -> przeliczony_przepis_e5b92afaf8734ba69789f4136084757b;

                if (przeliczony_przepis_e5b92afaf8734ba69789f4136084757b.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_e5b92afaf8734ba69789f4136084757b)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_e5b92afaf8734ba69789f4136084757b)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }



                // Przyklad implementacji Kuchni
                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 69); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_33e7d55c95fa48d282fe84ea052c18b9_skladniki = Set.of(
                        new Skladnik(Produkt.CUKIER_WANILIOWY, 2645),
                        new Skladnik(Produkt.OLEJ_ROSLINNY, 1733),
                        new Skladnik(Produkt.SOL, 296),
                        new Skladnik(Produkt.TWAROG, 2335),
                        new Skladnik(Produkt.DROZDZE, 1657),
                        new Skladnik(Produkt.WODA, 37),
                        new Skladnik(Produkt.RODZYNKI, 2281),
                        new Skladnik(Produkt.MASLO, 2045),
                        new Skladnik(Produkt.MLEKO, 372),
                        new Skladnik(Produkt.MAKA_ZIEMNIACZANA, 2200)
                );
                Przepis przepis_33e7d55c95fa48d282fe84ea052c18b9 = () -> przepis_33e7d55c95fa48d282fe84ea052c18b9_skladniki;




                Set<Skladnik> przeliczony_przepis_33e7d55c95fa48d282fe84ea052c18b9 = kuchnia.przeliczPrzepis(przepis_33e7d55c95fa48d282fe84ea052c18b9);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_33e7d55c95fa48d282fe84ea052c18b9);


                Przepis nowyPrzepis_przepis_33e7d55c95fa48d282fe84ea052c18b9 = () -> przeliczony_przepis_33e7d55c95fa48d282fe84ea052c18b9;

                if (przeliczony_przepis_33e7d55c95fa48d282fe84ea052c18b9.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_33e7d55c95fa48d282fe84ea052c18b9)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_33e7d55c95fa48d282fe84ea052c18b9)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }



                // Przyklad implementacji Kuchni
                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 19); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_2e4cb72383304305b80a9610f91af359_skladniki = Set.of(
                        new Skladnik(Produkt.MAKA_ZIEMNIACZANA, 2776),
                        new Skladnik(Produkt.TWAROG, 2537),
                        new Skladnik(Produkt.PROSZEK_DO_PIECZENIA, 127),
                        new Skladnik(Produkt.MARGARYNA, 817),
                        new Skladnik(Produkt.MAKA_PSZENNA, 2068)
                );
                Przepis przepis_2e4cb72383304305b80a9610f91af359 = () -> przepis_2e4cb72383304305b80a9610f91af359_skladniki;




                Set<Skladnik> przeliczony_przepis_2e4cb72383304305b80a9610f91af359 = kuchnia.przeliczPrzepis(przepis_2e4cb72383304305b80a9610f91af359);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_2e4cb72383304305b80a9610f91af359);


                Przepis nowyPrzepis_przepis_2e4cb72383304305b80a9610f91af359 = () -> przeliczony_przepis_2e4cb72383304305b80a9610f91af359;

                if (przeliczony_przepis_2e4cb72383304305b80a9610f91af359.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_2e4cb72383304305b80a9610f91af359)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_2e4cb72383304305b80a9610f91af359)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }



                // Przyklad implementacji Kuchni
                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 52); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_92758260c6ac46158071bfd274ef5300_skladniki = Set.of(
                        new Skladnik(Produkt.CUKIER_PUDER, 351),
                        new Skladnik(Produkt.MAKA_PSZENNA, 2158),
                        new Skladnik(Produkt.CUKIER, 1425),
                        new Skladnik(Produkt.WODA, 28),
                        new Skladnik(Produkt.RODZYNKI, 1306)
                );
                Przepis przepis_92758260c6ac46158071bfd274ef5300 = () -> przepis_92758260c6ac46158071bfd274ef5300_skladniki;




                Set<Skladnik> przeliczony_przepis_92758260c6ac46158071bfd274ef5300 = kuchnia.przeliczPrzepis(przepis_92758260c6ac46158071bfd274ef5300);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_92758260c6ac46158071bfd274ef5300);


                Przepis nowyPrzepis_przepis_92758260c6ac46158071bfd274ef5300 = () -> przeliczony_przepis_92758260c6ac46158071bfd274ef5300;

                if (przeliczony_przepis_92758260c6ac46158071bfd274ef5300.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_92758260c6ac46158071bfd274ef5300)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_92758260c6ac46158071bfd274ef5300)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }



                // Przyklad implementacji Kuchni
                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 77); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_8f81647eb8ad422e9f0d12e1c4d1b4d2_skladniki = Set.of(
                        new Skladnik(Produkt.MASLO, 1548),
                        new Skladnik(Produkt.MAKA_PSZENNA, 2498),
                        new Skladnik(Produkt.TWAROG, 2311),
                        new Skladnik(Produkt.CUKIER_WANILIOWY, 2034),
                        new Skladnik(Produkt.WODA, 474),
                        new Skladnik(Produkt.MARGARYNA, 1859)
                );
                Przepis przepis_8f81647eb8ad422e9f0d12e1c4d1b4d2 = () -> przepis_8f81647eb8ad422e9f0d12e1c4d1b4d2_skladniki;




                Set<Skladnik> przeliczony_przepis_8f81647eb8ad422e9f0d12e1c4d1b4d2 = kuchnia.przeliczPrzepis(przepis_8f81647eb8ad422e9f0d12e1c4d1b4d2);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_8f81647eb8ad422e9f0d12e1c4d1b4d2);


                Przepis nowyPrzepis_przepis_8f81647eb8ad422e9f0d12e1c4d1b4d2 = () -> przeliczony_przepis_8f81647eb8ad422e9f0d12e1c4d1b4d2;

                if (przeliczony_przepis_8f81647eb8ad422e9f0d12e1c4d1b4d2.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_8f81647eb8ad422e9f0d12e1c4d1b4d2)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_8f81647eb8ad422e9f0d12e1c4d1b4d2)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }



            }

            public static void  grzanie3()
            {
                // Wygenerowany kod
                Kuchnia kuchnia = new EKuchnia(); // Przyklad implementacji Kuchni
                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 49); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_900b0f55b067493bb1d023d15feda511_skladniki = Set.of(
                        new Skladnik(Produkt.MASLO, 1586),
                        new Skladnik(Produkt.CUKIER_PUDER, 1091),
                        new Skladnik(Produkt.WODA, 368),
                        new Skladnik(Produkt.MLEKO, 1099),
                        new Skladnik(Produkt.OLEJ_ROSLINNY, 1016),
                        new Skladnik(Produkt.CUKIER_WANILIOWY, 1041),
                        new Skladnik(Produkt.TWAROG, 1830),
                        new Skladnik(Produkt.MAKA_ZIEMNIACZANA, 602),
                        new Skladnik(Produkt.MAKA_PSZENNA, 2441),
                        new Skladnik(Produkt.MARGARYNA, 226)
                );
                Przepis przepis_900b0f55b067493bb1d023d15feda511 = () -> przepis_900b0f55b067493bb1d023d15feda511_skladniki;




                Set<Skladnik> przeliczony_przepis_900b0f55b067493bb1d023d15feda511 = kuchnia.przeliczPrzepis(przepis_900b0f55b067493bb1d023d15feda511);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_900b0f55b067493bb1d023d15feda511);


                Przepis nowyPrzepis_przepis_900b0f55b067493bb1d023d15feda511 = () -> przeliczony_przepis_900b0f55b067493bb1d023d15feda511;

                if (przeliczony_przepis_900b0f55b067493bb1d023d15feda511.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_900b0f55b067493bb1d023d15feda511)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_900b0f55b067493bb1d023d15feda511)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }




                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 96); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_7f20d4bc9f5942ddb8e7920bc266e5ae_skladniki = Set.of(
                        new Skladnik(Produkt.MAKA_ZIEMNIACZANA, 1461),
                        new Skladnik(Produkt.OLEJ_ROSLINNY, 2876),
                        new Skladnik(Produkt.CUKIER, 303),
                        new Skladnik(Produkt.MLEKO, 845),
                        new Skladnik(Produkt.TWAROG, 2547),
                        new Skladnik(Produkt.CUKIER_PUDER, 2467)
                );
                Przepis przepis_7f20d4bc9f5942ddb8e7920bc266e5ae = () -> przepis_7f20d4bc9f5942ddb8e7920bc266e5ae_skladniki;




                Set<Skladnik> przeliczony_przepis_7f20d4bc9f5942ddb8e7920bc266e5ae = kuchnia.przeliczPrzepis(przepis_7f20d4bc9f5942ddb8e7920bc266e5ae);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_7f20d4bc9f5942ddb8e7920bc266e5ae);


                Przepis nowyPrzepis_przepis_7f20d4bc9f5942ddb8e7920bc266e5ae = () -> przeliczony_przepis_7f20d4bc9f5942ddb8e7920bc266e5ae;

                if (przeliczony_przepis_7f20d4bc9f5942ddb8e7920bc266e5ae.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_7f20d4bc9f5942ddb8e7920bc266e5ae)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_7f20d4bc9f5942ddb8e7920bc266e5ae)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }




                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 51); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_30e57292ecbe4de4a5ec618ee39e9c36_skladniki = Set.of(
                        new Skladnik(Produkt.CUKIER, 1870),
                        new Skladnik(Produkt.CUKIER_WANILIOWY, 607),
                        new Skladnik(Produkt.MAKA_RAZOWA, 516),
                        new Skladnik(Produkt.CUKIER_PUDER, 2004),
                        new Skladnik(Produkt.MASLO, 880),
                        new Skladnik(Produkt.WODA, 2342),
                        new Skladnik(Produkt.OLEJ_ROSLINNY, 1668),
                        new Skladnik(Produkt.DROZDZE, 393),
                        new Skladnik(Produkt.SOL, 523),
                        new Skladnik(Produkt.MAKA_ZIEMNIACZANA, 2359)
                );
                Przepis przepis_30e57292ecbe4de4a5ec618ee39e9c36 = () -> przepis_30e57292ecbe4de4a5ec618ee39e9c36_skladniki;




                Set<Skladnik> przeliczony_przepis_30e57292ecbe4de4a5ec618ee39e9c36 = kuchnia.przeliczPrzepis(przepis_30e57292ecbe4de4a5ec618ee39e9c36);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_30e57292ecbe4de4a5ec618ee39e9c36);


                Przepis nowyPrzepis_przepis_30e57292ecbe4de4a5ec618ee39e9c36 = () -> przeliczony_przepis_30e57292ecbe4de4a5ec618ee39e9c36;

                if (przeliczony_przepis_30e57292ecbe4de4a5ec618ee39e9c36.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_30e57292ecbe4de4a5ec618ee39e9c36)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_30e57292ecbe4de4a5ec618ee39e9c36)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }




                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 0); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_b27e2113af7845b693c2efc679e79b77_skladniki = Set.of(
                        new Skladnik(Produkt.WODA, 2835),
                        new Skladnik(Produkt.CUKIER, 1287),
                        new Skladnik(Produkt.MARGARYNA, 1362),
                        new Skladnik(Produkt.CUKIER_PUDER, 753),
                        new Skladnik(Produkt.MAKA_ZIEMNIACZANA, 510),
                        new Skladnik(Produkt.OLEJ_ROSLINNY, 71),
                        new Skladnik(Produkt.MAKA_RAZOWA, 2556)
                );
                Przepis przepis_b27e2113af7845b693c2efc679e79b77 = () -> przepis_b27e2113af7845b693c2efc679e79b77_skladniki;




                Set<Skladnik> przeliczony_przepis_b27e2113af7845b693c2efc679e79b77 = kuchnia.przeliczPrzepis(przepis_b27e2113af7845b693c2efc679e79b77);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_b27e2113af7845b693c2efc679e79b77);


                Przepis nowyPrzepis_przepis_b27e2113af7845b693c2efc679e79b77 = () -> przeliczony_przepis_b27e2113af7845b693c2efc679e79b77;

                if (przeliczony_przepis_b27e2113af7845b693c2efc679e79b77.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_b27e2113af7845b693c2efc679e79b77)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_b27e2113af7845b693c2efc679e79b77)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }




                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 99); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_483473855f42422fb6db0f6b7ffdd1ec_skladniki = Set.of(
                        new Skladnik(Produkt.CUKIER_WANILIOWY, 698),
                        new Skladnik(Produkt.TWAROG, 2797),
                        new Skladnik(Produkt.MLEKO, 578),
                        new Skladnik(Produkt.MAKA_PSZENNA, 1614),
                        new Skladnik(Produkt.MAKA_RAZOWA, 211),
                        new Skladnik(Produkt.WODA, 1589)
                );
                Przepis przepis_483473855f42422fb6db0f6b7ffdd1ec = () -> przepis_483473855f42422fb6db0f6b7ffdd1ec_skladniki;




                Set<Skladnik> przeliczony_przepis_483473855f42422fb6db0f6b7ffdd1ec = kuchnia.przeliczPrzepis(przepis_483473855f42422fb6db0f6b7ffdd1ec);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_483473855f42422fb6db0f6b7ffdd1ec);


                Przepis nowyPrzepis_przepis_483473855f42422fb6db0f6b7ffdd1ec = () -> przeliczony_przepis_483473855f42422fb6db0f6b7ffdd1ec;

                if (przeliczony_przepis_483473855f42422fb6db0f6b7ffdd1ec.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_483473855f42422fb6db0f6b7ffdd1ec)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_483473855f42422fb6db0f6b7ffdd1ec)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }




                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 78); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_e028a695f06e4ee7af405cb141aa322a_skladniki = Set.of(
                        new Skladnik(Produkt.CUKIER_PUDER, 1967),
                        new Skladnik(Produkt.MASLO, 1950),
                        new Skladnik(Produkt.RODZYNKI, 2136),
                        new Skladnik(Produkt.CUKIER, 2059),
                        new Skladnik(Produkt.MARGARYNA, 2904),
                        new Skladnik(Produkt.MAKA_ZIEMNIACZANA, 1610),
                        new Skladnik(Produkt.WODA, 1943),
                        new Skladnik(Produkt.MLEKO, 289)
                );
                Przepis przepis_e028a695f06e4ee7af405cb141aa322a = () -> przepis_e028a695f06e4ee7af405cb141aa322a_skladniki;




                Set<Skladnik> przeliczony_przepis_e028a695f06e4ee7af405cb141aa322a = kuchnia.przeliczPrzepis(przepis_e028a695f06e4ee7af405cb141aa322a);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_e028a695f06e4ee7af405cb141aa322a);


                Przepis nowyPrzepis_przepis_e028a695f06e4ee7af405cb141aa322a = () -> przeliczony_przepis_e028a695f06e4ee7af405cb141aa322a;

                if (przeliczony_przepis_e028a695f06e4ee7af405cb141aa322a.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_e028a695f06e4ee7af405cb141aa322a)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_e028a695f06e4ee7af405cb141aa322a)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }




                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 92); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_2cd9938b316c4c2ba423741398e87c1f_skladniki = Set.of(
                        new Skladnik(Produkt.SOL, 1225),
                        new Skladnik(Produkt.TWAROG, 2671),
                        new Skladnik(Produkt.CUKIER_WANILIOWY, 558),
                        new Skladnik(Produkt.MAKA_RAZOWA, 337),
                        new Skladnik(Produkt.MARGARYNA, 6),
                        new Skladnik(Produkt.MAKA_ZIEMNIACZANA, 2821),
                        new Skladnik(Produkt.CUKIER, 1440)
                );
                Przepis przepis_2cd9938b316c4c2ba423741398e87c1f = () -> przepis_2cd9938b316c4c2ba423741398e87c1f_skladniki;




                Set<Skladnik> przeliczony_przepis_2cd9938b316c4c2ba423741398e87c1f = kuchnia.przeliczPrzepis(przepis_2cd9938b316c4c2ba423741398e87c1f);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_2cd9938b316c4c2ba423741398e87c1f);


                Przepis nowyPrzepis_przepis_2cd9938b316c4c2ba423741398e87c1f = () -> przeliczony_przepis_2cd9938b316c4c2ba423741398e87c1f;

                if (przeliczony_przepis_2cd9938b316c4c2ba423741398e87c1f.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_2cd9938b316c4c2ba423741398e87c1f)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_2cd9938b316c4c2ba423741398e87c1f)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }




                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 20); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_40defe5b24fa499582ec63dadb6ce59d_skladniki = Set.of(
                        new Skladnik(Produkt.MASLO, 1129),
                        new Skladnik(Produkt.TWAROG, 2162),
                        new Skladnik(Produkt.CUKIER, 1841),
                        new Skladnik(Produkt.CUKIER_WANILIOWY, 1339),
                        new Skladnik(Produkt.MAKA_PSZENNA, 72),
                        new Skladnik(Produkt.MAKA_RAZOWA, 1357),
                        new Skladnik(Produkt.CUKIER_PUDER, 502),
                        new Skladnik(Produkt.SOL, 1791)
                );
                Przepis przepis_40defe5b24fa499582ec63dadb6ce59d = () -> przepis_40defe5b24fa499582ec63dadb6ce59d_skladniki;




                Set<Skladnik> przeliczony_przepis_40defe5b24fa499582ec63dadb6ce59d = kuchnia.przeliczPrzepis(przepis_40defe5b24fa499582ec63dadb6ce59d);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_40defe5b24fa499582ec63dadb6ce59d);


                Przepis nowyPrzepis_przepis_40defe5b24fa499582ec63dadb6ce59d = () -> przeliczony_przepis_40defe5b24fa499582ec63dadb6ce59d;

                if (przeliczony_przepis_40defe5b24fa499582ec63dadb6ce59d.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_40defe5b24fa499582ec63dadb6ce59d)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_40defe5b24fa499582ec63dadb6ce59d)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }




                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 87); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_276997a1620442f2ae83af819ff0cbeb_skladniki = Set.of(
                        new Skladnik(Produkt.MAKA_RAZOWA, 720),
                        new Skladnik(Produkt.MASLO, 2522),
                        new Skladnik(Produkt.CUKIER, 1289),
                        new Skladnik(Produkt.MLEKO, 2119),
                        new Skladnik(Produkt.WODA, 1734),
                        new Skladnik(Produkt.CUKIER_WANILIOWY, 2121),
                        new Skladnik(Produkt.PROSZEK_DO_PIECZENIA, 694),
                        new Skladnik(Produkt.SOL, 1778),
                        new Skladnik(Produkt.CUKIER_PUDER, 1644),
                        new Skladnik(Produkt.MAKA_ZIEMNIACZANA, 199)
                );
                Przepis przepis_276997a1620442f2ae83af819ff0cbeb = () -> przepis_276997a1620442f2ae83af819ff0cbeb_skladniki;




                Set<Skladnik> przeliczony_przepis_276997a1620442f2ae83af819ff0cbeb = kuchnia.przeliczPrzepis(przepis_276997a1620442f2ae83af819ff0cbeb);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_276997a1620442f2ae83af819ff0cbeb);


                Przepis nowyPrzepis_przepis_276997a1620442f2ae83af819ff0cbeb = () -> przeliczony_przepis_276997a1620442f2ae83af819ff0cbeb;

                if (przeliczony_przepis_276997a1620442f2ae83af819ff0cbeb.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_276997a1620442f2ae83af819ff0cbeb)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_276997a1620442f2ae83af819ff0cbeb)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }




                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 79); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_800fe95b016e475e8715b702f623950a_skladniki = Set.of(
                        new Skladnik(Produkt.SOL, 2203),
                        new Skladnik(Produkt.OLEJ_ROSLINNY, 2328),
                        new Skladnik(Produkt.MASLO, 422),
                        new Skladnik(Produkt.WODA, 907),
                        new Skladnik(Produkt.MARGARYNA, 2244),
                        new Skladnik(Produkt.MAKA_ZIEMNIACZANA, 2368)
                );
                Przepis przepis_800fe95b016e475e8715b702f623950a = () -> przepis_800fe95b016e475e8715b702f623950a_skladniki;




                Set<Skladnik> przeliczony_przepis_800fe95b016e475e8715b702f623950a = kuchnia.przeliczPrzepis(przepis_800fe95b016e475e8715b702f623950a);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_800fe95b016e475e8715b702f623950a);


                Przepis nowyPrzepis_przepis_800fe95b016e475e8715b702f623950a = () -> przeliczony_przepis_800fe95b016e475e8715b702f623950a;

                if (przeliczony_przepis_800fe95b016e475e8715b702f623950a.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_800fe95b016e475e8715b702f623950a)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_800fe95b016e475e8715b702f623950a)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }




                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 15); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_9540900c3df441328a269feacdefa679_skladniki = Set.of(
                        new Skladnik(Produkt.DROZDZE, 1278),
                        new Skladnik(Produkt.WODA, 2690),
                        new Skladnik(Produkt.MASLO, 2392),
                        new Skladnik(Produkt.MAKA_ZIEMNIACZANA, 1015),
                        new Skladnik(Produkt.MLEKO, 978),
                        new Skladnik(Produkt.MARGARYNA, 529),
                        new Skladnik(Produkt.RODZYNKI, 1208),
                        new Skladnik(Produkt.TWAROG, 852)
                );
                Przepis przepis_9540900c3df441328a269feacdefa679 = () -> przepis_9540900c3df441328a269feacdefa679_skladniki;




                Set<Skladnik> przeliczony_przepis_9540900c3df441328a269feacdefa679 = kuchnia.przeliczPrzepis(przepis_9540900c3df441328a269feacdefa679);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_9540900c3df441328a269feacdefa679);


                Przepis nowyPrzepis_przepis_9540900c3df441328a269feacdefa679 = () -> przeliczony_przepis_9540900c3df441328a269feacdefa679;

                if (przeliczony_przepis_9540900c3df441328a269feacdefa679.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_9540900c3df441328a269feacdefa679)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_9540900c3df441328a269feacdefa679)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }




                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 29); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_47faa222d9784ac8b39828db78a8c1d9_skladniki = Set.of(
                        new Skladnik(Produkt.MLEKO, 1894),
                        new Skladnik(Produkt.CUKIER_PUDER, 1134),
                        new Skladnik(Produkt.MARGARYNA, 448),
                        new Skladnik(Produkt.DROZDZE, 967),
                        new Skladnik(Produkt.CUKIER, 2446),
                        new Skladnik(Produkt.OLEJ_ROSLINNY, 2941),
                        new Skladnik(Produkt.MAKA_RAZOWA, 581),
                        new Skladnik(Produkt.PROSZEK_DO_PIECZENIA, 1734),
                        new Skladnik(Produkt.TWAROG, 476)
                );
                Przepis przepis_47faa222d9784ac8b39828db78a8c1d9 = () -> przepis_47faa222d9784ac8b39828db78a8c1d9_skladniki;




                Set<Skladnik> przeliczony_przepis_47faa222d9784ac8b39828db78a8c1d9 = kuchnia.przeliczPrzepis(przepis_47faa222d9784ac8b39828db78a8c1d9);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_47faa222d9784ac8b39828db78a8c1d9);


                Przepis nowyPrzepis_przepis_47faa222d9784ac8b39828db78a8c1d9 = () -> przeliczony_przepis_47faa222d9784ac8b39828db78a8c1d9;

                if (przeliczony_przepis_47faa222d9784ac8b39828db78a8c1d9.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_47faa222d9784ac8b39828db78a8c1d9)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_47faa222d9784ac8b39828db78a8c1d9)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }




                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 24); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_99c5b7d639464392990f0f5a063ac208_skladniki = Set.of(
                        new Skladnik(Produkt.MLEKO, 1606),
                        new Skladnik(Produkt.MAKA_ZIEMNIACZANA, 1888),
                        new Skladnik(Produkt.SOL, 329),
                        new Skladnik(Produkt.MASLO, 2356),
                        new Skladnik(Produkt.CUKIER_PUDER, 85),
                        new Skladnik(Produkt.CUKIER_WANILIOWY, 1368),
                        new Skladnik(Produkt.MARGARYNA, 25),
                        new Skladnik(Produkt.DROZDZE, 2957),
                        new Skladnik(Produkt.MAKA_PSZENNA, 1042)
                );
                Przepis przepis_99c5b7d639464392990f0f5a063ac208 = () -> przepis_99c5b7d639464392990f0f5a063ac208_skladniki;




                Set<Skladnik> przeliczony_przepis_99c5b7d639464392990f0f5a063ac208 = kuchnia.przeliczPrzepis(przepis_99c5b7d639464392990f0f5a063ac208);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_99c5b7d639464392990f0f5a063ac208);


                Przepis nowyPrzepis_przepis_99c5b7d639464392990f0f5a063ac208 = () -> przeliczony_przepis_99c5b7d639464392990f0f5a063ac208;

                if (przeliczony_przepis_99c5b7d639464392990f0f5a063ac208.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_99c5b7d639464392990f0f5a063ac208)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_99c5b7d639464392990f0f5a063ac208)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }




                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 93); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_9d7d0f21d73840bd91f8a204e460bd9e_skladniki = Set.of(
                        new Skladnik(Produkt.MLEKO, 1037),
                        new Skladnik(Produkt.CUKIER_PUDER, 700),
                        new Skladnik(Produkt.CUKIER_WANILIOWY, 2455),
                        new Skladnik(Produkt.PROSZEK_DO_PIECZENIA, 1391),
                        new Skladnik(Produkt.MARGARYNA, 1002),
                        new Skladnik(Produkt.RODZYNKI, 2866),
                        new Skladnik(Produkt.CUKIER, 2106)
                );
                Przepis przepis_9d7d0f21d73840bd91f8a204e460bd9e = () -> przepis_9d7d0f21d73840bd91f8a204e460bd9e_skladniki;




                Set<Skladnik> przeliczony_przepis_9d7d0f21d73840bd91f8a204e460bd9e = kuchnia.przeliczPrzepis(przepis_9d7d0f21d73840bd91f8a204e460bd9e);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_9d7d0f21d73840bd91f8a204e460bd9e);


                Przepis nowyPrzepis_przepis_9d7d0f21d73840bd91f8a204e460bd9e = () -> przeliczony_przepis_9d7d0f21d73840bd91f8a204e460bd9e;

                if (przeliczony_przepis_9d7d0f21d73840bd91f8a204e460bd9e.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_9d7d0f21d73840bd91f8a204e460bd9e)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_9d7d0f21d73840bd91f8a204e460bd9e)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }




                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 99); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_88166860efc24617843ceffc1d9c0500_skladniki = Set.of(
                        new Skladnik(Produkt.MLEKO, 660),
                        new Skladnik(Produkt.OLEJ_ROSLINNY, 2859),
                        new Skladnik(Produkt.WODA, 704),
                        new Skladnik(Produkt.PROSZEK_DO_PIECZENIA, 2424),
                        new Skladnik(Produkt.RODZYNKI, 170),
                        new Skladnik(Produkt.MARGARYNA, 746),
                        new Skladnik(Produkt.MASLO, 1549)
                );
                Przepis przepis_88166860efc24617843ceffc1d9c0500 = () -> przepis_88166860efc24617843ceffc1d9c0500_skladniki;




                Set<Skladnik> przeliczony_przepis_88166860efc24617843ceffc1d9c0500 = kuchnia.przeliczPrzepis(przepis_88166860efc24617843ceffc1d9c0500);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_88166860efc24617843ceffc1d9c0500);


                Przepis nowyPrzepis_przepis_88166860efc24617843ceffc1d9c0500 = () -> przeliczony_przepis_88166860efc24617843ceffc1d9c0500;

                if (przeliczony_przepis_88166860efc24617843ceffc1d9c0500.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_88166860efc24617843ceffc1d9c0500)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_88166860efc24617843ceffc1d9c0500)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }




                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 3); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_f84c7ba57d66434bae5f0f36c93566a0_skladniki = Set.of(
                        new Skladnik(Produkt.CUKIER, 2056),
                        new Skladnik(Produkt.SOL, 422),
                        new Skladnik(Produkt.MAKA_PSZENNA, 1592),
                        new Skladnik(Produkt.CUKIER_WANILIOWY, 1007),
                        new Skladnik(Produkt.OLEJ_ROSLINNY, 2210),
                        new Skladnik(Produkt.MLEKO, 1661),
                        new Skladnik(Produkt.MASLO, 1293),
                        new Skladnik(Produkt.PROSZEK_DO_PIECZENIA, 285),
                        new Skladnik(Produkt.DROZDZE, 2899)
                );
                Przepis przepis_f84c7ba57d66434bae5f0f36c93566a0 = () -> przepis_f84c7ba57d66434bae5f0f36c93566a0_skladniki;




                Set<Skladnik> przeliczony_przepis_f84c7ba57d66434bae5f0f36c93566a0 = kuchnia.przeliczPrzepis(przepis_f84c7ba57d66434bae5f0f36c93566a0);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_f84c7ba57d66434bae5f0f36c93566a0);


                Przepis nowyPrzepis_przepis_f84c7ba57d66434bae5f0f36c93566a0 = () -> przeliczony_przepis_f84c7ba57d66434bae5f0f36c93566a0;

                if (przeliczony_przepis_f84c7ba57d66434bae5f0f36c93566a0.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_f84c7ba57d66434bae5f0f36c93566a0)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_f84c7ba57d66434bae5f0f36c93566a0)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }




                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 8); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_91306b132f874182b2e243e500caf52d_skladniki = Set.of(
                        new Skladnik(Produkt.RODZYNKI, 1315),
                        new Skladnik(Produkt.MLEKO, 1631),
                        new Skladnik(Produkt.CUKIER, 1091),
                        new Skladnik(Produkt.DROZDZE, 515),
                        new Skladnik(Produkt.PROSZEK_DO_PIECZENIA, 1301),
                        new Skladnik(Produkt.MASLO, 990)
                );
                Przepis przepis_91306b132f874182b2e243e500caf52d = () -> przepis_91306b132f874182b2e243e500caf52d_skladniki;




                Set<Skladnik> przeliczony_przepis_91306b132f874182b2e243e500caf52d = kuchnia.przeliczPrzepis(przepis_91306b132f874182b2e243e500caf52d);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_91306b132f874182b2e243e500caf52d);


                Przepis nowyPrzepis_przepis_91306b132f874182b2e243e500caf52d = () -> przeliczony_przepis_91306b132f874182b2e243e500caf52d;

                if (przeliczony_przepis_91306b132f874182b2e243e500caf52d.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_91306b132f874182b2e243e500caf52d)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_91306b132f874182b2e243e500caf52d)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }




                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 38); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_d0642da6bbcb4cecb081f48fc85b895b_skladniki = Set.of(
                        new Skladnik(Produkt.CUKIER_WANILIOWY, 1268),
                        new Skladnik(Produkt.CUKIER, 693),
                        new Skladnik(Produkt.SOL, 2622),
                        new Skladnik(Produkt.TWAROG, 2813),
                        new Skladnik(Produkt.MLEKO, 45),
                        new Skladnik(Produkt.MARGARYNA, 1453),
                        new Skladnik(Produkt.PROSZEK_DO_PIECZENIA, 1031),
                        new Skladnik(Produkt.WODA, 1661),
                        new Skladnik(Produkt.RODZYNKI, 541)
                );
                Przepis przepis_d0642da6bbcb4cecb081f48fc85b895b = () -> przepis_d0642da6bbcb4cecb081f48fc85b895b_skladniki;




                Set<Skladnik> przeliczony_przepis_d0642da6bbcb4cecb081f48fc85b895b = kuchnia.przeliczPrzepis(przepis_d0642da6bbcb4cecb081f48fc85b895b);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_d0642da6bbcb4cecb081f48fc85b895b);


                Przepis nowyPrzepis_przepis_d0642da6bbcb4cecb081f48fc85b895b = () -> przeliczony_przepis_d0642da6bbcb4cecb081f48fc85b895b;

                if (przeliczony_przepis_d0642da6bbcb4cecb081f48fc85b895b.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_d0642da6bbcb4cecb081f48fc85b895b)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_d0642da6bbcb4cecb081f48fc85b895b)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }




                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 22); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_a64dc960d334470b8f3b069c14471074_skladniki = Set.of(
                        new Skladnik(Produkt.MAKA_ZIEMNIACZANA, 1880),
                        new Skladnik(Produkt.RODZYNKI, 2180),
                        new Skladnik(Produkt.MARGARYNA, 648),
                        new Skladnik(Produkt.CUKIER_WANILIOWY, 2860),
                        new Skladnik(Produkt.SOL, 501),
                        new Skladnik(Produkt.DROZDZE, 788),
                        new Skladnik(Produkt.MLEKO, 2367),
                        new Skladnik(Produkt.WODA, 400),
                        new Skladnik(Produkt.MAKA_RAZOWA, 1764)
                );
                Przepis przepis_a64dc960d334470b8f3b069c14471074 = () -> przepis_a64dc960d334470b8f3b069c14471074_skladniki;




                Set<Skladnik> przeliczony_przepis_a64dc960d334470b8f3b069c14471074 = kuchnia.przeliczPrzepis(przepis_a64dc960d334470b8f3b069c14471074);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_a64dc960d334470b8f3b069c14471074);


                Przepis nowyPrzepis_przepis_a64dc960d334470b8f3b069c14471074 = () -> przeliczony_przepis_a64dc960d334470b8f3b069c14471074;

                if (przeliczony_przepis_a64dc960d334470b8f3b069c14471074.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_a64dc960d334470b8f3b069c14471074)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_a64dc960d334470b8f3b069c14471074)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }




                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 94); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_8cb5ebb9854e43e48c0fb9b69d1d538a_skladniki = Set.of(
                        new Skladnik(Produkt.SOL, 1321),
                        new Skladnik(Produkt.PROSZEK_DO_PIECZENIA, 244),
                        new Skladnik(Produkt.OLEJ_ROSLINNY, 1231),
                        new Skladnik(Produkt.CUKIER, 1619),
                        new Skladnik(Produkt.MLEKO, 893)
                );
                Przepis przepis_8cb5ebb9854e43e48c0fb9b69d1d538a = () -> przepis_8cb5ebb9854e43e48c0fb9b69d1d538a_skladniki;




                Set<Skladnik> przeliczony_przepis_8cb5ebb9854e43e48c0fb9b69d1d538a = kuchnia.przeliczPrzepis(przepis_8cb5ebb9854e43e48c0fb9b69d1d538a);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_8cb5ebb9854e43e48c0fb9b69d1d538a);


                Przepis nowyPrzepis_przepis_8cb5ebb9854e43e48c0fb9b69d1d538a = () -> przeliczony_przepis_8cb5ebb9854e43e48c0fb9b69d1d538a;

                if (przeliczony_przepis_8cb5ebb9854e43e48c0fb9b69d1d538a.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_8cb5ebb9854e43e48c0fb9b69d1d538a)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_8cb5ebb9854e43e48c0fb9b69d1d538a)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }




                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 32); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_cf34098f5afd4675be9d81e50c935bbf_skladniki = Set.of(
                        new Skladnik(Produkt.MLEKO, 2554),
                        new Skladnik(Produkt.WODA, 2743),
                        new Skladnik(Produkt.MAKA_RAZOWA, 368),
                        new Skladnik(Produkt.PROSZEK_DO_PIECZENIA, 893),
                        new Skladnik(Produkt.RODZYNKI, 2105),
                        new Skladnik(Produkt.MASLO, 23)
                );
                Przepis przepis_cf34098f5afd4675be9d81e50c935bbf = () -> przepis_cf34098f5afd4675be9d81e50c935bbf_skladniki;




                Set<Skladnik> przeliczony_przepis_cf34098f5afd4675be9d81e50c935bbf = kuchnia.przeliczPrzepis(przepis_cf34098f5afd4675be9d81e50c935bbf);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_cf34098f5afd4675be9d81e50c935bbf);


                Przepis nowyPrzepis_przepis_cf34098f5afd4675be9d81e50c935bbf = () -> przeliczony_przepis_cf34098f5afd4675be9d81e50c935bbf;

                if (przeliczony_przepis_cf34098f5afd4675be9d81e50c935bbf.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_cf34098f5afd4675be9d81e50c935bbf)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_cf34098f5afd4675be9d81e50c935bbf)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }




                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 16); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_56bb3c7d24ed46d3bab5e7e6cf1630ad_skladniki = Set.of(
                        new Skladnik(Produkt.MAKA_RAZOWA, 2736),
                        new Skladnik(Produkt.PROSZEK_DO_PIECZENIA, 2337),
                        new Skladnik(Produkt.CUKIER, 1727),
                        new Skladnik(Produkt.MAKA_PSZENNA, 225),
                        new Skladnik(Produkt.MAKA_ZIEMNIACZANA, 1094),
                        new Skladnik(Produkt.RODZYNKI, 2249),
                        new Skladnik(Produkt.MASLO, 327),
                        new Skladnik(Produkt.MARGARYNA, 2917)
                );
                Przepis przepis_56bb3c7d24ed46d3bab5e7e6cf1630ad = () -> przepis_56bb3c7d24ed46d3bab5e7e6cf1630ad_skladniki;




                Set<Skladnik> przeliczony_przepis_56bb3c7d24ed46d3bab5e7e6cf1630ad = kuchnia.przeliczPrzepis(przepis_56bb3c7d24ed46d3bab5e7e6cf1630ad);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_56bb3c7d24ed46d3bab5e7e6cf1630ad);


                Przepis nowyPrzepis_przepis_56bb3c7d24ed46d3bab5e7e6cf1630ad = () -> przeliczony_przepis_56bb3c7d24ed46d3bab5e7e6cf1630ad;

                if (przeliczony_przepis_56bb3c7d24ed46d3bab5e7e6cf1630ad.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_56bb3c7d24ed46d3bab5e7e6cf1630ad)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_56bb3c7d24ed46d3bab5e7e6cf1630ad)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }




                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 92); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_4c51e51d860c48ebb36f3bd61474de9f_skladniki = Set.of(
                        new Skladnik(Produkt.MAKA_PSZENNA, 1114),
                        new Skladnik(Produkt.OLEJ_ROSLINNY, 1417),
                        new Skladnik(Produkt.DROZDZE, 2491),
                        new Skladnik(Produkt.RODZYNKI, 549),
                        new Skladnik(Produkt.CUKIER_PUDER, 715)
                );
                Przepis przepis_4c51e51d860c48ebb36f3bd61474de9f = () -> przepis_4c51e51d860c48ebb36f3bd61474de9f_skladniki;




                Set<Skladnik> przeliczony_przepis_4c51e51d860c48ebb36f3bd61474de9f = kuchnia.przeliczPrzepis(przepis_4c51e51d860c48ebb36f3bd61474de9f);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_4c51e51d860c48ebb36f3bd61474de9f);


                Przepis nowyPrzepis_przepis_4c51e51d860c48ebb36f3bd61474de9f = () -> przeliczony_przepis_4c51e51d860c48ebb36f3bd61474de9f;

                if (przeliczony_przepis_4c51e51d860c48ebb36f3bd61474de9f.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_4c51e51d860c48ebb36f3bd61474de9f)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_4c51e51d860c48ebb36f3bd61474de9f)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }




                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 99); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_ea8d9312f8604b10956f7ddde230e1e3_skladniki = Set.of(
                        new Skladnik(Produkt.RODZYNKI, 1575),
                        new Skladnik(Produkt.MAKA_PSZENNA, 1582),
                        new Skladnik(Produkt.DROZDZE, 830),
                        new Skladnik(Produkt.CUKIER_PUDER, 2836),
                        new Skladnik(Produkt.TWAROG, 923),
                        new Skladnik(Produkt.MAKA_RAZOWA, 2824),
                        new Skladnik(Produkt.CUKIER_WANILIOWY, 1866),
                        new Skladnik(Produkt.MAKA_ZIEMNIACZANA, 1581)
                );
                Przepis przepis_ea8d9312f8604b10956f7ddde230e1e3 = () -> przepis_ea8d9312f8604b10956f7ddde230e1e3_skladniki;




                Set<Skladnik> przeliczony_przepis_ea8d9312f8604b10956f7ddde230e1e3 = kuchnia.przeliczPrzepis(przepis_ea8d9312f8604b10956f7ddde230e1e3);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_ea8d9312f8604b10956f7ddde230e1e3);


                Przepis nowyPrzepis_przepis_ea8d9312f8604b10956f7ddde230e1e3 = () -> przeliczony_przepis_ea8d9312f8604b10956f7ddde230e1e3;

                if (przeliczony_przepis_ea8d9312f8604b10956f7ddde230e1e3.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_ea8d9312f8604b10956f7ddde230e1e3)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_ea8d9312f8604b10956f7ddde230e1e3)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }




                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 12); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_44d90f347c1e464bade2b20eb9341cbc_skladniki = Set.of(
                        new Skladnik(Produkt.MASLO, 617),
                        new Skladnik(Produkt.MARGARYNA, 1870),
                        new Skladnik(Produkt.MAKA_ZIEMNIACZANA, 1706),
                        new Skladnik(Produkt.DROZDZE, 2247),
                        new Skladnik(Produkt.TWAROG, 2044),
                        new Skladnik(Produkt.MAKA_RAZOWA, 1559),
                        new Skladnik(Produkt.RODZYNKI, 1824),
                        new Skladnik(Produkt.PROSZEK_DO_PIECZENIA, 1831),
                        new Skladnik(Produkt.CUKIER_PUDER, 121)
                );
                Przepis przepis_44d90f347c1e464bade2b20eb9341cbc = () -> przepis_44d90f347c1e464bade2b20eb9341cbc_skladniki;




                Set<Skladnik> przeliczony_przepis_44d90f347c1e464bade2b20eb9341cbc = kuchnia.przeliczPrzepis(przepis_44d90f347c1e464bade2b20eb9341cbc);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_44d90f347c1e464bade2b20eb9341cbc);


                Przepis nowyPrzepis_przepis_44d90f347c1e464bade2b20eb9341cbc = () -> przeliczony_przepis_44d90f347c1e464bade2b20eb9341cbc;

                if (przeliczony_przepis_44d90f347c1e464bade2b20eb9341cbc.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_44d90f347c1e464bade2b20eb9341cbc)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_44d90f347c1e464bade2b20eb9341cbc)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }




                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 22); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_e5f98b1cd0ee40e7b03bee43135ca37d_skladniki = Set.of(
                        new Skladnik(Produkt.OLEJ_ROSLINNY, 175),
                        new Skladnik(Produkt.CUKIER_PUDER, 2528),
                        new Skladnik(Produkt.CUKIER_WANILIOWY, 245),
                        new Skladnik(Produkt.PROSZEK_DO_PIECZENIA, 611),
                        new Skladnik(Produkt.SOL, 1884),
                        new Skladnik(Produkt.MASLO, 1019),
                        new Skladnik(Produkt.MAKA_PSZENNA, 1361)
                );
                Przepis przepis_e5f98b1cd0ee40e7b03bee43135ca37d = () -> przepis_e5f98b1cd0ee40e7b03bee43135ca37d_skladniki;




                Set<Skladnik> przeliczony_przepis_e5f98b1cd0ee40e7b03bee43135ca37d = kuchnia.przeliczPrzepis(przepis_e5f98b1cd0ee40e7b03bee43135ca37d);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_e5f98b1cd0ee40e7b03bee43135ca37d);


                Przepis nowyPrzepis_przepis_e5f98b1cd0ee40e7b03bee43135ca37d = () -> przeliczony_przepis_e5f98b1cd0ee40e7b03bee43135ca37d;

                if (przeliczony_przepis_e5f98b1cd0ee40e7b03bee43135ca37d.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_e5f98b1cd0ee40e7b03bee43135ca37d)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_e5f98b1cd0ee40e7b03bee43135ca37d)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }




                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 76); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_916c1475b91d42ff8d5f1f94acae27b9_skladniki = Set.of(
                        new Skladnik(Produkt.MARGARYNA, 1486),
                        new Skladnik(Produkt.CUKIER_WANILIOWY, 137),
                        new Skladnik(Produkt.SOL, 2996),
                        new Skladnik(Produkt.WODA, 682),
                        new Skladnik(Produkt.MAKA_ZIEMNIACZANA, 864),
                        new Skladnik(Produkt.PROSZEK_DO_PIECZENIA, 2533)
                );
                Przepis przepis_916c1475b91d42ff8d5f1f94acae27b9 = () -> przepis_916c1475b91d42ff8d5f1f94acae27b9_skladniki;




                Set<Skladnik> przeliczony_przepis_916c1475b91d42ff8d5f1f94acae27b9 = kuchnia.przeliczPrzepis(przepis_916c1475b91d42ff8d5f1f94acae27b9);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_916c1475b91d42ff8d5f1f94acae27b9);


                Przepis nowyPrzepis_przepis_916c1475b91d42ff8d5f1f94acae27b9 = () -> przeliczony_przepis_916c1475b91d42ff8d5f1f94acae27b9;

                if (przeliczony_przepis_916c1475b91d42ff8d5f1f94acae27b9.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_916c1475b91d42ff8d5f1f94acae27b9)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_916c1475b91d42ff8d5f1f94acae27b9)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }




                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 61); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_343a991bc57a496f8a0f7b64f7c303ae_skladniki = Set.of(
                        new Skladnik(Produkt.MARGARYNA, 1388),
                        new Skladnik(Produkt.PROSZEK_DO_PIECZENIA, 881),
                        new Skladnik(Produkt.MAKA_ZIEMNIACZANA, 2341),
                        new Skladnik(Produkt.SOL, 1091),
                        new Skladnik(Produkt.CUKIER_PUDER, 2356),
                        new Skladnik(Produkt.RODZYNKI, 2393),
                        new Skladnik(Produkt.WODA, 2555),
                        new Skladnik(Produkt.TWAROG, 2329),
                        new Skladnik(Produkt.MAKA_PSZENNA, 2113),
                        new Skladnik(Produkt.MLEKO, 1030)
                );
                Przepis przepis_343a991bc57a496f8a0f7b64f7c303ae = () -> przepis_343a991bc57a496f8a0f7b64f7c303ae_skladniki;




                Set<Skladnik> przeliczony_przepis_343a991bc57a496f8a0f7b64f7c303ae = kuchnia.przeliczPrzepis(przepis_343a991bc57a496f8a0f7b64f7c303ae);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_343a991bc57a496f8a0f7b64f7c303ae);


                Przepis nowyPrzepis_przepis_343a991bc57a496f8a0f7b64f7c303ae = () -> przeliczony_przepis_343a991bc57a496f8a0f7b64f7c303ae;

                if (przeliczony_przepis_343a991bc57a496f8a0f7b64f7c303ae.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_343a991bc57a496f8a0f7b64f7c303ae)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_343a991bc57a496f8a0f7b64f7c303ae)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }




                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 0); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_cce0db6694d94182b7ca3c1ae47b72bc_skladniki = Set.of(
                        new Skladnik(Produkt.SOL, 1053),
                        new Skladnik(Produkt.CUKIER_PUDER, 1632),
                        new Skladnik(Produkt.RODZYNKI, 1809),
                        new Skladnik(Produkt.CUKIER, 468),
                        new Skladnik(Produkt.PROSZEK_DO_PIECZENIA, 2299),
                        new Skladnik(Produkt.MARGARYNA, 2483),
                        new Skladnik(Produkt.MAKA_ZIEMNIACZANA, 2269),
                        new Skladnik(Produkt.CUKIER_WANILIOWY, 822),
                        new Skladnik(Produkt.MLEKO, 2714)
                );
                Przepis przepis_cce0db6694d94182b7ca3c1ae47b72bc = () -> przepis_cce0db6694d94182b7ca3c1ae47b72bc_skladniki;




                Set<Skladnik> przeliczony_przepis_cce0db6694d94182b7ca3c1ae47b72bc = kuchnia.przeliczPrzepis(przepis_cce0db6694d94182b7ca3c1ae47b72bc);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_cce0db6694d94182b7ca3c1ae47b72bc);


                Przepis nowyPrzepis_przepis_cce0db6694d94182b7ca3c1ae47b72bc = () -> przeliczony_przepis_cce0db6694d94182b7ca3c1ae47b72bc;

                if (przeliczony_przepis_cce0db6694d94182b7ca3c1ae47b72bc.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_cce0db6694d94182b7ca3c1ae47b72bc)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_cce0db6694d94182b7ca3c1ae47b72bc)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }




                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 14); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_348354729a0b4c5f98fcdf847af1a6d5_skladniki = Set.of(
                        new Skladnik(Produkt.PROSZEK_DO_PIECZENIA, 2315),
                        new Skladnik(Produkt.MARGARYNA, 672),
                        new Skladnik(Produkt.SOL, 568),
                        new Skladnik(Produkt.CUKIER_PUDER, 144),
                        new Skladnik(Produkt.WODA, 2300),
                        new Skladnik(Produkt.CUKIER_WANILIOWY, 1224),
                        new Skladnik(Produkt.MASLO, 2576)
                );
                Przepis przepis_348354729a0b4c5f98fcdf847af1a6d5 = () -> przepis_348354729a0b4c5f98fcdf847af1a6d5_skladniki;




                Set<Skladnik> przeliczony_przepis_348354729a0b4c5f98fcdf847af1a6d5 = kuchnia.przeliczPrzepis(przepis_348354729a0b4c5f98fcdf847af1a6d5);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_348354729a0b4c5f98fcdf847af1a6d5);


                Przepis nowyPrzepis_przepis_348354729a0b4c5f98fcdf847af1a6d5 = () -> przeliczony_przepis_348354729a0b4c5f98fcdf847af1a6d5;

                if (przeliczony_przepis_348354729a0b4c5f98fcdf847af1a6d5.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_348354729a0b4c5f98fcdf847af1a6d5)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_348354729a0b4c5f98fcdf847af1a6d5)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }




                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 63); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_18ac1bce57c6464783347fb99dac5072_skladniki = Set.of(
                        new Skladnik(Produkt.MLEKO, 2390),
                        new Skladnik(Produkt.MAKA_PSZENNA, 457),
                        new Skladnik(Produkt.CUKIER_WANILIOWY, 2812),
                        new Skladnik(Produkt.MARGARYNA, 1872),
                        new Skladnik(Produkt.MAKA_RAZOWA, 455)
                );
                Przepis przepis_18ac1bce57c6464783347fb99dac5072 = () -> przepis_18ac1bce57c6464783347fb99dac5072_skladniki;




                Set<Skladnik> przeliczony_przepis_18ac1bce57c6464783347fb99dac5072 = kuchnia.przeliczPrzepis(przepis_18ac1bce57c6464783347fb99dac5072);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_18ac1bce57c6464783347fb99dac5072);


                Przepis nowyPrzepis_przepis_18ac1bce57c6464783347fb99dac5072 = () -> przeliczony_przepis_18ac1bce57c6464783347fb99dac5072;

                if (przeliczony_przepis_18ac1bce57c6464783347fb99dac5072.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_18ac1bce57c6464783347fb99dac5072)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_18ac1bce57c6464783347fb99dac5072)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }




                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 25); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_af242163baa8415a8b6138b7b1198b8d_skladniki = Set.of(
                        new Skladnik(Produkt.CUKIER_WANILIOWY, 1301),
                        new Skladnik(Produkt.WODA, 662),
                        new Skladnik(Produkt.CUKIER, 430),
                        new Skladnik(Produkt.RODZYNKI, 837),
                        new Skladnik(Produkt.OLEJ_ROSLINNY, 358),
                        new Skladnik(Produkt.DROZDZE, 2022),
                        new Skladnik(Produkt.PROSZEK_DO_PIECZENIA, 2391)
                );
                Przepis przepis_af242163baa8415a8b6138b7b1198b8d = () -> przepis_af242163baa8415a8b6138b7b1198b8d_skladniki;




                Set<Skladnik> przeliczony_przepis_af242163baa8415a8b6138b7b1198b8d = kuchnia.przeliczPrzepis(przepis_af242163baa8415a8b6138b7b1198b8d);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_af242163baa8415a8b6138b7b1198b8d);


                Przepis nowyPrzepis_przepis_af242163baa8415a8b6138b7b1198b8d = () -> przeliczony_przepis_af242163baa8415a8b6138b7b1198b8d;

                if (przeliczony_przepis_af242163baa8415a8b6138b7b1198b8d.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_af242163baa8415a8b6138b7b1198b8d)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_af242163baa8415a8b6138b7b1198b8d)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }




                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 29); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_753e277a7a6a4827a13fb93a1d5b7598_skladniki = Set.of(
                        new Skladnik(Produkt.CUKIER_WANILIOWY, 2953),
                        new Skladnik(Produkt.MAKA_ZIEMNIACZANA, 276),
                        new Skladnik(Produkt.MAKA_RAZOWA, 2192),
                        new Skladnik(Produkt.CUKIER_PUDER, 1688),
                        new Skladnik(Produkt.SOL, 2885),
                        new Skladnik(Produkt.MLEKO, 1811),
                        new Skladnik(Produkt.DROZDZE, 1017),
                        new Skladnik(Produkt.WODA, 695),
                        new Skladnik(Produkt.OLEJ_ROSLINNY, 1901),
                        new Skladnik(Produkt.CUKIER, 1787)
                );
                Przepis przepis_753e277a7a6a4827a13fb93a1d5b7598 = () -> przepis_753e277a7a6a4827a13fb93a1d5b7598_skladniki;




                Set<Skladnik> przeliczony_przepis_753e277a7a6a4827a13fb93a1d5b7598 = kuchnia.przeliczPrzepis(przepis_753e277a7a6a4827a13fb93a1d5b7598);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_753e277a7a6a4827a13fb93a1d5b7598);


                Przepis nowyPrzepis_przepis_753e277a7a6a4827a13fb93a1d5b7598 = () -> przeliczony_przepis_753e277a7a6a4827a13fb93a1d5b7598;

                if (przeliczony_przepis_753e277a7a6a4827a13fb93a1d5b7598.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_753e277a7a6a4827a13fb93a1d5b7598)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_753e277a7a6a4827a13fb93a1d5b7598)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }




                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 13); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_e5a1e0fa740f466b8ce7afb40565e141_skladniki = Set.of(
                        new Skladnik(Produkt.CUKIER_WANILIOWY, 643),
                        new Skladnik(Produkt.CUKIER, 565),
                        new Skladnik(Produkt.WODA, 2823),
                        new Skladnik(Produkt.MARGARYNA, 2170),
                        new Skladnik(Produkt.RODZYNKI, 2964),
                        new Skladnik(Produkt.PROSZEK_DO_PIECZENIA, 2578)
                );
                Przepis przepis_e5a1e0fa740f466b8ce7afb40565e141 = () -> przepis_e5a1e0fa740f466b8ce7afb40565e141_skladniki;




                Set<Skladnik> przeliczony_przepis_e5a1e0fa740f466b8ce7afb40565e141 = kuchnia.przeliczPrzepis(przepis_e5a1e0fa740f466b8ce7afb40565e141);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_e5a1e0fa740f466b8ce7afb40565e141);


                Przepis nowyPrzepis_przepis_e5a1e0fa740f466b8ce7afb40565e141 = () -> przeliczony_przepis_e5a1e0fa740f466b8ce7afb40565e141;

                if (przeliczony_przepis_e5a1e0fa740f466b8ce7afb40565e141.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_e5a1e0fa740f466b8ce7afb40565e141)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_e5a1e0fa740f466b8ce7afb40565e141)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }




                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 56); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_ad4216836a8f492fa112910196aa938f_skladniki = Set.of(
                        new Skladnik(Produkt.WODA, 147),
                        new Skladnik(Produkt.PROSZEK_DO_PIECZENIA, 408),
                        new Skladnik(Produkt.MAKA_ZIEMNIACZANA, 1033),
                        new Skladnik(Produkt.MLEKO, 1557),
                        new Skladnik(Produkt.CUKIER_WANILIOWY, 1728),
                        new Skladnik(Produkt.TWAROG, 866),
                        new Skladnik(Produkt.OLEJ_ROSLINNY, 1176),
                        new Skladnik(Produkt.CUKIER_PUDER, 742),
                        new Skladnik(Produkt.DROZDZE, 460)
                );
                Przepis przepis_ad4216836a8f492fa112910196aa938f = () -> przepis_ad4216836a8f492fa112910196aa938f_skladniki;




                Set<Skladnik> przeliczony_przepis_ad4216836a8f492fa112910196aa938f = kuchnia.przeliczPrzepis(przepis_ad4216836a8f492fa112910196aa938f);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_ad4216836a8f492fa112910196aa938f);


                Przepis nowyPrzepis_przepis_ad4216836a8f492fa112910196aa938f = () -> przeliczony_przepis_ad4216836a8f492fa112910196aa938f;

                if (przeliczony_przepis_ad4216836a8f492fa112910196aa938f.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_ad4216836a8f492fa112910196aa938f)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_ad4216836a8f492fa112910196aa938f)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }




                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 34); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_e8b7b073395e43bd8f6f488e0899241b_skladniki = Set.of(
                        new Skladnik(Produkt.MASLO, 2699),
                        new Skladnik(Produkt.CUKIER_PUDER, 593),
                        new Skladnik(Produkt.WODA, 2236),
                        new Skladnik(Produkt.OLEJ_ROSLINNY, 2713),
                        new Skladnik(Produkt.SOL, 734),
                        new Skladnik(Produkt.DROZDZE, 2658),
                        new Skladnik(Produkt.MAKA_PSZENNA, 662),
                        new Skladnik(Produkt.CUKIER_WANILIOWY, 1460)
                );
                Przepis przepis_e8b7b073395e43bd8f6f488e0899241b = () -> przepis_e8b7b073395e43bd8f6f488e0899241b_skladniki;




                Set<Skladnik> przeliczony_przepis_e8b7b073395e43bd8f6f488e0899241b = kuchnia.przeliczPrzepis(przepis_e8b7b073395e43bd8f6f488e0899241b);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_e8b7b073395e43bd8f6f488e0899241b);


                Przepis nowyPrzepis_przepis_e8b7b073395e43bd8f6f488e0899241b = () -> przeliczony_przepis_e8b7b073395e43bd8f6f488e0899241b;

                if (przeliczony_przepis_e8b7b073395e43bd8f6f488e0899241b.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_e8b7b073395e43bd8f6f488e0899241b)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_e8b7b073395e43bd8f6f488e0899241b)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }




                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 44); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_90bc79a5ebda43db9666bbb08a855fe9_skladniki = Set.of(
                        new Skladnik(Produkt.PROSZEK_DO_PIECZENIA, 687),
                        new Skladnik(Produkt.DROZDZE, 2147),
                        new Skladnik(Produkt.MLEKO, 381),
                        new Skladnik(Produkt.WODA, 594),
                        new Skladnik(Produkt.MAKA_RAZOWA, 23),
                        new Skladnik(Produkt.MARGARYNA, 1224),
                        new Skladnik(Produkt.MAKA_ZIEMNIACZANA, 2609),
                        new Skladnik(Produkt.MASLO, 2554),
                        new Skladnik(Produkt.MAKA_PSZENNA, 1650),
                        new Skladnik(Produkt.SOL, 98)
                );
                Przepis przepis_90bc79a5ebda43db9666bbb08a855fe9 = () -> przepis_90bc79a5ebda43db9666bbb08a855fe9_skladniki;




                Set<Skladnik> przeliczony_przepis_90bc79a5ebda43db9666bbb08a855fe9 = kuchnia.przeliczPrzepis(przepis_90bc79a5ebda43db9666bbb08a855fe9);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_90bc79a5ebda43db9666bbb08a855fe9);


                Przepis nowyPrzepis_przepis_90bc79a5ebda43db9666bbb08a855fe9 = () -> przeliczony_przepis_90bc79a5ebda43db9666bbb08a855fe9;

                if (przeliczony_przepis_90bc79a5ebda43db9666bbb08a855fe9.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_90bc79a5ebda43db9666bbb08a855fe9)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_90bc79a5ebda43db9666bbb08a855fe9)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }




                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 79); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_4078bf974c0c4ad29879fd40832c4bfb_skladniki = Set.of(
                        new Skladnik(Produkt.OLEJ_ROSLINNY, 488),
                        new Skladnik(Produkt.DROZDZE, 2507),
                        new Skladnik(Produkt.CUKIER_PUDER, 1407),
                        new Skladnik(Produkt.SOL, 2359),
                        new Skladnik(Produkt.TWAROG, 2956),
                        new Skladnik(Produkt.MAKA_RAZOWA, 2932),
                        new Skladnik(Produkt.MASLO, 531),
                        new Skladnik(Produkt.CUKIER_WANILIOWY, 2464)
                );
                Przepis przepis_4078bf974c0c4ad29879fd40832c4bfb = () -> przepis_4078bf974c0c4ad29879fd40832c4bfb_skladniki;




                Set<Skladnik> przeliczony_przepis_4078bf974c0c4ad29879fd40832c4bfb = kuchnia.przeliczPrzepis(przepis_4078bf974c0c4ad29879fd40832c4bfb);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_4078bf974c0c4ad29879fd40832c4bfb);


                Przepis nowyPrzepis_przepis_4078bf974c0c4ad29879fd40832c4bfb = () -> przeliczony_przepis_4078bf974c0c4ad29879fd40832c4bfb;

                if (przeliczony_przepis_4078bf974c0c4ad29879fd40832c4bfb.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_4078bf974c0c4ad29879fd40832c4bfb)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_4078bf974c0c4ad29879fd40832c4bfb)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }




                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 99); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_78de51a0428e4590bdc219103fdc80d9_skladniki = Set.of(
                        new Skladnik(Produkt.MAKA_ZIEMNIACZANA, 1302),
                        new Skladnik(Produkt.WODA, 1298),
                        new Skladnik(Produkt.RODZYNKI, 1501),
                        new Skladnik(Produkt.OLEJ_ROSLINNY, 2651),
                        new Skladnik(Produkt.MLEKO, 1936),
                        new Skladnik(Produkt.SOL, 2681)
                );
                Przepis przepis_78de51a0428e4590bdc219103fdc80d9 = () -> przepis_78de51a0428e4590bdc219103fdc80d9_skladniki;




                Set<Skladnik> przeliczony_przepis_78de51a0428e4590bdc219103fdc80d9 = kuchnia.przeliczPrzepis(przepis_78de51a0428e4590bdc219103fdc80d9);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_78de51a0428e4590bdc219103fdc80d9);


                Przepis nowyPrzepis_przepis_78de51a0428e4590bdc219103fdc80d9 = () -> przeliczony_przepis_78de51a0428e4590bdc219103fdc80d9;

                if (przeliczony_przepis_78de51a0428e4590bdc219103fdc80d9.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_78de51a0428e4590bdc219103fdc80d9)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_78de51a0428e4590bdc219103fdc80d9)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }




                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 43); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_c8c9133d9d4a4f26b10028e8ee303308_skladniki = Set.of(
                        new Skladnik(Produkt.MASLO, 2337),
                        new Skladnik(Produkt.MLEKO, 1951),
                        new Skladnik(Produkt.CUKIER_PUDER, 106),
                        new Skladnik(Produkt.WODA, 193),
                        new Skladnik(Produkt.MAKA_RAZOWA, 2794),
                        new Skladnik(Produkt.CUKIER, 1969),
                        new Skladnik(Produkt.PROSZEK_DO_PIECZENIA, 668),
                        new Skladnik(Produkt.TWAROG, 1774)
                );
                Przepis przepis_c8c9133d9d4a4f26b10028e8ee303308 = () -> przepis_c8c9133d9d4a4f26b10028e8ee303308_skladniki;




                Set<Skladnik> przeliczony_przepis_c8c9133d9d4a4f26b10028e8ee303308 = kuchnia.przeliczPrzepis(przepis_c8c9133d9d4a4f26b10028e8ee303308);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_c8c9133d9d4a4f26b10028e8ee303308);


                Przepis nowyPrzepis_przepis_c8c9133d9d4a4f26b10028e8ee303308 = () -> przeliczony_przepis_c8c9133d9d4a4f26b10028e8ee303308;

                if (przeliczony_przepis_c8c9133d9d4a4f26b10028e8ee303308.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_c8c9133d9d4a4f26b10028e8ee303308)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_c8c9133d9d4a4f26b10028e8ee303308)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }




                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 69); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_8a0f6f260f104719860480d580834bb4_skladniki = Set.of(
                        new Skladnik(Produkt.TWAROG, 2264),
                        new Skladnik(Produkt.MARGARYNA, 2961),
                        new Skladnik(Produkt.CUKIER_PUDER, 2149),
                        new Skladnik(Produkt.WODA, 905),
                        new Skladnik(Produkt.MAKA_ZIEMNIACZANA, 1150),
                        new Skladnik(Produkt.CUKIER_WANILIOWY, 234),
                        new Skladnik(Produkt.RODZYNKI, 1015),
                        new Skladnik(Produkt.MLEKO, 2402),
                        new Skladnik(Produkt.CUKIER, 741),
                        new Skladnik(Produkt.OLEJ_ROSLINNY, 208)
                );
                Przepis przepis_8a0f6f260f104719860480d580834bb4 = () -> przepis_8a0f6f260f104719860480d580834bb4_skladniki;




                Set<Skladnik> przeliczony_przepis_8a0f6f260f104719860480d580834bb4 = kuchnia.przeliczPrzepis(przepis_8a0f6f260f104719860480d580834bb4);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_8a0f6f260f104719860480d580834bb4);


                Przepis nowyPrzepis_przepis_8a0f6f260f104719860480d580834bb4 = () -> przeliczony_przepis_8a0f6f260f104719860480d580834bb4;

                if (przeliczony_przepis_8a0f6f260f104719860480d580834bb4.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_8a0f6f260f104719860480d580834bb4)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_8a0f6f260f104719860480d580834bb4)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }




                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 27); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_55bfd88be9354208b9dacb6bf93407c6_skladniki = Set.of(
                        new Skladnik(Produkt.RODZYNKI, 2673),
                        new Skladnik(Produkt.MLEKO, 1261),
                        new Skladnik(Produkt.CUKIER_WANILIOWY, 2917),
                        new Skladnik(Produkt.OLEJ_ROSLINNY, 2009),
                        new Skladnik(Produkt.DROZDZE, 2459)
                );
                Przepis przepis_55bfd88be9354208b9dacb6bf93407c6 = () -> przepis_55bfd88be9354208b9dacb6bf93407c6_skladniki;




                Set<Skladnik> przeliczony_przepis_55bfd88be9354208b9dacb6bf93407c6 = kuchnia.przeliczPrzepis(przepis_55bfd88be9354208b9dacb6bf93407c6);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_55bfd88be9354208b9dacb6bf93407c6);


                Przepis nowyPrzepis_przepis_55bfd88be9354208b9dacb6bf93407c6 = () -> przeliczony_przepis_55bfd88be9354208b9dacb6bf93407c6;

                if (przeliczony_przepis_55bfd88be9354208b9dacb6bf93407c6.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_55bfd88be9354208b9dacb6bf93407c6)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_55bfd88be9354208b9dacb6bf93407c6)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }




                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 66); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_032379542d2047acba5cbe2122f43bd1_skladniki = Set.of(
                        new Skladnik(Produkt.MAKA_PSZENNA, 2102),
                        new Skladnik(Produkt.OLEJ_ROSLINNY, 1883),
                        new Skladnik(Produkt.DROZDZE, 1060),
                        new Skladnik(Produkt.PROSZEK_DO_PIECZENIA, 1446),
                        new Skladnik(Produkt.WODA, 1666)
                );
                Przepis przepis_032379542d2047acba5cbe2122f43bd1 = () -> przepis_032379542d2047acba5cbe2122f43bd1_skladniki;




                Set<Skladnik> przeliczony_przepis_032379542d2047acba5cbe2122f43bd1 = kuchnia.przeliczPrzepis(przepis_032379542d2047acba5cbe2122f43bd1);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_032379542d2047acba5cbe2122f43bd1);


                Przepis nowyPrzepis_przepis_032379542d2047acba5cbe2122f43bd1 = () -> przeliczony_przepis_032379542d2047acba5cbe2122f43bd1;

                if (przeliczony_przepis_032379542d2047acba5cbe2122f43bd1.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_032379542d2047acba5cbe2122f43bd1)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_032379542d2047acba5cbe2122f43bd1)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }




                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 95); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_9619f11210484dde803e7b0c54f2ad07_skladniki = Set.of(
                        new Skladnik(Produkt.OLEJ_ROSLINNY, 2183),
                        new Skladnik(Produkt.MAKA_ZIEMNIACZANA, 113),
                        new Skladnik(Produkt.RODZYNKI, 443),
                        new Skladnik(Produkt.CUKIER, 2078),
                        new Skladnik(Produkt.MAKA_PSZENNA, 2869),
                        new Skladnik(Produkt.MARGARYNA, 384),
                        new Skladnik(Produkt.DROZDZE, 2934),
                        new Skladnik(Produkt.CUKIER_PUDER, 1490)
                );
                Przepis przepis_9619f11210484dde803e7b0c54f2ad07 = () -> przepis_9619f11210484dde803e7b0c54f2ad07_skladniki;




                Set<Skladnik> przeliczony_przepis_9619f11210484dde803e7b0c54f2ad07 = kuchnia.przeliczPrzepis(przepis_9619f11210484dde803e7b0c54f2ad07);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_9619f11210484dde803e7b0c54f2ad07);


                Przepis nowyPrzepis_przepis_9619f11210484dde803e7b0c54f2ad07 = () -> przeliczony_przepis_9619f11210484dde803e7b0c54f2ad07;

                if (przeliczony_przepis_9619f11210484dde803e7b0c54f2ad07.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_9619f11210484dde803e7b0c54f2ad07)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_9619f11210484dde803e7b0c54f2ad07)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }




                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 17); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_de5e731726614fc7b9a509d92a1e634f_skladniki = Set.of(
                        new Skladnik(Produkt.CUKIER_PUDER, 2709),
                        new Skladnik(Produkt.RODZYNKI, 2838),
                        new Skladnik(Produkt.CUKIER_WANILIOWY, 1252),
                        new Skladnik(Produkt.DROZDZE, 1438),
                        new Skladnik(Produkt.MARGARYNA, 2248)
                );
                Przepis przepis_de5e731726614fc7b9a509d92a1e634f = () -> przepis_de5e731726614fc7b9a509d92a1e634f_skladniki;




                Set<Skladnik> przeliczony_przepis_de5e731726614fc7b9a509d92a1e634f = kuchnia.przeliczPrzepis(przepis_de5e731726614fc7b9a509d92a1e634f);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_de5e731726614fc7b9a509d92a1e634f);


                Przepis nowyPrzepis_przepis_de5e731726614fc7b9a509d92a1e634f = () -> przeliczony_przepis_de5e731726614fc7b9a509d92a1e634f;

                if (przeliczony_przepis_de5e731726614fc7b9a509d92a1e634f.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_de5e731726614fc7b9a509d92a1e634f)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_de5e731726614fc7b9a509d92a1e634f)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }




                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 86); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_2b5d6f7411fc41779d842527403b432a_skladniki = Set.of(
                        new Skladnik(Produkt.RODZYNKI, 161),
                        new Skladnik(Produkt.MASLO, 2282),
                        new Skladnik(Produkt.CUKIER, 856),
                        new Skladnik(Produkt.PROSZEK_DO_PIECZENIA, 1199),
                        new Skladnik(Produkt.WODA, 217),
                        new Skladnik(Produkt.CUKIER_WANILIOWY, 2749)
                );
                Przepis przepis_2b5d6f7411fc41779d842527403b432a = () -> przepis_2b5d6f7411fc41779d842527403b432a_skladniki;




                Set<Skladnik> przeliczony_przepis_2b5d6f7411fc41779d842527403b432a = kuchnia.przeliczPrzepis(przepis_2b5d6f7411fc41779d842527403b432a);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_2b5d6f7411fc41779d842527403b432a);


                Przepis nowyPrzepis_przepis_2b5d6f7411fc41779d842527403b432a = () -> przeliczony_przepis_2b5d6f7411fc41779d842527403b432a;

                if (przeliczony_przepis_2b5d6f7411fc41779d842527403b432a.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_2b5d6f7411fc41779d842527403b432a)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_2b5d6f7411fc41779d842527403b432a)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }




                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 85); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_20f6eb6e913847c290153c1cbbd344f8_skladniki = Set.of(
                        new Skladnik(Produkt.PROSZEK_DO_PIECZENIA, 389),
                        new Skladnik(Produkt.SOL, 2063),
                        new Skladnik(Produkt.MAKA_ZIEMNIACZANA, 688),
                        new Skladnik(Produkt.WODA, 2375),
                        new Skladnik(Produkt.OLEJ_ROSLINNY, 516),
                        new Skladnik(Produkt.CUKIER_PUDER, 2630),
                        new Skladnik(Produkt.DROZDZE, 1764),
                        new Skladnik(Produkt.TWAROG, 2054),
                        new Skladnik(Produkt.MASLO, 2992),
                        new Skladnik(Produkt.MARGARYNA, 515)
                );
                Przepis przepis_20f6eb6e913847c290153c1cbbd344f8 = () -> przepis_20f6eb6e913847c290153c1cbbd344f8_skladniki;




                Set<Skladnik> przeliczony_przepis_20f6eb6e913847c290153c1cbbd344f8 = kuchnia.przeliczPrzepis(przepis_20f6eb6e913847c290153c1cbbd344f8);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_20f6eb6e913847c290153c1cbbd344f8);


                Przepis nowyPrzepis_przepis_20f6eb6e913847c290153c1cbbd344f8 = () -> przeliczony_przepis_20f6eb6e913847c290153c1cbbd344f8;

                if (przeliczony_przepis_20f6eb6e913847c290153c1cbbd344f8.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_20f6eb6e913847c290153c1cbbd344f8)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_20f6eb6e913847c290153c1cbbd344f8)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }




                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 52); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_db963cc48f8c4e12a90fa1eb3a24ec31_skladniki = Set.of(
                        new Skladnik(Produkt.MAKA_ZIEMNIACZANA, 2098),
                        new Skladnik(Produkt.CUKIER_WANILIOWY, 457),
                        new Skladnik(Produkt.OLEJ_ROSLINNY, 2332),
                        new Skladnik(Produkt.MARGARYNA, 1245),
                        new Skladnik(Produkt.RODZYNKI, 1428),
                        new Skladnik(Produkt.SOL, 2323),
                        new Skladnik(Produkt.DROZDZE, 1813),
                        new Skladnik(Produkt.MAKA_RAZOWA, 77),
                        new Skladnik(Produkt.MLEKO, 633)
                );
                Przepis przepis_db963cc48f8c4e12a90fa1eb3a24ec31 = () -> przepis_db963cc48f8c4e12a90fa1eb3a24ec31_skladniki;




                Set<Skladnik> przeliczony_przepis_db963cc48f8c4e12a90fa1eb3a24ec31 = kuchnia.przeliczPrzepis(przepis_db963cc48f8c4e12a90fa1eb3a24ec31);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_db963cc48f8c4e12a90fa1eb3a24ec31);


                Przepis nowyPrzepis_przepis_db963cc48f8c4e12a90fa1eb3a24ec31 = () -> przeliczony_przepis_db963cc48f8c4e12a90fa1eb3a24ec31;

                if (przeliczony_przepis_db963cc48f8c4e12a90fa1eb3a24ec31.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_db963cc48f8c4e12a90fa1eb3a24ec31)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_db963cc48f8c4e12a90fa1eb3a24ec31)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }




                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 78); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_79bd4b9e8da847a8bdc0c42a250d2c1b_skladniki = Set.of(
                        new Skladnik(Produkt.MAKA_ZIEMNIACZANA, 2204),
                        new Skladnik(Produkt.MARGARYNA, 2159),
                        new Skladnik(Produkt.MAKA_PSZENNA, 1646),
                        new Skladnik(Produkt.CUKIER, 212),
                        new Skladnik(Produkt.DROZDZE, 1601)
                );
                Przepis przepis_79bd4b9e8da847a8bdc0c42a250d2c1b = () -> przepis_79bd4b9e8da847a8bdc0c42a250d2c1b_skladniki;




                Set<Skladnik> przeliczony_przepis_79bd4b9e8da847a8bdc0c42a250d2c1b = kuchnia.przeliczPrzepis(przepis_79bd4b9e8da847a8bdc0c42a250d2c1b);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_79bd4b9e8da847a8bdc0c42a250d2c1b);


                Przepis nowyPrzepis_przepis_79bd4b9e8da847a8bdc0c42a250d2c1b = () -> przeliczony_przepis_79bd4b9e8da847a8bdc0c42a250d2c1b;

                if (przeliczony_przepis_79bd4b9e8da847a8bdc0c42a250d2c1b.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_79bd4b9e8da847a8bdc0c42a250d2c1b)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_79bd4b9e8da847a8bdc0c42a250d2c1b)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }




                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 63); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_ce06f2e3be2f4ba2a5a04e20dcdd68f5_skladniki = Set.of(
                        new Skladnik(Produkt.TWAROG, 96),
                        new Skladnik(Produkt.CUKIER_PUDER, 805),
                        new Skladnik(Produkt.MLEKO, 2665),
                        new Skladnik(Produkt.SOL, 2540),
                        new Skladnik(Produkt.MAKA_ZIEMNIACZANA, 2784),
                        new Skladnik(Produkt.PROSZEK_DO_PIECZENIA, 1438),
                        new Skladnik(Produkt.MAKA_PSZENNA, 2575),
                        new Skladnik(Produkt.OLEJ_ROSLINNY, 2812),
                        new Skladnik(Produkt.MARGARYNA, 1247),
                        new Skladnik(Produkt.CUKIER_WANILIOWY, 2053)
                );
                Przepis przepis_ce06f2e3be2f4ba2a5a04e20dcdd68f5 = () -> przepis_ce06f2e3be2f4ba2a5a04e20dcdd68f5_skladniki;




                Set<Skladnik> przeliczony_przepis_ce06f2e3be2f4ba2a5a04e20dcdd68f5 = kuchnia.przeliczPrzepis(przepis_ce06f2e3be2f4ba2a5a04e20dcdd68f5);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_ce06f2e3be2f4ba2a5a04e20dcdd68f5);


                Przepis nowyPrzepis_przepis_ce06f2e3be2f4ba2a5a04e20dcdd68f5 = () -> przeliczony_przepis_ce06f2e3be2f4ba2a5a04e20dcdd68f5;

                if (przeliczony_przepis_ce06f2e3be2f4ba2a5a04e20dcdd68f5.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_ce06f2e3be2f4ba2a5a04e20dcdd68f5)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_ce06f2e3be2f4ba2a5a04e20dcdd68f5)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }




                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 86); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_2ff62d39162e44c8b2cfea1d4a9d6587_skladniki = Set.of(
                        new Skladnik(Produkt.SOL, 689),
                        new Skladnik(Produkt.DROZDZE, 2966),
                        new Skladnik(Produkt.CUKIER, 771),
                        new Skladnik(Produkt.CUKIER_PUDER, 17),
                        new Skladnik(Produkt.MAKA_ZIEMNIACZANA, 2156),
                        new Skladnik(Produkt.MASLO, 1801),
                        new Skladnik(Produkt.MAKA_RAZOWA, 1591),
                        new Skladnik(Produkt.MLEKO, 796),
                        new Skladnik(Produkt.PROSZEK_DO_PIECZENIA, 516),
                        new Skladnik(Produkt.CUKIER_WANILIOWY, 1805)
                );
                Przepis przepis_2ff62d39162e44c8b2cfea1d4a9d6587 = () -> przepis_2ff62d39162e44c8b2cfea1d4a9d6587_skladniki;




                Set<Skladnik> przeliczony_przepis_2ff62d39162e44c8b2cfea1d4a9d6587 = kuchnia.przeliczPrzepis(przepis_2ff62d39162e44c8b2cfea1d4a9d6587);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_2ff62d39162e44c8b2cfea1d4a9d6587);


                Przepis nowyPrzepis_przepis_2ff62d39162e44c8b2cfea1d4a9d6587 = () -> przeliczony_przepis_2ff62d39162e44c8b2cfea1d4a9d6587;

                if (przeliczony_przepis_2ff62d39162e44c8b2cfea1d4a9d6587.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_2ff62d39162e44c8b2cfea1d4a9d6587)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_2ff62d39162e44c8b2cfea1d4a9d6587)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }




                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 12); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_0a61c4dbd6a84c5591d4e934a20b11a0_skladniki = Set.of(
                        new Skladnik(Produkt.MLEKO, 720),
                        new Skladnik(Produkt.SOL, 2657),
                        new Skladnik(Produkt.OLEJ_ROSLINNY, 1598),
                        new Skladnik(Produkt.DROZDZE, 1592),
                        new Skladnik(Produkt.WODA, 2518),
                        new Skladnik(Produkt.MAKA_ZIEMNIACZANA, 2600)
                );
                Przepis przepis_0a61c4dbd6a84c5591d4e934a20b11a0 = () -> przepis_0a61c4dbd6a84c5591d4e934a20b11a0_skladniki;




                Set<Skladnik> przeliczony_przepis_0a61c4dbd6a84c5591d4e934a20b11a0 = kuchnia.przeliczPrzepis(przepis_0a61c4dbd6a84c5591d4e934a20b11a0);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_0a61c4dbd6a84c5591d4e934a20b11a0);


                Przepis nowyPrzepis_przepis_0a61c4dbd6a84c5591d4e934a20b11a0 = () -> przeliczony_przepis_0a61c4dbd6a84c5591d4e934a20b11a0;

                if (przeliczony_przepis_0a61c4dbd6a84c5591d4e934a20b11a0.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_0a61c4dbd6a84c5591d4e934a20b11a0)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_0a61c4dbd6a84c5591d4e934a20b11a0)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }




                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 62); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_05c5ad98d39a46dcb2848d12e85742df_skladniki = Set.of(
                        new Skladnik(Produkt.PROSZEK_DO_PIECZENIA, 2232),
                        new Skladnik(Produkt.DROZDZE, 2362),
                        new Skladnik(Produkt.CUKIER_PUDER, 1051),
                        new Skladnik(Produkt.MLEKO, 1280),
                        new Skladnik(Produkt.OLEJ_ROSLINNY, 501),
                        new Skladnik(Produkt.MAKA_PSZENNA, 337),
                        new Skladnik(Produkt.SOL, 1840),
                        new Skladnik(Produkt.MASLO, 882)
                );
                Przepis przepis_05c5ad98d39a46dcb2848d12e85742df = () -> przepis_05c5ad98d39a46dcb2848d12e85742df_skladniki;




                Set<Skladnik> przeliczony_przepis_05c5ad98d39a46dcb2848d12e85742df = kuchnia.przeliczPrzepis(przepis_05c5ad98d39a46dcb2848d12e85742df);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_05c5ad98d39a46dcb2848d12e85742df);


                Przepis nowyPrzepis_przepis_05c5ad98d39a46dcb2848d12e85742df = () -> przeliczony_przepis_05c5ad98d39a46dcb2848d12e85742df;

                if (przeliczony_przepis_05c5ad98d39a46dcb2848d12e85742df.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_05c5ad98d39a46dcb2848d12e85742df)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_05c5ad98d39a46dcb2848d12e85742df)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }




                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 95); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_09549b82392c45bba6ea19b648189f47_skladniki = Set.of(
                        new Skladnik(Produkt.DROZDZE, 244),
                        new Skladnik(Produkt.SOL, 2483),
                        new Skladnik(Produkt.CUKIER_WANILIOWY, 2310),
                        new Skladnik(Produkt.RODZYNKI, 1375),
                        new Skladnik(Produkt.WODA, 2130),
                        new Skladnik(Produkt.MAKA_RAZOWA, 1271),
                        new Skladnik(Produkt.MARGARYNA, 1705),
                        new Skladnik(Produkt.CUKIER_PUDER, 1455),
                        new Skladnik(Produkt.CUKIER, 1554)
                );
                Przepis przepis_09549b82392c45bba6ea19b648189f47 = () -> przepis_09549b82392c45bba6ea19b648189f47_skladniki;




                Set<Skladnik> przeliczony_przepis_09549b82392c45bba6ea19b648189f47 = kuchnia.przeliczPrzepis(przepis_09549b82392c45bba6ea19b648189f47);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_09549b82392c45bba6ea19b648189f47);


                Przepis nowyPrzepis_przepis_09549b82392c45bba6ea19b648189f47 = () -> przeliczony_przepis_09549b82392c45bba6ea19b648189f47;

                if (przeliczony_przepis_09549b82392c45bba6ea19b648189f47.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_09549b82392c45bba6ea19b648189f47)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_09549b82392c45bba6ea19b648189f47)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }




                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 46); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_e4f31ea1e40b49088367d50c52709785_skladniki = Set.of(
                        new Skladnik(Produkt.MARGARYNA, 1520),
                        new Skladnik(Produkt.PROSZEK_DO_PIECZENIA, 1886),
                        new Skladnik(Produkt.MLEKO, 2120),
                        new Skladnik(Produkt.SOL, 869),
                        new Skladnik(Produkt.TWAROG, 2595),
                        new Skladnik(Produkt.DROZDZE, 1496),
                        new Skladnik(Produkt.OLEJ_ROSLINNY, 100),
                        new Skladnik(Produkt.CUKIER_PUDER, 1775)
                );
                Przepis przepis_e4f31ea1e40b49088367d50c52709785 = () -> przepis_e4f31ea1e40b49088367d50c52709785_skladniki;




                Set<Skladnik> przeliczony_przepis_e4f31ea1e40b49088367d50c52709785 = kuchnia.przeliczPrzepis(przepis_e4f31ea1e40b49088367d50c52709785);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_e4f31ea1e40b49088367d50c52709785);


                Przepis nowyPrzepis_przepis_e4f31ea1e40b49088367d50c52709785 = () -> przeliczony_przepis_e4f31ea1e40b49088367d50c52709785;

                if (przeliczony_przepis_e4f31ea1e40b49088367d50c52709785.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_e4f31ea1e40b49088367d50c52709785)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_e4f31ea1e40b49088367d50c52709785)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }




                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 86); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_671ab4ae23de4c6286a0edbdeff85fe0_skladniki = Set.of(
                        new Skladnik(Produkt.SOL, 2221),
                        new Skladnik(Produkt.MAKA_RAZOWA, 2229),
                        new Skladnik(Produkt.MASLO, 1290),
                        new Skladnik(Produkt.MLEKO, 635),
                        new Skladnik(Produkt.MAKA_ZIEMNIACZANA, 2324),
                        new Skladnik(Produkt.DROZDZE, 1788)
                );
                Przepis przepis_671ab4ae23de4c6286a0edbdeff85fe0 = () -> przepis_671ab4ae23de4c6286a0edbdeff85fe0_skladniki;




                Set<Skladnik> przeliczony_przepis_671ab4ae23de4c6286a0edbdeff85fe0 = kuchnia.przeliczPrzepis(przepis_671ab4ae23de4c6286a0edbdeff85fe0);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_671ab4ae23de4c6286a0edbdeff85fe0);


                Przepis nowyPrzepis_przepis_671ab4ae23de4c6286a0edbdeff85fe0 = () -> przeliczony_przepis_671ab4ae23de4c6286a0edbdeff85fe0;

                if (przeliczony_przepis_671ab4ae23de4c6286a0edbdeff85fe0.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_671ab4ae23de4c6286a0edbdeff85fe0)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_671ab4ae23de4c6286a0edbdeff85fe0)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }




                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 74); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_1eb9a70c17784f44972fba03eba17a38_skladniki = Set.of(
                        new Skladnik(Produkt.MASLO, 3),
                        new Skladnik(Produkt.CUKIER_WANILIOWY, 1565),
                        new Skladnik(Produkt.WODA, 1789),
                        new Skladnik(Produkt.TWAROG, 266),
                        new Skladnik(Produkt.RODZYNKI, 2799),
                        new Skladnik(Produkt.MAKA_ZIEMNIACZANA, 2448),
                        new Skladnik(Produkt.SOL, 1582),
                        new Skladnik(Produkt.MLEKO, 1707),
                        new Skladnik(Produkt.MAKA_PSZENNA, 1905),
                        new Skladnik(Produkt.CUKIER_PUDER, 980)
                );
                Przepis przepis_1eb9a70c17784f44972fba03eba17a38 = () -> przepis_1eb9a70c17784f44972fba03eba17a38_skladniki;




                Set<Skladnik> przeliczony_przepis_1eb9a70c17784f44972fba03eba17a38 = kuchnia.przeliczPrzepis(przepis_1eb9a70c17784f44972fba03eba17a38);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_1eb9a70c17784f44972fba03eba17a38);


                Przepis nowyPrzepis_przepis_1eb9a70c17784f44972fba03eba17a38 = () -> przeliczony_przepis_1eb9a70c17784f44972fba03eba17a38;

                if (przeliczony_przepis_1eb9a70c17784f44972fba03eba17a38.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_1eb9a70c17784f44972fba03eba17a38)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_1eb9a70c17784f44972fba03eba17a38)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }




                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 65); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_a1f0ad4076dd4dbdbb56d0832e7af56b_skladniki = Set.of(
                        new Skladnik(Produkt.SOL, 2207),
                        new Skladnik(Produkt.MARGARYNA, 1017),
                        new Skladnik(Produkt.MAKA_ZIEMNIACZANA, 538),
                        new Skladnik(Produkt.CUKIER_PUDER, 2467),
                        new Skladnik(Produkt.CUKIER, 2591)
                );
                Przepis przepis_a1f0ad4076dd4dbdbb56d0832e7af56b = () -> przepis_a1f0ad4076dd4dbdbb56d0832e7af56b_skladniki;




                Set<Skladnik> przeliczony_przepis_a1f0ad4076dd4dbdbb56d0832e7af56b = kuchnia.przeliczPrzepis(przepis_a1f0ad4076dd4dbdbb56d0832e7af56b);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_a1f0ad4076dd4dbdbb56d0832e7af56b);


                Przepis nowyPrzepis_przepis_a1f0ad4076dd4dbdbb56d0832e7af56b = () -> przeliczony_przepis_a1f0ad4076dd4dbdbb56d0832e7af56b;

                if (przeliczony_przepis_a1f0ad4076dd4dbdbb56d0832e7af56b.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_a1f0ad4076dd4dbdbb56d0832e7af56b)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_a1f0ad4076dd4dbdbb56d0832e7af56b)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }




                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 69); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_e7ea41b4242444e19ba32f6bfba94794_skladniki = Set.of(
                        new Skladnik(Produkt.CUKIER_WANILIOWY, 2566),
                        new Skladnik(Produkt.PROSZEK_DO_PIECZENIA, 2896),
                        new Skladnik(Produkt.MAKA_RAZOWA, 1431),
                        new Skladnik(Produkt.CUKIER_PUDER, 2215),
                        new Skladnik(Produkt.MASLO, 2857),
                        new Skladnik(Produkt.WODA, 1128),
                        new Skladnik(Produkt.RODZYNKI, 287)
                );
                Przepis przepis_e7ea41b4242444e19ba32f6bfba94794 = () -> przepis_e7ea41b4242444e19ba32f6bfba94794_skladniki;




                Set<Skladnik> przeliczony_przepis_e7ea41b4242444e19ba32f6bfba94794 = kuchnia.przeliczPrzepis(przepis_e7ea41b4242444e19ba32f6bfba94794);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_e7ea41b4242444e19ba32f6bfba94794);


                Przepis nowyPrzepis_przepis_e7ea41b4242444e19ba32f6bfba94794 = () -> przeliczony_przepis_e7ea41b4242444e19ba32f6bfba94794;

                if (przeliczony_przepis_e7ea41b4242444e19ba32f6bfba94794.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_e7ea41b4242444e19ba32f6bfba94794)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_e7ea41b4242444e19ba32f6bfba94794)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }




                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 28); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_f48c33ca1e884c5eb029d6ca5d5f8a38_skladniki = Set.of(
                        new Skladnik(Produkt.MASLO, 126),
                        new Skladnik(Produkt.MAKA_PSZENNA, 675),
                        new Skladnik(Produkt.RODZYNKI, 1283),
                        new Skladnik(Produkt.TWAROG, 1654),
                        new Skladnik(Produkt.SOL, 2234),
                        new Skladnik(Produkt.OLEJ_ROSLINNY, 1879),
                        new Skladnik(Produkt.CUKIER, 2085)
                );
                Przepis przepis_f48c33ca1e884c5eb029d6ca5d5f8a38 = () -> przepis_f48c33ca1e884c5eb029d6ca5d5f8a38_skladniki;




                Set<Skladnik> przeliczony_przepis_f48c33ca1e884c5eb029d6ca5d5f8a38 = kuchnia.przeliczPrzepis(przepis_f48c33ca1e884c5eb029d6ca5d5f8a38);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_f48c33ca1e884c5eb029d6ca5d5f8a38);


                Przepis nowyPrzepis_przepis_f48c33ca1e884c5eb029d6ca5d5f8a38 = () -> przeliczony_przepis_f48c33ca1e884c5eb029d6ca5d5f8a38;

                if (przeliczony_przepis_f48c33ca1e884c5eb029d6ca5d5f8a38.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_f48c33ca1e884c5eb029d6ca5d5f8a38)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_f48c33ca1e884c5eb029d6ca5d5f8a38)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }




                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 23); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_f38187e5113a4b79a33edb872006ebae_skladniki = Set.of(
                        new Skladnik(Produkt.MAKA_PSZENNA, 122),
                        new Skladnik(Produkt.MASLO, 2495),
                        new Skladnik(Produkt.MAKA_ZIEMNIACZANA, 2056),
                        new Skladnik(Produkt.CUKIER, 1001),
                        new Skladnik(Produkt.MARGARYNA, 2964),
                        new Skladnik(Produkt.DROZDZE, 1833),
                        new Skladnik(Produkt.MLEKO, 810),
                        new Skladnik(Produkt.CUKIER_PUDER, 2706),
                        new Skladnik(Produkt.PROSZEK_DO_PIECZENIA, 1128),
                        new Skladnik(Produkt.OLEJ_ROSLINNY, 2624)
                );
                Przepis przepis_f38187e5113a4b79a33edb872006ebae = () -> przepis_f38187e5113a4b79a33edb872006ebae_skladniki;




                Set<Skladnik> przeliczony_przepis_f38187e5113a4b79a33edb872006ebae = kuchnia.przeliczPrzepis(przepis_f38187e5113a4b79a33edb872006ebae);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_f38187e5113a4b79a33edb872006ebae);


                Przepis nowyPrzepis_przepis_f38187e5113a4b79a33edb872006ebae = () -> przeliczony_przepis_f38187e5113a4b79a33edb872006ebae;

                if (przeliczony_przepis_f38187e5113a4b79a33edb872006ebae.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_f38187e5113a4b79a33edb872006ebae)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_f38187e5113a4b79a33edb872006ebae)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }




                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 30); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_2811d1fdee534ead94f7bc4afa22cbad_skladniki = Set.of(
                        new Skladnik(Produkt.OLEJ_ROSLINNY, 1929),
                        new Skladnik(Produkt.CUKIER_PUDER, 962),
                        new Skladnik(Produkt.MLEKO, 962),
                        new Skladnik(Produkt.TWAROG, 546),
                        new Skladnik(Produkt.CUKIER, 1411),
                        new Skladnik(Produkt.MARGARYNA, 2317)
                );
                Przepis przepis_2811d1fdee534ead94f7bc4afa22cbad = () -> przepis_2811d1fdee534ead94f7bc4afa22cbad_skladniki;




                Set<Skladnik> przeliczony_przepis_2811d1fdee534ead94f7bc4afa22cbad = kuchnia.przeliczPrzepis(przepis_2811d1fdee534ead94f7bc4afa22cbad);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_2811d1fdee534ead94f7bc4afa22cbad);


                Przepis nowyPrzepis_przepis_2811d1fdee534ead94f7bc4afa22cbad = () -> przeliczony_przepis_2811d1fdee534ead94f7bc4afa22cbad;

                if (przeliczony_przepis_2811d1fdee534ead94f7bc4afa22cbad.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_2811d1fdee534ead94f7bc4afa22cbad)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_2811d1fdee534ead94f7bc4afa22cbad)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }




                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 14); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_5f6ca94e89f54474b264758953830848_skladniki = Set.of(
                        new Skladnik(Produkt.TWAROG, 176),
                        new Skladnik(Produkt.RODZYNKI, 1284),
                        new Skladnik(Produkt.MLEKO, 2851),
                        new Skladnik(Produkt.PROSZEK_DO_PIECZENIA, 2123),
                        new Skladnik(Produkt.SOL, 2420),
                        new Skladnik(Produkt.CUKIER, 2615),
                        new Skladnik(Produkt.OLEJ_ROSLINNY, 1300),
                        new Skladnik(Produkt.MASLO, 1391)
                );
                Przepis przepis_5f6ca94e89f54474b264758953830848 = () -> przepis_5f6ca94e89f54474b264758953830848_skladniki;




                Set<Skladnik> przeliczony_przepis_5f6ca94e89f54474b264758953830848 = kuchnia.przeliczPrzepis(przepis_5f6ca94e89f54474b264758953830848);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_5f6ca94e89f54474b264758953830848);


                Przepis nowyPrzepis_przepis_5f6ca94e89f54474b264758953830848 = () -> przeliczony_przepis_5f6ca94e89f54474b264758953830848;

                if (przeliczony_przepis_5f6ca94e89f54474b264758953830848.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_5f6ca94e89f54474b264758953830848)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_5f6ca94e89f54474b264758953830848)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }




                for (Produkt produkt : Produkt.values()) {
                    kuchnia.dodajDoSpizarni(produkt, 77); // Losowa wartość od 0 do 2000
                }


                Set<Skladnik> przepis_8db060a658494fa1baf5f1b622804f08_skladniki = Set.of(
                        new Skladnik(Produkt.CUKIER_PUDER, 29),
                        new Skladnik(Produkt.WODA, 2206),
                        new Skladnik(Produkt.MASLO, 184),
                        new Skladnik(Produkt.TWAROG, 2701),
                        new Skladnik(Produkt.SOL, 733),
                        new Skladnik(Produkt.DROZDZE, 1462),
                        new Skladnik(Produkt.MARGARYNA, 636),
                        new Skladnik(Produkt.PROSZEK_DO_PIECZENIA, 2210),
                        new Skladnik(Produkt.CUKIER_WANILIOWY, 1484)
                );
                Przepis przepis_8db060a658494fa1baf5f1b622804f08 = () -> przepis_8db060a658494fa1baf5f1b622804f08_skladniki;




                Set<Skladnik> przeliczony_przepis_8db060a658494fa1baf5f1b622804f08 = kuchnia.przeliczPrzepis(przepis_8db060a658494fa1baf5f1b622804f08);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_8db060a658494fa1baf5f1b622804f08);


                Przepis nowyPrzepis_przepis_8db060a658494fa1baf5f1b622804f08 = () -> przeliczony_przepis_8db060a658494fa1baf5f1b622804f08;

                if (przeliczony_przepis_8db060a658494fa1baf5f1b622804f08.isEmpty()) {
                    // Przygotowanie potrawy na podstawie oryginalnego przepisu
                    if (kuchnia.wykonaj(przepis_8db060a658494fa1baf5f1b622804f08)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    // Przygotowanie potrawy na podstawie optymalnego przepisu
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_8db060a658494fa1baf5f1b622804f08)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }



            }


            public static void grzanie4()
            {
                // Wygenerowany kod
                Kuchnia kuchnia = new EKuchnia(); // Przyklad implementacji Kuchni

                kuchnia.dodajDoSpizarni(Produkt.CUKIER, 140);
                kuchnia.dodajDoSpizarni(Produkt.MAKA_PSZENNA, 171);
                kuchnia.dodajDoSpizarni(Produkt.MAKA_RAZOWA, 31);
                kuchnia.dodajDoSpizarni(Produkt.MASLO, 157);
                kuchnia.dodajDoSpizarni(Produkt.WODA, 27);
                kuchnia.dodajDoSpizarni(Produkt.SOL, 33);
                kuchnia.dodajDoSpizarni(Produkt.DROZDZE, 193);
                kuchnia.dodajDoSpizarni(Produkt.PROSZEK_DO_PIECZENIA, 39);
                kuchnia.dodajDoSpizarni(Produkt.CUKIER_PUDER, 14);
                kuchnia.dodajDoSpizarni(Produkt.MLEKO, 55);
                kuchnia.dodajDoSpizarni(Produkt.OLEJ_ROSLINNY, 148);
                kuchnia.dodajDoSpizarni(Produkt.CUKIER_WANILIOWY, 9);
                kuchnia.dodajDoSpizarni(Produkt.MAKA_ZIEMNIACZANA, 15);
                kuchnia.dodajDoSpizarni(Produkt.TWAROG, 199);
                kuchnia.dodajDoSpizarni(Produkt.RODZYNKI, 29);
                kuchnia.dodajDoSpizarni(Produkt.MARGARYNA, 155);

                Set<Skladnik> przepis_baa7d2ec48db4868a2b3eb2054af4cb3_skladniki = Set.of(
                        new Skladnik(Produkt.CUKIER_WANILIOWY, 317),
                        new Skladnik(Produkt.MASLO, 27),
                        new Skladnik(Produkt.MARGARYNA, 6),
                        new Skladnik(Produkt.PROSZEK_DO_PIECZENIA, 189),
                        new Skladnik(Produkt.PROSZEK_DO_PIECZENIA, 222),
                        new Skladnik(Produkt.MAKA_PSZENNA, 292)
                );
                Przepis przepis_baa7d2ec48db4868a2b3eb2054af4cb3 = () -> przepis_baa7d2ec48db4868a2b3eb2054af4cb3_skladniki;

                Set<Skladnik> przeliczony_przepis_baa7d2ec48db4868a2b3eb2054af4cb3 = kuchnia.przeliczPrzepis(przepis_baa7d2ec48db4868a2b3eb2054af4cb3);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_baa7d2ec48db4868a2b3eb2054af4cb3);
                Przepis nowyPrzepis_przepis_baa7d2ec48db4868a2b3eb2054af4cb3 = () -> przeliczony_przepis_baa7d2ec48db4868a2b3eb2054af4cb3;
                if (przeliczony_przepis_baa7d2ec48db4868a2b3eb2054af4cb3.isEmpty()) {
                    if (kuchnia.wykonaj(przepis_baa7d2ec48db4868a2b3eb2054af4cb3)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_baa7d2ec48db4868a2b3eb2054af4cb3)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }




                kuchnia.dodajDoSpizarni(Produkt.CUKIER, 26);
                kuchnia.dodajDoSpizarni(Produkt.MAKA_PSZENNA, 155);
                kuchnia.dodajDoSpizarni(Produkt.MAKA_RAZOWA, 13);
                kuchnia.dodajDoSpizarni(Produkt.MASLO, 3);

                kuchnia.dodajDoSpizarni(Produkt.MARGARYNA, 197);

                Set<Skladnik> przepis_5ea565eb665240f897551e3120af7ad3_skladniki = Set.of(
                        new Skladnik(Produkt.MARGARYNA, 341),
                        new Skladnik(Produkt.RODZYNKI, 363),
                        new Skladnik(Produkt.MLEKO, 55),
                        new Skladnik(Produkt.MLEKO, 191),
                        new Skladnik(Produkt.TWAROG, 237)
                );
                Przepis przepis_5ea565eb665240f897551e3120af7ad3 = () -> przepis_5ea565eb665240f897551e3120af7ad3_skladniki;

                Set<Skladnik> przeliczony_przepis_5ea565eb665240f897551e3120af7ad3 = kuchnia.przeliczPrzepis(przepis_5ea565eb665240f897551e3120af7ad3);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_5ea565eb665240f897551e3120af7ad3);
                Przepis nowyPrzepis_przepis_5ea565eb665240f897551e3120af7ad3 = () -> przeliczony_przepis_5ea565eb665240f897551e3120af7ad3;
                if (przeliczony_przepis_5ea565eb665240f897551e3120af7ad3.isEmpty()) {
                    if (kuchnia.wykonaj(przepis_5ea565eb665240f897551e3120af7ad3)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_5ea565eb665240f897551e3120af7ad3)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }




                kuchnia.dodajDoSpizarni(Produkt.CUKIER, 39);
                kuchnia.dodajDoSpizarni(Produkt.MAKA_ZIEMNIACZANA, 65);
                kuchnia.dodajDoSpizarni(Produkt.TWAROG, 97);
                kuchnia.dodajDoSpizarni(Produkt.RODZYNKI, 32);
                kuchnia.dodajDoSpizarni(Produkt.MARGARYNA, 172);

                Set<Skladnik> przepis_96fc7712ac254136a692e09a0860ff59_skladniki = Set.of(
                        new Skladnik(Produkt.MARGARYNA, 90),
                        new Skladnik(Produkt.TWAROG, 257),
                        new Skladnik(Produkt.PROSZEK_DO_PIECZENIA, 259),
                        new Skladnik(Produkt.MAKA_PSZENNA, 40),
                        new Skladnik(Produkt.CUKIER_PUDER, 40),
                        new Skladnik(Produkt.OLEJ_ROSLINNY, 254),
                        new Skladnik(Produkt.SOL, 334),
                        new Skladnik(Produkt.MAKA_ZIEMNIACZANA, 21)
                );
                Przepis przepis_96fc7712ac254136a692e09a0860ff59 = () -> przepis_96fc7712ac254136a692e09a0860ff59_skladniki;

                Set<Skladnik> przeliczony_przepis_96fc7712ac254136a692e09a0860ff59 = kuchnia.przeliczPrzepis(przepis_96fc7712ac254136a692e09a0860ff59);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_96fc7712ac254136a692e09a0860ff59);
                Przepis nowyPrzepis_przepis_96fc7712ac254136a692e09a0860ff59 = () -> przeliczony_przepis_96fc7712ac254136a692e09a0860ff59;
                if (przeliczony_przepis_96fc7712ac254136a692e09a0860ff59.isEmpty()) {
                    if (kuchnia.wykonaj(przepis_96fc7712ac254136a692e09a0860ff59)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_96fc7712ac254136a692e09a0860ff59)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }



                kuchnia.dodajDoSpizarni(Produkt.RODZYNKI, 158);
                kuchnia.dodajDoSpizarni(Produkt.MARGARYNA, 129);

                Set<Skladnik> przepis_243ec520961646cc9d2f0e668a6d70e9_skladniki = Set.of(
                        new Skladnik(Produkt.MAKA_ZIEMNIACZANA, 70),
                        new Skladnik(Produkt.MAKA_ZIEMNIACZANA, 356),
                        new Skladnik(Produkt.PROSZEK_DO_PIECZENIA, 140),
                        new Skladnik(Produkt.CUKIER, 192),
                        new Skladnik(Produkt.MARGARYNA, 367),
                        new Skladnik(Produkt.MARGARYNA, 134),
                        new Skladnik(Produkt.MARGARYNA, 253),
                        new Skladnik(Produkt.MLEKO, 236),
                        new Skladnik(Produkt.WODA, 287)
                );
                Przepis przepis_243ec520961646cc9d2f0e668a6d70e9 = () -> przepis_243ec520961646cc9d2f0e668a6d70e9_skladniki;

                Set<Skladnik> przeliczony_przepis_243ec520961646cc9d2f0e668a6d70e9 = kuchnia.przeliczPrzepis(przepis_243ec520961646cc9d2f0e668a6d70e9);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_243ec520961646cc9d2f0e668a6d70e9);
                Przepis nowyPrzepis_przepis_243ec520961646cc9d2f0e668a6d70e9 = () -> przeliczony_przepis_243ec520961646cc9d2f0e668a6d70e9;
                if (przeliczony_przepis_243ec520961646cc9d2f0e668a6d70e9.isEmpty()) {
                    if (kuchnia.wykonaj(przepis_243ec520961646cc9d2f0e668a6d70e9)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_243ec520961646cc9d2f0e668a6d70e9)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }




                kuchnia.dodajDoSpizarni(Produkt.CUKIER_WANILIOWY, 95);
                kuchnia.dodajDoSpizarni(Produkt.MAKA_ZIEMNIACZANA, 155);
                kuchnia.dodajDoSpizarni(Produkt.TWAROG, 170);
                kuchnia.dodajDoSpizarni(Produkt.RODZYNKI, 146);
                kuchnia.dodajDoSpizarni(Produkt.MARGARYNA, 173);

                Set<Skladnik> przepis_7b82170f114d46dc849c4faa09ef019e_skladniki = Set.of(
                        new Skladnik(Produkt.MLEKO, 13),
                        new Skladnik(Produkt.MLEKO, 317),
                        new Skladnik(Produkt.MARGARYNA, 218),
                        new Skladnik(Produkt.MAKA_ZIEMNIACZANA, 322),
                        new Skladnik(Produkt.TWAROG, 105),
                        new Skladnik(Produkt.MLEKO, 388),
                        new Skladnik(Produkt.OLEJ_ROSLINNY, 301),
                        new Skladnik(Produkt.DROZDZE, 257)
                );
                Przepis przepis_7b82170f114d46dc849c4faa09ef019e = () -> przepis_7b82170f114d46dc849c4faa09ef019e_skladniki;

                Set<Skladnik> przeliczony_przepis_7b82170f114d46dc849c4faa09ef019e = kuchnia.przeliczPrzepis(przepis_7b82170f114d46dc849c4faa09ef019e);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_7b82170f114d46dc849c4faa09ef019e);
                Przepis nowyPrzepis_przepis_7b82170f114d46dc849c4faa09ef019e = () -> przeliczony_przepis_7b82170f114d46dc849c4faa09ef019e;
                if (przeliczony_przepis_7b82170f114d46dc849c4faa09ef019e.isEmpty()) {
                    if (kuchnia.wykonaj(przepis_7b82170f114d46dc849c4faa09ef019e)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_7b82170f114d46dc849c4faa09ef019e)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }




                kuchnia.dodajDoSpizarni(Produkt.CUKIER_WANILIOWY, 142);
                kuchnia.dodajDoSpizarni(Produkt.MAKA_ZIEMNIACZANA, 157);
                kuchnia.dodajDoSpizarni(Produkt.TWAROG, 93);
                kuchnia.dodajDoSpizarni(Produkt.RODZYNKI, 152);
                kuchnia.dodajDoSpizarni(Produkt.MARGARYNA, 104);

                Set<Skladnik> przepis_8ebb2ec934294605b40697ff314074f6_skladniki = Set.of(
                        new Skladnik(Produkt.RODZYNKI, 28),
                        new Skladnik(Produkt.MLEKO, 365),
                        new Skladnik(Produkt.MLEKO, 97),
                        new Skladnik(Produkt.DROZDZE, 98),
                        new Skladnik(Produkt.MASLO, 131),
                        new Skladnik(Produkt.OLEJ_ROSLINNY, 126),
                        new Skladnik(Produkt.PROSZEK_DO_PIECZENIA, 182),
                        new Skladnik(Produkt.PROSZEK_DO_PIECZENIA, 394)
                );
                Przepis przepis_8ebb2ec934294605b40697ff314074f6 = () -> przepis_8ebb2ec934294605b40697ff314074f6_skladniki;

                Set<Skladnik> przeliczony_przepis_8ebb2ec934294605b40697ff314074f6 = kuchnia.przeliczPrzepis(przepis_8ebb2ec934294605b40697ff314074f6);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_8ebb2ec934294605b40697ff314074f6);
                Przepis nowyPrzepis_przepis_8ebb2ec934294605b40697ff314074f6 = () -> przeliczony_przepis_8ebb2ec934294605b40697ff314074f6;
                if (przeliczony_przepis_8ebb2ec934294605b40697ff314074f6.isEmpty()) {
                    if (kuchnia.wykonaj(przepis_8ebb2ec934294605b40697ff314074f6)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_8ebb2ec934294605b40697ff314074f6)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }




                kuchnia.dodajDoSpizarni(Produkt.CUKIER, 93);
                kuchnia.dodajDoSpizarni(Produkt.MAKA_PSZENNA, 166);
                kuchnia.dodajDoSpizarni(Produkt.MAKA_RAZOWA, 45);
                kuchnia.dodajDoSpizarni(Produkt.MASLO, 171);

                Set<Skladnik> przepis_207347a66381409396749d4e5ef0f2c4_skladniki = Set.of(
                        new Skladnik(Produkt.CUKIER_WANILIOWY, 294),
                        new Skladnik(Produkt.MLEKO, 252),
                        new Skladnik(Produkt.RODZYNKI, 288),
                        new Skladnik(Produkt.MAKA_ZIEMNIACZANA, 304),
                        new Skladnik(Produkt.OLEJ_ROSLINNY, 119),
                        new Skladnik(Produkt.MASLO, 9),
                        new Skladnik(Produkt.MAKA_PSZENNA, 237)
                );
                Przepis przepis_207347a66381409396749d4e5ef0f2c4 = () -> przepis_207347a66381409396749d4e5ef0f2c4_skladniki;

                Set<Skladnik> przeliczony_przepis_207347a66381409396749d4e5ef0f2c4 = kuchnia.przeliczPrzepis(przepis_207347a66381409396749d4e5ef0f2c4);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_207347a66381409396749d4e5ef0f2c4);
                Przepis nowyPrzepis_przepis_207347a66381409396749d4e5ef0f2c4 = () -> przeliczony_przepis_207347a66381409396749d4e5ef0f2c4;
                if (przeliczony_przepis_207347a66381409396749d4e5ef0f2c4.isEmpty()) {
                    if (kuchnia.wykonaj(przepis_207347a66381409396749d4e5ef0f2c4)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_207347a66381409396749d4e5ef0f2c4)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }




                kuchnia.dodajDoSpizarni(Produkt.CUKIER, 190);
                kuchnia.dodajDoSpizarni(Produkt.MAKA_PSZENNA, 142);
                kuchnia.dodajDoSpizarni(Produkt.MAKA_RAZOWA, 6);


                Set<Skladnik> przepis_31bcedfb5ed2444fb1f999e6b9a52c3c_skladniki = Set.of(
                        new Skladnik(Produkt.MAKA_ZIEMNIACZANA, 43),
                        new Skladnik(Produkt.MAKA_ZIEMNIACZANA, 235),
                        new Skladnik(Produkt.MASLO, 89),
                        new Skladnik(Produkt.DROZDZE, 19),
                        new Skladnik(Produkt.MASLO, 4),
                        new Skladnik(Produkt.MAKA_RAZOWA, 390)
                );
                Przepis przepis_31bcedfb5ed2444fb1f999e6b9a52c3c = () -> przepis_31bcedfb5ed2444fb1f999e6b9a52c3c_skladniki;

                Set<Skladnik> przeliczony_przepis_31bcedfb5ed2444fb1f999e6b9a52c3c = kuchnia.przeliczPrzepis(przepis_31bcedfb5ed2444fb1f999e6b9a52c3c);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_31bcedfb5ed2444fb1f999e6b9a52c3c);
                Przepis nowyPrzepis_przepis_31bcedfb5ed2444fb1f999e6b9a52c3c = () -> przeliczony_przepis_31bcedfb5ed2444fb1f999e6b9a52c3c;
                if (przeliczony_przepis_31bcedfb5ed2444fb1f999e6b9a52c3c.isEmpty()) {
                    if (kuchnia.wykonaj(przepis_31bcedfb5ed2444fb1f999e6b9a52c3c)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_31bcedfb5ed2444fb1f999e6b9a52c3c)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }



                kuchnia.dodajDoSpizarni(Produkt.CUKIER_WANILIOWY, 35);
                kuchnia.dodajDoSpizarni(Produkt.MAKA_ZIEMNIACZANA, 167);
                kuchnia.dodajDoSpizarni(Produkt.TWAROG, 33);
                kuchnia.dodajDoSpizarni(Produkt.RODZYNKI, 74);
                kuchnia.dodajDoSpizarni(Produkt.MARGARYNA, 95);

                Set<Skladnik> przepis_16606cb47419471e9d3c361678bcc540_skladniki = Set.of(
                        new Skladnik(Produkt.OLEJ_ROSLINNY, 4),
                        new Skladnik(Produkt.PROSZEK_DO_PIECZENIA, 45),
                        new Skladnik(Produkt.DROZDZE, 130),
                        new Skladnik(Produkt.SOL, 303),
                        new Skladnik(Produkt.TWAROG, 110),
                        new Skladnik(Produkt.MASLO, 203),
                        new Skladnik(Produkt.MASLO, 282),
                        new Skladnik(Produkt.CUKIER, 291),
                        new Skladnik(Produkt.MAKA_PSZENNA, 307),
                        new Skladnik(Produkt.TWAROG, 287)
                );
                Przepis przepis_16606cb47419471e9d3c361678bcc540 = () -> przepis_16606cb47419471e9d3c361678bcc540_skladniki;

                Set<Skladnik> przeliczony_przepis_16606cb47419471e9d3c361678bcc540 = kuchnia.przeliczPrzepis(przepis_16606cb47419471e9d3c361678bcc540);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_16606cb47419471e9d3c361678bcc540);
                Przepis nowyPrzepis_przepis_16606cb47419471e9d3c361678bcc540 = () -> przeliczony_przepis_16606cb47419471e9d3c361678bcc540;
                if (przeliczony_przepis_16606cb47419471e9d3c361678bcc540.isEmpty()) {
                    if (kuchnia.wykonaj(przepis_16606cb47419471e9d3c361678bcc540)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_16606cb47419471e9d3c361678bcc540)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }




                kuchnia.dodajDoSpizarni(Produkt.CUKIER, 51);
                kuchnia.dodajDoSpizarni(Produkt.MAKA_PSZENNA, 119);
                kuchnia.dodajDoSpizarni(Produkt.MAKA_RAZOWA, 96);
                kuchnia.dodajDoSpizarni(Produkt.MASLO, 106);
                kuchnia.dodajDoSpizarni(Produkt.WODA, 143);
                kuchnia.dodajDoSpizarni(Produkt.SOL, 67);


                Set<Skladnik> przepis_8302d9b87cd0406398dec0543c8bd92f_skladniki = Set.of(
                        new Skladnik(Produkt.OLEJ_ROSLINNY, 210),
                        new Skladnik(Produkt.OLEJ_ROSLINNY, 54),
                        new Skladnik(Produkt.CUKIER_WANILIOWY, 105),
                        new Skladnik(Produkt.MLEKO, 50),
                        new Skladnik(Produkt.DROZDZE, 291),
                        new Skladnik(Produkt.WODA, 147),
                        new Skladnik(Produkt.CUKIER_PUDER, 357),
                        new Skladnik(Produkt.SOL, 46),
                        new Skladnik(Produkt.MLEKO, 221)
                );
                Przepis przepis_8302d9b87cd0406398dec0543c8bd92f = () -> przepis_8302d9b87cd0406398dec0543c8bd92f_skladniki;

                Set<Skladnik> przeliczony_przepis_8302d9b87cd0406398dec0543c8bd92f = kuchnia.przeliczPrzepis(przepis_8302d9b87cd0406398dec0543c8bd92f);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_8302d9b87cd0406398dec0543c8bd92f);
                Przepis nowyPrzepis_przepis_8302d9b87cd0406398dec0543c8bd92f = () -> przeliczony_przepis_8302d9b87cd0406398dec0543c8bd92f;
                if (przeliczony_przepis_8302d9b87cd0406398dec0543c8bd92f.isEmpty()) {
                    if (kuchnia.wykonaj(przepis_8302d9b87cd0406398dec0543c8bd92f)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_8302d9b87cd0406398dec0543c8bd92f)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }




                kuchnia.dodajDoSpizarni(Produkt.CUKIER, 84);
                kuchnia.dodajDoSpizarni(Produkt.MAKA_PSZENNA, 127);
                kuchnia.dodajDoSpizarni(Produkt.MAKA_RAZOWA, 7);
                kuchnia.dodajDoSpizarni(Produkt.MASLO, 34);
                kuchnia.dodajDoSpizarni(Produkt.WODA, 143);
                kuchnia.dodajDoSpizarni(Produkt.SOL, 154);


                Set<Skladnik> przepis_80d265f147f04ccdb6b35329cc6b9409_skladniki = Set.of(
                        new Skladnik(Produkt.DROZDZE, 179),
                        new Skladnik(Produkt.WODA, 310),
                        new Skladnik(Produkt.CUKIER_PUDER, 46),
                        new Skladnik(Produkt.WODA, 247),
                        new Skladnik(Produkt.MARGARYNA, 380),
                        new Skladnik(Produkt.MAKA_RAZOWA, 169),
                        new Skladnik(Produkt.MAKA_ZIEMNIACZANA, 77)
                );
                Przepis przepis_80d265f147f04ccdb6b35329cc6b9409 = () -> przepis_80d265f147f04ccdb6b35329cc6b9409_skladniki;

                Set<Skladnik> przeliczony_przepis_80d265f147f04ccdb6b35329cc6b9409 = kuchnia.przeliczPrzepis(przepis_80d265f147f04ccdb6b35329cc6b9409);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_80d265f147f04ccdb6b35329cc6b9409);
                Przepis nowyPrzepis_przepis_80d265f147f04ccdb6b35329cc6b9409 = () -> przeliczony_przepis_80d265f147f04ccdb6b35329cc6b9409;
                if (przeliczony_przepis_80d265f147f04ccdb6b35329cc6b9409.isEmpty()) {
                    if (kuchnia.wykonaj(przepis_80d265f147f04ccdb6b35329cc6b9409)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_80d265f147f04ccdb6b35329cc6b9409)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }




                kuchnia.dodajDoSpizarni(Produkt.CUKIER, 64);
                kuchnia.dodajDoSpizarni(Produkt.MAKA_PSZENNA, 172);
                kuchnia.dodajDoSpizarni(Produkt.MAKA_RAZOWA, 179);
                kuchnia.dodajDoSpizarni(Produkt.MASLO, 87);


                Set<Skladnik> przepis_31bfd9af05c74342ba209cd78cbdca4e_skladniki = Set.of(
                        new Skladnik(Produkt.PROSZEK_DO_PIECZENIA, 274),
                        new Skladnik(Produkt.CUKIER_WANILIOWY, 381),
                        new Skladnik(Produkt.CUKIER_PUDER, 324),
                        new Skladnik(Produkt.CUKIER, 379),
                        new Skladnik(Produkt.MASLO, 139),
                        new Skladnik(Produkt.MARGARYNA, 225),
                        new Skladnik(Produkt.CUKIER_WANILIOWY, 301),
                        new Skladnik(Produkt.MAKA_RAZOWA, 198),
                        new Skladnik(Produkt.PROSZEK_DO_PIECZENIA, 366),
                        new Skladnik(Produkt.CUKIER_PUDER, 38)
                );
                Przepis przepis_31bfd9af05c74342ba209cd78cbdca4e = () -> przepis_31bfd9af05c74342ba209cd78cbdca4e_skladniki;

                Set<Skladnik> przeliczony_przepis_31bfd9af05c74342ba209cd78cbdca4e = kuchnia.przeliczPrzepis(przepis_31bfd9af05c74342ba209cd78cbdca4e);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_31bfd9af05c74342ba209cd78cbdca4e);
                Przepis nowyPrzepis_przepis_31bfd9af05c74342ba209cd78cbdca4e = () -> przeliczony_przepis_31bfd9af05c74342ba209cd78cbdca4e;
                if (przeliczony_przepis_31bfd9af05c74342ba209cd78cbdca4e.isEmpty()) {
                    if (kuchnia.wykonaj(przepis_31bfd9af05c74342ba209cd78cbdca4e)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_31bfd9af05c74342ba209cd78cbdca4e)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }




                kuchnia.dodajDoSpizarni(Produkt.CUKIER, 175);
                kuchnia.dodajDoSpizarni(Produkt.MAKA_PSZENNA, 7);
                kuchnia.dodajDoSpizarni(Produkt.MAKA_RAZOWA, 76);
                kuchnia.dodajDoSpizarni(Produkt.MASLO, 152);


                Set<Skladnik> przepis_207a2027c369491795d30c68f1794908_skladniki = Set.of(
                        new Skladnik(Produkt.MAKA_PSZENNA, 166),
                        new Skladnik(Produkt.RODZYNKI, 64),
                        new Skladnik(Produkt.SOL, 291),
                        new Skladnik(Produkt.CUKIER_WANILIOWY, 203),
                        new Skladnik(Produkt.MARGARYNA, 129),
                        new Skladnik(Produkt.SOL, 361),
                        new Skladnik(Produkt.MLEKO, 20),
                        new Skladnik(Produkt.MAKA_PSZENNA, 393),
                        new Skladnik(Produkt.MASLO, 43)
                );
                Przepis przepis_207a2027c369491795d30c68f1794908 = () -> przepis_207a2027c369491795d30c68f1794908_skladniki;

                Set<Skladnik> przeliczony_przepis_207a2027c369491795d30c68f1794908 = kuchnia.przeliczPrzepis(przepis_207a2027c369491795d30c68f1794908);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_207a2027c369491795d30c68f1794908);
                Przepis nowyPrzepis_przepis_207a2027c369491795d30c68f1794908 = () -> przeliczony_przepis_207a2027c369491795d30c68f1794908;
                if (przeliczony_przepis_207a2027c369491795d30c68f1794908.isEmpty()) {
                    if (kuchnia.wykonaj(przepis_207a2027c369491795d30c68f1794908)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_207a2027c369491795d30c68f1794908)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }




                kuchnia.dodajDoSpizarni(Produkt.CUKIER, 187);
                kuchnia.dodajDoSpizarni(Produkt.MAKA_PSZENNA, 75);
                kuchnia.dodajDoSpizarni(Produkt.MAKA_RAZOWA, 63);
                kuchnia.dodajDoSpizarni(Produkt.MASLO, 73);

                kuchnia.dodajDoSpizarni(Produkt.MARGARYNA, 112);

                Set<Skladnik> przepis_231434a3725249d1bbda44e89b4bfa82_skladniki = Set.of(
                        new Skladnik(Produkt.CUKIER_PUDER, 353),
                        new Skladnik(Produkt.TWAROG, 152),
                        new Skladnik(Produkt.RODZYNKI, 327),
                        new Skladnik(Produkt.OLEJ_ROSLINNY, 322),
                        new Skladnik(Produkt.OLEJ_ROSLINNY, 179),
                        new Skladnik(Produkt.DROZDZE, 389),
                        new Skladnik(Produkt.MAKA_ZIEMNIACZANA, 284)
                );
                Przepis przepis_231434a3725249d1bbda44e89b4bfa82 = () -> przepis_231434a3725249d1bbda44e89b4bfa82_skladniki;

                Set<Skladnik> przeliczony_przepis_231434a3725249d1bbda44e89b4bfa82 = kuchnia.przeliczPrzepis(przepis_231434a3725249d1bbda44e89b4bfa82);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_231434a3725249d1bbda44e89b4bfa82);
                Przepis nowyPrzepis_przepis_231434a3725249d1bbda44e89b4bfa82 = () -> przeliczony_przepis_231434a3725249d1bbda44e89b4bfa82;
                if (przeliczony_przepis_231434a3725249d1bbda44e89b4bfa82.isEmpty()) {
                    if (kuchnia.wykonaj(przepis_231434a3725249d1bbda44e89b4bfa82)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_231434a3725249d1bbda44e89b4bfa82)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }




                kuchnia.dodajDoSpizarni(Produkt.CUKIER_PUDER, 199);
                kuchnia.dodajDoSpizarni(Produkt.MLEKO, 130);
                kuchnia.dodajDoSpizarni(Produkt.OLEJ_ROSLINNY, 107);
                kuchnia.dodajDoSpizarni(Produkt.CUKIER_WANILIOWY, 191);
                kuchnia.dodajDoSpizarni(Produkt.MAKA_ZIEMNIACZANA, 114);
                kuchnia.dodajDoSpizarni(Produkt.TWAROG, 156);
                kuchnia.dodajDoSpizarni(Produkt.RODZYNKI, 191);
                kuchnia.dodajDoSpizarni(Produkt.MARGARYNA, 134);

                Set<Skladnik> przepis_f05bb807f18c4029b8d9044116350298_skladniki = Set.of(
                        new Skladnik(Produkt.MAKA_RAZOWA, 333),
                        new Skladnik(Produkt.CUKIER_WANILIOWY, 83),
                        new Skladnik(Produkt.DROZDZE, 385),
                        new Skladnik(Produkt.RODZYNKI, 266),
                        new Skladnik(Produkt.PROSZEK_DO_PIECZENIA, 143),
                        new Skladnik(Produkt.DROZDZE, 78)
                );
                Przepis przepis_f05bb807f18c4029b8d9044116350298 = () -> przepis_f05bb807f18c4029b8d9044116350298_skladniki;

                Set<Skladnik> przeliczony_przepis_f05bb807f18c4029b8d9044116350298 = kuchnia.przeliczPrzepis(przepis_f05bb807f18c4029b8d9044116350298);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_f05bb807f18c4029b8d9044116350298);
                Przepis nowyPrzepis_przepis_f05bb807f18c4029b8d9044116350298 = () -> przeliczony_przepis_f05bb807f18c4029b8d9044116350298;
                if (przeliczony_przepis_f05bb807f18c4029b8d9044116350298.isEmpty()) {
                    if (kuchnia.wykonaj(przepis_f05bb807f18c4029b8d9044116350298)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_f05bb807f18c4029b8d9044116350298)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }




                Set<Skladnik> przepis_8e291300cb76431ea5cc96b9fecf3ea5_skladniki = Set.of(
                        new Skladnik(Produkt.CUKIER, 267),
                        new Skladnik(Produkt.CUKIER_PUDER, 310),
                        new Skladnik(Produkt.MAKA_PSZENNA, 44),
                        new Skladnik(Produkt.DROZDZE, 195),
                        new Skladnik(Produkt.PROSZEK_DO_PIECZENIA, 20),
                        new Skladnik(Produkt.MARGARYNA, 147)
                );
                Przepis przepis_8e291300cb76431ea5cc96b9fecf3ea5 = () -> przepis_8e291300cb76431ea5cc96b9fecf3ea5_skladniki;

                Set<Skladnik> przeliczony_przepis_8e291300cb76431ea5cc96b9fecf3ea5 = kuchnia.przeliczPrzepis(przepis_8e291300cb76431ea5cc96b9fecf3ea5);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_8e291300cb76431ea5cc96b9fecf3ea5);
                Przepis nowyPrzepis_przepis_8e291300cb76431ea5cc96b9fecf3ea5 = () -> przeliczony_przepis_8e291300cb76431ea5cc96b9fecf3ea5;
                if (przeliczony_przepis_8e291300cb76431ea5cc96b9fecf3ea5.isEmpty()) {
                    if (kuchnia.wykonaj(przepis_8e291300cb76431ea5cc96b9fecf3ea5)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_8e291300cb76431ea5cc96b9fecf3ea5)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }






                Set<Skladnik> przepis_8095f3d9409e4fd1b4551dede4bac24b_skladniki = Set.of(
                        new Skladnik(Produkt.DROZDZE, 373),
                        new Skladnik(Produkt.RODZYNKI, 348),
                        new Skladnik(Produkt.SOL, 122),
                        new Skladnik(Produkt.TWAROG, 274),
                        new Skladnik(Produkt.PROSZEK_DO_PIECZENIA, 313),
                        new Skladnik(Produkt.MAKA_ZIEMNIACZANA, 244),
                        new Skladnik(Produkt.RODZYNKI, 363),
                        new Skladnik(Produkt.DROZDZE, 132)
                );
                Przepis przepis_8095f3d9409e4fd1b4551dede4bac24b = () -> przepis_8095f3d9409e4fd1b4551dede4bac24b_skladniki;

                Set<Skladnik> przeliczony_przepis_8095f3d9409e4fd1b4551dede4bac24b = kuchnia.przeliczPrzepis(przepis_8095f3d9409e4fd1b4551dede4bac24b);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_8095f3d9409e4fd1b4551dede4bac24b);
                Przepis nowyPrzepis_przepis_8095f3d9409e4fd1b4551dede4bac24b = () -> przeliczony_przepis_8095f3d9409e4fd1b4551dede4bac24b;
                if (przeliczony_przepis_8095f3d9409e4fd1b4551dede4bac24b.isEmpty()) {
                    if (kuchnia.wykonaj(przepis_8095f3d9409e4fd1b4551dede4bac24b)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_8095f3d9409e4fd1b4551dede4bac24b)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }




                kuchnia.dodajDoSpizarni(Produkt.CUKIER, 52);
                kuchnia.dodajDoSpizarni(Produkt.MAKA_PSZENNA, 34);
                kuchnia.dodajDoSpizarni(Produkt.MAKA_RAZOWA, 160);


                Set<Skladnik> przepis_be37504382b845ceb5bb4f7d3bfd29d6_skladniki = Set.of(
                        new Skladnik(Produkt.CUKIER_PUDER, 116),
                        new Skladnik(Produkt.DROZDZE, 327),
                        new Skladnik(Produkt.OLEJ_ROSLINNY, 369),
                        new Skladnik(Produkt.WODA, 74),
                        new Skladnik(Produkt.MAKA_ZIEMNIACZANA, 395),
                        new Skladnik(Produkt.WODA, 271),
                        new Skladnik(Produkt.MAKA_PSZENNA, 391),
                        new Skladnik(Produkt.RODZYNKI, 334),
                        new Skladnik(Produkt.DROZDZE, 110)
                );
                Przepis przepis_be37504382b845ceb5bb4f7d3bfd29d6 = () -> przepis_be37504382b845ceb5bb4f7d3bfd29d6_skladniki;

                Set<Skladnik> przeliczony_przepis_be37504382b845ceb5bb4f7d3bfd29d6 = kuchnia.przeliczPrzepis(przepis_be37504382b845ceb5bb4f7d3bfd29d6);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_be37504382b845ceb5bb4f7d3bfd29d6);
                Przepis nowyPrzepis_przepis_be37504382b845ceb5bb4f7d3bfd29d6 = () -> przeliczony_przepis_be37504382b845ceb5bb4f7d3bfd29d6;
                if (przeliczony_przepis_be37504382b845ceb5bb4f7d3bfd29d6.isEmpty()) {
                    if (kuchnia.wykonaj(przepis_be37504382b845ceb5bb4f7d3bfd29d6)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_be37504382b845ceb5bb4f7d3bfd29d6)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }



                kuchnia.dodajDoSpizarni(Produkt.MARGARYNA, 160);

                Set<Skladnik> przepis_7d318218f1c142d4a6e56a54b0744f57_skladniki = Set.of(
                        new Skladnik(Produkt.CUKIER_PUDER, 106),
                        new Skladnik(Produkt.CUKIER_WANILIOWY, 169),
                        new Skladnik(Produkt.CUKIER, 78),
                        new Skladnik(Produkt.PROSZEK_DO_PIECZENIA, 73),
                        new Skladnik(Produkt.DROZDZE, 127),
                        new Skladnik(Produkt.MAKA_PSZENNA, 128),
                        new Skladnik(Produkt.RODZYNKI, 281)
                );
                Przepis przepis_7d318218f1c142d4a6e56a54b0744f57 = () -> przepis_7d318218f1c142d4a6e56a54b0744f57_skladniki;

                Set<Skladnik> przeliczony_przepis_7d318218f1c142d4a6e56a54b0744f57 = kuchnia.przeliczPrzepis(przepis_7d318218f1c142d4a6e56a54b0744f57);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_7d318218f1c142d4a6e56a54b0744f57);
                Przepis nowyPrzepis_przepis_7d318218f1c142d4a6e56a54b0744f57 = () -> przeliczony_przepis_7d318218f1c142d4a6e56a54b0744f57;
                if (przeliczony_przepis_7d318218f1c142d4a6e56a54b0744f57.isEmpty()) {
                    if (kuchnia.wykonaj(przepis_7d318218f1c142d4a6e56a54b0744f57)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_7d318218f1c142d4a6e56a54b0744f57)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }




                kuchnia.dodajDoSpizarni(Produkt.CUKIER, 40);
                kuchnia.dodajDoSpizarni(Produkt.MAKA_PSZENNA, 153);
                kuchnia.dodajDoSpizarni(Produkt.MAKA_RAZOWA, 102);
                kuchnia.dodajDoSpizarni(Produkt.MASLO, 56);
                kuchnia.dodajDoSpizarni(Produkt.WODA, 193);
                kuchnia.dodajDoSpizarni(Produkt.SOL, 133);
                kuchnia.dodajDoSpizarni(Produkt.DROZDZE, 44);
                kuchnia.dodajDoSpizarni(Produkt.PROSZEK_DO_PIECZENIA, 32);
                kuchnia.dodajDoSpizarni(Produkt.CUKIER_PUDER, 52);
                kuchnia.dodajDoSpizarni(Produkt.MLEKO, 82);
                kuchnia.dodajDoSpizarni(Produkt.OLEJ_ROSLINNY, 137);
                kuchnia.dodajDoSpizarni(Produkt.CUKIER_WANILIOWY, 167);
                kuchnia.dodajDoSpizarni(Produkt.MAKA_ZIEMNIACZANA, 153);
                kuchnia.dodajDoSpizarni(Produkt.TWAROG, 5);
                kuchnia.dodajDoSpizarni(Produkt.RODZYNKI, 2);
                kuchnia.dodajDoSpizarni(Produkt.MARGARYNA, 82);

                Set<Skladnik> przepis_28db34d59a8c43ab96f886b95d4b2096_skladniki = Set.of(
                        new Skladnik(Produkt.MARGARYNA, 121),
                        new Skladnik(Produkt.PROSZEK_DO_PIECZENIA, 347),
                        new Skladnik(Produkt.MAKA_RAZOWA, 137),
                        new Skladnik(Produkt.MAKA_RAZOWA, 65),
                        new Skladnik(Produkt.WODA, 119),
                        new Skladnik(Produkt.WODA, 220),
                        new Skladnik(Produkt.RODZYNKI, 50),
                        new Skladnik(Produkt.MLEKO, 256)
                );
                Przepis przepis_28db34d59a8c43ab96f886b95d4b2096 = () -> przepis_28db34d59a8c43ab96f886b95d4b2096_skladniki;

                Set<Skladnik> przeliczony_przepis_28db34d59a8c43ab96f886b95d4b2096 = kuchnia.przeliczPrzepis(przepis_28db34d59a8c43ab96f886b95d4b2096);
                System.out.println("Optymalny prz3epis: " + przeliczony_przepis_28db34d59a8c43ab96f886b95d4b2096);
                Przepis nowyPrzepis_przepis_28db34d59a8c43ab96f886b95d4b2096 = () -> przeliczony_przepis_28db34d59a8c43ab96f886b95d4b2096;
                if (przeliczony_przepis_28db34d59a8c43ab96f886b95d4b2096.isEmpty()) {
                    if (kuchnia.wykonaj(przepis_28db34d59a8c43ab96f886b95d4b2096)) {
                        System.out.println("Potrawa została przygotowana z oryginalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                } else {
                    if (kuchnia.wykonaj(nowyPrzepis_przepis_28db34d59a8c43ab96f886b95d4b2096)) {
                        System.out.println("Potrawa została przygotowana z optymalnego przepisu!");
                    } else {
                        System.out.println("Nie można przygotować potrawy.");
                    }
                }




            }



        }


}
