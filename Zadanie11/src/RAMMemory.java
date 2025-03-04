public class RAMMemory implements Memory {
    private short[] memory;

    public RAMMemory(int size) {
        memory = new short[size];
    }

    @Override
    public void set(int address, short value) {
        if (address < 0 || address >= memory.length) {
            throw new IllegalArgumentException("Nieprawidłowy adres pamięci");
        }
        if (value < 0 || value > 255) {
            throw new IllegalArgumentException("Nieprawidłowa wartość (0-255)");
        }
        memory[address] = value;
    }

    @Override
    public short get(int address) {
        if (address < 0 || address >= memory.length) {
            throw new IllegalArgumentException("Nieprawidłowy adres pamięci");
        }
        return memory[address];
    }

    @Override
    public short size() {
        if (memory == null){
            return 0;
        }
        return (short) memory.length;
    }
}