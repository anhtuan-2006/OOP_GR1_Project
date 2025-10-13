package anhtuannguyen.oop;

import java.util.*;

import org.w3c.dom.Text;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Function {
    private static final float WORLD_H = Screen.WORLD_H;
    private static final float WORLD_W = Screen.WORLD_W;

    private float x;
    private float y;
    private int SPEED = 3;
    private Texture texture;
    boolean alive = true;
    Bar bar;
    Ball ball;
    private int RADIUS = 30;

    private Texture Mulball1 = new Texture("Mulball1.png");
    private Texture Mulball2 = new Texture("Mulball2.png");
    private Texture Mulball3 = new Texture("Mulball3.png");
    private int Mulball_tex = 1;

    private Texture FireBall = new Texture("FireBall.png");

    // Random rand = new Random();
    // private int type = rand.nextInt(2) + 1;
    int type = 2;

    Function(float _x, float _y, Ball _ball) {
        x = _x;
        y = _y;
        ball = _ball;
        bar = ball.bar;
    }

    public int Move() {
        y = y - SPEED;

        Rectangle p = bar.getBounds();
        if (y - RADIUS <= p.y + p.height) {
            alive = false;
            return type;
        }

        if (y - RADIUS <= 0) {
            alive = false;
        }

        return 0;
    }

    public void render(SpriteBatch batch) {
        if (type == 1) {
            if (Mulball_tex == 1)
                batch.draw(Mulball1, x - RADIUS, y - RADIUS, RADIUS * 2, RADIUS * 2);
            if (Mulball_tex == 2)
                batch.draw(Mulball2, x - RADIUS, y - RADIUS, RADIUS * 2, RADIUS * 2);
            if (Mulball_tex == 3)
                batch.draw(Mulball3, x - RADIUS, y - RADIUS, RADIUS * 2, RADIUS * 2);
            Mulball_tex++;
            if (Mulball_tex > 3)
                Mulball_tex -= 3;
        }

        if (type == 2) {
            batch.draw(FireBall, x - RADIUS, y - RADIUS, RADIUS * 2, RADIUS * 2);
        }
    }
}
