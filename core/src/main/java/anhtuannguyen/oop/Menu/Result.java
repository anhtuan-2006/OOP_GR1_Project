package anhtuannguyen.oop.Menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Lớp Result quản lý giao diện kết quả sau khi người chơi hoàn thành màn chơi.
 * Hiển thị trạng thái thắng/thua, điểm số hiện tại, điểm cao nhất và các nút điều hướng.
 */
public class Result {
    private final Texture background = new Texture("Menu_background.jpg");
    private final Texture back_button = new Texture("back_button.png");
    private final Texture restart_button = new Texture("restart_button.png");
    private final Texture nextlevel_button = new Texture("play_buttom.png");
    private final Texture win = new Texture("win.png");
    private final Texture lose = new Texture("lose.png");
    private final Texture highscore = new Texture("highscore.png");
    private final Texture yourscore = new Texture("yourscore.png");
    private Viewport viewport;
    private Rectangle bounds;

    public HighScore highScore = new HighScore(viewport);

    private boolean playing = true;
    private boolean back_touch = false;
    private boolean restart_touch = false;
    private boolean nextlevel_touch = false;

    private Rectangle backgroundRect;
    private Rectangle backRect;
    private Rectangle restartRect;
    private Rectangle nextlevelRect;
    private Rectangle result;
    private Rectangle highscoreRect;
    private Rectangle yourscoreRect;

    private final float button_w = 400f;
    private final float button_h = 150f;
    private final float border = 50f;
    private final float buttonSize = 80f;

    private boolean wingame = false;

    private InGame ingame;
    private SelectMap selectmap;
    private GameState state;

    private Score currentScoreDisplay = new Score();
    private Score highestScoreDisplay = new Score();
    private int currentScore = 0;
    private int highestScore = 0;


    /**
     * Constructor khởi tạo giao diện kết quả với viewport.
     * @param _viewport Viewport hiện tại.
     */
    public Result(Viewport _viewport) {
        viewport = _viewport;
    }

    // Getters và Setters
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

    /**
     * Khởi tạo các vùng nút và bố cục giao diện kết quả.
     */
    public void create() {
        backgroundRect = new Rectangle(0, 0, Screen.WORLD_W, Screen.WORLD_H);
        backRect = new Rectangle((Screen.WORLD_W - 400) / 2, 150, 400, 150);
        restartRect = new Rectangle((Screen.WORLD_W - 400) / 2, 350, 400, 150);
        nextlevelRect = new Rectangle((Screen.WORLD_W - 400) / 2, 550, 400, 150);
        result = new Rectangle((Screen.WORLD_W - 1200) / 2, 1150, 1200, 800);
        yourscoreRect = new Rectangle((Screen.WORLD_W - 800) / 2, 800, 800, 150);
        highscoreRect = new Rectangle((Screen.WORLD_W - 800) / 2, 1000, 800, 150);

    }

    /**
     * Thiết lập điểm số hiện tại và đọc điểm cao nhất từ file.
     * @param score Điểm số hiện tại.
     */
    public void setCurrentScore(int score) {
        this.currentScore = score;
        highestScore = readHighestScore();
    }

    /**
     * Cập nhật trạng thái điểm số và xử lý tương tác người dùng.
     */
    public void update() {
        if (currentScore > highestScore) {
            highestScore = currentScore;
            saveHighestScore(highestScore);
        }

        currentScoreDisplay.setScore(currentScore);
        highestScoreDisplay.setScore(highestScore);

        Vector3 v = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        viewport.unproject(v);

        restart_touch = restartRect.contains(v.x, v.y);
        back_touch = backRect.contains(v.x, v.y);
        nextlevel_touch = nextlevelRect.contains(v.x, v.y);

        if (Gdx.input.justTouched()) {
            if (restart_touch) {
                ingame.reset();
                state = GameState.IN_GAME;
            }
            if (back_touch) {
                ingame.reset();
                selectmap.reset();
                highScore.highestScore = highestScore;
                state = GameState.MENU;
            }
            if (nextlevel_touch) {
                ingame.reset();
                selectmap.nextlevel();
                state = GameState.IN_GAME;
            }
        }
    }

    /**
     * Vẽ giao diện kết quả lên màn hình.
     * @param batch SpriteBatch để vẽ.
     */
    public void render(SpriteBatch batch) {
        batch.draw(background, backgroundRect.x, backgroundRect.y, backgroundRect.width, backgroundRect.height);

        if (!wingame)
            batch.draw(lose, result.x, result.y, result.width, result.height);
        else
            batch.draw(win, result.x, result.y, result.width, result.height);

        drawButton(batch, back_button, backRect, back_touch);
        drawButton(batch, restart_button, restartRect, restart_touch);
        drawButton(batch, nextlevel_button, nextlevelRect, nextlevel_touch);
        drawButton(batch, yourscore, yourscoreRect, false);
        drawButton(batch, highscore, highscoreRect, false);




        currentScoreDisplay.setPosition((Screen.WORLD_W) / 2 + 230, 840);
        currentScoreDisplay.renderResult(batch);

        highestScoreDisplay.setPosition((Screen.WORLD_W) / 2 + 230, 1040);
        highestScoreDisplay.renderResult(batch);
    }

    /**
     * Vẽ nút với hiệu ứng phóng to khi hover.
     */
    private void drawButton(SpriteBatch batch, Texture texture, Rectangle rect, boolean isHovered) {
        if (!isHovered) {
            batch.draw(texture, rect.x, rect.y, rect.width, rect.height);
        } else {
            batch.draw(texture, rect.x - 20, rect.y - 20, rect.width + 40, rect.height + 40);
        }
    }

    /**
     * Đọc điểm cao nhất từ file lưu trữ.
     * @return Điểm cao nhất.
     */
    private int readHighestScore() {
        try {
            com.badlogic.gdx.files.FileHandle file = Gdx.files.local("highest_score.txt");
            if (!file.exists()) {
                file.writeString("0", false);
                return 0;
            }
            return Integer.parseInt(file.readString().trim());
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * Lưu điểm cao nhất vào file.
     * @param score Điểm cao nhất mới.
     */
    private void saveHighestScore(int score) {
        com.badlogic.gdx.files.FileHandle file = Gdx.files.local("highest_score.txt");
        file.writeString(String.valueOf(score), false);
    }
}