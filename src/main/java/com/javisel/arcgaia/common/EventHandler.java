package com.javisel.arcgaia.common;


import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class EventHandler {




    @SubscribeEvent
    public static void joinWorldEvent(EntityJoinWorldEvent event) {


        if (event.getEntity() instanceof PlayerEntity && !event.getWorld().isRemote) {

            ((PlayerEntity) event.getEntity()).getAttribute(Attributes.ATTACK_SPEED).setBaseValue(1.0);


        }


    }

}
