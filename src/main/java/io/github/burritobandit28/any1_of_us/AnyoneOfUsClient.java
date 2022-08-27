package io.github.burritobandit28.any1_of_us;

import io.github.burritobandit28.any1_of_us.items.CloakingDeviceItem;
import io.github.burritobandit28.any1_of_us.items.KnifeItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;
import org.quiltmc.qsl.networking.api.client.ClientPlayNetworking;

import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

public class AnyoneOfUsClient implements ClientModInitializer {

	public static ArrayList<CloakingDeviceItem.CloakedClassBecauseImLazyAndThisIsEasy> isCloaked = new ArrayList<CloakingDeviceItem.CloakedClassBecauseImLazyAndThisIsEasy>();


	@Override
	public void onInitializeClient(ModContainer mod) {

		ClientPlayNetworking.registerGlobalReceiver(AnyoneOfUs.ID("cloaked_packet"), ((client, handler, buf, responseSender) -> {

			boolean worked = false;

			boolean on = buf.readBoolean();
			String name = buf.readString();


			CloakingDeviceItem.CloakedClassBecauseImLazyAndThisIsEasy obj = new CloakingDeviceItem.CloakedClassBecauseImLazyAndThisIsEasy(name, on);

			for (int i = 0; i < isCloaked.size() && isCloaked.get(i) != null; i++) {
				if (Objects.equals(isCloaked.get(i).name, obj.name)) {
					isCloaked.get(i).on = obj.on;
					worked = true;
					break;
				}
			}
			if (!worked) {
				isCloaked.add(obj);
			}

		}));

		ClientPlayNetworking.registerGlobalReceiver(AnyoneOfUs.ID("backstab_packet_client"), ((client, handler, buf, responseSender) -> {

			boolean backstab = buf.readBoolean();
			PlayerEntity player;


			if (client.getServer() != null) {
				player = client.getServer().getPlayerManager().getPlayer(buf.readString());
			}
			else {
				player = client.player;
			}
			ItemStack stack = player.getMainHandStack();
			if (stack.getItem() instanceof KnifeItem knifeItem) {
				knifeItem.setBackStab(backstab, stack);
			}

		}));
	}


}
