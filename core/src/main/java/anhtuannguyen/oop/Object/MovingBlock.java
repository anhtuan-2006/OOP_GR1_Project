package anhtuannguyen.oop.Object;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

/**
 * Lớp MovingBlock đại diện cho một khối di chuyển qua lại theo trục X.
 * Kế thừa từ lớp {@link Block}.
 */
public class MovingBlock extends Block {

    /** Tốc độ di chuyển của khối (pixel/giây). */
    private float speed = 100f;

    /** Hướng di chuyển: 1 = sang phải, -1 = sang trái. */
    private int direction = 1;

    /** Tọa độ X ban đầu của khối. */
    private float originX;

    /** Khoảng cách tối đa mà khối có thể di chuyển tính từ vị trí gốc. */
    private float range = 50f;

    /** Tọa độ X hiện tại của khối (được cập nhật theo thời gian). */
    private float positionX;

    /**
     * Khởi tạo một khối MovingBlock với vị trí, kích thước và texture xác định.
     *
     * @param x      tọa độ X ban đầu của khối
     * @param y      tọa độ Y ban đầu của khối
     * @param width  chiều rộng của khối
     * @param height chiều cao của khối
     * @param tex    texture được sử dụng để hiển thị khối
     */
    public MovingBlock(int x, int y, int width, int height, Texture tex) {
        super(x, y, width, height, tex);
        this.originX = x;
        this.positionX = x;
    }

    /**
     * Cập nhật vị trí di chuyển của khối theo thời gian.
     * Khối di chuyển qua lại giữa {@code originX - range} và {@code originX + range}.
     *
     * @param dt khoảng thời gian giữa hai frame (delta time)
     */
    public void updateMovement(float dt) {
        positionX += direction * speed * dt;

        // Nếu vượt khỏi phạm vi cho phép, đảo hướng di chuyển
        if (positionX < originX - range) {
            positionX = originX - range;
            direction = 1;
        } else if (positionX > originX + range) {
            positionX = originX + range;
            direction = -1;
        }

        // Cập nhật lại vị trí trong hình chữ nhật bao của khối
        getRect().x = positionX;
    }
}
