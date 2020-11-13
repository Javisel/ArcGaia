package com.javisel.arcgaia.common.attributes;

import com.javisel.arcgaia.ArcGaia;
import net.minecraft.entity.ai.attributes.RangedAttribute;

public class DamageDealtAttribute extends RangedAttribute {
    public DamageDealtAttribute(String attributeName, double defaultValue, double minimumValue, double maximumValue) {
        super(ArcGaia.MODID + ".damagedealt", 1.0, 0, 200);
    }
}
