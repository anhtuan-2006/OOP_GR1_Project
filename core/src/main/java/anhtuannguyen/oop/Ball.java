package anhtuannguyen.oop;

import java.lang.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

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
    private static final float SPEED = 1000f; // Tốc độ di chuyển (pixel/giây)

    public float radius = RADIUS;
    public float originalRadius = RADIUS;
    public float effectTimer = -1;


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

    // Bóng di chuyển: dùng vector vận tốc từ góc, phản xạ theo bán kính
    private static final float MAX_BOUNCE_DEG = 75f;

    public void Move() {


        if (playing == false)
            return;

         float dtt = Gdx.graphics.getDeltaTime();

     if (effectTimer >= 0) {
    effectTimer += dtt;
    if (effectTimer >= 5f) {
        radius = originalRadius;
        effectTimer = -1;
    }
}

        if (started == false) {
            if (Gdx.input.isKeyPressed(Input.Keys.SPACE))
                started = true;
            else
                return;
        }

        float dt = Gdx.graphics.getDeltaTime();

        // Lưu vị trí trước bước cập nhật
        double prevX = x;
        double prevY = y;

        // Vận tốc từ góc hiện tại
        double vx = Math.cos(angle) * SPEED * dx;
        double vy = Math.sin(angle) * SPEED * dy;

        // Cập nhật vị trí
        x += vx * dt;
        y += vy * dt;

        // Bật tường trái/phải theo bán kính
        if (x <= radius) {
            x = radius;
            dx = -dx;
        } else if (x >= WORLD_W - radius) {
            x = WORLD_W - radius;
            dx = -dx;
        }

        // Bật trần
        if (y >= WORLD_H - radius) {
            y = WORLD_H - radius;
            dy = -dy;
        }

        // Rơi khỏi đáy
        if (y <= radius) {
            alive = false;
            return;
        }

        // Va chạm với thanh
        if (bar != null) {
            com.badlogic.gdx.math.Rectangle p = bar.getBounds();
            float paddleTop = p.y + p.height;

            boolean goingDown = (vy < 0);
            boolean crossTop = (prevY - radius >= paddleTop) && (y - radius <= paddleTop);
            boolean overlapX = (x >= p.x - radius) && (x <= p.x + p.width + radius);

            // 1) Bật mép trên thanh (đang đi xuống, cắt qua mép trên)
            if (goingDown && crossTop && overlapX) {
                y = paddleTop + radius / 2;

                float paddleCenter = p.x + p.width / 2f;
                float hitRel = (float) ((x - paddleCenter) / (p.width / 2f));

                float outDeg = (float) Math.toDegrees(angle) - hitRel * MAX_BOUNCE_DEG;
                if (outDeg >= 160)
                    outDeg = 165f;
                if (outDeg <= 20)
                    outDeg = 15f;
                angle = Math.toRadians(outDeg);

                dy = +1;
            } else {
                // 2) Bật cạnh trái/phải của thanh
                boolean overlapY = (y + radius >= p.y) && (y - radius <= paddleTop);

                double leftSideX = p.x - radius;
                double rightSideX = p.x + p.width + radius;

                boolean goingRight = (vx > 0);
                boolean goingLeft = (vx < 0);

                boolean crossLeftSide = (prevX <= leftSideX) && (x >= leftSideX);
                boolean crossRightSide = (prevX >= rightSideX) && (x <= rightSideX);

                if (overlapY && goingRight && crossLeftSide) {
                    x = (float) leftSideX; // kẹp sát biên để tránh dính
                    dx = -dx; // bật theo trục dọc (đổi hướng ngang)
                    // giữ nguyên dy và angle (mô hình hiện tại dùng dx/dy để tạo góc)
                } else if (overlapY && goingLeft && crossRightSide) {
                    x = (float) rightSideX;
                    dx = -dx;
                }
            }
        }

        // Chuẩn hóa góc về [0, π) vì dùng dx/dy cho hướng
        angle = angle % (Math.PI);
        if (angle < 0)
            angle += Math.PI;

        return;
    }

    // Đổi hướng: 1=left, 2=right → lật trục X; 3=up, 4=down → lật trục Y
    public void Change_Direction(int c) {
        switch (c) {
            case 1:
            case 2:
                dx = -dx;
                break;
            case 3:
            case 4:
                dy = -dy;
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

    public void dispose() {
        texture.dispose();
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

}
