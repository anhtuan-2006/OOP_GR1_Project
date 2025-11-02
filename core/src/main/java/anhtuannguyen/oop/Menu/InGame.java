package anhtuannguyen.oop.Menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;

import anhtuannguyen.oop.Level.*;

/**
 * Lớp InGame quản lý toàn bộ trạng thái của trò chơi khi đang trong màn chơi.
 * Bao gồm các Level, trạng thái tạm dừng (Pause), chọn map (SelectMap),
 * cập nhật trạng thái (update), và vẽ nội dung (render).
 */
public class InGame {
    private static final float WORLD_W = Screen.WORLD_W;
    private static final float WORLD_H = Screen.WORLD_H;

    // Các level của trò chơi (Level1 -> Level12)
    private Level1 level1;
    private Level2 level2;
    private Level3 level3;
    private Level4 level4;
    private Level5 level5;
    private Level6 level6;
    private Level7 level7;
    private Level8 level8;
    private Level9 level9;
    private Level10 level10;
    private Level11 level11;
    private Level12 level12;

    private int life; // Số mạng hiện tại của người chơi

    private final Viewport viewport; // Viewport để xác định vùng hiển thị game
    private Pause pause; // Màn hình tạm dừng
    private SelectMap selectmap; // Chọn bản đồ
    private GameState state = GameState.IN_GAME; // Trạng thái trò chơi hiện tại
    private boolean win; // Kết quả thắng/thua

    /**
     * Constructor khởi tạo InGame với viewport tương ứng.
     * @param _v Viewport của game.
     */
    public InGame(Viewport _v) {
        this.viewport = _v;
    }

    // ==============================
    // Getter / Setter
    // ==============================

    /** @return đối tượng Pause hiện tại */
    public Pause getPause() {
        return pause;
    }

    /** @return đối tượng SelectMap hiện tại */
    public SelectMap getSelectMap() {
        return selectmap;
    }

    /** @return trạng thái trò chơi hiện tại */
    public GameState getState() {
        return state;
    }

    /**
     * Cập nhật trạng thái của game (IN_GAME, PAUSE, RESULT).
     * @param _state trạng thái mới.
     */
    public void setState(GameState _state) {
        state = _state;
        System.out.println("InGame state updated to: " + state); // Debug log
    }

    /**
     * Gán màn hình tạm dừng cho trò chơi.
     * @param _pause đối tượng Pause.
     */
    public void setPause(Pause _pause) {
        this.pause = _pause;
    }

    /**
     * Gán SelectMap cho trò chơi.
     * @param _selectmap đối tượng SelectMap.
     */
    public void setSelectMap(SelectMap _selectmap) {
        this.selectmap = _selectmap;
    }

    /**
     * Lấy kết quả thắng/thua của Level hiện tại.
     * @return true nếu người chơi thắng, false nếu chưa hoặc thua.
     */
    public boolean getresult() {
        if (selectmap == null) return false;
        int map = selectmap.getMap();
        if (map == 0 && level1 != null) return level1.getresult();
        if (map == 1 && level2 != null) return level2.getresult();
        if (map == 2 && level3 != null) return level3.getresult();
        if (map == 3 && level4 != null) return level4.getresult();
        if (map == 4 && level5 != null) return level5.getresult();
        if (map == 5 && level6 != null) return level6.getresult();
        if (map == 6 && level7 != null) return level7.getresult();
        if (map == 7 && level8 != null) return level8.getresult();
        if (map == 8 && level9 != null) return level9.getresult();
        if (map == 9 && level10 != null) return level10.getresult();
        if (map == 10 && level11 != null) return level11.getresult();
        if (map == 11 && level12 != null) return level12.getresult();
        return false;
    }

    /** Gán kết quả thắng/thua. */
    public void setresult(boolean win) {
        this.win = win;
    }

