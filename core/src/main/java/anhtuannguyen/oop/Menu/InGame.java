package anhtuannguyen.oop.Menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;

import anhtuannguyen.oop.Level.Level1;
import  anhtuannguyen.oop.Level.*;
import anhtuannguyen.oop.Level.LevelBase;

/**
 * Quản lý toàn bộ trạng thái khi đang chơi game.
 */
public class InGame {
    private static final float WORLD_W = Screen.WORLD_W;
    private static final float WORLD_H = Screen.WORLD_H;

    private LevelBase[] levels = new LevelBase[12];
    private int life;

    private final Viewport viewport;
    private Pause pause;
    private SelectMap selectmap;
    private GameState state = GameState.IN_GAME;
    private boolean win;

    public InGame(Viewport _v) {
        this.viewport = _v;
    }

    public Pause getPause() { return pause; }
    public SelectMap getSelectMap() { return selectmap; }
    public GameState getState() { return state; }
    public void setState(GameState _state) { state = _state; }
    public void setPause(Pause _pause) { this.pause = _pause; }
    public void setSelectMap(SelectMap _selectmap) { this.selectmap = _selectmap; }
    public void setresult(boolean win) { this.win = win; }

    public boolean getresult() {
        if (selectmap == null) return false;
        int map = selectmap.getMap();
        return (levels[map] != null) && levels[map].getresult();
    }

    public void setLife(int _life) {
        life = _life;
        for (LevelBase lvl : levels)
            if (lvl != null) lvl.setLife(_life);
    }

    public void create() {
        pause = new Pause(viewport);
        pause.create();

        levels[0] = new Level1(pause);
        levels[1] = new Level2(pause);
        levels[2] = new Level3(pause);
        levels[3] = new Level4(pause);
        levels[4] = new Level5(pause);
        levels[5] = new Level6(pause);
        levels[6] = new Level7(pause);
        levels[7] = new Level8(pause);
        levels[8] = new Level9(pause);
        levels[9] = new Level10(pause);
        levels[10] = new Level11(pause);
        levels[11] = new Level12(pause);

        for (LevelBase lvl : levels)
            if (lvl != null) lvl.create();
    }

    public void update() {
        handleInput();
        toResult();
    }

    private void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            state = (state == GameState.PAUSE) ? GameState.IN_GAME : GameState.PAUSE;
        }
    }

    private void toResult() {
        for (LevelBase lvl : levels) {
            if (lvl != null && lvl.getend()) {
                state = GameState.RESULT;
                if (selectmap != null && selectmap.getResult() != null)
                    selectmap.getResult().setCurrentScore(getScore());
                break;
            }
        }
    }

    public void render(SpriteBatch batch) {
        if (selectmap == null) return;
        int map = selectmap.getMap();
        if (levels[map] != null) levels[map].render(batch);
    }

    public int getScore() {
        if (selectmap == null) return 0;
        int map = selectmap.getMap();
        return (levels[map] != null) ? levels[map].getScore() : 0;
    }

    public void reset() {
        if (selectmap == null) return;
        int map = selectmap.getMap();
        setLife(life);
        if (levels[map] != null) {
            levels[map].setend(false);
            levels[map].dispose();
            levels[map].create();
        }
        if (pause != null) pause.setState(GameState.IN_GAME);
    }

    public void dispose() {
        if (pause != null) pause.dispose();
        for (LevelBase lvl : levels)
            if (lvl != null) lvl.dispose();
    }
}
