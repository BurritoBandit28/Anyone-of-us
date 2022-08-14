package io.github.burritobandit28.any1_of_us.mixins;

import io.github.burritobandit28.any1_of_us.AnyoneOfUs;
import io.github.burritobandit28.any1_of_us.items.KnifeItem;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {

	protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
		super(entityType, world);
	}

	@Inject(at = @At("HEAD"),method = "tick")
	public void getEntityAhead(CallbackInfo ci) {
		if (!this.world.isClient) {
			return;
		}

		MinecraftClient client = MinecraftClient.getInstance();
		HitResult hit = client.crosshairTarget;

		assert hit != null;
		if (hit.getType() == HitResult.Type.ENTITY) {
			EntityHitResult entityHit = (EntityHitResult) hit;
			Entity entity = entityHit.getEntity();

			if (AnyoneOfUs.isStabable(entity.getYaw(), this.getYaw()) && this.getStackInHand(this.getActiveHand()).getItem() instanceof KnifeItem && entity != this) {

				NbtCompound nbt = this.getStackInHand(this.getActiveHand()).getOrCreateNbt();

				nbt.putInt("Backstab", 1);

				this.getStackInHand(this.getActiveHand()).setNbt(nbt);

				//System.out.println("back");

			}
			else if (!AnyoneOfUs.isStabable(entity.getYaw(), this.getYaw()) && this.getStackInHand(this.getActiveHand()).getItem() instanceof KnifeItem) {

				NbtCompound nbt = this.getStackInHand(this.getActiveHand()).getOrCreateNbt();

				nbt.putInt("Backstab", 0);

				this.getStackInHand(this.getActiveHand()).setNbt(nbt);

			}

		}

	}



}
