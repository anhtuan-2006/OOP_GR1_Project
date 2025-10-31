package anhtuannguyen.oop;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class main extends ApplicationAdapter {

    private static final float WORLD_W = Screen.WORLD_W;
    private static final float WORLD_H = Screen.WORLD_H;

    private OrthographicCamera cam;
    private Viewport viewport;

    private SpriteBatch batch;

    Setting setting;

    Sound music;
    int lifes;
    private GameState state = GameState.MENU;
    private Menu menu;
    private SelectMap selectmap;
    private InGame ingame;
    private Pause pause;
    private Result result;

    @Override
    public void create() {
        cam = new OrthographicCamera();
        viewport = new FitViewport(WORLD_W, WORLD_H, cam);
        viewport.apply(true);
        cam.position.set(WORLD_W / 2f, WORLD_H / 2f, 0f);
        cam.update();

        batch = new SpriteBatch();

        music = new Sound();

        menu = new Menu(viewport);
        menu.create();
        selectmap = new SelectMap(viewport);
        selectmap.create();
        setting = new Setting(viewport, music);
        setting.create();
        ingame = new InGame(viewport);
        ingame.create();
        pause = new Pause(viewport);
        pause.create();
        result = new Result(viewport);
        result.create();

        // Gán các tham chiếu cần thiết
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

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        cam.update();
        batch.setProjectionMatrix(cam.combined);
        batch.begin();

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
                music.stopMusic();
                if (ingame != null) {
                    ingame.update(); // Cập nhật input và trạng thái
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
                ingame.setLife(lifes); // Đặt số mạng cho ingame
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

    @Override
    public void dispose() {
        batch.dispose();
        menu.dispose();
        selectmap.dispose();
        ingame.dispose();
        pause.dispose();
    }
}