package anhtuannguyen.oop;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Block {
    private static final int BLOCK_WIDTH = 128; // Chiều rộng của một khối
    private static final int BLOCK_HEIGHT = 64; // Chiều cao của một khối
    private static final int GRID_ROWS = 4; // Số hàng trong lưới khối
    private static final int GRID_COLS = 8; // Số cột trong lưới khối
    private static final int MAP_SIZE = 64; // Kích thước bản đồ (số ô tối đa mỗi chiều)
    private static final float WORLD_W = 1280f; // Chiều rộng thế giới game
    private static final float WORLD_H = 720f; // Chiều cao thế giới game
    private static final String TEXTURE_PATH_1 = "Lionel.PNG"; // Đường dẫn texture thứ nhất
    private static final String TEXTURE_PATH_2 = "7ta.PNG"; // Đường dẫn texture thứ hai
    private static final String COLOR = "Yellow"; // Màu sắc khối (chưa sử dụng)
    private static final int[][] map = { // Bản đồ tĩnh: 1 = có khối, 0 = không
        {1, 1, 1, 1, 1, 1, 1, 1},
        {1, 1, 1, 1, 1, 1, 1, 1},
        {1, 1, 1, 0, 0, 1, 1, 1},
        {1, 1, 1, 0, 0, 1, 1, 1}
    };
    private Rectangle rect; // Hình chữ nhật cho vị trí và kích thước khối
    private boolean alive; // Trạng thái khối (còn tồn tại hay không)
    private Texture texture; // Texture để vẽ khối
    private static Block[][] blocks; // Mảng 2D lưu trữ các khối

    public Block(int x, int y, boolean useFirstTexture) { // Constructor: Khởi tạo khối
        rect = new Rectangle(x, y, BLOCK_WIDTH, BLOCK_HEIGHT);
        alive = true;
        texture = new Texture(useFirstTexture ? TEXTURE_PATH_1 : TEXTURE_PATH_2); // Chọn texture
    }
    public static void initializeBlocks(){ // Khởi tạo lưới khối dựa trên bản đồ
        blocks = new Block[MAP_SIZE][MAP_SIZE]; // Khởi tạo mảng 2D
        float startX = (WORLD_W - GRID_COLS * BLOCK_WIDTH) / 2; // Tính vị trí X căn giữa
        float startY = WORLD_H - GRID_ROWS * BLOCK_HEIGHT - 50; // Tính vị trí Y
        for (int row = 0; row < GRID_ROWS; row++) {
            for (int col = 0; col < GRID_COLS; col++) {
                if (map[row][col] == 1) { // Tạo khối nếu map có giá trị 1
                    float x = startX + col * BLOCK_WIDTH;
                    float y = startY + row * BLOCK_HEIGHT;
                    boolean useFirstTexture = (row % 2 == 0); // Chọn texture xen kẽ
                    blocks[row][col] = new Block((int) x, (int) y, useFirstTexture);
                }
            }
        }
    }
    public static void renderBlocks(SpriteBatch batch){ // Vẽ tất cả khối còn tồn tại
        for (int row = 0; row < MAP_SIZE; row++) {
            for (int col = 0; col < MAP_SIZE; col++) {
                if (blocks[row][col] != null && blocks[row][col].alive) { // Vẽ khối nếu còn sống
                    blocks[row][col].render(batch);
                }
            }
        }
    }
    public static void checkAndHandleCollisions(float ballX, float ballY, float ballRadius) { // Kiểm tra va chạm
        Rectangle ballRect = new Rectangle(ballX - ballRadius / 2, ballY - ballRadius / 2, ballRadius, ballRadius); // Hình chữ nhật của bóng
        for (int row = 0; row < GRID_ROWS; row++) {
            for (int col = 0; col < GRID_COLS; col++) {
                Block block = blocks[row][col];
                if (block != null && block.alive && block.rect.overlaps(ballRect)) { // Nếu khối va chạm với bóng
                    block.alive = false; // Đánh dấu khối bị phá hủy
                    blocks[row][col] = null; // Xóa khối khỏi mảng
                    System.out.println("Va chạm! Khối biến mất! (Xử lý trong Block.java)");
                }
            }
        }
    }
    private void render(SpriteBatch batch){ // Vẽ một khối
        if (alive) {
            batch.draw(texture, rect.x, rect.y, rect.width, rect.height); // Vẽ texture nếu khối còn sống
        }
    }
    public static void disposeBlocks(){ // Giải phóng tài nguyên tất cả khối
        for (int row = 0; row < MAP_SIZE; row++) {
            for (int col = 0; col < MAP_SIZE; col++) {
                if (blocks[row][col] != null) { // Giải phóng nếu khối tồn tại
                    blocks[row][col].dispose();
                    blocks[row][col] = null;
                }
            }
        }
    }

    private void dispose(){ // Giải phóng texture của một khối
        if (texture != null) {
            texture.dispose();
        }
    }
}