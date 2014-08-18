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
package com.helger.commons.stats.visit;

import javax.annotation.Nonnull;

import com.helger.commons.stats.IStatisticsHandlerCache;
import com.helger.commons.stats.IStatisticsHandlerCounter;
import com.helger.commons.stats.IStatisticsHandlerKeyedCounter;
import com.helger.commons.stats.IStatisticsHandlerKeyedSize;
import com.helger.commons.stats.IStatisticsHandlerKeyedTimer;
import com.helger.commons.stats.IStatisticsHandlerSize;
import com.helger.commons.stats.IStatisticsHandlerTimer;

/**
 * Callback interface for visiting statistics.
 * 
 * @author Philip Helger
 */
public interface IStatisticsVisitor
{
  void onCache (@Nonnull String sName, @Nonnull IStatisticsHandlerCache aHandler);

  void onTimer (@Nonnull String sName, @Nonnull IStatisticsHandlerTimer aHandler);

  void onKeyedTimer (@Nonnull String sName, @Nonnull IStatisticsHandlerKeyedTimer aHandler);

  void onSize (@Nonnull String sName, @Nonnull IStatisticsHandlerSize aHandler);

  void onKeyedSize (@Nonnull String sName, @Nonnull IStatisticsHandlerKeyedSize aHandler);

  void onCounter (@Nonnull String sName, @Nonnull IStatisticsHandlerCounter aHandler);

  void onKeyedCounter (@Nonnull String sName, @Nonnull IStatisticsHandlerKeyedCounter aHandler);
}
