package anhtuannguyen.oop.Menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;

import anhtuannguyen.oop.Level.Level1;
import anhtuannguyen.oop.Level.*;
import anhtuannguyen.oop.Level.LevelBase;

/**
 * Lớp InGame quản lý trạng thái khi người chơi đang trong màn chơi.
 * Bao gồm xử lý tạo màn, cập nhật trạng thái, hiển thị điểm số, và chuyển đổi giữa các trạng thái như pause, result.
 */
public class InGame extends Screen {

    /** Mảng chứa các màn chơi từ Level1 đến Level12 */
    private LevelBase[] levels = new LevelBase[12];

    /** Số mạng hiện tại của người chơi */
    private int life;

    /**
     * Constructor khởi tạo InGame với viewport hiện tại.
     * @param _v Viewport được sử dụng để hiển thị.
     */
    public InGame(Viewport _v) {
        this.viewport = _v;
    }

    /**
     * Kiểm tra kết quả thắng của màn chơi hiện tại.
     * @return true nếu người chơi thắng, false nếu chưa hoặc không có màn chơi.
     */
    public boolean getresult() {
        if (selectmap == null) return false;
        int map = selectmap.getMap();
        return (levels[map] != null) && levels[map].getresult();
    }

    /**
     * Thiết lập số mạng cho tất cả các màn chơi.
     * @param _life Số mạng ban đầu.
     */
    public void setLife(int _life) {
        life = _life;
        for (LevelBase lvl : levels)
            if (lvl != null) lvl.setLife(_life);
    }

    /**
     * Khởi tạo tất cả các màn chơi và đối tượng Pause.
     */
    public void create() {
        pause = new Pause(viewport);
        pause.create();

        levels[0] = new Level1(pause);
        levels[1] = new Level2(pause);
        levels[2] = new Level3(pause);
        levels[3] = new Level4(pause);
        levels[4] = new Level5(pause);
        levels[5] = new Level6(pause);
        levels[6] = new Level7(pause);
        levels[7] = new Level8(pause);
        levels[8] = new Level9(pause);
        levels[9] = new Level10(pause);
        levels[10] = new Level11(pause);
        levels[11] = new Level12(pause);

        for (LevelBase lvl : levels)
            if (lvl != null) lvl.create();
    }

    /**
     * Cập nhật trạng thái trò chơi, bao gồm xử lý input và kiểm tra kết thúc màn.
     */
    public void update() {
        handleInput();
        toResult();
    }

    /**
     * Xử lý phím ESC để chuyển đổi giữa trạng thái PAUSE và IN_GAME.
     */
    private void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            state = (state == GameState.PAUSE) ? GameState.IN_GAME : GameState.PAUSE;
        }
    }

    /**
     * Kiểm tra nếu màn chơi kết thúc thì chuyển sang trạng thái RESULT.
     */
    private void toResult() {
        for (LevelBase lvl : levels) {
            if (lvl != null && lvl.getend()) {
                state = GameState.RESULT;
                if (selectmap != null && selectmap.getResult() != null)
                    selectmap.getResult().setCurrentScore(getScore());
                break;
            }
        }
    }

    /**
     * Vẽ màn chơi hiện tại lên màn hình.
     * @param batch SpriteBatch để vẽ.
     */
    public void render(SpriteBatch batch) {
        if (selectmap == null) return;
        int map = selectmap.getMap();
        if (levels[map] != null) levels[map].render(batch);
    }

    /**
     * Lấy điểm số hiện tại của màn chơi đang chơi.
     * @return Điểm số hiện tại.
     */
    public int getScore() {
        if (selectmap == null) return 0;
        int map = selectmap.getMap();
        return (levels[map] != null) ? levels[map].getScore() : 0;
    }

    /**
     * Reset lại màn chơi hiện tại, bao gồm khởi tạo lại và thiết lập lại mạng sống.
     */
    public void reset() {
        if (selectmap == null) return;
        int map = selectmap.getMap();
        if (map <= -1) {
            map = 0;
        }
        setLife(life);
        if (levels[map] != null) {
            levels[map].setend(false);
            levels[map].dispose();
            levels[map].create();
        }
        if (pause != null) pause.setState(GameState.IN_GAME);
    }

    /**
     * Giải phóng tài nguyên của tất cả các màn chơi và đối tượng Pause.
     */
    public void dispose() {
        if (pause != null) pause.dispose();
        for (LevelBase lvl : levels)
            if (lvl != null) lvl.dispose();
    }
}