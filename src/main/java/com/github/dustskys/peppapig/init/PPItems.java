package com.github.dustskys.peppapig.init;

import com.github.dustskys.peppapig.PeppaPig;
import com.github.dustskys.peppapig.item.TrowelItem;
import net.minecraft.core.Holder;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredRegister;

public class PPItems
{
    //create a DeferredRegister to take care of registering our items at the right time
    public static final DeferredRegister.Items REGISTRY = DeferredRegister.createItems(PeppaPig.MOD_ID);
    
    //add trowel to the DeferredRegister
    public static final Holder<Item> TROWEL = REGISTRY.registerItem("trowel", props -> new TrowelItem(props.stacksTo(1).durability(2000)));
}
