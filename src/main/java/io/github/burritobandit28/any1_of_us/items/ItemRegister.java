package io.github.burritobandit28.any1_of_us.items;

import io.github.burritobandit28.any1_of_us.AnyoneOfUs;
import net.fabricmc.fabric.api.object.builder.v1.client.model.FabricModelPredicateProviderRegistry;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ItemRegister {

	public static void registerItems() {

		Registry.register(Registry.ITEM, AnyoneOfUs.ID("knife"), ModItems.KNIFE);

		Registry.register(Registry.ITEM, AnyoneOfUs.ID("invisiwatch"), ModItems.CLOAKING_DEVICE);

		//BLU
		Registry.register(Registry.ITEM, AnyoneOfUs.ID("blu_balaclava"), ModItems.BLU_BALACLAVA);
		Registry.register(Registry.ITEM, AnyoneOfUs.ID("blu_suit_jacket"), ModItems.BLU_SUIT_JACKET);
		Registry.register(Registry.ITEM, AnyoneOfUs.ID("blu_suit_trousers"), ModItems.BLU_SUIT_TROUSERS);
		Registry.register(Registry.ITEM, AnyoneOfUs.ID("blu_shoes"), ModItems.BLU_SHOES);

		//RED
		Registry.register(Registry.ITEM, AnyoneOfUs.ID("red_balaclava"), ModItems.RED_BALACLAVA);
		Registry.register(Registry.ITEM, AnyoneOfUs.ID("red_suit_jacket"), ModItems.RED_SUIT_JACKET);
		Registry.register(Registry.ITEM, AnyoneOfUs.ID("red_suit_trousers"), ModItems.RED_SUIT_TROUSERS);
		Registry.register(Registry.ITEM, AnyoneOfUs.ID("red_shoes"), ModItems.RED_SHOES);

	}

}
