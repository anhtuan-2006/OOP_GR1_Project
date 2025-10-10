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

    private Level2 level2;

    private Play_Pause play_pause;

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

        play_pause = new Play_Pause(viewport);

        level2 = new Level2(play_pause);
        level2.create();
    }

    @Override
    public void resize(int width, int height) {
        // Khi cửa sổ thay đổi kích thước, cập nhật viewport
        viewport.update(width, height, true);
    }

    @Override
    public void render() {
        // handleInput(dt); // xử lý input và di chuyển

        // Xóa màn hình với màu nền xám đen
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        cam.update();

        play_pause.update();

        batch.setProjectionMatrix(cam.combined);
        batch.begin();
        level2.render(batch);
        play_pause.render(batch);
        batch.end();
    }

    @Override
    public void dispose() {
        // Giải phóng bộ nhớ ShapeRenderer]
        batch.dispose();
        play_pause.dispose();
        level2.dispose();
    }
}
