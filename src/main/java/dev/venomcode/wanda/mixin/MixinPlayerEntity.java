package dev.venomcode.wanda.mixin;

import dev.venomcode.wanda.api.IWandaPlayer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;

@Mixin(PlayerEntity.class)
public abstract class MixinPlayerEntity implements IWandaPlayer {
    @Override
    public BlockPos getSelectedPrimary() {
        return selectedPrimary;
    }

    @Override
    public BlockPos getSelectedSecondary() {
        return selectedSecondary;
    }

    @Override
    public void setSelected(BlockPos pos, boolean isPrimary) {
        if(isPrimary)
            selectedPrimary = pos;
        else
            selectedSecondary = pos;
    }

    private BlockPos selectedPrimary = null;
    private BlockPos selectedSecondary = null;
}
