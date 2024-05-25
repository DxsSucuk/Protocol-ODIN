package de.presti.odin.blocks.entities;

import de.presti.odin.ODIN;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class OdinControlPanelBlockEntity extends BlockEntity {

    long lastTime = 0;

    public OdinControlPanelBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.ODIN_CONTROL_PANEL.get(), pos, state);
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag nbt) {
        super.saveAdditional(nbt);
        CompoundTag shotTime = new CompoundTag();
        shotTime.putLong("lastTime", this.lastTime);
        nbt.put(ODIN.MODID, shotTime);
    }

    @Override
    public void load(@NotNull CompoundTag nbt) {
        CompoundTag shotTime = nbt.getCompound(ODIN.MODID);
        this.lastTime = shotTime.getLong("lastTime");
        super.load(nbt);
    }

    @Override
    public @NotNull CompoundTag getUpdateTag() {
        CompoundTag tag = super.getUpdateTag();
        saveAdditional(tag);
        return super.getUpdateTag();
    }

    public long getLastTime() {
        return this.lastTime;
    }

    public void setLastTime(long lastTime) {
        this.lastTime = lastTime;
        this.setChanged();
    }

    public static void tick(Level level, BlockPos blockPos, BlockState blockState, OdinControlPanelBlockEntity pEntity) {

    }
}
