package anhtuannguyen.oop.Object;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import anhtuannguyen.oop.Menu.Screen;

public class Block {
    private int blockWidth;
    private int blockHeight;
    private int gridRows;
    private int gridCols;
    private int[][] map;
    private Block[][] blocks;
    private Rectangle rect;
    private boolean alive;
    private Texture texture;
    private List<Ball> ball;
    private List<Function> function = new ArrayList<>();
    private Ball basic;
    private int point = 0;
    private static final float WORLD_W = Screen.WORLD_W;
    private static final float WORLD_H = Screen.WORLD_H;
    private static final int MAP_SIZE = 64;
    private boolean win = false;


    // Constructor chính cho instance Block của level
    public Block(int x, int y, List<Ball> _ball, int ROW, int COL, int[][] _map, int width, int height, Texture _tex) {
        blockWidth = width;
        blockHeight = height;
        gridRows = ROW;
        gridCols = COL;
        map = _map;
        rect = new Rectangle(x, y, blockWidth, blockHeight);
        alive = true;
        texture = _tex;
        ball = _ball;
        basic = new Ball(ball.get(0));
    }


    // Constructor cho các khối riêng lẻ trong lưới
    public Block(int x, int y, int width, int height, Texture _tex) {
        blockWidth = width;
        blockHeight = height;
        rect = new Rectangle(x, y, blockWidth, blockHeight);
        alive = true;
        texture = _tex;
    }

    public boolean getwin() {
        return win;
    }

    public void setwin(Boolean win) {
        this.win = win;
    }

    public void initializeBlocks(int level, Texture _tex) {
        blocks = new Block[MAP_SIZE][MAP_SIZE];
        float startX = (WORLD_W - gridCols * blockWidth) / 2;
        float startY = WORLD_H - gridRows * blockHeight - 200;
        for (int row = 0; row < map.length; row++) {
            for (int col = 0; col < map[row].length; col++) {
                if (map[row][col] == level) {
                    float x = startX + col * blockWidth;
                    float y = startY + row * blockHeight;
                   if (level == 3) {
        blocks[row][col] = new MovingBlock((int) x, (int) y, blockWidth, blockHeight, _tex);
    } else {
        blocks[row][col] = new Block((int) x, (int) y, blockWidth, blockHeight, _tex);
    }

                    // blocks[row][col] = new Block((int) x, (int) y, blockWidth, blockHeight, _tex);

                }
            }
        }
    }

    public void update(int x, Function f) {
        if (x == 0) return;
        if (x == 1) {
            if (!f.ball.alive) return;
            Ball b1 = new Ball(basic);
            Ball b2 = new Ball(basic);
            b1.setPosition((float) f.ball.getx() + 40f, (float) f.ball.gety());
            b2.setPosition((float) f.ball.getx() - 40f, (float) f.ball.gety());
            b1.setAngle((float) (Math.PI / 3));
            b2.setAngle((float) (2 * Math.PI / 3));
            ball.add(b1);
            ball.add(b2);
        } else if (x == 2) {
            if (!f.ball.alive) return;
            f.ball.fire = true;
            f.ball.Time_fire = 500;
        } else if (x == 3) {
            Rectangle barBounds = f.ball.bar.getBounds();
            barBounds.x = barBounds.x - barBounds.width / 4;
            barBounds.width *= 1.5f;
            f.ball.bar.effectTimer = 0;
            if (barBounds.x + barBounds.width > WORLD_W) {
                barBounds.x = WORLD_W - barBounds.width;
            }
        }
             else if (x == 4) {
            if (f.ball.alive == false)
                return;
            if(f.ball.radius < f.ball.originalRadius * 1.5f)f.  ball.radius = f.ball.radius * 1.5f;
            f.ball.effectTimer = 0; // Bắt đầu đếm
        }
        else if(x==5)
        {
            if (!f.ball.alive) return ;
            f.ball.stickyToBar = true; // bật hiệu ứng chờ dính

        }

    }

