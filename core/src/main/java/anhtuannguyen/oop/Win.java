package anhtuannguyen.oop;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Win {
    private static final float WORLD_W = Screen.WORLD_W;
    private static final float WORLD_H = Screen.WORLD_H;

    private Viewport viewport;

    private Texture background = new Texture("Menubackground.jpg");
    private Texture restart = new Texture("restart_button.png");
    private Texture back = new Texture("resume_button.png");
}
