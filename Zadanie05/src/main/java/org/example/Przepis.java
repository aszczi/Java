package org.example;

import java.util.Set;

/**
 * Interfes przepisu kulinarnego
 */
public interface Przepis {

    /**
     * Metoda dostarczajÄca informacjÄ o nazwie potrawy
     * @return nazwa potrawy, ktĂłra powstanie z uĹźycia Przepisu
     */
    default String naCo() {
        return "Przepis na potrawÄ";
    }

    /**
     * Skladniki przepisu.
     * @return zbiĂłr skĹadnikĂłw
     */
    Set<Skladnik> sklad();
}