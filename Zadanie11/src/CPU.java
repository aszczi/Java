import java.util.List;

/**
 * Interfejs prostego CPU.
 */
public interface CPU {
    /**
     * Ustawia dostÄp do pamiÄci RAM.
     *
     * @param ram referencja do obiektu zgodnego z Memory.
     */
    void setRAM(Memory ram);

    /**
     * Zleca wykonanie programu zapisanego w pamiÄci RAM.
     * PoczÄtkowy adres, spod ktĂłrego naleĹźy pobraÄ pierwszÄ
     * instrukcjÄ to address.
     *
     * @param address adres, od ktĂłrego rozpoczyna siÄ wykonanie kodu.
     */
    void execute(int address);
}