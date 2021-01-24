package theGorgon.actions;

import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import theGorgon.cards.SlotMachineCard;
import theGorgon.util.CenterGridCardSelectScreen;

import java.util.ArrayList;

public class SlotMachineAction extends AbstractGameAction {
    private boolean lucky = false;
    private boolean pickCard = false;

    public SlotMachineAction(boolean LUCKY) {
        duration = Settings.ACTION_DUR_XFAST;
        actionType = ActionType.WAIT;
        this.lucky = LUCKY;
    }

    @Override
    public void update() {
        if (duration == Settings.ACTION_DUR_XFAST) {
            CardGroup group = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            group.addToTop(new SlotMachineCard());
            group.addToTop(new SlotMachineCard());
            group.addToTop(new SlotMachineCard());
            if (lucky) {
                group.addToTop(new SlotMachineCard());
            }
            pickCard = true;
            CenterGridCardSelectScreen.centerGridSelect = true;
            CenterGridCardSelectScreen.isSlotMachine = true;
            AbstractDungeon.gridSelectScreen.open(group, group.size(), ("GOOD LUCK!"), false);
        } else if ((pickCard && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty())) {
            pickCard = false;
            ArrayList<String> awesomeList = new ArrayList<>();
            int blah = 1;
            for (AbstractCard q : AbstractDungeon.gridSelectScreen.selectedCards) {
                if (q instanceof SlotMachineCard) {
                    ((SlotMachineCard) q).yes = true;
                }
                switch (blah) {
                    case 1:
                        AbstractDungeon.actionManager.addToBottom(new VFXAction(new ShowCardBrieflyEffect(q, (float) Settings.WIDTH * 0.2F, (float) Settings.HEIGHT / 2.0F)));
                        break;
                    case 2:
                        AbstractDungeon.actionManager.addToBottom(new VFXAction(new ShowCardBrieflyEffect(q, (float) Settings.WIDTH * 0.4F, (float) Settings.HEIGHT / 2.0F)));
                        break;
                    case 3:
                        AbstractDungeon.actionManager.addToBottom(new VFXAction(new ShowCardBrieflyEffect(q, (float) Settings.WIDTH * 0.6F, (float) Settings.HEIGHT / 2.0F)));
                        break;
                    case 4:
                        AbstractDungeon.actionManager.addToBottom(new VFXAction(new ShowCardBrieflyEffect(q, (float) Settings.WIDTH * 0.8F, (float) Settings.HEIGHT / 2.0F)));
                        break;
                }
                switch (q.name) {
                    case "SEVEN!":
                        AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(AbstractDungeon.player, DamageInfo.createDamageMatrix(7), DamageInfo.DamageType.THORNS, AttackEffect.BLUNT_HEAVY));
                        awesomeList.add(q.name);
                        break;
                    case "BLOCK!":
                        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, 6));
                        awesomeList.add(q.name);
                        break;
                    case "HEALTH!":
                        AbstractDungeon.actionManager.addToBottom(new AddTemporaryHPAction(AbstractDungeon.player, AbstractDungeon.player, 4));
                        awesomeList.add(q.name);
                        break;
                    case "CARDS!":
                        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(AbstractDungeon.player, 2));
                        awesomeList.add(q.name);
                        break;
                    case "DEATHGAZE!":
                        AbstractDungeon.actionManager.addToBottom(new GainDeathgazeAction(5));
                        awesomeList.add(q.name);
                        break;
                    case "ENERGY!":
                        AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(1));
                        awesomeList.add(q.name);
                        break;
                    case "DEBUFF!":
                        for (AbstractMonster l : AbstractDungeon.getCurrRoom().monsters.monsters) {
                            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(l, AbstractDungeon.player, new StrengthPower(l, -3), -3));
                            if (!l.hasPower(ArtifactPower.POWER_ID)) {
                                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(l, AbstractDungeon.player, new GainStrengthPower(l, 3), 3));
                            }
                        }
                        awesomeList.add(q.name);
                        break;
                }
                blah++;
            }
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            CenterGridCardSelectScreen.centerGridSelect = false;
            CenterGridCardSelectScreen.isSlotMachine = false;
            isDone = true;
        }
        tickDuration();
    }
}