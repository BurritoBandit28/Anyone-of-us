package io.github.burritobandit28.any1_of_us;

import net.minecraft.util.Identifier;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;

import java.util.logging.Logger;

public class AnyoneOfUs implements ModInitializer {

	public static final String MOD_ID = "any1_of_us";

	public static Identifier ID(String name) {
		return new Identifier(MOD_ID, name);
	}

	public static final Logger LOGGER = Logger.getLogger(MOD_ID);

	@Override
	public void onInitialize(ModContainer mod) {
		LOGGER.info("Hellooo from anyone of us mod :)");
	}
}
