package anhtuannguyen.oop.Menu;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.w3c.dom.Text;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.Viewport;

public class HighScore {

    private static final float WORLD_H = Screen.WORLD_H;
    private static final float WORLD_W = Screen.WORLD_W;
    private Texture exit;
    private Rectangle back_button_size = new Rectangle((float) (WORLD_W) / 2 - 150, 100, 300, 150);
    private boolean touch_back_button = false;
    private Texture back_button = new Texture("back_button.png");
    Viewport viewport;
    private Texture highscore_board = new Texture("HighScoreBoard.png");
    private Rectangle highscore_board_size = new Rectangle(10, 300, WORLD_W - 20, 1500);

    Map<String, Integer> highScores = new HashMap<>();

    private Texture background = new Texture("Menu_background.jpg");

    public HighScore(Viewport viewport) {
        this.viewport = viewport;
    }

    public void Input() throws IOException {
        InputStream file = new FileInputStream("HighScore.txt");
        Scanner inp = new Scanner(new BufferedInputStream(file));
        while (inp.hasNextLine()) {
            String Name = inp.next();
            int Score = inp.nextInt();
            highScores.put(Name, Score);
        }
        inp.close();
    }

    public void ChangeHighScore(String Name, int Score) {
        if (highScores.containsKey(Name)) {
            if (highScores.get(Name) < Score) {
                highScores.put(Name, Score);
            }
        } else {
            highScores.put(Name, Score);
        }
    }

    public void Output() throws IOException {
        BufferedOutputStream file = new BufferedOutputStream(new FileOutputStream("HighScore.txt"));
        for (String Name : highScores.keySet()) {
            String line = Name + " " + highScores.get(Name) + "\n";
            file.write(line.getBytes());
        }
        file.close();
    }

    public GameState getSelectedMap() {
        Vector3 v = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        viewport.unproject(v);
        if (touch_back_button && Gdx.input.justTouched() && back_button_size.contains(v.x, v.y)) {
            return GameState.MENU;
        }
        return GameState.HIGHSCORE;
    }

    /**
     * Cập nhật trạng thái đầu vào của người chơi và xử lý lựa chọn.
     */
    public void update() {
        Vector3 v = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        viewport.unproject(v);

        // Kiểm tra chạm nút Back
        if (back_button_size.contains(v.x, v.y)) {
            touch_back_button = true;
        } else
            touch_back_button = false;

    }

    public void renderHighScore(SpriteBatch batch) {
        // Vẽ nền
        batch.draw(background, 0, 0, WORLD_W, WORLD_H);
        batch.draw(highscore_board, highscore_board_size.x, highscore_board_size.y, highscore_board_size.width,
                highscore_board_size.height);
        if (touch_back_button)
            batch.draw(back_button, back_button_size.x - 20, back_button_size.y - 20, back_button_size.width + 40,
                    back_button_size.height + 40);
        else
            batch.draw(back_button, back_button_size.x, back_button_size.y, back_button_size.width,
                    back_button_size.height);
    }
}
