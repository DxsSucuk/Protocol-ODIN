package de.presti.odin.blast;

import ballistix.common.blast.Blast;
import ballistix.common.blast.thread.ThreadSimpleBlast;
import ballistix.common.block.subtype.SubtypeBlast;
import ballistix.common.settings.Constants;
import ballistix.registers.BallistixSounds;
import de.presti.odin.utils.OdinConstants;
import electrodynamics.api.sound.SoundAPI;
import electrodynamics.prefab.utilities.WorldUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;

import java.util.Iterator;
import java.util.logging.Logger;

public class BlastOdinRay extends Blast {

    public BlastOdinRay(Level world, BlockPos position) {
        super(world, position);
    }

    @Override
    public void doPreExplode() {
        if (!world.isClientSide) {
            thread = new ThreadSimpleBlast(world, position, (int) OdinConstants.EXPLOSION_RADIUS, Integer.MAX_VALUE, null, true);
            thread.start();
        } else {
            SoundAPI.playSound(BallistixSounds.SOUND_ANTIMATTEREXPLOSION.get(), SoundSource.BLOCKS, 50, 1, position);
        }
    }

    private ThreadSimpleBlast thread;
    private int pertick = -1;

    @Override
    public SubtypeBlast getBlastType() {
        return SubtypeBlast.thermobaric;
    }

    private Iterator<BlockPos> cachedIterator;

    @Override
    public boolean doExplode(int i) {
        if (!world.isClientSide) {
            if (thread == null) {
                Logger.getAnonymousLogger().info("Thread is null");
                return true;
            }
            if (thread.isComplete) {
                Logger.getAnonymousLogger().info("Hit it!");
                hasStarted = true;
                if (pertick == -1) {
                    pertick = thread.results.size() / (int) OdinConstants.EXPLOSION_RADIUS + 1;
                    cachedIterator = thread.results.iterator();
                }
                int finished = pertick;
                while (cachedIterator.hasNext()) {
                    if (finished-- < 0) {
                        break;
                    }
                    BlockPos p = new BlockPos(cachedIterator.next()).offset(position);
                    WorldUtils.fastRemoveBlockExplosion((ServerLevel) world, p);
                }
                if (!cachedIterator.hasNext()) {
                    WorldUtils.clearChunkCache();
                    position = position.above().above();
                    attackEntities((float) Constants.EXPLOSIVE_LARGEANTIMATTER_RADIUS * 2, false);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean isInstantaneous() {
        return true;
    }

    public boolean isThreadFinished() {
        return thread.isComplete;
    }
}
