package io.github.burritobandit28.any1_of_us;

import io.github.burritobandit28.any1_of_us.armor.FrenchAttribute;
import io.github.burritobandit28.any1_of_us.effects.CloakedStatusEffect;
import io.github.burritobandit28.any1_of_us.items.ItemRegister;
import io.github.burritobandit28.any1_of_us.items.ModItems;
import io.github.burritobandit28.any1_of_us.sounds.SoundEvents;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.quiltmc.qsl.item.group.api.QuiltItemGroup;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AnyoneOfUs implements ModInitializer {

	public static ArrayList<String> shouldBeInvis = new ArrayList<>();

	public static final String MOD_ID = "any1_of_us";

	public static Identifier ID(String name) {
		return new Identifier(MOD_ID, name);
	}

	public static final Logger LOGGER = Logger.getLogger(MOD_ID);

	public static final QuiltItemGroup FRENCH_TAB = QuiltItemGroup.builder(ID("frenchtab"))
			.icon(() -> new ItemStack(ModItems.KNIFE))
			.build();

	public static boolean isStabable(float yaw1, float yaw2) {
		return (yaw1 > (yaw2 -10) && yaw1 < (yaw2 + 10));
	}

	@Override
	public void onInitialize(ModContainer mod) {
		LOGGER.info("Hellooo from anyone of us mod :)");
		FrenchAttribute.registerAttributes();
		ItemRegister.registerItems();
		CloakedStatusEffect.register();
		SoundEvents.registerSoundEvents();

	}
}
