package de.presti.odin.screen;

import de.presti.odin.blocks.BlockRegistry;
import de.presti.odin.blocks.entities.OdinControlPanelBlockEntity;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.Nullable;

public class OdinControlPanelMenu extends AbstractContainerMenu {

    public final OdinControlPanelBlockEntity blockEntity;
    private final Level level;
    private final ContainerData data;

    public OdinControlPanelMenu(int i, Inventory inventory, FriendlyByteBuf friendlyByteBuf) {
        this(i, inventory.player.level, friendlyByteBuf);
    }

    public OdinControlPanelMenu(int id, Level level, FriendlyByteBuf buf) {
        this(id, level.getBlockEntity(buf.readBlockPos()), new SimpleContainerData(3));
    }

    public OdinControlPanelMenu(int id,  BlockEntity entity, ContainerData data) {
        this(id, null, entity, data);
    }

    public OdinControlPanelMenu(int id, Inventory inventory, BlockEntity entity, ContainerData data) {
        super(ModMenuTypesRegistry.ODIN_CONTROL_PANEL_MENU.get(), id);
        checkContainerSize(inventory, 3);
        this.blockEntity = (OdinControlPanelBlockEntity) entity;
        this.level = blockEntity.getLevel();
        this.data = data;
    }

    @Override
    public ItemStack quickMoveStack(Player p_38941_, int p_38942_) {
        return null;
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()), player, BlockRegistry.ODIN_CONTROL_PANEL.get());
    }
}
