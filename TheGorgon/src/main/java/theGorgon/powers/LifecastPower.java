package theGorgon.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.patches.core.AbstractCreature.TempHPField;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.OnLoseTempHpPower;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theGorgon.GorgonMod;
import theGorgon.util.TextureLoader;

public class LifecastPower extends AbstractPower implements CloneablePowerInterface, OnLoseTempHpPower {
    public static final String POWER_ID = GorgonMod.makeID("LifecastPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final Texture tex84 = TextureLoader.getTexture("gorgonmodResources/images/powers/Lifecast_power84.png");
    private static final Texture tex32 = TextureLoader.getTexture("gorgonmodResources/images/powers/Lifecast_power32.png");

    public LifecastPower(final int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = AbstractDungeon.player;
        this.amount = amount;

        type = PowerType.BUFF;
        isTurnBased = false;
        priority = 99;


        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    @Override
    public int onLoseHp(int damageAmount) {
        if (damageAmount > 0) {
            this.flash();
            AbstractDungeon.actionManager.addToTop(new GainEnergyAction(1));
            AbstractDungeon.actionManager.addToTop(new DrawCardAction(this.owner, this.amount));
        }
        return damageAmount;
    }

    @Override
    public int onLoseTempHp(DamageInfo info, int damageAmount) {
        if (damageAmount > 0 && !(TempHPField.tempHp.get(AbstractDungeon.player) < damageAmount)) {
            this.flash();
            AbstractDungeon.actionManager.addToTop(new GainEnergyAction(1));
            AbstractDungeon.actionManager.addToTop(new DrawCardAction(this.owner, this.amount));
        }
        return damageAmount;
    }

    public void atEndOfRound() {
        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
    }

    @Override
    public AbstractPower makeCopy() {
        return new LifecastPower(amount);
    }

    @Override
    public void updateDescription() {
        StringBuilder sb = new StringBuilder();
        sb.append(powerStrings.DESCRIPTIONS[0]);

        for (int i = 0; i < this.amount; ++i) {
            sb.append("[E] ");
        }

        sb.append(powerStrings.DESCRIPTIONS[1]);
        sb.append(this.amount);
        if (this.amount == 1) {
            sb.append(powerStrings.DESCRIPTIONS[2]);
        } else {
            sb.append(powerStrings.DESCRIPTIONS[3]);
        }
        this.description = sb.toString();
    }
}