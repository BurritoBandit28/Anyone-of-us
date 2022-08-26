package io.github.burritobandit28.any1_of_us.mixins;

import io.github.burritobandit28.any1_of_us.AnyoneOfUs;
import io.github.burritobandit28.any1_of_us.effects.CloakedStatusEffect;
import io.github.burritobandit28.any1_of_us.items.KnifeItem;
import io.github.burritobandit28.any1_of_us.items.ModItems;
import io.github.burritobandit28.any1_of_us.sounds.SoundEvents;
import net.minecraft.advancement.Advancement;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.data.server.AdvancementsProvider;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.quiltmc.loader.api.QuiltLoader;
import org.quiltmc.qsl.networking.api.PacketByteBufs;
import org.quiltmc.qsl.networking.api.PlayerLookup;
import org.quiltmc.qsl.networking.api.ServerPlayNetworking;
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

	public LivingEntityMixin(EntityType<?> entityType, World world) {
		super(entityType, world);
	}

	@Shadow
	public abstract boolean hasStatusEffect(StatusEffect effect);

	@Shadow
	public abstract float getHealth();

	@Shadow
	public abstract void setHealth(float health);


	@Inject(at = @At("TAIL"), method = "updatePotionVisibility", cancellable = true)
	public void cloakingInvis(CallbackInfo ci) {
		if (this.hasStatusEffect(CloakedStatusEffect.CLOAKED) || this.hasStatusEffect(StatusEffects.INVISIBILITY)) {
			this.setInvisible(true);
		}
		if ((LivingEntity)(Object)this instanceof PlayerEntity) {

			PlayerEntity user = (PlayerEntity) (LivingEntity)(Object)this;

			PacketByteBuf buf = PacketByteBufs.create();

			buf.writeBoolean(this.hasStatusEffect(CloakedStatusEffect.CLOAKED));
			buf.writeString(this.getEntityName());

			for (ServerPlayerEntity player : PlayerLookup.tracking(this)) {
				ServerPlayNetworking.send(player, AnyoneOfUs.ID("cloaked_packet"), buf);
			}
			ServerPlayNetworking.send( (ServerPlayerEntity) user, AnyoneOfUs.ID("cloaked_packet"), buf);
			ci.cancel();
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






}
