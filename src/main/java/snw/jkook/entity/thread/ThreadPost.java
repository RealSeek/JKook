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
    String getContent();

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
     * Get the parent post ID if this is a nested reply.
     *
     * @return The parent post ID, or null if this is a main reply
     */
    String getParentPostId();

    /**
     * Delete this post.
     *
     * <p><b>Note:</b> This action requires appropriate permissions.
     *
     */
    void delete();

}