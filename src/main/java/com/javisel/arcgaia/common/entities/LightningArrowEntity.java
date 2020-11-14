package com.javisel.arcgaia.common.entities;

import com.javisel.arcgaia.common.registration.EntityRegistration;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.FMLPlayMessages;

public class LightningArrowEntity extends AbstractArrowEntity {

    public LightningArrowEntity(World worldIn, double x, double y, double z) {
        super((EntityType<? extends LightningArrowEntity>) EntityRegistration.LIGHTNING_ARROW.get(), x, y, z,worldIn);

    }

    public LightningArrowEntity(World worldIn, LivingEntity shooter) {
        super((EntityType<? extends LightningArrowEntity>) EntityRegistration.LIGHTNING_ARROW.get(),shooter, worldIn);

    }

    public LightningArrowEntity(EntityType<Entity> entityEntityType, World world) {

        super((EntityType<? extends LightningArrowEntity>) EntityRegistration.LIGHTNING_ARROW.get(), world);

    }

    public LightningArrowEntity(FMLPlayMessages.SpawnEntity spawnEntity, World world) {
        super((EntityType<? extends AbstractArrowEntity>) EntityRegistration.LIGHTNING_ARROW.get(),world);


    }


    protected void arrowHit(LivingEntity living) {
        super.arrowHit(living);

        LightningBoltEntity lightningBoltEntity = new LightningBoltEntity(EntityType.LIGHTNING_BOLT,this.getEntityWorld());
        lightningBoltEntity.setPositionAndUpdate(living.getPosX(),living.getPosY(),living.getPosZ());

        world.addEntity(lightningBoltEntity);


    }

    @Override
    public void tick() {
        super.tick();

        if (world.isRemote) {

            System.out.println("I exist in the client");
        }
        if (this.inGround) {
            if (this.timeInGround == 0) {
                LightningBoltEntity lightningBoltEntity = new LightningBoltEntity(EntityType.LIGHTNING_BOLT,this.getEntityWorld());
                lightningBoltEntity.setPositionAndUpdate(this.getPosX(),this.getPosY(),this.getPosZ());

                //     world.addEntity(lightningBoltEntity);

            }
        }
    }

    @Override
    protected ItemStack getArrowStack() {
        return new ItemStack(Items.ARROW);
    }


}