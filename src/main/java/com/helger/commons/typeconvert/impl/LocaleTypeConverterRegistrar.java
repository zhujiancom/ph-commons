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
package com.helger.commons.typeconvert.impl;

import java.util.Locale;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;

import com.helger.commons.annotations.IsSPIImplementation;
import com.helger.commons.locale.LocaleCache;
import com.helger.commons.typeconvert.ITypeConverter;
import com.helger.commons.typeconvert.ITypeConverterRegistrarSPI;
import com.helger.commons.typeconvert.ITypeConverterRegistry;

/**
 * Register the locale specific type converter
 *
 * @author Philip Helger
 */
@Immutable
@IsSPIImplementation
public final class LocaleTypeConverterRegistrar implements ITypeConverterRegistrarSPI
{
  private static final class TypeConverterStringLocale implements ITypeConverter
  {
    public Locale convert (@Nonnull final Object aSource)
    {
      final String sSource = (String) aSource;
      // Special handling for ROOT locale
      // IFJDK5
      // ELSE
      if ("".equals (sSource))
        return Locale.ROOT;
      // ENDIF
      return LocaleCache.getLocale (sSource);
    }
  }

  public void registerTypeConverter (@Nonnull final ITypeConverterRegistry aRegistry)
  {
    // Locale
    aRegistry.registerTypeConverter (String.class, Locale.class, new TypeConverterStringLocale ());
  }
}
