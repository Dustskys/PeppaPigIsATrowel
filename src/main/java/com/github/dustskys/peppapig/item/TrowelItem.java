package com.github.dustskys.peppapig.item;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.phys.BlockHitResult;

public class TrowelItem extends Item
{
    //constructor for item properties
    public TrowelItem(Properties properties)
    {
        super(properties);
       
    }
    
    //override isValidRepairItem so that the trowel can be repaired using iron ingots
    @Override
    public boolean isValidRepairItem(ItemStack stack, ItemStack repairCandidate)
    {
        return repairCandidate.getItem() == Items.IRON_INGOT;
    }
    
    //override useOn to run our code when the player right-clicks a block
    @Override
    public InteractionResult useOn(UseOnContext context)
    {
        //get our player
        Player player = context.getPlayer();
        
        //create a list to store the blocks in the players hotbar
        List<ItemStack> hotbar = new ArrayList<>();
        
        //loop over hotbar slots to gather the blocks
        for (int slot = 0; slot < 9; slot++) {
            ItemStack stack = player.getInventory().getItem(slot);
            
            //if the ItemStack represents a block add it to our list
            if (stack.getItem() instanceof BlockItem) {
                hotbar.add(stack);
            }
        }
        
        //cancel the interaction if the players hotbar has no blocks
        if (hotbar.isEmpty()) return InteractionResult.FAIL;
        
        //stop client from trying to place a different block than the server
        if (context.getLevel().isClientSide) return InteractionResult.SUCCESS;
        
        //grab a random block from the hotbar list
        ItemStack blockStack = hotbar.get(player.getRandom().nextInt(hotbar.size()));
        BlockItem block = (BlockItem) blockStack.getItem();
        //place the randomly selected block
        InteractionResult placement = block.place(new BlockPlaceContext(context.getLevel(), context.getPlayer(), context.getHand(), blockStack, 
                new BlockHitResult(context.getClickLocation(), context.getClickedFace(), context.getClickedPos(), context.isInside())));
        
        //if block was placement succeeded
        if (placement != InteractionResult.FAIL) {
            //play the blocks placement sound
            SoundType sound = block.getBlock().defaultBlockState().getSoundType(context.getLevel(), context.getClickedPos(), context.getPlayer());
            context.getLevel().playSound(null, context.getClickedPos(), sound.getPlaceSound(), SoundSource.BLOCKS, (sound.getVolume() + 1F) / 2F, sound.getPitch() * 0.8F);
            
            //grab the mending enchantment from the registry
            Holder<Enchantment> holder = context.getLevel().registryAccess().holderOrThrow(Enchantments.MENDING);
            
            //damage the item if it does not have mending
            if (context.getItemInHand().getEnchantmentLevel(holder) == 0) {
                context.getItemInHand().hurtAndBreak(1, player, EquipmentSlot.MAINHAND);
            }
        }
        //the interaction succeeded
        return InteractionResult.SUCCESS;
    }
}
