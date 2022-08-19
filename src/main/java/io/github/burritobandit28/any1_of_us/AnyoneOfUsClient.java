package io.github.burritobandit28.any1_of_us;

import io.github.burritobandit28.any1_of_us.items.CloakingDeviceItem;
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
	}


}
