package anhtuannguyen.oop;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Level3 {
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
     private  boolean win;
    private boolean end = false;

    Life life;

    private static int[][] map = { // Bản đồ tĩnh: 1 = có khối, 0 = không
            { 0, 0, 2, 2, 2, 2, 2, 2, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 3, 0, 1, 1, 0, 1, 1, 0, 1, 1 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 1, 1, 0, 1, 1, 0, 1, 1, 0, 3 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 1, 0, 1, 1, 0, 1, 1, 0, 1, 1 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 1, 1, 0, 1, 1, 0, 1, 1, 0, 3 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 3, 0, 1, 1, 0, 1, 1, 0, 1, 1 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
    };

    Level3(Pause _play_pause) {
        play_pause = _play_pause;
    }

    public void setLife(int _life) {
        life = new Life(_life);
    }
        public Boolean getend() {
        return end || !block.checkBlockAlive();
    }

    public void setend(Boolean end) {
        this.end = end;
    }

    public Boolean getwin() {
        return win;
    }

    public void create() {
        bar = new Bar(WORLD_W / 2 - 150, 200, 300, 30, new Texture("bar_level3.png"));

        Ball b = new Ball(bar, new Texture("ball_level3.png"));
        b.started = false;
        ball.add(b);

        block = new Block(0, 0, ball, 12, 10, map, 100, 64, new Texture("block_Level3.png"));
        ironblock = new Block(0, 0, ball, 12, 10, map, 100, 64, new Texture("iron_block_lv3.png"));

        block.initializeBlocks(1, new Texture("block_Level3.png"));
        ironblock.initializeBlocks(2, new Texture("iron_block_lv3.png"));

         movingBlock = new Block(0, 0, ball, 12, 10, map, 100, 64, new Texture("block_level3.png"));
        movingBlock.initializeBlocks(3, new Texture("block_level3.png"));

        background = new Texture("background_level3.png");
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
                Ball b = new Ball(bar, new Texture("ball_level3.png"));
                b.started = false;
                ball.add(b);
            } else
                // Gdx.app.exit();
                end = true;
                win = false;
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
     public boolean getresult() {
        if (block.getwin()) {
            return true;
        }        
        return win;
    }
    public void dispose() {
        bar.dispose();
        block.dispose();
        ironblock.dispose();
        movingBlock.dispose();
        ball.clear();
        // life.dispose();
    }
}