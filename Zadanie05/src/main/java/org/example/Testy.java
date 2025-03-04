package org.example;

import java.util.*;
import java.util.stream.Collectors;

public class Testy {

    private static Przepis MapToPrzepis(Map<Produkt, Integer> skladniki) {
        return new Przepis() {
            @Override
            public Set<Skladnik> sklad() {
                return skladniki.entrySet().stream()
                        .map(entry -> new Skladnik(entry.getKey(), entry.getValue()))
                        .collect(Collectors.toSet());
            }
        };
    }


    private static Przepis SetToPrzepis(Set<Skladnik> skladniki) {
        return new Przepis() {
            @Override
            public Set<Skladnik> sklad() {
                return skladniki;
            }
        };
    }


// === TESTY ===========================================================================================================

    // - - - -

    private static int testZTresciZadania() {
        System.out.println("---Test-z-tresci-zadania-----------------");
        String nazwaTestu = "TestZTresciZadania";

        boolean passed = true;

        EKuchnia kuchnia = new EKuchnia();

        // DODAWANIE PRODUKTOW DO SPIZARNI
        kuchnia.dodajDoSpizarni(Produkt.CUKIER, 600);
        kuchnia.dodajDoSpizarni(Produkt.MAKA_PSZENNA, 1500);
        kuchnia.dodajDoSpizarni(Produkt.SOL, 100);
        kuchnia.dodajDoSpizarni(Produkt.DROZDZE, 50);

        // SPRAWDZENIE STANU SPIZARNI
        Map<Produkt, Integer> stanSpizarni = kuchnia.stanSpizarni();
        System.out.println("Stan spizarni: " + stanSpizarni);
        if (stanSpizarni.size() != 4) {
            passed = false;
            System.out.println("-- " + nazwaTestu + " failed on: STAN SPIZARNI 1");
        }
        if (stanSpizarni.getOrDefault(Produkt.CUKIER, 0) != 600 ||
                stanSpizarni.getOrDefault(Produkt.MAKA_PSZENNA, 0) != 1500 ||
                stanSpizarni.getOrDefault(Produkt.SOL, 0) != 100 ||
                stanSpizarni.getOrDefault(Produkt.DROZDZE, 0) != 50) {
            passed = false;
            System.out.println("-- " + nazwaTestu + " failed on: STAN SPIZARNI 2");
        }

        // SPRAWDZENIE PRZELICZANIA PRZEPISU
        Przepis przepis = MapToPrzepis(Map.of(
                Produkt.CUKIER, 10,
                Produkt.MAKA_PSZENNA, 500,
                Produkt.SOL, 15,
                Produkt.DROZDZE, 25
        ));
        Set<Skladnik> przeliczonyPrzepis = kuchnia.przeliczPrzepis(przepis);
        System.out.println("Przeliczony przepis: " + przeliczonyPrzepis);
        if (przeliczonyPrzepis.size() != 4) {
            passed = false;
            System.out.println("-- " + nazwaTestu + " failed on: PRZELICZANIE PRZEPISU 1");
        }
        for (Skladnik s : przeliczonyPrzepis) {
            if ((s.produkt() == Produkt.CUKIER && s.gramow() != 20) ||
                    (s.produkt() == Produkt.MAKA_PSZENNA && s.gramow() != 1000) ||
                    (s.produkt() == Produkt.SOL && s.gramow() != 30) ||
                    (s.produkt() == Produkt.DROZDZE && s.gramow() != 50)) {
                passed = false;
                System.out.println("-- " + nazwaTestu + " failed on: PRZELICZANIE PRZEPISU 2");
                break;
            }
        }

        // SPRAWDZANIE WYKONYWANIA
        if ( ! kuchnia.wykonaj(SetToPrzepis(przeliczonyPrzepis))) {
            passed = false;
            System.out.println("-- " + nazwaTestu + " failed on: WYKONYWANIE 1");
        }
        stanSpizarni = kuchnia.stanSpizarni();
        if (stanSpizarni.size() != 3) {
            passed = false;
            System.out.println("-- " + nazwaTestu + " failed on: WYKONYWANIE 2");
        }
        if (stanSpizarni.getOrDefault(Produkt.CUKIER, 0) != 580 ||
                stanSpizarni.getOrDefault(Produkt.MAKA_PSZENNA, 0) != 500 ||
                stanSpizarni.getOrDefault(Produkt.SOL, 0) != 70) {
            passed = false;
            System.out.println("-- " + nazwaTestu + " failed on: WYKONYWANIE 3");
        }
        // drugie wykonanie juz nie moze sie udac
        if (kuchnia.wykonaj(przepis)) {
            passed = false;
            System.out.println("-- " + nazwaTestu + " failed on: WYKONYWANIE 4");
        }
        stanSpizarni = kuchnia.stanSpizarni();
        if (stanSpizarni.size() != 3) {
            passed = false;
            System.out.println("-- " + nazwaTestu + " failed on: WYKONYWANIE 5");
        }
        if (stanSpizarni.getOrDefault(Produkt.CUKIER, 0) != 580 ||
                stanSpizarni.getOrDefault(Produkt.MAKA_PSZENNA, 0) != 500 ||
                stanSpizarni.getOrDefault(Produkt.SOL, 0) != 70) {
            passed = false;
            System.out.println("-- " + nazwaTestu + " failed on: WYKONYWANIE 6");
        }

        System.out.println("Po wykonaniu: " + kuchnia.stanSpizarni());

        if (passed) {
            System.out.println("--- ++ PASSED ++ ------------------------\n");
            return 1;
        } else {
            System.out.println("--- -- FAILED -- ------------------------\n");
            return 0;
        }
    }

