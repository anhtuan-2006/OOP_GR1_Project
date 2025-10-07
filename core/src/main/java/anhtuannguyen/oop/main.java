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

/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all
 * platforms.
 */
public class main extends ApplicationAdapter {
    // Kích thước logic của thế giới (giữ cố định để không phụ thuộc độ phân giải
    // màn hình)
    private static final float WORLD_W = Screen.WORLD_W;
    private static final float WORLD_H = Screen.WORLD_H;

    private OrthographicCamera cam; // Camera nhìn vào thế giới
    private Viewport viewport; // Viewport giữ tỉ lệ khi resize

    private SpriteBatch batch;

    private Level1 level1;

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
        level1 = new Level1();

        level1.create();

    }

    @Override
    public void resize(int width, int height) {
        // Khi cửa sổ thay đổi kích thước, cập nhật viewport
        viewport.update(width, height, true);
    }

    // // Hàm xử lý input, dt = delta time
    // private void handleInput(float dt) {
    // float dx = 0, dy = 0; // vector di chuyển

    // // Kiểm tra phím bấm
    // if (Gdx.input.isKeyPressed(Input.Keys.LEFT) ||
    // Gdx.input.isKeyPressed(Input.Keys.A))
    // dx -= 1;
    // if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) ||
    // Gdx.input.isKeyPressed(Input.Keys.D))
    // dx += 1;
    // if (Gdx.input.isKeyPressed(Input.Keys.DOWN) ||
    // Gdx.input.isKeyPressed(Input.Keys.S))
    // dy -= 1;
    // if (Gdx.input.isKeyPressed(Input.Keys.UP) ||
    // Gdx.input.isKeyPressed(Input.Keys.W))
    // dy += 1;

    // // Chuẩn hóa vector để không nhanh hơn khi di chuyển chéo
    // if (dx != 0 || dy != 0) {
    // float len = (float) Math.sqrt(dx * dx + dy * dy); // độ dài vector
    // dx /= len;
    // dy /= len; // chuẩn hóa
    // x += dx * SPEED * dt; // cập nhật x
    // y += dy * SPEED * dt; // cập nhật y
    // }

    // // Giữ bóng trong giới hạn màn hình
    // x = MathUtils.clamp(x, RADIUS, WORLD_W - RADIUS);
    // y = MathUtils.clamp(y, RADIUS, WORLD_H - RADIUS);
    // }

    @Override
    public void render() {
        // handleInput(dt); // xử lý input và di chuyển

        // Xóa màn hình với màu nền xám đen
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(cam.combined);
        batch.begin();
        level1.render(batch);
        batch.end();
    }

    @Override
    public void dispose() {
        // Giải phóng bộ nhớ ShapeRenderer]
        batch.dispose();
        level1.dispose();
    }
}
