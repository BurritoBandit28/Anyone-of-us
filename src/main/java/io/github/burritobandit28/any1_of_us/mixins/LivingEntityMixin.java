package io.github.burritobandit28.any1_of_us.mixins;

import io.github.burritobandit28.any1_of_us.effects.CloakedStatusEffect;
import io.github.burritobandit28.any1_of_us.items.ModItems;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;
import java.util.Objects;

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

	@Shadow
	public abstract void kill();

	@Inject(at = @At("TAIL"), method = "updatePotionVisibility")
	public void cloakingInvis(CallbackInfo ci) {
		this.setInvisible(this.hasStatusEffect(CloakedStatusEffect.CLOAKED));
	}

	@Inject(at = @At("HEAD"), method = "damage", cancellable = true)
	public void doBackStabStuff(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
		if(source.getAttacker() instanceof PlayerEntity) {
			PlayerEntity ThatSteveIsASpy = (PlayerEntity) source.getAttacker();
			if(ThatSteveIsASpy.getStackInHand(ThatSteveIsASpy.getActiveHand()).getItem() == ModItems.KNIFE && Objects.requireNonNull(ThatSteveIsASpy.getStackInHand(ThatSteveIsASpy.getActiveHand()).getNbt()).getInt("Backstab") == 1) {

				//epic debug lines
				System.out.println("aaaaaaaaaaaaaa");
				System.out.println(this.getName());

				this.setVelocity(this.getRotationVector().x * 100, this.getRotationVector().y * 100, this.getRotationVector().z *100);
				this.kill();
				cir.setReturnValue(true);
			}
		}
	}




}
