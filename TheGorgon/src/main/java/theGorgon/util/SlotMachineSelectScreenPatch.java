package theGorgon.util;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.screens.select.GridCardSelectScreen;
import javassist.CtBehavior;

import java.util.ArrayList;

@SpirePatch(
        clz = GridCardSelectScreen.class,
        method = "update"
)
public class SlotMachineSelectScreenPatch {
    @SpireInsertPatch(
            locator = Locator.class
    )
    public static SpireReturn Insert(GridCardSelectScreen __instance) {
        if (CenterGridCardSelectScreen.isSlotMachine) {
            return SpireReturn.Return(null);
        }
        return SpireReturn.Continue();
    }

    private static class Locator extends SpireInsertLocator {
        private Locator() {
        }// 42

        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
            Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractCard.class, "stopGlowing");// 47
            return new int[]{LineFinder.findAllInOrder(ctMethodToPatch, new ArrayList<Matcher>(), finalMatcher)[5]};// 48
        }
    }
}
