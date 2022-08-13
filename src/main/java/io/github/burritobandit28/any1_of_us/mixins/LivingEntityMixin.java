package io.github.burritobandit28.any1_of_us.mixins;

import io.github.burritobandit28.any1_of_us.effects.CloakedStatusEffect;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

	@Shadow
	@Final
	private Map<StatusEffect, StatusEffectInstance> activeStatusEffects;

	public LivingEntityMixin(EntityType<?> entityType, World world) {
		super(entityType, world);
	}

	@Shadow
	public abstract boolean hasStatusEffect(StatusEffect effect);

	@Inject(at = @At("TAIL"), method = "updatePotionVisibility")
	public void cloakingInvis(CallbackInfo ci) {
		this.setInvisible(this.hasStatusEffect(CloakedStatusEffect.CLOAKED));
	}




}
