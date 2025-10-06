package anhtuannguyen.oop;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.*;
import java.lang.*;

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

    Ball(Bar _bar) {
        bar = _bar;
    }

    // Tạo bóng
    public void create() {
        texture = new Texture("ball.png");
    }

    // Bóng di chuyển: dùng vector vận tốc từ góc, phản xạ theo bán kính
    private static final float MAX_BOUNCE_DEG = 75f;

    public boolean Move() {
        float dt = Gdx.graphics.getDeltaTime();

        // Lưu vị trí trước bước cập nhật (để kiểm tra cắt biên trên thanh)
        double prevY = y;

        // Vận tốc từ góc hiện tại, giữ nguyên cơ chế dấu bằng dx, dy
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

        if (y <= RADIUS) {
            return false;
        }

        // Bật theo thanh (nếu có)
        if (bar != null) {
            com.badlogic.gdx.math.Rectangle p = bar.getBounds(); // cần hàm này trong Bar
            float paddleTop = p.y + p.height;

            // Đang đi xuống và cắt qua mép trên thanh giữa 2 khung hình
            boolean goingDown = (vy < 0);
            boolean crossTop = (prevY - RADIUS >= paddleTop) && (y - RADIUS <= paddleTop);
            boolean overlapX = (x >= p.x - RADIUS) && (x <= p.x + p.width + RADIUS);

            if (goingDown && crossTop && overlapX) {
                // Đặt bóng ngay trên thanh để tránh dính
                y = paddleTop + RADIUS;

                // Vị trí chạm tương đối trên thanh [-1, 1]
                float paddleCenter = p.x + p.width / 2f;
                float hitRel = (float) ((x - paddleCenter) / (p.width / 2f));

                // Góc bật ra: 90° ± MAX_BOUNCE_DEG (90° là thẳng lên)
                float outDeg = (float) Math.toDegrees(angle) - hitRel * MAX_BOUNCE_DEG;
                if (outDeg >= 160)
                    outDeg = 165f;
                if (outDeg <= 20)
                    outDeg = 15f;
                angle = Math.toRadians(outDeg);

                // Hướng đi lên sau va chạm
                dy = +1;
            }
        }

        // Chuẩn hóa góc về [0, 2π)
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

    // Xuất ra màn hình
    public void render(SpriteBatch batch) {
        batch.draw(texture, (int) (x - RADIUS / 2), (int) (y - RADIUS / 2), RADIUS, RADIUS);
    }

}
