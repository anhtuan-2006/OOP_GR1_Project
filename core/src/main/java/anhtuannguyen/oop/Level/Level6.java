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

/**
 * Lớp Level6 đại diện cho màn chơi thứ 6 trong game.
 * Quản lý các đối tượng như bóng, thanh bar, khối block, điểm số, mạng sống và trạng thái chơi.
 */
public class Level6 {
    private static final float WORLD_H = Screen.WORLD_H;
    private static final float WORLD_W = Screen.WORLD_W;

    private Texture background; // Hình nền màn chơi
    List<Ball> ball = new ArrayList<>(); // Danh sách các bóng
    Bar bar; // Thanh điều khiển bóng
    Block block; // Khối thường
    Block ironblock; // Khối sắt
    Block movingBlock; // Khối di chuyển ngang
    Score score = new Score(); // Điểm số
    boolean playing = true; // Trạng thái đang chơi

    Pause play_pause; // Đối tượng kiểm soát tạm dừng
    private boolean win; // Trạng thái thắng
    private boolean end = false; // Trạng thái kết thúc
    Life life; // Mạng sống

    // Bản đồ khối: 1 = khối thường, 2 = khối sắt, 3 = khối di chuyển
    private static int[][] map = {
        { 1, 1, 0, 2, 2, 0, 0, 3, 0, 1 },
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 1, 0, 2, 2, 0, 0, 3, 0, 1, 1 },
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 1, 1, 0, 0, 3, 0, 1, 1, 0, 1 },
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 1, 0, 1, 1, 0, 1, 1, 0, 1, 1 },
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 1, 1, 0, 1, 1, 0, 2, 2, 0, 1 },
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 1, 0, 1, 1, 0, 2, 2, 0, 1, 1 },
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
    };

    static int ROW = map.length; // Số hàng của bản đồ
    static int COL = map[0].length; // Số cột của bản đồ

    /**
     * Constructor khởi tạo màn chơi Level6.
     * @param _play_pause Đối tượng Pause để kiểm soát trạng thái chơi.
     */
    public Level6(Pause _play_pause) {
        play_pause = _play_pause;
    }

    /**
     * Lấy điểm hiện tại của người chơi.
     * @return Điểm số.
     */
    public int getScore() {
        return block.getScore();
    }

    /**
     * Thiết lập số mạng ban đầu cho người chơi.
     * @param _life Số mạng.
     */
    public void setLife(int _life) {
        life = new Life(_life);
    }

    /**
     * Kiểm tra xem màn chơi đã kết thúc chưa.
     * @return true nếu kết thúc hoặc không còn khối sống.
     */
    public Boolean getend() {
        return end || !block.checkBlockAlive();
    }

    /**
     * Thiết lập trạng thái kết thúc màn chơi.
     * @param end true nếu kết thúc.
     */
    public void setend(Boolean end) {
        this.end = end;
    }

    /**
     * Kiểm tra người chơi có thắng không.
     * @return true nếu thắng.
     */
    public Boolean getwin() {
        return win;
    }

    /**
     * Khởi tạo các đối tượng trong màn chơi: thanh bar, bóng, khối, nền.
     */
    public void create() {
        bar = new Bar(WORLD_W / 2 - 150, 200, 300, 50, new Texture("Bar_Level6.png"));

        Ball b = new Ball(bar, new Texture("ball_level6.png"));
        b.started = false;
        ball.add(b);

        block = new Block(0, 0, ball, ROW, COL, map, (int) WORLD_W / COL, (int) WORLD_H / (2 * ROW), new Texture("Block_Level6.png"));
        ironblock = new Block(0, 0, ball, ROW, COL, map, (int) WORLD_W / COL, (int) WORLD_H / (2 * ROW), new Texture("iron_block_lv6.jpg"));

        block.initializeBlocks(1, new Texture("Block_Level6.png"));
        ironblock.initializeBlocks(2, new Texture("iron_block_lv6.jpg"));

        movingBlock = new Block(0, 0, ball, ROW, COL, map, (int) WORLD_W / COL, (int) WORLD_H / (2 * ROW), new Texture("Block_Level6.png"));
        movingBlock.initializeBlocks(3, new Texture("Block_Level6.png"));

        background = new Texture("Background_Level6.jpg");

        // Kiểm tra tài nguyên đã được tải thành công
        if (background == null || bar == null || ball == null || block == null) {
            System.out.println("Failed to load texture!");
        }
    }

    /**
     * Vẽ toàn bộ màn chơi lên màn hình.
     * @param batch SpriteBatch để vẽ các đối tượng.
     */
    public void render(SpriteBatch batch) {
        // Cập nhật trạng thái chơi nếu có thay đổi
        if (playing != play_pause.isPlaying()) {
            playing = !playing;
            bar.isPlaying();
            for (Ball b : ball)
                b.isPlaying();
        }

        // Di chuyển bóng và loại bỏ bóng chết
        int i = 0;
        while (i < ball.size()) {
            if (!ball.get(i).alive) {
                ball.remove(i);
            } else {
                ball.get(i).Move();
                i++;
            }
        }

        // Nếu không còn bóng, kiểm tra mạng sống
        if (ball.size() == 0) {
            if (life.die()) {
                Ball b = new Ball(bar, new Texture("ball_level6.png"));
                b.started = false;
                ball.add(b);
            } else {
                end = true;
                win = false;
            }
        }

        // Kiểm tra va chạm giữa bóng và các khối
        for (Ball b : new ArrayList<>(ball)) {
            if (b.alive) {
                block.checkAndHandleCollisions((float) b.getx(), (float) b.gety(), b.getRADIUS(), b);
                ironblock.checkAndHandleCollisions((float) b.getx(), (float) b.gety(), b.getRADIUS(), b);
                movingBlock.checkAndHandleCollisions((float) b.getx(), (float) b.gety(), b.getRADIUS(), b);
            }
        }

        // Vẽ nền và các đối tượng
        batch.draw(background, 0, 0, WORLD_W, WORLD_H);

        for (Ball b : ball)
            if (b.alive)
                b.render(batch);

        score.setScore(block.getScore());
        score.render(batch);
        bar.render(batch);
        block.renderBlocks(batch);
        ironblock.renderBlocks(batch);
        movingBlock.renderBlocks(batch);
        life.render(batch);
    }

    /**
     * Kiểm tra kết quả thắng của màn chơi.
     * @return true nếu người chơi thắng.
     */
    public boolean getresult() {
        if (block.getwin()) {
            return true;
        }
        return win;
    }

    /**
     * Giải phóng tài nguyên khi thoát màn chơi.
     */
    public void dispose() {
        bar.dispose();
        block.dispose();
        ironblock.dispose();
        movingBlock.dispose();
        // life.dispose(); // Nếu cần giải phóng mạng sống
        ball.clear();
    }
}