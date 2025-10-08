package anhtuannguyen.oop;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Block {
    private static int BLOCK_WIDTH; // Chiều rộng của một khối
    private static int BLOCK_HEIGHT; // Chiều cao của một khối
    private static int GRID_ROWS; // Số hàng trong lưới khối
    private static int GRID_COLS; // Số cột trong lưới khối
    private static final int MAP_SIZE = 64; // Kích thước bản đồ (số ô tối đa mỗi chiều)
    private static final float WORLD_W = Screen.WORLD_W; // Chiều rộng thế giới game
    private static final float WORLD_H = Screen.WORLD_H; // Chiều cao thế giới game
    private static int[][] map;
    private Rectangle rect; // Hình chữ nhật cho vị trí và kích thước khối
    private boolean alive; // Trạng thái khối (còn tồn tại hay không)
    private Texture texture; // Texture để vẽ khối
    private static Block[][] blocks; // Mảng 2D lưu trữ các khối
    private Ball ball;

    public Block(int x, int y, Ball _ball, int ROW, int COL, int[][] _map, int width, int height) { // Constructor: Khởi
                                                                                                    // tạo khối
        rect = new Rectangle(x, y, BLOCK_WIDTH, BLOCK_HEIGHT);
        alive = true;
        ball = _ball;
        GRID_COLS = COL;
        GRID_ROWS = ROW;
        map = _map;
        BLOCK_HEIGHT = height;
        BLOCK_WIDTH = width;
    }

    Block(int x, int y, int level) {
        rect = new Rectangle(x, y, BLOCK_WIDTH, BLOCK_HEIGHT);
        alive = true;
        if (level == 1)
            texture = new Texture("block_level1.jpg"); // Chọn texture
    }

    public static void initializeBlocks(int level) { // Khởi tạo lưới khối dựa trên bản đồ
        blocks = new Block[MAP_SIZE][MAP_SIZE]; // Khởi tạo mảng 2D
        float startX = (WORLD_W - GRID_COLS * BLOCK_WIDTH) / 2; // Tính vị trí X căn giữa
        float startY = WORLD_H - GRID_ROWS * BLOCK_HEIGHT - 200; // Tính vị trí Y
        for (int row = 0; row < GRID_ROWS; row++) {
            for (int col = 0; col < GRID_COLS; col++) {
                if (map[row][col] == 1) { // Tạo khối nếu map có giá trị 1
                    float x = startX + col * BLOCK_WIDTH;
                    float y = startY + row * BLOCK_HEIGHT;
                    blocks[row][col] = new Block((int) x, (int) y, level);
                }
            }
        }
    }

    public static void renderBlocks(SpriteBatch batch) { // Vẽ tất cả khối còn tồn tại
        for (int row = 0; row < MAP_SIZE; row++) {
            for (int col = 0; col < MAP_SIZE; col++) {
                if (blocks[row][col] != null && blocks[row][col].alive) { // Vẽ khối nếu còn sống
                    blocks[row][col].render(batch);
                }
            }
        }
    }

    private void pullBall(Rectangle bal, Rectangle block) {
        // Tính biên
        float balLeft = bal.x;
        float balRight = bal.x + bal.width;
        float balBottom = bal.y;
        float balTop = bal.y + bal.height;

        float blkLeft = block.x;
        float blkRight = block.x + block.width;
        float blkBottom = block.y;
        float blkTop = block.y + block.height;

        // Độ chồng lấn theo từng trục
        float overlapX = Math.min(balRight, blkRight) - Math.max(balLeft, blkLeft);
        float overlapY = Math.min(balTop, blkTop) - Math.max(balBottom, blkBottom);

        if (overlapX <= 0 || overlapY <= 0)
            return; // an toàn

        if (overlapX < overlapY) {
            // Va chạm theo trục X (mặt trái/phải của block)
            if (bal.x + bal.width / 2f < block.x + block.width / 2f) {
                // Bóng ở bên trái khối -> đẩy sang trái và lật X (đụng "right wall")
                bal.x = blkLeft - bal.width;
                ball.Change_Direction(2);
            } else {
                // Bóng ở bên phải khối -> đẩy sang phải và lật X (đụng "left wall")
                bal.x = blkRight;
                ball.Change_Direction(1);
            }
        } else {
            // Va chạm theo trục Y (mặt trên/dưới của block)
            if (bal.y + bal.height / 2f < block.y + block.height / 2f) {
                // Bóng dưới khối -> đẩy xuống và lật Y (đụng "up wall")
                bal.y = blkBottom - bal.height;
                ball.Change_Direction(3);
            } else {
                // Bóng trên khối -> đẩy lên và lật Y (đụng "down wall")
                bal.y = blkTop;
                ball.Change_Direction(4);
            }
        }
    }

    public void checkAndHandleCollisions(float ballX, float ballY, float ballRadius) { // Kiểm tra va chạm
        Rectangle ballRect = new Rectangle(ballX - ballRadius / 2, ballY - ballRadius / 2, ballRadius, ballRadius);
        for (int row = 0; row < GRID_ROWS; row++) {
            for (int col = 0; col < GRID_COLS; col++) {
                Block block = blocks[row][col];
                if (block != null && block.alive && block.rect.overlaps(ballRect)) { // Nếu khối va chạm với bóng
                    pullBall(ballRect, block.rect);
                    block.alive = false; // Đánh dấu khối bị phá hủy
                    blocks[row][col] = null; // Xóa khối khỏi mảng
                }
            }
        }
    }

    private void render(SpriteBatch batch) { // Vẽ một khối
        if (alive) {
            batch.draw(texture, rect.x, rect.y, rect.width, rect.height); // Vẽ texture nếu khối còn sống
        }
    }

    public static void disposeBlocks() { // Giải phóng tài nguyên tất cả khối
        for (int row = 0; row < MAP_SIZE; row++) {
            for (int col = 0; col < MAP_SIZE; col++) {
                if (blocks[row][col] != null) { // Giải phóng nếu khối tồn tại
                    blocks[row][col].dispose();
                    blocks[row][col] = null;
                }
            }
        }
    }

    public void dispose() { // Giải phóng texture của một khối
        if (texture != null) {
            texture.dispose();
        }
    }
}