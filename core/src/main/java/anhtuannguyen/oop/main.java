package anhtuannguyen.oop;

import java.io.IOException;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import anhtuannguyen.oop.Menu.GameState;
import anhtuannguyen.oop.Menu.HighScore;
import anhtuannguyen.oop.Menu.InGame;
import anhtuannguyen.oop.Menu.Menu;
import anhtuannguyen.oop.Menu.Pause;
import anhtuannguyen.oop.Menu.Result;
import anhtuannguyen.oop.Menu.Screen;
import anhtuannguyen.oop.Menu.SelectMap;
import anhtuannguyen.oop.Menu.Setting;
import anhtuannguyen.oop.Menu.Sound;

/**
 * Lớp main là điểm khởi đầu của trò chơi.
 * <p>
 * Quản lý toàn bộ vòng đời game (create, render, resize, dispose)
 * và điều phối giữa các màn hình (menu, chọn map, gameplay, pause, cài đặt, kết
 * quả).
 * </p>
 */
public class main extends ApplicationAdapter {

    /** Chiều rộng logic của thế giới game. */
    private static final float WORLD_W = Screen.WORLD_W;
    /** Chiều cao logic của thế giới game. */
    private static final float WORLD_H = Screen.WORLD_H;

    /** Camera dùng để hiển thị vùng nhìn. */
    private OrthographicCamera cam;
    /** Viewport để đảm bảo tỉ lệ khung hình cố định. */
    private Viewport viewport;
    /** Batch để vẽ hình ảnh. */
    private SpriteBatch batch;

    /** Màn hình cài đặt. */
    Setting setting;
    /** Quản lý âm thanh nền và hiệu ứng. */
    Sound music;
    /** Số mạng của người chơi. */
    int lifes;

    /** Trạng thái hiện tại của trò chơi. */
    private GameState state = GameState.MENU;

    /** Màn hình menu chính. */
    private Menu menu;
    /** Màn hình chọn bản đồ. */
    private SelectMap selectmap;
    /** Màn hình trong game. */
    private InGame ingame;
    /** Màn hình tạm dừng. */
    private Pause pause;
    /** Màn hình kết quả. */
    private Result result;

    private HighScore highscore;

    /**
     * Hàm khởi tạo game. Được gọi một lần khi game bắt đầu chạy.
     */
    @Override
    public void create() {
        // Thiết lập camera và viewport
        cam = new OrthographicCamera();
        viewport = new FitViewport(WORLD_W, WORLD_H, cam);
        viewport.apply(true);
        cam.position.set(WORLD_W / 2f, WORLD_H / 2f, 0f);
        cam.update();

        // Khởi tạo batch dùng để vẽ
        batch = new SpriteBatch();

        // Tạo âm thanh
        music = new Sound();

        // Tạo các màn hình game
        highscore = new HighScore(viewport);
        highscore.Input();
        menu = new Menu(viewport, highscore);
        menu.create();
        selectmap = new SelectMap(viewport);
        selectmap.create();
        setting = new Setting(viewport, music);
        setting.create();
        ingame = new InGame(viewport);
        ingame.create();
        pause = new Pause(viewport);
        pause.create();

        // Liên kết giữa các màn hình

        result = new Result(viewport, highscore);
        result.create();
        selectmap.setResult(result);
        selectmap.setIngame(ingame);
        selectmap.setPause(pause);

        ingame.setSelectMap(selectmap);
        ingame.setPause(pause);

        pause.setIngame(ingame);
        pause.setSelectMap(selectmap);

        // Lấy số mạng từ setting và gán vào ingame
        lifes = setting.getlife();
        ingame.setLife(lifes);

        result.setIngame(ingame);
        result.setSelectMap(selectmap);

        // Các gán tham chiếu lặp lại (đảm bảo chắc chắn không null)
        selectmap.setIngame(ingame);
        selectmap.setPause(pause);
        ingame.setSelectMap(selectmap);
        ingame.setPause(pause);
        pause.setIngame(ingame);
        pause.setSelectMap(selectmap);
        lifes = setting.getlife(); // Lấy số mạng mặc định từ setting
        ingame.setLife(lifes); // Đặt số mạng cho ingame
        result.setIngame(ingame);
        result.setSelectMap(selectmap);
    }

    /**
     * Cập nhật lại viewport khi cửa sổ bị thay đổi kích thước.
     */
    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    /**
     * Hàm vẽ chính, được gọi liên tục trong vòng lặp game.
     */
    @Override
    public void render() {
        // Xóa màn hình trước mỗi khung hình
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        cam.update();
        batch.setProjectionMatrix(cam.combined);
        batch.begin();

        // Điều hướng giữa các màn hình dựa trên trạng thái game

        if(state == GameState.MENU) {
            menu.inwindown = true;
        } else {
            menu.inwindown = false;
        }

        switch (state) {
            case MENU:
                music.playMusic();
                menu.render(batch);
                state = menu.nextscreen(state);
                break;
            case SELECT_MAP:
                selectmap.setState(state);
                selectmap.update();
                selectmap.render(batch);
                state = selectmap.getSelectedMap();
                break;
            case IN_GAME:
                ingame.setState(state);
                music.decreaseMusic(); // tắt nhạc nền khi vào game
                if (ingame != null) {
                    ingame.update(); // cập nhật logic trong game
                    ingame.render(batch);
                }
                state = ingame.getState();
                break;
            case PAUSE:
                pause.setState(state);
                pause.update();
                pause.render(batch);
                state = pause.getState();
                break;
            case SETTING:
                setting.update();
                lifes = setting.getlife();
                state = setting.getSelectedMap();
                setting.render(batch);
                ingame.setLife(lifes); // cập nhật lại số mạng khi thay đổi
                break;
            case HIGHSCORE:
                highscore.update();
                state = highscore.getSelectedMap();
                highscore.renderHighScore(batch);
                highscore.Output();
                break;
            case RESULT:
                result.setState(state);
                result.setresult(ingame.getresult());
                result.update();
                result.render(batch);
                state = result.getState();
        }

        batch.end();
    }

    /**
     * Giải phóng tài nguyên khi game thoát.
     */
    @Override
    public void dispose() {
        batch.dispose();
        menu.dispose();
        selectmap.dispose();
        ingame.dispose();
        pause.dispose();
    }
}
