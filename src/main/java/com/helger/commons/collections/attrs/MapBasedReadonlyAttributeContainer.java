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
package com.helger.commons.collections.attrs;

import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.NotThreadSafe;

/**
 * Default implementation of the {@link IGenericAttributeContainer <String,
 * Object>} based on a hash map. This implementation may carry <code>null</code>
 * values but that is not recommended.
 *
 * @author Philip Helger
 */
@NotThreadSafe
public class MapBasedReadonlyAttributeContainer extends MapBasedGenericReadonlyAttributeContainer <String, Object>
{
  public MapBasedReadonlyAttributeContainer (@Nonnull final String sKey, @Nullable final Object aValue)
  {
    super (sKey, aValue);
  }

  public MapBasedReadonlyAttributeContainer (@Nonnull final Map <? extends String, ? extends Object> aMap)
  {
    super (aMap);
  }

  public MapBasedReadonlyAttributeContainer (@Nonnull final IGenericAttributeContainer <? extends String, ? extends Object> aCont)
  {
    super (aCont);
  }
}
