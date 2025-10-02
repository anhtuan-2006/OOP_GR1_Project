package anhtuannguyen.oop;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Ball {
    // Các thuộc tính của quả bóng
    private Texture texture;
    public int Vx = 300;
    public int Vy = 300;
    public double x = main.WORLD_W/2; //Cần chuyển hàng số kích thước cửa sổ thành biến static
    public double y = main.WORLD_H/2;
    public double dx = 0;
    public double dy = 0;

    // Tạo bóng
    public void create() {
        texture = new Texture("ball.png");
        
    }

    // Bóng di chuyển
    public void Move() {
        float dt = Gdx.graphics.getDeltaTime();
        dx = Vx * dt;
        dy = Vy * dt;
        x += dx;
        y += dy;
    }
    // Bóng chạm vào tường
    public void Change_Direction() {
        if (x < 0 || x > main.WORLD_W - main.RADIUS*2) {
            Vx = -Vx;
        }
        if (y < 0 || y > main.WORLD_H - main.RADIUS*2) {
            Vy = -Vy;
            }
        
    }
    // Xuất ra màn hình
    public  void render(SpriteBatch batch) {
        batch.draw(texture, (float) x, (float) y, main.RADIUS*2, main.RADIUS*2);
    }

    
    
        
    
}
