package de.presti.odin.entities;

import de.presti.odin.blast.BlastOdinRay;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.level.Level;

public class OdinExplosiveEntity extends PrimedTnt {

    private static final EntityDataAccessor<Integer> DATA_FUSE_ID = SynchedEntityData.defineId(OdinExplosiveEntity.class, EntityDataSerializers.INT);
    private static final int DEFAULT_FUSE_TIME = 80;

    public OdinExplosiveEntity(EntityType<? extends PrimedTnt> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        setFuse(DEFAULT_FUSE_TIME);
        this.blocksBuilding = true;
    }

    @Override
    public void tick() {
        if (!this.isNoGravity()) {
            this.setDeltaMovement(this.getDeltaMovement().add(0.0D, -0.04D, 0.0D));
        }

        this.move(MoverType.SELF, this.getDeltaMovement());
        this.setDeltaMovement(this.getDeltaMovement().scale(0.98D));
        if (this.onGround) {
            this.setDeltaMovement(this.getDeltaMovement().multiply(0.7D, -0.5D, 0.7D));
        }

        int i = this.getFuse() - 1;
        this.setFuse(i);
        if (i <= 0) {
            if (!this.level.isClientSide) {
                this.explode();
            }
        } else {
            this.updateInWaterStateAndDoFluidPushing();
            if (this.level.isClientSide) {
                this.level.addParticle(ParticleTypes.SMOKE, this.getX(), this.getY() + 0.5D, this.getZ(), 0.0D, 0.0D, 0.0D);
            }
        }

    }

    @Override
    protected void defineSynchedData() {
        this.entityData.define(DATA_FUSE_ID, DEFAULT_FUSE_TIME);
    }

    @Override
    public void setFuse(int pLife) {
        this.entityData.set(DATA_FUSE_ID, pLife);
    }

    @Override
    public int getFuse() {
        return this.entityData.get(DATA_FUSE_ID);
    }

    @Override
    public boolean isPickable() {
        return false;
    }


    boolean stopCalling = false;

    BlastOdinRay blast;

    int callCount = 0;

    @Override
    protected void explode() {
        if (blast == null) {
            blast = new BlastOdinRay(getLevel(), new BlockPos(this.getX(), this.getY(), this.getZ()));
            blast.doPreExplode();
        }

        if (isThreadFinished() && !stopCalling) {
            if (blast.doExplode(callCount)) {
                blast.doPostExplode();
                stopCalling = true;
                EntityTypRegistry.ODIN_IMPACT.get().spawn((ServerLevel) getLevel(), null, null, new BlockPos(this.getX(), this.getY(), this.getZ()), MobSpawnType.EVENT, true, true);
                removeAfterChangingDimensions();
            }
        }

        if (!stopCalling)
            callCount++;
    }

    public boolean isThreadFinished() {
        if (blast == null) return false;
        return blast.isThreadFinished();
    }
}
