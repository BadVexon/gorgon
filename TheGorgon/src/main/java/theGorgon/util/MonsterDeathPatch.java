package theGorgon.util;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.MinionPower;

@SpirePatch(
        clz = AbstractMonster.class,
        method = "die",
        paramtypez =
                {
                        boolean.class
                }
)
public class MonsterDeathPatch {
    public static void Prefix(AbstractMonster __instance, boolean triggerRelics) {
        if (__instance.hasPower(MinionPower.POWER_ID)) {
            StatuePatchue.nonStatueList.add(__instance);
        }
    }
}