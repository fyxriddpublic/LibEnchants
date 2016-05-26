package com.fyxridd.lib.enchants.model;

import com.fyxridd.lib.core.api.getter.MultiRandomInt;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class Enchant {
    private int mode;
    private boolean all;
    private boolean fit;
    private boolean safe;
    private Map<Enchantment, MultiRandomInt> enchants;

    public Enchant(int mode, boolean all, boolean fit, boolean safe, Map<Enchantment, MultiRandomInt> enchants) {
        this.mode = mode;
        this.all = all;
        this.fit = fit;
        this.safe = safe;
        this.enchants = enchants;
    }

    /**
     * 给物品检测添加附魔
     * @param is 物品,不为null
     * @return 添加是否成功(只要有一个成功)
     */
    public boolean addEnchant(ItemStack is) {
        if (enchants == null) return false;

        boolean result = false;
        for (Enchantment e:enchants.keySet()) {
            MultiRandomInt level = enchants.get(e);
            int tarLevel = level.get(0);
            if (tarLevel > 0) {//检测给物品添加附魔
                //fit检测
                if (!fit || (e.getItemTarget() != null && e.getItemTarget().includes(is))) {
                    //不更新
                    if (mode == 1) {
                        int preLevel = is.getEnchantmentLevel(e);
                        if (preLevel >= tarLevel) continue;
                    }else if (mode == 3) {
                        int preLevel = is.getEnchantmentLevel(e);
                        if (preLevel > 0) continue;
                    }
                    //更新
                    //safe检测
                    if (safe && (tarLevel > e.getMaxLevel() || tarLevel < e.getStartLevel())) continue;
                    //附魔
                    is.addUnsafeEnchantment(e, tarLevel);
                    result = true;
                    //all检测
                    if (!all) break;
                }
            }
        }

        return result;
    }
}
