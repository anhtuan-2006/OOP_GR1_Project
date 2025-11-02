package anhtuannguyen.oop.Menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class Sound {
    Music music1 = Gdx.audio.newMusic(Gdx.files.internal("Music1.mp3"));
    Music music2 = Gdx.audio.newMusic(Gdx.files.internal("Music2.mp3"));
    Music ball_block = Gdx.audio.newMusic(Gdx.files.internal("ball_block.mp3"));
    Music ball_bar = Gdx.audio.newMusic(Gdx.files.internal("ball_bar.mp3"));

    public float volume = 1f;

    int type = 1;

    public Sound() {
        music1.setLooping(true);
        music1.setVolume(volume);

        music2.setLooping(true);
        music2.setVolume(volume);
    }

    public void play_ball_block() {
        ball_block.stop();
        ball_block.play();
    }

    public void play_ball_bar() {
        ball_bar.stop();
        ball_bar.play();
    }

    void setVol(int x)
    {
        if(type == 1)
        {
            volume = x / 10f;
            music1.setVolume(volume);
        }
        else
        {
            volume = x / 10f;
            music2.setVolume(volume);
        }
    }

    public void playMusic() {
        if (type == 1) {
            if(music2.isPlaying()) {
                music2.stop();
            }

            if (!music1.isPlaying()) {
                music1.play();
            }
        } else {
            if(music1.isPlaying()) {
                music1.stop();
            }

            if (!music2.isPlaying()) {
                music2.play();
            }
        }
    }

    public void stopMusic() {
        if (type == 1) {
            if (music1.isPlaying()) {
                music1.stop();
            }
        } else {
            if (music2.isPlaying()) {
                music2.stop();
            }
        }
    }

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
