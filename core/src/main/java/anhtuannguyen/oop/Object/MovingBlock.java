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

    private float positionX;

public void updateMovement(float dt) {
    positionX += direction * speed * dt;

    // Nếu vượt biên, giới hạn lại và đảo hướng
    if (positionX < originX - range) {
        positionX = originX - range;
        direction = 1;
    } else if (positionX > originX + range) {
        positionX = originX + range;
        direction = -1;
    }

    getRect().x = positionX;
}
}