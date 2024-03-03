package de.presti.odin.items;

import de.presti.odin.ODIN;
import de.presti.odin.blocks.BlockRegistry;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemRegistry {

    // Create a Deferred Register to hold Blocks which will all be registered under the "odin" namespace
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ODIN.MODID);

    public static final RegistryObject<Item> LAUNCH_STICK = ITEMS.register("launch_stick", () -> new Item(new Item.Properties().tab(ModCreativeModeTab.ODIN_TAB)));
    public static final RegistryObject<Item> ODIN_LASER_BLOCK_ITEM = ITEMS.register("odin_laser_block", () -> new BlockItem(BlockRegistry.ODIN_LASER_BLOCK.get(),
            new Item.Properties().tab(ModCreativeModeTab.ODIN_TAB)));

    public static void register(IEventBus event) {
        // Register the Deferred Register to the mod event bus so blocks get registered
        ITEMS.register(event);
    }

}
