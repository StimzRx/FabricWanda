package dev.venomcode.wanda.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import dev.venomcode.serverapi.api.ServerUtils;
import dev.venomcode.wanda.WandaMod;
import dev.venomcode.wanda.api.WandaAPI;
import me.lucko.fabric.api.permissions.v0.Permissions;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;

import static net.minecraft.server.command.CommandManager.literal;

public class WandCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        LiteralArgumentBuilder<ServerCommandSource> builder = literal("wand")
                .requires( src -> (Permissions.check(src, "wanda.wand")) || src.hasPermissionLevel(2))
                .executes(ctx -> executeWand(ctx));

        dispatcher.register(builder);
    }

    public static int executeWand(CommandContext<ServerCommandSource> ctx) throws CommandSyntaxException {
        if(!ctx.getSource().isExecutedByPlayer())
            return Command.SINGLE_SUCCESS;

        ServerPlayerEntity serverPlayer = ctx.getSource().getPlayer();

        ItemStack stack = new ItemStack(WandaMod.WAND_ITEM, 1);

        serverPlayer.giveItemStack(stack);

        serverPlayer.sendMessage(ServerUtils.getText("Wand Given", Formatting.LIGHT_PURPLE), true);

        return Command.SINGLE_SUCCESS;
    }
}
