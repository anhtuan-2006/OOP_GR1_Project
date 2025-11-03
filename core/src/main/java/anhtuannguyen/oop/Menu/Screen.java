package anhtuannguyen.oop.Menu;

import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Lớp Screen là lớp cơ sở dùng để chia sẻ thông tin chung giữa các màn hình trong game.
 * Bao gồm thông tin về kích thước thế giới, viewport, trạng thái trò chơi và các thành phần như InGame, Pause, SelectMap, Result.
 */
public class Screen {

    /** Chiều rộng của thế giới game (theo đơn vị pixel) */
    public static final float WORLD_W = 1280f;

    /** Chiều cao của thế giới game (theo đơn vị pixel) */
    public static final float WORLD_H = 2000f;

    /** Viewport hiện tại dùng để hiển thị nội dung game */
    protected Viewport viewport;

    /** Tham chiếu đến màn chơi hiện tại */
    protected InGame ingame;

    /** Tham chiếu đến màn tạm dừng */
    protected Pause pause;

    /** Trạng thái hiện tại của trò chơi */
    protected GameState state;

    /** Tham chiếu đến màn kết quả */
    protected Result result;

    /** Tham chiếu đến màn chọn bản đồ */
    protected SelectMap selectmap;

    /**
     * Thiết lập trạng thái trò chơi hiện tại.
     * @param state Trạng thái mới cần thiết lập.
     */
    public void setState(GameState state) {
        this.state = state;
    }

    /**
     * Lấy trạng thái trò chơi hiện tại.
     * @return Trạng thái hiện tại.
     */
    public GameState getState() {
        return state;
    }

    /**
     * Thiết lập tham chiếu đến màn chơi InGame.
     * @param ingame Đối tượng InGame.
     */
    public void setIngame(InGame ingame) {
        this.ingame = ingame;
    }

    /**
     * Lấy tham chiếu đến màn chơi InGame.
     * @return Đối tượng InGame.
     */
    public InGame getIngame() {
        return ingame;
    }

    /**
     * Thiết lập tham chiếu đến màn chọn bản đồ.
     * @param selectmap Đối tượng SelectMap.
     */
    public void setSelectMap(SelectMap selectmap) {
        this.selectmap = selectmap;
    }

    /**
     * Lấy tham chiếu đến màn chọn bản đồ.
     * @return Đối tượng SelectMap.
     */
    public SelectMap getSelectMap() {
        return selectmap;
    }

    /**
     * Lấy tham chiếu đến màn tạm dừng.
     * @return Đối tượng Pause.
     */
    public Pause getPause() {
        return pause;
    }

    /**
     * Thiết lập tham chiếu đến màn tạm dừng.
     * @param _pause Đối tượng Pause.
     */
    public void setPause(Pause _pause) {
        this.pause = _pause;
    }

    /**
     * Lấy tham chiếu đến màn kết quả.
     * @return Đối tượng Result.
     */
    public Result getResult() {
        return result;
    }

    /**
     * Thiết lập tham chiếu đến màn kết quả.
     * @param result Đối tượng Result.
     */
    public void setResult(Result result) {
        this.result = result;
    }
}