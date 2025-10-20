package anhtuannguyen.oop;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Level3 {
    private static final float WORLD_H = Screen.WORLD_H;
    private static final float WORLD_W = Screen.WORLD_W;
    private Texture background;
    List<Ball> ball = new ArrayList<>();
    Bar bar;
    Block block; //
    Block ironblock; // block sat
    Score score = new Score();
    boolean playing = true;

    Play_Pause play_pause;

    private static int[][] map = { // Bản đồ tĩnh: 1 = có khối, 0 = không
            { 0, 0, 2, 2, 2, 2, 2, 2, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 1, 0, 1, 1, 0, 1, 1, 0, 1, 1 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 1, 1, 0, 1, 1, 0, 1, 1, 0, 1 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 1, 0, 1, 1, 0, 1, 1, 0, 1, 1 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 1, 1, 0, 1, 1, 0, 1, 1, 0, 1 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 1, 0, 1, 1, 0, 1, 1, 0, 1, 1 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
    };
    static int ROW = map.length;
    static int COL = map[0].length;

    Level3(Play_Pause _play_pause) {
        play_pause = _play_pause;
    }

    public void create() {
        bar = new Bar(WORLD_W / 2 - 150, 200, 300, 50, new Texture("bar_Level3.png"));

        Ball b = new Ball(bar, new Texture("ball_Level3.png"));
        b.started = false;
        ball.add(b);
        
        block = new Block(0, 0, ball, 10, 12, map, (int) WORLD_W/12, 64, new Texture("block_Level3.png"));
        ironblock = new Block(0, 0, ball, 10, 12, map, (int) WORLD_W/12, 64, new Texture("iron_block_lv3.png"));

        block.initializeBlocks(1, new Texture("block_Level3.png"));
        ironblock.initializeBlocks(2, new Texture("iron_block_lv3.png"));

        background = new Texture("background_Level3.png");
        if (background == null || bar == null || ball == null || block == null) {
            System.out.println("Failed to load texture!");
        }
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
        score.setScore(block.getScore());
        score.render(batch);
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