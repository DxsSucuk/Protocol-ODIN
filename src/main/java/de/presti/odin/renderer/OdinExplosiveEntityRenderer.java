package de.presti.odin.renderer;

import ballistix.client.ClientRegister;
import com.mojang.blaze3d.vertex.PoseStack;
import de.presti.odin.entities.OdinExplosiveEntity;
import de.presti.odin.entities.OdinImpactEntity;
import de.presti.odin.utils.OdinConstants;
import electrodynamics.prefab.utilities.RenderingUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class OdinExplosiveEntityRenderer extends EntityRenderer<OdinExplosiveEntity> {

    public OdinExplosiveEntityRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
        shadowRadius = 0.5f;
    }

    @Override
    public void render(@NotNull OdinExplosiveEntity entityIn, float entityYaw, float partialTicks, PoseStack matrixStack, @NotNull MultiBufferSource bufferIn, int packedLightIn) {
        matrixStack.pushPose();

        float scale = (float) 0.1 * ((float) OdinConstants.EXPLOSION_RADIUS);
        BakedModel modelSphere = Minecraft.getInstance().getModelManager().getModel(ClientRegister.MODEL_DARKMATTERSPHERE);

        matrixStack.pushPose();
        matrixStack.translate(0, 500, 0);
        matrixStack.scale(scale / 8, scale / 8, scale / 8);
        RenderingUtils.renderModel(modelSphere, null, RenderType.solid(), matrixStack, bufferIn, packedLightIn, packedLightIn);
        matrixStack.popPose();

        matrixStack.popPose();
        super.render(entityIn, entityYaw, partialTicks, matrixStack, bufferIn, packedLightIn);
    }

    @Override
    public boolean shouldRender(@NotNull OdinExplosiveEntity b, @NotNull Frustum f, double x, double y, double z) {
        return true;
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull OdinExplosiveEntity entity) {
        return InventoryMenu.BLOCK_ATLAS;
    }
}
