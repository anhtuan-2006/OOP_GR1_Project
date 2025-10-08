package anhtuannguyen.oop;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Play_Pause {
    private final Texture pause = new Texture("pause.png");
    private final Texture play = new Texture("play.png");
    private final Viewport viewport;
    private final Rectangle bounds = new Rectangle(0, 0, 150, 150);
    private boolean playing = true;

    public Play_Pause(Viewport viewport) {
        this.viewport = viewport;
    }

    public void update() {
        if (com.badlogic.gdx.Gdx.input.justTouched()) {
            Vector3 v = new Vector3(com.badlogic.gdx.Gdx.input.getX(), com.badlogic.gdx.Gdx.input.getY(), 0);
            viewport.unproject(v);
            if (bounds.contains(v.x, v.y)) {
                playing = !playing;
            }
        }
    }

    public void render(SpriteBatch batch) {
        if (playing) {
            batch.draw(pause, bounds.x, bounds.y, bounds.width, bounds.height);
        } else {
            batch.draw(play, bounds.x, bounds.y, bounds.width, bounds.height);
        }
    }

    public boolean isPlaying() {
        return playing;
    }

    public void dispose() {
        play.dispose();
        pause.dispose();
    }
}
