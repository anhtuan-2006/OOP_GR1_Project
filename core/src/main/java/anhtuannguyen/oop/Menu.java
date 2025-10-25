package anhtuannguyen.oop;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Menu {
    private static final float WORLD_W = Screen.WORLD_W;
    private static final float WORLD_H = Screen.WORLD_H;

    private Texture background;
    private Texture play;
    private Texture exit;
    private Texture setting;
    private Texture logo;   
    public static boolean press = false;

   
    Viewport viewport;

    Rectangle play_size;
    boolean touch_play = false;

    Rectangle exit_size;
    boolean touch_exit = false;

    Rectangle setting_size;
    boolean touch_setting = false;

    Rectangle logo_size;
    
    Menu(Viewport _viewport) {
        viewport = _viewport;
    }

    public void create() {
        background = new Texture("Menu_background.jpg");
        play = new Texture("play_buttom.png");
        setting = new Texture("setting_buttom.png");
        exit = new Texture("exit_buttom.png");
        logo = new Texture("Arkanoid.png");

        play_size = new Rectangle(WORLD_W / 2 - 200, WORLD_H / 2 - 100, 400, 200);
        setting_size = new Rectangle(WORLD_W / 2 - 200, WORLD_H / 2 - 350, 400, 200);
        exit_size = new Rectangle(WORLD_W / 2 - 200, WORLD_H / 2 - 600, 400, 200);
        logo_size = new Rectangle(WORLD_W / 2 - 600, WORLD_H / 3 * 2, 1200, 600);
    }

    public void update() {
        Vector3 v = new Vector3(com.badlogic.gdx.Gdx.input.getX(), com.badlogic.gdx.Gdx.input.getY(), 0);
        viewport.unproject(v);
        if (play_size.contains(v.x, v.y)) {
            touch_play = true;
        } else
            touch_play = false;

        if (setting_size.contains(v.x, v.y)) {
            touch_setting = true;
        } else
            touch_setting = false;

        if (exit_size.contains(v.x, v.y)) {
            touch_exit = true;
        } else
            touch_exit = false;
    }

    public GameState nextscreen(GameState state) {
        if (touch_play == true && com.badlogic.gdx.Gdx.input.justTouched()) {
            Vector3 v = new Vector3(com.badlogic.gdx.Gdx.input.getX(), com.badlogic.gdx.Gdx.input.getY(), 0);
            viewport.unproject(v);
            if (play_size.contains(v.x, v.y)) {
            state = GameState.SELECT_MAP;
            press = true;
                
            }
        }
        return state;
        
        }
    public void render(SpriteBatch batch) {
        update();
        batch.draw(background, 0, 0, WORLD_W, WORLD_H);
        if (touch_play == false)
            batch.draw(play, play_size.x, play_size.y, play_size.width, play_size.height);
        else
            batch.draw(play, play_size.x - 20, play_size.y - 20, play_size.width + 40, play_size.height + 40);

        if (touch_setting == false)
            batch.draw(setting, setting_size.x, setting_size.y, setting_size.width, setting_size.height);
        else
            batch.draw(setting, setting_size.x - 20, setting_size.y - 20, setting_size.width + 40,setting_size.height + 40);

        if (touch_exit == false)
            batch.draw(exit, exit_size.x, exit_size.y, exit_size.width, exit_size.height);
            
        else if (touch_exit == true) {
            batch.draw(exit, exit_size.x - 20, exit_size.y - 20, exit_size.width + 40, exit_size.height + 40);
            if (com.badlogic.gdx.Gdx.input.justTouched()) {
                com.badlogic.gdx.Gdx.app.exit();
            }
        }
        batch.draw(logo, logo_size.x, logo_size.y, logo_size.width, logo_size.height);
    }

    public void dispose() {
        background.dispose();
        play.dispose();
        exit.dispose();
        setting.dispose();
        logo.dispose();
    }
}
