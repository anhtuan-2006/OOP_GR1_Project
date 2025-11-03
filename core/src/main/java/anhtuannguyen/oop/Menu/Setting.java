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
 * Lớp Setting đại diện cho màn hình cài đặt trong trò chơi Arkanoid.
 * Người chơi có thể thay đổi:
 * <ul>
 *   <li>Nhạc nền (music)</li>
 *   <li>Âm lượng (volume)</li>
 *   <li>Số mạng (tim - heart)</li>
 * </ul>
 * Lớp này chịu trách nhiệm tạo giao diện, cập nhật thao tác chuột và hiển thị hình ảnh.
 */
public class Setting extends Screen {

    // ====== Texture hiển thị giao diện ======
    private Texture background;            // Hình nền chính của màn hình Setting
    private Texture play;                  // (Không dùng - có thể dùng cho nút Play)
    private Texture exit;                  // (Không dùng - có thể dùng cho nút Exit)
    private Texture setting;               // (Không dùng - texture biểu tượng cài đặt)
    private Texture logo;                  // Logo Arkanoid
    private Texture setting_background;    // Khung nền vùng cài đặt
    private Texture right;                 // Nút chuyển phải
    private Texture left;                  // Nút chuyển trái

    // ====== Danh sách texture cho từng loại cài đặt ======
    private List<Texture> music = new ArrayList<>();   // Danh sách các ảnh nhạc
    private List<Texture> volume = new ArrayList<>();  // Danh sách các mức âm lượng
    private List<Texture> heart = new ArrayList<>();   // Danh sách số tim

    // ====== Trạng thái hiện tại của các lựa chọn ======
    int music_type = 1;   // Loại nhạc đang chọn (1-2)
    int volume_type = 5;  // Mức âm lượng (1-5)
    int heart_type = 3;   // Loại tim (1,3,5 tim)

    // ====== Vị trí và kích thước vùng hiển thị ======
    Rectangle setting_size;   // Vùng nền cài đặt
    Rectangle logo_size;      // Vị trí logo

    // ====== Nút quay lại menu chính ======
    private Texture back_button;  
    private Rectangle back_button_size = new Rectangle((float) (WORLD_W) / 2 - 150, 100, 300, 150);
    private boolean touch_back_button = false;  // Trạng thái chạm vào nút Back

    // ====== Biến lựa chọn hiện tại (0 = none, 1 = music, 2 = volume, 3 = heart) ======
    int chose = 0;

    // ====== Trạng thái chạm của các nút và vùng ======
    boolean touch_music = false;
    boolean touch_volume = false;
    boolean touch_heart = false;
    boolean touch_right = false;
    boolean touch_left = false;

    // ====== Âm thanh của game ======
    private Sound MusicSound;

    // ====== Vùng cảm ứng của từng phần ======
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
     * Tải các texture và thiết lập kích thước vùng hiển thị cho màn hình Setting.
     */
    public void create() {
        background = new Texture("Menu_background.jpg");
        setting_background = new Texture("settingbackground.png");
        setting_size = new Rectangle(10, 300, WORLD_W - 20, 1000);

        logo = new Texture("Arkanoid.png");
        logo_size = new Rectangle(WORLD_W / 2 - 600, WORLD_H / 3 * 2, 1200, 600);

        back_button = new Texture("back_button.png");

        // Texture cho nhạc
        music.add(new Texture("music1.png"));
        music.add(new Texture("music2.png"));

        // Texture cho âm lượng (5 mức)
        volume.add(new Texture("Vol1.png"));
        volume.add(new Texture("Vol2.png"));
        volume.add(new Texture("Vol3.png"));
        volume.add(new Texture("Vol4.png"));
        volume.add(new Texture("Vol5.png"));

        // Texture cho số mạng (tim)
        heart.add(new Texture("Heart1.png"));
        heart.add(new Texture("Heart3.png"));
        heart.add(new Texture("Heart5.png"));

        right = new Texture("right_buttom.png");
        left = new Texture("left_buttom.png");
    }

    /**
     * Lấy trạng thái màn hình tiếp theo.
     * 
     * @return GameState.SETTING nếu vẫn ở màn cài đặt, GameState.MENU nếu người dùng chọn Back.
     */
    public GameState getSelectedMap() {
        Vector3 v = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        viewport.unproject(v);

        // Kiểm tra nếu người chơi nhấn nút Back
        if (touch_back_button && Gdx.input.justTouched() && back_button_size.contains(v.x, v.y)) {
            return GameState.MENU;
        }
        return GameState.SETTING;
    }

