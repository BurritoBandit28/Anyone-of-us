package io.github.burritobandit28.any1_of_us.sounds;

import io.github.burritobandit28.any1_of_us.AnyoneOfUs;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class SoundEvents {

	public static  SoundEvent INVISIWATCH_ON;
	public static  SoundEvent INVISIWATCH_OFF;
	public static  SoundEvent CRIT;

	public static void registerSoundEvents() {

		INVISIWATCH_ON = register("invisiwatch.on");
		INVISIWATCH_OFF = register("invisiwatch.off");
		CRIT = register("crit");

	}

	private static SoundEvent register(String name) {
		Identifier id = AnyoneOfUs.ID(name);
		return Registry.register(Registry.SOUND_EVENT, id, new SoundEvent(id));
	}

}
