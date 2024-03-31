package de.presti.odin.entities;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import team.lodestar.lodestone.handlers.ScreenshakeHandler;
import team.lodestar.lodestone.systems.screenshake.ScreenshakeInstance;

public class OdinImpactEntity extends Entity {

    int lifetimeInTicks = 200;

    public OdinImpactEntity(EntityType<? extends OdinImpactEntity> p_19870_, Level p_19871_) {
        super(p_19870_, p_19871_);
    }

    boolean effect = true;

    @Override
    public void tick() {
        if (this.tickCount == 1 && effect) {
            ScreenshakeHandler.addScreenshake(new ScreenshakeInstance(10).setIntensity(0.3f));
            effect = false;
        }

        if (this.tickCount > lifetimeInTicks) {
            removeAfterChangingDimensions();
        }

        super.tick();
    }

    @Override
    protected void defineSynchedData() {

    }

    @Override
    protected void readAdditionalSaveData(CompoundTag p_20052_) {

    }

    @Override
    protected void addAdditionalSaveData(CompoundTag p_20139_) {

    }

    @Override
    public @NotNull Packet<?> getAddEntityPacket() {
        return new ClientboundAddEntityPacket(this);
    }
}
