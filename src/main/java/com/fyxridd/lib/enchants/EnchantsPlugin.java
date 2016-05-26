package com.fyxridd.lib.enchants;

import com.fyxridd.lib.core.api.config.ConfigApi;
import com.fyxridd.lib.core.api.plugin.SimplePlugin;
import com.fyxridd.lib.enchants.manager.EnchantsManager;

public class EnchantsPlugin extends SimplePlugin {
    public static EnchantsPlugin instance;

    private EnchantsManager enchantsManager;
    
    //启动插件
    @Override
    public void onEnable() {
        instance = this;

        enchantsManager = new EnchantsManager();
        
        super.onEnable();
    }

    public EnchantsManager getEnchantsManager() {
        return enchantsManager;
    }
}