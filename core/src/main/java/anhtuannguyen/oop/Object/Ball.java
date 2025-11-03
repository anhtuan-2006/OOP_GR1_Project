package anhtuannguyen.oop.Object;

import java.lang.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import anhtuannguyen.oop.Menu.Screen;
import anhtuannguyen.oop.Menu.Sound;

public class Ball {

    private static final float WORLD_H = Screen.WORLD_H;
    private static final float WORLD_W = Screen.WORLD_W;

    // Các thuộc tính của quả bóng
    private Texture texture;
    private Texture FIRE = new Texture("Ball_fire.png");
    private double x; // Cần chuyển hàng số kích thước cửa sổ thành biến static
    private double y;
    private double dx = 1;
    private double dy = 1;
    private double angle = Math.PI / 2;
    public Bar bar;
    private float angle_role = 45f;
    private float ROLE_SPEED = 400f;

    public boolean started = true;
    boolean playing = true;

    public boolean alive = true;

    public boolean fire = false;
    public int Time_fire = 0;

    private static final float RADIUS = 48f; // Bán kính quả bóng
    private static final float SPEED = 700f; // Tốc độ di chuyển (pixel/giây)

    public float radius = RADIUS;
    public float originalRadius = RADIUS;
    public float effectTimer = -1;

    public boolean stickyToBar = false; // hiệu ứng dính vào thanh

    Sound sound = new Sound();

    public double getx() {
        return x;
    }

    public double gety() {
        return y;
    }

    public float getRADIUS() {
        return radius;
    }

    public Ball(Ball src) {
        this.bar = src.bar;
        this.texture = src.texture; // dùng chung texture OK
        this.x = src.getx();
        this.y = src.gety();
        // nếu Ball có getter cho dx, dy, angle thì dùng; không thì copy trực tiếp nếu
        // cùng file
        this.dx = 1; // hoặc src.dx nếu để cùng package/đặt accessor
        this.dy = 1; // đảm bảo không bằng 0
        this.angle = src.angle; // giữ hướng
        this.started = true; // bóng mới tự chạy
        this.playing = true;
        this.alive = true;
    }

    public Ball(Bar _bar, Texture _tex) {
        bar = _bar;
        texture = _tex;
        x = WORLD_W / 2;
        y = bar.getBounds().y + bar.getBounds().height + radius / 2;
    }

    public void isPlaying() {
        playing = !playing;
    }

    private final float MAX_BOUNCE_DEG = 75f; // góc nảy tối đa khi va chạm với thanh
    private float vx = 0;
    private float vy = SPEED;

    /**
     * Bóng di chuyển: dùng vector vận tốc từ góc, phản xạ theo bán kính.
     */
    public void Move() {
        if (!playing)
            return;

        float dt = Gdx.graphics.getDeltaTime();

        // nếu chưa bắt đầu, bám theo thanh
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

        // cập nhật vị trí
        x += vx * dt;
        y += vy * dt;

        // kiểm tra va tường
        if (x <= radius) {
            x = radius;
            vx = Math.abs(vx);
            sound.play_ball_block();
        } else if (x >= WORLD_W - radius) {
            x = WORLD_W - radius;
            vx = -Math.abs(vx);
            sound.play_ball_block();
        }
        if (y >= WORLD_H - radius) {
            y = WORLD_H - radius;
            vy = -Math.abs(vy);
            sound.play_ball_block();
        }

        // rơi khỏi màn hình
        if (y <= -radius) {
            alive = false;
            return;
        }

        // va chạm với thanh
        if (bar != null) {
            Rectangle p = bar.getBounds();
            float paddleTop = p.y + p.height;
            if (y - radius <= paddleTop && y + radius >= p.y &&
                    x >= p.x && x <= p.x + p.width && vy < 0) {

                sound.play_ball_bar();
                y = paddleTop + radius; // đặt bóng lên trên

                // tính góc bật dựa theo vị trí va chạm
                float paddleCenter = p.x + p.width / 2f;
                float hitRel = (float) ((x - paddleCenter) / (p.width / 2f));
                float bounceAngle = hitRel * MAX_BOUNCE_DEG;

                float speed = (float) Math.sqrt(vx * vx + vy * vy);
                vx = speed * (float) Math.sin(Math.toRadians(bounceAngle));
                vy = speed * (float) Math.cos(Math.toRadians(bounceAngle));
            }
        }
    }

    // ======= Hàm đổi hướng (nếu cần gọi thủ công) =======
    public void Change_Direction(int c) {
        sound.play_ball_block();
        switch (c) {
            case 1: // trái
            case 2: // phải
                vx = -vx;
                break;
            case 3: // trên
            case 4: // dưới
                vy = -vy;
                break;
            default:
                break;
        }
    }

    private float currentSpeed = SPEED;

    public void increaseSpeed(float multiplier) {
        currentSpeed *= multiplier;
    }

    // Xuất ra màn hình
    public void render(SpriteBatch batch) {
        if (Time_fire > 0)
            Time_fire--;
        if (Time_fire == 0)
            fire = false;
        angle_role += ROLE_SPEED * com.badlogic.gdx.Gdx.graphics.getDeltaTime();

        if (fire == true) {
            batch.draw(FIRE, (float) (x - radius / 2f - 10), (float) (y - radius / 2f - 10), (float) (radius + 20),
                    (float) (RADIUS + 20));
        }

        batch.draw(texture, (float) (x - radius / 2f), (float) (y - radius / 2f), (float) (radius / 2f),
                (float) (radius / 2f), (float) radius, (float) radius, 1f, 1f, angle_role, 0, 0, texture.getWidth(),
                texture.getHeight(), false, false);
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void setAngle(float angleRad) {
        this.angle = angleRad;
    }

    public Bar getBar() {
        return bar;
    }

    public void dispose() {
        texture.dispose();
    }
}
