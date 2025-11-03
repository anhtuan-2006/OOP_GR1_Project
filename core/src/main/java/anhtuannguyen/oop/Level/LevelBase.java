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
 * {@code LevelBase} là lớp trừu tượng cơ sở cho tất cả các màn chơi (Level1, Level2, ...).
 * <p>
 * Quản lý các đối tượng chính trong gameplay như: bóng, thanh đỡ, block,
 * điểm, mạng sống và tạm dừng.  
 * Các lớp con sẽ kế thừa và triển khai cụ thể layout, texture và logic riêng.
 * </p>
 */
public abstract class LevelBase {

    /** Hình nền của màn chơi. */
    protected Texture background;
    /** Ảnh của bóng. */
    protected Texture ballTexture;
    /** Ảnh của block thường. */
    protected Texture blockTexture;
    /** Ảnh của thanh đỡ. */
    protected Texture barTexture;
    /** Ảnh của block sắt (không phá được). */
    protected Texture ironblockTexture;

    /** Danh sách các quả bóng hiện có trong màn chơi. */
    protected List<Ball> ball = new ArrayList<>();
    /** Thanh đỡ của người chơi. */
    protected Bar bar;
    /** Các loại block trong màn chơi. */
    protected Block block, ironblock, movingBlock;
    /** Quản lý điểm số. */
    protected Score score = new Score();
    /** Quản lý mạng sống. */
    protected Life life;
    /** Trạng thái tạm dừng. */
    protected Pause pause;

    /** Biến trạng thái: đang chơi hay tạm dừng. */
    protected boolean playing = true;
    /** Biến trạng thái: thắng màn hay chưa. */
    protected boolean win = false;
    /** Biến trạng thái: kết thúc màn chơi. */
    protected boolean end = false;

    /**
     * Khởi tạo màn chơi cơ bản với trạng thái pause được truyền vào.
     *
     * @param _pause đối tượng quản lý tạm dừng
     */
    public LevelBase(Pause _pause) {
        this.pause = _pause;
    }

    /** Khởi tạo tài nguyên, đối tượng cho màn chơi (mỗi Level sẽ cài đặt riêng). */
    public abstract void create();

    /** Vẽ nội dung màn chơi lên màn hình. */
    public abstract void render(SpriteBatch batch);

    /** Lấy số thứ tự của màn (Level1 → 1, Level2 → 2, ...). */
    public abstract int getMapNumber();

    /**
     * Cập nhật logic gameplay (chung cho tất cả các Level).
     * Bao gồm: xử lý bóng, mạng sống, va chạm và pause.
     */
    protected void updateGameplay() {
        // Cập nhật điểm số theo block
        score.setScore(block.getScore());

        // Chuyển đổi trạng thái pause/play
        if (playing != pause.isPlaying()) {
            playing = !playing;
            bar.isPlaying();
            for (Ball b : ball) b.isPlaying();
        }

        // Cập nhật chuyển động và loại bỏ bóng đã chết
        int i = 0;
        while (i < ball.size()) {
            if (!ball.get(i).alive) ball.remove(i);
            else {
                ball.get(i).Move();
                i++;
            }
        }

        // Khi không còn bóng → trừ mạng hoặc kết thúc màn
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

        // Kiểm tra va chạm giữa bóng và các loại block
        for (Ball b : new ArrayList<>(ball)) {
            if (b.alive) {
                block.checkAndHandleCollisions((float)b.getx(), (float)b.gety(), b.getRADIUS(), b);
                ironblock.checkAndHandleCollisions((float)b.getx(), (float)b.gety(), b.getRADIUS(), b);
                movingBlock.checkAndHandleCollisions((float)b.getx(), (float)b.gety(), b.getRADIUS(), b);
            }
        }
    }

    /**
     * Vẽ toàn bộ các đối tượng trong màn chơi: bóng, thanh đỡ, block, điểm và mạng sống.
     */
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

    /**
     * Thiết lập số mạng cho người chơi.
     *
     * @param _life số mạng ban đầu
     */
    public void setLife(int _life) { life = new Life(_life); }

    /**
     * Kiểm tra xem màn chơi đã kết thúc hay chưa.
     *
     * @return {@code true} nếu kết thúc, ngược lại {@code false}
     */
    public boolean getend() { return end || !block.checkBlockAlive(); }

    /**
     * Đặt trạng thái kết thúc cho màn chơi.
     *
     * @param e giá trị trạng thái kết thúc
     */
    public void setend(boolean e) { end = e; }

    /**
     * Kiểm tra kết quả thắng/thua của màn chơi.
     *
     * @return {@code true} nếu thắng, ngược lại {@code false}
     */
    public boolean getresult() { return block.getwin(); }

    /**
     * Lấy điểm số hiện tại.
     *
     * @return điểm số hiện tại
     */
    public int getScore() { return block.getScore(); }

    /**
     * Giải phóng tài nguyên, xóa danh sách bóng và texture của các đối tượng.
     */
    public void dispose() {
        if (bar != null) bar.dispose();
        if (block != null) block.dispose();
        if (ironblock != null) ironblock.dispose();
        if (movingBlock != null) movingBlock.dispose();
        ball.clear();
    }
}
