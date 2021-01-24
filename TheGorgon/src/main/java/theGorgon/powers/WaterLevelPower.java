package theGorgon.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.NonStackablePower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theGorgon.GorgonMod;
import theGorgon.util.TextureLoader;

public class WaterLevelPower extends AbstractPower implements CloneablePowerInterface {
    public static final String POWER_ID = GorgonMod.makeID("WaterLevelPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final Texture tex84 = TextureLoader.getTexture("gorgonmodResources/images/powers/WaterLevel_power84.png");
    private static final Texture tex32 = TextureLoader.getTexture("gorgonmodResources/images/powers/WaterLevel_power32.png");

    public WaterLevelPower(final int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = AbstractDungeon.player;
        this.amount = amount;

        type = PowerType.BUFF;
        isTurnBased = false;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    public void atStartOfTurnPostDraw() {
        AbstractDungeon.actionManager.addToBottom(
                new DamageAllEnemiesAction(
                        owner,
                        DamageInfo.createDamageMatrix(amount, true),
                        DamageInfo.DamageType.THORNS,
                        AbstractGameAction.AttackEffect.FIRE
                )
        );
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.owner, this.owner, new WaterLevelPower(5), 5));
    }

    @Override
    public AbstractPower makeCopy() {
        return new WaterLevelPower(amount);
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }
}