package theGorgon.util;

import basemod.abstracts.DynamicVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;
import theGorgon.cards.AbstractGorgonCard;

public class Deathgaze extends DynamicVariable {

    @Override
    public String key() {
        return "deathGaze";
    }

    @Override
    public boolean isModified(AbstractCard card) {
        return ((AbstractGorgonCard) card).isDeathgazeModified;
    }

    @Override
    public int value(AbstractCard card) {
        return ((AbstractGorgonCard) card).deathgaze;
    }

    @Override
    public int baseValue(AbstractCard card) {
        return ((AbstractGorgonCard) card).baseDeathgaze;
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        return ((AbstractGorgonCard) card).upgradedDeathgaze;
    }
}