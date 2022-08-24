package io.github.burritobandit28.any1_of_us.mixins;

import io.github.burritobandit28.any1_of_us.AnyoneOfUs;
import io.github.burritobandit28.any1_of_us.effects.CloakedStatusEffect;
import io.github.burritobandit28.any1_of_us.items.KnifeItem;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;
import org.quiltmc.qsl.networking.api.PacketByteBufs;
import org.quiltmc.qsl.networking.api.PlayerLookup;
import org.quiltmc.qsl.networking.api.ServerPlayNetworking;
import org.quiltmc.qsl.networking.api.client.ClientPlayNetworking;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixinClient extends LivingEntity {


	@Shadow
	public abstract Iterable<ItemStack> getArmorItems();


	@Shadow
	public abstract String getEntityName();

	protected PlayerEntityMixinClient(EntityType<? extends LivingEntity> entityType, World world) {
		super(entityType, world);
	}

	@Inject(at = @At("HEAD"),method = "tick")
	public void getEntityAhead(CallbackInfo ci) {
		if (!this.world.isClient) {
			return;
		}
		MinecraftClient client = MinecraftClient.getInstance();
		HitResult hit = client.crosshairTarget;

		ItemStack stack =  this.getStackInHand(this.getActiveHand());


		if (hit.getType() == HitResult.Type.ENTITY && hit.getType() != null && stack.getItem() instanceof KnifeItem bob) {
			EntityHitResult entityHit = (EntityHitResult) hit;
			Entity entity = entityHit.getEntity();
			PacketByteBuf buf = PacketByteBufs.create();
			boolean backstab = AnyoneOfUs.isStabable(this, entity);
			buf.writeBoolean(backstab);
			buf.writeString(this.getEntityName());
			ClientPlayNetworking.send(AnyoneOfUs.ID("backstab_packet"), buf);
			bob.setBackStab(backstab, stack);

			/*
			if (AnyoneOfUs.isStabable(entity.getYaw(), this.getYaw()) && this.getStackInHand(this.getActiveHand()).getItem() instanceof KnifeItem) {
				PacketByteBuf buf = PacketByteBufs.create();
				buf.writeBoolean(true);
				buf.writeItemStack(stack);
				buf.writeString("1");
				ClientPlayNetworking.send(AnyoneOfUs.ID("backstab_packet"), buf);
			}
			else if (this.getStackInHand(this.getActiveHand()).getItem() instanceof KnifeItem && !AnyoneOfUs.isStabable(entity.getYaw(), this.getYaw())){
				PacketByteBuf buf = PacketByteBufs.create();
				buf.writeBoolean(false);
				buf.writeItemStack(stack);
				buf.writeString("2");
				ClientPlayNetworking.send(AnyoneOfUs.ID("backstab_packet"), buf);
			}
		}
		else {
			if (this.getStackInHand(this.getActiveHand()).getItem() instanceof KnifeItem) {
				PacketByteBuf buf = PacketByteBufs.create();
				buf.writeBoolean(false);
				buf.writeItemStack(stack);
				buf.writeString("3");
				ClientPlayNetworking.send(AnyoneOfUs.ID("backstab_packet"), buf);
			}
			*/
		}
		else if ((!(hit.getType() == HitResult.Type.ENTITY) || hit.getType() == null) && stack.getItem() instanceof KnifeItem bob) {
			PacketByteBuf buf = PacketByteBufs.create();
			buf.writeBoolean(false);
			buf.writeString(this.getEntityName());
			ClientPlayNetworking.send(AnyoneOfUs.ID("backstab_packet"), buf);

		}
	}
}
