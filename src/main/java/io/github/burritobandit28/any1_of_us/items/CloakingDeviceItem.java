package io.github.burritobandit28.any1_of_us.items;

import io.github.burritobandit28.any1_of_us.effects.CloakedStatusEffect;
import io.github.burritobandit28.any1_of_us.sounds.SoundEvents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.EnderPearlItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.Util;
import net.minecraft.world.World;

import java.util.Objects;

public class CloakingDeviceItem extends Item {

	private int timeUsed;

	private int timeRemaining = 200;
	private int calls;

	private int ticksPast;
	private boolean on = false;

	public CloakingDeviceItem(Settings settings) {
		super(settings);

		NbtCompound nbt = this.getDefaultStack().getOrCreateNbt();

		nbt.putInt("Active", 0);

		getDefaultStack().writeNbt(nbt);

	}



	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		ItemStack itemStack = user.getStackInHand(hand);

		this.calls ++;

		if (this.calls == 2) {
			this.on = !this.on;
			this.calls = 0;

			if (this.on) {
				user.addStatusEffect(new StatusEffectInstance(CloakedStatusEffect.CLOAKED, this.timeRemaining, 0, false, false, true));

				NbtCompound nbt = itemStack.getOrCreateNbt();

				assert nbt != null;
				nbt.putInt("Active", 1);

				itemStack.writeNbt(nbt);

				user.playSound(SoundEvents.INVISIWATCH_ON, SoundCategory.PLAYERS, 1.0f, 1.0f);

			}
			else  {

				if (user.getStatusEffect(CloakedStatusEffect.CLOAKED) == null) {
					timeUsed = 0;
				}

				else {
					this.timeUsed = Objects.requireNonNull(user.getStatusEffect(CloakedStatusEffect.CLOAKED)).getDuration();
				}

				this.timeRemaining = this.timeUsed;

				user.setStatusEffect(new StatusEffectInstance(CloakedStatusEffect.CLOAKED, 0, 0, false, false, true), user);
				if (this.timeRemaining == 0){
					user.getItemCooldownManager().set(this, 600);
					this.timeRemaining = 200;
				}

				NbtCompound nbt = itemStack.getOrCreateNbt();

				assert nbt != null;
				nbt.putInt("Active", 0);

				itemStack.writeNbt(nbt);

				user.playSound(SoundEvents.INVISIWATCH_OFF, SoundCategory.PLAYERS, 1.0f, 1.0f);

			}
		}



		return TypedActionResult.consume(itemStack);

	}
}
