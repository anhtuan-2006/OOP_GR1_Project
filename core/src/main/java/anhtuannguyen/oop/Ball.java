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
    private double x = WORLD_W / 2; // Cần chuyển hàng số kích thước cửa sổ thành biến static
    private double y = WORLD_H / 2;
    private double dx = 1;
    private double dy = 1;
    private double angle = Math.PI / 2;
    private Bar bar;
    private float angle_role = 45f;
    private float ROLE_SPEED = 400f;

    boolean started = false;

    boolean playing = true;

    private static final float RADIUS = 48f; // Bán kính quả bóng
    private static final float SPEED = 1000f; // Tốc độ di chuyển (pixel/giây)

    public double getx() {
        return x;
    }

    public double gety() {
        return y;
    }

    public float getRADIUS() {
        return RADIUS;
    }

    public Ball(Bar _bar, Texture _tex) {
        bar = _bar;
        texture = _tex;
        x = WORLD_W / 2;
        y = bar.getBounds().y + bar.getBounds().height + RADIUS / 2;
    }

    public void isPlaying()
    {
        playing = !playing;
    }

    // Bóng di chuyển: dùng vector vận tốc từ góc, phản xạ theo bán kính
    private static final float MAX_BOUNCE_DEG = 75f;

    public boolean Move() {

        if(playing == false) return true;

        if (started == false) {
            if (Gdx.input.isKeyPressed(Input.Keys.SPACE))
                started = true;
            else
                return true;
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
        if (x <= RADIUS) {
            x = RADIUS;
            dx = -dx;
        } else if (x >= WORLD_W - RADIUS) {
            x = WORLD_W - RADIUS;
            dx = -dx;
        }

        // Bật trần
        if (y >= WORLD_H - RADIUS) {
            y = WORLD_H - RADIUS;
            dy = -dy;
        }

        // Rơi khỏi đáy
        if (y <= RADIUS) {
            return false;
        }

        // Va chạm với thanh
        if (bar != null) {
            com.badlogic.gdx.math.Rectangle p = bar.getBounds();
            float paddleTop = p.y + p.height;

            boolean goingDown = (vy < 0);
            boolean crossTop = (prevY - RADIUS >= paddleTop) && (y - RADIUS <= paddleTop);
            boolean overlapX = (x >= p.x - RADIUS) && (x <= p.x + p.width + RADIUS);

            // 1) Bật mép trên thanh (đang đi xuống, cắt qua mép trên)
            if (goingDown && crossTop && overlapX) {
                y = paddleTop + RADIUS / 2;

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
                boolean overlapY = (y + RADIUS >= p.y) && (y - RADIUS <= paddleTop);

                double leftSideX = p.x - RADIUS;
                double rightSideX = p.x + p.width + RADIUS;

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

        return true;
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

        angle_role += ROLE_SPEED * com.badlogic.gdx.Gdx.graphics.getDeltaTime();
        batch.draw(texture, (float)(x - RADIUS/2f), (float)(y - RADIUS/2f), (float)(RADIUS/2f), (float)(RADIUS/2f), (float)RADIUS, (float)RADIUS, 1f, 1f, angle_role, 0, 0, texture.getWidth(), texture.getHeight(), false, false);
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
