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

    public void setPosition(float x, float y) {
    for (int i = 0; i < scoreRect.length; i++) {
        scoreRect[i].x = x + i * space;
        scoreRect[i].y = y;
    }
    }

    public void render(SpriteBatch batch) {
    if (score == 0) {
        batch.draw(scoreTexture[0], scoreRect[0].x, scoreRect[0].y, 50, 80);
        return;
    }
    int temp = score;
    int digitCount = 0;
    int[] digits = new int[10];
    while (temp > 0) {
        digits[digitCount] = temp % 10;
        temp /= 10;
        digitCount++;
    }
    float totalWidth = digitCount * 50 + (digitCount - 1) * (space - 50); // 50 = width của số
    float startX = scoreRect[0].x - totalWidth / 2 + 25; // căn giữa
    for (int i = 0; i < digitCount; i++) {
        int digit = digits[digitCount - 1 - i]; // lấy từ trái sang
        float x = startX + i * space;
        batch.draw(scoreTexture[digit], x, scoreRect[0].y, 50, 80);
    }
}

}
