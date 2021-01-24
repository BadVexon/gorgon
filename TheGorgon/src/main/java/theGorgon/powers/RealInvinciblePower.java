package theGorgon.powers;

import com.evacipated.cardcrawl.mod.stslib.patches.core.AbstractCreature.TempHPField;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.OnLoseTempHpPower;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class RealInvinciblePower extends AbstractPower implements OnLoseTempHpPower {
    public static final String POWER_ID = "RealInvincible";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    public int maxAmt;

    public RealInvinciblePower(AbstractCreature owner, int amount) {
        this.name = NAME;// 16
        this.ID = "RealInvincible";// 17
        this.owner = owner;// 18
        this.amount = amount;// 19
        this.maxAmt = amount;// 20
        this.updateDescription();// 21
        this.loadRegion("heartDef");// 22
        this.priority = 99;// 23
    }// 24

    public int onLoseHp(int damageAmount) {
        if (damageAmount > this.amount) {// 39
            damageAmount = this.amount;// 40
        }

        this.amount -= damageAmount;// 42
        if (this.amount < 0) {// 43
            this.amount = 0;// 44
        }

        this.updateDescription();// 47
        return damageAmount;// 48
    }

    public int onLoseTempHp(DamageInfo info, int damageAmount) {
        if (damageAmount > TempHPField.tempHp.get(this.owner)) {
            if (damageAmount > this.amount) {// 39
                damageAmount = this.amount;// 40
            }

            this.amount -= Math.min(TempHPField.tempHp.get(this.owner), damageAmount);// 42
            if (this.amount < 0) {// 43
                this.amount = 0;// 44
            }

            this.updateDescription();// 47
            return damageAmount;// 48
        } else {
            if (damageAmount > this.amount) {// 39
                damageAmount = this.amount;// 40
            }

            this.amount -= damageAmount;// 42
            if (this.amount < 0) {// 43
                this.amount = 0;// 44
            }

            this.updateDescription();// 47
            return damageAmount;// 48
        }
    }

    public void atStartOfTurn() {
        this.amount = this.maxAmt;// 53
        this.updateDescription();// 54
    }// 55

    public void updateDescription() {
        if (this.amount <= 0) {// 59
            this.description = DESCRIPTIONS[2];// 60
        } else {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];// 62
        }

    }// 64

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Invincible");// 10
        NAME = powerStrings.NAME;// 11
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;// 12
    }
}
