package io.github.burritobandit28.any1_of_us.effects;

import io.github.burritobandit28.any1_of_us.AnyoneOfUs;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.util.registry.Registry;

public class CloakedStatusEffect {

	public static final StatusEffect CLOAKED = new FunkyThingBecauseStatusEffectIsWeird(StatusEffectType.BENEFICIAL, 0);

	public static void register() {
		Registry.register(Registry.STATUS_EFFECT, AnyoneOfUs.ID("cloaked"), CLOAKED);
	}

}
