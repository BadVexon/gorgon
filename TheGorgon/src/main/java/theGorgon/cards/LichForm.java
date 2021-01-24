package theGorgon.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theGorgon.GorgonMod;
import theGorgon.characters.TheGorgon;
import theGorgon.powers.RealInvinciblePower;

import static theGorgon.GorgonMod.makeCardPath;

public class LichForm extends AbstractGorgonCard {

    public static final String ID = GorgonMod.makeID("LichForm");
    public static final String IMG = makeCardPath("LichForm.png");
    public static final AbstractCard.CardColor COLOR = TheGorgon.Enums.COLOR_PURPLE;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final AbstractCard.CardRarity RARITY = CardRarity.RARE;
    private static final AbstractCard.CardTarget TARGET = CardTarget.SELF;
    private static final AbstractCard.CardType TYPE = CardType.POWER;
    private static final int COST = 2;

    public LichForm() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (p.hasPower(RealInvinciblePower.POWER_ID)) {
            addToBot(new ApplyPowerAction(p, p, new RealInvinciblePower(p, -3), -3));
            ((RealInvinciblePower)AbstractDungeon.player.getPower(RealInvinciblePower.POWER_ID)).maxAmt -= 3;
        } else {
            addToBot(new ApplyPowerAction(p, p, new RealInvinciblePower(p, 12), 12));
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(1);
            initializeDescription();
        }
    }
}
