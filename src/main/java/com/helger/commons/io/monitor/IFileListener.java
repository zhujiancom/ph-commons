/**
 * Copyright (C) 2006-2014 phloc systems (www.phloc.com)
 * Copyright (C) 2014 Philip Helger (www.helger.com)
 * philip[at]helger[dot]com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.helger.commons.io.monitor;

import javax.annotation.Nonnull;

/**
 * Listens for changes to a file.
 * 
 * @author <a href="http://commons.apache.org/vfs/team-list.html">Commons VFS
 *         team</a>
 * @author Philip Helger
 */
public interface IFileListener
{
  /**
   * Called when a file is created.
   * 
   * @param aEvent
   *        The FileChangeEvent. Never <code>null</code>.
   */
  void onFileCreated (@Nonnull FileChangeEvent aEvent);

  /**
   * Called when a file is deleted.
   * 
   * @param aEvent
   *        The FileChangeEvent. Never <code>null</code>.
   */
  void onFileDeleted (@Nonnull FileChangeEvent aEvent);

  /**
   * Called when a file is changed.
   * 
   * @param aEvent
   *        The FileChangeEvent. Never <code>null</code>.
   */
  void onFileChanged (@Nonnull FileChangeEvent aEvent);
}
