package io.github.burritobandit28.any1_of_us.items;

import io.github.burritobandit28.any1_of_us.effects.CloakedStatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class CloakingDeviceItem extends Item {

	private int cooldowntime;

	public CloakingDeviceItem(Settings settings) {
		super(settings);
	}

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		ItemStack itemStack = user.getStackInHand(hand);

		user.addStatusEffect(new StatusEffectInstance(CloakedStatusEffect.CLOAKED, 80, 0, false, false, true));

		return TypedActionResult.consume(itemStack);
	}

}
