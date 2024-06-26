package mandatory;

import java.sql.Array;
import java.util.ArrayList;

public class RadixSort extends Sorts {

    public RadixSort(int[] data) {
        super(data);
    }

    @Override
    public long sort() {
         long startTime = System.currentTimeMillis();
        String[] temp = new String[data.length];
        int longestnum = -1;
        // 最長の桁数を取得
        for (int i = 0; i < data.length; i++) {
            temp[i] = String.valueOf(data[i]);
            if (longestnum < temp[i].length()) {
                longestnum = temp[i].length();
            }
        }
        // 最長の桁数に合わせて0を追加
        for (int i = 0; i < temp.length; i++) {
            while (temp[i].length() < longestnum) {
                temp[i] = "0" + temp[i];
            }
        }
        // 桁別にソート
        for (int i = longestnum - 1; i >= 0; i--) {
            ArrayList<String>[] bucket = new ArrayList[10];
            for (int j = 0; j < 10; j++) {
                if (bucket[j] == null) {
                    bucket[j] = new ArrayList<>();
                }
            }
            for (int a = 0; a < temp.length; a++) {
                bucket[Integer.parseInt(String.valueOf(temp[a].charAt(i)))].add(temp[a]);
            }

            int index = 0;
            for (int j = 0; j < 10; j++) {
                if (bucket[j] != null) {
                    for (int k = 0; k < bucket[j].size(); k++) {
                        temp[index] = bucket[j].get(k);
                        index++;
                    }
                }
            }
        }
        // 元の配列に戻す
        for (int i = 0; i < temp.length; i++) {
            data[i] = Integer.parseInt(temp[i]);
        }
        return System.currentTimeMillis() - startTime;
    }

    public int[] getData() {
        return data;
    }

    @Override
    public String toString() {
        return "基数ソート";
    }
}
