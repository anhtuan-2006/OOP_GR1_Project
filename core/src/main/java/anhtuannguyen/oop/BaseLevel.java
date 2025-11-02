package anhtuannguyen.oop;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import anhtuannguyen.oop.Menu.Life;
import anhtuannguyen.oop.Menu.Pause;
import anhtuannguyen.oop.Menu.Score;
import anhtuannguyen.oop.Menu.Screen;
import anhtuannguyen.oop.Object.Ball;
import anhtuannguyen.oop.Object.Bar;
import anhtuannguyen.oop.Object.Block;

public abstract class BaseLevel {
    protected static final float WORLD_H = Screen.WORLD_H;
    protected static final float WORLD_W = Screen.WORLD_W;

    protected Texture background;
    protected List<Ball> balls = new ArrayList<>();
    protected Bar bar;
    protected Block block; 
    protected Block ironblock; 
    protected Block movingBlock; 
    protected Score score = new Score();
    protected boolean playing = true;
    protected Pause play_pause;
    protected boolean win;
    protected boolean end = false;
    protected Life life;

    protected int[][] map;

    // Constructor
    public BaseLevel(Pause _play_pause) {
        this.play_pause = _play_pause;
    }

    // ---- Các phương thức trừu tượng ----
    protected abstract int[][] loadMap();                // load bản đồ riêng cho mỗi level
    protected abstract void loadTextures();              // load hình ảnh riêng cho level
    protected abstract void setupBlocks();               // setup block riêng
    public abstract boolean getresult();
    // ---- Phần chung ----
    public void setLife(int _life) {
        life = new Life(_life);
    }

    public int getScore() {
        return block.getScore();
    }

    public boolean isEnd() {
        return end || !block.checkBlockAlive();
    }

    public void setEnd(boolean end) {
        this.end = end;
    }

    public boolean isWin() {
        return block.getwin();
    }

    public void create() {
        map = loadMap();
        loadTextures();
        setupBlocks();
    }

    public void render(SpriteBatch batch) {
        score.setScore(block.getScore());

        if (playing != play_pause.isPlaying()) {
            playing = !playing;
            bar.isPlaying();
            for (Ball b : balls)
                b.isPlaying();
        }

        // Cập nhật bóng
        int i = 0;
        while (i < balls.size()) {
            if (!balls.get(i).alive) {
                balls.remove(i);
            } else {
                balls.get(i).Move();
                i++;
            }
        }

        // Nếu không còn bóng
        if (balls.isEmpty()) {
            if (life.die()) {
                Ball b = new Ball(bar, new Texture("ball.png"));
                b.started = false;
                balls.add(b);
            } else {
                end = true;
                win = false;
            }
        }

        // Kiểm tra va chạm
        for (Ball b : new ArrayList<>(balls)) {
            if (b.alive) {
                block.checkAndHandleCollisions((float) b.getx(), (float) b.gety(), (float) b.getRADIUS(), b);
                ironblock.checkAndHandleCollisions((float) b.getx(), (float) b.gety(), (float) b.getRADIUS(), b);
                movingBlock.checkAndHandleCollisions((float) b.getx(), (float) b.gety(), (float) b.getRADIUS(), b);
                }
        }

        // Vẽ
        batch.draw(background, 0, 0, WORLD_W, WORLD_H);

        for (Ball b : balls)
            if (b.alive)
                b.render(batch);

        bar.render(batch);
        block.renderBlocks(batch);
        ironblock.renderBlocks(batch);
        movingBlock.renderBlocks(batch);
        score.render(batch);
        life.render(batch);
    }

    public boolean getResult() {
        return block.getwin() || win;
    }

    public void dispose() {
        bar.dispose();
        block.dispose();
        ironblock.dispose();
        movingBlock.dispose();
        balls.clear();
    }
}
