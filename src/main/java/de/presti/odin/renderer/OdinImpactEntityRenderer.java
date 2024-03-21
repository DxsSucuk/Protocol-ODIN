package de.presti.odin.renderer;

import ballistix.client.ClientRegister;
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
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class OdinImpactEntityRenderer extends EntityRenderer<OdinImpactEntity> {

    public OdinImpactEntityRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
        shadowRadius = 0.5f;
    }

    @Override
    public void render(OdinImpactEntity entityIn, float entityYaw, float partialTicks, PoseStack matrixStack, MultiBufferSource bufferIn, int packedLightIn) {
        matrixStack.pushPose();
        double tickCount = entityIn.tickCount;
        double time = 4.0 / 3.0 * Math.PI * Math.pow(OdinConstants.EXPLOSION_RADIUS, 3);
        float scale = (float) (0.1 * Math.log(tickCount * tickCount) + tickCount / (time * 2));
        //BakedModel modelDisk = Minecraft.getInstance().getModelManager().getModel(ClientRegister.MODEL_DARKMATTERDISK);
        BakedModel modelSphere = Minecraft.getInstance().getModelManager().getModel(ClientRegister.MODEL_DARKMATTERSPHERE);

        float animationRadians = (entityIn.tickCount + partialTicks) * 0.05f;

        matrixStack.pushPose();
        matrixStack.scale(scale * 6, scale * 6, scale * 6);
        matrixStack.mulPose(new Quaternion(new Vector3f(0, 1, 0), -animationRadians, false));
        matrixStack.mulPose(new Quaternion(new Vector3f(1, 0, 0), -animationRadians, false));
        matrixStack.mulPose(new Quaternion(new Vector3f(0, 0, 1), -animationRadians, false));
        RenderingUtils.renderModel(modelSphere, null, RenderType.solid(), matrixStack, bufferIn, packedLightIn, packedLightIn);
        matrixStack.popPose();

        /*matrixStack.pushPose();
        matrixStack.translate(0, 0.5, 0);
        matrixStack.scale(scale, scale, scale);
        matrixStack.mulPose(new Quaternion(new Vector3f(0, 1, 0), -animationRadians, false));
        matrixStack.scale(1.25f, 1.25f, 1.25f);
        RenderingUtils.renderModel(modelDisk, null, RenderType.translucent(), matrixStack, bufferIn, packedLightIn, packedLightIn);
        matrixStack.popPose();*/

        matrixStack.pushPose();
        matrixStack.scale(scale, scale, scale);
        RenderingUtils.renderStar(matrixStack, bufferIn, entityIn.tickCount + partialTicks, 60, 1, 1, 1, 0.3f, true);
        matrixStack.popPose();

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
