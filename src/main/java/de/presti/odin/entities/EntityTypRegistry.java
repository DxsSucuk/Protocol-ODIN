package de.presti.odin.entities;

import de.presti.odin.ODIN;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class EntityTypRegistry {
    private static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, ODIN.MODID);

    public static final RegistryObject<EntityType<OdinExplosiveEntity>> ODIN_EXPLOSIVE = ENTITY_TYPES.register("odin_explosiv", () -> {
        return EntityType.Builder.of(OdinExplosiveEntity::new, MobCategory.MISC)
                .sized(0.98F, 0.98F)
                .clientTrackingRange(10)
                .updateInterval(20)
                .build(ODIN.MODID + ":odin_explosiv");
    });

    public static final RegistryObject<EntityType<OdinImpactEntity>> ODIN_IMPACT = ENTITY_TYPES.register("odin_impact", () -> {
        return EntityType.Builder.of(OdinImpactEntity::new, MobCategory.MISC)
                .sized(0.98F, 0.98F)
                .clientTrackingRange(20)
                .updateInterval(20)
                .build(ODIN.MODID + ":odin_impact");
    });


    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
