package anhtuannguyen.oop.Level;

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

public class Level12 {
    private static final float WORLD_H = Screen.WORLD_H;
    private static final float WORLD_W = Screen.WORLD_W;
    private Texture background;
    List<Ball> ball = new ArrayList<>();
    Bar bar;
    Block block; //
    Block ironblock; // block sat
        Block movingBlock; // khối di chuyển ngang
    Score score = new Score();
    boolean playing = true;
    private  boolean win;
    private boolean end = false;
    Pause play_pause;

    Life life;
    private static int[][] map = { // Bản đồ tĩnh: 1 = có khối, 0 = không
    { 0,0,0,0,0,0,0,0,0,2,0,0,0,0,0,0,0,0,0,0 },
    { 0,0,0,0,0,0,0,0,2,2,2,0,0,0,0,0,0,0,0,0 },
    { 0,0,0,0,0,0,0,3,0,3,0,3,0,0,0,0,0,0,0,0 },
    { 0,0,0,0,0,0,1,1,1,1,1,1,1,0,0,0,0,0,0,0 },
    { 0,0,0,0,0,1,1,1,1,1,1,1,1,1,0,0,0,0,0,0 },
    { 0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0 },
    { 0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0 },
    { 0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0 },
    { 0,2,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,2,0,0 },
    { 2,2,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,2,2,0 },
    { 0,2,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,2,0,0 },
    { 0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0 },
    { 0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0 },
    { 0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0 },
    { 0,0,0,0,0,1,1,1,1,1,1,1,1,1,0,0,0,0,0,0 },
    { 0,0,0,0,0,0,1,1,1,1,1,1,1,0,0,0,0,0,0,0 },
    { 0,0,0,0,0,0,0,3,0,3,0,3,0,0,0,0,0,0,0,0 },
    { 0,0,0,0,0,0,0,0,2,2,2,0,0,0,0,0,0,0,0,0 },
    { 0,0,0,0,0,0,0,0,0,2,0,0,0,0,0,0,0,0,0,0 },
    { 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 }
    //  { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
    // { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
    // { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
    // { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
    // { 1, 1, 1, 1, 0, 1, 1, 1, 1, 1 },
    // { 1, 1, 1, 1, 0, 1, 1, 1, 1, 1 },
    // { 1, 1, 1, 1, 0, 1, 1, 1, 1, 1 },
    // { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
    // { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
    // { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
    // { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
    // { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
    };
    static int ROW = map.length;
    static int COL = map[0].length;
    public Level12(Pause _play_pause)
    {
        play_pause = _play_pause;
    }

    public int getScore() {
    return block.getScore();
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
        bar = new Bar(WORLD_W / 2 - 150, 200, 300, 50, new Texture("Bar_level12.png"));

        Ball b = new Ball(bar, new Texture("Ball_level12.png"));
        b.started = false;
        ball.add(b);
        
        block = new Block(0, 0, ball, ROW, COL, map, (int) WORLD_W/COL, (int) WORLD_H/(2*ROW), new Texture("Block_level12.png"));
         ironblock = new Block(0, 0, ball, ROW, COL, map, (int) WORLD_W/COL, (int) WORLD_H/(2*ROW), new Texture("iron_block_lv12.png"));

        block.initializeBlocks(1, new Texture("Block_level12.png"));
        ironblock.initializeBlocks(2, new Texture("iron_block_lv12.png"));

        movingBlock = new Block(0, 0, ball, ROW, COL, map, (int) WORLD_W/COL, (int) WORLD_H/(2*ROW), new Texture("Block_level12.png"));
        movingBlock.initializeBlocks(3, new Texture("Block_level12.png"));

        background = new Texture("Background_level12.png");
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
            if(life.die()== true) {
                Ball b = new Ball(bar, new Texture("Ball_level12.png"));
                b.started = false;
                ball.add(b);
            }
            else {
                // Gdx.app.exit();
            end = true;
                win = false;
            }
        }

        for (Ball b : new ArrayList<>(ball)) {           // chụp snapshot
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
        score.setScore(block.getScore());
        score.render(batch);
        bar.render(batch);
        block.renderBlocks(batch);
        ironblock.renderBlocks(batch);
        movingBlock.renderBlocks(batch);
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
        // life.dispose();
        ball.clear();
    }
}
