package anhtuannguyen.oop;

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
    private Texture background;
    private Texture[] maps;
    private boolean[] touch_maps;
    private Rectangle[] map_size = new Rectangle[10];

    private int selectedMap = -1; // map được click chọn

    public SelectMap(Viewport _viewport) {
        viewport = _viewport;
    }

    public void create() {
        background = new Texture("Menu_background.jpg");
        maps = new Texture[10];
        maps[0] = new Texture("background_level1.jpg");
        maps[1] = new Texture("background_level2.png");
        maps[2] = new Texture("background_level3.png");
        maps[3] = new Texture("Background_Level4.png");
        maps[4] = new Texture("Background_Level5.png");
        maps[5] = new Texture("Background_Level6.jpg");
        maps[6] = new Texture("Background_Level7.jpg");
        maps[7] = new Texture("Background_Level8.jpg");
        maps[8] = new Texture("Background_Level9.jpg");
        maps[9] = new Texture("Background_Level10.jpg");
        

        map_size = new Rectangle[maps.length];
        touch_maps = new boolean[maps.length]; //  khởi tạo mảng hover

        float count_row = 100f;
        float spacing = 20f;

        for (int i = 0; i < maps.length; i++) {
            if (i % 2 == 0) {
                // cột trái
                map_size[i] = new Rectangle(
                    WORLD_W / 20,
                    count_row,
                    (8 * WORLD_W) / 20,
                    WORLD_H / 6
                );
            } else {
                // cột phải
                map_size[i] = new Rectangle(
                    (11 * WORLD_W) / 20,
                    count_row,
                    (8 * WORLD_W) / 20,
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
        if (!(selectedMap == -1)) return GameState.IN_GAME; // Level
        return GameState.SELECT_MAP;
    }
    public void update() {
        Vector3 v = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        viewport.unproject(v);

        // kiểm tra hover
        for (int i = 0; i < map_size.length; i++) {
            touch_maps[i] = map_size[i].contains(v.x, v.y);
        }

        // kiểm tra click chọn map
        
        if (Gdx.input.justTouched() && Menu.press != true) {
            for (int i = 0; i < map_size.length; i++) {
                if (map_size[i].contains(v.x, v.y)) {
                    selectedMap = i;
                    getSelectedMap();
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
            if (maps[i] != null && map_size[i] != null) {
                if (touch_maps[i]) {
                    // hiệu ứng hover
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
    }

    

    public void dispose() {
        background.dispose();
        for (Texture texture : maps) {
            if (texture != null) texture.dispose();
        }
    }
}
