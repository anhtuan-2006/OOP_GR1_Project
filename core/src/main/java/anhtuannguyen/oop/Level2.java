package anhtuannguyen.oop;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Level2 {
    private static final float WORLD_H = Screen.WORLD_H;
    private static final float WORLD_W = Screen.WORLD_W;
    private Texture background;
    Ball ball;
    Bar bar;
    Block block; //

    boolean playing = true;

    Play_Pause play_pause;
    RandomBlock randomBlock;

    List<Ball> ballList = new ArrayList<>();
    RandomBall randomBall;

    private static int[][] map = { // Bản đồ tĩnh: 1 = có khối, 0 = không
            { 1, 1, 0, 1, 1, 0, 1, 1, 0, 1 },
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

    Level2(Play_Pause _play_pause)
    {
        play_pause = _play_pause;
    }

    public void create() {
        bar = new Bar(WORLD_W / 2 - 150, 200, 300, 30, new Texture("bar_level1.png"));

        ball = new Ball(bar, new Texture("ball.png"));

        block = new Block(0, 0, ball, 12, 10, map, 100, 64, new Texture("block_level2.png"));
        block.initializeBlocks(1,new Texture("block_level2.png"));

        background = new Texture("background_level2.jpg");

        Random rand = new Random();
         randomBlock = new RandomBlock(1280-rand.nextInt(500),2000- rand.nextInt(600), ball, new Texture("block_random.png"));

        
        ballList.add(ball);

       randomBall = new RandomBall(600, 500, ball, ballList,new Texture("block_random.png")); // vị trí khối đặc biệt

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

        for (Ball b : ballList) {
    block.checkAndHandleCollisions((float) b.getx(), (float) b.gety(), b.getRADIUS());
}

        batch.draw(background, 0, 0, WORLD_W, WORLD_H);
        ball.render(batch);
        bar.render(batch);
        block.renderBlocks(batch);
         randomBlock.checkCollision();
    randomBlock.render(batch);


    randomBall.checkCollision();
randomBall.render(batch);

int aliveCount = 0;

for (Ball b : ballList) {
    boolean isAlive = b.Move();
    if (isAlive) {
        aliveCount++;
    }
    b.render(batch);
}

// Nếu không còn bóng nào sống → thoát game
if (aliveCount == 0) {
    Gdx.app.exit();
}
    }

    public void dispose() {
        bar.dispose();
        block.dispose();
        ball.dispose();
    }
}
