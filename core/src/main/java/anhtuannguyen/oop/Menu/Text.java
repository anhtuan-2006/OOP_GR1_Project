package anhtuannguyen.oop.Menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

/**
 * Lớp {@code Text} dùng để hiển thị văn bản trong game bằng hình ảnh từng ký tự.
 * <p>
 * Mỗi chữ cái, số và ký tự đặc biệt được lưu trong một {@link Texture} riêng biệt.
 * Lớp này cho phép:
 * <ul>
 *   <li>Vẽ văn bản tại vị trí xác định</li>
 *   <li>Căn giữa văn bản trên trục X</li>
 *   <li>Giải phóng tài nguyên hình ảnh khi không còn sử dụng</li>
 * </ul>
 */
public class Text {

    // Texture của các ký tự số
    private Texture ZERO = new Texture("0.png");
    private Texture ONE = new Texture("1.png");
    private Texture TWO = new Texture("2.png");
    private Texture THREE = new Texture("3.png");
    private Texture FOUR = new Texture("4.png");
    private Texture FIVE = new Texture("5.png");
    private Texture SIX = new Texture("6.png");
    private Texture SEVEN = new Texture("7.png");
    private Texture EIGHT = new Texture("8.png");
    private Texture NINE = new Texture("9.png");

    // Texture của các ký tự chữ cái A–Z
    private Texture A = new Texture("Text/A.png");
    private Texture B = new Texture("Text/B.png");
    private Texture C = new Texture("Text/C.png");
    private Texture D = new Texture("Text/D.png");
    private Texture E = new Texture("Text/E.png");
    private Texture F = new Texture("Text/F.png");
    private Texture G = new Texture("Text/G.png");
    private Texture H = new Texture("Text/H.png");
    private Texture I = new Texture("Text/I.png");
    private Texture J = new Texture("Text/J.png");
    private Texture K = new Texture("Text/K.png");
    private Texture L = new Texture("Text/L.png");
    private Texture M = new Texture("Text/M.png");
    private Texture N = new Texture("Text/N.png");
    private Texture O = new Texture("Text/O.png");
    private Texture P = new Texture("Text/P.png");
    private Texture Q = new Texture("Text/Q.png");
    private Texture R = new Texture("Text/R.png");
    private Texture S = new Texture("Text/S.png");
    private Texture T = new Texture("Text/T.png");
    private Texture U = new Texture("Text/U.png");
    private Texture V = new Texture("Text/V.png");
    private Texture W = new Texture("Text/W.png");
    private Texture X = new Texture("Text/X.png");
    private Texture Y = new Texture("Text/Y.png");
    private Texture Z = new Texture("Text/Z.png");

    // Texture của các ký tự đặc biệt
    private Texture SPACE = new Texture("Text/space.png");
    private Texture COLON = new Texture("Text/colon.png");

    // Vùng vẽ ký tự hiện tại
    private Rectangle bounds = new Rectangle(0, 0, 100, 100);

    /**
     * Trả về {@link Texture} tương ứng với ký tự được truyền vào.
     *
     * @param c ký tự cần lấy hình ảnh
     * @return texture tương ứng hoặc {@code null} nếu không tồn tại
     */
    public Texture getTexture(char c) {
        switch (c) {
            case 'A': return A;
            case 'B': return B;
            case 'C': return C;
            case 'D': return D;
            case 'E': return E;
            case 'F': return F;
            case 'G': return G;
            case 'H': return H;
            case 'I': return I;
            case 'J': return J;
            case 'K': return K;
            case 'L': return L;
            case 'M': return M;
            case 'N': return N;
            case 'O': return O;
            case 'P': return P;
            case 'Q': return Q;
            case 'R': return R;
            case 'S': return S;
            case 'T': return T;
            case 'U': return U;
            case 'V': return V;
            case 'W': return W;
            case 'X': return X;
            case 'Y': return Y;
            case 'Z': return Z;
            case '0': return ZERO;
            case '1': return ONE;
            case '2': return TWO;
            case '3': return THREE;
            case '4': return FOUR;
            case '5': return FIVE;
            case '6': return SIX;
            case '7': return SEVEN;
            case '8': return EIGHT;
            case '9': return NINE;
            case ' ': return SPACE;
            case ':': return COLON;
            default: return null;
        }
    }

