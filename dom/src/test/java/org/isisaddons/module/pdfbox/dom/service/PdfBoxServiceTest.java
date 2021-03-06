/*
 *  Copyright 2013~2014 Dan Haywood
 *
 *  Licensed under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package org.isisaddons.module.pdfbox.dom.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.pdfbox.io.IOUtils;
import org.jmock.auto.Mock;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import org.apache.isis.applib.services.config.ConfigurationService;
import org.apache.isis.core.unittestsupport.jmocking.JUnitRuleMockery2;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class PdfBoxServiceTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Rule
    public JUnitRuleMockery2 context = JUnitRuleMockery2.createFor(JUnitRuleMockery2.Mode.INTERFACES_AND_CLASSES);

    @Mock
    ConfigurationService mockConfigurationService;

    PdfBoxService service;

    @Before
    public void setUp() throws Exception {
        service = new PdfBoxService();
    }

    @Test
    public void simple() throws Exception {

        // given
        InputStream pdf1= new FileInputStream(new File("src/test/java/org/isisaddons/module/pdfbox/dom/service/input-1.pdf"));
        final byte[] pdf1Bytes = IOUtils.toByteArray(pdf1);

        InputStream pdf2= new FileInputStream(new File("src/test/java/org/isisaddons/module/pdfbox/dom/service/input-2.pdf"));
        final byte[] pdf2Bytes = IOUtils.toByteArray(pdf2);

        InputStream expected = new FileInputStream(new File("src/test/java/org/isisaddons/module/pdfbox/dom/service/output-expected.pdf"));
        final byte[] expectedBytes = IOUtils.toByteArray(expected);

        // when
        final byte[] mergedBytes = service.merge(pdf1Bytes, pdf2Bytes);

        // then
        assertThat(mergedBytes.length, is(equalTo(expectedBytes.length)));

    }

    private static void write(byte[] data, OutputStream output) throws IOException {
        if(data != null) {
            output.write(data);
        }

    }

}
