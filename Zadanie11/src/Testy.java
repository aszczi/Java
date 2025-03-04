import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Testy {

    public static void main(String[] args) {
        int passed = 0;
        int all = 0;

        passed += testBasicOperations();
        all++;

        passed += testArithmetic();
        all++;

        passed += testJumpAndConditional();
        all++;

        passed += testMemoryInteraction();
        all++;

        System.out.println("\nTesting ended.\n" + passed + "/" + all + " tests passed.");
    }

    private static void announceError(String testName, String message) {
        System.out.println("Test " + testName + " failed: " + message);
    }

    private static int testBasicOperations() {
        String testName = "Basic Operations";
        VonNeumannCPU cpu = new VonNeumannCPU();
        RAM ram = new RAM();
        cpu.setRAM(ram);
       // RAMMemory ram = new RAMMemory(256);
      //  cpu.setRAM(ram);

        // Program: LDA #10; TAX; TXA; END
        ram.set(0, (short) 8);   // LDA #10
        ram.set(1, (short) 10);  // wartość 10
        ram.set(2, (short) 24);  // TAX
        ram.set(3, (short) 25);  // TXA
        ram.set(4, (short) 255); // END

        cpu.execute(0);

        if (cpu.A != 10) {
            announceError(testName, "Expected A = 10, got " + cpu.A);
            return 0;
        }
        if (cpu.X != 10) {
            announceError(testName, "Expected X = 10, got " + cpu.X);
            return 0;
        }
        return 1;
    }

    private static int testArithmetic() {
        String testName = "Arithmetic Operations";
        VonNeumannCPU cpu = new VonNeumannCPU();
        RAM ram = new RAM();
        cpu.setRAM(ram);

        // Program: LDA #5; ADC #10; ADX; ADY; END
        cpu.X = 3;
        cpu.Y = 2;

        ram.set(0, (short) 8);   // LDA #5
        ram.set(1, (short) 5);
        ram.set(2, (short) 28);  // ADC #10
        ram.set(3, (short) 10);
        ram.set(4, (short) 30);  // ADX
        ram.set(5, (short) 30); //smiec
        ram.set(6, (short) 31);  // ADY
        ram.set(7, (short) 30); //smiec
        ram.set(8, (short) 255); // END
        ram.set(9, (short) 255); //smiec

        cpu.execute(0);

        if (cpu.A != 20) {
            announceError(testName, "Expected A = 20, got " + cpu.A);
            return 0;
        }
        return 1;
    }

    private static int testJumpAndConditional() {
        String testName = "Jump and Conditional";
        VonNeumannCPU cpu = new VonNeumannCPU();
        RAM ram = new RAM();
        cpu.setRAM(ram);

        // Program: LDA #5; CMP A,#5; JMP #10; NOP; LDA #15; END
        ram.set(0, (short) 8);   // LDA #5
        ram.set(1, (short) 5);
        ram.set(2, (short) 136); // CMP A,#5
        ram.set(3, (short) 5);
        ram.set(4, (short) 128); // JMP #10
        ram.set(5, (short) 10);
        ram.set(6, (short) 254); // NOP
        ram.set(7, (short) 8);   // LDA #15
        ram.set(8, (short) 15);
        ram.set(10, (short) 255); // END

        cpu.execute(0);

        if (cpu.A != 5) {
            announceError(testName, "Expected A = 5, got " + cpu.A);
            return 0;
        }
        return 1;
    }

    private static int testMemoryInteraction() {
        String testName = "Memory Interaction";
        VonNeumannCPU cpu = new VonNeumannCPU();
        RAM ram = new RAM();
        cpu.setRAM(ram);

        // Program: LDA #50; STA 100; LDA 100; END
        ram.set(0, (short) 8);   // LDA #50
        ram.set(1, (short) 50);
        ram.set(2, (short) 20);  // STA 100
        ram.set(3, (short) 100);
        ram.set(4, (short) 9);   // LDA 100
        ram.set(5, (short) 100);
        ram.set(6, (short) 255); // END

        cpu.execute(0);

        if (cpu.A != 50) {
            announceError(testName, "Expected A = 50, got " + cpu.A);
            return 0;
        }
        if (ram.get(100) != 50) {
            announceError(testName, "Expected RAM[100] = 50, got " + ram.get(100));
            return 0;
        }
        return 1;
    }
}
