package io.github.burritobandit28.any1_of_us;

import io.github.burritobandit28.any1_of_us.armor.FrenchAttribute;
import io.github.burritobandit28.any1_of_us.effects.CloakedStatusEffect;
import io.github.burritobandit28.any1_of_us.items.ItemRegister;
import io.github.burritobandit28.any1_of_us.items.KnifeItem;
import io.github.burritobandit28.any1_of_us.items.ModItems;
import io.github.burritobandit28.any1_of_us.sounds.SoundEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import org.apache.commons.lang3.Range;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.quiltmc.qsl.item.group.api.QuiltItemGroup;
import org.quiltmc.qsl.networking.api.PacketByteBufs;
import org.quiltmc.qsl.networking.api.PlayerLookup;
import org.quiltmc.qsl.networking.api.ServerPlayNetworking;

import java.util.ArrayList;
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

	public static boolean isStabable(Entity spy, Entity target) {

		float yaw1 = spy.getYaw();
		float yaw2 = target.getYaw();
		float upper = yaw2 + 25.0f;
		float lower = yaw2 - 25.0f;

		Range<Float> range = Range.between(correct(lower), correct(upper));

		return range.contains(yaw1);
	}

	public static float correct(float initial) {
		float finalVal = initial;

		if (finalVal >179.9) {
			finalVal = (finalVal -180) - ((finalVal -180) *2);
		}
		else if (finalVal < -180) {
			finalVal = 180 - ((0.0f - finalVal ) - 180);
		}
		if (finalVal == -180) finalVal = 180;

		return finalVal;
	}

	@Override
	public void onInitialize(ModContainer mod) {
		LOGGER.info("Hellooo from anyone of us mod :)");
		FrenchAttribute.registerAttributes();
		ItemRegister.registerItems();
		CloakedStatusEffect.register();
		SoundEvents.registerSoundEvents();

		ServerPlayNetworking.registerGlobalReceiver(ID("backstab_packet"), ((server, serverPlayerEntity, serverPlayNetworkHandler, buf, responseSender) -> {
			boolean backstab = buf.readBoolean();
			PlayerEntity player = server.getPlayerManager().getPlayer(buf.readString());

			if (player == null) {
				ItemStack stack = player.getMainHandStack();
				if (stack.getItem() instanceof KnifeItem knifeItem) {
					knifeItem.setBackStab(backstab, stack);
					if (serverPlayerEntity == player) {
						PacketByteBuf buf2 = PacketByteBufs.create();

						buf2.writeBoolean(backstab);
						buf2.writeString(player.getEntityName());

						for (ServerPlayerEntity ignored : PlayerLookup.all(player.getServer())) {
							ServerPlayNetworking.send((ServerPlayerEntity) player, AnyoneOfUs.ID("backstab_packet_client"), buf2);
						}
					}
				}
			}

		}));
	}
}
