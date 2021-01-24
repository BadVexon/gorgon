package theGorgon.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theGorgon.characters.TheGorgon;

public class GainDeathgazeAction extends AbstractGameAction {
    public GainDeathgazeAction(int amount) {
        this.amount = amount;
        this.actionType = ActionType.SPECIAL;
    }

    @Override
    public void update() {
        if (AbstractDungeon.player instanceof TheGorgon) {
            ((TheGorgon) AbstractDungeon.player).gainDeathgaze(amount);
        }
        this.isDone = true;
    }
}