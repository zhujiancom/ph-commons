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
package com.helger.commons.concurrent.collector;

import com.helger.commons.callback.IThrowingRunnableWithParameter;

final class MockConcurrentCollectorSingle extends ConcurrentCollectorSingle <String> implements
                                                                                    IThrowingRunnableWithParameter <String>
{
  private int m_nPerformCount = 0;

  public MockConcurrentCollectorSingle ()
  {
    setPerformer (this);
  }

  public void run (final String aObject)
  {
    m_nPerformCount++;
  }

  public int getPerformCount ()
  {
    return m_nPerformCount;
  }
}
