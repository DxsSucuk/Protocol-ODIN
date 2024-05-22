package de.presti.odin.blocks.entities;

import de.presti.odin.screen.OdinControlPanelMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class OdinControlPanelBlockEntity extends BlockEntity implements MenuProvider {
    protected final ContainerData data;
    private int targetX, targetY, targetZ;

    public OdinControlPanelBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.ODIN_CONTROL_PANEL.get(), pos, state);
        this.data = new ContainerData() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 1 -> targetY;
                    case 2 -> targetZ;
                    default -> targetX;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 1 -> targetY = value;
                    case 2 -> targetZ = value;
                    default -> targetX = value;
                }
            }

            @Override
            public int getCount() {
                return 3;
            }
        };
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Odin Control Panel");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inv, Player player) {
        return new OdinControlPanelMenu(id, inv,this, this.data);
    }

    public static void tick(Level level, BlockPos blockPos, BlockState blockState, OdinControlPanelBlockEntity pEntity) {

    }
}
