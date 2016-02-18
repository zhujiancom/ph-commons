package com.helger.commons.collection.ext;

import java.util.Collection;
import java.util.Vector;

import javax.annotation.Nonnull;

import com.helger.commons.annotation.ReturnsMutableCopy;

public class CommonsVector <ELEMENTTYPE> extends Vector <ELEMENTTYPE> implements ICommonsList <ELEMENTTYPE>
{
  public CommonsVector ()
  {}

  public CommonsVector (final int nInitialCapacity)
  {
    super (nInitialCapacity);
  }

  public CommonsVector (@Nonnull final Collection <? extends ELEMENTTYPE> aCont)
  {
    super (aCont);
  }

  @Nonnull
  @ReturnsMutableCopy
  public ICommonsList <ELEMENTTYPE> getCopy ()
  {
    return new CommonsVector <> (this);
  }
}