    /**
     * Vẽ chuỗi văn bản tại vị trí xác định trên màn hình.
     *
     * @param batch đối tượng {@link SpriteBatch} dùng để vẽ
     * @param text chuỗi văn bản cần hiển thị
     * @param x vị trí X bắt đầu vẽ
     * @param y vị trí Y của văn bản
     * @param Size chiều cao mong muốn của ký tự
     * @param scale độ giãn giữa các ký tự
     */
    public void renderText(SpriteBatch batch, String text, float x, float y, float Size, float scale) {
        bounds.y = y;
        bounds.height = (int) Size;

        for (int i = 0; i < text.length(); i++) {
            bounds.x = x;
            char c = text.charAt(i);
            Texture charTexture = getTexture(c);

            if (charTexture != null) {
                // Giữ tỷ lệ gốc của ký tự theo chiều cao
                bounds.width = (int) ((1.0 * charTexture.getWidth() / charTexture.getHeight()) * bounds.height);

                // Vẽ ký tự
                batch.draw(charTexture, bounds.x, bounds.y, bounds.width, bounds.height);

                // Dịch chuyển vị trí vẽ cho ký tự kế tiếp
                x += bounds.width + scale * 2 + 5;
            }
        }
    }

    /**
     * Vẽ chuỗi văn bản được căn giữa theo trục X.
     * <p>
     * Nếu chuỗi rỗng, mặc định hiển thị "YOUR NAME".
     *
     * @param batch đối tượng {@link SpriteBatch} dùng để vẽ
     * @param text chuỗi văn bản cần hiển thị
     * @param centerX tọa độ X trung tâm của vùng cần căn giữa
     * @param y vị trí Y để vẽ văn bản
     * @param Size chiều cao mong muốn của ký tự
     * @param scale độ giãn giữa các ký tự
     */
    public void renderTextName(SpriteBatch batch, String text, float centerX, float y, float Size, float scale) {
        if (text.length() == 0) text = "YOUR NAME";

        // Tính tổng chiều rộng của toàn bộ chuỗi
        float totalWidth = 0;
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            Texture charTexture = getTexture(c);
            if (charTexture != null) {
                float charWidth = (1.0f * charTexture.getWidth() / charTexture.getHeight()) * Size;
                totalWidth += charWidth + scale * 2 + 5;
            }
        }
        totalWidth -= (scale * 2 + 5); // Loại bỏ khoảng cách dư ở cuối

        // Xác định vị trí bắt đầu để căn giữa
        float startX = centerX - totalWidth / 2;

        // Vẽ văn bản tại vị trí đã căn chỉnh
        renderText(batch, text, startX, y, Size, scale);
    }

    /**
     * Giải phóng tài nguyên của tất cả {@link Texture} để tránh rò rỉ bộ nhớ.
     * <p>
     * Cần gọi phương thức này khi màn hình hoặc game kết thúc.
     */
    public void dispose() {
        ZERO.dispose(); ONE.dispose(); TWO.dispose(); THREE.dispose(); FOUR.dispose();
        FIVE.dispose(); SIX.dispose(); SEVEN.dispose(); EIGHT.dispose(); NINE.dispose();

        A.dispose(); B.dispose(); C.dispose(); D.dispose(); E.dispose(); F.dispose();
        G.dispose(); H.dispose(); I.dispose(); J.dispose(); K.dispose(); L.dispose();
        M.dispose(); N.dispose(); O.dispose(); P.dispose(); Q.dispose(); R.dispose();
        S.dispose(); T.dispose(); U.dispose(); V.dispose(); W.dispose(); X.dispose();
        Y.dispose(); Z.dispose(); SPACE.dispose(); COLON.dispose();
    }
}
