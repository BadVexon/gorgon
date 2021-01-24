package theGorgon.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.MindblastEffect;
import theGorgon.GorgonMod;
import theGorgon.characters.TheGorgon;

import static theGorgon.GorgonMod.makeCardPath;

public class EyeBeam extends AbstractGorgonCard {

    public static final String ID = GorgonMod.makeID("EyeBeam");
    public static final String IMG = makeCardPath("EyeBeam.png");
    public static final CardColor COLOR = TheGorgon.Enums.COLOR_PURPLE;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    private static final int COST = 2;

    public EyeBeam() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
    }

    public void applyPowers() {
        if (AbstractDungeon.player instanceof TheGorgon) {
            this.baseDamage = ((TheGorgon) AbstractDungeon.player).deathgaze;// 77
            super.applyPowers();// 78
            this.rawDescription = DESCRIPTION + EXTENDED_DESCRIPTION[0];// 79
            this.initializeDescription();// 80
        }

    }// 82

    public void onMoveToDiscard() {
        this.rawDescription = DESCRIPTION;// 86
        this.initializeDescription();// 87
    }// 88

    public void calculateCardDamage(AbstractMonster mo) {
        super.calculateCardDamage(mo);// 92
        this.rawDescription = DESCRIPTION;// 94
        this.rawDescription = this.rawDescription + EXTENDED_DESCRIPTION[0];// 95
        this.initializeDescription();// 96
    }// 97

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new SFXAction("ATTACK_HEAVY"));// 46
        AbstractDungeon.actionManager.addToBottom(new VFXAction(p, new MindblastEffect(p.dialogX, p.dialogY, p.flipHorizontal), 0.1F));// 47
        addToBot(new DamageAction(m, makeInfo(p), AbstractGameAction.AttackEffect.FIRE));
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
