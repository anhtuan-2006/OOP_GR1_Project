package anhtuannguyen.oop.Menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

/**
 * Lớp Sound chịu trách nhiệm quản lý âm thanh trong trò chơi.
 * Bao gồm nhạc nền, âm thanh khi bóng chạm thanh chắn hoặc khối.
 * Hỗ trợ chuyển đổi nhạc, điều chỉnh âm lượng và phát/tắt nhạc.
 */
public class Sound {

    // Nhạc nền 1 và 2
    Music music1 = Gdx.audio.newMusic(Gdx.files.internal("Music1.mp3"));
    Music music2 = Gdx.audio.newMusic(Gdx.files.internal("Music2.mp3"));

    // Hiệu ứng âm thanh khi bóng chạm vào khối và thanh chắn
    Music ball_block = Gdx.audio.newMusic(Gdx.files.internal("ball_block.mp3"));
    Music ball_bar = Gdx.audio.newMusic(Gdx.files.internal("ball_bar.mp3"));

    public float volume = 1f; // Âm lượng hiện tại (0.0f – 1.0f)
    int type = 1; // Loại nhạc nền đang được phát (1 hoặc 2)

    /**
     * Khởi tạo đối tượng Sound.
     * Thiết lập nhạc nền phát lặp lại và gán âm lượng mặc định.
     */
    public Sound() {
        
        music1.setLooping(true);  // Lặp lại vô hạn
        music1.setVolume(volume); // Đặt âm lượng cho nhạc 1

        music2.setLooping(true);  // Lặp lại vô hạn
        music2.setVolume(volume); // Đặt âm lượng cho nhạc 2
    }

    /**
     * Phát âm thanh khi bóng chạm vào khối.
     */
    public void play_ball_block() {
        ball_block.stop(); // Ngừng phát nếu đang phát
        ball_block.play(); // Phát lại âm thanh
    }

    /**
     * Phát âm thanh khi bóng chạm vào thanh chắn.
     */
    public void play_ball_bar() {
        ball_bar.stop(); // Ngừng phát nếu đang phát
        ball_bar.play(); // Phát lại âm thanh
    }

    /**
     * Thiết lập âm lượng cho nhạc nền.
     * @param x giá trị âm lượng (từ 1 đến 10), quy đổi thành 0.1 – 1.0f
     */
    void setVol(int x) {
        if (type == 1) {
            volume = x / 10f;          // Chuyển sang dạng float (0.0 – 1.0)
            music1.setVolume(volume);  // Cập nhật cho nhạc 1
        } else {
            volume = x / 10f;
            music2.setVolume(volume);  // Cập nhật cho nhạc 2
        }
    }

    /**
     * Phát nhạc nền hiện tại, đảm bảo chỉ có một bản nhạc phát cùng lúc.
     */
    public void playMusic() {
        volume = 1f;
        music1.setVolume(volume); 
        music2.setVolume(volume); 
        if (type == 1) {
            if (music2.isPlaying()) {
                music2.stop();
            }
            if (!music1.isPlaying()) {
                music1.play();
            }
        } else {
            if (music1.isPlaying()) {
                music1.stop();
            }
            if (!music2.isPlaying()) {
                music2.play();
            }
        }
    }

    /**
     * Dừng phát nhạc nền hiện tại.
     */
    public void decreaseMusic() {
        volume = 0.3f;  
        if (type == 1) {
            if (music1.isPlaying()) {     
                music1.setVolume(volume); 
            }
        } else {
            if (music2.isPlaying()) {
                music1.setVolume(volume);
            }
        }
    }

    /**
     * Chuyển đổi loại nhạc nền (giữa music1 và music2).
     * Nếu nhạc đang phát, dừng nhạc hiện tại và phát nhạc mới.
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
