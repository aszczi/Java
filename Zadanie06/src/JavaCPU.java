import java.util.*;
import java.lang.*;
import static java.lang.Integer.*;

public class JavaCPU implements CPU {
    public int A = 0;
    public int X = 0;
    public int Y = 0;
    public int global_address = -1;
    public short global_value = -1;
    public short global_memory_value;
    String currentInstruction;

    private Memory ram;

    @Override
    public void setRAM(Memory ram) {
        this.ram = ram;
    }


    public void AD(String currentString) {

        if (currentString.contains("#")) {
            int value = parseInt(currentString.substring(currentString.indexOf("#") + 1).trim());
            A += value;
        } else {
            char register = currentString.charAt(4);
            switch (register) {
                case 'X':
                    A += X;
                    break;
                case 'Y':
                    A += Y;
                    break;
                default:
                    int address = parseInt(currentString.substring(4).trim());
                    A += ram.get(address);
            }
        }

    }

    public void ST(String currentString){
        int address = parseInt(currentString.substring(4));
        global_value = (short) A;
        ram.set(address, global_value);

    }

    public void TA(){
        X=A;
    }

    public void TX(String currentString){
        char register =  currentString.charAt(2);
        switch(register){
            case 'A':
                A = X;
                break;
            case 'Y':
                Y = X;
                break;
        }
    }

    public void TY(){
        X = Y;
    }

    public void LD(String currentString){
        char register = currentString.charAt(2);
        int value1 = 0;
        int value2 = 0;
        int index = currentString.indexOf(',');

        if(currentString.charAt(4) == '#'){
            value1 = Integer.parseInt(currentString.substring(5));
            // inicjalizacja rejestru (A|X|Y) podaną liczbą V
            switch (register){
                case 'A':
                    A = value1;
                    break;
                case 'X':
                    X = value1;
                    break;
                case 'Y':
                    Y = value1;
                    break;
            }
        }else{
            // nie mamy przecinka, jeden argument
            if (index == -1) {
                int address = parseInt(currentString.substring(4));

                global_memory_value = ram.get(address);
                switch (register){
                    case 'A':
                        A = (int) global_memory_value;
                        break;
                    case 'X':
                        X = (int) global_memory_value;
                        break;
                    case 'Y':
                        Y = (int) global_memory_value;
                        break;
                }
            } else { //mamy przecinek ,dwa argumenty
                value1 = Integer.parseInt(currentString.substring(4, index));

                switch(register){
                    case 'A':
                        global_address = value1 + Y;
                        A = (int) ram.get(global_address);
                        break;
                    case 'X':
                       global_address = value1 + Y;
                       X = (int) ram.get(global_address);
                        break;
                }
            }
        }
    }

    public void IN(String currentString){
        char Operation = currentString.charAt(2);
        switch (Operation){
            case 'X':
                X++;
                break;
            case 'Y':
                Y++;
                break;
        }
    }

    @Override
    public void execute(List<String> code) {
        for (String instruction : code) {
            // Pomijanie pustych instrukcji
            if (instruction == null || instruction.trim().isEmpty()) {
                continue;
            }

            currentInstruction = instruction.trim();
            String first2letters = currentInstruction.substring(0, 2);
            switch (first2letters) {
                case "IN":
                    IN(currentInstruction);
                    break;
                case "LD":
                    LD(currentInstruction);
                    break;
                case "ST":
                    ST(currentInstruction);
                    break;
                case "AD":
                    AD(currentInstruction);
                    break;
                case "TA":
                    TA();
                    break;
                case "TX":
                    TX(currentInstruction);
                    break;
                case "TY":
                    TY();
                    break;
            }
        }
    }
}