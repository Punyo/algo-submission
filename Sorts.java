/**
 * ソートアルゴリズムを実装するための抽象クラス
 */
public abstract class Sorts {
    /**
     * ソート対象のデータを保持
     */
    protected int[] data;

    /**
     * ソート前の元のデータを保持
     */
    private final int[] originalData;

    /**
     * 
     * 
     * @param data ソート対象のデータ
     */
    public Sorts(int[] data) {
        this.data = data;
        this.originalData = data;
    }

    /**
     * データをソートする
     * 
     * @return ソートにかかった時間（ミリ秒単位）
     */
    public abstract long sort();

    /**
     * ソートアルゴリズムの名前を返す
     * 
     * @return ソートアルゴリズムの名前を返す
     */
    public abstract String toString();

    /**
     * データをソート前の状態にリセットする
     */
    public void reset() {
        data = originalData;
    }
}