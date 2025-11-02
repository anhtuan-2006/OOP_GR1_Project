package anhtuannguyen.oop.Menu;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Lớp Setting đại diện cho màn hình cài đặt trong trò chơi.
 * Người chơi có thể thay đổi nhạc nền, âm lượng và số mạng (tim).
 * Quản lý giao diện, xử lý thao tác chuột và vẽ các phần tử.
 */
public class Setting {
    private static final float WORLD_W = Screen.WORLD_W; // Chiều rộng thế giới game
    private static final float WORLD_H = Screen.WORLD_H; // Chiều cao thế giới game

    // Các texture hiển thị giao diện
    private Texture background;
    private Texture play;
    private Texture exit;
    private Texture setting;
    private Texture logo;
    private Texture setting_background;
    private Texture right;
    private Texture left;

    // Danh sách texture cho các loại nhạc, âm lượng, tim
    private List<Texture> music = new ArrayList<>();
    private List<Texture> volume = new ArrayList<>();
    private List<Texture> heart = new ArrayList<>();

    // Loại nhạc, âm lượng, số tim hiện tại
    int music_type = 1;
    int volume_type = 5;
    int heart_type = 3;

    Viewport viewport; // Đối tượng viewport để chuyển đổi tọa độ chuột

    Rectangle setting_size;
    Rectangle logo_size;
    private Texture back_button; // Nút quay lại menu chính
    private Rectangle back_button_size = new Rectangle((float) (WORLD_W) / 2 - 150, 100, 300, 150);
    private boolean touch_back_button = false; // Trạng thái chạm nút quay lại

    int chose = 0; // Biến chọn mục đang được thao tác (0 = none, 1 = music, 2 = volume, 3 = heart)

    // Các trạng thái chạm của các nút giao diện
    boolean touch_music = false;
    boolean touch_volume = false;
    boolean touch_heart = false;
    boolean touch_right = false;
    boolean touch_left = false;

    private Sound MusicSound; // Đối tượng âm thanh của game

    // Các vùng cảm ứng của các mục trong cài đặt
    Rectangle music_size = new Rectangle(WORLD_W / 2 - 200, WORLD_W / 2 + 325, 400, 150);
    Rectangle volume_size = new Rectangle(WORLD_W / 2 - 200, WORLD_W / 2 + 75, 400, 150);
    Rectangle heart_size = new Rectangle(WORLD_W / 2 - 200, WORLD_W / 2 - 175, 400, 150);
    Rectangle right_size = new Rectangle(950, WORLD_W / 2 - 175, 200, 650);
    Rectangle left_size = new Rectangle(WORLD_W - 950 - 200, WORLD_W / 2 - 175, 200, 650);

    /**
     * Khởi tạo lớp Setting.
     * 
     * @param _viewport viewport dùng để chiếu điểm cảm ứng
     * @param _music    đối tượng âm thanh hiện tại của trò chơi
     */
    public Setting(Viewport _viewport, Sound _music) {
        viewport = _viewport;
        MusicSound = _music;
    }

    /**
     * Hàm tạo và tải các texture cần dùng cho màn hình Setting.
     */
    public void create() {
        background = new Texture("Menu_background.jpg");
        setting_background = new Texture("settingbackground.png");
        setting_size = new Rectangle(10, 300, WORLD_W - 20, 1000);

        logo = new Texture("Arkanoid.png");
        logo_size = new Rectangle(WORLD_W / 2 - 600, WORLD_H / 3 * 2, 1200, 600);

        back_button = new Texture("back_button.png");

        // Thêm các texture cho nhạc, âm lượng, và tim
        music.add(new Texture("music1.png"));
        music.add(new Texture("music2.png"));
        volume.add(new Texture("Vol1.png"));
        volume.add(new Texture("Vol2.png"));
        volume.add(new Texture("Vol3.png"));
        volume.add(new Texture("Vol4.png"));
        volume.add(new Texture("Vol5.png"));
        heart.add(new Texture("Heart1.png"));
        heart.add(new Texture("Heart3.png"));
        heart.add(new Texture("Heart5.png"));

        right = new Texture("right_buttom.png");
        left = new Texture("left_buttom.png");
    }

    /**
     * Lấy trạng thái màn hình tiếp theo tùy theo lựa chọn.
     * 
     * @return GameState.SETTING nếu ở lại màn cài đặt,
     *         GameState.MENU nếu người dùng nhấn nút Back.
     */
    public GameState getSelectedMap() {
        Vector3 v = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        viewport.unproject(v);
        if (touch_back_button && Gdx.input.justTouched() && back_button_size.contains(v.x, v.y)) {
            return GameState.MENU;
        }
        return GameState.SETTING;
    }

