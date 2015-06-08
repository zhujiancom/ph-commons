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

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.ThreadSafe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.annotation.Nonempty;
import com.helger.commons.annotation.OverrideOnDemand;
import com.helger.commons.annotation.ReturnsMutableCopy;
import com.helger.commons.callback.INonThrowingCallableWithParameter;
import com.helger.commons.callback.INonThrowingRunnableWithParameter;
import com.helger.commons.callback.adapter.AdapterRunnableToCallableWithParameter;
import com.helger.commons.collection.attr.MapBasedAttributeContainer;
import com.helger.commons.hashcode.HashCodeGenerator;
import com.helger.commons.string.ToStringGenerator;

/**
 * Abstract scope implementation based on a Map containing the attribute values.
 *
 * @author Philip Helger
 */
@ThreadSafe
public abstract class AbstractMapBasedScope extends MapBasedAttributeContainer <String, Object> implements IScope
{
  private static final Logger s_aLogger = LoggerFactory.getLogger (AbstractMapBasedScope.class);

  protected ReadWriteLock m_aRWLock = new ReentrantReadWriteLock ();
  /** ID of the scope */
  private final String m_sScopeID;
  /** Is the scope currently in destruction? */
  private boolean m_bInDestruction = false;
  /** Is the scope already completely destroyed? */
  private boolean m_bDestroyed = false;

  /**
   * Ctor.
   *
   * @param sScopeID
   *        The scope ID. May neither be <code>null</code> nor empty.
   */
  public AbstractMapBasedScope (@Nonnull @Nonempty final String sScopeID)
  {
    super (true, new ConcurrentHashMap <String, Object> ());
    m_sScopeID = ValueEnforcer.notEmpty (sScopeID, "ScopeID");
  }

  @Nonnull
  @Nonempty
  public final String getID ()
  {
    return m_sScopeID;
  }

  public final boolean isValid ()
  {
    m_aRWLock.readLock ().lock ();
    try
    {
      return !m_bInDestruction && !m_bDestroyed;
    }
    finally
    {
      m_aRWLock.readLock ().unlock ();
    }
  }

  public final boolean isInDestruction ()
  {
    m_aRWLock.readLock ().lock ();
    try
    {
      return m_bInDestruction;
    }
    finally
    {
      m_aRWLock.readLock ().unlock ();
    }
  }

  public final boolean isDestroyed ()
  {
    m_aRWLock.readLock ().lock ();
    try
    {
      return m_bDestroyed;
    }
    finally
    {
      m_aRWLock.readLock ().unlock ();
    }
  }

  /**
   * Override this method to perform further actions BEFORE the scope is
   * destroyed.
   */
  @OverrideOnDemand
  protected void preDestroy ()
  {}

  /**
   * Override this method to destroy all scopes owned by this scope.
   */
  @OverrideOnDemand
  protected void destroyOwnedScopes ()
  {}

  /**
   * Override this method to perform further actions AFTER the scope was
   * destroyed.
   */
  @OverrideOnDemand
  protected void postDestroy ()
  {}

  public final void destroyScope ()
  {
    preDestroy ();

    m_aRWLock.writeLock ().lock ();
    try
    {
      if (m_bDestroyed)
        throw new IllegalStateException ("Scope " + getID () + " is already destroyed!");
      if (m_bInDestruction)
        throw new IllegalStateException ("Scope " + getID () + " is already in destruction!");
      m_bInDestruction = true;
    }
    finally
    {
      m_aRWLock.writeLock ().unlock ();
    }

    // destroy all owned scopes before destroying this scope!
    destroyOwnedScopes ();

    // Call callback (if special interface is implemented)
    for (final Object aValue : getAllAttributeValues ())
      if (aValue instanceof IScopeDestructionAware)
        try
        {
          ((IScopeDestructionAware) aValue).onScopeDestruction (this);
        }
        catch (final Throwable t)
        {
          s_aLogger.error ("Failed to call onScopeDestruction in scope " + getID () + " for " + aValue, t);
        }

    // Finished destruction process -> remember this
    m_aRWLock.writeLock ().lock ();
    try
    {
      // remove all attributes (double write lock is no problem)
      clear ();

      m_bDestroyed = true;
      m_bInDestruction = false;
    }
    finally
    {
      m_aRWLock.writeLock ().unlock ();
    }

    postDestroy ();
  }

  public final void runAtomic (@Nonnull final INonThrowingRunnableWithParameter <IScope> aRunnable)
  {
    // Wrap runnable in callable
    runAtomic (AdapterRunnableToCallableWithParameter.createAdapter (aRunnable));
  }

  @Nullable
  public final <T> T runAtomic (@Nonnull final INonThrowingCallableWithParameter <T, IScope> aCallable)
  {
    ValueEnforcer.notNull (aCallable, "Callable");

    m_aRWLock.writeLock ().lock ();
    try
    {
      return aCallable.call (this);
    }
    finally
    {
      m_aRWLock.writeLock ().unlock ();
    }
  }

  @Nonnull
  @ReturnsMutableCopy
  public final Map <String, IScopeRenewalAware> getAllScopeRenewalAwareAttributes ()
  {
    final Map <String, IScopeRenewalAware> ret = new HashMap <String, IScopeRenewalAware> ();
    for (final Map.Entry <String, Object> aEntry : getAllAttributes ().entrySet ())
    {
      final Object aValue = aEntry.getValue ();
      if (aValue instanceof IScopeRenewalAware)
        ret.put (aEntry.getKey (), (IScopeRenewalAware) aValue);
    }
    return ret;
  }

  @Override
  public boolean equals (final Object o)
  {
    if (o == this)
      return true;
    if (!super.equals (o))
      return false;
    final AbstractMapBasedScope rhs = (AbstractMapBasedScope) o;
    return m_sScopeID.equals (rhs.m_sScopeID);
  }

  @Override
  public int hashCode ()
  {
    return HashCodeGenerator.getDerived (super.hashCode ()).append (m_sScopeID).getHashCode ();
  }

  @Override
  public String toString ()
  {
    return ToStringGenerator.getDerived (super.toString ())
                            .append ("ScopeID", m_sScopeID)
                            .append ("InDestruction", m_bInDestruction)
                            .append ("Destroyed", m_bDestroyed)
                            .toString ();
  }
}
