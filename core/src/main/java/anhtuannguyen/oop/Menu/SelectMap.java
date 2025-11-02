package anhtuannguyen.oop.Menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.Viewport;

public class SelectMap {
    private static final float WORLD_W = Screen.WORLD_W;
    private static final float WORLD_H = Screen.WORLD_H;

    private Viewport viewport;
    private Texture back_button;
    private Rectangle back_button_size = new Rectangle((float) (WORLD_W)/2 - 150, 100, 300, 150);
    private boolean touch_back_button = false;
    private Texture background;
    private Texture[] maps;
    private boolean[] touch_maps;
    private Rectangle[] map_size = new Rectangle[12];
    private int selectedMap = -1; // map được click chọn
    private InGame ingame; // Tham chiếu đến InGame
    private Pause pause; // Tham chiếu đến Pause
    private GameState gamestate;
    private Result result;
    
    // Constructor
    public SelectMap(Viewport _viewport) {
        viewport = _viewport;
    }

    // Getters and Setters
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
            if (i % 3 == 0) {
                map_size[i] = new Rectangle(
                    WORLD_W / 30,
                    count_row,
                    (8 * WORLD_W) / 30,
                    WORLD_H / 6
                );
            } else if (i % 3 == 1) {
                map_size[i] = new Rectangle(
                    (11 * WORLD_W) / 30,
                    count_row,
                    (8 * WORLD_W) / 30,
                    WORLD_H / 6
                );
            } else {
                // cột giữa
                map_size[i] = new Rectangle(
                    (21 * WORLD_W) / 30,
                    count_row,
                    (8 * WORLD_W) / 30,
                    WORLD_H / 6
                );
                count_row += WORLD_H / 6 + spacing;
            }
        }
    }

    public int getMap() {
        return selectedMap;
    }

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
            return GameState.IN_GAME; // Chuyển sang trạng thái IN_GAME nếu đã chọn map
        }
        return GameState.SELECT_MAP;
    }

    public void update() {
        Vector3 v = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        viewport.unproject(v);

        // Kiểm tra hover
        for (int i = 0; i < map_size.length; i++) {
            touch_maps[i] = map_size[i].contains(v.x, v.y);
        }
        touch_back_button = back_button_size.contains(v.x, v.y);

        // Kiểm tra click chọn map
        if (Gdx.input.justTouched() && !Menu.press) {
            for (int i = 0; i < map_size.length; i++) {
                if (map_size[i].contains(v.x, v.y)) {
                    selectedMap = i;
                    if (pause != null) {
                        pause.setState(GameState.IN_GAME); // Cập nhật trạng thái Pause
                    }
                    break;
                }
            }
        }

        if (!Gdx.input.isTouched()) {
            Menu.press = false;
        }
    }

    public void render(SpriteBatch batch) {
        batch.draw(background, 0, 0, WORLD_W, WORLD_H);
        for (int i = 0; i < maps.length; i++) {
            // System.out.println("load map_number " + (i + 1));
            if (maps[i] != null && map_size[i] != null) {
                if (touch_maps[i]) {
                    // Hiệu ứng hover
                    batch.draw(
                        maps[i],
                        map_size[i].x - 20,
                        map_size[i].y - 20,
                        map_size[i].width + 40,
                        map_size[i].height + 40
                    );
                } else {
                    batch.draw(
                        maps[i],
                        map_size[i].x,
                        map_size[i].y,
                        map_size[i].width,
                        map_size[i].height
                    );
                }
            }
        }
        if (back_button != null) {
            if (touch_back_button) {
                batch.draw(back_button, back_button_size.x - 20, back_button_size.y - 20, back_button_size.width + 40, back_button_size.height + 40);
            } else {
                batch.draw(back_button, back_button_size.x, back_button_size.y, back_button_size.width, back_button_size.height);
            }
        }
    }

    public void reset() {
        selectedMap = -1; // Đặt lại map được chọn
        for (int i = 0; i < touch_maps.length; i++) {
            touch_maps[i] = false; // Đặt lại trạng thái hover
        }
        touch_back_button = false;
        
    }
    public void nextlevel() {
        selectedMap++;
    }
    public void dispose() {
        if (background != null) background.dispose();
        if (back_button != null) back_button.dispose();
        for (Texture texture : maps) {
            if (texture != null) texture.dispose();
        }
    }
}