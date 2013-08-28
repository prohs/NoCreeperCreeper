package cgNoCreeper;

import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

@Mod(modid = NoCreeperLib.ID, name = NoCreeperLib.NAME, version = NoCreeperLib.VERSION)
@NetworkMod(channels = { NoCreeperLib.CHANNEL }, clientSideRequired = true, serverSideRequired = false)
public class NoCreeperMain {
	@EventHandler
	public void preModInit(FMLPreInitializationEvent event) {

	}

	@EventHandler
	public void modInit(FMLInitializationEvent event) {

		EntityRegistry.registerGlobalEntityID(NoCreeperCreeper.class,
				"NoCreeperCreeper", EntityRegistry.findGlobalUniqueEntityId(),
				6750105, 7859797);
		EntityRegistry.registerModEntity(NoCreeperCreeper.class,
				"NoCreeperCreeper", 3, this, 64, 3, true);

		MinecraftForge.EVENT_BUS.register(new EntityLivingHandler());

		LanguageRegistry.instance().addStringLocalization(
				"entity.NoCreeperCreeper.name", "No Creeper Creepin");
	}

	@EventHandler
	public void modsLoaded(FMLPostInitializationEvent event) {

	}
}
