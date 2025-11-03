package anhtuannguyen.oop.Object;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

/**
 * Lớp IronBlock đại diện cho loại block không thể bị phá hủy trong game.
 * Kế thừa từ lớp {@link Block}.
 */
public class IronBlock extends Block {

    /**
     * Khởi tạo một khối IronBlock với vị trí, kích thước và texture xác định.
     *
     * @param x      tọa độ X của khối
     * @param y      tọa độ Y của khối
     * @param width  chiều rộng của khối
     * @param height chiều cao của khối
     * @param tex    texture được sử dụng để hiển thị khối
     */
    public IronBlock(int x, int y, int width, int height, Texture tex) {
        super(x, y, width, height, tex);
    }

    /**
     * Kiểm tra tình trạng khối IronBlock.
     * Vì đây là khối sắt, nên luôn trả về true (không thể phá hủy).
     *
     * @return true — khối vẫn còn tồn tại
     */
    @Override
    public boolean CheckBlock() {
        return true;
    }

}
