/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.DragonAPI;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.world.World;
import Reika.DragonAPI.Base.DragonAPIMod;
import Reika.DragonAPI.Exception.MisuseException;
import Reika.DragonAPI.Exception.RegistrationException;
import Reika.DragonAPI.Libraries.Java.ReikaJavaLibrary;
import cpw.mods.fml.common.FMLCommonHandler;

public class DragonAPICore {

	protected DragonAPICore() {throw new MisuseException("The class "+this.getClass()+" cannot be instantiated!");}

	protected static final Random rand = new Random();

	public static final String FORUM_PAGE = "http://www.minecraftforum.net/topic/1969694-";

	public static URL getReikaForumPage(DragonAPIMod instance) {
		try {
			return new URL(FORUM_PAGE);
		}
		catch (MalformedURLException e) {
			throw new RegistrationException(instance, "The mod provided a malformed URL for its documentation site!");
		}
	}

	public static final boolean hasAllClasses() {
		return true;
	}

	//TODO Add handler for custom death messages

	public static boolean isReikasComputer() {
		try {
			String username = System.getProperty("user.name");
			boolean win = System.getProperty("os.name").toLowerCase().contains("win");
			return win && "Reika".equals(username);
		}
		catch (SecurityException e) {
			return false;
		}
	}

	static {
		if (isReikasComputer())
			ReikaJavaLibrary.pConsole("DRAGONAPI: Loading on Reika's computer; Dev features enabled.");
	}

	public static boolean isDeObfEnvironment() {
		try {
			Method m = ItemHoe.class.getMethod("onItemUse", ItemStack.class, EntityPlayer.class, World.class, int.class, int.class, int.class, int.class, float.class, float.class, float.class);
			return true;
		}
		catch (NoSuchMethodException e) {
			return false;
		}
	}

	public static boolean isOnActualServer() {
		return FMLCommonHandler.instance().getMinecraftServerInstance() instanceof DedicatedServer;
	}
}
