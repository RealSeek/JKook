/*
 * Copyright 2022 - 2024 JKook contributors
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

package snw.jkook.entity.thread;

import snw.jkook.entity.User;
import snw.jkook.message.component.card.MultipleCardComponent;

import java.util.Collection;

/**
 * Represents a reply to a thread post in a thread channel.
 *
 * <p>Thread replies are responses to the main thread post. They support rich media content
 * (text + images) and can have nested replies (楼中楼) that only support KMD and emoji.
 *
 * <p><b>Reply Hierarchy:</b>
 * <ul>
 *   <li>Direct replies to main post - Rich media content</li>
 *   <li>Nested replies (楼中楼) - KMD and emoji only</li>
 * </ul>
 *
 * @see ThreadPost
 * @since 0.55.0
 */
public interface ThreadReply {

    /**
     * Get the unique identifier of this reply.
     *
     * @return The reply ID
     */
    String getId();

    /**
     * Get the thread post that this reply belongs to.
     *
     * @return The parent thread post
     */
    ThreadPost getThreadPost();

    /**
     * Get the author of this reply.
     *
     * @return The user who created this reply
     */
    User getAuthor();

    /**
     * Get the content of this reply.
     *
     * <p>The content supports rich media format (text + images) for direct replies.
     * Nested replies (楼中楼) only support KMD and emoji.
     *
     * @return The reply content as card component
     */
    MultipleCardComponent getContent();

    /**
     * Get the timestamp when this reply was created.
     *
     * @return The creation timestamp in milliseconds
     */
    long getCreateTime();

    /**
     * Get the ID of the main floor post this reply belongs to (belong_to_post_id in API response).
     *
     * <p>This identifies which main floor post this reply is attached to.
     * All replies and nested replies under the same main floor will have the same belong_to_post_id.
     *
     * @return The main floor post ID, or null if this is a main floor post itself
     */
    String getBelongToPostId();

    /**
     * Get the ID of the post this is directly replying to (reply_id in API response).
     *
     * <p>This is used for nested replies (楼中楼) to identify which specific post
     * this reply is responding to.
     *
     * @return The direct parent post ID, or null if this is a direct reply to the thread
     */
    String getReplyToPostId();

    /**
     * Get all nested replies to this reply.
     *
     * <p>Note: Nested replies (楼中楼) only support KMD format and emoji reactions.
     *
     * @return A collection of nested thread replies
     */
    Collection<ThreadReply> getReplies();

    /**
     * Check if this reply has been edited/updated.
     *
     * @return True if the reply has been updated after creation
     */
    boolean isUpdated();

    /**
     * Reply to this post (create a nested reply/楼中楼).
     *
     * <p><b>Note:</b> Nested replies only support KMD format and emoji.
     *
     * @param content The reply content (KMD format)
     * @return The created nested reply
     */
    ThreadReply reply(String content);

    /**
     * Delete this reply.
     *
     * <p><b>Note:</b> This action requires appropriate permissions.
     */
    void delete();

}
