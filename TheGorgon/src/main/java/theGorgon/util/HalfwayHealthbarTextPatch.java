package theGorgon.util;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;
import theGorgon.characters.TheGorgon;

public class HalfwayHealthbarTextPatch {
    @SpirePatch(clz = AbstractCreature.class, method = "renderHealthText")
    public static class TextRender {
        public static ExprEditor Instrument() {
            return new ExprEditor() {
                @Override
                public void edit(MethodCall m) throws CannotCompileException {
                    if (m.getClassName().equals(FontHelper.class.getName()) && m.getMethodName().equals("renderFontCentered")) {
                        m.replace("{" +
                                "if(" + AbstractDungeon.class.getName() + ".player instanceof " + TheGorgon.class.getName() + ") {" +
                                FontHelper.class.getName() + ".renderFontCentered(sb, " + FontHelper.class.getName() + ".healthInfoFont, this.currentHealth + \"/\" + this.maxHealth + \" (\"+(int)(((float)this.currentHealth/this.maxHealth) * 100) +\"%)\", " +
                                "this.hb.cX, y + HEALTH_BAR_OFFSET_Y + HEALTH_TEXT_OFFSET_Y + 5.0F * " + Settings.class.getName() + ".scale, this.hbTextColor);" +
                                "} else {" +
                                FontHelper.class.getName() + ".renderFontCentered(sb, " + FontHelper.class.getName() + ".healthInfoFont, this.currentHealth + \"/\" + this.maxHealth, " +
                                "this.hb.cX, y + HEALTH_BAR_OFFSET_Y + HEALTH_TEXT_OFFSET_Y + 5.0F * " + Settings.class.getName() + ".scale, this.hbTextColor);" +
                                "}" +
                                "}");
                    }
                }
            };
        }
    }
}
//FontHelper.renderFontCentered(sb, FontHelper.healthInfoFont, this.currentHealth + "/" + this.maxHealth, this.hb.cX, y + HEALTH_BAR_OFFSET_Y + HEALTH_TEXT_OFFSET_Y + 5.0F * Settings.scale, this.hbTextColor);