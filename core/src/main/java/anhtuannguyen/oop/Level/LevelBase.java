package anhtuannguyen.oop.Level;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import anhtuannguyen.oop.Menu.Life;
import anhtuannguyen.oop.Menu.Pause;
import anhtuannguyen.oop.Menu.Score;
import anhtuannguyen.oop.Object.Ball;
import anhtuannguyen.oop.Object.Bar;
import anhtuannguyen.oop.Object.Block;

/**
 * Lớp cơ sở cho tất cả các màn chơi (Level1, Level2, ...).
 */
public abstract class LevelBase {
    protected Texture background;
    protected Texture ballTexture;
    protected Texture blockTexture;
    protected Texture barTexture;
    protected Texture ironblockTexture;
    protected List<Ball> ball = new ArrayList<>();
    protected Bar bar;
    protected Block block, ironblock, movingBlock;
    protected Score score = new Score();
    protected Life life;
    protected Pause pause;
    protected boolean playing = true;
    protected boolean win = false;
    protected boolean end = false;

    public LevelBase(Pause _pause) {
        this.pause = _pause;
    }

    // Các hàm chung mà Level cụ thể có thể override
    public abstract void create();
    public abstract void render(SpriteBatch batch);
    public abstract int getMapNumber();

    /** Cập nhật logic gameplay (chung cho mọi màn) */
    protected void updateGameplay() {
        score.setScore(block.getScore());

        if (playing != pause.isPlaying()) {
            playing = !playing;
            bar.isPlaying();
            for (Ball b : ball) b.isPlaying();
        }

        // Bóng chết
        int i = 0;
        while (i < ball.size()) {
            if (!ball.get(i).alive) ball.remove(i);
            else {
                ball.get(i).Move();
                i++;
            }
        }

        // Không còn bóng → xử lý mạng sống
        if (ball.isEmpty()) {
            if (life != null && life.die()) {
                Ball b = new Ball(bar, new Texture("Ball_Level" + getMapNumber() + ".png"));
                b.started = false;
                ball.add(b);
            } else {
                end = true;
                win = false;
            }
        }

        // Kiểm tra va chạm
        for (Ball b : new ArrayList<>(ball)) {
            if (b.alive) {
                block.checkAndHandleCollisions((float)b.getx(), (float)b.gety(), b.getRADIUS(), b);
                ironblock.checkAndHandleCollisions((float)b.getx(),(float) b.gety(), b.getRADIUS(), b);
                movingBlock.checkAndHandleCollisions((float)b.getx(),(float) b.gety(), b.getRADIUS(), b);
            }
        }
    }

    /** Vẽ toàn bộ đối tượng lên màn hình */
    protected void renderAll(SpriteBatch batch) {
        for (Ball b : ball)
            if (b.alive) b.render(batch);
        bar.render(batch);
        block.renderBlocks(batch);
        ironblock.renderBlocks(batch);
        movingBlock.renderBlocks(batch);
        score.render(batch);
        if (life != null) life.render(batch);
    }

    public void setLife(int _life) { life = new Life(_life); }
    public boolean getend() { return end || !block.checkBlockAlive(); }
    public void setend(boolean e) { end = e; }
    public boolean getresult() { return block.getwin(); }
    public int getScore() { return block.getScore(); }

    public void dispose() {
        if (bar != null) bar.dispose();
        if (block != null) block.dispose();
        if (ironblock != null) ironblock.dispose();
        if (movingBlock != null) movingBlock.dispose();
        ball.clear();
    }
}
