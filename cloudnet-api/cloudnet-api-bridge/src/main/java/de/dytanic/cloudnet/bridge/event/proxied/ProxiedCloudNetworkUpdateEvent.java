/*
 * Copyright (c) Tarek Hosni El Alaoui 2017
 */

package de.dytanic.cloudnet.bridge.event.proxied;

import de.dytanic.cloudnet.lib.CloudNetwork;
import lombok.AllArgsConstructor;

/**
 * Called if the cloudnetwork objective was updated
 */
@AllArgsConstructor
public class ProxiedCloudNetworkUpdateEvent extends ProxiedCloudEvent {

    private CloudNetwork cloudNetwork;

    public CloudNetwork getCloudNetwork()
    {
        return cloudNetwork;
    }
}