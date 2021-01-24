//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package theGorgon.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.GetAllInBattleInstances;
import java.util.Iterator;
import java.util.UUID;

public class IncreaseCostAction extends AbstractGameAction {
    UUID uuid;

    public IncreaseCostAction(UUID targetUUID, int amount) {
        this.uuid = targetUUID;// 14
        this.amount = amount;// 15
        this.duration = Settings.ACTION_DUR_XFAST;// 16
    }// 17

    public void update() {

        for (AbstractCard c : GetAllInBattleInstances.get(this.uuid)) {
            c.modifyCostForCombat(1);// 22
        }

        this.isDone = true;// 24
    }// 25
}
