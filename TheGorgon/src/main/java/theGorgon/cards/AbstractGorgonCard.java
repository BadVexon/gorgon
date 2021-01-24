package theGorgon.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;

public abstract class AbstractGorgonCard extends CustomCard {

    public int deathgaze;
    public int baseDeathgaze;
    public boolean upgradedDeathgaze;
    public boolean isDeathgazeModified;

    public AbstractGorgonCard(final String id, final String name, final String img, final int cost, final String rawDescription, final CardType type, final CardColor color, final CardRarity rarity, final CardTarget target) {
        super(id, name, img, cost, rawDescription, type, color, rarity, target);
        isCostModified = false;
        isCostModifiedForTurn = false;
        isDamageModified = false;
        isBlockModified = false;
        isMagicNumberModified = false;
        isDeathgazeModified = false;
    }

    public void displayUpgrades() {
        super.displayUpgrades();
        if (upgradedDeathgaze) {
            deathgaze = baseDeathgaze;
            isDeathgazeModified = true;
        }
    }

    public DamageInfo makeInfo(AbstractPlayer p) {
        return new DamageInfo(p, damage, damageTypeForTurn);
    }

    void upgradeDeathGaze(int amount) {
        baseDeathgaze += amount;
        deathgaze = baseDeathgaze;
        upgradedDeathgaze = true;
    }
}