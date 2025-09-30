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
import snw.jkook.entity.channel.ThreadChannel;
import snw.jkook.util.PageIterator;

import java.util.Collection;

/**
 * Represents a thread (post/topic) in a thread channel.
 *
 * <p>A thread is the main content unit in KOOK thread channels, similar to a forum post or topic.
 * It contains a title, content (text + images), and can have multiple replies.
 *
 * <p><b>Thread Structure:</b>
 * <ul>
 *   <li>Main post (thread itself) - Rich media content (text + images)</li>
 *   <li>Replies - Rich media responses to the main post</li>
 *   <li>Nested replies (楼中楼) - KMD and emoji-only responses to replies</li>
 * </ul>
 *
 * @see ThreadPost
 * @see ThreadChannel
 * @since 0.55.0
 */
public interface Thread {

    /**
     * Get the unique identifier of this thread.
     *
     * @return The thread ID
     */
    String getId();

    /**
     * Get the thread channel that contains this thread.
     *
     * @return The parent thread channel
     */
    ThreadChannel getChannel();

    /**
     * Get the author of this thread.
     *
     * @return The user who created this thread
     */
    User getAuthor();

    /**
     * Get the title of this thread.
     *
     * @return The thread title
     */
    String getTitle();

    /**
     * Get the content of this thread.
     *
     * <p>The content supports rich media format (text + images).
     *
     * @return The thread content
     */
    String getContent();

    /**
     * Get the category ID this thread belongs to.
     *
     * @return The category ID, or null if not categorized
     */
    String getCategoryId();

    /**
     * Get the timestamp when this thread was created.
     *
     * @return The creation timestamp in milliseconds
     */
    long getTimeStamp();

    /**
     * Get the number of replies to this thread.
     *
     * @return The reply count
     */
    int getReplyCount();

    /**
     * Get the number of views for this thread.
     *
     * @return The view count
     */
    int getViewCount();

    /**
     * Check if this thread is pinned.
     *
     * @return True if the thread is pinned
     */
    boolean isPinned();

    /**
     * Get all replies (posts) to this thread.
     *
     * @return A page iterator for thread posts
     */
    PageIterator<Collection<ThreadPost>> getReplies();

    /**
     * Reply to this thread.
     *
     * @param content The reply content (supports rich media)
     * @return The created thread post
     */
    ThreadPost reply(String content);

    /**
     * Delete this thread.
     *
     * <p><b>Note:</b> This action requires appropriate permissions.
     *
     */
    void delete();

}