package com.fyxridd.lib.enchants.manager;

import com.fyxridd.lib.core.api.CoreApi;
import com.fyxridd.lib.core.api.UtilApi;
import com.fyxridd.lib.core.api.getter.MultiRandomInt;
import com.fyxridd.lib.enchants.api.EnchantsApi;
import com.fyxridd.lib.enchants.model.Enchant;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.*;

public class EnchantsManager{
    //插件名 类型名 附魔信息
    private static Map<String, Map<String, Enchant>> enchants = new HashMap<>();

    /**
     * @see EnchantsApi#reloadEnchants(String)
     */
    public void reloadEnchants(String plugin) {
        try {
            try {
                if (plugin == null) return;
                YamlConfiguration config = UtilApi.loadConfigByUTF8(new File(CoreApi.pluginPath, plugin+"/enchants.yml"));
                if (config == null) return;
                Map<String, Enchant> map = new HashMap<String, Enchant>();
                enchants.put(plugin, map);
                for (String key:config.getValues(false).keySet()) {
                    try {
                        //mode
                        int mode = config.getInt(key+".mode");
                        if (mode < 1 || mode > 3) throw new Exception("error: mode < 1 or mode > 3");
                        //all
                        boolean all = config.getBoolean(key+".all");
                        //fit
                        boolean fit = config.getBoolean(key+".fit");
                        //safe
                        boolean safe = config.getBoolean(key+".safe");
                        //enchants
                        Map<Enchantment, MultiRandomInt> enchants = new HashMap<Enchantment, MultiRandomInt>();
                        for (String s:config.getStringList(key+".enchants")) {
                            //enchantment
                            String[] ss = s.split(" ", 2);
                            if (ss.length != 2) throw new Exception("format error!");
                            Enchantment enchantment = CoreApi.getEnchantment(ss[0]);
                            if (enchantment == null) throw new Exception("enchantment is null!");
                            enchants.put(enchantment, new MultiRandomInt(ss[1]));
                        }
                        //添加
                        map.put(key, new Enchant(mode, all, fit, safe, enchants));
                    } catch (Exception e) {
                        throw new Exception("load key '"+key+"' error: "+e.getMessage(), e);
                    }
                }
            } catch (Exception e) {
                throw new Exception("load enchants of plugin '"+plugin+"' error: "+e.getMessage(), e);
            }
        } catch (Exception e) {
            //todo
        }
    }
    
    /**
     * @see com.fyxridd.lib.enchants.api.EnchantsApi#addEnchant(String, String, org.bukkit.inventory.ItemStack)
     */
    public boolean addEnchant(String plugin, String type, ItemStack is) {
        if (plugin == null || type == null || is == null) return false;

        //不存在此注册的插件
        if (!enchants.containsKey(plugin)) return false;

        //此插件未注册此附魔类型
        if (!enchants.get(plugin).containsKey(type)) return false;

        //检测添加
        return enchants.get(plugin).get(type).addEnchant(is);
    }
}
