package dev.venomcode.wanda;

import dev.venomcode.serverapi.api.ServerAPI;
import dev.venomcode.serverapi.api.ServerUtils;
import dev.venomcode.wanda.api.IWandaPlayer;
import dev.venomcode.wanda.configs.WandaConfig;
import dev.venomcode.wanda.items.WandItem;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.hocon.HoconConfigurationLoader;

import java.nio.file.Path;

public class WandaMod implements ModInitializer {
    // This logger is used to write text to the console and the log file.
    // It is considered best practice to use your mod id as the logger's name.
    // That way, it's clear which mod wrote info, warnings, and errors.
    public static final String MODID = "wanda";
    public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

    @Override
    public void onInitialize() {

        WandaConfig config = getConfig();
        saveConfig();

        Identifier wandItemProxyTag = config.getWandDisplayItem();
        if(wandItemProxyTag.getPath().equalsIgnoreCase(MODID))
        {
            wandItemProxyTag = new Identifier("minecraft:nether_star");
        }

        Item wandItemProxy = Registries.ITEM.get(wandItemProxyTag);

        WAND_ITEM = new WandItem(new FabricItemSettings().maxCount(1), wandItemProxy);
        Registry.register(Registries.ITEM, new Identifier(MODID, "wanda"), WAND_ITEM);

        AttackBlockCallback.EVENT.register(((player, world, hand, pos, direction) -> {
            if(!player.getWorld().isClient) {
                ServerPlayerEntity serverPlayer = (ServerPlayerEntity) player;
                IWandaPlayer wandaPlayer = (IWandaPlayer) serverPlayer;

                wandaPlayer.setSelected(pos, true);

                serverPlayer.sendMessage(ServerUtils.getText("[Wanda] Set PRIMARY selection to " + pos.toShortString(), Formatting.GOLD));
                return ActionResult.SUCCESS;
            }
            return ActionResult.PASS;
        }));

    }

    public static WandaConfig getConfig() {
        if(_configCached != null)
            return _configCached;

        try {
            CommentedConfigurationNode node = configLoader.load();

            _configCached = node.get(WandaConfig.class);
        }
        catch (ConfigurateException ex) {
            LOGGER.error(ServerAPI.Logger.Error("[ERROR]Failed to load wanda config."));
        }

        return _configCached;
    }

    public static void saveConfig() {
        CommentedConfigurationNode node = CommentedConfigurationNode.root();
        try {
            node.set(WandaConfig.class, _configCached);
            configLoader.save(node);
        }
        catch (ConfigurateException ex) {
            LOGGER.error(ServerAPI.Logger.Error("[ERROR]Failed to save wanda config."));
        }
    }

    public WandItem WAND_ITEM;

    private static final HoconConfigurationLoader configLoader = HoconConfigurationLoader.builder()
            .path(Path.of(ServerAPI.CONFIG_PATH + "wanda.conf"))
            .build();
    private static WandaConfig _configCached = null;

}
