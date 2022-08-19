package io.github.burritobandit28.any1_of_us.armor;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;

public class FrenchMaterial implements ArmorMaterial {

	private static final int[] BASE_DURABILITY = new int[]{13, 15, 16, 11};
	private static final int[] FRENCHNESS = new int[]{20, 30, 35, 15};

	private Team team;


	public FrenchMaterial(Team team) {
		this.team = team;
	}


	@Override
	public int getDurability(EquipmentSlot slot) {
		return BASE_DURABILITY[slot.getEntitySlotId()] * 6;
	}

	@Override
	public int getProtectionAmount(EquipmentSlot slot) {
		int[] prot = new int[]{1, 2, 3, 1};
		return prot[slot.getEntitySlotId()];
	}

	@Override
	public int getEnchantability() {
		return 15;
	}

	@Override
	public SoundEvent getEquipSound() {
		return SoundEvents.ITEM_ARMOR_EQUIP_LEATHER;
	}

	@Override
	public Ingredient getRepairIngredient() {
		switch (this.team) {
			case BLU -> {return Ingredient.ofItems(Items.BLUE_WOOL);}
			case RED -> {return Ingredient.ofItems(Items.RED_WOOL);}
		}
		return Ingredient.EMPTY;
	}

	@Override
	public String getName() {
		switch (this.team) {
			case BLU -> {return "blu_suit";}
			case RED -> {return "red_suit";}
		}
		return "suit";
	}

	@Override
	public float getToughness() {
		return 0;
	}

	@Override
	public float getKnockbackResistance() {
		return 0;
	}

	public int getFrenchness(EquipmentSlot slot) {
		return FRENCHNESS[slot.getEntitySlotId()];
	}

	public enum Team {
		RED,
		BLU
	}

}
