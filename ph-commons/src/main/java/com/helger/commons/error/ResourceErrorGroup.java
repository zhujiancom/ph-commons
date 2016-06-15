/**
 * Copyright (C) 2014-2016 Philip Helger (www.helger.com)
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
package com.helger.commons.error;

import java.util.Iterator;
import java.util.function.Consumer;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.concurrent.NotThreadSafe;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.annotation.ReturnsMutableCopy;
import com.helger.commons.collection.ext.CommonsArrayList;
import com.helger.commons.collection.ext.ICommonsList;
import com.helger.commons.hashcode.HashCodeGenerator;
import com.helger.commons.lang.ICloneable;
import com.helger.commons.state.EChange;
import com.helger.commons.state.IClearable;
import com.helger.commons.string.ToStringGenerator;

/**
 * Contains a list of resource errors and some sanity access methods.
 *
 * @author Philip Helger
 */
@NotThreadSafe
public class ResourceErrorGroup implements IResourceErrorGroup, ICloneable <ResourceErrorGroup>, IClearable
{
  private final ICommonsList <IResourceError> m_aErrors = new CommonsArrayList <> ();

  public ResourceErrorGroup ()
  {}

  public ResourceErrorGroup (@Nonnull final IResourceError aResourceError)
  {
    addResourceError (aResourceError);
  }

  public ResourceErrorGroup (@Nonnull final IResourceError... aResourceErrors)
  {
    ValueEnforcer.notNull (aResourceErrors, "ResourceErrors");
    for (final IResourceError aResourceError : aResourceErrors)
      addResourceError (aResourceError);
  }

  public ResourceErrorGroup (@Nonnull final Iterable <? extends IResourceError> aResourceErrors)
  {
    ValueEnforcer.notNull (aResourceErrors, "ResourceErrors");
    for (final IResourceError aResourceError : aResourceErrors)
      addResourceError (aResourceError);
  }

  /**
   * Add a new resource error item.
   *
   * @param aResourceError
   *        The resource error to be added. May not be <code>null</code>.
   * @return this
   */
  @Nonnull
  public final ResourceErrorGroup addResourceError (@Nonnull final IResourceError aResourceError)
  {
    ValueEnforcer.notNull (aResourceError, "ResourceError");
    m_aErrors.add (aResourceError);
    return this;
  }

  /**
   * Add multiple resource error items.
   *
   * @param aResourceErrors
   *        The resource errors to be added. May not be <code>null</code>.
   * @return this
   * @since 5.3.0
   */
  @Nonnull
  public ResourceErrorGroup addResourceErrors (@Nonnull final IResourceError... aResourceErrors)
  {
    ValueEnforcer.notNull (aResourceErrors, "ResourceErrors");
    for (final IResourceError aResourceError : aResourceErrors)
      addResourceError (aResourceError);
    return this;
  }

  /**
   * Add multiple resource error items.
   *
   * @param aResourceErrors
   *        The resource errors to be added. May not be <code>null</code>.
   * @return this
   * @since 5.3.0
   */
  @Nonnull
  public ResourceErrorGroup addResourceErrors (@Nonnull final Iterable <? extends IResourceError> aResourceErrors)
  {
    ValueEnforcer.notNull (aResourceErrors, "ResourceErrors");
    for (final IResourceError aResourceError : aResourceErrors)
      addResourceError (aResourceError);
    return this;
  }

  /**
   * Add a all resource errors of the other group
   *
   * @param aResourceErrorGroup
   *        The resource error group to be added. May not be <code>null</code>.
   * @return this
   */
  @Nonnull
  public final ResourceErrorGroup addResourceErrorGroup (@Nonnull final IResourceErrorGroup aResourceErrorGroup)
  {
    ValueEnforcer.notNull (aResourceErrorGroup, "ResourceErrorGroup");
    m_aErrors.addAll (aResourceErrorGroup.getAllResourceErrors ());
    return this;
  }

