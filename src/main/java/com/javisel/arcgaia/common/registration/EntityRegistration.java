package com.javisel.arcgaia.common.registration;

import com.javisel.arcgaia.ArcGaia;
import com.javisel.arcgaia.common.entities.LightningArrowEntity;
import com.javisel.arcgaia.common.items.Stormcaller;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import static com.javisel.arcgaia.ArcGaia.MODID;

public class EntityRegistration {


    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, MODID);

    public static final RegistryObject<EntityType<?>> LIGHTNING_ARROW = ENTITIES.register("lightning_arrow", () -> EntityType.Builder.create(LightningArrowEntity::new, EntityClassification.AMBIENT).build(MODID + ":lightning_arrow"));








}
