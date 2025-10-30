package anhtuannguyen.oop;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Level4 {
    private static final float WORLD_H = Screen.WORLD_H;
    private static final float WORLD_W = Screen.WORLD_W;
    private Texture background;
    List<Ball> ball = new ArrayList<>();
    Bar bar;
    Block block; // block thuong
    Block ironblock; // block sat
    Block movingBlock; // khối di chuyển ngang 

    Score score = new Score();

    boolean playing = true;
    Pause play_pause;

    Life life;

    private static int[][] map = { // Bản đồ tĩnh: 1 = có khối, 0 = không
            { 0, 0, 2, 2, 2, 2, 2, 2, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 1, 0, 1, 1, 0, 1, 1, 0, 1, 1 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 1, 1, 0, 3, 0, 0, 0, 3, 0, 1 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 1, 0, 1, 1, 0, 1, 1, 0, 1, 1 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 1, 1, 0, 3, 0, 0, 0, 3, 0, 1 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 1, 0, 1, 1, 0, 1, 1, 0, 1, 1 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
    };

    Level4(Pause _play_pause) {
        play_pause = _play_pause;
    }

    public void setLife(int _life) {
        life = new Life(_life);
    }

    public void create() {
        bar = new Bar(WORLD_W / 2 - 150, 200, 300, 30, new Texture("Bar_Level4.png"));

        Ball b = new Ball(bar, new Texture("Ball_Level4.png"));
        b.started = false;
        ball.add(b);

        block = new Block(0, 0, ball, 12, 10, map, 100, 64, new Texture("Block_Level4.png"));
        ironblock = new Block(0, 0, ball, 12, 10, map, 100, 64, new Texture("Iron_block_lv4.png"));

        block.initializeBlocks(1, new Texture("Block_Level4.png"));
        ironblock.initializeBlocks(2, new Texture("Iron_block_lv4.png"));

        movingBlock = new Block(0, 0, ball, 12, 10, map, 100, 64, new Texture("Block_Level4.png"));
        movingBlock.initializeBlocks(3, new Texture("Block_Level4.png"));

        background = new Texture("Background_Level4.png");
    }

    public void render(SpriteBatch batch) {

        score.setScore(block.getScore());

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
            if (life.die() == true) {
                Ball b = new Ball(bar, new Texture("Ball_Level4.png"));
                b.started = false;
                ball.add(b);
            } else
                Gdx.app.exit();
        }

        for (Ball b : new ArrayList<>(ball)) { // chụp snapshot
            if (b.alive) {
                block.checkAndHandleCollisions((float) b.getx(), (float) b.gety(), b.getRADIUS(), b);
                ironblock.checkAndHandleCollisions((float) b.getx(), (float) b.gety(), b.getRADIUS(), b);
                movingBlock.checkAndHandleCollisions((float) b.getx(), (float) b.gety(), b.getRADIUS(), b);
            }
        }

        batch.draw(background, 0, 0, WORLD_W, WORLD_H);

        for (Ball b : ball)
            if (b.alive == true)
                b.render(batch);

        bar.render(batch);
        block.renderBlocks(batch);
        ironblock.renderBlocks(batch);
        movingBlock.renderBlocks(batch);
        score.render(batch);
        life.render(batch);
    }

    public void dispose() {
        bar.dispose();
        block.dispose();
        ironblock.dispose();
        movingBlock.dispose(); 
        ball.clear();
        life.dispose();
    }
}