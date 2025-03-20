package com.github.dustskys.peppapig;

import com.github.dustskys.peppapig.init.PPItems;
import net.minecraft.world.item.CreativeModeTab.TabVisibility;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

//tell NeoForge that this is the mod's entry point
@Mod(PeppaPig.MOD_ID)
public final class PeppaPig {
    
    //constant to store the mod id
	public static final String MOD_ID = "peppapig";
	
	public PeppaPig(IEventBus modBus) {
	    //register the DeferredRegister to our event bus
	    PPItems.REGISTRY.register(modBus);
	    //add an event handler for the BuildCreativeModeTabContentsEvent
	    modBus.addListener(this::addItemsToCreativeTab);
	}
	
	//event handler for BuildCreativeModeTabContentsEvent
	private void addItemsToCreativeTab(BuildCreativeModeTabContentsEvent event) {
	    //only add our item to the tools and utilities tab
	    if (event.getTabKey() != CreativeModeTabs.TOOLS_AND_UTILITIES) return;
	    
	    //find the shears item in the tools and utilities tab
	    ItemStack shears = event.getParentEntries().stream()
	            .filter(entry -> entry.getItem() == Items.SHEARS)
	            .findFirst().get();
	    
	    //place the trowel in the tools and utilities tab after the shears
	    event.insertAfter(shears, new ItemStack(PPItems.TROWEL), TabVisibility.PARENT_AND_SEARCH_TABS);
	}
}