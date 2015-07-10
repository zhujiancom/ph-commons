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
package com.helger.commons.supplementary.test.java;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import com.helger.commons.lang.ClassHelper;
import com.helger.commons.lang.ClassLoaderHelper;

/**
 * Test the difference between loading from a ClassLoader and a Class. The
 * results are:
 * <ul>
 * <li>When using a ClassLoader NO leading slash may be present</li>
 * <li>When using a Class the leading slash MUST be present</li>
 * </ul>
 *
 * @author Philip Helger
 */
public final class JavaClassLoaderFuncTest
{
  @Test
  public void testGetResourceThisProject ()
  {
    final String sWithoutSlash = "classldr/test1.txt";
    final String sWithSlash = "/" + sWithoutSlash;

    // Context class loader
    assertNotNull (ClassLoaderHelper.getContextClassLoader ().getResource (sWithoutSlash));
    assertNull (ClassLoaderHelper.getContextClassLoader ().getResource (sWithSlash));

    assertNotNull (ClassLoaderHelper.getContextClassLoader ().getResourceAsStream (sWithoutSlash));
    assertNull (ClassLoaderHelper.getContextClassLoader ().getResourceAsStream (sWithSlash));

    // This is the work around to be used
    assertNotNull (ClassLoaderHelper.getResource (ClassLoaderHelper.getContextClassLoader (), sWithoutSlash));
    assertNotNull (ClassLoaderHelper.getResource (ClassLoaderHelper.getContextClassLoader (), sWithSlash));

    assertNotNull (ClassLoaderHelper.getResourceAsStream (ClassLoaderHelper.getContextClassLoader (), sWithoutSlash));
    assertNotNull (ClassLoaderHelper.getResourceAsStream (ClassLoaderHelper.getContextClassLoader (), sWithSlash));

    // Current class
    assertNull (JavaClassLoaderFuncTest.class.getResource (sWithoutSlash));
    assertNotNull (JavaClassLoaderFuncTest.class.getResource (sWithSlash));

    assertNull (JavaClassLoaderFuncTest.class.getResourceAsStream (sWithoutSlash));
    assertNotNull (JavaClassLoaderFuncTest.class.getResourceAsStream (sWithSlash));

    // This is the work around to be used
    assertNotNull (ClassHelper.getResource (JavaClassLoaderFuncTest.class, sWithoutSlash));
    assertNotNull (ClassHelper.getResource (JavaClassLoaderFuncTest.class, sWithSlash));

    assertNotNull (ClassHelper.getResourceAsStream (JavaClassLoaderFuncTest.class, sWithoutSlash));
    assertNotNull (ClassHelper.getResourceAsStream (JavaClassLoaderFuncTest.class, sWithSlash));

    // Current class class loader
    assertNotNull (ClassLoaderHelper.getClassClassLoader (JavaClassLoaderFuncTest.class).getResource (sWithoutSlash));
    assertNull (ClassLoaderHelper.getClassClassLoader (JavaClassLoaderFuncTest.class).getResource (sWithSlash));

    // System class loader
    assertNotNull (ClassLoaderHelper.getSystemClassLoader ().getResource (sWithoutSlash));
    assertNull (ClassLoaderHelper.getSystemClassLoader ().getResource (sWithSlash));
  }

  @Test
  public void testGetResourceLinkedProject ()
  {
    final String sWithoutSlash = "org/junit/Assert.class";
    final String sWithSlash = "/" + sWithoutSlash;

    // Context class loader
    assertNotNull (ClassLoaderHelper.getContextClassLoader ().getResource (sWithoutSlash));
    assertNull (ClassLoaderHelper.getContextClassLoader ().getResource (sWithSlash));

    // This is the work around to be used
    assertNotNull (ClassLoaderHelper.getResource (ClassLoaderHelper.getContextClassLoader (), sWithoutSlash));
    assertNotNull (ClassLoaderHelper.getResource (ClassLoaderHelper.getContextClassLoader (), sWithSlash));

    // Current class
    assertNull (JavaClassLoaderFuncTest.class.getResource (sWithoutSlash));
    assertNotNull (JavaClassLoaderFuncTest.class.getResource (sWithSlash));

    // Current class class loader
    assertNotNull (ClassLoaderHelper.getClassClassLoader (JavaClassLoaderFuncTest.class).getResource (sWithoutSlash));
    assertNull (ClassLoaderHelper.getClassClassLoader (JavaClassLoaderFuncTest.class).getResource (sWithSlash));

    // System class loader
    assertNotNull (ClassLoaderHelper.getSystemClassLoader ().getResource (sWithoutSlash));
    assertNull (ClassLoaderHelper.getSystemClassLoader ().getResource (sWithSlash));
  }

  @Test
  public void testGetResourceRuntime ()
  {
    final String sWithoutSlash = "java/lang/String.class";
    final String sWithSlash = "/" + sWithoutSlash;

    // Context class loader
    assertNotNull (ClassLoaderHelper.getContextClassLoader ().getResource (sWithoutSlash));
    assertNull (ClassLoaderHelper.getContextClassLoader ().getResource (sWithSlash));

    // This is the work around to be used
    assertNotNull (ClassLoaderHelper.getResource (ClassLoaderHelper.getContextClassLoader (), sWithoutSlash));
    assertNotNull (ClassLoaderHelper.getResource (ClassLoaderHelper.getContextClassLoader (), sWithSlash));

    // Current class
    assertNull (JavaClassLoaderFuncTest.class.getResource (sWithoutSlash));
    assertNotNull (JavaClassLoaderFuncTest.class.getResource (sWithSlash));

    // Current class class loader
    assertNotNull (ClassLoaderHelper.getClassClassLoader (JavaClassLoaderFuncTest.class).getResource (sWithoutSlash));
    assertNull (ClassLoaderHelper.getClassClassLoader (JavaClassLoaderFuncTest.class).getResource (sWithSlash));

    // System class loader
    assertNotNull (ClassLoaderHelper.getSystemClassLoader ().getResource (sWithoutSlash));
    assertNull (ClassLoaderHelper.getSystemClassLoader ().getResource (sWithSlash));
  }
}