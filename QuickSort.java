public class QuickSort extends Sorts {

    public QuickSort(int[] data) {
        super(data);
    }

    @Override
    public long sort() {
        long startTime = System.currentTimeMillis();
        quickSort(0, data.length - 1);
        return System.currentTimeMillis() - startTime;
    }

    private void quickSort(int low, int high) {
        if (low < high) {
            int pi = partition(low, high);
            quickSort(low, pi - 1);
            quickSort(pi + 1, high);
        }
    }

    private int partition(int low, int high) {
        int mid = low + (high - low) / 2;  
        int pivot = data[mid];  
        swap(mid, high);  

        int i = (low - 1);
        for (int j = low; j < high; j++) {
            if (data[j] < pivot) {
                i++;
                swap(i, j);
            }
        }
        swap(i + 1, high);
        return i + 1;
    }

    private void swap(int i, int j) {
        int temp = data[i];
        data[i] = data[j];
        data[j] = temp;
    }

    @Override
    public String toString() {
       return "クイックソート";
    }

}
