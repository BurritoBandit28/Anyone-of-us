package io.github.burritobandit28.any1_of_us.mixins;

import io.github.burritobandit28.any1_of_us.effects.CloakedStatusEffect;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.ElytraFeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Arm;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

import static io.github.burritobandit28.any1_of_us.AnyoneOfUsClient.isCloaked;

@Mixin(ElytraFeatureRenderer.class)
public abstract class ElytraFeatureRendererMixin<T extends LivingEntity, M extends EntityModel<T>> extends FeatureRenderer<T, M> {
	public ElytraFeatureRendererMixin(FeatureRendererContext<T, M> featureRendererContext) {
		super(featureRendererContext);
	}

	@Inject(at = @At("HEAD"), method = "render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/entity/LivingEntity;FFFFFF)V", cancellable = true)
	public void cancelElytraRender(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, T livingEntity, float f, float g, float h, float j, float k, float l, CallbackInfo ci) {

		for (int iii = 0; iii < isCloaked.size() && isCloaked.get(iii) != null; iii++) {
			if (Objects.equals(isCloaked.get(iii).name, livingEntity.getEntityName())) {
				if(isCloaked.get(iii).on) {
					ci.cancel();
				}
			}
		}
		if (livingEntity.hasStatusEffect(CloakedStatusEffect.CLOAKED)) {
			ci.cancel();
		}

	}
}