    // - - - -

    private static int testDodawaniaDoSpizarni() {
        System.out.println("---Test-dodawania-do-spizarni------------");
        String nazwaTestu = "TestDodawaniaDoSpizarni";
        boolean passed = true;

        EKuchnia kuchnia = new EKuchnia();

        // DODAWANIE PRODUKTOW DO SPIZARNI
        kuchnia.dodajDoSpizarni(Produkt.CUKIER, 5);
        kuchnia.dodajDoSpizarni(Produkt.CUKIER, 3);
        kuchnia.dodajDoSpizarni(Produkt.MLEKO, 0);
        kuchnia.dodajDoSpizarni(Produkt.TWAROG, 123);
        kuchnia.dodajDoSpizarni(Produkt.TWAROG, 100);

        // SPRAWDZENIE STANU SPIZARNI
        Map<Produkt, Integer> stanSpizarni = kuchnia.stanSpizarni();
        System.out.println("Stan spizarni: " + stanSpizarni);
        if (stanSpizarni.size() != 2) { // mleko nie jest raportowane, bo jest go 0 (nie ma go xd)
            passed = false;
            System.out.println("-- " + nazwaTestu + " failed on: STAN SPIZARNI 1");
        }
        if (stanSpizarni.getOrDefault(Produkt.CUKIER, 0) != 8 ||
                stanSpizarni.getOrDefault(Produkt.TWAROG, 0) != 223) {
            passed = false;
            System.out.println("-- " + nazwaTestu + " failed on: STAN SPIZARNI 2");
        }

        if (passed) {
            System.out.println("--- ++ PASSED ++ ------------------------\n");
            return 1;
        } else {
            System.out.println("--- -- FAILED -- ------------------------\n");
            return 0;
        }
    }

    // - - - -

