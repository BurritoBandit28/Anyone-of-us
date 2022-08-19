package io.github.burritobandit28.any1_of_us.items;

import io.github.burritobandit28.any1_of_us.AnyoneOfUs;
import io.github.burritobandit28.any1_of_us.armor.FrenchMaterial;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterials;

public class ModItems {


	public static final Item KNIFE = new KnifeItem(ToolMaterials.IRON, 3, new Item.Settings()
			.group(AnyoneOfUs.FRENCH_TAB));

	public static final Item CLOAKING_DEVICE = new CloakingDeviceItem(new Item.Settings()
			.group(AnyoneOfUs.FRENCH_TAB)
			.maxCount(1));

	//BLU
	public static final Item BLU_BALACLAVA = new SuitArmorItem(new FrenchMaterial(FrenchMaterial.Team.BLU), EquipmentSlot.HEAD);
	public static final Item BLU_SUIT_JACKET = new SuitArmorItem(new FrenchMaterial(FrenchMaterial.Team.BLU), EquipmentSlot.CHEST);
	public static final Item BLU_SUIT_TROUSERS = new SuitArmorItem(new FrenchMaterial(FrenchMaterial.Team.BLU), EquipmentSlot.LEGS);
	public static final Item BLU_SHOES = new SuitArmorItem(new FrenchMaterial(FrenchMaterial.Team.BLU), EquipmentSlot.FEET);

	//RED
	public static final Item RED_BALACLAVA = new SuitArmorItem(new FrenchMaterial(FrenchMaterial.Team.RED), EquipmentSlot.HEAD);
	public static final Item RED_SUIT_JACKET = new SuitArmorItem(new FrenchMaterial(FrenchMaterial.Team.RED), EquipmentSlot.CHEST);
	public static final Item RED_SUIT_TROUSERS = new SuitArmorItem(new FrenchMaterial(FrenchMaterial.Team.RED), EquipmentSlot.LEGS);
	public static final Item RED_SHOES = new SuitArmorItem(new FrenchMaterial(FrenchMaterial.Team.RED), EquipmentSlot.FEET);
}
