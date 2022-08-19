package io.github.burritobandit28.any1_of_us.mixins;

import io.github.burritobandit28.any1_of_us.items.SuitArmorItem;
import io.github.burritobandit28.any1_of_us.sounds.SoundEvents;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class AlsoPlayerEntityMixin extends LivingEntity {

	protected AlsoPlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
		super(entityType, world);
	}

	@Inject(at = @At("HEAD"), method = "getHurtSound", cancellable = true)
	public void doSpyHurtSound(DamageSource source, CallbackInfoReturnable<SoundEvent> cir) {
		if (isFrench((PlayerEntity)(Object)this)) {
			System.out.println("I am the sp- AAAAAAAAAAAAARRGHH");
			if (((PlayerEntity)(Object)this).getHealth() == 0) {
				cir.setReturnValue(SoundEvents.SPY_HURT_DEATH);
			}
			else if (source.getAttacker() instanceof PlayerEntity attacker) {
				boolean bl  = attacker.getAttackCooldownProgress(0.5F) > 0.9f;
				boolean bl3 = bl && attacker.fallDistance > 0.0F && !attacker.isOnGround() && !attacker.isClimbing() && !attacker.isTouchingWater() && !attacker.hasStatusEffect(StatusEffects.BLINDNESS) && !attacker.hasVehicle();
				if (bl3) {
					cir.setReturnValue(SoundEvents.SPY_HURT_CRIT);
				}
			}
			else if (source == DamageSource.ON_FIRE) {
				cir.setReturnValue(SoundEvents.SPY_HURT_FIRE);
			}
			else {
				cir.setReturnValue(SoundEvents.SPY_HURT);
			}

		}
	}

	private boolean isFrench(PlayerEntity spy) {
		return spy.getInventory().armor.get(0).getItem() instanceof SuitArmorItem && spy.getInventory().armor.get(1).getItem() instanceof SuitArmorItem && spy.getInventory().armor.get(2).getItem() instanceof SuitArmorItem && spy.getInventory().armor.get(3).getItem() instanceof SuitArmorItem;
	}
}
