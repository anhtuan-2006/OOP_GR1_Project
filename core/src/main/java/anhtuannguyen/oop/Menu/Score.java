
package anhtuannguyen.oop.Menu;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

/**
 * Lớp Score dùng để hiển thị điểm số của người chơi bằng hình ảnh các chữ số.
 * Mỗi chữ số được vẽ bằng một texture riêng biệt từ 0.png đến 9.png.
 */
public class Score {
    /** Mảng texture chứa hình ảnh từ 0 đến 9 */
    private Texture[] scoreTexture = new Texture[10];
    private Texture whiteTexture;

    /** Mảng vị trí để vẽ các chữ số */
    private Rectangle[] scoreRect = new Rectangle[10];
    private Rectangle whiteRect;

    private static final float WORLD_H = Screen.WORLD_H;
    private static final float WORLD_W = Screen.WORLD_W;

    /** Khoảng cách giữa các chữ số */
    private int space = 55;

    /** Điểm số hiện tại */
    int score;

    /**
     * Constructor khởi tạo texture và vị trí mặc định cho các chữ số.
     */
    public Score() {
        whiteTexture = new Texture("white.png");
        whiteRect = new Rectangle(0, 0 , WORLD_W, 100);
        for (int i = 0; i < 10; i++) {
            scoreTexture[i] = new Texture(i + ".png");
        }

        for (int i = 0; i < 10; i++) {
            scoreRect[i] = new Rectangle(WORLD_W / 2 - 150 + i * space, 12, 50, 80);
        }
    }

    /**
     * Thiết lập điểm số cần hiển thị.
     * 
     * @param _score Điểm số mới.
     */
    public void setScore(int _score) {
        score = _score;
    }

    /**
     * Thiết lập vị trí bắt đầu để vẽ điểm số.
     * 
     * @param x Tọa độ X.
     * @param y Tọa độ Y.
     */
    public void setPosition(float x, float y) {
        for (int i = 0; i < scoreRect.length; i++) {
            scoreRect[i].x = x + i * space;
            scoreRect[i].y = y;
        }
    }

    /**
     * Lấy điểm cao
     * 
     * @param score Điểm số cần hiển thị.
     */
    public int getScore() {
        return score;
    }

    /**
     * Vẽ điểm số lên màn hình bằng SpriteBatch.
     * 
     * @param batch SpriteBatch để vẽ.
     */
    public void render(SpriteBatch batch) {
        batch.draw(whiteTexture, whiteRect.x, whiteRect.y, whiteRect.width, whiteRect.height);
        if (score == 0) {
            batch.draw(scoreTexture[0], 10, scoreRect[0].y, 50, 80);
            return;
        }

        int temp = score;
        int digitCount = 0;
        int[] digits = new int[10];

        // Tách các chữ số từ phải sang trái
        while (temp > 0) {
            digits[digitCount] = temp % 10;
            temp /= 10;
            digitCount++;
        }
    

        // Vẽ các chữ số từ trái sang phải
        for (int i = 0; i < digitCount; i++) {
            int digit = digits[digitCount - 1 - i];

            batch.draw(scoreTexture[digit], i * space + 20, scoreRect[0].y, 50, 80);
        }
    }
    public void renderResult(SpriteBatch batch) {
        if (score == 0) {
            batch.draw(scoreTexture[0], scoreRect[0].x, scoreRect[0].y, 50, 80);
            return;
        }

        int temp = score;
        int digitCount = 0;
        int[] digits = new int[10];

        // Tách các chữ số từ phải sang trái
        while (temp > 0) {
            digits[digitCount] = temp % 10;
            temp /= 10;
            digitCount++;
        }
        // Tính tổng chiều rộng để căn giữa
        float totalWidth = digitCount * 50 + (digitCount - 1) * (space - 50);
        float startX = scoreRect[0].x - totalWidth / 2 + 25;

        // Vẽ các chữ số từ trái sang phải
        for (int i = 0; i < digitCount; i++) {
            int digit = digits[digitCount - 1 - i];
            float x = startX + i * space;
            batch.draw(scoreTexture[digit], x, scoreRect[0].y, 50, 80);
            }
    }
}