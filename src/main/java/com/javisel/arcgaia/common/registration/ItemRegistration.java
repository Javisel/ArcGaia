package com.javisel.arcgaia.common.registration;

import com.javisel.arcgaia.common.items.Stormcaller;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import static com.javisel.arcgaia.ArcGaia.MODID;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ItemRegistration {


    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);

    public static final RegistryObject<Item> STORMCALLER = ITEMS.register("stormcaller", () -> new Stormcaller());




}