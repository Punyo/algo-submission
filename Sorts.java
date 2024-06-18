public abstract class Sorts {
    public Sorts(int[] data) {
        this.data = data;
        this.originalData = data;
    }

    public void reset() {
        data = originalData;
    }

    protected int[] data;

    private final int[] originalData;

    public abstract long sort();

    public abstract String toString();
}
