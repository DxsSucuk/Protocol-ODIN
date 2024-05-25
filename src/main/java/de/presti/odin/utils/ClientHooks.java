package de.presti.odin.utils;

import de.presti.odin.screen.OdinControlPanelScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;

public class ClientHooks {
    public static void openOdinScreen(BlockPos pos) {
        Minecraft.getInstance().setScreen(new OdinControlPanelScreen(pos));
    }
}