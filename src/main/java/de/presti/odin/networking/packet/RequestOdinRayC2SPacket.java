package de.presti.odin.networking.packet;

import de.presti.odin.blocks.entities.OdinControlPanelBlockEntity;
import de.presti.odin.entities.EntityTypRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.network.NetworkEvent;

import java.time.Duration;
import java.util.function.Supplier;

public class RequestOdinRayC2SPacket {
    private static final String MESSAGE_CONTROL_SHOOT = "message.odin.control_panel.shoot";
    private static final String MESSAGE_CONTROL_COOLDOWN = "message.odin.control_panel.cooldown";

    private final BlockPos position;
    private final int x, y, z;

    public RequestOdinRayC2SPacket() {
        this.position = null;
        this.x = 0;
        this.y = 0;
        this.z = 0;
    }

    public RequestOdinRayC2SPacket(BlockPos position, int x, int y, int z) {
        this.position = position;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public RequestOdinRayC2SPacket(FriendlyByteBuf buf) {
        this.position = buf.readBlockPos();
        this.x = buf.readInt();
        this.y = buf.readInt();
        this.z = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeBlockPos(this.position);
        buf.writeInt(this.x);
        buf.writeInt(this.y);
        buf.writeInt(this.z);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // HERE WE ARE ON THE SERVER!
            ServerPlayer player = context.getSender();

            if (player == null) {
                return;
            }

            ServerLevel level = player.getLevel();

            if (level.getBlockEntity(this.position) instanceof OdinControlPanelBlockEntity entity) {
                if (System.currentTimeMillis() - entity.getLastTime() < Duration.ofMinutes(15).toMillis()) {
                    player.sendSystemMessage(Component.translatable(MESSAGE_CONTROL_COOLDOWN, Duration.ofMillis(System.currentTimeMillis() - entity.getLastTime()).toSeconds()).withStyle(ChatFormatting.RED));
                    return;
                }

                entity.setLastTime(System.currentTimeMillis());
                EntityTypRegistry.ODIN_EXPLOSIVE.get().spawn(level, null, null, new BlockPos(this.x, this.y, this.z), MobSpawnType.EVENT, true, true);
                player.sendSystemMessage(Component.translatable(MESSAGE_CONTROL_SHOOT).withStyle(ChatFormatting.GREEN));
                //OdinControlPanelBlockEntity.tick(level, this.position, level.getBlockState(this.position), entity);
            }
        });
        return true;
    }
}