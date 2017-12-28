/*
 * Copyright (c) Tarek Hosni El Alaoui 2017
 */

package de.dytanic.cloudnet.bridge.internal.command.proxied;

import de.dytanic.cloudnet.api.CloudAPI;
import de.dytanic.cloudnet.lib.NetworkUtils;
import de.dytanic.cloudnet.lib.player.OfflinePlayer;
import de.dytanic.cloudnet.lib.player.permission.*;
import de.dytanic.cloudnet.lib.utility.CollectionWrapper;
import de.dytanic.cloudnet.lib.utility.threading.Runnabled;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Created by Tareko on 23.08.2017.
 */
public class CommandPermissions extends Command {

    public CommandPermissions()
    {
        super("cperms", "cloudnet.command.permissions", "permissions", "perms");
    }

    @Override
    public void execute(CommandSender sender, String[] args)
    {
        PermissionPool permissionPool = CloudAPI.getInstance().getPermissionPool();
        if (args.length > 0)
        {
            if (args[0].equalsIgnoreCase("group"))
            {
                if (args.length == 1)
                {
                    sender.sendMessage(" ");
                    sender.sendMessage("Permission Groups:");
                    for (PermissionGroup permissionGroup : permissionPool.getGroups().values())
                    {
                        sender.sendMessage(permissionGroup.getName() + " [" + permissionGroup.getJoinPower() + "] implements " + permissionGroup.getImplementGroups());
                    }
                    sender.sendMessage(" ");
                    return;
                }
                if (args.length == 2)
                {
                    if (permissionPool.getGroups().containsKey(args[1]))
                    {
                        sender.sendMessage(" ");
                        PermissionGroup permissionGroup = permissionPool.getGroups().get(args[1]);
                        sender.sendMessage("Name: " + permissionGroup.getName());
                        sender.sendMessage("Implementations: " + permissionGroup.getImplementGroups());
                        sender.sendMessage("TagId: " + permissionGroup.getTagId());
                        sender.sendMessage("JoinPower: " + permissionGroup.getJoinPower());
                        for (Map.Entry<String, Boolean> x : permissionGroup.getPermissions().entrySet())
                            sender.sendMessage("- " + x.getKey() + ":" + x.getValue());
                        sender.sendMessage(" ");
                        sender.sendMessage("Permissions for groups:");
                        for (Map.Entry<String, List<String>> x : permissionGroup.getServerGroupPermissions().entrySet())
                        {
                            sender.sendMessage(x.getKey() + ":");
                            CollectionWrapper.iterator(x.getValue(), new Runnabled<String>() {
                                @Override
                                public void run(String obj)
                                {
                                    sender.sendMessage("- " + obj);
                                }
                            });
                        }
                        sender.sendMessage(" ");
                    } else
                    {
                        sender.sendMessage("The group doesn't exists");
                    }
                }

                if (args.length == 4)
                {
                    if (args[2].equalsIgnoreCase("setDisplay"))
                    {
                        if (permissionPool.getGroups().containsKey(args[1]))
                        {
                            PermissionGroup permissionGroup = permissionPool.getGroups().get(args[1]);
                            permissionGroup.setDisplay(args[3].replace("_", " "));
                            CloudAPI.getInstance().updatePermissionGroup(permissionGroup);
                            sender.sendMessage("You set for " + permissionGroup.getName() + " the display \"" + permissionGroup.getDisplay() + "\"");
                        } else
                        {
                            sender.sendMessage("The group doesn't exists");
                        }
                    }

                    if (args[2].equalsIgnoreCase("setPrefix"))
                    {
                        if (permissionPool.getGroups().containsKey(args[1]))
                        {
                            PermissionGroup permissionGroup = permissionPool.getGroups().get(args[1]);
                            permissionGroup.setPrefix(args[3].replace("_", " "));
                            CloudAPI.getInstance().updatePermissionGroup(permissionGroup);
                            sender.sendMessage("You set for " + permissionGroup.getName() + " the prefix \"" + permissionGroup.getPrefix() + "\"");
                        } else
                        {
                            sender.sendMessage("The group doesn't exists");
                        }
                    }

                    if (args[2].equalsIgnoreCase("setSuffix"))
                    {
                        if (permissionPool.getGroups().containsKey(args[1]))
                        {
                            PermissionGroup permissionGroup = permissionPool.getGroups().get(args[1]);
                            permissionGroup.setSuffix(args[3].replace("_", " "));
                            CloudAPI.getInstance().updatePermissionGroup(permissionGroup);
                            sender.sendMessage("You set for " + permissionGroup.getName() + " the suffix \"" + permissionGroup.getSuffix() + "\"");
                        } else
                        {
                            sender.sendMessage("The group doesn't exists");
                        }
                    }

                    if (args[2].equalsIgnoreCase("setDefault"))
                    {
                        if (permissionPool.getGroups().containsKey(args[1]))
                        {
                            PermissionGroup permissionGroup = permissionPool.getGroups().get(args[1]);
                            permissionGroup.setDefaultGroup(args[3].equalsIgnoreCase("true"));
                            CloudAPI.getInstance().updatePermissionGroup(permissionGroup);
                            sender.sendMessage("You set for " + permissionGroup.getName() + " the default \"" + permissionGroup.isDefaultGroup() + "\"");
                        } else
                        {
                            sender.sendMessage("The group doesn't exists");
                        }
                    }

                    if (args[2].equalsIgnoreCase("setJoinPower"))
                    {
                        if (permissionPool.getGroups().containsKey(args[1]) && NetworkUtils.checkIsNumber(args[3]))
                        {
                            PermissionGroup permissionGroup = permissionPool.getGroups().get(args[1]);
                            permissionGroup.setJoinPower(Integer.parseInt(args[3]));
                            CloudAPI.getInstance().updatePermissionGroup(permissionGroup);
                            sender.sendMessage("You set for " + permissionGroup.getName() + " the joinPower \"" + permissionGroup.getJoinPower() + "\"");
                        } else
                        {
                            sender.sendMessage("The group doesn't exists");
                        }
                    }

                    if (args[2].equalsIgnoreCase("setTagId"))
                    {
                        if (permissionPool.getGroups().containsKey(args[1]) && NetworkUtils.checkIsNumber(args[3]))
                        {
                            PermissionGroup permissionGroup = permissionPool.getGroups().get(args[1]);
                            permissionGroup.setTagId(Integer.parseInt(args[3]));
                            CloudAPI.getInstance().updatePermissionGroup(permissionGroup);
                            sender.sendMessage("You set for " + permissionGroup.getName() + " the tagId \"" + permissionGroup.getTagId() + "\"");
                        } else
                        {
                            sender.sendMessage("The group doesn't exists");
                        }
                    }
                    return;
                }

                if (args.length == 5)
                {
                    if (args[2].equalsIgnoreCase("add") && args[3].equalsIgnoreCase("permission"))
                    {
                        if (permissionPool.getGroups().containsKey(args[1]))
                        {
                            PermissionGroup permissionGroup = permissionPool.getGroups().get(args[1]);
                            permissionGroup.getPermissions().put(args[4].replaceFirst("-", ""), !args[4].startsWith("-"));
                            CloudAPI.getInstance().updatePermissionGroup(permissionGroup);
                            sender.sendMessage("You add for " + permissionGroup.getName() + " the permission \"" + args[4] + "\"");
                        } else
                        {
                            sender.sendMessage("The group doesn't exists");
                        }
                    }

                    if (args[2].equalsIgnoreCase("remove") && args[3].equalsIgnoreCase("permission"))
                    {
                        if (permissionPool.getGroups().containsKey(args[1]))
                        {
                            PermissionGroup permissionGroup = permissionPool.getGroups().get(args[1]);
                            permissionGroup.getPermissions().remove(args[4]);
                            CloudAPI.getInstance().updatePermissionGroup(permissionGroup);
                            sender.sendMessage("You removed for " + permissionGroup.getName() + " the permission \"" + args[4] + "\"");
                        } else
                        {
                            sender.sendMessage("The group doesn't exists");
                        }
                    }
                }

                if (args.length == 6)
                {
                    if (args[2].equalsIgnoreCase("add") && args[3].equalsIgnoreCase("permission"))
                    {
                        if (permissionPool.getGroups().containsKey(args[1]))
                        {
                            PermissionGroup permissionGroup = permissionPool.getGroups().get(args[1]);

                            if (!permissionGroup.getServerGroupPermissions().containsKey(args[5]))
                            {
                                permissionGroup.getServerGroupPermissions().put(args[5], new ArrayList<>());
                            }

                            permissionGroup.getServerGroupPermissions().get(args[5]).add(args[4].replaceFirst("-", ""));
                            CloudAPI.getInstance().updatePermissionGroup(permissionGroup);
                            sender.sendMessage("You add for " + permissionGroup.getName() + " the permission \"" + args[4] + "\" on " + args[5]);
                        } else
                        {
                            sender.sendMessage("The group doesn't exists");
                        }
                    }

                    if (args[2].equalsIgnoreCase("remove") && args[3].equalsIgnoreCase("permission"))
                    {
                        if (permissionPool.getGroups().containsKey(args[1]))
                        {
                            PermissionGroup permissionGroup = permissionPool.getGroups().get(args[1]);

                            if (!permissionGroup.getServerGroupPermissions().containsKey(args[5]))
                            {
                                permissionGroup.getServerGroupPermissions().put(args[5], new ArrayList<>());
                            }

                            permissionGroup.getServerGroupPermissions().get(args[5]).remove(args[4].replaceFirst("-", ""));
                            CloudAPI.getInstance().updatePermissionGroup(permissionGroup);
                            sender.sendMessage("You removed for  " + permissionGroup.getName() + " the permission \"" + args[4] + "\" on " + args[5]);
                        } else
                        {
                            sender.sendMessage("The group doesn't exists");
                        }
                    }
                }

                return;
            }
            if (args[0].equalsIgnoreCase("user"))
            {
                if (args.length == 2)
                {
                    UUID uniqueId = CloudAPI.getInstance().getPlayerUniqueId(args[1]);
                    if (uniqueId != null)
                    {
                        OfflinePlayer offlinePlayer = CloudAPI.getInstance().getOfflinePlayer(uniqueId);
                        if (offlinePlayer != null && offlinePlayer.getPermissionEntity() != null)
                        {
                            StringBuilder stringBuilder = new StringBuilder();
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy-HH:mm:ss");
                            for (GroupEntityData groupEntityData : offlinePlayer.getPermissionEntity().getGroups())
                            {
                                stringBuilder.append(groupEntityData.getGroup() + "@" + (groupEntityData.getTimeout() == 0 || groupEntityData.getTimeout() == -1 ? "LIFETIME" : simpleDateFormat.format(groupEntityData.getTimeout())) + " ");
                            }
                            sender.sendMessage(" ");
                            sender.sendMessage("Player " + offlinePlayer.getName() + ": " + offlinePlayer.getUniqueId());
                            sender.sendMessage("Groups: " + stringBuilder.substring(0));

                            for (Map.Entry<String, Boolean> booleanEntry : offlinePlayer.getPermissionEntity().getPermissions().entrySet())
                            {
                                sender.sendMessage("- " + booleanEntry.getKey() + " [" + booleanEntry.getValue() + "]");
                            }

                            sender.sendMessage(" ");
                        } else
                        {
                            sender.sendMessage("The player doesn't registered");
                        }
                    } else
                    {
                        sender.sendMessage("The player doesn't registered");
                    }
                    return;
                }

                //UTILS

                if (args.length == 5)
                {
                    UUID uniqueId = CloudAPI.getInstance().getPlayerUniqueId(args[1]);
                    if (uniqueId != null)
                    {
                        OfflinePlayer offlinePlayer = CloudAPI.getInstance().getOfflinePlayer(uniqueId);
                        if (offlinePlayer != null && offlinePlayer.getPermissionEntity() != null)
                        {
                            if (args[2].equalsIgnoreCase("GROUP"))
                            {
                                if (args[3].equalsIgnoreCase("REMOVE"))
                                {
                                    if (permissionPool.getGroups().containsKey(args[4]))
                                    {
                                        GroupEntityData groupEntityData = null;
                                        for (GroupEntityData groupEntity : offlinePlayer.getPermissionEntity().getGroups())
                                        {
                                            if (groupEntity.getGroup().equalsIgnoreCase(args[4]))
                                            {
                                                groupEntityData = groupEntity;
                                            }
                                        }

                                        if (groupEntityData != null)
                                        {
                                            offlinePlayer.getPermissionEntity().getGroups().remove(groupEntityData);
                                        }

                                        if (offlinePlayer.getPermissionEntity().getGroups().size() == 0)
                                        {
                                            offlinePlayer.getPermissionEntity().getGroups().add(new GroupEntityData(permissionPool.getDefaultGroup().getName(), 0));
                                        }
                                        updatePlayer(offlinePlayer);
                                        sender.sendMessage("The player " + offlinePlayer.getName() + " has removed the group " + args[4]);
                                    }
                                }
                            }

                            if (args[2].equalsIgnoreCase("ADD"))
                            {
                                if (args[3].equalsIgnoreCase("PERMISSION"))
                                {
                                    offlinePlayer.getPermissionEntity().getPermissions().put(args[4].replaceFirst("-", ""), !args[4].startsWith("-"));
                                    updatePlayer(offlinePlayer);
                                    sender.sendMessage("The permission \"" + args[4] + "\" was add for " + offlinePlayer.getName());
                                }
                            }

                            if (args[2].equalsIgnoreCase("REMOVE"))
                            {
                                if (args[3].equalsIgnoreCase("PERMISSION"))
                                {
                                    offlinePlayer.getPermissionEntity().getPermissions().remove(args[4]);
                                    updatePlayer(offlinePlayer);
                                    sender.sendMessage("The permission \"" + args[4] + "\" was removed for " + offlinePlayer.getName());
                                }
                            }
                        } else
                        {
                            sender.sendMessage("The player doesn't registered");
                        }
                    } else
                    {
                        sender.sendMessage("The player doesn't registered");
                    }
                    return;
                }

                if (args.length == 6)
                {
                    UUID uniqueId = CloudAPI.getInstance().getPlayerUniqueId(args[1]);
                    if (uniqueId != null)
                    {
                        OfflinePlayer offlinePlayer = CloudAPI.getInstance().getOfflinePlayer(uniqueId);
                        if (offlinePlayer != null && offlinePlayer.getPermissionEntity() != null)
                        {
                            if (args[2].equalsIgnoreCase("GROUP"))
                            {
                                if (args[3].equalsIgnoreCase("SET"))
                                {
                                    if (permissionPool.getGroups().containsKey(args[4]))
                                    {
                                        offlinePlayer.getPermissionEntity().getGroups().clear();
                                        offlinePlayer.getPermissionEntity().getGroups()
                                                .add(new GroupEntityData(args[4],
                                                        (args[5].equalsIgnoreCase("lifetime") ? 0L : NetworkUtils.checkIsNumber(args[5]) ? calcDays(Integer.parseInt(args[5])) : 0L)));
                                        updatePlayer(offlinePlayer);
                                        sender.sendMessage("The central group of " + offlinePlayer.getName() + " is now " + args[4]);
                                    } else
                                    {
                                        sender.sendMessage("The group doesn't exists");
                                    }
                                    return;
                                }
                                if (args[3].equalsIgnoreCase("ADD"))
                                {
                                    if (permissionPool.getGroups().containsKey(args[4]))
                                    {
                                        offlinePlayer.getPermissionEntity().getGroups()
                                                .add(new GroupEntityData(args[4],
                                                        (args[5].equalsIgnoreCase("lifetime") ? 0L : NetworkUtils.checkIsNumber(args[4]) ? calcDays(Integer.parseInt(args[4])) : 0L)));
                                        updatePlayer(offlinePlayer);
                                        sender.sendMessage("The player " + offlinePlayer.getName() + " has now the group " + args[4] + ", too");
                                    } else
                                    {
                                        sender.sendMessage("The group doesn't exists");
                                    }
                                    return;
                                }
                            }
                        } else
                        {
                            sender.sendMessage("The player doesn't registered");
                        }
                    } else
                    {
                        sender.sendMessage("The player doesn't registered");
                    }
                    return;
                }

                return;
            }

            if (args[0].equalsIgnoreCase("create"))
            {
                if (args.length == 2)
                {
                    if (!permissionPool.getGroups().containsKey(args[1]))
                    {
                        PermissionGroup permissionGroup = new DefaultPermissionGroup(args[1]);
                        CloudAPI.getInstance().updatePermissionGroup(permissionGroup);
                        sender.sendMessage("The group " + args[1] + " is now created!");
                    } else
                    {
                        sender.sendMessage("The permission group already exists");
                    }
                }
            }
        } else
        {
            sender.sendMessage("CloudNet-Permissions: [\"_\" = \" \"]");
            sender.sendMessage(" ");
            sender.sendMessage(CloudAPI.getInstance().getPrefix() + "/cperms CREATE <groupName>");
            sender.sendMessage(CloudAPI.getInstance().getPrefix() + "/cperms GROUP");
            sender.sendMessage(CloudAPI.getInstance().getPrefix() + "/cperms GROUP <name>");
            sender.sendMessage(CloudAPI.getInstance().getPrefix() + "/cperms GROUP <name> add permission <permission>");
            sender.sendMessage(CloudAPI.getInstance().getPrefix() + "/cperms GROUP <name> remove permission <permission>");
            sender.sendMessage(CloudAPI.getInstance().getPrefix() + "/cperms GROUP <name> add permission <permission> <group>");
            sender.sendMessage(CloudAPI.getInstance().getPrefix() + "/cperms GROUP <name> remove permission <permission> <group>");
            sender.sendMessage(CloudAPI.getInstance().getPrefix() + "/cperms GROUP <name> setDisplay <display>");
            sender.sendMessage(CloudAPI.getInstance().getPrefix() + "/cperms GROUP <name> setJoinPower <joinPower>");
            sender.sendMessage(CloudAPI.getInstance().getPrefix() + "/cperms GROUP <name> setSuffix <joinPower>");
            sender.sendMessage(CloudAPI.getInstance().getPrefix() + "/cperms GROUP <name> setPrefix <prefix>");
            sender.sendMessage(CloudAPI.getInstance().getPrefix() + "/cperms GROUP <name> setTagId <tagId>");
            sender.sendMessage(CloudAPI.getInstance().getPrefix() + "/cperms GROUP <name> setDefault <true : false>");
            sender.sendMessage(CloudAPI.getInstance().getPrefix() + "/cperms USER <user>");
            sender.sendMessage(CloudAPI.getInstance().getPrefix() + "/cperms USER <user> GROUP SET <name> <lifetime | time in days> ");
            sender.sendMessage(CloudAPI.getInstance().getPrefix() + "/cperms USER <user> GROUP ADD <name> <lifetime | time in days> ");
            sender.sendMessage(CloudAPI.getInstance().getPrefix() + "/cperms USER <user> GROUP REMOVE <name>");
            sender.sendMessage(CloudAPI.getInstance().getPrefix() + "/cperms USER <user> ADD PERMISSION <permission>");
            sender.sendMessage(CloudAPI.getInstance().getPrefix() + "/cperms USER <user> REMOVE PERMISSION <permission>");
        }
    }

    private void updatePlayer(OfflinePlayer offlinePlayer)
    {
        CloudAPI.getInstance().updatePlayer(offlinePlayer);
    }

    private long calcDays(int value)
    {
        return (System.currentTimeMillis() + ((TimeUnit.DAYS.toMillis(value))));
    }
}