package io.github.burritobandit28.any1_of_us.armor;

import io.github.burritobandit28.any1_of_us.AnyoneOfUs;
import net.minecraft.entity.attribute.ClampedEntityAttribute;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class FrenchAttribute {

	public static EntityAttribute GENERIC_FRENCH;


	public static void registerAttributes() {
		GENERIC_FRENCH = register(new ClampedEntityAttribute("attribute.name.generic.french", 0, 0, 100));
	}


	private static EntityAttribute register(EntityAttribute attribute) {
		return Registry.register(Registry.ATTRIBUTE, new Identifier(AnyoneOfUs.MOD_ID, attribute.getTranslationKey()), attribute);
	}

}
