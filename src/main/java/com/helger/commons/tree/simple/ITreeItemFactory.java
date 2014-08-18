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
package com.helger.commons.tree.simple;

import javax.annotation.Nonnull;

import com.helger.commons.factory.IHierarchicalFactory;
import com.helger.commons.factory.IHierarchicalRootFactory;

/**
 * Interface for a simple tree item factory
 * 
 * @author Philip Helger
 * @param <DATATYPE>
 *        item value type
 * @param <ITEMTYPE>
 *        item implementation type
 */
public interface ITreeItemFactory <DATATYPE, ITEMTYPE extends ITreeItem <DATATYPE, ITEMTYPE>> extends
                                                                                              IHierarchicalFactory <ITEMTYPE>,
                                                                                              IHierarchicalRootFactory <ITEMTYPE>
{
  /**
   * {@inheritDoc}
   */
  @Nonnull
  ITEMTYPE createRoot ();

  /**
   * {@inheritDoc}
   */
  @Nonnull
  ITEMTYPE create (@Nonnull ITEMTYPE aParent);
}
