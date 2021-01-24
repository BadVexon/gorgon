package theGorgon;

import basemod.BaseMod;
import basemod.ReflectionHacks;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.Keyword;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import javassist.CtClass;
import javassist.Modifier;
import javassist.NotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.clapper.util.classutil.*;
import theGorgon.characters.TheGorgon;
import theGorgon.relics.SapphireLocks;
import theGorgon.util.Deathgaze;
import theGorgon.util.DraggableStatDisplay;
import theGorgon.util.StatuePatchue;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;

@SpireInitializer
public class GorgonMod implements
        EditCardsSubscriber,
        EditRelicsSubscriber,
        EditStringsSubscriber,
        EditKeywordsSubscriber,
        EditCharactersSubscriber,
        StartGameSubscriber,
        PostBattleSubscriber,
        OnStartBattleSubscriber {
    public static final Logger logger = LogManager.getLogger(GorgonMod.class.getName());
    public static final Color GORG_PURPLE = new Color(0.854F, 0.2F, 1F, 1F);
    public static final String THE_OCTO_SHOULDER_1 = "gorgonmodResources/images/char/octoChar/shoulder.png";
    public static final String THE_OCTO_SHOULDER_2 = "gorgonmodResources/images/char/octoChar/shoulder2.png";
    public static final String THE_OCTO_CORPSE = "gorgonmodResources/images/char/octoChar/corpse.png";
    private static final String ATTACK_OCTO_GRAY = "gorgonmodResources/images/512/bg_attack_default_gray.png";
    private static final String SKILL_OCTO_GRAY = "gorgonmodResources/images/512/bg_skill_default_gray.png";
    private static final String POWER_OCTO_GRAY = "gorgonmodResources/images/512/bg_power_default_gray.png";
    private static final String ENERGY_ORB_OCTO_GRAY = "gorgonmodResources/images/512/card_default_gray_orb.png";
    private static final String CARD_ENERGY_ORB = "gorgonmodResources/images/512/card_small_orb.png";
    private static final String ATTACK_OCTO_GRAY_PORTRAIT = "gorgonmodResources/images/1024/bg_attack_default_gray.png";
    private static final String SKILL_OCTO_GRAY_PORTRAIT = "gorgonmodResources/images/1024/bg_skill_default_gray.png";
    private static final String POWER_OCTO_GRAY_PORTRAIT = "gorgonmodResources/images/1024/bg_power_default_gray.png";
    private static final String ENERGY_ORB_OCTO_GRAY_PORTRAIT = "gorgonmodResources/images/1024/card_default_gray_orb.png";
    private static final String THE_OCTO_BUTTON = "gorgonmodResources/images/charSelect/OctoCharacterButton.png";
    private static final String THE_OCTO_PORTRAIT = "gorgonmodResources/images/charSelect/OctoCharacterBG.png";
    private static String modID;
    private static boolean thindone;

    public static DraggableStatDisplay newHitbox;

    public GorgonMod() {
        logger.info("Subscribe to BaseMod hooks");

        BaseMod.subscribe(this);


        modID = "gorgonmod";

        logger.info("Done subscribing");

        logger.info("Creating the color " + TheGorgon.Enums.COLOR_PURPLE.toString());

        BaseMod.addColor(TheGorgon.Enums.COLOR_PURPLE, GORG_PURPLE, GORG_PURPLE, GORG_PURPLE,
                GORG_PURPLE, GORG_PURPLE, GORG_PURPLE, GORG_PURPLE,
                ATTACK_OCTO_GRAY, SKILL_OCTO_GRAY, POWER_OCTO_GRAY, ENERGY_ORB_OCTO_GRAY,
                ATTACK_OCTO_GRAY_PORTRAIT, SKILL_OCTO_GRAY_PORTRAIT, POWER_OCTO_GRAY_PORTRAIT,
                ENERGY_ORB_OCTO_GRAY_PORTRAIT, CARD_ENERGY_ORB);

        logger.info("Done creating the color");

        newHitbox = new DraggableStatDisplay();

    }

    public static String makeCardPath(String resourcePath) {
        return getModID() + "Resources/images/cards/" + resourcePath;
    }

    public static String makeRelicPath(String resourcePath) {
        return getModID() + "Resources/images/relics/" + resourcePath;
    }

    public static String makeRelicOutlinePath(String resourcePath) {
        return getModID() + "Resources/images/relics/outline/" + resourcePath;
    }

    public static String makePowerPath(String resourcePath) {
        return getModID() + "Resources/images/powers/" + resourcePath;
    }

    public static String getModID() {
        return modID;
    }

    @SuppressWarnings("unused")
    public static void initialize() {
        logger.info("========================= IfsdfsdfMod. Hi. =========================");
        GorgonMod gorgMod = new GorgonMod();
        logger.info("========================= /Defadfasdfadgdfdgkln./ =========================");
    }

    public static String makeID(String idText) {
        return getModID() + ":" + idText;
    }

    @Override
    public void receiveEditCharacters() {
        logger.info("Beginning to edit characters. " + "Add " + TheGorgon.Enums.THE_GORGON.toString());

        BaseMod.addCharacter(new TheGorgon("the Gorgon", TheGorgon.Enums.THE_GORGON),
                THE_OCTO_BUTTON, THE_OCTO_PORTRAIT, TheGorgon.Enums.THE_GORGON);

        logger.info("Added " + TheGorgon.Enums.THE_GORGON.toString());
    }

    @Override
    public void receiveEditRelics() {
        logger.info("Adding relics");

        BaseMod.addRelicToCustomPool(new SapphireLocks(), TheGorgon.Enums.COLOR_PURPLE);

        logger.info("Done adding relics!");
    }

    @Override
    public void receiveEditCards() {
        BaseMod.addDynamicVariable(new Deathgaze());
        try {
            autoAddCards();
        } catch (URISyntaxException | IllegalAccessException | InstantiationException | NotFoundException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static void autoAddCards()
            throws URISyntaxException, IllegalAccessException, InstantiationException, NotFoundException, ClassNotFoundException {
        ClassFinder finder = new ClassFinder();
        URL url = GorgonMod.class.getProtectionDomain().getCodeSource().getLocation();
        finder.add(new File(url.toURI()));

        ClassFilter filter =
                new AndClassFilter(
                        new NotClassFilter(new InterfaceOnlyClassFilter()),
                        new NotClassFilter(new AbstractClassFilter()),
                        new ClassModifiersClassFilter(Modifier.PUBLIC),
                        new CardFilter()
                );
        Collection<ClassInfo> foundClasses = new ArrayList<>();
        finder.findClasses(foundClasses, filter);

        for (ClassInfo classInfo : foundClasses) {
            CtClass cls = Loader.getClassPool().get(classInfo.getClassName());
            if (cls.hasAnnotation(CardIgnore.class)) {
                continue;
            }
            boolean isCard = false;
            CtClass superCls = cls;
            while (superCls != null) {
                superCls = superCls.getSuperclass();
                if (superCls == null) {
                    break;
                }
                if (superCls.getName().equals(AbstractCard.class.getName())) {
                    isCard = true;
                    break;
                }
            }
            if (!isCard) {
                continue;
            }
            System.out.println(classInfo.getClassName());
            AbstractCard card = (AbstractCard) Loader.getClassPool().getClassLoader().loadClass(cls.getName()).newInstance();
            BaseMod.addCard(card);
            if (cls.hasAnnotation(CardNoSeen.class)) {
                UnlockTracker.hardUnlockOverride(card.cardID);
            } else {
            UnlockTracker.unlockCard(card.cardID);
            }
        }
    }


    @Override
    public void receiveEditStrings() {
        logger.info("You seeing this?");
        logger.info("Beginning to edit strings for mod with ID: " + getModID());

        BaseMod.loadCustomStringsFile(CardStrings.class, getModID() + "Resources/localization/eng/GorgonMod-Card-Strings.json");

        BaseMod.loadCustomStringsFile(PowerStrings.class, getModID() + "Resources/localization/eng/GorgonMod-Power-Strings.json");

        BaseMod.loadCustomStringsFile(RelicStrings.class, getModID() + "Resources/localization/eng/GorgonMod-Relic-Strings.json");

        BaseMod.loadCustomStringsFile(CharacterStrings.class, getModID() + "Resources/localization/eng/GorgonMod-Character-Strings.json");

        logger.info("Done editting strings");
    }

    @Override
    public void receiveStartGame() {
        if (!thindone) {
            newHitbox.hb.resize(320 * Settings.scale, 160 * Settings.scale);
            newHitbox.hb.move(256 * Settings.scale, 600 * Settings.scale);
        }
        thindone = true;
    }

    @Override
    public void receivePostBattle(AbstractRoom room) {
        if (AbstractDungeon.player instanceof TheGorgon) {
            ((TheGorgon) AbstractDungeon.player).deathgaze = ((TheGorgon) AbstractDungeon.player).basedeathgaze;
        }
    }

    @Override
    public void receiveOnBattleStart(AbstractRoom room) {
        if (AbstractDungeon.player instanceof TheGorgon) {
            ((TheGorgon) AbstractDungeon.player).deathgaze = ((TheGorgon) AbstractDungeon.player).basedeathgaze;
        }
    }

    @Override
    public void receiveEditKeywords() {
        Gson gson = new Gson();
        String json = Gdx.files.internal(getModID() + "Resources/localization/eng/GorgonMod-Keyword-Strings.json").readString(String.valueOf(StandardCharsets.UTF_8));
        com.evacipated.cardcrawl.mod.stslib.Keyword[] keywords = gson.fromJson(json, com.evacipated.cardcrawl.mod.stslib.Keyword[].class);

        if (keywords != null) {
            for (Keyword keyword : keywords) {
                BaseMod.addKeyword(modID, keyword.PROPER_NAME, keyword.NAMES, keyword.DESCRIPTION);
            }
        }
    }
}
