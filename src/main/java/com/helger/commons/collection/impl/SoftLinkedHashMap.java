package com.helger.commons.collection.impl;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;

import com.helger.commons.annotation.OverrideOnDemand;

/**
 * Soft {@link HashMap} implementation based on
 * http://www.javaspecialists.eu/archive/Issue015.html<br />
 * The entrySet implementation is from org.hypergraphdb.util
 *
 * @author Philip Helger
 * @param <K>
 *        Key type
 * @param <V>
 *        Value type
 */
public class SoftLinkedHashMap <K, V> extends AbstractSoftMap <K, V>
{
  private final int m_nMaxSize;

  private static interface IRemoveEldest <K, V>
  {
    boolean removeEldestSoftEntry (Map.Entry <K, V> aEntry);
  }

  private static class InternalLinkedHashMap <K, V> extends LinkedHashMap <K, SoftValue <K, V>>
  {
    // Note: 0.75f is the same as HashMap.DEFAULT_LOAD_FACTOR
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;

    private IRemoveEldest <K, V> m_aCallback;

    public InternalLinkedHashMap (@Nonnegative final int nMaxSize)
    {
      super (nMaxSize, DEFAULT_LOAD_FACTOR, true);
    }

    @Override
    protected final boolean removeEldestEntry (@Nonnull final Map.Entry <K, SoftValue <K, V>> aEldest)
    {
      final MapEntry <K, V> aEntry = new MapEntry <K, V> (aEldest.getKey (), aEldest.getValue ().get ());
      return m_aCallback.removeEldestSoftEntry (aEntry);
    }
  }

  public SoftLinkedHashMap (@Nonnegative final int nMaxSize)
  {
    super (new InternalLinkedHashMap <K, V> (nMaxSize));
    ((InternalLinkedHashMap <K, V>) m_aSrcMap).m_aCallback = new IRemoveEldest <K, V> ()
    {
      public boolean removeEldestSoftEntry (final Map.Entry <K, V> aEldest)
      {
        final int nSize = size ();
        if (nSize <= m_nMaxSize)
        {
          // No need to remove anything
          return false;
        }

        // Invoke protected method
        onRemoveEldestEntry (nSize, aEldest);
        return true;
      }
    };
    m_nMaxSize = nMaxSize;
  }

  /**
   * @return The maximum number of elements that can reside inside this object.
   *         Never &lt; 0.
   */
  @Nonnegative
  public final int getMaxSize ()
  {
    return m_nMaxSize;
  }

  /**
   * Protected method that is invoked every time the oldest entry is removed.
   *
   * @param nSize
   *        Current size of the map. Always &ge; 0.
   * @param aEldest
   *        The map entry that is removed. Never <code>null</code>.
   */
  @OverrideOnDemand
  protected void onRemoveEldestEntry (@Nonnegative final int nSize, @Nonnull final Map.Entry <K, V> aEldest)
  {}
}