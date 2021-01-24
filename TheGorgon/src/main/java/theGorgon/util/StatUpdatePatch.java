package theGorgon.util;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import com.megacrit.cardcrawl.ui.panels.TopPanel;
import theGorgon.characters.TheGorgon;

import static theGorgon.GorgonMod.newHitbox;

@SpirePatch(
        clz = EnergyPanel.class,
        method = "update"
)
public class StatUpdatePatch {
    public static void Prefix(EnergyPanel __instance) {
        if (AbstractDungeon.player instanceof TheGorgon && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            newHitbox.dragUpdate();
            newHitbox.update();
        }
    }
}