    private static int testPrzeliczania1() {
        System.out.println("---Test-przeliczania-1-------------------");
        String nazwaTestu = "TestPrzeliczania1";

        boolean passed = true;

        EKuchnia kuchnia = new EKuchnia();

        // DODAWANIE PRODUKTOW DO SPIZARNI
        kuchnia.dodajDoSpizarni(Produkt.CUKIER, 500);
        kuchnia.dodajDoSpizarni(Produkt.MAKA_PSZENNA, 500);
        kuchnia.dodajDoSpizarni(Produkt.SOL, 500);
        kuchnia.dodajDoSpizarni(Produkt.DROZDZE, 500);
        System.out.println("Stan spizarni: " + kuchnia.stanSpizarni());

        // SPRAWDZENIE PRZELICZANIA PRZEPISU
        Przepis przepis = MapToPrzepis(Map.of(
                Produkt.CUKIER, 10,
                Produkt.MAKA_PSZENNA, 10,
                Produkt.SOL, 10,
                Produkt.DROZDZE, 10
        ));
        Set<Skladnik> przeliczonyPrzepis = kuchnia.przeliczPrzepis(przepis);
        System.out.println("Przeliczony przepis: " + przeliczonyPrzepis);
        if (przeliczonyPrzepis.size() != 4) {
            passed = false;
            System.out.println("-- " + nazwaTestu + " failed on: PRZELICZANIE PRZEPISU 1");
        }
        for (Skladnik s : przeliczonyPrzepis) {
            if ((s.produkt() == Produkt.CUKIER && s.gramow() != 500) ||
                    (s.produkt() == Produkt.MAKA_PSZENNA && s.gramow() != 500) ||
                    (s.produkt() == Produkt.SOL && s.gramow() != 500) ||
                    (s.produkt() == Produkt.DROZDZE && s.gramow() != 500)) {
                passed = false;
                System.out.println("-- " + nazwaTestu + " failed on: PRZELICZANIE PRZEPISU 2");
                break;
            }
        }

        if (passed) {
            System.out.println("--- ++ PASSED ++ ------------------------\n");
            return 1;
        } else {
            System.out.println("--- -- FAILED -- ------------------------\n");
            return 0;
        }
    }

    // - - - -

    private static int testPrzeliczania2() {
        System.out.println("---Test-przeliczania-2-------------------");
        String nazwaTestu = "TestPrzeliczania2";

        boolean passed = true;

        EKuchnia kuchnia = new EKuchnia();

        // DODAWANIE PRODUKTOW DO SPIZARNI
        kuchnia.dodajDoSpizarni(Produkt.CUKIER, 500);
        kuchnia.dodajDoSpizarni(Produkt.MAKA_PSZENNA, 500);
        kuchnia.dodajDoSpizarni(Produkt.SOL, 500);
        kuchnia.dodajDoSpizarni(Produkt.DROZDZE, 500);
        System.out.println("Stan spizarni: " + kuchnia.stanSpizarni());

        // SPRAWDZENIE PRZELICZANIA PRZEPISU
        Przepis przepis = MapToPrzepis(Map.of(
                Produkt.CUKIER, 10,
                Produkt.MAKA_PSZENNA, 10,
                Produkt.SOL, 10,
                Produkt.DROZDZE, 10,
                Produkt.MARGARYNA, 10 // nie mamy margaryny w spizarni, wiec powinno dac pusta liste
        ));
        Set<Skladnik> przeliczonyPrzepis = kuchnia.przeliczPrzepis(przepis);
        System.out.println("Przeliczony przepis: " + przeliczonyPrzepis);
        if (!przeliczonyPrzepis.isEmpty()) {
            passed = false;
            System.out.println("-- " + nazwaTestu + " failed on: PRZELICZANIE PRZEPISU 1");
        }

        if (passed) {
            System.out.println("--- ++ PASSED ++ ------------------------\n");
            return 1;
        } else {
            System.out.println("--- -- FAILED -- ------------------------\n");
            return 0;
        }
    }

    // - - - -

