package anhtuannguyen.oop;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;

public class InGame {
    private static final float WORLD_W = Screen.WORLD_W;
    private static final float WORLD_H = Screen.WORLD_H;
    private Viewport viewport;
    private Level1 level1;
    private Level2 level2;
    private Level3 level3;
    private Level4 level4;
    private Level5 level5;
    private Level6 level6;
    private Level7 level7;

    private Play_Pause play_pause;

    InGame(Viewport _v) {
        viewport = _v;
    }

    public void create() {
        play_pause = new Play_Pause(viewport);
        level1 = new Level1(play_pause);
        level2 = new Level2(play_pause);
        level3 = new Level3(play_pause);
        level4 = new Level4(play_pause);
        level5 = new Level5(play_pause);
        level6 = new Level6(play_pause);
        level7 = new Level7(play_pause);
        level1.create();
        level2.create();
        level3.create();
        level4.create();
        level5.create();
        level6.create();
        level7.create();
    }

    public void render(SpriteBatch batch, SelectMap selectmap) {
        int map_number = selectmap.getMap();
        play_pause.update();
        if (map_number == 0) {
            level1.render(batch);
        } else if (map_number == 1) {
            level2.render(batch);
        } else if (map_number == 2) {
            level3.render(batch);
        } else if (map_number == 3) {
            level4.render(batch);
        } else if (map_number == 4) {
            level5.render(batch);
        } else if (map_number == 5) {
            level6.render(batch);
        } else if (map_number == 6) {
            level7.render(batch);
        }
        
        
        play_pause.render(batch);
    }

    public void dispose() {
        play_pause.dispose();
        level1.dispose();
        level2.dispose();
        level3.dispose();
        level4.dispose();           
        level5.dispose();
        level6.dispose();
        level7.dispose();
    }
}
