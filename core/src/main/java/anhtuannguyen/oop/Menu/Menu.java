package anhtuannguyen.oop.Menu;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Lớp Menu quản lý giao diện menu chính của trò chơi.
 * Bao gồm các nút Play, Setting, Exit và logo trò chơi.
 */
public class Menu extends Screen {
  

    private Texture background; // Hình nền menu
    private Texture play; // Nút Play
    private Texture exit; // Nút Exit
    private Texture setting; // Nút Setting
    private Texture logo; // Logo trò chơi

    /** Biến tĩnh để đánh dấu người chơi đã nhấn nút nào đó */
    public static boolean press = false;


    Rectangle play_size; // Kích thước vùng nút Play
    boolean touch_play = false;

    Rectangle exit_size; // Kích thước vùng nút Exit
    boolean touch_exit = false;

    Rectangle setting_size; // Kích thước vùng nút Setting
    boolean touch_setting = false;

    Rectangle logo_size; // Kích thước vùng logo

    Texture highscore;;
    Rectangle highscore_size;
    boolean touch_highscore = false;

    String Name = "PLAYER";
    StringBuilder Name_builder = new StringBuilder();
    Texture Name_texture;
    Rectangle Name_rect;
    public boolean inwindown = true;

    Text text = new Text();

    HighScore highScore;

    /**
     * Constructor khởi tạo menu với viewport.
     * 
     * @param _viewport Viewport hiện tại.
     */
    public Menu(Viewport _viewport, HighScore _highScore) {
        viewport = _viewport;
        highScore = _highScore;
    }

    /**
     * Khởi tạo các texture và vùng kích thước của các nút.
     */
    public void create() {
        background = new Texture("Menu_background.jpg");
        play = new Texture("play_buttom.png");
        setting = new Texture("setting_buttom.png");
        exit = new Texture("exit_buttom.png");
        logo = new Texture("Arkanoid.png");
        highscore = new Texture("HighScoreIcon.png");
        Name_texture = new Texture("Name.png");

        play_size = new Rectangle(WORLD_W / 2 - 200, WORLD_H / 2 - 150, 400, 200);
        setting_size = new Rectangle(WORLD_W / 2 - 200, WORLD_H / 2 - 400, 400, 200);
        exit_size = new Rectangle(WORLD_W / 2 - 200, WORLD_H / 2 - 650, 400, 200);
        logo_size = new Rectangle(WORLD_W / 2 - 600, WORLD_H / 3 * 2, 1200, 600);
        highscore_size = new Rectangle(WORLD_W - 250, 250, 200, 200);
        Name_rect = new Rectangle(WORLD_W / 2 - 350, WORLD_H / 2 + 100, 700, 200);

        Gdx.input.setInputProcessor(new InputAdapter() {
            public boolean keyTyped(char character) {
                if(!inwindown) return false;
                if (character == '\b' && Name_builder.length() > 0) {
                    // Xóa ký tự khi nhấn Backspace
                    Name_builder.deleteCharAt(Name_builder.length() - 1);
                } else if (Character.isLetterOrDigit(character) && Name_builder.length() < 10) {
                    // Chỉ cho phép chữ và số, tối đa 10 ký tự
                    Name_builder.append(Character.toUpperCase(character));
                }
                return true;
            }
        });
    }

    /**
     * Cập nhật trạng thái chuột đang chạm vào nút nào.
     */
    public void update() {
        Vector3 v = new Vector3(com.badlogic.gdx.Gdx.input.getX(), com.badlogic.gdx.Gdx.input.getY(), 0);
        viewport.unproject(v);

        touch_play = play_size.contains(v.x, v.y);
        touch_setting = setting_size.contains(v.x, v.y);
        touch_exit = exit_size.contains(v.x, v.y);
        touch_highscore = highscore_size.contains(v.x, v.y);

        // Xử lý nhập tên người chơi
    }

