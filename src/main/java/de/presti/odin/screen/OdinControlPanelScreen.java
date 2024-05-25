package de.presti.odin.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import de.presti.odin.ODIN;
import de.presti.odin.blocks.entities.OdinControlPanelBlockEntity;
import de.presti.odin.networking.MessagesRegistry;
import de.presti.odin.networking.packet.RequestOdinRayC2SPacket;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.NotNull;

import java.util.function.Predicate;

public class OdinControlPanelScreen extends Screen {
    private static final Component TITLE =
            Component.translatable("gui." + ODIN.MODID + ".control_panel");

    private static final ResourceLocation TEXTURE =
            new ResourceLocation("odin", "textures/gui/odin_control_panel.png");

    private final BlockPos position;
    private final int imageWidth, imageHeight;

    private OdinControlPanelBlockEntity blockEntity;
    private int leftPos, topPos;

    private Button button;
    private EditBox yEditBox, xEditBox, zEditBox;

    public OdinControlPanelScreen(BlockPos position) {
        super(TITLE);
        this.position = position;
        this.imageWidth = 176;
        this.imageHeight = 166;
    }

    @Override
    protected void init() {
        super.init();
        this.leftPos = (this.width - this.imageWidth) / 2;
        this.topPos = (this.height - this.imageHeight) / 2;

        if(this.minecraft == null) return;
        Level level = this.minecraft.level;
        if(level == null) return;

        BlockEntity be = level.getBlockEntity(this.position);
        if(be instanceof OdinControlPanelBlockEntity entity) {
            this.blockEntity = entity;
        } else {
            System.err.printf("BlockEntity at %s is not of type OdinControlPanelBlockEntity!\n", this.position);
            return;
        }

        button = addRenderableWidget(new Button(this.width / 2 - imageWidth / 4, topPos + imageHeight - 30, imageWidth / 2, 20, Component.literal("Strike"), (button) -> {
            // Request button action
            MessagesRegistry.sendToServer(new RequestOdinRayC2SPacket(position, Integer.parseInt(xEditBox.getValue()), Integer.parseInt(yEditBox.getValue()), Integer.parseInt(zEditBox.getValue())));
        }));

        yEditBox = addRenderableWidget(new EditBox(this.font, this.leftPos + 28, this.topPos + 30, 120, 20, Component.literal("Y")));
        xEditBox = addRenderableWidget(new EditBox(this.font, this.leftPos + 28, this.topPos + 60, 120, 20, Component.literal("X")));
        zEditBox = addRenderableWidget(new EditBox(this.font, this.leftPos + 28, this.topPos + 90, 120, 20, Component.literal("Z")));

        Predicate<String> filter = s -> {
            if (s.isEmpty()) return true;
            if (s.length() == 1 && s.charAt(0) == '-') return true;
            return s.matches("^-?\\d+$");
        };

        yEditBox.setFilter(filter);
        xEditBox.setFilter(filter);
        zEditBox.setFilter(filter);
    }

    @Override
    public void render(@NotNull PoseStack stack, int pMouseX, int pMouseY, float delta) {
        renderBackground(stack);
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.setShaderTexture(0, TEXTURE);
        this.blit(stack, leftPos, topPos, 0, 0, imageWidth, imageHeight);
        super.render(stack, pMouseX, pMouseY, delta);

        drawString(stack,
                this.font,
                TITLE,
                this.leftPos + 8,
                this.topPos + 8,
                0x7800FF);

        drawString(stack,
                this.font,
                "X",
                this.leftPos + 8,
                this.topPos + 30 + (this.font.lineHeight / 2),
                0x7800FF);

        drawString(stack,
                this.font,
                "Y",
                this.leftPos + 8,
                this.topPos + 60 + (this.font.lineHeight / 2),
                0x7800FF);

        drawString(stack,
                this.font,
                "Z",
                this.leftPos + 8,
                this.topPos + 90 + (this.font.lineHeight / 2),
                0x7800FF);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
