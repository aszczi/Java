public class ArrayImpl implements Array {
    private final int[] array;

    public ArrayImpl(int[] array) {
        this.array = array;
    }

    @Override
    public int size() {
        return array.length;
    }

    @Override
    public int get(int position) {
        return array[position];
    }

    @Override
    public void set0(int position) {
        array[position] = 0;
    }
}
