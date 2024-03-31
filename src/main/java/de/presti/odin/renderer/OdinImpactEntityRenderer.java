package de.presti.odin.renderer;

import ballistix.client.ClientRegister;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
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
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import team.lodestar.lodestone.handlers.ScreenshakeHandler;
import team.lodestar.lodestone.systems.screenshake.ScreenshakeInstance;

@OnlyIn(Dist.CLIENT)
public class OdinImpactEntityRenderer extends EntityRenderer<OdinImpactEntity> {

    public OdinImpactEntityRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
        shadowRadius = 0.5f;
    }

    @Override
    public void render(OdinImpactEntity entityIn, float entityYaw, float partialTicks, PoseStack matrixStack, @NotNull MultiBufferSource bufferIn, int packedLightIn) {
        matrixStack.pushPose();
        int entityTick = entityIn.tickCount;

        float scale = (float) 0.1 * ((float) OdinConstants.EXPLOSION_RADIUS);
        BakedModel modelSphere = Minecraft.getInstance().getModelManager().getModel(ClientRegister.MODEL_DARKMATTERSPHERE);
        BakedModel modelBeam = Minecraft.getInstance().getModelManager().getModel(OdinConstants.BEACON_BEAM);

        boolean shouldShowImpact = entityTick > 10;

        if (shouldShowImpact) {
            matrixStack.pushPose();
            matrixStack.scale(scale, scale, scale);
            RenderingUtils.renderModel(modelSphere, null, RenderType.solid(), matrixStack, bufferIn, packedLightIn, packedLightIn);
            matrixStack.popPose();

            matrixStack.pushPose();
            matrixStack.scale(scale, scale, scale);
            RenderingUtils.renderStar(matrixStack, bufferIn, entityIn.tickCount + partialTicks, 60, 1, 1, 1, 0.3f, true);
            matrixStack.popPose();
        } else {
            matrixStack.pushPose();
            matrixStack.scale(scale / 2, scale * 50, scale / 2);
            RenderingUtils.renderModel(modelBeam, null, RenderType.solid(), matrixStack, bufferIn, packedLightIn, packedLightIn);
            matrixStack.popPose();
            matrixStack.pushPose();
            matrixStack.scale(scale / 8, scale / 8, scale / 8);
            RenderingUtils.renderModel(modelSphere, null, RenderType.solid(), matrixStack, bufferIn, packedLightIn, packedLightIn);
            matrixStack.popPose();
        }

        matrixStack.popPose();
        super.render(entityIn, entityYaw, partialTicks, matrixStack, bufferIn, packedLightIn);
    }

    @Override
    public boolean shouldRender(@NotNull OdinImpactEntity b, @NotNull Frustum f, double x, double y, double z) {
        return true;
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull OdinImpactEntity entity) {
        return InventoryMenu.BLOCK_ATLAS;
    }
}
