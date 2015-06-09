/**
 * Copyright (C) 2014-2015 Philip Helger (www.helger.com)
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
package com.helger.commons.scope;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.GuardedBy;
import javax.annotation.concurrent.ThreadSafe;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.scope.factory.DefaultScopeFactory;
import com.helger.commons.scope.factory.IScopeFactory;

/**
 * The meta scope factory holding both the factory for non-web scopes as well as
 * the factory for web-scopes.
 *
 * @author Philip Helger
 */
@ThreadSafe
public final class MetaScopeFactory
{
  private static final ReadWriteLock s_aRWLock = new ReentrantReadWriteLock ();

  @GuardedBy ("s_aRWLock")
  private static IScopeFactory s_aScopeFactory = new DefaultScopeFactory ();

  private MetaScopeFactory ()
  {}

  /**
   * Set the default non-web scope factory
   *
   * @param aScopeFactory
   *        The scope factory to use. May not be <code>null</code>.
   */
  public static void setScopeFactory (@Nonnull final IScopeFactory aScopeFactory)
  {
    ValueEnforcer.notNull (aScopeFactory, "ScopeFactory");

    s_aRWLock.writeLock ().lock ();
    try
    {
      s_aScopeFactory = aScopeFactory;
    }
    finally
    {
      s_aRWLock.writeLock ().unlock ();
    }
  }

  /**
   * @return The scope factory for non-web scopes. Never <code>null</code>.
   */
  @Nonnull
  public static IScopeFactory getScopeFactory ()
  {
    s_aRWLock.readLock ().lock ();
    try
    {
      return s_aScopeFactory;
    }
    finally
    {
      s_aRWLock.readLock ().unlock ();
    }
  }
}