package io.github.burritobandit28.any1_of_us.items;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import io.github.burritobandit28.any1_of_us.AnyoneOfUs;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;

public class KnifeItem extends ToolItem {

	private double attackDamage;

	private boolean isBackStab;

	private Multimap<EntityAttribute, EntityAttributeModifier> attributeModifiers;

	public KnifeItem(ToolMaterial toolMaterial, int damage, Settings settings) {
		super(toolMaterial, settings);

		this.attackDamage = damage;

		ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> builder = ImmutableMultimap.builder();
		builder.put(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier(ATTACK_DAMAGE_MODIFIER_ID, "Weapon modifier", (double)this.attackDamage, EntityAttributeModifier.Operation.ADDITION));
		this.attributeModifiers = builder.build();

		NbtCompound nbt = this.getDefaultStack().getOrCreateNbt();

		nbt.putInt("Backstab", 0);

		getDefaultStack().writeNbt(nbt);

	}

	@Override
	public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(EquipmentSlot slot) {
		return slot == EquipmentSlot.MAINHAND ? this.attributeModifiers : super.getAttributeModifiers(slot);
	}

	public void setBackStab(boolean isBackStab, ItemStack stack){
		this.isBackStab = isBackStab;

		if (this.isBackStab) {
			NbtCompound nbt = stack.getOrCreateNbt();

			nbt.putInt("Backstab", 1);

			stack.setNbt(nbt);
		}

		else {
			NbtCompound nbt = stack.getOrCreateNbt();

			nbt.putInt("Backstab", 0);

			stack.setNbt(nbt);
		}

	}

	public boolean isBackStab() {
		return this.isBackStab;
	}


}
