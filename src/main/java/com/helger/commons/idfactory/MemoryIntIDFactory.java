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
package com.helger.commons.idfactory;

import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.Nonnegative;
import javax.annotation.concurrent.ThreadSafe;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.hash.HashCodeGenerator;
import com.helger.commons.string.ToStringGenerator;

/**
 * A default implementation for non-negative in-memory IDs.
 * 
 * @author Philip Helger
 */
@ThreadSafe
public final class MemoryIntIDFactory implements IIntIDFactory
{
  /** The default start ID to use. */
  @Nonnegative
  public static final int DEFAULT_START_ID = 10000;

  @Nonnegative
  private final AtomicInteger m_aID;

  public MemoryIntIDFactory ()
  {
    // new IDs start at 10000
    this (DEFAULT_START_ID);
  }

  public MemoryIntIDFactory (@Nonnegative final int nStartID)
  {
    ValueEnforcer.isGE0 (nStartID, "StartID");
    m_aID = new AtomicInteger (nStartID);
  }

  @Nonnegative
  public int getNewID ()
  {
    return m_aID.getAndIncrement ();
  }

  @Override
  public boolean equals (final Object o)
  {
    if (o == this)
      return true;
    if (!(o instanceof MemoryIntIDFactory))
      return false;
    // AtomicInteger does not implement equals and hashCode!
    final MemoryIntIDFactory rhs = (MemoryIntIDFactory) o;
    return m_aID.get () == rhs.m_aID.get ();
  }

  @Override
  public int hashCode ()
  {
    return new HashCodeGenerator (this).append (m_aID.get ()).getHashCode ();
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (this).append ("ID", m_aID).toString ();
  }
}
