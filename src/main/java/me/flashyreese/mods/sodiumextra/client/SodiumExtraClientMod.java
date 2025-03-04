package me.flashyreese.mods.sodiumextra.client;

import me.flashyreese.mods.sodiumextra.client.gui.HudRenderImpl;
import me.flashyreese.mods.sodiumextra.client.gui.SodiumExtraGameOptions;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.IExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.network.NetworkConstants;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

@Mod("sodiumextra")
@OnlyIn(Dist.CLIENT)
public class SodiumExtraClientMod {

    private static final ClientTickHandler clientTickHandler = new ClientTickHandler();
    private static SodiumExtraGameOptions CONFIG;
    private static Logger LOGGER;

    public static Logger logger() {
        if (LOGGER == null) {
            LOGGER = LogManager.getLogger("Sodium Extra");
        }

        return LOGGER;
    }

    public static SodiumExtraGameOptions options() {
        if (CONFIG == null) {
            CONFIG = loadConfig();
        }

        return CONFIG;
    }

    public static ClientTickHandler getClientTickHandler() {
        return clientTickHandler;
    }

    private static SodiumExtraGameOptions loadConfig() {
        return SodiumExtraGameOptions.load(FMLPaths.CONFIGDIR.get().resolve("sodium-extra-options.json").toFile());
    }

    public SodiumExtraClientMod() {
        ModLoadingContext.get().registerExtensionPoint(IExtensionPoint.DisplayTest.class, () -> new IExtensionPoint.DisplayTest(() -> NetworkConstants.IGNORESERVERONLY, (a, b) -> true));

        /* if (SodiumExtraClientMod.options().superSecretSettings.fetchSodiumExtraCrowdinTranslations) {
            CrowdinTranslate.downloadTranslations(SodiumExtraClientMod.options().superSecretSettings.sodiumExtraCrowdinProjectIdentifier, "sodium-extra");
        }
        if (SodiumExtraClientMod.options().superSecretSettings.fetchSodiumCrowdinTranslations) {
            CrowdinTranslate.downloadTranslations(SodiumExtraClientMod.options().superSecretSettings.sodiumCrowdinProjectIdentifier, "sodium");
        }*/

        getClientTickHandler().onClientInitialize();

        MinecraftForge.EVENT_BUS.register(new HudRenderImpl());
    }
}
