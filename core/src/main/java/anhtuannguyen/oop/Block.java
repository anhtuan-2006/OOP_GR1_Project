package anhtuannguyen.oop;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

/**
 * Lớp Block quản lý các khối (Messi) trong game.
 */
public class Block {
    private static final int BLOCK_WIDTH = 128;  // Kích thước khối
    private static final int BLOCK_HEIGHT = 64;
    private static final int GRID_ROWS = 4;     // Số hàng khối
    private static final int GRID_COLS = 8;     // Số cột khối
    private static final float WORLD_W = 1280f; // Kích thước thế giới
    private static final float WORLD_H = 720f;  // Kích thước thế giới
    private static final String TEXTURE_PATH_1 = "Lionel.PNG"; // Đường dẫn ảnh 1
    private static final String TEXTURE_PATH_2 = "7ta.PNG";    // Đường dẫn ảnh 2
    private static final String COLOR = "Yellow"; // Màu đại diện (nếu cần)

    private Rectangle rect;     // Vị trí + kích thước
    private boolean alive;      // Khối còn sống?
    private Texture texture;    // Hình ảnh khối
    private static List<Block> blocks; // Danh sách tất cả khối

    // Constructor cho một khối
    public Block(int x, int y, boolean useFirstTexture) {
        rect = new Rectangle(x, y, BLOCK_WIDTH, BLOCK_HEIGHT);
        alive = true;
        texture = new Texture(useFirstTexture ? TEXTURE_PATH_1 : TEXTURE_PATH_2);
    }

    // Khởi tạo lưới khối
    public static void initializeBlocks() {
        blocks = new ArrayList<>();
        float startX = (WORLD_W - GRID_COLS * BLOCK_WIDTH) / 2; // Căn giữa lưới
        float startY = WORLD_H - GRID_ROWS * BLOCK_HEIGHT - 50; // Đặt phía trên

        for (int row = 0; row < GRID_ROWS; row++) {
            for (int col = 0; col < GRID_COLS; col++) {
                float x = startX + col * BLOCK_WIDTH;
                float y = startY + row * BLOCK_HEIGHT;
                // Xen kẽ texture: hàng chẵn dùng Lionel.PNG, hàng lẻ dùng 7ta.PNG
                boolean useFirstTexture = (row % 2 == 0);
                blocks.add(new Block((int) x, (int) y, useFirstTexture));
            }
        }
    }

    // Vẽ tất cả khối còn sống
    public static void renderBlocks(SpriteBatch batch) {
        for (Block block : blocks) {
            block.render(batch);
        }
    }

    // Xử lý va chạm với bóng (toàn bộ logic va chạm)
    public static void checkAndHandleCollisions(float ballX, float ballY, float ballRadius) {
        Rectangle ballRect = new Rectangle(
            ballX - ballRadius / 2,
            ballY - ballRadius / 2,
            ballRadius,
            ballRadius
        );
        for (Block block : blocks) {
            if (block.alive && block.rect.overlaps(ballRect)) {
                block.alive = false; // Khối biến mất
                System.out.println("Va chạm! Khối biến mất! (Xử lý trong Block.java)");
            }
        }
    }

    // Vẽ một khối
    private void render(SpriteBatch batch) {
        if (alive) {
            batch.draw(texture, rect.x, rect.y, rect.width, rect.height);
        }
    }

    // Giải phóng tài nguyên
    public static void disposeBlocks() {
        for (Block block : blocks) {
            block.dispose();
        }
    }

    // Giải phóng texture của một khối
    private void dispose() {
        if (texture != null) {
            texture.dispose();
        }
    }
}