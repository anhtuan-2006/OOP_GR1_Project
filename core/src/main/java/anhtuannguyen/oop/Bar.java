package anhtuannguyen.oop;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.math.Rectangle;

public class Bar {
    // Phắc
    // Toạ độ của bóng
    // Hình ảnh của bóng
    // Kích thước

    // Khởi tạo bóng
    private Texture texture;
    private Rectangle bounds;
    private float speed = 300;

  
    public Bar(float x, float y, float width, float height) {
        texture = new Texture("bar.jpg"); // ảnh thanh đỡ
        bounds = new Rectangle(x, y, width, height);
    }


    //public void create() {
    //}

    // Thanh di chuyển

    //public void Move() {

    //}

    // Xuất ra màn hình
    public void update(float deltaTime, boolean moveLeft, boolean moveRight) {
        if (moveLeft) {
            bounds.x -= speed * deltaTime;
        }
        if (moveRight) {
            bounds.x += speed * deltaTime;
        }

        // Giữ thanh đỡ trong màn hình
        if (bounds.x < 0) bounds.x = 0;
        if (bounds.x + bounds.width > 800) bounds.x = 800 - bounds.width; // giả sử màn hình rộng 800
    }


    public void render(SpriteBatch batch) {
        batch.draw(texture, bounds.x, bounds.y, bounds.width, bounds.height);
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void dispose() {
        texture.dispose();
    }

}
//