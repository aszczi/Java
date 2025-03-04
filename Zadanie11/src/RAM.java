import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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