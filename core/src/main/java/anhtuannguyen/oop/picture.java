package anhtuannguyen.oop;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;


public class picture {
    
    static Texture background;
    static Texture ball;

    public static void create_picture() {
        background = new Texture("background.jpg");
        ball = new Texture("ball.png");   
    }

    public static void render_picture(SpriteBatch batch, Texture texture, int u, int v, int x, int y) {
        batch.begin();
        batch.draw(texture, u, v, x, y);
        batch.end();
    }
}
