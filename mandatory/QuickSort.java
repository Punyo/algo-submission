package mandatory;

public class QuickSort extends Sorts {

    private int median;

    public QuickSort(int[] data) {
        super(data);
    }

    @Override
    public long sort() {
        long startTime = System.currentTimeMillis();
        quickSort(0, data.length - 1);
        long endTime = System.currentTimeMillis();
        if (data.length % 2 == 0) {
            int sumOfMiddleElements = data[data.length / 2] + data[data.length / 2 - 1];
            // 2で割ることで中央値を得る
            median = (sumOfMiddleElements) / 2;
        } else {
            // 配列の要素数が奇数の場合、中央の要素をそのまま中央値とする
            median = data[data.length / 2];
        }
        System.out.println("初期のピボット: " + 0);
        System.out.println("中央値: " + median);
        return endTime - startTime;
    }

    private void quickSort(int low, int high) {
        if (low < high) {
            int pi = partition(low, high);
            quickSort(low, pi - 1);
            quickSort(pi + 1, high);
        }
    }

    // ピボットを選択し、データを分割する
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

    // データの入れ替え
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