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
package com.helger.commons.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This is an indicator, that an object is aware to break the style guide. This
 * may be evaluated in coding style guide checks.
 * 
 * @author Philip Helger
 */
@Retention (RetentionPolicy.CLASS)
@Target ({ ElementType.TYPE,
          ElementType.FIELD,
          ElementType.METHOD,
          ElementType.PARAMETER,
          ElementType.CONSTRUCTOR,
          ElementType.LOCAL_VARIABLE,
          ElementType.ANNOTATION_TYPE,
          ElementType.PACKAGE })
@Documented
public @interface CodingStyleguideUnaware
{
  String value() default "";
}