    private static int testPrzeliczania3() {
        System.out.println("---Test-przeliczania-3-------------------");
        String nazwaTestu = "TestPrzeliczania3";

        boolean passed = true;

        EKuchnia kuchnia = new EKuchnia();

        // DODAWANIE PRODUKTOW DO SPIZARNI
        kuchnia.dodajDoSpizarni(Produkt.CUKIER, 100);
        kuchnia.dodajDoSpizarni(Produkt.MAKA_PSZENNA, 100);
        kuchnia.dodajDoSpizarni(Produkt.SOL, 100);
        kuchnia.dodajDoSpizarni(Produkt.DROZDZE, 100);
        System.out.println("Stan spizarni: " + kuchnia.stanSpizarni());

        // SPRAWDZENIE PRZELICZANIA PRZEPISU
        Przepis przepis = MapToPrzepis(Map.of(
                Produkt.CUKIER, 10,
                Produkt.MAKA_PSZENNA, 20,
                Produkt.SOL, 5,
                Produkt.DROZDZE, 20
        ));
        Set<Skladnik> przeliczonyPrzepis = kuchnia.przeliczPrzepis(przepis);
        System.out.println("Przeliczony przepis: " + przeliczonyPrzepis);
        if (przeliczonyPrzepis.size() != 4) {
            passed = false;
            System.out.println("-- " + nazwaTestu + " failed on: PRZELICZANIE PRZEPISU 1");
        }
        for (Skladnik s : przeliczonyPrzepis) {
            if ((s.produkt() == Produkt.CUKIER && s.gramow() != 50) ||
                    (s.produkt() == Produkt.MAKA_PSZENNA && s.gramow() != 100) ||
                    (s.produkt() == Produkt.SOL && s.gramow() != 25) ||
                    (s.produkt() == Produkt.DROZDZE && s.gramow() != 100)) {
                passed = false;
                System.out.println("-- " + nazwaTestu + " failed on: PRZELICZANIE PRZEPISU 2");
                break;
            }
        }

        if (passed) {
            System.out.println("--- ++ PASSED ++ ------------------------\n");
            return 1;
        } else {
            System.out.println("--- -- FAILED -- ------------------------\n");
            return 0;
        }
    }

    // - - - -

    private static int testPrzeliczania4() {
        System.out.println("---Test-przeliczania-4-------------------");
        String nazwaTestu = "TestPrzeliczania4";

        boolean passed = true;

        EKuchnia kuchnia = new EKuchnia();

        // DODAWANIE PRODUKTOW DO SPIZARNI
        kuchnia.dodajDoSpizarni(Produkt.CUKIER, 100);
        kuchnia.dodajDoSpizarni(Produkt.MAKA_PSZENNA, 100);
        kuchnia.dodajDoSpizarni(Produkt.SOL, 100);
        kuchnia.dodajDoSpizarni(Produkt.DROZDZE, 100);
        System.out.println("Stan spizarni: " + kuchnia.stanSpizarni());

        // SPRAWDZENIE PRZELICZANIA PRZEPISU
        Przepis przepis = MapToPrzepis(Map.of(
                Produkt.CUKIER, 200,
                Produkt.MAKA_PSZENNA, 100,
                Produkt.SOL, 50,
                Produkt.DROZDZE, 20
        ));
        Set<Skladnik> przeliczonyPrzepis = kuchnia.przeliczPrzepis(przepis);
        System.out.println("Przeliczony przepis: " + przeliczonyPrzepis);
        if (przeliczonyPrzepis.size() != 4) {
            passed = false;
            System.out.println("-- " + nazwaTestu + " failed on: PRZELICZANIE PRZEPISU 1");
        }
        for (Skladnik s : przeliczonyPrzepis) {
            if ((s.produkt() == Produkt.CUKIER && s.gramow() != 100) ||
                    (s.produkt() == Produkt.MAKA_PSZENNA && s.gramow() != 50) ||
                    (s.produkt() == Produkt.SOL && s.gramow() != 25) ||
                    (s.produkt() == Produkt.DROZDZE && s.gramow() != 10)) {
                passed = false;
                System.out.println("-- " + nazwaTestu + " failed on: PRZELICZANIE PRZEPISU 2");
                break;
            }
        }

        if (passed) {
            System.out.println("--- ++ PASSED ++ ------------------------\n");
            return 1;
        } else {
            System.out.println("--- -- FAILED -- ------------------------\n");
            return 0;
        }
    }

    // - - - -