    /**
     * Cập nhật trạng thái đầu vào của người chơi và xử lý lựa chọn.
     */
    public void update() {
        Vector3 v = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        viewport.unproject(v);

        // Kiểm tra chạm nút Back
        if (back_button_size.contains(v.x, v.y)) {
            touch_back_button = true;
        } else
            touch_back_button = false;

        // Xử lý khi nhấn nút Right
        if (right_size.contains(v.x, v.y)) {
            touch_right = true;
            if (Gdx.input.justTouched()) {
                if (chose == 1) { // Nhạc
                    music_type++;
                    if (music_type > 2)
                        music_type = 1;
                    MusicSound.changeType();
                } else if (chose == 2) { // Âm lượng
                    volume_type++;
                    if (volume_type > 5)
                        volume_type = 1;
                    MusicSound.setVol(volume_type * 2);
                } else if (chose == 3) { // Tim
                    heart_type++;
                    if (heart_type > 3)
                        heart_type = 1;
                }
            }
        } else
            touch_right = false;

        // Xử lý khi nhấn nút Left
        if (left_size.contains(v.x, v.y)) {
            touch_left = true;
            if (Gdx.input.justTouched()) {
                if (chose == 1) {
                    music_type--;
                    if (music_type < 1)
                        music_type = 2;
                    MusicSound.changeType();
                } else if (chose == 2) {
                    volume_type--;
                    if (volume_type < 1)
                        volume_type = 5;
                    MusicSound.setVol(volume_type * 2);
                } else if (chose == 3) {
                    heart_type--;
                    if (heart_type < 1)
                        heart_type = 3;
                }
            }
        } else
            touch_left = false;

        // Cập nhật trạng thái mục đang chọn
        if (chose == 0) {
            touch_music = false;
            touch_volume = false;
            touch_heart = false;
        } else if (chose == 1) {
            touch_music = true;
            touch_volume = false;
            touch_heart = false;
        } else if (chose == 2) {
            touch_music = false;
            touch_volume = true;
            touch_heart = false;
        } else {
            touch_music = false;
            touch_volume = false;
            touch_heart = true;
        }

        // Kiểm tra click chọn từng mục
        if (music_size.contains(v.x, v.y) && Gdx.input.justTouched()) {
            if (chose == 1)
                chose = 0;
            else
                chose = 1;
        }

        if (volume_size.contains(v.x, v.y) && Gdx.input.justTouched()) {
            if (chose == 2)
                chose = 0;
            else
                chose = 2;
        }

        if (heart_size.contains(v.x, v.y) && Gdx.input.justTouched()) {
            if (chose == 3)
                chose = 0;
            else
                chose = 3;
        }

    }

    /**
     * Trả về số mạng (tim) tương ứng với lựa chọn hiện tại.
     * 
     * @return số mạng (1, 3 hoặc 5)
     */
    public int getlife() {
        if (heart_type == 1) {
            return 1;
        } else if (heart_type == 2) {
            return 3;
        }
        return 5;
    }

    /**
     * Vẽ toàn bộ các phần tử trên màn hình Setting.
     * 
     * @param batch SpriteBatch được truyền vào từ vòng lặp render chính.
     */
    public void render(SpriteBatch batch) {
        batch.draw(background, 0, 0, WORLD_W, WORLD_H);
        batch.draw(setting_background, setting_size.x, setting_size.y, setting_size.width, setting_size.height);

        // Vẽ nút Back với hiệu ứng khi chạm
        if (touch_back_button)
            batch.draw(back_button, back_button_size.x - 20, back_button_size.y - 20, back_button_size.width + 40,
                    back_button_size.height + 40);
        else
            batch.draw(back_button, back_button_size.x, back_button_size.y, back_button_size.width,
                    back_button_size.height);

        // Vẽ mục Nhạc
        if (touch_music)
            batch.draw(music.get(music_type - 1), music_size.x - 50, music_size.y - 50, music_size.width + 100,
                    music_size.height + 100);
        else
            batch.draw(music.get(music_type - 1), music_size.x, music_size.y, music_size.width, music_size.height);

        // Vẽ mục Âm lượng
        if (touch_volume)
            batch.draw(volume.get(volume_type - 1), volume_size.x - 50, volume_size.y - 50, volume_size.width + 100,
                    volume_size.height + 100);
        else
            batch.draw(volume.get(volume_type - 1), volume_size.x, volume_size.y, volume_size.width,
                    volume_size.height);

        // Vẽ mục Tim (số mạng)
        if (touch_heart)
            batch.draw(heart.get(heart_type - 1), heart_size.x - 50, heart_size.y - 50, heart_size.width + 100,
                    heart_size.height + 100);
        else
            batch.draw(heart.get(heart_type - 1), heart_size.x, heart_size.y, heart_size.width, heart_size.height);

        // Vẽ nút phải
        if (touch_right) {
            batch.draw(right, right_size.x - 30, right_size.y - 30, right_size.width + 60, right_size.height + 60);
        } else {
            batch.draw(right, right_size.x, right_size.y, right_size.width, right_size.height);
        }

        // Vẽ nút trái
        if (touch_left) {
            batch.draw(left, left_size.x - 30, left_size.y - 30, left_size.width + 60, left_size.height + 60);
        } else {
            batch.draw(left, left_size.x, left_size.y, left_size.width, left_size.height);
        }

        // Vẽ logo game
        batch.draw(logo, logo_size.x, logo_size.y, logo_size.width, logo_size.height);
    }

    /**
     * Giải phóng bộ nhớ các texture khi không còn sử dụng.
     */
    public void dispose() {
        background.dispose();
        logo.dispose();
    }
}
