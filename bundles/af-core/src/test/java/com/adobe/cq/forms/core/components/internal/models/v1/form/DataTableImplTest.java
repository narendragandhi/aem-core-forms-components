/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 ~ Copyright 2022 Adobe
 ~
 ~ Licensed under the Apache License, Version 2.0 (the "License");
 ~ you may not use this file except in compliance with the License.
 ~ You may obtain a copy of the License at
 ~
 ~     http://www.apache.org/licenses/LICENSE-2.0
 ~
 ~ Unless required by applicable law or agreed to in writing, software
 ~ distributed under the License is distributed on an "AS IS" BASIS,
 ~ WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 ~ See the License for the specific language governing permissions and
 ~ limitations under the License.
 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
package com.adobe.cq.forms.core.components.internal.models.v1.form;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;

import com.adobe.cq.forms.core.Utils;
import com.adobe.cq.forms.core.components.internal.form.FormConstants;
import com.adobe.cq.forms.core.components.models.form.DataTable;
import com.adobe.cq.forms.core.components.models.form.FieldType;
import com.adobe.cq.forms.core.context.FormsCoreComponentTestContext;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@ExtendWith(AemContextExtension.class)
public class DataTableImplTest {
    private static final String BASE = "/form/datatable";
    private static final String CONTENT_ROOT = "/content";
    private static final String PATH_DATATABLE = CONTENT_ROOT + "/datatable";
    private static final String PATH_DATATABLE_CUSTOMIZED = CONTENT_ROOT + "/datatable-customized";
    private static final String PATH_DATATABLE_URL_SOURCE = CONTENT_ROOT + "/datatable-url-source";
    private static final String PATH_DATATABLE_WITHOUT_FIELDTYPE = CONTENT_ROOT + "/datatable-without-fieldtype";

    private final AemContext context = FormsCoreComponentTestContext.newAemContext();

    @BeforeEach
    void setUp() {
        context.load().json(BASE + FormsCoreComponentTestContext.TEST_CONTENT_JSON, CONTENT_ROOT);
    }

    @Test
    void testExportedType() {
        DataTable dataTable = Utils.getComponentUnderTest(PATH_DATATABLE_CUSTOMIZED, DataTable.class, context);
        assertEquals(FormConstants.RT_FD_FORM_DATA_TABLE_V1, dataTable.getExportedType());
        DataTable dataTableMock = Mockito.mock(DataTable.class);
        Mockito.when(dataTableMock.getExportedType()).thenCallRealMethod();
        assertEquals("", dataTableMock.getExportedType());
    }

    @Test
    void testFieldType() {
        DataTable dataTable = Utils.getComponentUnderTest(PATH_DATATABLE_CUSTOMIZED, DataTable.class, context);
        assertEquals(FieldType.DATA_TABLE.getValue(), dataTable.getFieldType());
    }

    @Test
    void testGetName() {
        DataTable dataTable = Utils.getComponentUnderTest(PATH_DATATABLE_CUSTOMIZED, DataTable.class, context);
        assertEquals("dataTable2", dataTable.getName());
        DataTable dataTableMock = Mockito.mock(DataTable.class);
        Mockito.when(dataTableMock.getName()).thenCallRealMethod();
        assertEquals(null, dataTableMock.getName());
    }

    @Test
    void testGetLabel() {
        DataTable dataTable = Utils.getComponentUnderTest(PATH_DATATABLE_CUSTOMIZED, DataTable.class, context);
        assertEquals("Custom Data Table", dataTable.getLabel().getValue());
    }

    @Test
    void testGetDescription() {
        DataTable dataTable = Utils.getComponentUnderTest(PATH_DATATABLE_CUSTOMIZED, DataTable.class, context);
        assertEquals("This is a customized data table", dataTable.getDescription());
        DataTable dataTableMock = Mockito.mock(DataTable.class);
        Mockito.when(dataTableMock.getDescription()).thenCallRealMethod();
        assertEquals(null, dataTableMock.getDescription());
    }

