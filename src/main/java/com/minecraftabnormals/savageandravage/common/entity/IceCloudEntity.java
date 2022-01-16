package com.minecraftabnormals.savageandravage.common.entity;

import com.minecraftabnormals.savageandravage.core.registry.SREffects;
import com.minecraftabnormals.savageandravage.core.registry.SREntities;
import com.minecraftabnormals.savageandravage.core.registry.SRParticles;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.protocol.Packet;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;

/**
 * @author Ocelot
 */
public class IceCloudEntity extends AbstractHurtingProjectile {

	public IceCloudEntity(EntityType<IceCloudEntity> entityType, Level world) {
		super(entityType, world);
	}

	public IceCloudEntity(double x, double y, double z, double targetX, double targetY, double targetZ, Level world) {
		super(SREntities.ICE_CLOUD.get(), x, y, z, targetX - x, targetY - y, targetZ - z, world);
	}

	@Override
	public void tick() {
		super.tick();

		for (Entity entity : this.level.getEntities(this.getOwner(), this.getBoundingBox().expandTowards(2, 2, 2), this::canHitEntity)) {
			if (entity instanceof LivingEntity && !(entity instanceof IceologerEntity)) {
				((LivingEntity) entity).addEffect(new MobEffectInstance(SREffects.FROSTBITE.get(), 80, 0, false, false, true));
			}
		}

		if (!this.level.isClientSide()) {
			((ServerLevel) this.level).sendParticles(this.getTrailParticle(), this.getX(), this.getY(), this.getZ(), 30, 1.5, 1.5, 1.5, 1);
		}

		if (this.tickCount > 100)
			this.discard();
	}

	@Override
	protected boolean shouldBurn() {
		return false;
	}

	@Override
	protected ParticleOptions getTrailParticle() {
		return SRParticles.SNOWFLAKE.get();
	}

	@Override
	public Packet<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}
}
