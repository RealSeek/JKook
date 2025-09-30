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
import snw.jkook.message.component.card.MultipleCardComponent;

import snw.jkook.entity.User;

import java.util.Collection;

/**
 * Represents a post (reply) in a thread.
 *
 * <p>A thread post is a response to a thread's main content. Posts support rich media
 * content (text + images) and can have nested replies (楼中楼).
 *
 * <p><b>Content Restrictions:</b>
 * <ul>
 *   <li>Main replies: Support rich media (text + images)</li>
 *   <li>Nested replies: Only support KMD format and emojis</li>
 * </ul>
 *
 * @see Thread
 * @since 0.55.0
 */
public interface ThreadPost {

    /**
     * Get the unique identifier of this post.
     *
     * @return The post ID
     */
    String getId();

    /**
     * Get the thread this post belongs to.
     *
     * @return The parent thread
     */
    Thread getThread();

    /**
     * Get the author of this post.
     *
     * @return The user who created this post
     */
    User getAuthor();

    /**
     * Get the content of this post.
     *
     * <p>The content format depends on whether this is a main reply or nested reply.
     *
     * @return The post content
     */
    MultipleCardComponent getContent();

    /**
     * Get the timestamp when this post was created.
     *
     * @return The creation timestamp in milliseconds
     */
    long getTimeStamp();

    /**
     * Check if this is a nested reply (楼中楼).
     *
     * @return True if this is a nested reply
     */
    boolean isNestedReply();

    /**
     * Get the ID of the main floor post this reply belongs to (belong_to_post_id in API response).
     *
     * <p>This identifies which main floor post this reply is attached to.
     * All replies and nested replies under the same main floor will have the same belong_to_post_id.
     *
     * <p><b>Example:</b>
     * <pre>
     * Thread
     * ├─ Post #001 (main floor)
     * │  └─ Post #002 (belong_to_post_id: "001") ← belongs to main floor #001
     * │     └─ Post #003 (belong_to_post_id: "001") ← still belongs to main floor #001
     * </pre>
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
     * <p><b>Example:</b>
     * <pre>
     * Post #001 (main floor)
     * └─ Post #002 (reply_id: null) ← direct reply to main floor
     *    └─ Post #003 (reply_id: "002") ← nested reply to #002
     * </pre>
     *
     * @return The direct parent post ID, or null if this is a direct reply to the thread
     */
    String getReplyToPostId();

    /**
     * Get all nested replies to this post (楼中楼).
     *
     * @return A collection of nested reply posts, or empty if no replies
     */
    Collection<ThreadPost> getReplies();

    /**
     * Check if this post has been edited/updated.
     *
     * @return True if the post has been updated after creation
     */
    boolean isUpdated();

    /**
     * Delete this post.
     *
     * <p><b>Note:</b> This action requires appropriate permissions.
     *
     */
    void delete();

}