    public void renderBlocks(SpriteBatch batch) {
        int i = 0;
        while (i < function.size()) {
            if (!function.get(i).alive) {
                function.remove(i);
            } else {
                update(function.get(i).Move(), function.get(i));
                i++;
            }
        }

        for (Function f : function) {
            if (f.alive) f.render(batch);
        }
        float dt = Gdx.graphics.getDeltaTime();

        for (int row = 0; row < map.length; row++) {
            for (int col = 0; col < map[row].length; col++) {
                if (blocks[row][col] != null && blocks[row][col].alive) {
                    if (blocks[row][col] instanceof MovingBlock) {
                        ((MovingBlock) blocks[row][col]).updateMovement(dt);
                    }
                    blocks[row][col].render(batch);

                }
            }
        }
    }
    public boolean checkBlockAlive() {
        for (int row = 0; row < map.length; row++) {
            for (int col = 0; col < map[row].length; col++) {
                if (blocks[row][col] != null && blocks[row][col].alive) {

                    return true;
                }

            }
        }
        win = true; 
        return false;
    }



    private void pullBall(Rectangle bal, Rectangle block, Ball ball , boolean check) {
        if (ball.fire  && !check) return;
        float balLeft = bal.x;
        float balRight = bal.x + bal.width;
        float balBottom = bal.y;
        float balTop = bal.y + bal.height;

        float blkLeft = block.x;
        float blkRight = block.x + block.width;
        float blkBottom = block.y;
        float blkTop = block.y + block.height;

        float overlapX = Math.min(balRight, blkRight) - Math.max(balLeft, blkLeft);
        float overlapY = Math.min(balTop, blkTop) - Math.max(balBottom, blkBottom);

        if (overlapX <= 0 || overlapY <= 0) return;
        
        if (overlapX < overlapY) {
            if (bal.x + bal.width / 2f < block.x + block.width / 2f) {
                bal.x = blkLeft - bal.width;
                ball.Change_Direction(2);
            } else {
                bal.x = blkRight;
                ball.Change_Direction(1);
            }
        } else {
            if (bal.y + bal.height / 2f < block.y + block.height / 2f) {
                bal.y = blkBottom - bal.height;
                ball.Change_Direction(3);
            } else {
                bal.y = blkTop;
                ball.Change_Direction(4);
            }
        }
        ball.setPosition(bal.x + bal.width / 2f, bal.y + bal.height / 2f);
    }

    void fn(Rectangle r, Ball b) {
        Random rand = new Random();
        int x = rand.nextInt(10);
        if (x != 0) return;
        Function f = new Function((float) r.x + r.width / 2, (float) r.y + r.height / 2, b);
        function.add(f);
    }

    public void checkAndHandleCollisions(float ballX, float ballY, float ballRadius, Ball b) {
        Rectangle ballRect = new Rectangle(ballX - ballRadius / 2, ballY - ballRadius / 2, ballRadius, ballRadius);
        for (int row = 0; row < map.length; row++) {
            for (int col = 0; col < map[row].length; col++) {
                Block block = blocks[row][col];
                if (block != null && block.alive && block.rect.overlaps(ballRect)) {
                    if(map[row][col] !=2)
                    {
                    fn(block.rect, b);
                    pullBall(ballRect, block.rect, b, false);
                    block.alive = false;
                    point += 10;
                    blocks[row][col] = null;
                    } else {
                        pullBall(ballRect, block.rect, b,true);
                    }
                }
            }
        }
    }

    
    public int getScore() {
        return point;
    }
    private void render(SpriteBatch batch) {
        if (alive && texture != null) {
            batch.draw(texture, rect.x, rect.y, rect.width, rect.height);
        }
    }

    public void disposeBlocks() {
        for (int row = 0; row < map.length; row++) {
            for (int col = 0; col < map[row].length; col++) {
                if (blocks[row][col] != null) {
                    blocks[row][col].dispose();
                    blocks[row][col] = null;
                }
            }
        }
    }
    public Rectangle getRect() {
    return rect;
}

    public void dispose() {

            texture.dispose();
        
    }
}