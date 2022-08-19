package io.github.burritobandit28.any1_of_us.items;

import io.github.burritobandit28.any1_of_us.AnyoneOfUs;
import io.github.burritobandit28.any1_of_us.effects.CloakedStatusEffect;
import io.github.burritobandit28.any1_of_us.sounds.SoundEvents;
import net.fabricmc.api.Environment;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.quiltmc.loader.api.QuiltLoader;
import org.quiltmc.qsl.networking.api.PacketByteBufs;
import org.quiltmc.qsl.networking.api.PlayerLookup;
import org.quiltmc.qsl.networking.api.ServerPlayNetworking;

import java.util.Objects;
import java.util.UUID;

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

		if (world.isClient()) return super.use(world, user, hand);




			this.on = !this.on;
			this.calls = 0;

			if (this.on) {
				user.addStatusEffect(new StatusEffectInstance(CloakedStatusEffect.CLOAKED, this.timeRemaining, 0, false, false, true));

				NbtCompound nbt = itemStack.getOrCreateNbt();

				assert nbt != null;
				nbt.putInt("Active", 1);

				itemStack.writeNbt(nbt);

				world.playSound(user, user.getBlockPos(), SoundEvents.INVISIWATCH_ON, SoundCategory.PLAYERS, 1f, 1f);
				user.playSound(SoundEvents.INVISIWATCH_ON, SoundCategory.PLAYERS, 1f, 1f);

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

				world.playSound(user, user.getBlockPos(), SoundEvents.INVISIWATCH_OFF, SoundCategory.PLAYERS, 1f, 1f);
				user.playSound(SoundEvents.INVISIWATCH_OFF, SoundCategory.PLAYERS, 1f, 1f);

			}

			/*
		if (!world.isClient) {
			PacketByteBuf buf = PacketByteBufs.create();

			buf.writeBoolean(on);
			buf.writeString(user.getEntityName());s

			for (ServerPlayerEntity player : PlayerLookup.tracking(user)) {
				ServerPlayNetworking.send(player, AnyoneOfUs.ID("cloaked_packet"), buf);
			}
			ServerPlayNetworking.send((ServerPlayerEntity) user, AnyoneOfUs.ID("cloaked_packet"), buf);
		}


			 */

		return TypedActionResult.consume(itemStack);

	}

	public static class CloakedClassBecauseImLazyAndThisIsEasy {

		public String name;
		public boolean on;

		public CloakedClassBecauseImLazyAndThisIsEasy(String name, boolean on) {
			this.name = name;
			this.on = on;
		}
	}

}
