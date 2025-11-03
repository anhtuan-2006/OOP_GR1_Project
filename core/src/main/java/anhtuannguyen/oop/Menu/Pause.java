package anhtuannguyen.oop.Menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Lớp Pause quản lý giao diện và logic khi trò chơi đang tạm dừng.
 * Bao gồm các nút Resume, Restart và Back để điều hướng trạng thái trò chơi.
 */
public class Pause extends Screen {
    private final Texture pause = new Texture("pause.png");
    private final Texture play = new Texture("play.png");
    private final Texture background = new Texture("Menu_background.jpg");
    private final Texture back_button = new Texture("back_button.png");
    private final Texture restart_button = new Texture("restart_button.png");
    private final Texture resume_button = new Texture("resume_button.png");

    private Rectangle bounds; // Vị trí nút pause/play
    private boolean playing = true;

    private boolean back_touch = false;
    private boolean restart_touch = false;
    private boolean resume_touch = false;

    private Rectangle backgroundRect;
    private Rectangle backRect;
    private Rectangle restartRect;
    private Rectangle resumeRect;

    private final float button_w = 400f;
    private final float button_h = 150f;
    private final float border = 50f;
    private final float buttonSize = 80f;


    /**
     * Constructor khởi tạo lớp Pause với viewport.
     * @param viewport Viewport hiện tại.
     */
    public Pause(Viewport viewport) {
        this.viewport = viewport;
    }

    /**
     * Khởi tạo các vùng nút và bố cục giao diện tạm dừng.
     */
    public void create() {
        bounds = new Rectangle(Screen.WORLD_W - buttonSize - 20, Screen.WORLD_H - buttonSize - 20, buttonSize, buttonSize);
        backgroundRect = new Rectangle(border, border, Screen.WORLD_W - 2 * border, Screen.WORLD_H - 2 * border);
        backRect = new Rectangle((Screen.WORLD_W - button_w) / 2, (Screen.WORLD_H - button_h) / 2 - button_h * 1.5f, button_w, button_h);
        restartRect = new Rectangle((Screen.WORLD_W - button_w) / 2, (Screen.WORLD_H - button_h) / 2, button_w, button_h);
        resumeRect = new Rectangle((Screen.WORLD_W - button_w) / 2, (Screen.WORLD_H - button_h) / 2 + button_h * 1.5f, button_w, button_h);

        if (bounds != null && backgroundRect != null && backRect != null && restartRect != null && resumeRect != null) {

        }
    }


    /**
     * Cập nhật trạng thái giao diện Pause và xử lý các tương tác người dùng.
     * @return Trạng thái trò chơi sau khi xử lý.
     */
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

        return state;
    }

    /**
     * Đặt lại trạng thái màn chơi hiện tại.
     */
    private void reset() {
        ingame.reset();
    }

    /**
     * Vẽ giao diện Pause lên màn hình.
     * @param batch SpriteBatch để vẽ.
     */
    public void render(SpriteBatch batch) {
        batch.draw(background, backgroundRect.x, backgroundRect.y, backgroundRect.width, backgroundRect.height);
        float hoverScale = 1.1f;
        drawButton(batch, resume_button, resumeRect, resume_touch, hoverScale);
        drawButton(batch, restart_button, restartRect, restart_touch, hoverScale);
        drawButton(batch, back_button, backRect, back_touch, hoverScale);
        batch.draw(play, bounds.x, bounds.y, bounds.width, bounds.height);
    }

    /**
     * Vẽ một nút với hiệu ứng phóng to khi di chuột vào.
     * @param batch SpriteBatch để vẽ.
     * @param texture Texture của nút.
     * @param rect Vị trí và kích thước nút.
     * @param isHovered true nếu chuột đang hover.
     * @param hoverScale Tỷ lệ phóng to khi hover.
     */
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

    /**
     * Kiểm tra trạng thái chơi hiện tại.
     * @return true nếu đang chơi, false nếu đang tạm dừng.
     */
    public boolean isPlaying() {
        return playing;
    }

    /**
     * Giải phóng tài nguyên texture khi không còn sử dụng.
     */
    public void dispose() {
        pause.dispose();
        play.dispose();
        background.dispose();
        back_button.dispose();
        restart_button.dispose();
        resume_button.dispose();
    }
}