    /**
     * Xử lý chuyển màn hình khi người chơi nhấn nút.
     * 
     * @param state Trạng thái hiện tại của trò chơi.
     * @return Trạng thái mới sau khi xử lý.
     */
    public GameState nextscreen(GameState state) {
        if (touch_play && com.badlogic.gdx.Gdx.input.justTouched()) {
            Vector3 v = new Vector3(com.badlogic.gdx.Gdx.input.getX(), com.badlogic.gdx.Gdx.input.getY(), 0);
            viewport.unproject(v);
            if (play_size.contains(v.x, v.y)) {
                state = GameState.SELECT_MAP;
                press = true;
                Name = Name_builder.toString();
                if (Name.length() == 0)
                    Name = "Player";
                highScore.setName(Name);
            }
        }

        if (touch_setting && com.badlogic.gdx.Gdx.input.justTouched()) {
            Vector3 v = new Vector3(com.badlogic.gdx.Gdx.input.getX(), com.badlogic.gdx.Gdx.input.getY(), 0);
            viewport.unproject(v);
            if (setting_size.contains(v.x, v.y)) {
                state = GameState.SETTING;
                press = true;
            }
        }

        if (touch_exit && com.badlogic.gdx.Gdx.input.justTouched()) {
            Vector3 v = new Vector3(com.badlogic.gdx.Gdx.input.getX(), com.badlogic.gdx.Gdx.input.getY(), 0);
            viewport.unproject(v);
            if (exit_size.contains(v.x, v.y)) {
                com.badlogic.gdx.Gdx.app.exit();
            }
        }

        if (touch_highscore && com.badlogic.gdx.Gdx.input.justTouched()) {
            Vector3 v = new Vector3(com.badlogic.gdx.Gdx.input.getX(), com.badlogic.gdx.Gdx.input.getY(), 0);
            viewport.unproject(v);
            if (highscore_size.contains(v.x, v.y)) {
                state = GameState.HIGHSCORE;
                press = true;
            }
        }

        return state;
    }

    /**
     * Vẽ giao diện menu lên màn hình.
     * 
     * @param batch SpriteBatch để vẽ.
     */
    public void render(SpriteBatch batch) {
        update();
        batch.draw(background, 0, 0, WORLD_W, WORLD_H);

        batch.draw(Name_texture, Name_rect.x, Name_rect.y, Name_rect.width, Name_rect.height);
        text.renderTextName(batch, Name_builder.toString(), WORLD_W / 2, Name_rect.y + 60, 70, 0.75f);

        // Vẽ nút Play
        if (!touch_play)
            batch.draw(play, play_size.x, play_size.y, play_size.width, play_size.height);
        else
            batch.draw(play, play_size.x - 20, play_size.y - 20, play_size.width + 40, play_size.height + 40);

        // Vẽ nút Setting
        if (!touch_setting)
            batch.draw(setting, setting_size.x, setting_size.y, setting_size.width, setting_size.height);
        else
            batch.draw(setting, setting_size.x - 20, setting_size.y - 20, setting_size.width + 40,
                    setting_size.height + 40);

        // Vẽ nút Exit
        if (!touch_exit)
            batch.draw(exit, exit_size.x, exit_size.y, exit_size.width, exit_size.height);
        else {
            batch.draw(exit, exit_size.x - 20, exit_size.y - 20, exit_size.width + 40, exit_size.height + 40);
            if (com.badlogic.gdx.Gdx.input.justTouched()) {
                com.badlogic.gdx.Gdx.app.exit();
            }
        }

        if (!touch_highscore)
            batch.draw(highscore, highscore_size.x, highscore_size.y, highscore_size.width, highscore_size.height);
        else
            batch.draw(highscore, highscore_size.x - 10, highscore_size.y - 10, highscore_size.width + 20,
                    highscore_size.height + 20);

        // Vẽ logo
        batch.draw(logo, logo_size.x, logo_size.y, logo_size.width, logo_size.height);
    }

    /**
     * Giải phóng tài nguyên texture khi không còn sử dụng.
     */
    public void dispose() {
        background.dispose();
        play.dispose();
        exit.dispose();
        setting.dispose();
        logo.dispose();
        highscore.dispose();
    }
}