package optional;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import mandatory.Main;

public class AdditionalDataCreator {
    private static final Main.DatasetDir DATASET_DIR = new Main.DatasetDir(
            "/home/staff/ebn02865/lecture/data-algo/1000000/");
    private static final Path OUTPUT_PATH = Path
            .of("/home/g35714/cnt14029/データ構造とアルゴリズム/additionaldata_output/");

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("1000000のデータセットを何倍にしたものを生成しますか？：");
        try {
            int multiple = scanner.nextInt();
            if (multiple <= 0) {
                System.out.println("1以上の整数を入力してください。");
                main(args);
            }
            if (!Files.exists(OUTPUT_PATH)) {
                try {
                    Files.createDirectories(OUTPUT_PATH);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            for (Main.DatasetFile file : DATASET_DIR.FILES) {
                if (file != null) {
                    try {
                        //ファイルを読み込み、データをlong型の配列に変換
                        List<String> s = Files.readAllLines(file.FILE.toPath());
                        long[] data = new long[s.size()];
                        for (int i = 0; i < s.size(); i++) {
                            data[i] = Long.parseLong(s.get(i));
                        }
                        int length = data.length * multiple;
                        Path dataset_output = OUTPUT_PATH.resolve(Integer.toString(length));
                        if (!Files.exists(dataset_output)) {
                            try {
                                Files.createDirectories(dataset_output);
                            } catch (IOException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                        //データを倍にして出力
                        long[] newData = new long[length];
                        long max = Arrays.stream(data).max().getAsLong();
                        for (int i = 0; i < multiple; i++) {
                            for (int j = 0; j < data.length; j++) {
                                newData[i * data.length + j] = max * i + data[j];
                            }
                        }
                        //書き込み
                        Path path = dataset_output
                                .resolve(file.FILE.getName().replace("1000000", Integer.toString(length)));
                        Files.write(path, Arrays.stream(newData).mapToObj(String::valueOf).collect(Collectors.toList()),
                                StandardCharsets.UTF_8);
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("整数を入力してください。");
            main(args);
        }
    }
}
