package de.presti.odin;

import com.mojang.logging.LogUtils;
import de.presti.odin.blocks.BlockRegistry;
import de.presti.odin.blocks.entities.BlockEntityRegistry;
import de.presti.odin.entities.EntityTypRegistry;
import de.presti.odin.items.ItemRegistry;
import de.presti.odin.networking.ModMessages;
import de.presti.odin.renderer.OdinExplosiveEntityRenderer;
import de.presti.odin.renderer.OdinImpactEntityRenderer;
import de.presti.odin.screen.ModMenuTypesRegistry;
import de.presti.odin.screen.OdinControlPanelScreen;
import de.presti.odin.sound.ModSounds;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(ODIN.MODID)
public class ODIN {

    // Define mod id in a common place for everything to reference
    public static final String MODID = "odin";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    public ODIN() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Register the Deferred Register to the mod event bus so blocks get registered
        BlockRegistry.register(modEventBus);

        // Register the Deferred Register to the mod event bus so items get registered
        ItemRegistry.register(modEventBus);

        BlockEntityRegistry.register(modEventBus);

        ModSounds.register(modEventBus);

        EntityTypRegistry.register(modEventBus);
        ModMenuTypesRegistry.register(modEventBus);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        // Some common setup code
        LOGGER.info("HELLO FROM COMMON SETUP");
        ModMessages.register();
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            // Some client setup code
            LOGGER.info("HELLO FROM CLIENT SETUP");
            LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());

            //MenuScreens.register(ModMenuTypesRegistry.ODIN_CONTROL_PANEL_MENU.get(), OdinControlPanelScreen::new);
        }

        @SubscribeEvent
        public static void entityRenderers(EntityRenderersEvent.RegisterRenderers event) {
            LOGGER.info("HELLO FROM ENTITY RENDERERS");
            event.registerEntityRenderer(EntityTypRegistry.ODIN_IMPACT.get(), OdinImpactEntityRenderer::new);
            event.registerEntityRenderer(EntityTypRegistry.ODIN_EXPLOSIVE.get(), OdinExplosiveEntityRenderer::new);
        }
    }
}
