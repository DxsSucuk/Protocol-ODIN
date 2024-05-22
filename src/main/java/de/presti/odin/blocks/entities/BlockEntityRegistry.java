package de.presti.odin.blocks.entities;

import de.presti.odin.ODIN;
import de.presti.odin.blocks.BlockRegistry;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlockEntityRegistry {
    private static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, ODIN.MODID);

    public static final RegistryObject<BlockEntityType<OdinControlPanelBlockEntity>> ODIN_CONTROL_PANEL = BLOCK_ENTITIES.register("odin_control_panel", () -> BlockEntityType.Builder.of(OdinControlPanelBlockEntity::new, BlockRegistry.ODIN_CONTROL_PANEL.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
