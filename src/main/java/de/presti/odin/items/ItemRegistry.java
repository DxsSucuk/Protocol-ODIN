package de.presti.odin.items;

import de.presti.odin.ODIN;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemRegistry {

    // Create a Deferred Register to hold Blocks which will all be registered under the "odin" namespace
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ODIN.MODID);

    public static final RegistryObject<Item> LAUNCH_STICK = ITEMS.register("launch_stick", () -> new Item(new Item.Properties().tab(ModCreativeModeTab.ODIN_TAB)));

    public static void register(IEventBus event) {
        // Register the Deferred Register to the mod event bus so blocks get registered
        ITEMS.register(event);
    }

}
