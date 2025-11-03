package anhtuannguyen.oop.Menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.Viewport;

public class HighScore {

    private static final float WORLD_H = Screen.WORLD_H;
    private static final float WORLD_W = Screen.WORLD_W;

    private Texture back_button = new Texture("back_button.png");
    private Texture highscore_board = new Texture("HighScoreBoard.png");
    private Texture background = new Texture("Menu_background.jpg");

    private Rectangle back_button_size = new Rectangle(WORLD_W / 2 - 150, 100, 300, 150);
    private Rectangle highscore_board_size = new Rectangle(10, 300, WORLD_W - 20, 1500);

    private boolean touch_back_button = false;
    private Viewport viewport;

    private Text textRenderer = new Text();
    public int highestScore = 0;

    public HighScore(Viewport viewport) {
        this.viewport = viewport;
        readHighestScore(); // đọc điểm cao khi khởi tạo
    }

    public void readHighestScore() {
        try {
            com.badlogic.gdx.files.FileHandle file = Gdx.files.local("highest_score.txt");
            if (!file.exists()) {
                file.writeString("0", false);
                highestScore = 0;
            } else {
                highestScore = Integer.parseInt(file.readString().trim());
            }
        } catch (Exception e) {
            highestScore = 0;
        }
    }

    public void saveHighestScore(int score) {
        com.badlogic.gdx.files.FileHandle file = Gdx.files.local("highest_score.txt");
        file.writeString(String.valueOf(score), false);
        highestScore = score;
    }

    public GameState getSelectedMap() {
        Vector3 v = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        viewport.unproject(v);
        if (touch_back_button && Gdx.input.justTouched() && back_button_size.contains(v.x, v.y)) {
            return GameState.MENU;
        }
        return GameState.HIGHSCORE;
    }

    public void update() {
        Vector3 v = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        viewport.unproject(v);
        touch_back_button = back_button_size.contains(v.x, v.y);
    }

    

    public void renderHighScore(SpriteBatch batch) {
        // Vẽ nền và bảng
        batch.draw(background, 0, 0, WORLD_W, WORLD_H);
        batch.draw(highscore_board, highscore_board_size.x, highscore_board_size.y,
                   highscore_board_size.width, highscore_board_size.height);

        // Vẽ nút Back
        if (touch_back_button)
            batch.draw(back_button, back_button_size.x - 20, back_button_size.y - 20,
                       back_button_size.width + 40, back_button_size.height + 40);
        else
            batch.draw(back_button, back_button_size.x, back_button_size.y,
                       back_button_size.width, back_button_size.height);

        // Vẽ điểm cao nhất
    
        textRenderer.renderText(batch, "HIGH SCORE", 100, 1200, 2.0f);

        String line = "" + highestScore;
        float startX = highscore_board_size.x + 500;
        float startY = highscore_board_size.y + 600;
        float scale = 0.5f;
        textRenderer.renderText(batch, line, startX, startY, scale);
    }
}