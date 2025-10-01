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
import snw.jkook.message.component.card.MultipleCardComponent;
import snw.jkook.util.PageIterator;

import java.util.Collection;

/**
 * Represents a thread post (main post/topic) in a thread channel.
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
 * @see ThreadReply
 * @see ThreadChannel
 * @since 0.55.0
 */
public interface ThreadPost {

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
    MultipleCardComponent getContent();

    /**
     * Get the preview content of this thread (plain text summary).
     *
     * @return The preview content string
     */
    String getPreviewContent();

    /**
     * Get the cover image URL for this thread.
     *
     * @return The cover image URL, or null if no cover is set
     */
    String getCover();

    /**
     * Get the status of this thread.
     *
     * <p>Status values:
     * <ul>
     *   <li>0 - Normal</li>
     *   <li>1 - Under review (审核中)</li>
     *   <li>2 - Approved (审核通过)</li>
     *   <li>3 - Edit under review (编辑审核中)</li>
     * </ul>
     *
     * @return The thread status code
     */
    int getStatus();

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
    long getCreateTime();

    /**
     * Get the timestamp when this thread was last active.
     *
     * <p>This reflects the latest activity time including replies and updates.
     *
     * @return The latest active timestamp in milliseconds
     */
    long getLatestActiveTime();

    /**
     * Check if this thread has been edited/updated.
     *
     * @return True if the thread has been updated after creation
     */
    boolean isUpdated();

    /**
     * Check if the content has been deleted.
     *
     * @return True if content is deleted
     */
    boolean isContentDeleted();

    /**
     * Get the content deletion type.
     *
     * <p>Type values:
     * <ul>
     *   <li>0 - Not deleted</li>
     *   <li>1 - Deleted by author</li>
     *   <li>2 - Deleted by moderator</li>
     *   <li>3 - Deleted by review (审核删除)</li>
     * </ul>
     *
     * @return The content deletion type code
     */
    int getContentDeletedType();

    /**
     * Get the number of collections (favorites/bookmarks) for this thread.
     *
     * @return The collection count
     */
    int getCollectNum();

    /**
     * Get the tags associated with this thread.
     *
     * @return A collection of tag strings
     */
    Collection<String> getTags();

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
     * Get all replies (posts) to this thread.
     *
     * @return A page iterator for thread replies
     */
    PageIterator<Collection<ThreadReply>> getReplies();

    /**
     * Reply to this thread.
     *
     * @param content The reply content (supports rich media)
     * @return The created thread reply
     */
    ThreadReply reply(String content);

    /**
     * Delete this thread.
     *
     * <p><b>Note:</b> This action requires appropriate permissions.
     *
     */
    void delete();

}