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
package com.helger.commons.state;

import javax.annotation.Nonnull;

/**
 * Small enum for manager actions to indicate finished or unfinished states.
 * 
 * @author Philip Helger
 */
public enum EFinish implements IFinishIndicator
{
  FINISHED,
  UNFINISHED;

  public boolean isFinished ()
  {
    return this == FINISHED;
  }

  public boolean isUnfinished ()
  {
    return this == UNFINISHED;
  }

  @Nonnull
  public EFinish or (@Nonnull final IFinishIndicator aFinish)
  {
    return valueOf (isFinished () || aFinish.isFinished ());
  }

  @Nonnull
  public EFinish and (@Nonnull final IFinishIndicator aFinish)
  {
    return valueOf (isFinished () && aFinish.isFinished ());
  }

  @Nonnull
  public static EFinish valueOf (final boolean bFinished)
  {
    return bFinished ? FINISHED : UNFINISHED;
  }

  @Nonnull
  public static EFinish valueOf (@Nonnull final IFinishIndicator aFinishIndicator)
  {
    return valueOf (aFinishIndicator.isFinished ());
  }
}
