package com.javisel.arcgaia;

import com.javisel.arcgaia.client.renderer.LightningArrowRenderer;
import com.javisel.arcgaia.common.items.Stormcaller;
import com.javisel.arcgaia.common.registration.EntityRegistration;
import com.javisel.arcgaia.common.registration.ItemRegistration;
import net.minecraft.block.Block;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("arcgaia")
public class ArcGaia
{
    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger();
    public static final String MODID = "arcgaia";

    public ArcGaia() {

        ItemRegistration.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());

        EntityRegistration.ENTITIES.register(FMLJavaModLoadingContext.get().getModEventBus());
        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        // Register the enqueueIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        // Register the processIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
        // Register the doClientStuff method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new EntityRegistration());

    }

    private void setup(final FMLCommonSetupEvent event)
    {

    }

    private void doClientStuff(final FMLClientSetupEvent event) {

        ItemModelsProperties.registerProperty(ItemRegistration.STORMCALLER.get(), new ResourceLocation("pull"), (itemStack, clientWorld, livingEntity) -> {
            if (livingEntity == null) {
                return 0.0F;
            } else {
                return livingEntity.getActiveItemStack() != itemStack ? 0.0F : (float)(itemStack.getUseDuration() - livingEntity.getItemInUseCount()) / Stormcaller.getChargeTime(itemStack);
            }
        });
        ItemModelsProperties.registerProperty(ItemRegistration.STORMCALLER.get(), new ResourceLocation("pulling"), (itemStack, clientWorld, livingEntity) -> {
            return livingEntity != null && livingEntity.isHandActive() && livingEntity.getActiveItemStack() == itemStack ? 1.0F : 0.0F;
        });

        RenderingRegistry.registerEntityRenderingHandler(EntityRegistration.LIGHTNING_ARROW.get(),  new LightningArrowRenderer.RenderFactory());

    }

    private void enqueueIMC(final InterModEnqueueEvent event)
    {

    }

    private void processIMC(final InterModProcessEvent event)
    {

    }
    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {

    }

    // You can use EventBusSubscriber to automatically subscribe events on the contained class (this is subscribing to the MOD
    // Event bus for receiving Registry Events)
    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void onBlocksRegistry(final RegistryEvent.Register<Block> blockRegistryEvent) {

        }
    }
}
