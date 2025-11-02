package anhtuannguyen.oop.Menu;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

/**
 * Lớp Life quản lý số mạng sống của người chơi và hiển thị biểu tượng trái tim trên màn hình.
 */
public class Life {
    private static final float WORLD_W = Screen.WORLD_W;
    private static final float WORLD_H = Screen.WORLD_H;

    /** Số mạng hiện tại của người chơi */
    public int lifes;

    /** Texture trái tim biểu tượng mạng sống */
    private Texture texture;

    /** Vị trí vẽ trái tim */
    private float x;
    private float y;

    /** Khoảng cách giữa các trái tim */
    private float spacing = 10f;

    /** Kích thước trái tim */
    private float heartsize = 50f;

    /** Danh sách các hình chữ nhật đại diện cho vị trí vẽ trái tim */
    List<Rectangle> lifeList = new ArrayList<>();

    /**
     * Constructor khởi tạo số mạng và vị trí vẽ trái tim.
     * @param _lifes Số mạng ban đầu.
     */
    public Life(int _lifes) {
        lifes = _lifes;
        System.out.println("Life created with: " + lifes);
        texture = new Texture("heart.png");

        for (int i = 0; i <= lifes; i++) {
            Rectangle lifeRect = new Rectangle();
            lifeRect.width = heartsize;
            lifeRect.height = heartsize;
            lifeRect.x = WORLD_W - heartsize - (i * (heartsize + spacing));
            lifeRect.y = 10;
            lifeList.add(lifeRect);
        }
    }

    /**
     * Trừ đi một mạng khi người chơi mất mạng.
     * @return true nếu vẫn còn mạng sau khi trừ, false nếu đã hết mạng.
     */
    public boolean die() {
        if (lifes > 0) {
            lifes--;
            return true;
        }
        return false;
    }

    /**
     * Lấy số mạng hiện tại.
     * @return Số mạng còn lại.
     */
    public int getLives() {
        return lifes;
    }

    /**
     * Vẽ các biểu tượng trái tim lên màn hình.
     * @param batch SpriteBatch để vẽ.
     */
    public void render(SpriteBatch batch) {
        for (int i = 0; i < lifes; i++) {
            batch.draw(texture, lifeList.get(i).x, lifeList.get(i).y, lifeList.get(i).width, lifeList.get(i).height);
        }
    }

    /**
     * Giải phóng tài nguyên texture khi không còn sử dụng.
     */
    public void dispose() {
        texture.dispose();
    }
}