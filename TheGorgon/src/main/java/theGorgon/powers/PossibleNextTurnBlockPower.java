package theGorgon.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.patches.core.AbstractCreature.TempHPField;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.OnLoseTempHpPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.NextTurnBlockPower;
import theGorgon.GorgonMod;
import theGorgon.util.TextureLoader;

public class PossibleNextTurnBlockPower extends AbstractPower implements CloneablePowerInterface, OnLoseTempHpPower {
    public static final String POWER_ID = GorgonMod.makeID("PossibleNextTurnBlockPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final Texture tex84 = TextureLoader.getTexture("gorgonmodResources/images/powers/PossibleBlock_84.png");
    private static final Texture tex32 = TextureLoader.getTexture("gorgonmodResources/images/powers/PossibleBlock_32.png");

    public PossibleNextTurnBlockPower(final int amount) {
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

    @Override
    public int onLoseHp(int damageAmount) {
        if (damageAmount > 0) {
            this.flash();
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(this.owner, this.owner, new NextTurnBlockPower(this.owner, this.amount), this.amount));
        }
        return damageAmount;
    }

    @Override
    public int onLoseTempHp(DamageInfo info, int damageAmount) {
        if (damageAmount > 0 && !(TempHPField.tempHp.get(AbstractDungeon.player) < damageAmount)) {
            this.flash();
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(this.owner, this.owner, new NextTurnBlockPower(this.owner, this.amount), this.amount));
        }
        return damageAmount;
    }

    public void atEndOfRound() {
        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
    }

    @Override
    public AbstractPower makeCopy() {
        return new PossibleNextTurnBlockPower(amount);
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }
}