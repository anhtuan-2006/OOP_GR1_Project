package anhtuannguyen.oop.Menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

/**
 * Lớp {@code Sound} chịu trách nhiệm quản lý tất cả âm thanh trong trò chơi Arkanoid.
 * <p>
 * Bao gồm:
 * <ul>
 *   <li>Hai bản nhạc nền (Music1.mp3, Music2.mp3)</li>
 *   <li>Hiệu ứng âm thanh khi bóng chạm thanh chắn (ball_bar.mp3)</li>
 *   <li>Hiệu ứng âm thanh khi bóng chạm khối (ball_block.mp3)</li>
 * </ul>
 * 
 * Hỗ trợ các chức năng:
 * <ul>
 *   <li>Phát / dừng nhạc nền</li>
 *   <li>Chuyển đổi giữa hai bản nhạc</li>
 *   <li>Điều chỉnh âm lượng</li>
 *   <li>Phát hiệu ứng âm thanh khi có va chạm</li>
 * </ul>
 */
public class Sound {

    // Nhạc nền 1 và 2
    Music music1 = Gdx.audio.newMusic(Gdx.files.internal("Music1.mp3"));
    Music music2 = Gdx.audio.newMusic(Gdx.files.internal("Music2.mp3"));

    // Âm thanh va chạm
    Music ball_block = Gdx.audio.newMusic(Gdx.files.internal("ball_block.mp3"));
    Music ball_bar = Gdx.audio.newMusic(Gdx.files.internal("ball_bar.mp3"));

    public float volume = 1f; // Âm lượng hiện tại (0.0f – 1.0f)
    int type = 1;             // Loại nhạc nền đang phát (1 hoặc 2)

    /**
     * Khởi tạo đối tượng Sound.
     * <p>
     * Thiết lập chế độ lặp và âm lượng ban đầu cho hai bản nhạc nền.
     */
    public Sound() {
        music1.setLooping(true);
        music1.setVolume(volume);

        music2.setLooping(true);
        music2.setVolume(volume);
    }

    /**
     * Phát âm thanh khi bóng chạm vào khối gạch.
     * Dừng âm hiện tại trước khi phát lại để tránh chồng âm.
     */
    public void play_ball_block() {
        ball_block.stop();
        ball_block.play();
    }

    /**
     * Phát âm thanh khi bóng chạm vào thanh chắn.
     * Dừng âm hiện tại trước khi phát lại để đảm bảo rõ ràng.
     */
    public void play_ball_bar() {
        ball_bar.stop();
        ball_bar.play();
    }

    /**
     * Thiết lập âm lượng cho nhạc nền.
     *
     * @param x giá trị âm lượng từ 1 đến 10, được quy đổi thành 0.1f – 1.0f
     */
    void setVol(int x) {
        volume = x / 10f; // Quy đổi sang dạng float
        if (type == 1) {
            music1.setVolume(volume);
        } else {
            music2.setVolume(volume);
        }
    }

    /**
     * Phát nhạc nền theo loại hiện tại.
     * Đảm bảo chỉ có một bản nhạc phát tại cùng một thời điểm.
     */
    public void playMusic() {
        volume = 1f; // Đặt âm lượng tối đa
        music1.setVolume(volume);
        music2.setVolume(volume);

        if (type == 1) {
            if (music2.isPlaying()) music2.stop();
            if (!music1.isPlaying()) music1.play();
        } else {
            if (music1.isPlaying()) music1.stop();
            if (!music2.isPlaying()) music2.play();
        }
    }

    /**
     * Giảm âm lượng của nhạc nền hiện tại xuống mức thấp (0.3f).
     * Thường được sử dụng khi tạm dừng hoặc giảm âm thanh nền.
     */
    public void decreaseMusic() {
        volume = 0.3f;
        if (type == 1) {
            if (music1.isPlaying()) music1.setVolume(volume);
        } else {
            if (music2.isPlaying()) music2.setVolume(volume);
        }
    }

    /**
     * Chuyển đổi loại nhạc nền giữa {@code music1} và {@code music2}.
     * Nếu nhạc hiện tại đang phát, nó sẽ được dừng trước khi phát bản mới.
     */
    public void changeType() {
        if (type == 1) {
            type = 2;
            if (music1.isPlaying()) {
                music1.stop();
                music2.play();
            }
        } else {
            type = 1;
            if (music2.isPlaying()) {
                music2.stop();
                music1.play();
            }
        }
    }
}
