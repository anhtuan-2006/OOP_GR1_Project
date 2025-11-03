package anhtuannguyen.oop.Object;

import java.util.Random;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import anhtuannguyen.oop.Menu.Screen;

public class Function extends Object {
    private static final float WORLD_H = Screen.WORLD_H;
    private static final float WORLD_W = Screen.WORLD_W;

    // private float x;
    // private float y;
    private int SPEED = 3;
    private Texture texture;
    boolean alive = true;
    Bar bar;
    Ball ball;
    private int RADIUS = 30;

    private Texture Mulball1 = new Texture("Mulball1.png");
    private Texture Mulball2 = new Texture("Mulball2.png");
    private Texture Mulball3 = new Texture("Mulball3.png");

    private Texture LongBar1 = new Texture("LongBar1.png");
    private Texture LongBar2 = new Texture("LongBar2.png");
    private Texture LongBar3 = new Texture("LongBar3.png");

    private Texture Bigball1 = new Texture("Bigball1.png");
    private Texture Bigball2 = new Texture("Bigball2.png");
    private Texture Bigball3 = new Texture("Bigball3.png");

    private Texture SkipBall1 = new Texture("SkipBall1.png");
    private Texture SkipBall2 = new Texture("SkipBall2.png");
    private Texture SkipBall3 = new Texture("SkipBall3.png");

    private int tex = 1;

    private Texture FireBall = new Texture("FireBall.png");

    Random rand = new Random();
    private int type = rand.nextInt(5) + 1;
    
    Function(float _x, float _y, Ball _ball) {
        x = _x;
        y = _y;
        ball = _ball;
        bar = ball.bar;
    }

    public int Move() {
        y = y - SPEED;
       
        Rectangle p = bar.getBounds();
        if (y - RADIUS <= p.y + p.height && x + RADIUS >= p.x && x - RADIUS <= p.x + p.width) {
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
            if (tex == 1)
                batch.draw(Mulball1, x - RADIUS, y - RADIUS, RADIUS * 2, RADIUS * 2);
            if (tex == 2)
                batch.draw(Mulball2, x - RADIUS, y - RADIUS, RADIUS * 2, RADIUS * 2);
            if (tex == 3)
                batch.draw(Mulball3, x - RADIUS, y - RADIUS, RADIUS * 2, RADIUS * 2);
            tex++;
            if (tex > 3)
                tex -= 3;
        }

        else if (type == 2) {
            batch.draw(FireBall, x - RADIUS, y - RADIUS, RADIUS * 2, RADIUS * 2);
        }

        else if (type == 3) {
            if (tex == 1)
                batch.draw(LongBar1, x - RADIUS, y - RADIUS, RADIUS * 2, RADIUS * 2);
            if (tex == 2)
                batch.draw(LongBar2, x - RADIUS, y - RADIUS, RADIUS * 2, RADIUS * 2);
            if (tex == 3)
                batch.draw(LongBar3, x - RADIUS, y - RADIUS, RADIUS * 2, RADIUS * 2);
            tex++;
            if (tex > 3)
                tex -= 3;
        }
        else if (type == 4) {
            if (tex == 1)
                batch.draw(Bigball1, x - RADIUS, y - RADIUS, RADIUS * 2, RADIUS * 2);
            if (tex == 2)
                batch.draw(Bigball2, x - RADIUS, y - RADIUS, RADIUS * 2, RADIUS * 2);
            if (tex == 3)
                batch.draw(Bigball3, x - RADIUS, y - RADIUS, RADIUS * 2, RADIUS * 2);
            tex++;
            if (tex > 3)
                tex -= 3;
        }
        else if (type == 5) {
            if (tex == 1)
                batch.draw(SkipBall1, x - RADIUS, y - RADIUS, RADIUS * 2, RADIUS * 2);
            if (tex == 2)
                batch.draw(SkipBall2, x - RADIUS, y - RADIUS, RADIUS * 2, RADIUS * 2);
            if (tex == 3)
                batch.draw(SkipBall3, x - RADIUS, y - RADIUS, RADIUS * 2, RADIUS * 2);
            tex++;
            if (tex > 3)
                tex -= 3;
        }
    }
}
