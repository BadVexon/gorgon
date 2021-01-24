package theGorgon.cards;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import theGorgon.GorgonMod;
import theGorgon.actions.SightsingAction;
import theGorgon.characters.TheGorgon;

import static theGorgon.GorgonMod.makeCardPath;


public class Sightsing extends AbstractGorgonCard {

    public static final String ID = GorgonMod.makeID("Sightsing");
    public static final String IMG = makeCardPath("Strike.png");
    public static final AbstractCard.CardColor COLOR = TheGorgon.Enums.COLOR_PURPLE;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.UNCOMMON;
    private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.ENEMY;
    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.SKILL;
    private static final int COST = -1;
    public String betaArtPath;

    public Sightsing() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        int blah = MathUtils.random(11) + 1;
        betaArtPath = makeCardPath("Sightsing" + blah + ".png");
        loadCardImage(betaArtPath);
    }

    @Override
    protected Texture getPortraitImage() {
        if (this.textureImg == null) {
            return null;
        } else {
            if (betaArtPath != null) {
                int endingIndex = betaArtPath.lastIndexOf(".");
                String newPath = betaArtPath.substring(0, endingIndex) + "_p" + betaArtPath.substring(endingIndex);
                System.out.println("Finding texture: " + newPath);

                Texture portraitTexture;
                try {
                    portraitTexture = ImageMaster.loadImage(newPath);
                } catch (Exception var5) {
                    portraitTexture = null;
                }

                return portraitTexture;
            }
        }
        return super.getPortraitImage();
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (this.energyOnUse < EnergyPanel.totalCount) {
            this.energyOnUse = EnergyPanel.totalCount;
        }

        AbstractDungeon.actionManager.addToBottom(new SightsingAction(p, m, this.freeToPlayOnce, this.energyOnUse, this.upgraded));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
