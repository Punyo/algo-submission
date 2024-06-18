public class MergeSort extends Sorts {


    public MergeSort(int[] data) {
        super(data);
    }

    @Override
    public long sort() {
        long startTime = System.currentTimeMillis();
        mergeSort(data, 0, data.length - 1);
        return System.currentTimeMillis() - startTime;
    }

    private void mergeSort(int[] data, int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;
            mergeSort(data, left, mid);
            mergeSort(data, mid + 1, right);
            merge(data, left, mid, right);
        }
    }

    private void merge(int[] data, int left, int mid, int right) {
        int[] temp = new int[right - left + 1];
        int i = left, j = mid + 1, k = 0;
    
        while (i <= mid && j <= right) {
            if (data[i] <= data[j]) {
                temp[k] = data[i];
                i++;
            } else {
                temp[k] = data[j];
                j++;
            }
            k++;
        }
    
        while (i <= mid) {
            temp[k] = data[i];
            i++;
            k++;
        }
    
        while (j <= right) {
            temp[k] = data[j];
            j++;
            k++;
        }
    
        for (i = left; i <= right; i++) {
            data[i] = temp[i - left];
        }
    }

    public int[] getData() {
        return data;
    }

    @Override
    public  String toString() {
        return "マージソート";
    }
}
