package de.presti.odin.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class OdinControlPanelScreen extends AbstractContainerScreen<OdinControlPanelMenu> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation("odin", "textures/gui/odin_control_panel.png");

    public OdinControlPanelScreen(OdinControlPanelMenu menu, Inventory inv, Component comp) {
        super(menu, inv, comp);
    }

    @Override
    protected void init() {
        super.init();
    }

    @Override
    protected void renderBg(PoseStack stack, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        this.blit(stack, x, y, 0, 0, imageWidth, imageHeight);
    }

    @Override
    public void render(PoseStack stack, int pMouseX, int pMouseY, float delta) {
        renderBackground(stack);
        super.render(stack, pMouseX, pMouseY, delta);
        renderTooltip(stack, pMouseX, pMouseY);
    }
}
