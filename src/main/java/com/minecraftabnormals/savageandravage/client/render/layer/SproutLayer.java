package com.minecraftabnormals.savageandravage.client.render.layer;

import com.minecraftabnormals.savageandravage.common.entity.CreepieEntity;
import com.minecraftabnormals.savageandravage.core.SRConfig;
import com.minecraftabnormals.savageandravage.core.SavageAndRavage;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SproutLayer<E extends CreepieEntity, M extends EntityModel<E>> extends RenderLayer<E, M> {
	private static final ResourceLocation SPROUT_TEXTURE = new ResourceLocation(SavageAndRavage.MOD_ID, "textures/entity/creepie_sprout.png");

	public SproutLayer(RenderLayerParent<E, M> entityRenderer) {
		super(entityRenderer);
	}

	@Override
	public void render(PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn, E entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
		if (!SRConfig.CLIENT.creepieSprout.get() || entity.isInvisible()) return;
		matrixStackIn.pushPose();
		matrixStackIn.translate(0.0F, 0.125F, 0.0F);
		matrixStackIn.scale(1.5F, 1.5F, 1.5F);
		VertexConsumer builder = bufferIn.getBuffer(RenderType.entityCutoutNoCull(SPROUT_TEXTURE));
		this.getParentModel().setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
		this.getParentModel().renderToBuffer(matrixStackIn, builder, packedLightIn, LivingEntityRenderer.getOverlayCoords(entity, 0.0F), 1.0F, 1.0F, 1.0F, 1.0F);
		matrixStackIn.popPose();
	}
}