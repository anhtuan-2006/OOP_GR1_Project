package anhtuannguyen.oop;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Score {
    private Texture[] scoreTexture = new Texture[10];
    private Rectangle[] scoreRect = new Rectangle[10];
    private static final float WORLD_H = Screen.WORLD_H;
    private static final float WORLD_W = Screen.WORLD_W;
    private int space = 55;
    int score;

    public Score() {
        for (int i = 0; i < 10; i++) {
            scoreTexture[i] = new Texture(i + ".png");
        }

        for (int i = 0; i < 10; i++) {
            scoreRect[i] = new Rectangle(WORLD_W / 2 - 150 + i * space, 0, 50, 80);
        }

    }

    public void setScore(int _score) {
        score = _score;
    }

    public void render(SpriteBatch batch) {

        int[] digits = new int[10];
        int tempScore = score;
        for (int i = 5; i >= 0; i--) {
            digits[i] = tempScore % 10;
            tempScore /= 10;
        }

        for (int i = 0; i < 6; i++) {
            batch.draw(scoreTexture[digits[i]], scoreRect[i].x, scoreRect[i].y, scoreRect[i].width,
                    scoreRect[i].height);

        }

    }

}
