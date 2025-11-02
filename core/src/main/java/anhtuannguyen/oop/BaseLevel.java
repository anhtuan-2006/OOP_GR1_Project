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

/**
 * Lớp trừu tượng đại diện cho một level cơ bản trong trò chơi.
 * <p>
 * Các lớp con sẽ kế thừa và định nghĩa chi tiết riêng (bản đồ, texture, block...).
 * </p>
 */
public abstract class BaseLevel {
    /** Chiều cao thế giới game (theo đơn vị logic). */
    protected static final float WORLD_H = Screen.WORLD_H;
    /** Chiều rộng thế giới game (theo đơn vị logic). */
    protected static final float WORLD_W = Screen.WORLD_W;

    /** Hình nền của level. */
    protected Texture background;
    /** Danh sách các quả bóng đang tồn tại. */
    protected List<Ball> balls = new ArrayList<>();
    /** Thanh bar điều khiển bóng. */
    protected Bar bar;
    /** Các block thường. */
    protected Block block; 
    /** Các block sắt (khó phá hơn). */
    protected Block ironblock; 
    /** Các block di chuyển. */
    protected Block movingBlock; 
    /** Điểm số của người chơi. */
    protected Score score = new Score();
    /** Trạng thái đang chơi hay tạm dừng. */
    protected boolean playing = true;
    /** Đối tượng quản lý trạng thái tạm dừng. */
    protected Pause play_pause;
    /** Trạng thái thắng level. */
    protected boolean win;
    /** Trạng thái kết thúc level. */
    protected boolean end = false;
    /** Mạng sống còn lại. */
    protected Life life;

    /** Ma trận bản đồ khối của level. */
    protected int[][] map;

    /**
     * Hàm khởi tạo cơ bản.
     * @param _play_pause Đối tượng quản lý tạm dừng.
     */
    public BaseLevel(Pause _play_pause) {
        this.play_pause = _play_pause;
    }

    // ---- Các phương thức trừu tượng ----
    /** Load bản đồ (ma trận khối) riêng cho từng level. */
    protected abstract int[][] loadMap();
    /** Load hình ảnh, texture riêng cho level. */
    protected abstract void loadTextures();
    /** Khởi tạo, sắp xếp các block riêng cho level. */
    protected abstract void setupBlocks();
    /** Trả về kết quả thắng/thua của level (được override cụ thể). */
    public abstract boolean getresult();

    // ---- Phần chung ----

    /**
     * Thiết lập số mạng ban đầu.
     * @param _life số mạng của người chơi.
     */
    public void setLife(int _life) {
        life = new Life(_life);
    }

    /**
     * Lấy điểm số hiện tại.
     * @return điểm số của người chơi.
     */
    public int getScore() {
        return block.getScore();
    }

    /**
     * Kiểm tra level đã kết thúc chưa.
     * @return true nếu kết thúc.
     */
    public boolean isEnd() {
        return end || !block.checkBlockAlive();
    }

    /**
     * Thiết lập trạng thái kết thúc.
     * @param end trạng thái kết thúc.
     */
    public void setEnd(boolean end) {
        this.end = end;
    }

    /**
     * Kiểm tra người chơi có thắng không.
     * @return true nếu thắng.
     */
    public boolean isWin() {
        return block.getwin();
    }

    /**
     * Tạo level: load map, texture, và setup block.
     */
    public void create() {
        map = loadMap();
        loadTextures();
        setupBlocks();
    }

    /**
     * Hàm vẽ và cập nhật logic mỗi khung hình.
     * @param batch SpriteBatch để vẽ đối tượng.
     */
    public void render(SpriteBatch batch) {
        // Cập nhật điểm số hiển thị
        score.setScore(block.getScore());

        // Kiểm tra trạng thái tạm dừng / tiếp tục
        if (playing != play_pause.isPlaying()) {
            playing = !playing;
            bar.isPlaying();
            for (Ball b : balls)
                b.isPlaying();
        }

        // Cập nhật chuyển động của bóng
        int i = 0;
        while (i < balls.size()) {
            if (!balls.get(i).alive) {
                balls.remove(i);
            } else {
                balls.get(i).Move();
                i++;
            }
        }

        // Nếu không còn bóng => mất mạng hoặc thua
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

        // Kiểm tra va chạm giữa bóng và các loại block
        for (Ball b : new ArrayList<>(balls)) {
            if (b.alive) {
                block.checkAndHandleCollisions((float) b.getx(), (float) b.gety(), (float) b.getRADIUS(), b);
                ironblock.checkAndHandleCollisions((float) b.getx(), (float) b.gety(), (float) b.getRADIUS(), b);
                movingBlock.checkAndHandleCollisions((float) b.getx(), (float) b.gety(), (float) b.getRADIUS(), b);
            }
        }

        // Vẽ hình nền
        batch.draw(background, 0, 0, WORLD_W, WORLD_H);

        // Vẽ các quả bóng còn sống
        for (Ball b : balls)
            if (b.alive)
                b.render(batch);

        // Vẽ thanh bar, block, điểm và mạng
        bar.render(batch);
        block.renderBlocks(batch);
        ironblock.renderBlocks(batch);
        movingBlock.renderBlocks(batch);
        score.render(batch);
        life.render(batch);
    }

    /**
     * Trả về kết quả level (thắng nếu block.getwin() hoặc win == true).
     * @return true nếu thắng.
     */
    public boolean getResult() {
        return block.getwin() || win;
    }

    /**
     * Giải phóng tài nguyên khi thoát level.
     */
    public void dispose() {
        bar.dispose();
        block.dispose();
        ironblock.dispose();
        movingBlock.dispose();
        balls.clear();
    }
}
