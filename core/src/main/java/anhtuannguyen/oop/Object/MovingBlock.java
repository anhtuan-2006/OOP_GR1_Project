package anhtuannguyen.oop.Object;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class MovingBlock extends Block {
    private float speed = 100f;
    private int direction = 1;
    private float originX;
    private float range = 50f;

    public MovingBlock(int x, int y, int width, int height, Texture tex) {
        super(x, y, width, height, tex);
        this.originX = x;
    }

    public void updateMovement(float dt) {
        Rectangle r = getRect();
        r.x += direction * speed * dt;
        if (r.x < originX - range || r.x > originX + range) {
            direction *= -1;
        }
    }
}