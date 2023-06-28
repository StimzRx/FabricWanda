package dev.venomcode.wanda.api;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Pair;
import net.minecraft.util.math.BlockPos;

/**
 * API for the Wanda point-selection library/tool
 */
public class WandaAPI {
    /**
     * Gets a players selection in BlockPos format as a pair of PRIMARY, SECONDARY
     */
    public Pair<BlockPos, BlockPos> getPlayerSelection(ServerPlayerEntity serverPlayer) {
        IWandaPlayer wandaPlayer = (IWandaPlayer) serverPlayer;
        return new Pair<>(wandaPlayer.getSelectedPrimary(), wandaPlayer.getSelectedSecondary());
    }

}
