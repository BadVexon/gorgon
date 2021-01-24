package theGorgon.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theGorgon.GorgonMod;
import theGorgon.util.TextureLoader;

public class MirrorShockPower extends TwoAmountPower implements CloneablePowerInterface {
    public static final String POWER_ID = GorgonMod.makeID("MirrorShockPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final Texture tex84 = TextureLoader.getTexture("gorgonmodResources/images/powers/MirrorShock_power32.png");
    private static final Texture tex32 = TextureLoader.getTexture("gorgonmodResources/images/powers/MirrorShock_power84.png");

    public MirrorShockPower(final int amount, final int amount2) {
        name = NAME;
        ID = POWER_ID;

        this.owner = AbstractDungeon.player;
        this.amount = amount;
        this.amount2 = amount2;

        type = PowerType.BUFF;
        isTurnBased = false;


        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    @Override
    public void atStartOfTurnPostDraw() {
        this.flash();
        AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(this.amount));
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(this.owner, this.amount));
        AbstractDungeon.actionManager.addToBottom(new LoseHPAction(this.owner, this.owner, this.amount2));
    }

    @Override
    public AbstractPower makeCopy() {
        return new MirrorShockPower(amount, amount2);
    }

    @Override
    public void updateDescription() {
        StringBuilder sb = new StringBuilder();
        sb.append(powerStrings.DESCRIPTIONS[0]);
        sb.append(this.amount2);
        sb.append(powerStrings.DESCRIPTIONS[1]);

        for (int i = 0; i < this.amount; ++i) {
            sb.append("[E] ");
        }

        sb.append(powerStrings.DESCRIPTIONS[2]);
        sb.append(this.amount);
        if (this.amount == 1) {
            sb.append(powerStrings.DESCRIPTIONS[3]);
        } else {
            sb.append(powerStrings.DESCRIPTIONS[4]);
        }
        this.description = sb.toString();
    }
}