

package anhtuannguyen.oop.Level;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import anhtuannguyen.oop.Menu.Pause;
import anhtuannguyen.oop.Menu.Screen;
import anhtuannguyen.oop.Object.Ball;
import anhtuannguyen.oop.Object.Bar;
import anhtuannguyen.oop.Object.Block;

/**
 * Màn chơi Level 4.
 * Kế thừa từ LevelBase để dùng chung logic cơ bản.
 */
public class Level4 extends LevelBase {

    // ====== Hằng số màn hình ======
    private int mapnumber = 4;
    private static final float WORLD_W = Screen.WORLD_W;
    private static final float WORLD_H = Screen.WORLD_H;

    // ====== Dữ liệu bản đồ ======
    // Bản đồ khối: 1 = khối thường, 2 = khối sắt, 3 = khối di chuyển, 0 = trống
    private static int[][] map = {
        { 0, 0, 2, 2, 2, 2, 2, 2, 0, 0 },
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 1, 0, 1, 1, 0, 1, 1, 0, 1, 1 },
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 1, 1, 0, 3, 0, 0, 0, 3, 0, 1 },
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 1, 0, 1, 1, 0, 1, 1, 0, 1, 1 },
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 1, 1, 0, 3, 0, 0, 0, 3, 0, 1 },
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 1, 0, 1, 1, 0, 1, 1, 0, 1, 1 },
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
    };
   // ====== Constructor ======
    public Level4(Pause pause) {
        super(pause); // gọi constructor của LevelBase
    }

    // ====== Hàm khởi tạo ======
    @Override
    public int getMapNumber() {
        return mapnumber;
    }
    public void create() {
        // Nền
        background = new Texture("Background_Level" + mapnumber + ".png");
        ballTexture = new Texture("Ball_Level"+ mapnumber + ".png");
        barTexture = new Texture("Bar_Level" + mapnumber + ".png");
        blockTexture = new Texture("Block_Level" + mapnumber +".png");
        ironblockTexture = new Texture("iron_block_Lv"+ mapnumber + ".png");
        // Thanh chắn
        bar = new Bar(
            WORLD_W / 2 - 150,
            200,
            300,
            30,
            barTexture
        );

        // Khởi tạo bóng
        Ball b = new Ball(bar, ballTexture);
        b.started = false;
        ball.add(b);

        int ROW = map[0].length;
        int COL = map.length;
        // Khởi tạo khối
        block = new Block(0, 0, ball, ROW, COL, map, (int) WORLD_W/ROW, (int) WORLD_W/ROW * 64 / 100, blockTexture);
        ironblock = new Block(0, 0, ball, ROW, COL, map, (int) WORLD_W/ROW, (int) WORLD_W/ROW * 64 / 100, ironblockTexture);
        movingBlock = new Block(0, 0, ball, ROW, COL, map, (int) WORLD_W/ROW, (int) WORLD_W/ROW * 64 / 100, blockTexture);
        
        // Gán texture cho từng loại block
        block.initializeBlocks(1, blockTexture);
        ironblock.initializeBlocks(2, ironblockTexture);
        movingBlock.initializeBlocks(3, blockTexture);
    }

    // ====== Hàm vẽ ======
    @Override
    public void render(SpriteBatch batch) {
        super.updateGameplay(); // dùng logic chung trong LevelBase

        // Vẽ nền
        batch.draw(background, 0, 0, WORLD_W, WORLD_H);

        // Vẽ mọi thành phần (bóng, thanh chắn, khối, điểm, mạng)
        super.renderAll(batch);
    }
}

