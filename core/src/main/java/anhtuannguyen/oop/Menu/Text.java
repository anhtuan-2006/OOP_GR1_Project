package anhtuannguyen.oop.Menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import java.lang.String;

public class Text {
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
    private Texture SPACE = new Texture("Text/space.png");
    private Texture COLON = new Texture("Text/colon.png");

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

    public void renderText(SpriteBatch batch, String text, float x, float y, float scale) {
        float startX = x;
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            Texture charTexture = getTexture(c);
            if (charTexture != null) {
                batch.draw(charTexture, x, y, charTexture.getWidth() * scale, charTexture.getHeight() * scale);
                x += charTexture.getWidth() * scale + 5; // Cộng thêm khoảng cách giữa các chữ cái
            }
        }
    }
}
