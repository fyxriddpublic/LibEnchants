package com.fyxridd.lib.enchants;

import com.fyxridd.lib.core.api.config.ConfigApi;
import com.fyxridd.lib.core.api.plugin.SimplePlugin;
import com.fyxridd.lib.show.chat.config.DelayChatConfig;
import com.fyxridd.lib.show.chat.config.LangConfig;
import com.fyxridd.lib.show.chat.config.ShowConfig;
import com.fyxridd.lib.show.chat.manager.DelayChatManager;
import com.fyxridd.lib.show.chat.manager.ShowEventManager;
import com.fyxridd.lib.show.chat.manager.ShowListManager;
import com.fyxridd.lib.show.chat.manager.ShowManager;

public class EnchantsPlugin extends SimplePlugin {
    public static ShowPlugin instance;

    private ShowManager showManager;
    private ShowEventManager showEventManager;
    private ShowListManager showListManager;
    private DelayChatManager delayChatManager;

    //启动插件
    @Override
    public void onEnable() {
        instance = this;

        //注册配置
        ConfigApi.register(ShowPlugin.instance.pn, LangConfig.class);
        ConfigApi.register(ShowPlugin.instance.pn, ShowConfig.class);
        ConfigApi.register(ShowPlugin.instance.pn, DelayChatConfig.class);

        showManager = new ShowManager();
        showEventManager = new ShowEventManager();
        showListManager = new ShowListManager();
        delayChatManager = new DelayChatManager();

        super.onEnable();
    }

    public ShowManager getShowManager() {
        return showManager;
    }

    public ShowEventManager getShowEventManager() {
        return showEventManager;
    }

    public ShowListManager getShowListManager() {
        return showListManager;
    }

    public DelayChatManager getDelayChatManager() {
        return delayChatManager;
    }
}