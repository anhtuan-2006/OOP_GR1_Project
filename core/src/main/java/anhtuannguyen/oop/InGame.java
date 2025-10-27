package anhtuannguyen.oop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;

public class InGame {
    private static final float WORLD_W = Screen.WORLD_W;
    private static final float WORLD_H = Screen.WORLD_H;

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

    private final Viewport viewport;
    private Pause pause;
    private SelectMap selectmap;
    private GameState state = GameState.IN_GAME;

    // Constructor
    public InGame(Viewport _v) {
        this.viewport = _v;
    }

    // Getters and Setters
    public Pause getPause() {
        return pause;
    }

    public SelectMap getSelectMap() {
        return selectmap;
    }

    public GameState getState() {
        return state;
    }

    public void setState(GameState _state) {
        state = _state;
        System.out.println("InGame state updated to: " + state); // Debug log
    }

    public void setPause(Pause _pause) {
        this.pause = _pause;
    }

    public void setSelectMap(SelectMap _selectmap) {
        this.selectmap = _selectmap;
    }

    public void setLife(int _life) {
        life = _life;
        level1.setLife(_life);
        level2.setLife(_life);
        level3.setLife(_life);
        level4.setLife(_life);
        level5.setLife(_life);
        level6.setLife(_life);
        level7.setLife(_life);
        level8.setLife(_life);
        level9.setLife(_life);
        level10.setLife(_life);
        level11.setLife(_life);
        level12.setLife(_life);
    }


    public void create() {
        pause = new Pause(viewport);
        pause.create();
        level1 = new Level1(pause);
        level1.create();
        level2 = new Level2(pause);
        level2.create();
        level3 = new Level3(pause);
        level3.create();
        level4 = new Level4(pause);
        level4.create();
        level5 = new Level5(pause);
        level5.create();
        level6 = new Level6(pause);
        level6.create();
        level7 = new Level7(pause);
        level7.create();
        level8 = new Level8(pause);
        level8.create();
        level9 = new Level9(pause);
        level9.create();
        level10 = new Level10(pause);
        level10.create();
        level11 = new Level11(pause);
        level11.create();
        level12 = new Level12(pause);
        level12.create();
    }

    public void update() {

            handleInput(); // Xử lý input từ phím ESC
       
    }

    // Hàm con để xử lý input
    private void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            if (state == GameState.PAUSE) {
                state = GameState.IN_GAME;
            }
             if (state == GameState.IN_GAME) {
                state = GameState.PAUSE;
            }
        }
    }

    public void render(SpriteBatch batch) {

        int map_number = selectmap.getMap();
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
        } else if (map_number == 7) {
            level8.render(batch);
        } else if (map_number == 8) {
            level9.render(batch);
        } else if (map_number == 9) {
            level10.render(batch);
        } else if (map_number == 10) {
            level11.render(batch);
        } else if (map_number == 11) {
            level12.render(batch);
        }


        
        

    }

    public void reset() {
        setLife(life);
        if (selectmap.getMap() == 0){
            level1.dispose();
            level1.create();
        }
        if (selectmap.getMap() == 1) {
            level2.dispose();
            level2.create();
        }
        if (selectmap.getMap() == 2) {
            level3.dispose();
            level3.create();
        }
       
        if (selectmap.getMap() == 3) {
            level4.dispose();
            level4.create();
        }
        if (selectmap.getMap() == 4) {
            level5.dispose();
            level5.create();
        }
        if (selectmap.getMap() == 5) {
            level6.dispose();
            level6.create();
        }
        if (selectmap.getMap() == 6) {
            level7.dispose();
            level7.create();
        }
        if (selectmap.getMap() == 7) {
            level8.dispose();
            level8.create();
        }
        if (selectmap.getMap() == 8) {
            level9.dispose();
            level9.create();
        }
        if (selectmap.getMap() == 9) {
            level10.dispose();
            level10.create();
        }
        if (selectmap.getMap() == 10) {
            level11.dispose();
            level11.create();
        }
         if (selectmap.getMap() == 11) {
            level12.dispose();
            level12.create();
        }
        

        if (pause != null) {
            pause.setState(GameState.IN_GAME); // Đặt lại trạng thái Pause
        }
    }

    public void dispose() {
        if (pause != null) pause.dispose();
        if (level1 != null) level1.dispose();
        if (level2 != null) level2.dispose();
        if (level3 != null) level3.dispose();
        if (level4 != null) level4.dispose();
        if (level5 != null) level5.dispose();
        if (level6 != null) level6.dispose();
        if (level7 != null) level7.dispose();
        if (level8 != null) level8.dispose();
        if (level9 != null) level9.dispose();
        if (level10 != null) level10.dispose();
        if (level11 != null) level11.dispose();
        if (level12 != null) level12.dispose();
    }
}