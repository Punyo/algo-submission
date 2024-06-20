package optional;

import java.sql.Array;
import java.util.ArrayList;

import mandatory.Sorts;

public class RadixSort_OnlyArray extends Sorts {

    public RadixSort_OnlyArray(int[] data) {
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
        return System.currentTimeMillis() - startTime;
        // 桁別にソート
        // for (int i = longestnum - 1; i >= 0; i--) {
        //     String[][] bucket = new String[10][temp.length];
        //     int[] count = new int[10];

        //     for (int a = 0; a < temp.length; a++) {
        //         int digit = Integer.parseInt(String.valueOf(temp[a].charAt(i)));
        //         bucket[digit][count[digit]] = temp[a];
        //         count[digit]++;
        //     }

        //     int index = 0;
        //     for (int j = 0; j < 10; j++) {
        //         for (int k = 0; k < count[j]; k++) {
        //             temp[index] = bucket[j][k];
        //             index++;
        //         }
        //     }
        // }
      
        // //元の配列に戻す
        // for (int i = 0; i < temp.length; i++) {
        // data[i] = Integer.parseInt(temp[i]);
        // }
    }

    public int[] getData() {
        return data;
    }

    @Override
    public String toString() {
        return "基数ソート";
    }
}
