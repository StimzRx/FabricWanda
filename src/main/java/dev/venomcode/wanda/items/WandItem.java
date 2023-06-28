package dev.venomcode.wanda.items;

import dev.venomcode.serverapi.api.ServerUtils;
import dev.venomcode.wanda.api.IWandaPlayer;
import eu.pb4.polymer.core.api.item.SimplePolymerItem;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WandItem extends SimplePolymerItem {
    public WandItem(Settings settings, Item polymerItem) {
        super(settings, polymerItem);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        if(context.getWorld().isClient)
            return ActionResult.PASS;

        ServerPlayerEntity serverPlayer = (ServerPlayerEntity) context.getPlayer();
        IWandaPlayer wandaPlayer = (IWandaPlayer)serverPlayer;

        BlockPos sel = wandaPlayer.getSelectedSecondary();
        if(sel == null || !sel.equals(context.getBlockPos()))
        {
            wandaPlayer.setSelected(context.getBlockPos(), false);

            serverPlayer.sendMessage(ServerUtils.getText("[Wanda] Set SECONDARY selection to " + context.getBlockPos().toShortString(), Formatting.GOLD));
        }


        return ActionResult.PASS;
    }

}
