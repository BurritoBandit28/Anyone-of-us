package io.github.burritobandit28.any1_of_us.mixins;

import io.github.burritobandit28.any1_of_us.effects.CloakedStatusEffect;
import io.github.burritobandit28.any1_of_us.items.ModItems;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;

@Mixin(PlayerEntityRenderer.class)
public abstract class PlayerEntityRendererMixin extends LivingEntityRenderer<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> {


	public PlayerEntityRendererMixin(EntityRendererFactory.Context context, PlayerEntityModel<AbstractClientPlayerEntity> entityModel, float f) {
		super(context, entityModel, f);
	}

	@Inject(at = @At("TAIL"), method = "getArmPose", cancellable = true)
	private static void doArmMovement(AbstractClientPlayerEntity player, Hand hand, CallbackInfoReturnable<BipedEntityModel.ArmPose> cir) {

		ItemStack itemStack = player.getStackInHand(hand);


		if (itemStack.getItem() == ModItems.KNIFE && Objects.requireNonNull(itemStack.getNbt()).getInt("Backstab") == 1) {
			cir.setReturnValue(BipedEntityModel.ArmPose.SPYGLASS);
		}
	}
}
