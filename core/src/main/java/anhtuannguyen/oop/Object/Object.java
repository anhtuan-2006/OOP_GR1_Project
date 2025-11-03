package anhtuannguyen.oop.Object;

/**
 * Lớp cơ sở {@code Object} đại diện cho một đối tượng có tọa độ trong thế giới game.
 * Các lớp khác (như Ball, Bar, Block, Function, ...) sẽ kế thừa từ lớp này.
 */
public class Object {

    /** Tọa độ X của đối tượng. */
    protected float x;

    /** Tọa độ Y của đối tượng. */
    protected float y;

    /**
     * Lấy tọa độ X hiện tại của đối tượng.
     *
     * @return giá trị x
     */
    public float getx() {
        return x;
    }

    /**
     * Thiết lập tọa độ X cho đối tượng.
     *
     * @param x giá trị x mới
     */
    public void setx(float x) {
        this.x = x;
    }

    /**
     * Lấy tọa độ Y hiện tại của đối tượng.
     *
     * @return giá trị y
     */
    public float gety() {
        return y;
    }

    /**
     * Thiết lập tọa độ Y cho đối tượng.
     *
     * @param y giá trị y mới
     */
    public void sety(float y) {
        this.y = y;
    }
}
