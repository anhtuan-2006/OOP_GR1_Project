package anhtuannguyen.oop;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all
 * platforms.
 */
public class main extends ApplicationAdapter {

    private static final float WORLD_W = Screen.WORLD_W;
    private static final float WORLD_H = Screen.WORLD_H;

    private OrthographicCamera cam;
    private Viewport viewport;

    private SpriteBatch batch;

    GameState state = GameState.MENU; // Trạng thái hiện tại của

    InGame ingame;
    Menu menu;
    SelectMap selectmap;
    Setting setting;

    Sound music;

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

        music = new Sound();

        menu = new Menu(viewport);
        menu.create();
        selectmap = new SelectMap(viewport);
        selectmap.create();
        ingame = new InGame(viewport);
        ingame.create();
        setting = new Setting(viewport, music);
        setting.create();
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

        batch.setProjectionMatrix(cam.combined);
        batch.begin();
        state = menu.nextscreen(state);
        if (state == GameState.MENU) {
            music.playMusic();
            menu.update();
            menu.render(batch);
        }
        if (state == GameState.SELECT_MAP) {
            selectmap.update();
            state = selectmap.getSelectedMap();
            selectmap.render(batch);
        }
        if(state == GameState.SETTING) {
            setting.update();
            state = setting.getSelectedMap();
            setting.render(batch);
        }
        if (state == GameState.IN_GAME) {

            music.stopMusic();
            ingame.render(batch, selectmap);
        }
        batch.end();
    }

    @Override
    public void dispose() {
        // Giải phóng bộ nhớ ShapeRenderer]
        batch.dispose();
        ingame.dispose();
    }
}
