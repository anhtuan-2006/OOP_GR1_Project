package anhtuannguyen.oop.Menu;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Life {
    private static final float WORLD_W = Screen.WORLD_W;
    private static final float WORLD_H = Screen.WORLD_H;
    public int lifes;
    private Texture texture;
    private float x;
    private float y;
    private float spacing = 10f;
    private float heartsize = 50f;
    List<Rectangle> lifeList = new ArrayList<>();

    public Life(int _lifes) {
        lifes = _lifes;
        System.out.println("Life created with: " + lifes);
        texture = new Texture("heart.png");
        for (int i = 0; i <= lifes; i++) {
            Rectangle lifeRect = new Rectangle();
            lifeRect.width = heartsize;
            lifeRect.height = heartsize;

            lifeRect.x = WORLD_W - heartsize - (i * (heartsize + spacing));
            lifeRect.y = 10;
            // lifeRect.width = 100;
            // lifeRect.height = 100;
            lifeList.add(lifeRect);
        }
    }

    public boolean die() {
        if (lifes > 0) {
            lifes--;
            return true;
        }
        return false;
    }

    public int getLives() {
        return lifes;
    }

    public void render(SpriteBatch batch) {
        for (int i = 0; i < lifes; i++) {
            batch.draw(texture, lifeList.get(i).x, lifeList.get(i).y, lifeList.get(i).width, lifeList.get(i).height);
        }
    }

    public void dispose() {
        texture.dispose();
    }
}