    @Test
    void testGetDataSource() {
        DataTable dataTable = Utils.getComponentUnderTest(PATH_DATATABLE_CUSTOMIZED, DataTable.class, context);
        assertTrue(dataTable.getDataSource().contains("John Doe"));
        DataTable dataTableMock = Mockito.mock(DataTable.class);
        Mockito.when(dataTableMock.getDataSource()).thenCallRealMethod();
        assertEquals(null, dataTableMock.getDataSource());
    }

    @Test
    void testGetDataSourceType() {
        DataTable dataTable = Utils.getComponentUnderTest(PATH_DATATABLE_CUSTOMIZED, DataTable.class, context);
        assertEquals("inline", dataTable.getDataSourceType());
        DataTable dataTableMock = Mockito.mock(DataTable.class);
        Mockito.when(dataTableMock.getDataSourceType()).thenCallRealMethod();
        assertEquals("inline", dataTableMock.getDataSourceType());
    }

    @Test
    void testGetDataSourceTypeUrl() {
        DataTable urlDataTable = Utils.getComponentUnderTest(PATH_DATATABLE_URL_SOURCE, DataTable.class, context);
        assertNotNull("URL data table should not be null", urlDataTable);
        assertEquals("url", urlDataTable.getDataSourceType());
    }

    @Test
    void testGetColumns() {
        DataTable dataTable = Utils.getComponentUnderTest(PATH_DATATABLE_CUSTOMIZED, DataTable.class, context);
        assertTrue(dataTable.getColumns().contains("id"));
        assertTrue(dataTable.getColumns().contains("name"));
        assertTrue(dataTable.getColumns().contains("age"));

        DataTable dataTableMock = Mockito.mock(DataTable.class);
        Mockito.when(dataTableMock.getColumns()).thenCallRealMethod();
        assertEquals(null, dataTableMock.getColumns());
    }

    @Test
    void testGetViewConfig() {
        DataTable dataTable = Utils.getComponentUnderTest(PATH_DATATABLE_CUSTOMIZED, DataTable.class, context);
        assertTrue(dataTable.getViewConfig().contains("default"));
        assertTrue(dataTable.getViewConfig().contains("detailed"));

        DataTable dataTableMock = Mockito.mock(DataTable.class);
        Mockito.when(dataTableMock.getViewConfig()).thenCallRealMethod();
        assertEquals(null, dataTableMock.getViewConfig());
    }

    @Test
    void testGetHeight() {
        DataTable dataTable = Utils.getComponentUnderTest(PATH_DATATABLE_CUSTOMIZED, DataTable.class, context);
        assertEquals("400px", dataTable.getHeight());
    }

    @Test
    void testGetHeightDefault() {
        DataTable defaultDataTable = Utils.getComponentUnderTest(PATH_DATATABLE, DataTable.class, context);
        assertNotNull("Default data table should not be null", defaultDataTable);
        assertEquals("300px", defaultDataTable.getHeight());

        DataTable dataTableMock = Mockito.mock(DataTable.class);
        Mockito.when(dataTableMock.getHeight()).thenCallRealMethod();
        assertEquals("300px", dataTableMock.getHeight());
    }

    @Test
    void testIsPagination() {
        DataTable dataTable = Utils.getComponentUnderTest(PATH_DATATABLE_CUSTOMIZED, DataTable.class, context);
        assertTrue(dataTable.isPagination());

        DataTable dataTableMock = Mockito.mock(DataTable.class);
        Mockito.when(dataTableMock.isPagination()).thenCallRealMethod();
        assertTrue(dataTableMock.isPagination());
    }

    @Test
    void testGetPageSize() {
        DataTable dataTable = Utils.getComponentUnderTest(PATH_DATATABLE_CUSTOMIZED, DataTable.class, context);
        assertEquals(5, dataTable.getPageSize());
    }

    @Test
    void testGetPageSizeDefault() {
        DataTable defaultDataTable = Utils.getComponentUnderTest(PATH_DATATABLE, DataTable.class, context);
        assertNotNull("Default data table should not be null", defaultDataTable);
        assertEquals(10, defaultDataTable.getPageSize());

        DataTable dataTableMock = Mockito.mock(DataTable.class);
        Mockito.when(dataTableMock.getPageSize()).thenCallRealMethod();
        assertEquals(10, dataTableMock.getPageSize());
    }

