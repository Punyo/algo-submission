public class SelectionSort extends Sorts {

    public SelectionSort(int[] data) {
        super(data);
    }

    @Override
    public long sort() {
        long startTime = System.currentTimeMillis();
        for (int i = data.length - 1; i >= 0; i--) {
            int maxIndex = i;
            for (int j = 0; j < i; j++) {
                if (data[j] > data[maxIndex]) {
                    maxIndex = j;
                }
            }
            int temp = data[i];
            data[i] = data[maxIndex];
            data[maxIndex] = temp;
        }
        return System.currentTimeMillis() - startTime;
    }

    @Override
    public String toString() {
        return "選択ソート";
    }
}
