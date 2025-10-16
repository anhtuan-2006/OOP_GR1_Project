package anhtuannguyen.oop;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Level2 {
    private static final float WORLD_H = Screen.WORLD_H;
    private static final float WORLD_W = Screen.WORLD_W;
    private Texture background;
    List<Ball> ball = new ArrayList<>();
    Bar bar;
    Block block; //
    Block ironblock; // block sat

    boolean playing = true;

    Play_Pause play_pause;

    private static int[][] map = { // Bản đồ tĩnh: 1 = có khối, 0 = không
            { 1, 1, 0, 1, 1, 0, 1, 1, 0, 1 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 1, 0, 2, 2, 0, 1, 1, 0, 1, 1 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 1, 1, 0, 1, 1, 0, 2, 2, 0, 1 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 1, 0, 2, 2, 0, 1, 1, 0, 1, 1 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 1, 1, 0, 1, 1, 0, 2, 2, 0, 1 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 1, 0, 2, 2, 0, 1, 1, 0, 1, 1 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
    };

    Level2(Play_Pause _play_pause)
    {
        play_pause = _play_pause;
    }

    public void create() {
        bar = new Bar(WORLD_W / 2 - 150, 200, 300, 30, new Texture("bar_Level2.png"));

        Ball b = new Ball(bar, new Texture("Ball_level2.png"));
        b.started = false;
        ball.add(b);

        block = new Block(0, 0, ball, 12, 10, map, 100, 64, new Texture("block_level2.png"));
        ironblock = new Block(0, 0, ball, 12, 10, map, 100, 64, new Texture("iron_block.png"));

        block.initializeBlocks(1,new Texture("block_level2.png"));
        ironblock.initializeBlocks(2, new Texture("iron_block.png"));
        

        background = new Texture("background_level2.png");
    }

    public void render(SpriteBatch batch) {

        if (playing != play_pause.isPlaying()) {
            playing = !playing;
            bar.isPlaying();
            for (Ball b : ball)
                b.isPlaying();
        }

        int i = 0;
        while (i < ball.size()) {
            if (ball.get(i).alive == false) {
                ball.remove(i);
            } else {
                ball.get(i).Move();
                i++;
            }
        }

        if (ball.size() == 0) {
            Gdx.app.exit();
        }

        for (Ball b : new ArrayList<>(ball)) {           // chụp snapshot
            if (b.alive) {
                block.checkAndHandleCollisions((float) b.getx(), (float) b.gety(), b.getRADIUS(), b);
                ironblock.checkAndHandleCollisions((float) b.getx(), (float) b.gety(), b.getRADIUS(), b);
            }
        }

        batch.draw(background, 0, 0, WORLD_W, WORLD_H);

        for (Ball b : ball)
            if (b.alive == true)
                b.render(batch);

        bar.render(batch);
        block.renderBlocks(batch);
        ironblock.renderBlocks(batch);
    }

    public void dispose() {
        bar.dispose();
        block.dispose();
        ironblock.dispose();
    }
}
