package snw.jkook.event.guild;

import snw.jkook.entity.Guild;
import snw.jkook.entity.User;
import snw.jkook.event.HandlerList;

/**
 * Represents an event related to a guild's information was updated. <p>
 * To get the new information, use methods in {@link Guild} interface.
 */
public class GuildInfoUpdateEvent extends GuildEvent {
    private static final HandlerList handlers = new HandlerList();


    public GuildInfoUpdateEvent(final Guild guild) {
        super(guild);
    }

    public static HandlerList getHandlers() {
        return handlers;
    }
}
