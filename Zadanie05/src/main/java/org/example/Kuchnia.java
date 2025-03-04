package org.example;

import java.util.Map;
import java.util.Set;

/**
 * Interfejs kuchni
 */
public interface Kuchnia {
    /**
     * Do spiĹźarni dodawany jest produkt w podanej liczbie gramĂłw.
     *
     * @param produkt dodawany produkt
     * @param gram    ciÄĹźar produktu
     */
    void dodajDoSpizarni(Produkt produkt, int gram);

    /**
     * Na podstawie przepisu i stanu spiĹźarni wyliczana jest optymalna gramatura
     * skladnikĂłw tak aby moĹźliwe byĹo przygotowanie przepisu. JeĹli w spiĹźarni brak
     * skĹadnikĂłw potrzebych do przygotowania przepisu zwracany jest pusty zbiĂłr.
     *
     * @param przepis przepis do przygotowania
     * @return sklad przepisu przeliczony wg. stanu spiĹźarni.
     */
    Set<Skladnik> przeliczPrzepis(Przepis przepis);

    /**
     * Na podstawie przekazanego przepisu przygotowywana jest potrawa. Jej
     * przygotowanie obniĹźa stan spiĹźarni.
     *
     * @param przepis przepis na potrawÄ
     * @return true - potrawÄ moĹźna byĹo przygotowaÄ, false - stan spiĹźarni nie
     *         pozwala na przygotowanie potrawy
     */
    boolean wykonaj(Przepis przepis);

    /**
     * Aktualny stan spiĹźarni. Uwaga: raportowane sÄ wyĹÄcznie posiadane produkty.
     * JeĹli aktualna iloĹÄ posiadanego w spiĹźarni produktu wynosi 0, to nie jest on
     * raportowany.
     *
     * @return mapa, w ktorej kluczem jest produkt a wartoscia ciÄĹźar posiadanych
     *         zapasĂłw danego produktu
     */
    Map<Produkt, Integer> stanSpizarni();
}