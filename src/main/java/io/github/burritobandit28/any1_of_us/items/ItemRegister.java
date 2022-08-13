package io.github.burritobandit28.any1_of_us.items;

import io.github.burritobandit28.any1_of_us.AnyoneOfUs;
import net.fabricmc.fabric.api.object.builder.v1.client.model.FabricModelPredicateProviderRegistry;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ItemRegister {

	public static void registerItems() {

		Registry.register(Registry.ITEM, AnyoneOfUs.ID("knife"), ModItems.KNIFE);

		FabricModelPredicateProviderRegistry.register(ModItems.KNIFE, new Identifier("backstab"), ((itemStack, clientWorld, livingEntity, i) -> {
			int val = 0;
			if (itemStack.getNbt() != null && itemStack.getNbt().contains("Backstab")) {
				val = itemStack.getNbt().getInt("Backstab");
			}
			return val;
		}));

		Registry.register(Registry.ITEM, AnyoneOfUs.ID("cloaking_device"), ModItems.CLOAKING_DEVICE);

	}

}
