package anhtuannguyen.oop.Menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Lớp SelectMap quản lý giao diện chọn màn chơi.
 * Hiển thị danh sách các bản đồ và xử lý tương tác người dùng để chọn map hoặc quay lại menu.
 */
public class SelectMap extends Screen {

    private Viewport viewport;
    private Texture back_button;
    private Rectangle back_button_size = new Rectangle(WORLD_W / 2 - 150, 100, 300, 150);
    private boolean touch_back_button = false;

    private Texture background;
    private Texture[] maps;
    private boolean[] touch_maps;
    private Rectangle[] map_size = new Rectangle[12];

    private int selectedMap = -1; // Chỉ số map được chọn

    private InGame ingame; // Tham chiếu đến InGame
    private Pause pause;   // Tham chiếu đến Pause
    private GameState gamestate;
    private Result result;

    /**
     * Constructor khởi tạo giao diện chọn map với viewport.
     * @param _viewport Viewport hiện tại.
     */
    public SelectMap(Viewport _viewport) {
        viewport = _viewport;
    }

    // Getters và Setters
    public InGame getIngame() {
        return ingame;
    }

    public Pause getPause() {
        return pause;
    }

    public void setIngame(InGame _ingame) {
        this.ingame = _ingame;
    }

    public void setPause(Pause _pause) {
        this.pause = _pause;
    }

    public void setState(GameState _gamestate) {
        gamestate = _gamestate;
    }

    public GameState getState() {
        return gamestate;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    /**
     * Khởi tạo các texture và vị trí bản đồ.
     */
    public void create() {
        background = new Texture("Menu_background.jpg");
        maps = new Texture[12];
        for (int i = 0; i < maps.length; i++) {
            maps[i] = new Texture("Avata_Level" + (i + 1) + ".png");
        }
        back_button = new Texture("back_button.png");

        map_size = new Rectangle[maps.length];
        touch_maps = new boolean[maps.length];

        float count_row = 300f;
        float spacing = 20f;

        for (int i = 0; i < maps.length; i++) {
            float x, y = count_row;
            float width = (8 * WORLD_W) / 30;
            float height = WORLD_H / 6;

            if (i % 3 == 0) x = WORLD_W / 30;
            else if (i % 3 == 1) x = (11 * WORLD_W) / 30;
            else {
                x = (21 * WORLD_W) / 30;
                count_row += height + spacing;
            }

            map_size[i] = new Rectangle(x, y, width, height);
        }
    }

    /**
     * Lấy chỉ số map đã được chọn.
     * @return Chỉ số map.
     */
    public int getMap() {
        return selectedMap;
    }

    /**
     * Xác định trạng thái tiếp theo dựa trên tương tác người dùng.
     * @return Trạng thái trò chơi tiếp theo.
     */
    public GameState getSelectedMap() {
        Vector3 v = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        viewport.unproject(v);

        if (touch_back_button && Gdx.input.justTouched() && !Menu.press) {
            Menu.press = true;
            if (ingame != null) {
                ingame.reset(); // Reset InGame khi quay lại Menu
            }
            return GameState.MENU;
        }

        if (selectedMap != -1) {
            return GameState.IN_GAME;
        }

        return GameState.SELECT_MAP;
    }

    /**
     * Cập nhật trạng thái hover và xử lý chọn map.
     */
    public void update() {
        Vector3 v = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        viewport.unproject(v);

        for (int i = 0; i < map_size.length; i++) {
            touch_maps[i] = map_size[i].contains(v.x, v.y);
        }

        touch_back_button = back_button_size.contains(v.x, v.y);

        if (Gdx.input.justTouched() && !Menu.press) {
            for (int i = 0; i < map_size.length; i++) {
                if (map_size[i].contains(v.x, v.y)) {
                    selectedMap = i;
                    if (pause != null) {
                        pause.setState(GameState.IN_GAME);
                    }
                    break;
                }
            }
        }

        if (!Gdx.input.isTouched()) {
            Menu.press = false;
        }
    }

    /**
     * Vẽ giao diện chọn map lên màn hình.
     * @param batch SpriteBatch để vẽ.
     */
    public void render(SpriteBatch batch) {
        batch.draw(background, 0, 0, WORLD_W, WORLD_H);

        for (int i = 0; i < maps.length; i++) {
            if (maps[i] != null && map_size[i] != null) {
                if (touch_maps[i]) {
                    batch.draw(maps[i],
                               map_size[i].x - 20,
                               map_size[i].y - 20,
                               map_size[i].width + 40,
                               map_size[i].height + 40);
                } else {
                    batch.draw(maps[i],
                               map_size[i].x,
                               map_size[i].y,
                               map_size[i].width,
                               map_size[i].height);
                }
            }
        }

        if (back_button != null) {
            if (touch_back_button) {
                batch.draw(back_button,
                           back_button_size.x - 20,
                           back_button_size.y - 20,
                           back_button_size.width + 40,
                           back_button_size.height + 40);
            } else {
                batch.draw(back_button,
                           back_button_size.x,
                           back_button_size.y,
                           back_button_size.width,
                           back_button_size.height);
            }
        }
    }

    /**
     * Đặt lại trạng thái chọn map.
     */
    public void reset() {
        selectedMap = -1;
        for (int i = 0; i < touch_maps.length; i++) {
            touch_maps[i] = false;
        }
        touch_back_button = false;
    }

    /**
     * Chuyển sang màn chơi tiếp theo.
     */
    public void nextlevel() {
        selectedMap++;
    }

    /**
     * Giải phóng tài nguyên texture khi không còn sử dụng.
     */
    public void dispose() {
        if (background != null) background.dispose();
        if (back_button != null) back_button.dispose();
        for (Texture texture : maps) {
            if (texture != null) texture.dispose();
        }
    }
}