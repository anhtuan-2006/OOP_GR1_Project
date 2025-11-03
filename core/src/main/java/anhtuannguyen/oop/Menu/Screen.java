package anhtuannguyen.oop.Menu;

import com.badlogic.gdx.utils.viewport.Viewport;

public class Screen {

    /** Chiều rộng của thế giới game (theo đơn vị pixel) */
    public static final float WORLD_W = 1280f;
    /** Chiều cao của thế giới game (theo đơn vị pixel) */
    public static final float WORLD_H = 2000f;

    protected Viewport viewport;

    protected InGame ingame; // Tham chiếu đến InGame
    protected Pause pause;   // Tham chiếu đến Pause
    protected GameState state;
    protected Result result;
    protected SelectMap selectmap;


    public void setState(GameState state) {
        this.state = state;
    }

    public GameState getState() {
        return state;
    }

    public void setIngame(InGame ingame) {
        this.ingame = ingame;
    }

    public InGame getIngame() {
        return ingame;
    }

    public void setSelectMap(SelectMap selectmap) {
        this.selectmap = selectmap;
    }

    public SelectMap getSelectMap() {
        return selectmap;
    }

    public Pause getPause() {
        return pause;
    }

    public void setPause(Pause _pause) {
        this.pause = _pause;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

}