package anhtuannguyen.oop;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;

public class InGame {
    private static final float WORLD_W = Screen.WORLD_W;
    private static final float WORLD_H = Screen.WORLD_H;
    private Viewport viewport;

    private Level4 level;  

    private Play_Pause play_pause;

    InGame(Viewport _v) {
        viewport = _v;
    }

    public void create() {
        play_pause = new Play_Pause(viewport);
        level = new Level4(play_pause);
        level.create();
    }

    public void render(SpriteBatch batch) {
        play_pause.update();
        level.render(batch);
        play_pause.render(batch);
    }

    public void dispose() {
        play_pause.dispose();
        level.dispose();
    }
}
