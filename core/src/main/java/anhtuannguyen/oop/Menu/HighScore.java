package anhtuannguyen.oop.Menu;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

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

    private Text text = new Text();

    private Texture rank1 = new Texture("rank1.png");
    private Texture rank2 = new Texture("rank2.png");
    private Texture rank3 = new Texture("rank3.png");

    String Name;

    public HighScore(Viewport viewport) {
        this.viewport = viewport;
    }

    public void setName(String name) {
        if (name.length() == 0)
            name = "Player";
        else
            this.Name = name;
    }

    public void Input() {
        File f = new File("D:\\PROJECT_BT_OOP\\OOP_GR1_Project\\core\\src\\main\\java\\anhtuannguyen\\oop\\Menu\\HighScore.txt");
        System.out.println("Trying file: " + f.getAbsolutePath());
        if (!f.exists()) {
            return;
        }
        try (InputStream file = new FileInputStream(f);
                Scanner inp = new Scanner(new BufferedInputStream(file), "UTF-8")) {
            while (inp.hasNext()) {
                String name = inp.next();
                if (!inp.hasNextInt())
                    break;
                int score = inp.nextInt();
                highScores.put(name, score);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void ChangeHighScore(int Score) {
        if (!highScores.containsKey(Name)) {
            highScores.put(Name, Score);
        } else {
            highScores.put(Name, highScores.get(Name) + Score);
        }
    }

    public void Output() {
        File f = new File("D:\\PROJECT_BT_OOP\\OOP_GR1_Project\\core\\src\\main\\java\\anhtuannguyen\\oop\\Menu\\HighScore.txt");

        try (BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(f))) {
            // Sắp xếp giảm dần theo điểm
            highScores.entrySet()
                    .stream()
                    .sorted((a, b) -> Integer.compare(b.getValue(), a.getValue()))
                    .forEach(e -> {
                        String line = e.getKey() + " " + e.getValue() + "\n";
                        try {
                            out.write(line.getBytes());
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    });
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    /**
     * Trả về danh sách entry đã sắp xếp giảm dần theo score.
     */
    private List<Map.Entry<String, Integer>> getSortedScores() {
        List<Map.Entry<String, Integer>> list = new ArrayList<>(highScores.entrySet());
        list.sort((a, b) -> Integer.compare(b.getValue(), a.getValue())); // giảm dần
        return list;
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

        text.renderText(batch, "HIGH SCORES", WORLD_W / 2 - 450, WORLD_H - 425, 100, 3f);

        float line = WORLD_H - 700;
        int cnt = 0;
        for (Map.Entry<String, Integer> e : getSortedScores()) {
            cnt++;
            if (cnt > 5)
                break;
            String Name = e.getKey();
            int score = e.getValue();
            if (cnt == 1)
                batch.draw(rank1, WORLD_W / 2 - 550, line, 80, 80);
            else if (cnt == 2)
                batch.draw(rank2, WORLD_W / 2 - 550, line, 80, 80);
            else if (cnt == 3)
                batch.draw(rank3, WORLD_W / 2 - 550, line, 80, 80);
            text.renderText(batch, Name, WORLD_W / 2 - 400, line, 60, 1.5f);
            String scoreStr = String.valueOf(score);
            text.renderText(batch, scoreStr, WORLD_W / 2 + 350, line, 60, 1.5f);
            line -= 200;
        }
    }
}