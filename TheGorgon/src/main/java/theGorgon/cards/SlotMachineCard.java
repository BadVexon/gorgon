package theGorgon.cards;

import basemod.abstracts.CustomCard;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theGorgon.CardIgnore;
import theGorgon.GorgonMod;
import theGorgon.characters.TheGorgon;

import static theGorgon.GorgonMod.makeCardPath;

@CardIgnore
public class SlotMachineCard extends CustomCard {
    private int TIMER = 10;
    public boolean yes;

    public SlotMachineCard() {
        super("SlotMachineCard", "Slot Machine Card", GorgonMod.makeCardPath("Strike.png"), -2, "You should never see this.", CardType.SKILL, TheGorgon.Enums.COLOR_PURPLE, CardRarity.SPECIAL, CardTarget.NONE);
    }

    @Override
    public void update() {
        super.update();
        if (AbstractDungeon.gridSelectScreen != null) {
            if (!AbstractDungeon.gridSelectScreen.selectedCards.contains(this) && !yes) {
                this.TIMER += Gdx.graphics.getDeltaTime() * 60;
                if (TIMER >= 10) {
                    TIMER = 0;
                    int blah = AbstractDungeon.cardRandomRng.random(6);
                    if (blah == 0) {
                        loadCardImage(makeCardPath("Seven.png"));
                        this.name = "SEVEN!";
                        this.rawDescription = "Deal 7 damage to ALL enemies.";
                        this.initializeDescription();
                    } else if (blah == 1) {
                        loadCardImage(makeCardPath("Six.png"));
                        this.name = "BLOCK!";
                        this.rawDescription = "Gain 6 Block.";
                        this.initializeDescription();
                    } else if (blah == 2) {
                        loadCardImage(makeCardPath("Four.png"));
                        this.name = "HEALTH!";
                        this.rawDescription = "Gain 4 Temporary_HP.";
                        this.initializeDescription();
                    } else if (blah == 3) {
                        loadCardImage(makeCardPath("Two.png"));
                        this.name = "CARDS!";
                        this.rawDescription = "Draw 2 cards.";
                        this.initializeDescription();
                    } else if (blah == 4) {
                        loadCardImage(makeCardPath("Five.png"));
                        this.name = "DEATHGAZE!";
                        this.rawDescription = "Gain 5 gorgonmod:Deathgaze.";
                        this.initializeDescription();
                    } else if (blah == 5) {
                        loadCardImage(makeCardPath("One.png"));
                        this.name = "ENERGY!";
                        this.rawDescription = "Gain [E] .";
                        this.initializeDescription();
                    } else if (blah == 6) {
                        loadCardImage(makeCardPath("Three.png"));
                        this.name = "DEBUFF!";
                        this.rawDescription = "ALL enemies lose 3 Strength this turn.";
                        this.initializeDescription();
                    }
                }
            }
        }
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {

    }

    @Override
    public void upgrade() {

    }
}