    /**
     * Cập nhật trạng thái đầu vào của người chơi (nhấn chuột, chọn mục, thay đổi giá trị).
     */
    public void update() {
        Vector3 v = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        viewport.unproject(v);

        // ===== Kiểm tra nút Back =====
        touch_back_button = back_button_size.contains(v.x, v.y);

        // ===== Xử lý nút Right =====
        if (right_size.contains(v.x, v.y)) {
            touch_right = true;
            if (Gdx.input.justTouched()) {
                if (chose == 1) { // Thay đổi loại nhạc
                    music_type = (music_type % 2) + 1;
                    MusicSound.changeType();
                } else if (chose == 2) { // Tăng âm lượng
                    volume_type = (volume_type % 5) + 1;
                    MusicSound.setVol(volume_type * 2);
                } else if (chose == 3) { // Tăng số tim
                    heart_type = (heart_type % 3) + 1;
                }
            }
        } else touch_right = false;

        // ===== Xử lý nút Left =====
        if (left_size.contains(v.x, v.y)) {
            touch_left = true;
            if (Gdx.input.justTouched()) {
                if (chose == 1) { // Giảm loại nhạc
                    music_type = (music_type == 1) ? 2 : music_type - 1;
                    MusicSound.changeType();
                } else if (chose == 2) { // Giảm âm lượng
                    volume_type = (volume_type == 1) ? 5 : volume_type - 1;
                    MusicSound.setVol(volume_type * 2);
                } else if (chose == 3) { // Giảm số tim
                    heart_type = (heart_type == 1) ? 3 : heart_type - 1;
                }
            }
        } else touch_left = false;

        // ===== Cập nhật trạng thái mục được chọn =====
        touch_music = (chose == 1);
        touch_volume = (chose == 2);
        touch_heart = (chose == 3);

        // ===== Xử lý chọn mục =====
        if (music_size.contains(v.x, v.y) && Gdx.input.justTouched()) {
            chose = (chose == 1) ? 0 : 1;
        }
        if (volume_size.contains(v.x, v.y) && Gdx.input.justTouched()) {
            chose = (chose == 2) ? 0 : 2;
        }
        if (heart_size.contains(v.x, v.y) && Gdx.input.justTouched()) {
            chose = (chose == 3) ? 0 : 3;
        }
    }

    /**
     * Lấy số mạng (tim) tương ứng với lựa chọn hiện tại.
     * 
     * @return số mạng (1, 3 hoặc 5)
     */
    public int getlife() {
        if (heart_type == 1) return 1;
        else if (heart_type == 2) return 3;
        return 5;
    }

    /**
     * Vẽ toàn bộ các phần tử của màn hình Setting.
     * 
     * @param batch SpriteBatch từ vòng lặp render chính.
     */
    public void render(SpriteBatch batch) {
        // Vẽ nền
        batch.draw(background, 0, 0, WORLD_W, WORLD_H);
        batch.draw(setting_background, setting_size.x, setting_size.y, setting_size.width, setting_size.height);

        // Nút Back
        if (touch_back_button)
            hoverDraw(batch, back_button, back_button_size);
        else
            normalDraw(batch, back_button, back_button_size);

        // Mục Nhạc
        if (touch_music)
            hoverDraw(batch, music.get(music_type - 1), music_size);
        else
            normalDraw(batch, music.get(music_type - 1), music_size);

        // Mục Âm lượng
        if (touch_volume)
            hoverDraw(batch, volume.get(volume_type - 1), volume_size);
        else
            normalDraw(batch, volume.get(volume_type - 1), volume_size);

        // Mục Tim
        if (touch_heart)
            hoverDraw(batch, heart.get(heart_type - 1), heart_size);
        else
            normalDraw(batch, heart.get(heart_type - 1), heart_size);

        // Nút phải
        if (touch_right)
            hoverDraw(batch, right, right_size);
        else
            normalDraw(batch, right, right_size);

        // Nút trái
        if (touch_left)
            hoverDraw(batch, left, left_size);
        else
            normalDraw(batch, left, left_size);

        // Logo game
        batch.draw(logo, logo_size.x, logo_size.y, logo_size.width, logo_size.height);
    }

    /**
     * Vẽ texture ở trạng thái bình thường.
     */
    private void normalDraw(SpriteBatch batch, Texture texture, Rectangle rect) {
        batch.draw(texture, rect.x - 30, rect.y - 30, rect.width + 60, rect.height + 60);
    }

    /**
     * Vẽ texture khi được chọn (hiệu ứng hover).
     */
    private void hoverDraw(SpriteBatch batch, Texture texture, Rectangle rect) {
        batch.draw(texture, rect.x, rect.y, rect.width, rect.height);
    }

    /**
     * Giải phóng tài nguyên hình ảnh khi không còn sử dụng.
     */
    public void dispose() {
        background.dispose();
        logo.dispose();
    }
}
