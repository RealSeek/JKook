package snw.jkook.event.channel;

import snw.jkook.entity.User;
import snw.jkook.entity.channel.Channel;
import snw.jkook.event.HandlerList;

import java.util.Objects;

/**
 * Represents an event related to an operator created a channel.
 */
public class ChannelCreateEvent extends ChannelEvent {
    private static final HandlerList handlers = new HandlerList();

    private final User operator;

    public ChannelCreateEvent(final Channel channel, final User operator) {
        super(channel);
        this.operator = Objects.requireNonNull(operator);
    }

    /**
     * Get the operator.
     */
    public User getOperator() {
        return operator;
    }

    public static HandlerList getHandlers() {
        return handlers;
    }

}
