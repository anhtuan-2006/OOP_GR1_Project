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
import anhtuannguyen.oop.picture;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class main extends ApplicationAdapter {
     // Kích thước logic của thế giới (giữ cố định để không phụ thuộc độ phân giải màn hình)
    private static final float WORLD_W = 1280f;
    private static final float WORLD_H = 720f;

    private static final float RADIUS = 24f;  // Bán kính quả bóng
    private static final float SPEED = 420f;  // Tốc độ di chuyển (pixel/giây)

    private OrthographicCamera cam; // Camera nhìn vào thế giới
    private Viewport viewport;      // Viewport giữ tỉ lệ khi resize
    private ShapeRenderer shapes;   // Dùng để vẽ quả bóng

    private SpriteBatch batch;

    // Tọa độ quả bóng, khởi tạo ở giữa màn hình
    private float x = WORLD_W / 2f;
    private float y = WORLD_H / 2f;

    @Override
    public void create() {
        // Tạo camera trực giao
        cam = new OrthographicCamera();
        // Tạo viewport với kích thước thế giới xác định
        viewport = new FitViewport(WORLD_W, WORLD_H, cam);
        // Áp dụng viewport
        viewport.apply(true);
        // Đặt camera ở giữa thế giới
        cam.position.set(WORLD_W / 2f, WORLD_H / 2f, 0f);
        cam.update();

        batch = new SpriteBatch();
        picture.create_picture();
    }

    @Override
    public void resize(int width, int height) {
        // Khi cửa sổ thay đổi kích thước, cập nhật viewport
        viewport.update(width, height, true);
    }

    // Hàm xử lý input, dt = delta time
    private void handleInput(float dt) {
        float dx = 0, dy = 0; // vector di chuyển

        // Kiểm tra phím bấm
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)  || Gdx.input.isKeyPressed(Input.Keys.A)) dx -= 1;
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) dx += 1;
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)  || Gdx.input.isKeyPressed(Input.Keys.S)) dy -= 1;
        if (Gdx.input.isKeyPressed(Input.Keys.UP)    || Gdx.input.isKeyPressed(Input.Keys.W)) dy += 1;

        // Chuẩn hóa vector để không nhanh hơn khi di chuyển chéo
        if (dx != 0 || dy != 0) {
            float len = (float)Math.sqrt(dx*dx + dy*dy); // độ dài vector
            dx /= len; dy /= len;                        // chuẩn hóa
            x += dx * SPEED * dt;                        // cập nhật x
            y += dy * SPEED * dt;                        // cập nhật y
        }

        // Giữ bóng trong giới hạn màn hình
        x = MathUtils.clamp(x, RADIUS, WORLD_W - RADIUS);
        y = MathUtils.clamp(y, RADIUS, WORLD_H - RADIUS);
    }

    @Override
    public void render() {
        float dt = Gdx.graphics.getDeltaTime(); // thời gian giữa 2 khung hình
        handleInput(dt);                        // xử lý input và di chuyển

        // Xóa màn hình với màu nền xám đen
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(cam.combined);
        picture.render_picture(batch, picture.background, 0, 0, 1280, 720);
        picture.render_picture(batch, picture.ball, (int)(x - RADIUS), (int)(y - RADIUS), 48, 48);
    }

    @Override
    public void dispose() {
        // Giải phóng bộ nhớ ShapeRenderer
        shapes.dispose();
        batch.dispose();
    }
}
