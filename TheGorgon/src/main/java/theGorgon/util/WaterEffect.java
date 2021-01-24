package theGorgon.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

import java.util.ArrayList;

public class WaterEffect extends AbstractGameEffect {
    private static float RISINGVELOCITY = Settings.HEIGHT / 2F;


    private float phase;
    private float waterY;

    private ArrayList<WaterThing> waves;
    private ArrayList<WaterThing> bubbles;

    float bubbletimer;
    float wavetimer;

    public WaterEffect() {

        this.waves = new ArrayList<>();
        this.bubbles = new ArrayList<>();
        this.waterY = -20 * Settings.scale;
        this.color = Color.BLUE.cpy();
    }

    public void update() {
        this.phase = (this.phase + Gdx.graphics.getDeltaTime()) % ((float) Math.PI * 2);
        this.waterY += (Math.sin(phase) + 0.5F) * RISINGVELOCITY * Gdx.graphics.getDeltaTime();

        for (int i = waves.size() - 1; i >= 0; i--) {
            WaterThing w = waves.get(i);
            if ((w.velocity < 0 && w.x - w.texturewidth / 2 < 0) ||
                    (w.velocity > 0 && w.x + w.texturewidth / 2 > Settings.WIDTH)) {
                waves.remove(i);
            } else {
                w.update();
            }
        }
        wavetimer -= Gdx.graphics.getDeltaTime();
        if (wavetimer <= 0) {
            wavetimer = 1F;
            waves.add(new Wave(TextureLoader.getTexture("gorgonmodResources/images/wave.png")));
        }

        for (int i = bubbles.size() - 1; i >= 0; i--) {
            WaterThing b = bubbles.get(i);
            if (b.a <= 0F) {
                bubbles.remove(i);
            } else {
                b.update();
            }
        }
        bubbletimer -= Gdx.graphics.getDeltaTime();
        if (bubbletimer <= 0) {
            bubbletimer = 0.2F;
            bubbles.add(new Bubble(TextureLoader.getTexture("gorgonmodResources/images/bubble.png")));
        }
    }

    public void render(SpriteBatch sb) {
        sb.setColor(this.color);
        //Render water rectangle here

        /*ArrayList<WaterThing>[] wt = new ArrayList[2];
        wt[0] = waves;
        wt[1] = bubbles;*/
        ArrayList<WaterThing>[] wt = new ArrayList[]{waves, bubbles};


        Color tmp = Color.WHITE.cpy();
        for (final ArrayList<WaterThing> list : wt) {
            for (final WaterThing w : list) {
                tmp.a = w.a;
                sb.setColor(tmp);
                sb.draw(w.img, w.x, w.y, w.img.getWidth(), w.img.getHeight());
            }
        }

        sb.setColor(Color.WHITE.cpy());
    }

    public void dispose() {

    }


    abstract class WaterThing {
        protected Texture img;

        protected float x;
        protected float y;

        protected float a;

        protected float velocity;
        protected float texturewidth;

        public WaterThing(Texture img, float x, float y) {
            this.img = img;
            this.texturewidth = this.img.getWidth() * scale;
            this.x = x;
            this.y = y;
            this.a = 1F;
        }

        abstract void update();
    }

    class Wave extends WaterThing {
        private float phase;

        public Wave(Texture img) {
            super(img, MathUtils.randomBoolean() ? -img.getWidth() * scale : (img.getWidth() * scale + Settings.WIDTH), MathUtils.random(-50F, 50F) * scale);
            this.velocity = MathUtils.random(Settings.WIDTH / 5F, Settings.WIDTH / 3F);
            if (this.x > 0) {
                this.velocity *= -1;
            }
            this.phase = MathUtils.random(0, (float) Math.PI * 2);
        }

        public void update() {
            this.x += this.velocity * Gdx.graphics.getDeltaTime();
            this.phase = (this.phase + Gdx.graphics.getDeltaTime()) % ((float) Math.PI * 2);
            this.a = Math.max(1, (float) Math.sin(this.phase) + 1);
        }
    }

    class Bubble extends WaterThing {
        private float topdistance;

        public Bubble(Texture img) {
            super(img, MathUtils.random(0, Settings.WIDTH), -img.getHeight());
            this.velocity = MathUtils.random(Settings.HEIGHT / 2F, Settings.HEIGHT / 1.5F);
            this.topdistance = MathUtils.random(20F, 50F);
        }

        public void update() {
            this.y += this.velocity * Gdx.graphics.getDeltaTime();
            if (this.y + topdistance > waterY) {
                this.a -= this.velocity / topdistance * Gdx.graphics.getDeltaTime();
            }
        }
    }
}
