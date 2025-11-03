package anhtuannguyen.oop.Object;

import java.lang.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import anhtuannguyen.oop.Menu.Screen;
import anhtuannguyen.oop.Menu.Sound;

/**
 * Lớp Ball mô tả quả bóng trong trò chơi Arkanoid.
 * Quả bóng có thể di chuyển, nảy, và tương tác với thanh Bar.
 */
public class Ball extends Object {

    private static final float WORLD_H = Screen.WORLD_H;
    private static final float WORLD_W = Screen.WORLD_W;

    // Texture của quả bóng
    private Texture texture;
    private Texture FIRE = new Texture("Ball_fire.png");

    // Vận tốc theo 2 trục
    private double dx = 1;
    private double dy = 1;
    private double angle = Math.PI / 2;

    // Liên kết với thanh Bar
    public Bar bar;

    // Góc quay để hiệu ứng xoay bóng
    private float angle_role = 45f;
    private float ROLE_SPEED = 400f;
    private float prevY;

    public boolean started = true;
    boolean playing = true;
    public boolean alive = true;
    public boolean fire = false;
    public int Time_fire = 0;

    // Bán kính và tốc độ bóng
    private static final float RADIUS = 48f;
    private static final float SPEED = 700f;

    public float radius = RADIUS;
    public float originalRadius = RADIUS;
    public float effectTimer = -1;

    public boolean stickyToBar = false; // hiệu ứng dính thanh

    Sound sound = new Sound();

    /** @return Bán kính hiện tại của bóng */
    public float getRADIUS() {
        return radius;
    }

    /**
     * Constructor sao chép từ một quả bóng khác.
     * 
     * @param src Quả bóng gốc
     */
    public Ball(Ball src) {
        this.bar = src.bar;
        this.texture = src.texture;
        x = (float) src.getx();
        y = (float) src.gety();
        this.dx = 1;
        this.dy = 1;
        this.angle = src.angle;
        this.started = true;
        this.playing = true;
        this.alive = true;
    }

    /**
     * Constructor khởi tạo bóng theo thanh bar và texture.
     * 
     * @param _bar Thanh điều khiển
     * @param _tex Texture của bóng
     */
    public Ball(Bar _bar, Texture _tex) {
        bar = _bar;
        texture = _tex;
        x = WORLD_W / 2;
        y = bar.getBounds().y + bar.getBounds().height + radius / 2;
    }

    /** Đảo trạng thái chơi/tạm dừng */
    public void isPlaying() {
        playing = !playing;
    }

    private final float MAX_BOUNCE_DEG = 75f; // Góc bật tối đa
    private float vx = 0;
    private float vy = SPEED;

    /**
     * Cập nhật vị trí và xử lý va chạm của bóng mỗi frame.
     */
    public void Move() {
        if (!playing)
            return;

        float dt = Gdx.graphics.getDeltaTime();

        // Nếu chưa bắt đầu, bóng bám theo thanh
        if (!started) {
            x = bar.getx() + bar.getWidth() / 2f;
            y = bar.gety() + radius;
            if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
                started = true;
                angle = (float) Math.PI / 2;
                vx = 0;
                vy = SPEED;
            }
            return;
        }

        // Cập nhật vị trí
        prevY = y;
        x += vx * dt;
        y += vy * dt;

        // Va chạm tường trái/phải
        if (x <= radius) {
            x = radius;
            vx = Math.abs(vx);
            sound.play_ball_block();
        } else if (x >= WORLD_W - radius) {
            x = WORLD_W - radius;
            vx = -Math.abs(vx);
            sound.play_ball_block();
        }

        // Va trần
        if (y >= WORLD_H - radius) {
            y = WORLD_H - radius;
            vy = -Math.abs(vy);
            sound.play_ball_block();
        }

        // Rơi khỏi màn
        if (y <= -radius) {
            alive = false;
            return;
        }

        // Va chạm với thanh bar
        Rectangle p = bar.getBounds();
        float paddleTop = p.y + p.height;

        // Hiệu ứng dính thanh
        if (stickyToBar && (vy < 0) && (prevY - radius >= paddleTop) && (y - radius <= paddleTop)
                && (x >= p.x - radius) && (x <= p.x + p.width + radius)) {
            started = false;
            stickyToBar = false;
            setPosition(bar.getx() + bar.getWidth() / 2, bar.gety() + RADIUS);
            return;
        }

        // Va chạm hai bên
        if (y < paddleTop && y + radius / 2 >= p.y && x + radius / 2 >= p.x && x < p.x) {
            x = p.x - radius / 2;
            Change_Direction(2);
        } else if (y < paddleTop && y + radius / 2 >= p.y && x - radius / 2 <= p.x + p.width && x > p.x + p.width) {
            x = p.x + p.width + radius / 2;
            Change_Direction(1);
        }

        // Va chạm trên mặt thanh
        else if (y - radius / 2f <= paddleTop && y > paddleTop &&
                x + radius / 2 >= p.x && x - radius / 2 <= p.x + p.width && vy < 0) {

            sound.play_ball_bar();
            y = paddleTop + radius / 2;

            // Tính góc bật theo vị trí va chạm
            float paddleCenter = p.x + p.width / 2f;
            float hitRel = (float) ((x - paddleCenter) / (p.width / 2f));
            float bounceAngle = hitRel * MAX_BOUNCE_DEG;

            float speed = (float) Math.sqrt(vx * vx + vy * vy);
            vx = speed * (float) Math.sin(Math.toRadians(bounceAngle));
            vy = speed * (float) Math.cos(Math.toRadians(bounceAngle));
        }
    }

    /**
     * Thay đổi hướng bóng thủ công.
     * 
     * @param c Mã hướng (1: trái, 2: phải, 3: trên, 4: dưới)
     */
    public void Change_Direction(int c) {
        sound.play_ball_block();
        switch (c) {
            case 1:
            case 2:
                vx = -vx;
                break;
            case 3:
            case 4:
                vy = -vy;
                break;
            default:
                break;
        }
    }

    private float currentSpeed = SPEED;

    /**
     * Tăng tốc bóng theo hệ số.
     * 
     * @param multiplier Hệ số nhân tốc độ
     */
    public void increaseSpeed(float multiplier) {
        currentSpeed *= multiplier;
    }

    /**
     * Vẽ bóng lên màn hình.
     * 
     * @param batch SpriteBatch để vẽ
     */
    public void render(SpriteBatch batch) {
        if (Time_fire > 0)
            Time_fire--;
        if (Time_fire == 0)
            fire = false;

        angle_role += ROLE_SPEED * Gdx.graphics.getDeltaTime();

        // Hiệu ứng bóng lửa
        if (fire) {
            batch.draw(FIRE, (float) (x - radius / 2f - 10), (float) (y - radius / 2f - 10),
                    (float) (radius + 20), (float) (RADIUS + 20));
        }

        // Vẽ bóng chính
        batch.draw(texture, (float) (x - radius / 2f), (float) (y - radius / 2f),
                (float) (radius / 2f), (float) (radius / 2f),
                (float) radius, (float) radius,
                1f, 1f, angle_role, 0, 0, texture.getWidth(), texture.getHeight(), false, false);
    }

    /** Đặt vị trí bóng */
    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    /** Đặt góc hướng bóng */
    public void setAngle(float angleRad) {
        this.angle = angleRad;
    }

    /** @return Thanh bar hiện tại */
    public Bar getBar() {
        return bar;
    }

    /** Giải phóng texture */
    public void dispose() {
        texture.dispose();
    }
}
