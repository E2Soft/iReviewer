/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
/*
 * This code was generated by https://code.google.com/p/google-apis-client-generator/
 * (build: 2015-03-26 20:30:19 UTC)
 * on 2015-06-30 at 01:33:39 UTC 
 * Modify at your own risk.
 */

package com.appspot.elevated_surge_702.crud.model;

/**
 * Model definition for MessagesGroupToUserMessage.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the crud. For a detailed explanation see:
 * <a href="http://code.google.com/p/google-http-java-client/wiki/JSON">http://code.google.com/p/google-http-java-client/wiki/JSON</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class MessagesGroupToUserMessage extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Boolean deleted;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String group;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key("last_modified")
  private com.google.api.client.util.DateTime lastModified;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String user;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String uuid;

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Boolean getDeleted() {
    return deleted;
  }

  /**
   * @param deleted deleted or {@code null} for none
   */
  public MessagesGroupToUserMessage setDeleted(java.lang.Boolean deleted) {
    this.deleted = deleted;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getGroup() {
    return group;
  }

  /**
   * @param group group or {@code null} for none
   */
  public MessagesGroupToUserMessage setGroup(java.lang.String group) {
    this.group = group;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public com.google.api.client.util.DateTime getLastModified() {
    return lastModified;
  }

  /**
   * @param lastModified lastModified or {@code null} for none
   */
  public MessagesGroupToUserMessage setLastModified(com.google.api.client.util.DateTime lastModified) {
    this.lastModified = lastModified;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getUser() {
    return user;
  }

  /**
   * @param user user or {@code null} for none
   */
  public MessagesGroupToUserMessage setUser(java.lang.String user) {
    this.user = user;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getUuid() {
    return uuid;
  }

  /**
   * @param uuid uuid or {@code null} for none
   */
  public MessagesGroupToUserMessage setUuid(java.lang.String uuid) {
    this.uuid = uuid;
    return this;
  }

  @Override
  public MessagesGroupToUserMessage set(String fieldName, Object value) {
    return (MessagesGroupToUserMessage) super.set(fieldName, value);
  }

  @Override
  public MessagesGroupToUserMessage clone() {
    return (MessagesGroupToUserMessage) super.clone();
  }

}
