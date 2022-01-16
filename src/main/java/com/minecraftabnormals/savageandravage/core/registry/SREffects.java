package com.minecraftabnormals.savageandravage.core.registry;

import com.minecraftabnormals.savageandravage.common.effect.FrostbiteEffect;
import com.minecraftabnormals.savageandravage.common.effect.WeightEffect;
import com.minecraftabnormals.savageandravage.core.SavageAndRavage;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SREffects {
	public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, SavageAndRavage.MOD_ID);

	public static final RegistryObject<MobEffect> FROSTBITE = EFFECTS.register("frostbite", FrostbiteEffect::new);
	public static final RegistryObject<MobEffect> WEIGHT = EFFECTS.register("weight", WeightEffect::new);
}
