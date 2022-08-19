package io.github.burritobandit28.any1_of_us.sounds;

import io.github.burritobandit28.any1_of_us.AnyoneOfUs;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class SoundEvents {

	public static  SoundEvent INVISIWATCH_ON;
	public static  SoundEvent INVISIWATCH_OFF;
	public static  SoundEvent CRIT;
	public static SoundEvent SPY_HURT;
	public static SoundEvent SPY_HURT_CRIT;
	public static SoundEvent SPY_HURT_DEATH;
	public static SoundEvent SPY_HURT_FIRE;

	public static void registerSoundEvents() {

		INVISIWATCH_ON = register("invisiwatch.on");
		INVISIWATCH_OFF = register("invisiwatch.off");
		CRIT = register("crit");
		SPY_HURT = register("spy_hurt");
		SPY_HURT_CRIT = register("spy_hurt_crit");
		SPY_HURT_DEATH = register("spy_hurt_death");
		SPY_HURT_FIRE = register("spy_hurt_fire");

	}

	private static SoundEvent register(String name) {
		Identifier id = AnyoneOfUs.ID(name);
		return Registry.register(Registry.SOUND_EVENT, id, new SoundEvent(id));
	}

}
