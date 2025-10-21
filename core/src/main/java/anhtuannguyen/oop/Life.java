package anhtuannguyen.oop;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Life {
    private int lifes;
    private Texture texture;
    private float x;
    private float y;
    private float spacing;
    int WitdhScreen;
    
    public Life(int initialLives, float x, float y, float spacing) {
        this.spacing = spacing;
        texture = new Texture("heart.png");
    }
    public void die() {
        if(lifes > 0) {
            lifes--;
        }
    }
    public void addLife() {
        lifes++;
    }
    public int getLives() {
        return lifes;
    }
    public void render(SpriteBatch batch) {
        for(int i = 0; i < lifes; i++) {
                batch.draw(texture, x + i * (texture.getWidth() + spacing), y);
        }
    }
    public void dispose() {
        texture.dispose();
    }
 }
