package theGorgon.actions;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.BlockedWordEffect;
import com.megacrit.cardcrawl.vfx.combat.DeckPoofEffect;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;
import theGorgon.util.GainStatCurvy;
import theGorgon.util.GainStatLine;

import static theGorgon.GorgonMod.newHitbox;


public class StatueEnemyAction extends AbstractGameAction {
    private AbstractMonster m;

    public StatueEnemyAction(AbstractMonster m) {
        this.m = m;
    }

    private int stride = 360 / 36;
    private float offset = MathUtils.random(-180.0F, 180.0F);

    @Override
    public void update() {
        AbstractDungeon.getCurrRoom().cannotLose = false;
        CardCrawlGame.sound.play("ATTACK_DEFECT_BEAM");
        for (int i = 0; i < 36; i++) {
            AbstractDungeon.effectList.add(new GainStatLine(m.hb.cX, m.hb.cY + (20 * Settings.scale), new Color(0.545F, 0F, 0.878F, 1), ((stride * i) + MathUtils.random(-stride, stride) + offset)));
            if (i % 2 == 0) {
                AbstractDungeon.effectList.add(new GainStatCurvy(m.hb.cX, m.hb.cY + (20 * Settings.scale), new Color(0.545F, 0F, 0.878F, 1)));
            }
        }
        AbstractDungeon.effectList.add(new LightningEffect(m.hb.cX, m.hb.cY));
        AbstractDungeon.effectList.add(new BlockedWordEffect(m, m.hb.cX, m.hb.cY, "DEATHGAZED!"));
        m.currentHealth = 0;
        m.healthBarUpdatedEvent();
        m.die(); //(hopefully) ensure death

        if (AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            AbstractDungeon.actionManager.cleanCardQueue();
            AbstractDungeon.effectList.add(new DeckPoofEffect(64.0F * Settings.scale, 64.0F * Settings.scale, true));
            AbstractDungeon.effectList.add(new DeckPoofEffect((float) Settings.WIDTH - 64.0F * Settings.scale, 64.0F * Settings.scale, false));
            AbstractDungeon.overlayMenu.hideCombatPanels();
        }

        if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
            AbstractDungeon.actionManager.clearPostCombatActions();
        }
        this.isDone = true;
    }
}