    /**
     * Cập nhật số mạng hiện tại và đồng bộ đến tất cả các Level.
     * @param _life số mạng.
     */
    public void setLife(int _life) {
        life = _life;
        if (level1 != null) level1.setLife(_life);
        if (level2 != null) level2.setLife(_life);
        if (level3 != null) level3.setLife(_life);
        if (level4 != null) level4.setLife(_life);
        if (level5 != null) level5.setLife(_life);
        if (level6 != null) level6.setLife(_life);
        if (level7 != null) level7.setLife(_life);
        if (level8 != null) level8.setLife(_life);
        if (level9 != null) level9.setLife(_life);
        if (level10 != null) level10.setLife(_life);
        if (level11 != null) level11.setLife(_life);
        if (level12 != null) level12.setLife(_life);
    }

    /**
     * Khởi tạo tất cả các Level và màn hình Pause.
     */
    public void create() {
        pause = new Pause(viewport);
        pause.create();

        level1 = new Level1(pause);
        level1.create();

        level2 = new Level2(pause);
        level2.create();

        level3 = new Level3(pause);
        level3.create();

        level4 = new Level4(pause);
        level4.create();

        level5 = new Level5(pause);
        level5.create();

        level6 = new Level6(pause);
        level6.create();

        level7 = new Level7(pause);
        level7.create();

        level8 = new Level8(pause);
        level8.create();

        level9 = new Level9(pause);
        level9.create();

        level10 = new Level10(pause);
        level10.create();

        level11 = new Level11(pause);
        level11.create();

        level12 = new Level12(pause);
        level12.create();
    }

    /**
     * Hàm cập nhật trạng thái trò chơi mỗi frame.
     * Gọi xử lý input và kiểm tra chuyển sang màn hình kết quả.
     */
    public void update() {
        handleInput(); // Xử lý phím ESC để pause/resume
        toResult();    // Kiểm tra nếu level kết thúc
    }

