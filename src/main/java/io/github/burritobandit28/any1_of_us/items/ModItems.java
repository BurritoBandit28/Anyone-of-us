package io.github.burritobandit28.any1_of_us.items;

import io.github.burritobandit28.any1_of_us.AnyoneOfUs;
import net.minecraft.item.Item;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterials;

public class ModItems {


	public static final SwordItem KNIFE = new SwordItem(ToolMaterials.IRON, 3, 100, new Item.Settings()
			.group(AnyoneOfUs.FRENCH_TAB));

	public static final Item CLOAKING_DEVICE = new CloakingDeviceItem(new Item.Settings()
			.group(AnyoneOfUs.FRENCH_TAB)
			.maxCount(1));
}
