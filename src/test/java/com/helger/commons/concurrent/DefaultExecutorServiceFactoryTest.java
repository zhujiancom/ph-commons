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
package com.helger.commons.concurrent;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.concurrent.ExecutorService;

import org.junit.Test;

/**
 * Test class for class {@link DefaultExecutorServiceFactory}.
 * 
 * @author Philip Helger
 */
public final class DefaultExecutorServiceFactoryTest
{
  @Test
  public void testAll ()
  {
    for (int i = 1; i < 10; ++i)
    {
      final ExecutorService aES = new DefaultExecutorServiceFactory ().getExecutorService (i);
      assertNotNull (aES);
      new ManagedExecutorService (aES).shutdownAndWaitUntilAllTasksAreFinished ();
    }

    try
    {
      new DefaultExecutorServiceFactory ().getExecutorService (0);
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {}
  }
}
