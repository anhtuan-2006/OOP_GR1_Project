package anhtuannguyen.oop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Pause {
    private final Texture pause = new Texture("pause.png");
    private final Texture play = new Texture("play.png");
    private final Texture background = new Texture("Menu_background.jpg");
    private final Texture back_button = new Texture("back_button.png");
    private final Texture restart_button = new Texture("restart_button.png");
    private final Texture resume_button = new Texture("resume_button.png");
    private  Viewport viewport;
    private  Rectangle bounds;
    private boolean playing = true;
    private boolean back_touch = false;
    private boolean restart_touch = false;
    private boolean resume_touch = false;

    private  Rectangle backgroundRect;
    private  Rectangle backRect;
    private  Rectangle restartRect;
    private  Rectangle resumeRect;
    private final float button_w = 400f;
    private final float button_h = 150f;
    private final float border = 50f;
    private final float buttonSize = 80f;


    private InGame ingame;
    private SelectMap selectmap;
    private GameState state = GameState.IN_GAME;

    // Constructor
    public Pause(Viewport viewport) {
        this.viewport = viewport;
    }
    public void create(){
        bounds = new Rectangle(Screen.WORLD_W - buttonSize - 20, Screen.WORLD_H - buttonSize - 20, buttonSize, buttonSize);
        backgroundRect = new Rectangle(border, border, Screen.WORLD_W - 2 * border, Screen.WORLD_H - 2 * border);
        backRect = new Rectangle((Screen.WORLD_W - button_w) / 2, (Screen.WORLD_H - button_h) / 2 - button_h * 1.5f, button_w, button_h);
        restartRect = new Rectangle((Screen.WORLD_W - button_w) / 2, (Screen.WORLD_H - button_h) / 2, button_w, button_h);
        resumeRect = new Rectangle((Screen.WORLD_W - button_w) / 2, (Screen.WORLD_H - button_h) / 2 + button_h * 1.5f, button_w, button_h);
        if (bounds != null && backgroundRect  != null && backRect != null && restartRect != null && resumeRect != null) {
            System.out.println("loaded texture");
        }
    }

    // Getters and Setters
    public InGame getIngame() {
        return ingame;
    }

    public SelectMap getSelectMap() {
        return selectmap;
    }

    public GameState getState() {
        return state;
    }

    public void setState(GameState _state) {
        state = _state;

    }

    public void setIngame(InGame _ingame) {
        this.ingame = _ingame;
    }

    public void setSelectMap(SelectMap _selectmap) {
        this.selectmap = _selectmap;
    }


        // Khởi tạo nếu cần thêm tài nguyên
    
    public GameState update() {
        Vector3 v = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        viewport.unproject(v);


            back_touch = backRect.contains(v.x, v.y);
            restart_touch = restartRect.contains(v.x, v.y);
            resume_touch = resumeRect.contains(v.x, v.y);
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
                    state = GameState.IN_GAME;
}
            if (Gdx.input.justTouched()) {
                if (resume_touch) {
                    playing = true;
                    state = GameState.IN_GAME;
                }
                if (restart_touch && ingame != null) {
                    reset();
                    playing = true;
                    state = GameState.IN_GAME;
                }
                if (back_touch) {
                    reset();
                    selectmap.reset();
                    state = GameState.MENU;
                }
            }
        

        return state; // Trả về trạng thái mới
    }

    private void reset() {
        ingame.reset();
    }
    public void render(SpriteBatch batch) {

        System.out.println("rendered");
         {
            batch.draw(background, backgroundRect.x, backgroundRect.y, backgroundRect.width, backgroundRect.height);
            float hoverScale = 1.1f;
            drawButton(batch, resume_button, resumeRect, resume_touch, hoverScale);
            drawButton(batch, restart_button, restartRect, restart_touch, hoverScale);
            drawButton(batch, back_button, backRect, back_touch, hoverScale);
            batch.draw(play, bounds.x, bounds.y, bounds.width, bounds.height);
        }


    }

    private void drawButton(SpriteBatch batch, Texture texture, Rectangle rect, boolean isHovered, float hoverScale) {
        float x = rect.x;
        float y = rect.y;
        float width = rect.width;
        float height = rect.height;

        if (isHovered) {
            x -= width * (hoverScale - 1) / 2;
            y -= height * (hoverScale - 1) / 2;
            width *= hoverScale;
            height *= hoverScale;
        }
        batch.draw(texture, x, y, width, height);
        System.out.println("drawed");
    }

    public boolean isPlaying() {
        return playing;
    }


    public void dispose() {
        pause.dispose();
        play.dispose();
        background.dispose();
        back_button.dispose();
        restart_button.dispose();
        resume_button.dispose();
    }
}