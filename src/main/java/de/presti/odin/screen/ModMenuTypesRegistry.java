package de.presti.odin.screen;

import de.presti.odin.ODIN;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModMenuTypesRegistry {

    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(ForgeRegistries.MENU_TYPES, ODIN.MODID);

    public static final RegistryObject<MenuType<OdinControlPanelMenu>> ODIN_CONTROL_PANEL_MENU = registerMenuType(OdinControlPanelMenu::new, "odin_control_panel");

    public static void register(IEventBus eventBus) {
        MENUS.register(eventBus);
    }

    public static <T extends AbstractContainerMenu> RegistryObject<MenuType<T>> registerMenuType(IContainerFactory<T> factory, String name) {
        return MENUS.register(name, () -> IForgeMenuType.create(factory));
    }
}
