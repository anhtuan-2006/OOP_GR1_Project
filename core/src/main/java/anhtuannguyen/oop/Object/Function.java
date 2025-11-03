package anhtuannguyen.oop.Object;

import java.util.Random;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import anhtuannguyen.oop.Menu.Screen;

/**
 * Lớp Function đại diện cho các vật phẩm rơi xuống khi phá vỡ block.
 * Mỗi Function có một loại hiệu ứng khác nhau tác động lên Bar hoặc Ball khi được nhặt.
 * 
 * Các loại Function:
 * 1 - Nhân đôi bóng (MultiBall)
 * 2 - Bóng lửa (FireBall)
 * 3 - Thanh dài hơn (LongBar)
 * 4 - Bóng to hơn (BigBall)
 * 5 - Bóng dính thanh (StickyBall)
 */
public class Function extends Object {
    private static final float WORLD_H = Screen.WORLD_H;
    private static final float WORLD_W = Screen.WORLD_W;

    private int SPEED = 3;           // Tốc độ rơi của vật phẩm
    private Texture texture;         // Ảnh hiện tại
    boolean alive = true;            // Còn tồn tại hay không
    Bar bar;                         // Thanh đỡ mà Function có thể va chạm
    Ball ball;                       // Quả bóng liên kết
    private int RADIUS = 30;         // Bán kính vật phẩm rơi

    // Các ảnh cho hiệu ứng MultiBall
    private Texture Mulball1 = new Texture("Mulball1.png");
    private Texture Mulball2 = new Texture("Mulball2.png");
    private Texture Mulball3 = new Texture("Mulball3.png");

    // Các ảnh cho hiệu ứng LongBar
    private Texture LongBar1 = new Texture("LongBar1.png");
    private Texture LongBar2 = new Texture("LongBar2.png");
    private Texture LongBar3 = new Texture("LongBar3.png");

    // Các ảnh cho hiệu ứng BigBall
    private Texture Bigball1 = new Texture("Bigball1.png");
    private Texture Bigball2 = new Texture("Bigball2.png");
    private Texture Bigball3 = new Texture("Bigball3.png");

    // Các ảnh cho hiệu ứng StickyBall
    private Texture SkipBall1 = new Texture("SkipBall1.png");
    private Texture SkipBall2 = new Texture("SkipBall2.png");
    private Texture SkipBall3 = new Texture("SkipBall3.png");

    private int tex = 1; // Dùng để thay đổi frame ảnh (1,2,3) giúp tạo hiệu ứng động

    private Texture FireBall = new Texture("FireBall.png"); // Ảnh cho hiệu ứng FireBall

    Random rand = new Random();
    private int type = rand.nextInt(5) + 1; // Chọn ngẫu nhiên loại hiệu ứng (1-5)
    
    /**
     * Hàm khởi tạo Function.
     * 
     * @param _x     Tọa độ X ban đầu.
     * @param _y     Tọa độ Y ban đầu.
     * @param _ball  Quả bóng gắn với Function.
     */
    Function(float _x, float _y, Ball _ball) {
        x = _x;
        y = _y;
        ball = _ball;
        bar = ball.bar;
    }

    /**
     * Cập nhật vị trí vật phẩm khi rơi và kiểm tra va chạm với thanh đỡ.
     * 
     * @return Trả về mã loại hiệu ứng nếu va chạm thanh, 0 nếu chưa va chạm.
     */
    public int Move() {
        y = y - SPEED; // Vật phẩm rơi xuống theo tốc độ cố định
       
        // Lấy vùng va chạm của thanh đỡ
        Rectangle p = bar.getBounds();

        // Kiểm tra nếu vật phẩm chạm thanh
        if (y - RADIUS <= p.y + p.height && x + RADIUS >= p.x && x - RADIUS <= p.x + p.width) {
            alive = false;
            return type;
        }

        // Nếu rơi khỏi màn hình
        if (y - RADIUS <= 0) {
            alive = false;
        }

        return 0;
    }

    /**
     * Vẽ vật phẩm ra màn hình theo loại hiệu ứng.
     * Có animation 3 khung hình cho các hiệu ứng không phải FireBall.
     */
    public void render(SpriteBatch batch) {
        if (type == 1) { // MultiBall
            if (tex == 1)
                batch.draw(Mulball1, x - RADIUS, y - RADIUS, RADIUS * 2, RADIUS * 2);
            if (tex == 2)
                batch.draw(Mulball2, x - RADIUS, y - RADIUS, RADIUS * 2, RADIUS * 2);
            if (tex == 3)
                batch.draw(Mulball3, x - RADIUS, y - RADIUS, RADIUS * 2, RADIUS * 2);
            tex++;
            if (tex > 3)
                tex -= 3;
        }

        else if (type == 2) { // FireBall
            batch.draw(FireBall, x - RADIUS, y - RADIUS, RADIUS * 2, RADIUS * 2);
        }

        else if (type == 3) { // LongBar
            if (tex == 1)
                batch.draw(LongBar1, x - RADIUS, y - RADIUS, RADIUS * 2, RADIUS * 2);
            if (tex == 2)
                batch.draw(LongBar2, x - RADIUS, y - RADIUS, RADIUS * 2, RADIUS * 2);
            if (tex == 3)
                batch.draw(LongBar3, x - RADIUS, y - RADIUS, RADIUS * 2, RADIUS * 2);
            tex++;
            if (tex > 3)
                tex -= 3;
        }
        else if (type == 4) { // BigBall
            if (tex == 1)
                batch.draw(Bigball1, x - RADIUS, y - RADIUS, RADIUS * 2, RADIUS * 2);
            if (tex == 2)
                batch.draw(Bigball2, x - RADIUS, y - RADIUS, RADIUS * 2, RADIUS * 2);
            if (tex == 3)
                batch.draw(Bigball3, x - RADIUS, y - RADIUS, RADIUS * 2, RADIUS * 2);
            tex++;
            if (tex > 3)
                tex -= 3;
        }
        else if (type == 5) { // StickyBall
            if (tex == 1)
                batch.draw(SkipBall1, x - RADIUS, y - RADIUS, RADIUS * 2, RADIUS * 2);
            if (tex == 2)
                batch.draw(SkipBall2, x - RADIUS, y - RADIUS, RADIUS * 2, RADIUS * 2);
            if (tex == 3)
                batch.draw(SkipBall3, x - RADIUS, y - RADIUS, RADIUS * 2, RADIUS * 2);
            tex++;
            if (tex > 3)
                tex -= 3;
        }
    }
}
