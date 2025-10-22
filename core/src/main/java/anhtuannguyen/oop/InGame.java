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
    private Level8 level8;
    private Level9 level9;
    private Level10 level10;
    private Level11 level11;
    private Level12 level12;

    private int life;

    private Play_Pause play_pause;

    InGame(Viewport _v) {
        viewport = _v;
    }

    public void setLife(int _life) {
        level1.setLife(_life);
    }

    public void create() {
        play_pause = new Play_Pause(viewport);
        level1 = new Level1(play_pause);
        level1.create();
        level2 = new Level2(play_pause);
        level2.create();
        level3 = new Level3(play_pause);
        level3.create();
        level4 = new Level4(play_pause);
        level4.create();
        level5 = new Level5(play_pause);
        level5.create();
        level6 = new Level6(play_pause);
        level6.create();
        level7 = new Level7(play_pause);
        level7.create();
        level8 = new Level8(play_pause);
        level8.create();
        level9 = new Level9(play_pause);
        level9.create();
        level10 = new Level10(play_pause);
        level10.create();
        level11 = new Level11(play_pause);
        level11.create();
        level12 = new Level12(play_pause);
        level12.create();
    }

    public void render(SpriteBatch batch, SelectMap selectmap) {
        int map_number = selectmap.getMap();
        play_pause.update();
        if (map_number == 0) {
            level1.render(batch);
        }
        if (map_number == 1) {
            level2.render(batch);
        }
        if (map_number == 2) {
            level3.render(batch);
        }
        if (map_number == 3) {
            level4.render(batch);
        }
        if (map_number == 4) {
            level5.render(batch);
        }
        if (map_number == 5) {
            level6.render(batch);
        }
        if (map_number == 6) {
            level7.render(batch);
        }
        if (map_number == 7) {
            level8.render(batch);
        }
        if (map_number == 8) {
            level9.render(batch);
        }
        if (map_number == 9) {
            level10.render(batch);
        }
        if (map_number == 10) {
            level11.render(batch);
        }
        if (map_number == 11) {
            level12.render(batch);
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
        level8.dispose();
        level9.dispose();
        level10.dispose();
        level11.dispose();
        level12.dispose();
    }
}