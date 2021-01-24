package theGorgon.util;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.TopPanel;
import javassist.CtBehavior;
import theGorgon.actions.StatueEnemyAction;
import theGorgon.characters.TheGorgon;

@SpirePatch(
        clz = GameActionManager.class,
        method = "getNextAction"
)
public class TurnStartCheck {
    @SpireInsertPatch(
            locator = Locator.class
    )
    public static void Insert(GameActionManager __instance) {
        if (AbstractDungeon.player instanceof TheGorgon) {
            for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
                if (!m.isDying && !m.isDead) {
                    if (((int) (((float) m.currentHealth / (float) m.maxHealth) * 100) <= ((TheGorgon) AbstractDungeon.player).deathgaze)) {
                        AbstractDungeon.actionManager.addToBottom(new StatueEnemyAction(m));
                    }
                }
            }
        }
    }
    private static class Locator extends SpireInsertLocator {
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
            Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractPlayer.class, "applyStartOfTurnRelics");
            return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
        }
    }
}