  public boolean containsOnlySuccess ()
  {
    return m_aErrors.containsOnly (e -> e.isSuccess ());
  }

  public boolean containsAtLeastOneSuccess ()
  {
    return m_aErrors.containsAny (e -> e.isSuccess ());
  }

  public boolean containsNoSuccess ()
  {
    return m_aErrors.containsNone (e -> e.isSuccess ());
  }

  @Nonnegative
  public int getSuccessCount ()
  {
    return m_aErrors.getCount (e -> e.isSuccess ());
  }

  public boolean containsOnlyFailure ()
  {
    return m_aErrors.containsOnly (e -> e.isFailure ());
  }

  public boolean containsAtLeastOneFailure ()
  {
    return m_aErrors.containsAny (e -> e.isFailure ());
  }

  public boolean containsNoFailure ()
  {
    return m_aErrors.containsNone (e -> e.isFailure ());
  }

  @Nonnegative
  public int getFailureCount ()
  {
    return m_aErrors.getCount (e -> e.isFailure ());
  }

  public boolean containsOnlyError ()
  {
    return m_aErrors.containsOnly (e -> e.isError ());
  }

  public boolean containsAtLeastOneError ()
  {
    return m_aErrors.containsAny (e -> e.isError ());
  }

  public boolean containsNoError ()
  {
    return m_aErrors.containsNone (e -> e.isError ());
  }

  @Nonnegative
  public int getErrorCount ()
  {
    return m_aErrors.getCount (e -> e.isError ());
  }

  @Nonnull
  @ReturnsMutableCopy
  public ResourceErrorGroup getAllFailures ()
  {
    final ResourceErrorGroup ret = new ResourceErrorGroup ();
    m_aErrors.findAll (e -> e.isFailure (), ret::addResourceError);
    return ret;
  }

  @Nonnull
  @ReturnsMutableCopy
  public ResourceErrorGroup getAllErrors ()
  {
    final ResourceErrorGroup ret = new ResourceErrorGroup ();
    m_aErrors.findAll (e -> e.isError (), ret::addResourceError);
    return ret;
  }

  @Nonnull
  public IErrorLevel getMostSevereErrorLevel ()
  {
    IErrorLevel aRet = EErrorLevel.SUCCESS;
    for (final IResourceError aError : m_aErrors)
    {
      final IErrorLevel aCur = aError.getErrorLevel ();
      if (aCur.isMoreSevereThan (aRet))
      {
        aRet = aCur;
        if (aRet.isHighest ())
          break;
      }
    }
    return aRet;
  }

  @Nonnegative
  public int getSize ()
  {
    return m_aErrors.size ();
  }

  public boolean isEmpty ()
  {
    return m_aErrors.isEmpty ();
  }

  @Nonnull
  @ReturnsMutableCopy
  public ResourceErrorGroup getClone ()
  {
    return new ResourceErrorGroup (m_aErrors);
  }

  @Nonnull
  public Iterator <IResourceError> iterator ()
  {
    return m_aErrors.iterator ();
  }

  @Nonnull
  @ReturnsMutableCopy
  public ICommonsList <IResourceError> getAllResourceErrors ()
  {
    return m_aErrors.getClone ();
  }

  public void forEachResourceError (@Nonnull final Consumer <? super IResourceError> aConsumer)
  {
    m_aErrors.forEach (aConsumer);
  }

  @Nonnull
  public EChange clear ()
  {
    return m_aErrors.removeAll ();
  }

  @Override
  public boolean equals (final Object o)
  {
    if (o == this)
      return true;
    if (o == null || !getClass ().equals (o.getClass ()))
      return false;
    final ResourceErrorGroup rhs = (ResourceErrorGroup) o;
    return m_aErrors.equals (rhs.m_aErrors);
  }

  @Override
  public int hashCode ()
  {
    return new HashCodeGenerator (this).append (m_aErrors).getHashCode ();
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (this).append ("errors", m_aErrors).toString ();
  }
}
