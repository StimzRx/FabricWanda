package dev.venomcode.wanda.api;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Pair;
import net.minecraft.util.math.BlockPos;

public class WandaAPI {
    public Pair<BlockPos, BlockPos> getPlayersSelected(ServerPlayerEntity serverPlayer) {
        IWandaPlayer wandaPlayer = (IWandaPlayer) serverPlayer;
        return new Pair<>(wandaPlayer.getSelectedPrimary(), wandaPlayer.getSelectedSecondary());
    }

}
