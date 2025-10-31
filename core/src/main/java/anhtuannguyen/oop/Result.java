package anhtuannguyen.oop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.Viewport;


public class Result {

    private final Texture background = new Texture("Menu_background.jpg");
    private final Texture back_button = new Texture("back_button.png");
    private final Texture restart_button = new Texture("restart_button.png");
    private final Texture nextlevel_button = new Texture("play_buttom.png");
    private final Texture win = new Texture("win.png");
    private final Texture lose = new Texture("lose.png");
    private  Viewport viewport;
    private  Rectangle bounds;
    private boolean playing = true;
    private boolean back_touch = false;
    private boolean restart_touch = false;
    private boolean nextlevel_touch = false;

    private  Rectangle backgroundRect;
    private  Rectangle backRect;
    private  Rectangle restartRect;
    private  Rectangle nextlevelRect;

    private  Rectangle result;

    private final float button_w = 400f;
    private final float button_h = 150f;
    private final float border = 50f;
    private final float buttonSize = 80f;
    private boolean wingame = false;

    private InGame ingame;
    private SelectMap selectmap;
    private GameState state;
    

    public Result(Viewport _viewport) {
        viewport = _viewport;
    }

    
    public void setState(GameState state) {
        this.state = state;
    }
    public GameState getState() {
        return state;
    }
    public void setresult(boolean wingame) {
        System.out.println(wingame);
        this.wingame = wingame;
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

    public void create() {
        backgroundRect = new Rectangle(0,0, Screen.WORLD_W, Screen.WORLD_H);
        backRect = new Rectangle((Screen.WORLD_W-400)/2, (Screen.WORLD_H)/5, 400,150);
        restartRect = new Rectangle((Screen.WORLD_W-400)/2, (Screen.WORLD_H)/5 + 250, 400,150);
        nextlevelRect = new Rectangle((Screen.WORLD_W-400)/2, (Screen.WORLD_H)/5 + 500, 400,150);
        result = new Rectangle((Screen.WORLD_W-1200)/2, (Screen.WORLD_H)/5 + 750, 1200,800);
    }

    public void update() {
        Vector3 v = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        viewport.unproject(v);

        if (restartRect.contains(v.x,v.y)) {
            restart_touch = true;
            if (Gdx.input.justTouched()) {
                ingame.reset();
                state = GameState.IN_GAME;

            }
        }
        else restart_touch = false;

        if (backRect.contains(v.x, v.y)) {
            back_touch = true;
            if (Gdx.input.justTouched()) {
                ingame.reset();
                selectmap.reset();
                state = GameState.MENU;
            }
        }
        else back_touch = false;
        
        if (nextlevelRect.contains(v.x, v.y)) {
            nextlevel_touch = true;
            if (Gdx.input.justTouched()) {
                ingame.reset();
                selectmap.nextlevel();
                state = GameState.IN_GAME;
            }
        }
        else nextlevel_touch = false;
    }

    public void render(SpriteBatch batch) {
        batch.draw(background, backgroundRect.x, backgroundRect.y, backgroundRect.width, backgroundRect.height);
        if (!wingame)
        batch.draw(lose, result.x, result.y, result.width, result.height);
        else 
        batch.draw(win, result.x, result.y, result.width, result.height);
        if (!back_touch)
        batch.draw(back_button, backRect.x, backRect.y, backRect.width, backRect.height);
        else 
        batch.draw(back_button, backRect.x-20, backRect.y-20, backRect.width+40, backRect.height+40);
        if (!restart_touch)
        batch.draw(restart_button, restartRect.x, restartRect.y, restartRect.width, restartRect.height);
        else 
        batch.draw(restart_button, restartRect.x-20, restartRect.y-20, restartRect.width+40, restartRect.height+40);
        if (!nextlevel_touch) {
        batch.draw(nextlevel_button, nextlevelRect.x, nextlevelRect.y, nextlevelRect.width, nextlevelRect.height);
        }
        else 
        batch.draw(nextlevel_button, nextlevelRect.x-20, nextlevelRect.y-20, nextlevelRect.width+40, nextlevelRect.height+40);
    }
}