    private static int testPrzeliczania5() {
        System.out.println("---Test-przeliczania-5-------------------");
        String nazwaTestu = "TestPrzeliczania5";

        boolean passed = true;

        EKuchnia kuchnia = new EKuchnia();

        // DODAWANIE PRODUKTOW DO SPIZARNI
        kuchnia.dodajDoSpizarni(Produkt.CUKIER, 33);
        kuchnia.dodajDoSpizarni(Produkt.MAKA_PSZENNA, 1000);
        kuchnia.dodajDoSpizarni(Produkt.SOL, 1000);
        kuchnia.dodajDoSpizarni(Produkt.DROZDZE, 1000);
        System.out.println("Stan spizarni: " + kuchnia.stanSpizarni());

        // SPRAWDZENIE PRZELICZANIA PRZEPISU
        Przepis przepis = MapToPrzepis(Map.of(
                Produkt.CUKIER, 99,
                Produkt.MAKA_PSZENNA, 3,
                Produkt.SOL, 50,
                Produkt.DROZDZE, 10
        ));
        Set<Skladnik> przeliczonyPrzepis = kuchnia.przeliczPrzepis(przepis);
        System.out.println("Przeliczony przepis: " + przeliczonyPrzepis);
        if (przeliczonyPrzepis.size() != 4) {
            passed = false;
            System.out.println("-- " + nazwaTestu + " failed on: PRZELICZANIE PRZEPISU 1");
        }
        for (Skladnik s : przeliczonyPrzepis) {
            if ((s.produkt() == Produkt.CUKIER && s.gramow() != 33) ||
                    (s.produkt() == Produkt.MAKA_PSZENNA && s.gramow() != 1) ||
                    (s.produkt() == Produkt.SOL && !List.of(16,17).contains(s.gramow())) ||
                    (s.produkt() == Produkt.DROZDZE && !List.of(3,4).contains(s.gramow()))) {
                passed = false;
                System.out.println("-- " + nazwaTestu + " failed on: PRZELICZANIE PRZEPISU 2");
                break;
            }
        }

        if (passed) {
            System.out.println("--- ++ PASSED ++ ------------------------\n");
            return 1;
        } else {
            System.out.println("--- -- FAILED -- ------------------------\n");
            return 0;
        }
    }

    // - - - -

    private static int testWykonywania1() {
        System.out.println("---Test-wykonywania-1--------------------");
        String nazwaTestu = "TestWykonywania1";

        boolean passed = true;

        EKuchnia kuchnia = new EKuchnia();

        // DODAWANIE PRODUKTOW DO SPIZARNI
        kuchnia.dodajDoSpizarni(Produkt.CUKIER, 10);
        kuchnia.dodajDoSpizarni(Produkt.MAKA_PSZENNA, 10);
        kuchnia.dodajDoSpizarni(Produkt.SOL, 10);
        kuchnia.dodajDoSpizarni(Produkt.DROZDZE, 10);
        Map<Produkt, Integer> stanSpizarni = kuchnia.stanSpizarni();
        System.out.println("Stan spizarni: " + stanSpizarni);

        // SPRAWDZANIE WYKONYWANIA
        Przepis przepis = MapToPrzepis(Map.of(
                Produkt.CUKIER, 10,
                Produkt.MAKA_PSZENNA, 10,
                Produkt.SOL, 10,
                Produkt.DROZDZE, 10
        ));

        if ( ! kuchnia.wykonaj(przepis)) {
            passed = false;
            System.out.println("-- " + nazwaTestu + " failed on: WYKONYWANIE 1");
        }
        stanSpizarni = kuchnia.stanSpizarni();
        if (!stanSpizarni.isEmpty()) {
            passed = false;
            System.out.println("-- " + nazwaTestu + " failed on: WYKONYWANIE 2");
        }
        if (stanSpizarni.getOrDefault(Produkt.CUKIER, 0) != 0 ||
                stanSpizarni.getOrDefault(Produkt.MAKA_PSZENNA, 0) != 0 ||
                stanSpizarni.getOrDefault(Produkt.SOL, 0) != 0) {
            passed = false;
            System.out.println("-- " + nazwaTestu + " failed on: WYKONYWANIE 3");
        }

        System.out.println("Po wykonaniu: " + kuchnia.stanSpizarni());

        // drugie wykonanie juz nie moze sie udac
        if (kuchnia.wykonaj(przepis)) {
            passed = false;
            System.out.println("-- " + nazwaTestu + " failed on: WYKONYWANIE 4");
        }

        if (passed) {
            System.out.println("--- ++ PASSED ++ ------------------------\n");
            return 1;
        } else {
            System.out.println("--- -- FAILED -- ------------------------\n");
            return 0;
        }
    }

    // - - - -

