package anhtuannguyen.oop;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import java.util.List;
import java.util.Random;

public class RandomBall {

    private Rectangle rect;
    private boolean alive = true;
    private Texture texture;
    private Ball ball;
    private List<Ball> ballList;

    Bar bar;
    Block block;

    boolean playing = true;

    Play_Pause play_pause;
    RandomBlock randomBlock;

    private static final float WIDTH = Screen.WORLD_W;
    private static final float HEIGHT = Screen.WORLD_H;

    public RandomBall(float x, float y, Ball _ball, List<Ball> _ballList, Texture _tex)
    {
        rect = new Rectangle(x, y, WIDTH/10, HEIGHT/10);
        //texture = new Texture("block_random.png");
        texture = _tex;
        ball = _ball;
        ballList = _ballList;
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
            Random rand = new Random();
            int numberOfBalls = rand.nextInt(4) + 3; // tạo bóng ngẫu nhiên

           for (int i = 0; i < numberOfBalls; i++) {
    Ball newBall = new Ball(ball.getBar(), new Texture("ball.png"));
    newBall.setPosition((float) ball.getx(), (float) ball.gety());

    float angleDeg = rand.nextInt(120) + 30;
    newBall.setAngle((float) Math.toRadians(angleDeg));

    ballList.add(newBall);
}
            alive = false;
        }
    }

    public void dispose() {
        if (texture != null) texture.dispose();
    }


}