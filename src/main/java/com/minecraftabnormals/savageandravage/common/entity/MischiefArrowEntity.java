package com.minecraftabnormals.savageandravage.common.entity;

import com.minecraftabnormals.savageandravage.core.registry.SREntities;
import com.minecraftabnormals.savageandravage.core.registry.SRItems;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.FMLPlayMessages;
import net.minecraftforge.fml.network.NetworkHooks;

public class MischiefArrowEntity extends AbstractArrowEntity {

    public MischiefArrowEntity(EntityType<? extends MischiefArrowEntity> type, World worldIn) {
        super(type, worldIn);
    }

    public MischiefArrowEntity(World worldIn, double x, double y, double z) {
        super(SREntities.MISCHIEF_ARROW.get(), x, y, z, worldIn);
    }

    public MischiefArrowEntity(FMLPlayMessages.SpawnEntity spawnEntity, World world) {
        this(SREntities.MISCHIEF_ARROW.get(), world);
    }

    public MischiefArrowEntity(World worldIn, LivingEntity shooter) {
        super(SREntities.MISCHIEF_ARROW.get(), shooter, worldIn);
    }

    @Override
    protected void func_230299_a_(BlockRayTraceResult result) {
        super.func_230299_a_(result);
        this.pickupStatus = PickupStatus.DISALLOWED;
        
        CreepieEntity creepie = SREntities.CREEPIE.get().create(world);
        creepie.setLocationAndAngles(this.getPosX(), this.getPosY(), this.getPosZ(), 0.0F, 0.0F);
        this.attemptSetOwner(creepie);

        this.world.addEntity(creepie);
    }

    @Override
    protected void onEntityHit(EntityRayTraceResult result) {
        super.onEntityHit(result);

        CreepieEntity creepie = SREntities.CREEPIE.get().create(world);
        creepie.setLocationAndAngles(this.getPosX(), this.getPosY(), this.getPosZ(), 0.0F, 0.0F);
        this.attemptSetOwner(creepie);

        if (result.getEntity() instanceof LivingEntity) {
            creepie.setAttackTarget((LivingEntity) result.getEntity());
        }

        this.world.addEntity(creepie);
    }

    public void attemptSetOwner(CreepieEntity creepie) {
        boolean throwerIsInvisible;
        try {
            throwerIsInvisible = ((LivingEntity) this.func_234616_v_()).isPotionActive(Effects.INVISIBILITY);
        } catch (NullPointerException nullPointer) {
            throwerIsInvisible = false;
        }
        if (!throwerIsInvisible) {
            try {
                creepie.setOwnerId(((LivingEntity) this.func_234616_v_()).getUniqueID());
            } catch (NullPointerException nullPointer) {
                creepie.setOwnerId(null);
            }
        }
    }

    protected ItemStack getArrowStack() {
        return new ItemStack(SRItems.MISCHIEF_ARROW.get());
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}