    private static int testWykonywania2() {
        System.out.println("---Test-wykonywania-2--------------------");
        String nazwaTestu = "TestWykonywania2";

        boolean passed = true;

        EKuchnia kuchnia = new EKuchnia();

        // DODAWANIE PRODUKTOW DO SPIZARNI
        kuchnia.dodajDoSpizarni(Produkt.CUKIER, 20);
        kuchnia.dodajDoSpizarni(Produkt.MAKA_PSZENNA, 20);
        kuchnia.dodajDoSpizarni(Produkt.SOL, 20);
        kuchnia.dodajDoSpizarni(Produkt.DROZDZE, 20);
        Map<Produkt, Integer> stanSpizarni = kuchnia.stanSpizarni();
        System.out.println("Stan spizarni: " + stanSpizarni);

        // SPRAWDZANIE WYKONYWANIA
        Przepis przepis = MapToPrzepis(Map.of(
                Produkt.CUKIER, 10,
                Produkt.MAKA_PSZENNA, 10,
                Produkt.SOL, 10,
                Produkt.DROZDZE, 10
        ));

        if ( ! kuchnia.wykonaj(przepis)) {
            passed = false;
            System.out.println("-- " + nazwaTestu + " failed on: WYKONYWANIE 1");
        }
        stanSpizarni = kuchnia.stanSpizarni();
        if (stanSpizarni.size() != 4) {
            passed = false;
            System.out.println("-- " + nazwaTestu + " failed on: WYKONYWANIE 2");
        }
        if (stanSpizarni.getOrDefault(Produkt.CUKIER, 0) != 10 ||
                stanSpizarni.getOrDefault(Produkt.MAKA_PSZENNA, 0) != 10 ||
                stanSpizarni.getOrDefault(Produkt.SOL, 0) != 10 ||
                stanSpizarni.getOrDefault(Produkt.DROZDZE, 0) != 10) {
            passed = false;
            System.out.println("-- " + nazwaTestu + " failed on: WYKONYWANIE 3");
        }

        System.out.println("Po wykonaniu: " + kuchnia.stanSpizarni());

        // drugie wykonanie tez musi sie udac
        if ( ! kuchnia.wykonaj(przepis)) {
            passed = false;
            System.out.println("-- " + nazwaTestu + " failed on: WYKONYWANIE 4");
        }
        stanSpizarni = kuchnia.stanSpizarni();
        if (!stanSpizarni.isEmpty()) {
            passed = false;
            System.out.println("-- " + nazwaTestu + " failed on: WYKONYWANIE 2");
        }
        if (stanSpizarni.getOrDefault(Produkt.CUKIER, 0) != 0 ||
                stanSpizarni.getOrDefault(Produkt.MAKA_PSZENNA, 0) != 0 ||
                stanSpizarni.getOrDefault(Produkt.SOL, 0) != 0 ||
                stanSpizarni.getOrDefault(Produkt.DROZDZE, 0) != 0) {
            passed = false;
            System.out.println("-- " + nazwaTestu + " failed on: WYKONYWANIE 3");
        }
        System.out.println("Po 2 wykonaniu: " + kuchnia.stanSpizarni());

        // trzecie wykonanie juz nie moze sie udac
        if (kuchnia.wykonaj(przepis)) {
            passed = false;
            System.out.println("-- " + nazwaTestu + " failed on: WYKONYWANIE 4");
        }

        if (passed) {
            System.out.println("--- ++ PASSED ++ ------------------------\n");
            return 1;
        } else {
            System.out.println("--- -- FAILED -- ------------------------\n");
            return 0;
        }
    }

    // - - - -