    @Test
    void testIsSortable() {
        DataTable dataTable = Utils.getComponentUnderTest(PATH_DATATABLE_CUSTOMIZED, DataTable.class, context);
        assertTrue(dataTable.isSortable());

        DataTable dataTableMock = Mockito.mock(DataTable.class);
        Mockito.when(dataTableMock.isSortable()).thenCallRealMethod();
        assertTrue(dataTableMock.isSortable());
    }

    @Test
    void testIsFilterable() {
        DataTable dataTable = Utils.getComponentUnderTest(PATH_DATATABLE_CUSTOMIZED, DataTable.class, context);
        assertTrue(dataTable.isFilterable());

        DataTable dataTableMock = Mockito.mock(DataTable.class);
        Mockito.when(dataTableMock.isFilterable()).thenCallRealMethod();
        assertTrue(dataTableMock.isFilterable());
    }

    @Test
    void testIsResizableColumns() {
        DataTable dataTable = Utils.getComponentUnderTest(PATH_DATATABLE_CUSTOMIZED, DataTable.class, context);
        assertTrue(dataTable.isResizableColumns());

        DataTable dataTableMock = Mockito.mock(DataTable.class);
        Mockito.when(dataTableMock.isResizableColumns()).thenCallRealMethod();
        assertTrue(dataTableMock.isResizableColumns());
    }

    @Test
    void testIsSelectableRows() {
        DataTable dataTable = Utils.getComponentUnderTest(PATH_DATATABLE_CUSTOMIZED, DataTable.class, context);
        assertTrue(dataTable.isSelectableRows());
    }

    @Test
    void testIsSelectableRowsDefault() {
        DataTable defaultDataTable = Utils.getComponentUnderTest(PATH_DATATABLE, DataTable.class, context);
        assertNotNull("Default data table should not be null", defaultDataTable);
        assertFalse("Default data table should not have selectable rows", defaultDataTable.isSelectableRows());

        DataTable dataTableMock = Mockito.mock(DataTable.class);
        Mockito.when(dataTableMock.isSelectableRows()).thenCallRealMethod();
        assertFalse(dataTableMock.isSelectableRows());
    }

    @Test
    void testGetRefreshInterval() {
        DataTable dataTable = Utils.getComponentUnderTest(PATH_DATATABLE_CUSTOMIZED, DataTable.class, context);
        assertEquals(0, dataTable.getRefreshInterval());

        DataTable dataTableMock = Mockito.mock(DataTable.class);
        Mockito.when(dataTableMock.getRefreshInterval()).thenCallRealMethod();
        assertEquals(0, dataTableMock.getRefreshInterval());
    }

    @Test
    void testGetRefreshIntervalUrl() {
        DataTable urlDataTable = Utils.getComponentUnderTest(PATH_DATATABLE_URL_SOURCE, DataTable.class, context);
        assertNotNull("URL data table should not be null", urlDataTable);
        assertEquals(60, urlDataTable.getRefreshInterval());
    }

    @Test
    void testIsVisible() {
        DataTable dataTable = Utils.getComponentUnderTest(PATH_DATATABLE_CUSTOMIZED, DataTable.class, context);
        assertEquals(true, dataTable.isVisible());

        DataTable dataTableMock = Mockito.mock(DataTable.class);
        Mockito.when(dataTableMock.isVisible()).thenCallRealMethod();
        assertEquals(null, dataTableMock.isVisible());
    }

    @Test
    void testIsEnabled() {
        DataTable dataTable = Utils.getComponentUnderTest(PATH_DATATABLE_CUSTOMIZED, DataTable.class, context);
        assertEquals(true, dataTable.isEnabled());

        DataTable dataTableMock = Mockito.mock(DataTable.class);
        Mockito.when(dataTableMock.isEnabled()).thenCallRealMethod();
        assertEquals(null, dataTableMock.isEnabled());
    }

    @Test
    void testFieldTypeWithoutFieldTypeProperty() {
        DataTable dataTable = Utils.getComponentUnderTest(PATH_DATATABLE_WITHOUT_FIELDTYPE, DataTable.class, context);
        assertEquals(FieldType.DATA_TABLE.getValue(), dataTable.getFieldType());
    }
}
