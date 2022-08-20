package io.github.burritobandit28.any1_of_us.mixins;

import io.github.burritobandit28.any1_of_us.items.KnifeItem;
import io.github.burritobandit28.any1_of_us.items.ModItems;
import io.github.burritobandit28.any1_of_us.items.SuitArmorItem;
import io.github.burritobandit28.any1_of_us.sounds.SoundEvents;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvent;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixinCommon extends LivingEntity {

	protected PlayerEntityMixinCommon(EntityType<? extends LivingEntity> entityType, World world) {
		super(entityType, world);
	}

	@Inject(at = @At("HEAD"), method = "getHurtSound", cancellable = true)
	public void doSpyHurtSound(DamageSource source, CallbackInfoReturnable<SoundEvent> cir) {
		if (isFrench((PlayerEntity)(Object)this)) {
			if (source.getSource() instanceof PlayerEntity attacker) {
				boolean bl  = attacker.getAttackCooldownProgress(0.5F) > 0.9f;
				boolean bl3 = bl && attacker.fallDistance > 0.0F && !attacker.isOnGround() && !attacker.isClimbing() && !attacker.isTouchingWater() && !attacker.hasStatusEffect(StatusEffects.BLINDNESS) && !attacker.hasVehicle();
				if (bl3) {
					cir.setReturnValue(SoundEvents.SPY_HURT_CRIT);
				}
			}
			else if (source.isFromFalling()) {
				cir.setReturnValue(SoundEvents.SPY_HURT_FALL);
			}
			else if (source.isFire()) {
				cir.setReturnValue(SoundEvents.SPY_HURT_FIRE);
			}
			else if (source == DamageSource.ANVIL || source == DamageSource.FALLING_STALACTITE || source == DamageSource.FLY_INTO_WALL) {
				cir.setReturnValue(SoundEvents.SPY_HURT_CRIT);
			}
			else {
				cir.setReturnValue(SoundEvents.SPY_HURT);
			}

		}
	}

	@Inject(at = @At("HEAD"), method = "getDeathSound", cancellable = true)
	public void doSpyDeathSound(CallbackInfoReturnable<SoundEvent> cir) {
		if (isFrench((PlayerEntity)(Object)this)) {
			cir.setReturnValue(SoundEvents.SPY_HURT_DEATH);
		}
	}

	@Inject(at = @At("RETURN"), method = "applyDamage", cancellable = true)
	public void doBackStabStuff(DamageSource source, float amount, CallbackInfo ci) {


		if(source.getAttacker() instanceof PlayerEntity) {

			PlayerEntity ThatSteveIsASpy = (PlayerEntity) source.getAttacker();

			ItemStack itemStack = ThatSteveIsASpy.getStackInHand(ThatSteveIsASpy.getActiveHand());

			if(itemStack.getItem() == ModItems.KNIFE) {
				KnifeItem knifeItem = (KnifeItem) ThatSteveIsASpy.getStackInHand(ThatSteveIsASpy.getActiveHand()).getItem();
				if (knifeItem.isBackStab()) {

					amount = 300;

					float h = this.getHealth();
					this.setHealth(h - amount);
					this.playSound(SoundEvents.CRIT,0.4f, 1.0f);

					ci.cancel();
				}
			}
		}
	}

	private boolean isFrench(PlayerEntity spy) {
		return spy.getInventory().armor.get(0).getItem() instanceof SuitArmorItem && spy.getInventory().armor.get(1).getItem() instanceof SuitArmorItem && spy.getInventory().armor.get(2).getItem() instanceof SuitArmorItem && spy.getInventory().armor.get(3).getItem() instanceof SuitArmorItem;
	}
}
