package com.magicsmp.magicspawners.gui;

import com.magicsmp.magicspawners.MagicSpawners;
import com.magicsmp.magicspawners.model.SpawnerData;
import com.magicsmp.magicspawners.util.ColorUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public final class SpawnerPanelGui {
    private SpawnerPanelGui(){}
    public static void open(Player p){
        Inventory inv=Bukkit.createInventory(null,27,ColorUtil.component("ꜱᴘᴀᴡɴᴇʀ ᴘᴀɴᴇʟ"));
        int o=count(World.Environment.NORMAL),n=count(World.Environment.NETHER),e=count(World.Environment.THE_END);
        inv.setItem(11,SpawnerGui.button(Material.GRASS_BLOCK,"&#3AFF00ᴏᴠᴇʀᴡᴏʀʟᴅ","&7Total Spawners: &f"+o));
        inv.setItem(13,SpawnerGui.button(Material.NETHERRACK,"&#FE3333ɴᴇᴛʜᴇʀ","&7Total Spawners: &f"+n));
        inv.setItem(15,SpawnerGui.button(Material.END_STONE,"&#C11FFEᴇɴᴅ","&7Total Spawners: &f"+e));
        p.openInventory(inv);
    }
    private static int count(World.Environment env){int c=0;for(SpawnerData d:MagicSpawners.get().storage().all())if(d.location()!=null&&d.location().getWorld().getEnvironment()==env)c++;return c;}
}
