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

package snw.jkook.entity.channel;

import org.jetbrains.annotations.Nullable;
import snw.jkook.entity.thread.Thread;
import snw.jkook.util.PageIterator;

import java.util.Collection;

/**
 * Represents a thread channel (帖子频道).
 *
 * <p>Thread channels are specialized content channels in KOOK that allow users to create
 * structured content for knowledge sharing and experience exchange. They support hierarchical
 * content organization with main posts, replies, and nested replies.
 *
 * <p><b>Key Features:</b>
 * <ul>
 *   <li>Support for category management</li>
 *   <li>Structured content hierarchy (main posts, replies, nested replies)</li>
 *   <li>Rich media support (text + images) for main posts and replies</li>
 *   <li>KMD and emoji-only support for nested replies</li>
 *   <li>Mention functionality (@user, @all, @online)</li>
 * </ul>
 *
 * <p><b>API Limitations:</b>
 * <ul>
 *   <li>Daily quota: 10,000 messages per developer account</li>
 *   <li>Quota resets daily at 12:00 Beijing time</li>
 * </ul>
 *
 * <p><b>Usage Example:</b>
 * <pre>{@code
 * ThreadChannel threadChannel = (ThreadChannel) guild.getChannelById("channel_id");
 * String topic = threadChannel.getTopic();
 * threadChannel.setTopic("New discussion topic");
 *
 * // Create a new thread
 * Thread thread = threadChannel.createThread("Title", "Content", null);
 *
 * // Reply to the thread
 * thread.reply("My reply");
 * }</pre>
 *
 * @see snw.jkook.entity.thread.Thread
 * @see snw.jkook.entity.thread.ThreadPost
 * @see <a href="https://developer.kookapp.cn/doc/http/thread">KOOK Thread Channel Documentation</a>
 * @since 0.55.0
 */
public interface ThreadChannel extends NonCategoryChannel {

    /**
     * Get the topic (description) of this thread channel.
     *
     * <p>The topic provides a brief description of what this thread channel is for,
     * helping users understand the channel's purpose before participating.
     *
     * @return The topic string, or an empty string if not set
     */
    String getTopic();

    /**
     * Set the topic (description) of this thread channel.
     *
     * <p>The topic should provide a clear description of the thread channel's purpose.
     * Maximum length may be subject to platform limitations.
     *
     * @param topic The new topic content
     * @throws IllegalArgumentException if the topic is null or exceeds maximum length
     */
    void setTopic(String topic);

    /**
     * Create a new thread (post) in this channel.
     *
     * @param title The thread title
     * @param content The thread content (supports rich media: text + images)
     * @param categoryId The category ID to organize this thread, null for default category
     * @return The created thread
     * @throws IllegalArgumentException if title or content is null or empty
     */
    Thread createThread(String title, String content, @Nullable String categoryId);

    /**
     * Get a specific thread by its ID.
     *
     * @param threadId The thread ID
     * @return The thread, or null if not found
     */
    @Nullable
    Thread getThread(String threadId);

    /**
     * Get threads in this channel with pagination.
     *
     * @param categoryId The category ID to filter threads, null for all threads
     * @return A page iterator for threads
     */
    PageIterator<Collection<Thread>> getThreads(@Nullable String categoryId);

    /**
     * Get all available categories in this thread channel.
     *
     * <p>Categories are used to organize threads into different topics or sections.
     * The returned collection includes both the default "All" category and custom categories.
     *
     * @return A collection of category IDs and names
     */
    Collection<ThreadCategory> getCategories();

    /**
     * Represents a category in a thread channel.
     *
     * @since 0.55.0
     */
    interface ThreadCategory {
        /**
         * Get the category ID.
         *
         * @return The category ID
         */
        String getId();

        /**
         * Get the category name.
         *
         * @return The category name
         */
        String getName();

        /**
         * Check if this is the default "All" category.
         *
         * @return True if this is the default category
         */
        boolean isDefault();
    }

}