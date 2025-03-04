import java.lang.*;

public class VonNeumannCPU implements CPU {

    public int A = 0;
    public int X = 0;
    public int Y = 0;
    public int PC = 0;
    public short global_value = -1;
    public short global_memory_value;
    short currentInstruction; //PC
    short nextInstruction;

    public Memory ram;

    @Override
    public void setRAM(Memory ram) {
        this.ram = ram;
    }

    public void INX(){ //04
        X++;
    }

    public void INY(){ //05
        Y++;
    }

    public void LDA(){ //08
        A = (int)nextInstruction;
    }

    public void LDAV(){ //09
        global_memory_value = ram.get((int)nextInstruction);
        A = (int) global_memory_value;
    }

    public void LDAVY(){ //10
        PC = (int)nextInstruction + Y;
        A = (int) ram.get(PC);
    }

    public void LDX(){ //12
        X = (int)nextInstruction;
    }

    public void LDXV(){  //13
        global_memory_value = ram.get((int)nextInstruction);
        X = (int) global_memory_value;
    }

    public void LDXVY(){ //14
        PC = (int)nextInstruction + Y;
        X = (int) ram.get(PC);
    }

    public void LDY(){ //16
        Y = (int)nextInstruction;
    }

    public void LDYV(){ //17
        global_memory_value = ram.get((int)nextInstruction);
        Y = (int) global_memory_value;
    }

    public void STA(){ //20
        //zapisanie stanu rejestru A pod wartosc v
        int address = nextInstruction;
        global_value = (short) A;
        ram.set(address, global_value);
    }

    public void TAX(){ //24
        X = A;
    }

    public void TXA(){ //25
        A = X;
    }

    public void TXY(){ //26
        A = Y;
    }

    public void TYX(){ //27
        X = Y;
    }

    public void ADA() { //28
        int value = nextInstruction;
        A += value;
    }

    public void ADV() { //29
        int address = nextInstruction;
        A += ram.get(address);
    }

    public void ADX() { //30
        A += X;
    }

    public void ADY() { //31
        A += Y;
    }

    public void JMPV() { // 128
        PC = (int)nextInstruction;
    }

    public void CMPAX() {// 132
        if(A==X){
          PC +=2;
        }
    }

    public void CMPAY() { //133
        if(A==Y){
            PC +=2;
        }
    }

    public void CMPAV(){ //136
        if(A==(int)nextInstruction){
            PC +=2;
        }
    }
    public void CMPXV() {//137
        if(X==(int)nextInstruction){
            PC +=2;
        }
    }
    public void CMPYV() {//138
        if(Y==(int)nextInstruction){
            PC +=2;
        }
    }

    public void NOP() {//254
        //nic sobie nie robi
    }
    public void END() {//255
        //nic nie rob bedzie break
    }


    @Override
    public void execute(int address) {
        PC = address;
        while(true) {
           currentInstruction = ram.get(PC);
           PC++;
           nextInstruction = ram.get(PC);
           PC++;
            String CurrentInstructionString = String.valueOf(currentInstruction);
            if(CurrentInstructionString.length()<3){ CurrentInstructionString = "0"+CurrentInstructionString; }
            if(CurrentInstructionString.length()<3){ CurrentInstructionString = "0"+CurrentInstructionString; }

            if(CurrentInstructionString.equals("255")){
                break;
            }

            switch (CurrentInstructionString) {
                case "004":
                    INX();
                    break;
                case "005":
                    INY();
                    break;
                case "008":
                    LDA();
                    break;
                case "009":
                    LDAV();
                    break;
                case "010":
                    LDAVY();
                    break;
                case "012":
                    LDX();
                    break;
                case "013":
                    LDXV();
                    break;
                case "014":
                    LDXVY();
                    break;
                case "016":
                    LDY();
                    break;
                case "017":
                    LDYV();
                    break;
                case "020":
                    STA();
                    break;
                case "024":
                   TAX();
                    break;
                case "025":
                    TXA();
                    break;
                case "026":
                    TXY();
                    break;
                case "027":
                    TYX();
                    break;
                case "028":
                    ADA();
                    break;
                case "029":
                    ADV();
                    break;
                case "030":
                    ADX();
                    break;
                case "031":
                    ADY();
                    break;
                case "128":
                    JMPV();
                    break;
                case "132":
                    CMPAX();
                    break;
                case "133":
                    CMPAY();
                    break;
                case "136":
                    CMPAV();
                    break;
                case "137":
                    CMPXV();
                    break;
                case "138":
                    CMPYV();
                    break;
                case "254":
                    NOP();
                    break;
            }
        }
    }
}