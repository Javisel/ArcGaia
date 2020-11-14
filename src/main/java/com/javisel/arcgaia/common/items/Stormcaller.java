package com.javisel.arcgaia.common.items;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.javisel.arcgaia.common.entities.LightningArrowEntity;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.enchantment.IVanishable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

import java.util.UUID;
import java.util.function.Predicate;

public class Stormcaller  extends BowItem  implements IVanishable {

    private final Multimap<Attribute, AttributeModifier> attributes;

    private static final String bowid = "1ea38d51-3683-444f-96b5-31612f5b1117";

    public Stormcaller() {
        super(new Properties().maxDamage(100).rarity(Rarity.EPIC));

        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(UUID.fromString(bowid), "Bow modifier", 0.50, AttributeModifier.Operation.MULTIPLY_BASE));
        builder.put(Attributes.MOVEMENT_SPEED, new AttributeModifier(UUID.fromString(bowid), "Bow modifier", 0.10, AttributeModifier.Operation.MULTIPLY_BASE));



        this.attributes = builder.build();


    }

    /**
     * Called when the player stops using an Item (stops holding the right mouse button).
     */
    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, LivingEntity entityLiving, int timeLeft) {

        if (entityLiving instanceof PlayerEntity) {
            PlayerEntity playerentity = (PlayerEntity)entityLiving;
            boolean flag = true;
            ItemStack itemstack = playerentity.findAmmo(stack);

            int bowcharge = ((stack.getUseDuration() - timeLeft));
            bowcharge = net.minecraftforge.event.ForgeEventFactory.onArrowLoose(stack, worldIn, playerentity, bowcharge, !itemstack.isEmpty() || flag);

            if (bowcharge < 0) return;
            if (itemstack.isEmpty()) {
                itemstack = new ItemStack(Items.ARROW);
            }

            float velocity = getArrowVelocity( bowcharge);
            if (!((double)velocity < 0.1D)) {
                boolean flag1 = playerentity.abilities.isCreativeMode || (itemstack.getItem() instanceof ArrowItem && ((ArrowItem)itemstack.getItem()).isInfinite(itemstack, stack, playerentity));

                    ArrowItem arrowitem = (ArrowItem)(itemstack.getItem() instanceof ArrowItem ? itemstack.getItem() : Items.ARROW);
                    AbstractArrowEntity abstractarrowentity = new LightningArrowEntity(worldIn,playerentity);




                    abstractarrowentity = customArrow(abstractarrowentity);
                    abstractarrowentity.func_234612_a_(playerentity, playerentity.rotationPitch, playerentity.rotationYaw, 0.0F, velocity * 3.0F, 0);
                    if (velocity == 1.0F) {
                        abstractarrowentity.setIsCritical(true);
                    }

                    int powerboost = EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER, stack);
                    if (powerboost > 0) {
                        abstractarrowentity.setDamage(abstractarrowentity.getDamage() + (double)powerboost * 0.5D + 0.5D);
                    }

                    int knockback = EnchantmentHelper.getEnchantmentLevel(Enchantments.PUNCH, stack);
                    if (knockback > 0) {
                        abstractarrowentity.setKnockbackStrength(knockback);
                    }

                    if (EnchantmentHelper.getEnchantmentLevel(Enchantments.FLAME, stack) > 0) {
                        abstractarrowentity.setFire(100);
                    }

                    stack.damageItem(1, playerentity, (p_220009_1_) -> {
                        p_220009_1_.sendBreakAnimation(playerentity.getActiveHand());
                    });
                    if (flag1 || playerentity.abilities.isCreativeMode && (itemstack.getItem() == Items.SPECTRAL_ARROW || itemstack.getItem() == Items.TIPPED_ARROW)) {
                        abstractarrowentity.pickupStatus = AbstractArrowEntity.PickupStatus.CREATIVE_ONLY;
                    }

                    abstractarrowentity.setPierceLevel((byte) 6);
                    worldIn.addEntity(abstractarrowentity);


                worldIn.playSound((PlayerEntity)null, playerentity.getPosX(), playerentity.getPosY(), playerentity.getPosZ(), SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 1.0F, 1.0F / (random.nextFloat() * 0.4F + 1.2F) + velocity * 0.5F);
                if (!flag1 && !playerentity.abilities.isCreativeMode) {
                    itemstack.shrink(1);
                    if (itemstack.isEmpty()) {
                        playerentity.inventory.deleteStack(itemstack);
                    }
                }

                playerentity.addStat(Stats.ITEM_USED.get(this));



            }
        }
    }

    /**
     * Gets the velocity of the arrow entity from the bow's charge
     */
    public static float getArrowVelocity(int charge) {
        float f = (float)charge / 20.0F;
        f = (f * f + f * 2.0F) / 3.0F;

        if (f > 1.0F) {
            f = 1.0F;
        }

        return f;
    }

    /**
     * How long it takes to use or consume an item
     */
    public int getUseDuration(ItemStack stack) {




        return 20000;
    }




    public static int getChargeTime(ItemStack stack) {
        int attacktime = (int) (20/(stack.getTag().getDouble(Attributes.ATTACK_SPEED.getAttributeName())));


        return attacktime ;
    }
    /**
     * returns the action that specifies what animation to play when the items is being used
     */
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.BOW;
    }

    /**
     * Called to trigger the item's "innate" right click behavior. To handle when this item is used on a Block, see
     * {@link #onItemUse}.
     */
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {

        ItemStack itemstack = playerIn.getHeldItem(handIn);
        boolean flag = !playerIn.findAmmo(itemstack).isEmpty();

        ActionResult<ItemStack> ret = net.minecraftforge.event.ForgeEventFactory.onArrowNock(itemstack, worldIn, playerIn, handIn, flag);
        if (ret != null) return ret;



        CompoundNBT nbt = itemstack.hasTag() ? itemstack.getTag() : new CompoundNBT();


        nbt.putDouble(Attributes.ATTACK_SPEED.getAttributeName(), playerIn.getAttributeValue(Attributes.ATTACK_SPEED));



        itemstack.setTag(nbt);



        if (!playerIn.abilities.isCreativeMode && !flag) {
            return ActionResult.resultFail(itemstack);
        } else {
            playerIn.setActiveHand(handIn);
            return ActionResult.resultConsume(itemstack);
        }

    }

    /**
     * Get the predicate to match ammunition when searching the player's inventory, not their main/offhand
     */
    public Predicate<ItemStack> getInventoryAmmoPredicate() {
        return ARROWS;
    }

    public AbstractArrowEntity customArrow(AbstractArrowEntity arrow) {
        return arrow;
    }

    public int func_230305_d_() {
        return 15;
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlotType slot, ItemStack stack) {






        return slot ==EquipmentSlotType.MAINHAND ? attributes : super.getAttributeModifiers(slot, stack);
    }
}
