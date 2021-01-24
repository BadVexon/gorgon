package theGorgon.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.PoisonPower;
import theGorgon.GorgonMod;
import theGorgon.util.TextureLoader;

import static theGorgon.GorgonMod.makeRelicOutlinePath;
import static theGorgon.GorgonMod.makeRelicPath;

public class SapphireLocks extends CustomRelic {

    public static final String ID = GorgonMod.makeID("SapphireLocks");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("Amulet.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("Amulet.png"));

    public SapphireLocks() {
        super(ID, IMG, OUTLINE, RelicTier.STARTER, LandingSound.FLAT);
    }

    @Override
    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
        if (damageAmount > 0 && target != AbstractDungeon.player && info.type == DamageInfo.DamageType.NORMAL) {
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(target, AbstractDungeon.player, new PoisonPower(target, AbstractDungeon.player, 1), 1, true));
        }
    }

    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
