package anhtuannguyen.oop;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class RandomBlock {
    private Rectangle rect;
    private boolean alive = true;
    private Texture texture;
    private Ball ball;

    private static final float WIDTH = Screen.WORLD_W;
    private static final float HEIGHT = Screen.WORLD_H;

    public RandomBlock(float x, float y, Ball _ball,Texture _tex) {
        rect = new Rectangle(x, y, WIDTH, HEIGHT);
        //texture = new Texture("block_random.png");
        texture = _tex;
        ball = _ball;
    }

    public void render(SpriteBatch batch) {
        if (alive) {
            batch.draw(texture, rect.x, rect.y, rect.width, rect.height);
        }
    }

    public void checkCollision() {
        if (!alive) return;

        Rectangle ballRect = new Rectangle(
            (float) ball.getx() - ball.getRADIUS() / 2,
            (float) ball.gety() - ball.getRADIUS() / 2,
            ball.getRADIUS(),
            ball.getRADIUS()
        );

        if (rect.overlaps(ballRect)) {
            // Bật lại bóng
            ball.Change_Direction(3); 

            // Tăng tốc độ bóng lên 1.5 lần
            ball.increaseSpeed(1.5f);

            // Khối biến mất sau va chạm
            alive = false;
        }
    }

    public void dispose() {
        if (texture != null) texture.dispose();
    }
}