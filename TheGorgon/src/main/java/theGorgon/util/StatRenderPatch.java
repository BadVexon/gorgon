package theGorgon.util;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import com.megacrit.cardcrawl.ui.panels.TopPanel;
import theGorgon.GorgonMod;
import theGorgon.characters.TheGorgon;

@SpirePatch(
        clz = EnergyPanel.class,
        method = "render",
        paramtypes = {"com.badlogic.gdx.graphics.g2d.SpriteBatch"}
)
public class StatRenderPatch {
    public static void Prefix(EnergyPanel __instance, SpriteBatch sb) {
        if (AbstractDungeon.player instanceof TheGorgon && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            StatHelper.renderGenericTip(GorgonMod.newHitbox.hb.x, GorgonMod.newHitbox.hb.y + (GorgonMod.newHitbox.hb.height / 1.33F), "Deathgaze", "At the start of your turn, ALL enemies with #b" + ((TheGorgon) AbstractDungeon.player).deathgaze + "% HP or less turn to stone.");
            StatHelper.render(sb);
        }
    }
}
