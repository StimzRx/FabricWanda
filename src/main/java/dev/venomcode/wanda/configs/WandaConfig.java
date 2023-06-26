package dev.venomcode.wanda.configs;

import net.minecraft.util.Identifier;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Comment;
import org.spongepowered.configurate.objectmapping.meta.Setting;

@ConfigSerializable
public class WandaConfig {
    @Comment("The display item for the wand. Use a tag such as minecraft:stick")
    @Setting("wand_display_item")
    String wandDisplayItem = "minecraft:nether_star";

    public Identifier getWandDisplayItem()
    {
        return new Identifier(wandDisplayItem);
    }
}
