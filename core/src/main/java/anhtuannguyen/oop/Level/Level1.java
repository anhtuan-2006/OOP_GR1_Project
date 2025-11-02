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
 * Lớp đại diện cho màn chơi Level 1.
 * Quản lý các đối tượng như bóng, thanh chắn, khối thường, khối sắt, khối di chuyển,
 * điểm số, mạng sống và trạng thái chơi.
 */
public class Level1 {
    private static final float WORLD_H = Screen.WORLD_H;
    private static final float WORLD_W = Screen.WORLD_W;

    private Texture background; // Hình nền màn chơi
    List<Ball> ball = new ArrayList<>(); // Danh sách bóng
    Bar bar; // Thanh chắn
    Block block; // Khối thường
    Block ironblock; // Khối sắt
    Block movingBlock; // Khối di chuyển ngang
    Score score = new Score(); // Đối tượng điểm số
    boolean playing = true; // Trạng thái đang chơi
    Pause play_pause; // Đối tượng tạm dừng
    private boolean win; // Trạng thái thắng
    private boolean end = false; // Trạng thái kết thúc màn
    Life life; // Đối tượng mạng sống

    // Bản đồ khối: 1 = khối thường, 2 = khối sắt, 3 = khối di chuyển, 0 = trống
    private static int[][] map = {
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 1, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 1, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },

        // { 1, 1, 0, 2, 2, 0, 1, 1, 0, 1 },
        // { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        // { 1, 0, 1, 1, 0, 2, 2, 0, 1, 1 },
        // { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        // { 1, 1, 0, 2, 2, 0, 1, 1, 0, 1 },
        // { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        // { 1, 0, 1, 1, 0, 2, 2, 0, 1, 1 },
        // { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        // { 1, 1, 0, 2, 2, 0, 1, 1, 0, 1 },
        // { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        // { 1, 0, 1, 1, 0, 2, 2, 0, 1, 1 },
        // { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
    };

    /**
     * Hàm khởi tạo màn chơi Level1.
     * @param _play_pause Đối tượng Pause để kiểm soát trạng thái tạm dừng.
     */
    public Level1(Pause _play_pause) {
        play_pause = _play_pause;
    }

    /**
     * Trả về điểm hiện tại từ khối thường.
     * @return Điểm số hiện tại.
     */
    public int getScore() {
        return block.getScore();
    }

    /**
     * Thiết lập số mạng sống ban đầu.
     * @param _life Số mạng sống.
     */
    public void setLife(int _life) {
        life = new Life(_life);
    }

    /**
     * Kiểm tra màn chơi đã kết thúc chưa.
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
        return block.getwin();
    }

    /**
     * Khởi tạo các đối tượng trong màn chơi.
     */
    public void create() {
        bar = new Bar(WORLD_W / 2 - 150, 200, 300, 30, new Texture("bar_level1.png"));
        Ball b = new Ball(bar, new Texture("ball.png"));
        b.started = false;
        ball.add(b);

        block = new Block(0, 0, ball, 12, 10, map, 100, 64, new Texture("block_level1.png"));
        ironblock = new Block(0, 0, ball, 12, 10, map, 100, 64, new Texture("iron_block_lv1.png"));

        block.initializeBlocks(1, new Texture("block_level1.png"));
        ironblock.initializeBlocks(2, new Texture("iron_block_lv1.png"));

        movingBlock = new Block(0, 0, ball, 12, 10, map, 100, 64, new Texture("block_level1.png"));
        movingBlock.initializeBlocks(3, new Texture("block_level1.png"));

        background = new Texture("background_level1.jpg");
    }

    /**
     * Vẽ toàn bộ màn chơi lên màn hình.
     * @param batch SpriteBatch dùng để vẽ.
     */
    public void render(SpriteBatch batch) {
        score.setScore(block.getScore()); // Cập nhật điểm từ khối thường

        // Kiểm tra trạng thái chơi/tạm dừng
        if (playing != play_pause.isPlaying()) {
            playing = !playing;
            bar.isPlaying();
            for (Ball b : ball)
                b.isPlaying();
        }

        // Xử lý bóng chết
        int i = 0;
        while (i < ball.size()) {
            if (ball.get(i).alive == false) {
                ball.remove(i);
            } else {
                ball.get(i).Move();
                i++;
            }
        }

        // Nếu không còn bóng, kiểm tra mạng sống
        if (ball.size() == 0) {
            if (life.die() == true) {
                Ball b = new Ball(bar, new Texture("ball.png"));
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

        // Vẽ các đối tượng lên màn hình
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

    /**
     * Trả về kết quả thắng/thua của màn chơi.
     * @return true nếu thắng, false nếu thua.
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
        ball.clear();
        ironblock.dispose();
        movingBlock.dispose();
        // life.dispose(); // nếu cần giải phóng mạng sống
        ball.clear();
    }
}