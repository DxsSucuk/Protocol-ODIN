package de.presti.odin.blocks;

import de.presti.odin.ODIN;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlockRegistry {

    // Create a Deferred Register to hold Blocks which will all be registered under the "odin" namespace
    private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, ODIN.MODID);

    public static final RegistryObject<Block> ODIN_LASER_BLOCK = BLOCKS.register("odin_laser_block", () -> new Block(BlockBehaviour.Properties.of(Material.STONE)));

    public static void register(IEventBus event) {
        // Register the Deferred Register to the mod event bus so blocks get registered
        BLOCKS.register(event);
    }

}
