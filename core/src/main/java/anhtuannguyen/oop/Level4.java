package anhtuannguyen.oop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Level4 {
    private static final float WORLD_H = Screen.WORLD_H;
    private static final float WORLD_W = Screen.WORLD_W;
    private Texture background;
    Ball ball;
    Bar bar;
    Block block;

    boolean playing = true; //

    Play_Pause play_pause;

    private static int[][] map = { // Bản đồ tĩnh: 1 = có khối, 0 = không
            { 1, 1, 0, 1, 1, 0, 1, 1, 0, 1 },
            { 0, 1, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 1, 0, 1, 1, 0, 1, 1, 0, 1, 1 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 1, 1, 0, 1, 1, 0, 1, 1, 0, 1 },
            { 0, 0, 0, 1, 1, 0, 0, 0, 0, 0 },
            { 1, 0, 1, 1, 0, 1, 1, 0, 1, 1 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 1, 1, 0, 1, 1, 0, 1, 1, 0, 1 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 1, 0, 1, 1, 0, 1, 1, 0, 1, 1 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
    };

    Level4(Play_Pause _play_pause)
    {
        play_pause = _play_pause;
    }

    public void create() {
        bar = new Bar(WORLD_W / 2 - 150, 200, 300, 30, new Texture("Bar_Level4.png"));

        ball = new Ball(bar, new Texture("Ball_Level4.png"));

        block = new Block(0, 0, ball, 12, 10, map, (int) WORLD_W/10, 64, new Texture("block_Level4.png"));
        block.initializeBlocks(4, new Texture("block_Level4.png"));

        background = new Texture("Background_Level4.png");
        if (background == null || bar == null || ball == null || block == null) {
            System.out.println("Failed to load texture!");
        }
    }

    public void render(SpriteBatch batch) {

        if(playing != play_pause.isPlaying())
        {
            playing = !playing;
            bar.isPlaying();
            ball.isPlaying();
        }


        if (ball.Move() == false) {
            Gdx.app.exit();
        }

        block.checkAndHandleCollisions((float) ball.getx(), (float) ball.gety(), ball.getRADIUS());
        batch.draw(background, 0, 0, WORLD_W, WORLD_H);
        ball.render(batch);
        bar.render(batch);
        block.renderBlocks(batch);
    }

    public void dispose() {
        bar.dispose();
        block.dispose();
        ball.dispose();
    }
}
