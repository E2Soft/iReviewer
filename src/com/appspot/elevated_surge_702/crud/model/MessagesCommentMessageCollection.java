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
 * on 2015-06-27 at 23:19:09 UTC 
 * Modify at your own risk.
 */

package com.appspot.elevated_surge_702.crud.model;

/**
 * Model definition for MessagesCommentMessageCollection.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the crud. For a detailed explanation see:
 * <a href="http://code.google.com/p/google-http-java-client/wiki/JSON">http://code.google.com/p/google-http-java-client/wiki/JSON</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class MessagesCommentMessageCollection extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.util.List<MessagesCommentMessage> items;

  /**
   * @return value or {@code null} for none
   */
  public java.util.List<MessagesCommentMessage> getItems() {
    return items;
  }

  /**
   * @param items items or {@code null} for none
   */
  public MessagesCommentMessageCollection setItems(java.util.List<MessagesCommentMessage> items) {
    this.items = items;
    return this;
  }

  @Override
  public MessagesCommentMessageCollection set(String fieldName, Object value) {
    return (MessagesCommentMessageCollection) super.set(fieldName, value);
  }

  @Override
  public MessagesCommentMessageCollection clone() {
    return (MessagesCommentMessageCollection) super.clone();
  }

}
