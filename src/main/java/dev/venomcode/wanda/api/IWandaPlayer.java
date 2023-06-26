package dev.venomcode.wanda.api;

import net.minecraft.util.math.BlockPos;

public interface IWandaPlayer {
    public BlockPos getSelectedPrimary();
    public BlockPos getSelectedSecondary();

    public void setSelected(BlockPos pos, boolean isPrimary);
}
