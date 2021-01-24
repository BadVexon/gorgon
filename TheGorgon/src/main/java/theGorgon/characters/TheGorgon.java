package theGorgon.characters;

import basemod.abstracts.CustomPlayer;
import basemod.animations.SpriterAnimation;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.cutscenes.CutscenePanel;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import theGorgon.GorgonMod;
import theGorgon.cards.Deathstare;
import theGorgon.cards.Defend;
import theGorgon.cards.SnakeSkin;
import theGorgon.cards.Strike;
import theGorgon.relics.SapphireLocks;
import theGorgon.util.GainStatCurvy;
import theGorgon.util.GainStatLine;

import java.util.ArrayList;
import java.util.List;

import static theGorgon.GorgonMod.*;
import static theGorgon.characters.TheGorgon.Enums.COLOR_PURPLE;

public class TheGorgon extends CustomPlayer {
    public static final String[] orbTextures = {
            "gorgonmodResources/images/char/octoChar/orb/layer1.png",
            "gorgonmodResources/images/char/octoChar/orb/layer2.png",
            "gorgonmodResources/images/char/octoChar/orb/layer3.png",
            "gorgonmodResources/images/char/octoChar/orb/layer4.png",
            "gorgonmodResources/images/char/octoChar/orb/layer5.png",
            "gorgonmodResources/images/char/octoChar/orb/layer6.png",
            "gorgonmodResources/images/char/octoChar/orb/layer1d.png",
            "gorgonmodResources/images/char/octoChar/orb/layer2d.png",
            "gorgonmodResources/images/char/octoChar/orb/layer3d.png",
            "gorgonmodResources/images/char/octoChar/orb/layer4d.png",
            "gorgonmodResources/images/char/octoChar/orb/layer5d.png",};
    private static final String ID = makeID("theGorgon");
    private static final CharacterStrings characterStrings = CardCrawlGame.languagePack.getCharacterString(ID);
    private static final String[] NAMES = characterStrings.NAMES;
    private static final String[] TEXT = characterStrings.TEXT;

    private int maxLines = 36;
    private int stride = 360 / maxLines;
    private float offset = MathUtils.random(-180.0F, 180.0F);
    public int basedeathgaze = 5;
    public int deathgaze;

    public TheGorgon(String name, PlayerClass setClass) {
        super(name, setClass, orbTextures,
                "gorgonmodResources/images/char/octoChar/orb/vfx.png", null,
                new SpriterAnimation(
                        "gorgonmodResources/images/char/octoChar/octo.scml"));


        initializeClass(null,
                THE_OCTO_SHOULDER_1,
                THE_OCTO_SHOULDER_2,
                THE_OCTO_CORPSE,
                getLoadout(), 20.0F, -10.0F, 224.0F, 324.0F, new EnergyManager(3));


        dialogX = (drawX + 0.0F * Settings.scale);
        dialogY = (drawY + 250.0F * Settings.scale);
        deathgaze = basedeathgaze;
    }

    @Override
    public CharSelectInfo getLoadout() {
        return new CharSelectInfo(NAMES[0], TEXT[0],
                77, 77, 0, 99, 5, this, getStartingRelics(),
                getStartingDeck(), false);
    }

    @Override
    public ArrayList<String> getStartingDeck() {
        ArrayList<String> retVal = new ArrayList<>();

        retVal.add(Strike.ID);
        retVal.add(Strike.ID);
        retVal.add(Strike.ID);
        retVal.add(Strike.ID);
        retVal.add(Defend.ID);
        retVal.add(Defend.ID);
        retVal.add(Defend.ID);
        retVal.add(Defend.ID);
        retVal.add(SnakeSkin.ID);
        retVal.add(Deathstare.ID);
        return retVal;
    }

    public ArrayList<String> getStartingRelics() {
        ArrayList<String> retVal = new ArrayList<>();
        retVal.add(SapphireLocks.ID);
        return retVal;
    }

    @Override
    public void doCharSelectScreenSelectEffect() {
        CardCrawlGame.sound.play("ATTACK_DEFECT_BEAM");
        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.LOW, ScreenShake.ShakeDur.SHORT,
                false);
    }

    @Override
    public String getCustomModeCharacterButtonSoundKey() {
        return "ATTACK_DEFECT_BEAM";
    }

    @Override
    public int getAscensionMaxHPLoss() {
        return 7;
    }

    @Override
    public AbstractCard.CardColor getCardColor() {
        return COLOR_PURPLE;
    }

    @Override
    public Color getCardTrailColor() {
        return GorgonMod.GORG_PURPLE;
    }

    @Override
    public BitmapFont getEnergyNumFont() {
        return FontHelper.energyNumFontRed;
    }

    @Override
    public String getLocalizedCharacterName() {
        return NAMES[0];
    }

    @Override
    public AbstractCard getStartCardForEvent() {
        return new Deathstare();
    }

    @Override
    public String getTitle(AbstractPlayer.PlayerClass playerClass) {
        return NAMES[1];
    }

    @Override
    public AbstractPlayer newInstance() {
        return new TheGorgon(name, chosenClass);
    }

    @Override
    public Color getCardRenderColor() {
        return GorgonMod.GORG_PURPLE;
    }

    @Override
    public Color getSlashAttackColor() {
        return GorgonMod.GORG_PURPLE;
    }

    @Override
    public List<CutscenePanel> getCutscenePanels() {
        List<CutscenePanel> panels = new ArrayList<>();
        panels.add(new CutscenePanel("gorgonmodResources/images/charSelect/Comic_01.png"));
        panels.add(new CutscenePanel("gorgonmodResources/images/charSelect/Comic_02.png"));
        panels.add(new CutscenePanel("gorgonmodResources/images/charSelect/Comic_03.png"));
        return panels;
    }

    @Override
    public AbstractGameAction.AttackEffect[] getSpireHeartSlashEffect() {
        return new AbstractGameAction.AttackEffect[]{
                AbstractGameAction.AttackEffect.FIRE,
                AbstractGameAction.AttackEffect.BLUNT_HEAVY,
                AbstractGameAction.AttackEffect.FIRE};
    }

    @Override
    public String getSpireHeartText() {
        return TEXT[1];
    }

    @Override
    public String getVampireText() {
        return TEXT[2];
    }

    public void gainBaseDeathgaze(float amount) {
        basedeathgaze += amount;
    }

    public void gainDeathgaze(float amount) {
        deathgaze += amount;
        for (int i = 0; i < maxLines; i++) {
            AbstractDungeon.effectList.add(new GainStatLine(newHitbox.hb.cX, newHitbox.hb.cY + (20 * Settings.scale), new Color(0.545F, 0F, 0.878F, 1), ((stride * i) + MathUtils.random(-stride, stride) + offset)));
            if (i % 2 == 0) {
                AbstractDungeon.effectList.add(new GainStatCurvy(newHitbox.hb.cX, newHitbox.hb.cY + (20 * Settings.scale), new Color(0.545F, 0F, 0.878F, 1)));
            }
        }
    }

    public static class Enums {
        @SpireEnum
        public static AbstractPlayer.PlayerClass THE_GORGON;
        @SpireEnum(name = "GORGON_PURPLE_COLOR")
        public static AbstractCard.CardColor COLOR_PURPLE;
        @SpireEnum(name = "GORGON_PURPLE_COLOR")
        @SuppressWarnings("unused")
        public static CardLibrary.LibraryType LIBRARY_COLOR;
    }
}
