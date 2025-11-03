package anhtuannguyen.oop.Object;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import anhtuannguyen.oop.Menu.Screen;

/**
 * Lớp Bar mô tả thanh đỡ trong trò chơi Arkanoid.
 * Thanh có thể di chuyển trái/phải và thay đổi kích thước tạm thời khi có hiệu ứng.
 */
public class Bar extends Object {

    private static final float WORLD_W = Screen.WORLD_W;
    private static final float WORLD_H = Screen.WORLD_H;

    /** Ảnh đại diện cho thanh đỡ */
    private Texture texture;

    /** Hình chữ nhật bao quanh thanh, dùng cho va chạm */
    private Rectangle bounds;

    /** Tốc độ di chuyển thanh */
    private float speed = 14;

    /** Trạng thái đang chơi hay tạm dừng */
    boolean playing = true;

    /** Chiều rộng ban đầu của thanh (phục vụ hoàn tác hiệu ứng) */
    private float originalWidth;

    /** Bộ đếm thời gian hiệu ứng (-1 = không chạy) */
    public float effectTimer = -1;

    /**
     * Khởi tạo thanh đỡ.
     * 
     * @param x      Tọa độ X ban đầu
     * @param y      Tọa độ Y ban đầu
     * @param width  Chiều rộng
     * @param height Chiều cao
     * @param _tex   Texture của thanh đỡ
     */
    public Bar(float x, float y, float width, float height, Texture _tex) {
        super.x = x;
        super.y = y;
        texture = _tex;
        bounds = new Rectangle(x, y, width, height);
        originalWidth = width;
    }

    /** Đảo trạng thái chơi/tạm dừng */
    public void isPlaying() {
        playing = !playing;
    }

    /** @return Chiều rộng hiện tại của thanh */
    public float getWidth() {
        return bounds.width;
    }

    /**
     * Cập nhật trạng thái và vị trí thanh theo thời gian.
     * 
     * @param deltaTime Khoảng thời gian giữa 2 frame
     */
    public void update(float deltaTime) {
        if (playing == false)
            return;

        // Đếm thời gian hiệu ứng thu/phóng
        if (effectTimer >= 0) {
            effectTimer += deltaTime;
            if (effectTimer >= 10f) {
                // Hết hiệu ứng: trả thanh về kích thước ban đầu
                x = x + bounds.width / 2 - originalWidth / 2;
                bounds.width = originalWidth;
                effectTimer = -1;
            }
        }

        // Di chuyển trái/phải bằng phím mũi tên hoặc A/D
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A))
            x -= speed;
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D))
            x += speed;

        // Giữ thanh trong phạm vi màn hình
        if (x < 0)
            x = 0;
        if (x + bounds.width > WORLD_W)
            x = WORLD_W - bounds.width;

        bounds.x = x;
    }

    /**
     * Vẽ thanh đỡ lên màn hình.
     * 
     * @param batch SpriteBatch để render
     */
    public void render(SpriteBatch batch) {
        float dt = Gdx.graphics.getDeltaTime();
        update(dt);
        batch.draw(texture, bounds.x, bounds.y, bounds.width, bounds.height);
    }

    /** @return Hình chữ nhật đại diện cho thanh (dùng kiểm tra va chạm) */
    public Rectangle getBounds() {
        return bounds;
    }

    /** Giải phóng tài nguyên texture */
    public void dispose() {
        texture.dispose();
    }
}