    /**
     * Xử lý đầu vào từ bàn phím, đặc biệt phím ESCAPE để pause hoặc resume game.
     */
    private void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            if (state == GameState.PAUSE) {
                state = GameState.IN_GAME;
            } else if (state == GameState.IN_GAME) {
                state = GameState.PAUSE;
            }
        }
    }

    /**
     * Chuyển sang trạng thái RESULT khi level kết thúc.
     * Đồng thời cập nhật điểm hiện tại vào kết quả.
     */
    private void toResult() {
        if ((level1 != null && level1.getend())
        || (level2 != null && level2.getend())
        || (level3 != null && level3.getend())
        || (level4 != null && level4.getend())
        || (level5 != null && level5.getend())
        || (level6 != null && level6.getend())
        || (level7 != null && level7.getend())
        || (level8 != null && level8.getend())
        || (level9 != null && level9.getend())
        || (level10 != null && level10.getend())
        || (level11 != null && level11.getend())
        || (level12 != null && level12.getend())
        )
        {
            state = GameState.RESULT;

            // chống NPE khi selectmap hoặc result chưa được set
            if (selectmap != null && selectmap.getResult() != null) {
                selectmap.getResult().setCurrentScore(getScore());
            }
        }
    }

    /**
     * Hàm vẽ nội dung màn chơi tương ứng với map đang chọn.
     * @param batch SpriteBatch dùng để vẽ.
     */
    public void render(SpriteBatch batch) {
        int map_number = (selectmap != null) ? selectmap.getMap() : 0;

        if (map_number == 0) {
            if (level1 != null) level1.render(batch);
        } else if (map_number == 1) {
            if (level2 != null) level2.render(batch);
        } else if (map_number == 2) {
            if (level3 != null) level3.render(batch);
        } else if (map_number == 3) {
            if (level4 != null) level4.render(batch);
        } else if (map_number == 4) {
            if (level5 != null) level5.render(batch);
        } else if (map_number == 5) {
            if (level6 != null) level6.render(batch);
        } else if (map_number == 6) {
            if (level7 != null) level7.render(batch);
        } else if (map_number == 7) {
            if (level8 != null) level8.render(batch);
        } else if (map_number == 8) {
            if (level9 != null) level9.render(batch);
        } else if (map_number == 9) {
            if (level10 != null) level10.render(batch);
        } else if (map_number == 10) {
            if (level11 != null) level11.render(batch);
        } else if (map_number == 11) {
            if (level12 != null) level12.render(batch);
        }
    }

    /**
     * Lấy điểm số hiện tại của Level đang chơi.
     * @return điểm số của Level tương ứng.
     */
    public int getScore() {
        if (selectmap == null) return 0;
        int map = selectmap.getMap();
        if (map == 0 && level1 != null) return level1.getScore();
        if (map == 1 && level2 != null) return level2.getScore();
        if (map == 2 && level3 != null) return level3.getScore();
        if (map == 3 && level4 != null) return level4.getScore();
        if (map == 4 && level5 != null) return level5.getScore();
        if (map == 5 && level6 != null) return level6.getScore();
        if (map == 6 && level7 != null) return level7.getScore();
        if (map == 7 && level8 != null) return level8.getScore();
        if (map == 8 && level9 != null) return level9.getScore();
        if (map == 9 && level10 != null) return level10.getScore();
        if (map == 10 && level11 != null) return level11.getScore();
        if (map == 11 && level12 != null) return level12.getScore();
        return 0;
    }

    /**
     * Reset lại trạng thái của Level hiện tại.
     * Thiết lập lại mạng, khởi tạo lại Level và đưa Pause về trạng thái IN_GAME.
     */
    public void reset() {
        setLife(life);

        int map = (selectmap != null) ? selectmap.getMap() : 0;

        if (map == 0 && level1 != null) {
            level1.setend(false);
            level1.dispose();
            level1.create();
        }
        if (map == 1 && level2 != null) {
            level2.setend(false);
            level2.dispose();
            level2.create();
        }
        if (map == 2 && level3 != null) {
            level3.setend(false);
            level3.dispose();
            level3.create();
        }
        if (map == 3 && level4 != null) {
            level4.setend(false);
            level4.dispose();
            level4.create();
        }
        if (map == 4 && level5 != null) {
            level5.setend(false);
            level5.dispose();
            level5.create();
        }
        if (map == 5 && level6 != null) {
            level6.setend(false);
            level6.dispose();
            level6.create();
        }
        if (map == 6 && level7 != null) {
            level7.setend(false);
            level7.dispose();
            level7.create();
        }
        if (map == 7 && level8 != null) {
            level8.setend(false);
            level8.dispose();
            level8.create();
        }
        if (map == 8 && level9 != null) {
            level9.setend(false);
            level9.dispose();
            level9.create();
        }
        if (map == 9 && level10 != null) {
            level10.setend(false);
            level10.dispose();
            level10.create();
        }
        if (map == 10 && level11 != null) {
            level11.setend(false);
            level11.dispose();
            level11.create();
        }
        if (map == 11 && level12 != null) {
            level12.setend(false);
            level12.dispose();
            level12.create();
        }

        if (pause != null) {
            pause.setState(GameState.IN_GAME); // Đặt lại trạng thái Pause
        }
    }

    /**
     * Giải phóng tài nguyên của tất cả đối tượng khi thoát game.
     */
    public void dispose() {
        if (pause != null) pause.dispose();
        if (level1 != null) level1.dispose();
        if (level2 != null) level2.dispose();
        if (level3 != null) level3.dispose();
        if (level4 != null) level4.dispose();
        if (level5 != null) level5.dispose();
        if (level6 != null) level6.dispose();
        if (level7 != null) level7.dispose();
        if (level8 != null) level8.dispose();
        if (level9 != null) level9.dispose();
        if (level10 != null) level10.dispose();
        if (level11 != null) level11.dispose();
        if (level12 != null) level12.dispose();
    }
}
