import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Testy{

    private static void announceError(String name, int x) {
        System.out.println("\nfailed on: test_" + name + " ("+x+")");
    }

    private static int test_programik1() {
        /*
         * A = 0
         * X = start
         * X = start + 1
         * Y = start + 1
         * A = start + 1
         * A = start + 3
         * return start+3 */
        String name = "programik1";

        JavaCPU cpu = new JavaCPU();
        RAM ram = new RAM();
        cpu.setRAM(ram);

        boolean passed = true;

        for (int start : List.of(0,1,12,69,181,252)) {
            ram.set(0, (short) start);
            cpu.execute(List.of(
                    "LDA #0",
                    "LDX 0",
                    "INX",
                    "TXY",
                    "ADC Y",
                    "ADC #2",
                    "STA 1"));
            if (ram.get(1) != start + 3) {
                announceError(name, start);
                passed = false;
            }
        }

        if (passed) {
            return 1;
        } else {
            return 0;
        }

    }

    private static int test_programik2() {
        /* return: a + b + c + d */
        String name = "programik2";

        JavaCPU cpu = new JavaCPU();
        RAM ram = new RAM();
        cpu.setRAM(ram);

        boolean passed = true;

        for (int a=0; a < 5; a++){
            for (int b=5; b < 36; b+=10) {
                for (int c=2; c < 10; c+=2) {
                    for (int d=13; a < 15; a++) {
                        ram.set(0, (short) a);
                        ram.set(1, (short) b);
                        ram.set(2, (short) c);
                        ram.set(3, (short) d);
                        cpu.execute(List.of(
                                "LDA 0",
                                "LDY #1",
                                "LDX 0,Y",
                                "ADC X",
                                "INY",
                                "LDX 0,Y",
                                "ADC X",
                                "INY",
                                "LDX 0,Y",
                                "ADC X",
                                "STA 4"));
                        if (ram.get(4) != a+b+c+d) {
                            announceError(name, a);
                            passed = false;
                        }
                    }
                }
            }
        }

        if (passed) {
            return 1;
        } else {
            return 0;
        }

    }

    private static int test_programik3() {
        /* return: a + b + c + d */
        String name = "programik3";

        JavaCPU cpu = new JavaCPU();
        RAM ram = new RAM();
        cpu.setRAM(ram);

        boolean passed = true;

        for (int a=0; a < 5; a++){
            for (int b=5; b < 36; b+=10) {
                for (int c=2; c < 10; c+=2) {
                    for (int d=13; a < 15; a++) {
                        ram.set(0, (short) a);
                        ram.set(1, (short) b);
                        ram.set(2, (short) c);
                        ram.set(3, (short) d);
                        cpu.execute(List.of(
                                "LDA 0",
                                "ADC 1",
                                "ADC 2",
                                "ADC 3",
                                "STA 4"));
                        if (ram.get(4) != a+b+c+d) {
                            announceError(name, a);
                            passed = false;
                        }
                    }
                }
            }
        }

        if (passed) {
            return 1;
        } else {
            return 0;
        }

    }

    private static int test_LD_() {
        String name = "test_LD_";
        JavaCPU cpu = new JavaCPU();
        RAM ram = new RAM();
        cpu.setRAM(ram);
        boolean passed = true;

        cpu.execute(List.of(
                "LDA #15",
                "STA 0"
        ));
        if (ram.get(0) != 15) {
            announceError(name, 1);
            passed = false;
        }

        ram.set(255, (short) 69);
        cpu.execute(List.of(
                "LDA 255",
                "STA 1"
        ));
        if (ram.get(1) != 69) {
            announceError(name, 1);
            passed = false;
        }

        ram.set(242, (short) 55);
        cpu.execute(List.of(
                "LDY #12",
                "LDA 230,Y",
                "STA 2"
        ));
        if (ram.get(2) != 55) {
            announceError(name, 1);
            passed = false;
        }

        if (passed) {
            return 1;
        } else {
            return 0;
        }

    }

    private static int test_IN_() {
        String name = "test_IN_";
        JavaCPU cpu = new JavaCPU();
        RAM ram = new RAM();
        cpu.setRAM(ram);
        boolean passed = true;

        cpu.execute(List.of(
                "LDX #15",
                "INX",
                "INX",
                "INX",
                "INX",
                "TXA",
                "STA 0"
        ));
        if (ram.get(0) != 19) {
            announceError(name, 1);
            passed = false;
        }

        cpu.execute(List.of(
                "LDY #20",
                "INY",
                "INY",
                "INY",
                "INY",
                "TYX",
                "TXA",
                "STA 1"
        ));
        if (ram.get(1) != 24) {
            announceError(name, 2);
            passed = false;
        }

        if (passed) {
            return 1;
        } else {
            return 0;
        }

    }

    private static int test_T__() {
        String name = "test_T__";
        JavaCPU cpu = new JavaCPU();
        RAM ram = new RAM();
        cpu.setRAM(ram);
        boolean passed = true;

        cpu.execute(List.of(
                "LDX #15",
                "TXA",
                "STA 0"
        ));
        if (ram.get(0) != 15) {
            announceError(name, 1);
            passed = false;
        }

        cpu.execute(List.of(
                "LDY #34",
                "TYX",
                "TXA",
                "STA 1"
        ));
        if (ram.get(1) != 34) {
            announceError(name, 2);
            passed = false;
        }

        cpu.execute(List.of(
                "LDY #11",
                "TYX",
                "LDX #88",
                "TXA",
                "STA 2"
        ));
        if (ram.get(2) != 88) {
            announceError(name, 3);
            passed = false;
        }

        cpu.execute(List.of(
                "LDA #99",
                "TAX",
                "INX",
                "TXA",
                "STA 3"
        ));
        if (ram.get(3) != 100) {
            announceError(name, 4);
            passed = false;
        }

        cpu.execute(List.of(
                "LDA #77",
                "TAX",
                "LDA #15",
                "TXY",
                "LDX #21",
                "TYX",
                "TXA",
                "STA 4"
        ));
        if (ram.get(4) != 77) {
            announceError(name, 5);
            passed = false;
        }

        if (passed) {
            return 1;
        } else {
            return 0;
        }

    }

    private static int test_ADC() {
        String name = "test_ADC";
        JavaCPU cpu = new JavaCPU();
        RAM ram = new RAM();
        cpu.setRAM(ram);
        boolean passed = true;

        cpu.execute(List.of(
                "LDA #9",
                "ADC #15",   // A += 15 = 24
                "STA 0"
        ));
        if (ram.get(0) != 24) {
            announceError(name, 1);
            passed = false;
        }

        ram.set(255, (short) 69);
        cpu.execute(List.of(
                "ADC 255",
                "STA 1"
        ));
        if (ram.get(1) != 93) { // 69 + 24 = 93
            announceError(name, 2);
            passed = false;
        }

        cpu.execute(List.of(
                "LDY #12",
                "ADC Y",
                "STA 2"
        ));
        if (ram.get(2) != 105) { // 93 + 12
            announceError(name, 3);
            passed = false;
        }

        cpu.execute(List.of(
                "LDX #15",
                "ADC X",
                "STA 2"
        ));
        if (ram.get(2) != 120) { // 105 + 15
            announceError(name, 3);
            passed = false;
        }

        if (passed) {
            return 1;
        } else {
            return 0;
        }
    }

    private static int test_NaSpocenca() {
        String name = "test_NaSpocenca";
        JavaCPU cpu = new JavaCPU();
        RAM ram = new RAM();
        cpu.setRAM(ram);
        boolean passed = true;

        cpu.execute(List.of(
                "LD% #15",
                "IN%",
                "IN%",
                "IN%",
                "IN%",
                "T%A",
                "STA 0"
        ));
        if (ram.get(0) != 18) {
            announceError(name + " NIE MUSI CI PRZEJSC, NIE MARTW SIE", 1);
            passed = false;
        }

        if (passed) {
            return 1;
        } else {
            return 0;
        }

    }





    public static void main(String[] args) {

        int passed = 0;
        int all = 0;

        passed += test_programik1();
        all++;

        passed += test_programik2();
        all++;

        passed += test_programik3();
        all++;

        passed += test_IN_();
        all++;

        passed += test_LD_();
        all++;

        passed += test_T__();
        all++;

        passed += test_ADC();
        all++;

      //  passed += test_NaSpocenca();
        all++;

        // podsumowanie
        System.out.println("\n\nTesting ended.\n" + passed + "/" + all + " tests passed.");

    }

    // ----------------------------------------------------------------------------------------

}

class RAM implements Memory {

    final List<Short> cells = new ArrayList<>(Collections.nCopies(256, (short)0));
    @Override
    public void set(int address, short value) {
        this.cells.set(address, value);
    }

    @Override
    public short get(int address) {
        return this.cells.get(address);
    }

    @Override
    public short size() {
        return 256;
    }
}