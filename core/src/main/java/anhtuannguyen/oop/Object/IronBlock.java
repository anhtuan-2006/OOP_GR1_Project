package anhtuannguyen.oop.Object;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class IronBlock extends Block {

    public IronBlock(int x, int y, int width, int height, Texture tex) {
        super(x, y, width, height, tex);
    }

    public boolean CheckBlock() {
        return true;
    }

}