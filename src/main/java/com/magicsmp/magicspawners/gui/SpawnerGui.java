package com.magicsmp.magicspawners.gui;

import com.magicsmp.magicspawners.MagicSpawners;
import com.magicsmp.magicspawners.model.SpawnerData;
import com.magicsmp.magicspawners.util.ColorUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public final class SpawnerGui {
    public static final Map<UUID,SpawnerData> OPEN=new HashMap<>();
    private static final int[] CONTENT={0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44};
    private SpawnerGui(){}
    public static void open(Player p,SpawnerData d){OPEN.put(p.getUniqueId(),d);p.openInventory(build(d));}
    public static void refresh(Player p){SpawnerData d=OPEN.get(p.getUniqueId());if(d!=null)p.openInventory(build(d));}
    private static Inventory build(SpawnerData d){
        MagicSpawners pl=MagicSpawners.get(); String mob=pl.manager().pretty(d.entityType());
        Inventory inv=Bukkit.createInventory(null,54,ColorUtil.component(d.stackSize()+" "+mob+" ѕᴘᴀᴡɴᴇʀ"));
        int idx=0;for(var e:d.storage().entrySet()){int left=e.getValue();while(left>0&&idx<CONTENT.length){int n=Math.min(left,e.getKey().getMaxStackSize());inv.setItem(CONTENT[idx++],new ItemStack(e.getKey(),n));left-=n;}}
        inv.setItem(48,button(Material.GOLD_INGOT,"&#FF1D1Dѕᴇʟʟ ᴀʟʟ","&fClick to sell all mob drops!"));
        inv.setItem(49,button(Material.SPAWNER,"&#1FFD98"+mob+" ѕᴘᴀᴡɴᴇʀ","&#1FFD98"+d.totalItems()+" &fitems stored","&7Stack: &fx"+d.stackSize()));
        inv.setItem(50,button(Material.DROPPER,"&#1FFD98ᴄᴏʟʟᴇᴄᴛ ʟᴏᴏᴛ","&fClick to collect all loot"));
        return inv;
    }
    public static ItemStack button(Material m,String name,String... lore){ItemStack i=new ItemStack(m);ItemMeta meta=i.getItemMeta();meta.displayName(ColorUtil.component(name));meta.lore(Arrays.stream(lore).map(ColorUtil::component).toList());i.setItemMeta(meta);return i;}
}
