import java.io.*;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    private static final DatasetDir[] DATASET_DIRS = {
            new DatasetDir("/home/staff/ebn02865/lecture/data-algo/10000"),
            new DatasetDir("/home/staff/ebn02865/lecture/data-algo/50000"),
            new DatasetDir("/home/staff/ebn02865/lecture/data-algo/100000"),
            new DatasetDir("/home/staff/ebn02865/lecture/data-algo/500000"),
            new DatasetDir("/home/staff/ebn02865/lecture/data-algo/1000000")
    };

    private static final Path OUTPUT_PATH = Path.of("/home/g35714/cnt14029/データ構造とアルゴリズム/output1.txt");
    private static final int ATTEMPTS = 10;

    public static void main(String[] args) {
        try (BufferedWriter writer = Files.newBufferedWriter(OUTPUT_PATH)) {
            for (DatasetDir datasetDir : DATASET_DIRS) {
                if (datasetDir.FILES != null) {
                    for (DatasetFile datasetFile : datasetDir.FILES) {
                        if (datasetFile != null) {
                            datasetFile.executeSort();
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        ArrayList<long[]>[] processingTimesbySortAlgorithm = null;
        try (BufferedWriter writer = Files.newBufferedWriter(OUTPUT_PATH, java.nio.file.StandardOpenOption.APPEND)) {
            for (DatasetDir datasetDir : DATASET_DIRS) {
                if (datasetDir.FILES != null) {
                    for (DatasetFile datasetFile : datasetDir.FILES) {
                        if (datasetFile != null) {
                            long[] averageProcessingTimes = datasetFile.getAverageProcessingTimeofAllAttempts();
                            StringBuilder sb = new StringBuilder();
                            sb.append(datasetFile.FILE.getName()).append("\n");
                            for (int i = 0; i < averageProcessingTimes.length; i++) {
                                sb.append(datasetFile.sorts[i].toString()).append(": ");
                                sb.append(averageProcessingTimes[i]).append("ms\n");
                            }
                            sb.append("\n");
                            writer.write(sb.toString());
                        }
                        if (processingTimesbySortAlgorithm == null) {
                            processingTimesbySortAlgorithm = new ArrayList[datasetFile.sorts.length];
                        }
                        for (int i = 0; i < datasetFile.sorts.length; i++) {
                            if (processingTimesbySortAlgorithm[i] == null) {
                                processingTimesbySortAlgorithm[i] = new ArrayList<>();
                            }
                            processingTimesbySortAlgorithm[i].add(datasetFile.getProcessingTimesbySortAlgorithm(i));
                        }
                    }

                    double[] variances = new double[processingTimesbySortAlgorithm.length];
                    for (int i = 0; i < processingTimesbySortAlgorithm.length; i++) {
                        long[] processingTimes = processingTimesbySortAlgorithm[i].stream()
                                .flatMapToLong(Arrays::stream)
                                .toArray();
                        double mean = Arrays.stream(processingTimes).average().orElse(0);
                        double variance = Arrays.stream(processingTimes)
                                .mapToDouble(time -> Math.pow(time - mean, 2))
                                .average()
                                .orElse(0);
                        variances[i] = variance;
                    }

                    StringBuilder sb = new StringBuilder();
                    sb.append(datasetDir.DIR.toPath().toString()).append("\n");
                    for (int i = 0; i < processingTimesbySortAlgorithm.length; i++) {
                        sb.append(datasetDir.FILES[0].sorts[i].toString()).append("\n");
                        sb.append("分散: ");
                        sb.append(variances[i]).append("\n");
                    }
                    writer.write(sb.toString());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class DatasetDir {
        private final DatasetFile[] FILES;
        private final File DIR;

        public DatasetDir(String path) {
            DIR = new File(path);
            ArrayList<File> files = new ArrayList<>();
            if (DIR.exists() && DIR.isDirectory()) {
                for (File file : DIR.listFiles()) {
                    if (file.isFile() && !file.getName().contains("sorted")) {
                        files.add(file);
                    }
                }
                FILES = new DatasetFile[files.size()];
                for (int i = 0; i < files.size(); i++) {
                    FILES[i] = new DatasetFile(files.get(i));
                }
            } else {
                FILES = null;
            }
        }
    }

    private static class DatasetFile {
        private final File FILE;
        private ArrayList<long[]> PROCESSING_TIMES = new ArrayList<>();
        private List<String> s;
        private Sorts[] sorts;

        public DatasetFile(File file) {
            this.FILE = file;
        }

        public void executeSort() {
            for (int ia = 0; ia < ATTEMPTS; ia++)
                try {
                    if (s == null) {
                        s = Files.readAllLines(FILE.toPath());
                    }
                    int[] data = new int[s.size()];
                    for (int i = 0; i < s.size(); i++) {
                        data[i] = Integer.parseInt(s.get(i));
                    }
                    StringBuilder sb = new StringBuilder();
                    if (sorts == null) {
                        sorts = new Sorts[] {
                                new SelectionSort(data),
                                new QuickSort(data),
                                new RadixSort(data)
                        };
                    }
                    long[] PROCESSING_TIMES = new long[sorts.length];
                    for (int i = 0; i < sorts.length; i++) {
                        sorts[i].reset();
                        PROCESSING_TIMES[i] = sorts[i].sort();
                    }
                    this.PROCESSING_TIMES.add(PROCESSING_TIMES);
                    System.out.println("ファイル名：" + FILE.getName());
                    System.out.println("試行回数：" + ia);
                    for (int i = 0; i < sorts.length; i++) {
                        sb.append(sorts[i].toString()).append(": ").append(PROCESSING_TIMES[i]).append("ms\n");
                    }
                    System.out.println(sb.toString());
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
        }

        public long[] getProcessingTimesbySortAlgorithm(int sortindex) {
            long[] processingTimes = new long[PROCESSING_TIMES.size()];
            for (int i = 0; i < PROCESSING_TIMES.size(); i++) {
                processingTimes[i] = PROCESSING_TIMES.get(i)[sortindex];
            }
            return processingTimes;
        }

        public long[] getAverageProcessingTimeofAllAttempts() {
            long[] averageProcessingTimes = new long[PROCESSING_TIMES.get(0).length];
            for (int i = 0; i < PROCESSING_TIMES.get(0).length; i++) {
                long sum = 0;
                for (int j = 0; j < PROCESSING_TIMES.size(); j++) {
                    sum += PROCESSING_TIMES.get(j)[i];
                }
                averageProcessingTimes[i] = sum / PROCESSING_TIMES.size();
            }
            return averageProcessingTimes;
        }
    }
}
