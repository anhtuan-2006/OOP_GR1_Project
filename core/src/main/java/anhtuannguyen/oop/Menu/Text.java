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
    private Texture A = new Texture("A.png");
    private Texture B = new Texture("B.png");
    private Texture C = new Texture("C.png");
    private Texture D = new Texture("D.png");  
    private Texture E = new Texture("E.png");
    private Texture F = new Texture("F.png");
    private Texture G = new Texture("G.png");
    private Texture H = new Texture("H.png");
    private Texture I = new Texture("I.png");
    private Texture J = new Texture("J.png");
    private Texture K = new Texture("K.png");
    private Texture L = new Texture("L.png");
    private Texture M = new Texture("M.png");
    private Texture N = new Texture("N.png");
    private Texture O = new Texture("O.png");
    private Texture P = new Texture("P.png");
    private Texture Q = new Texture("Q.png");
    private Texture R = new Texture("R.png");
    private Texture S = new Texture("S.png");
    private Texture T = new Texture("T.png");
    private Texture U = new Texture("U.png");
    private Texture V = new Texture("V.png");
    private Texture W = new Texture("W.png");
    private Texture X = new Texture("X.png");
    private Texture Y = new Texture("Y.png");
    private Texture Z = new Texture("Z.png");

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