    private static int testWykonywania3() {
        System.out.println("---Test-wykonywania-3--------------------");
        String nazwaTestu = "TestWykonywania3";

        boolean passed = true;

        EKuchnia kuchnia = new EKuchnia();

        // DODAWANIE PRODUKTOW DO SPIZARNI
        kuchnia.dodajDoSpizarni(Produkt.CUKIER, 20);
        kuchnia.dodajDoSpizarni(Produkt.MAKA_PSZENNA, 20);
        kuchnia.dodajDoSpizarni(Produkt.SOL, 20);
        kuchnia.dodajDoSpizarni(Produkt.DROZDZE, 20);
        kuchnia.dodajDoSpizarni(Produkt.MARGARYNA, 20);
        Map<Produkt, Integer> stanSpizarni = kuchnia.stanSpizarni();
        System.out.println("Stan spizarni: " + stanSpizarni);

        // SPRAWDZANIE WYKONYWANIA
        Przepis przepis = MapToPrzepis(Map.of(
                Produkt.CUKIER, 10,
                Produkt.MAKA_PSZENNA, 10,
                Produkt.SOL, 10,
                Produkt.DROZDZE, 10
        ));

        if ( ! kuchnia.wykonaj(przepis)) {
            passed = false;
            System.out.println("-- " + nazwaTestu + " failed on: WYKONYWANIE 1");
        }
        stanSpizarni = kuchnia.stanSpizarni();
        if (stanSpizarni.size() != 5) {
            passed = false;
            System.out.println("-- " + nazwaTestu + " failed on: WYKONYWANIE 2");
        }
        if (stanSpizarni.getOrDefault(Produkt.CUKIER, 0) != 10 ||
                stanSpizarni.getOrDefault(Produkt.MAKA_PSZENNA, 0) != 10 ||
                stanSpizarni.getOrDefault(Produkt.SOL, 0) != 10 ||
                stanSpizarni.getOrDefault(Produkt.DROZDZE, 0) != 10 ||
                stanSpizarni.getOrDefault(Produkt.MARGARYNA, 0) != 20) {
            passed = false;
            System.out.println("-- " + nazwaTestu + " failed on: WYKONYWANIE 3");
        }

        System.out.println("Po wykonaniu: " + kuchnia.stanSpizarni());

        // drugie wykonanie tez musi sie udac
        if ( ! kuchnia.wykonaj(przepis)) {
            passed = false;
            System.out.println("-- " + nazwaTestu + " failed on: WYKONYWANIE 4");
        }
        stanSpizarni = kuchnia.stanSpizarni();
        if (stanSpizarni.size() != 1) {
            passed = false;
            System.out.println("-- " + nazwaTestu + " failed on: WYKONYWANIE 2");
        }
        if (stanSpizarni.getOrDefault(Produkt.CUKIER, 0) != 0 ||
                stanSpizarni.getOrDefault(Produkt.MAKA_PSZENNA, 0) != 0 ||
                stanSpizarni.getOrDefault(Produkt.SOL, 0) != 0 ||
                stanSpizarni.getOrDefault(Produkt.DROZDZE, 0) != 0 ||
                stanSpizarni.getOrDefault(Produkt.MARGARYNA, 0) != 20) {
            passed = false;
            System.out.println("-- " + nazwaTestu + " failed on: WYKONYWANIE 3");
        }
        System.out.println("Po 2 wykonaniu: " + kuchnia.stanSpizarni());

        // trzecie wykonanie juz nie moze sie udac
        if (kuchnia.wykonaj(przepis)) {
            passed = false;
            System.out.println("-- " + nazwaTestu + " failed on: WYKONYWANIE 4");
        }

        if (passed) {
            System.out.println("--- ++ PASSED ++ ------------------------\n");
            return 1;
        } else {
            System.out.println("--- -- FAILED -- ------------------------\n");
            return 0;
        }
    }

// === MAIN ============================================================================================================

    public static void main(String[] args) {
        System.out.println();

        int passed = 0;
        int all = 0;

        // wykonanie testow
        passed += testZTresciZadania();
        all++;
        passed += testDodawaniaDoSpizarni();
        all++;
        passed += testPrzeliczania1();
        all++;
        passed += testPrzeliczania2();
        all++;
        passed += testPrzeliczania3();
        all++;
        passed += testPrzeliczania4();
        all++;
        passed += testPrzeliczania5();
        all++;
        passed += testWykonywania1();
        all++;
        passed += testWykonywania2();
        all++;
        passed += testWykonywania3();
        all++;
        //

        // podsumowanie
        System.out.println("Testing ended.\n" + passed + "/" + all + " tests passed.");

    }
}
