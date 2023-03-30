/*
 * Copyright 2022 - 2023 JKook contributors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package snw.jkook.permission;

import snw.jkook.command.CommandSender;
import snw.jkook.entity.Guild;
import snw.jkook.entity.Role;
import snw.jkook.entity.User;
import snw.jkook.entity.channel.Channel;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author huanmeng_qwq
 */
public class KookPermission implements Permission {
    private final snw.jkook.Permission permissionType;

    public KookPermission(snw.jkook.Permission permissionType) {
        this.permissionType = permissionType;
    }

    @Override
    public boolean check(Guild guild, Channel channel, CommandSender sender) {
        if (!(sender instanceof User)) {
            return false;
        }
        User user = (User) sender;
        List<Role> roles = user.getRoles(guild).stream()
                .map(guild::getRole)
                .sorted(Comparator.comparingInt(Role::getPosition))
                .collect(Collectors.toList());
        for (Channel.UserPermissionOverwrite overwrittenUserPermission : channel.getOverwrittenUserPermissions()) {
            if (Objects.equals(overwrittenUserPermission.getUser().getId(), user.getId())) {
                if (snw.jkook.Permission.hasPermission(permissionType, overwrittenUserPermission.getRawDeny())) {
                    return false;
                } else {
                    return snw.jkook.Permission.hasPermission(permissionType, overwrittenUserPermission.getRawAllow());
                }
            }
        }
        for (Role role : roles) {
            for (Channel.RolePermissionOverwrite overwrittenRolePermission : channel.getOverwrittenRolePermissions()) {
                if (Objects.equals(overwrittenRolePermission.getRoleId(), role.getId())) {
                    if (snw.jkook.Permission.hasPermission(permissionType, overwrittenRolePermission.getRawDeny())) {
                        return false;
                    } else {
                        return snw.jkook.Permission.hasPermission(permissionType, overwrittenRolePermission.getRawAllow());
                    }
                }
            }
        }
        return false;
    }
}
