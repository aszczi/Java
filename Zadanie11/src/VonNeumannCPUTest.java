public class VonNeumannCPUTest {

    public static void main(String[] args) {
        // Tworzenie procesora
        VonNeumannCPU cpu = new VonNeumannCPU();

        // Test 1: Prosty program inicjalizacyjny
        System.out.println("Test 1: Prosty program");
        byte[] memory1 = new byte[256];
        memory1[0] = 8;  // LDA #0
        memory1[1] = 0;  // wartość 0
        memory1[2] = 13; // LDX 0
        memory1[3] = 0;  // adres 0
        memory1[4] = 4;  // INX
        memory1[5] = 26; // TXY
        memory1[6] = 31; // ADC Y
        memory1[7] = 28; // ADC #2
        memory1[8] = 2;  // wartość 2
        memory1[9] = 20; // STA 1
        memory1[10] = 1; // adres 1
        memory1[11] = -1; // END

        // Ładowanie pamięci i uruchomienie
        cpu.ram.set(0, memory1[0]);
        cpu.ram.set(1, memory1[1]);
        cpu.ram.set(2, memory1[2]);
        cpu.ram.set(3, memory1[3]);
        cpu.ram.set(4, memory1[4]);
        cpu.ram.set(5, memory1[5]);
        cpu.ram.set(6, memory1[6]);
        cpu.ram.set(7, memory1[7]);
        cpu.ram.set(8, memory1[8]);
        cpu.ram.set(9, memory1[9]);
        cpu.ram.set(10, memory1[10]);
        cpu.ram.set(11, memory1[11]);

        cpu.execute(0);

        // Weryfikacja wyników
        if (cpu.ram.get(1) == 4) {
            System.out.println("Test 1 zakończony sukcesem");
        } else {
            System.out.println("Test 1 niepowodzenie: Oczekiwano 4, otrzymano " + cpu.ram.get(1));
        }

        // Test 2: Pętla sumująca wartości
        System.out.println("\nTest 2: Pętla sumująca wartości");
        byte[] memory2 = new byte[256];
        memory2[100] = 8;   // LDA #0
        memory2[101] = 0;   // wartość 0
        memory2[102] = 22;  // LDY #0
        memory2[103] = 0;   // wartość 0
        memory2[104] = 14;  // LDX V,Y
        memory2[105] = 10;  // adres początkowy danych (10)
        memory2[106] = 30;  // ADC X
        memory2[107] = 5;   // INY
        memory2[108] = -118; // CMP Y,#10
        memory2[109] = 10;  // wartość 10
        memory2[110] = -128; // JMP start (adres 104)
        memory2[111] = 104;
        memory2[112] = 20;  // STA 0
        memory2[113] = 0;   // adres 0
        memory2[114] = -1;  // END

        // Załaduj dane do pamięci
        for (int i = 10; i <= 19; i++) {
            memory2[i] = (byte) (i - 9); // Dane od 1 do 10
        }

        // Ładowanie pamięci i uruchomienie
        cpu.ram.set(100, memory2[100]);
        cpu.ram.set(101, memory2[101]);
        cpu.ram.set(102, memory2[102]);
        cpu.ram.set(103, memory2[103]);
        cpu.ram.set(104, memory2[104]);
        cpu.ram.set(105, memory2[105]);
        cpu.ram.set(106, memory2[106]);
        cpu.ram.set(107, memory2[107]);
        cpu.ram.set(108, memory2[108]);
        cpu.ram.set(109, memory2[109]);
        cpu.ram.set(110, memory2[110]);
        cpu.ram.set(111, memory2[111]);
        cpu.ram.set(112, memory2[112]);
        cpu.ram.set(113, memory2[113]);
        cpu.ram.set(114, memory2[114]);

        cpu.execute(100);

        // Weryfikacja wyników
        if (cpu.ram.get(0) == 55) {
            System.out.println("Test 2 zakończony sukcesem");
        } else {
            System.out.println("Test 2 niepowodzenie: Oczekiwano 55, otrzymano " + cpu.ram.get(0));
        }
    }
}
