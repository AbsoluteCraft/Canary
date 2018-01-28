package com.boveybrawlers.Canary;

import com.boveybrawlers.Canary.lib.ObjectStore;

public class Stores {

    public ObjectStore players;

    Stores(Canary plugin) {
        this.players = new ObjectStore(plugin, "players");
    }

}
