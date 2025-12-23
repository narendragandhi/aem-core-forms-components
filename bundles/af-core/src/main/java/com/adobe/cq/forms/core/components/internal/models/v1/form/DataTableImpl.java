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

import javax.annotation.Nullable;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import com.adobe.cq.export.json.ComponentExporter;
import com.adobe.cq.export.json.ExporterConstants;
import com.adobe.cq.forms.core.components.internal.form.FormConstants;
import com.adobe.cq.forms.core.components.models.form.DataTable;
import com.adobe.cq.forms.core.components.models.form.FieldType;
import com.adobe.cq.forms.core.components.util.AbstractFieldImpl;

@Model(adaptables = { SlingHttpServletRequest.class, Resource.class }, adapters = { DataTable.class,
        ComponentExporter.class }, resourceType = { FormConstants.RT_FD_FORM_DATA_TABLE_V1 })
@Exporter(name = ExporterConstants.SLING_MODEL_EXPORTER_NAME, extensions = ExporterConstants.SLING_MODEL_EXTENSION)
public class DataTableImpl extends AbstractFieldImpl implements DataTable {

    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL, name = "dataSource")
    @Nullable
    protected String dataSource;

    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL, name = "dataSourceType")
    @Default(values = "inline")
    @Nullable
    protected String dataSourceType;

    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL, name = "columns")
    @Nullable
    protected String columns;

    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL, name = "viewConfig")
    @Nullable
    protected String viewConfig;

    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL, name = "height")
    @Default(values = "300px")
    @Nullable
    protected String height;

    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL, name = "pagination")
    @Default(booleanValues = true)
    protected boolean pagination;

    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL, name = "pageSize")
    @Default(intValues = 10)
    protected int pageSize;

    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL, name = "sortable")
    @Default(booleanValues = true)
    protected boolean sortable;

    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL, name = "filterable")
    @Default(booleanValues = true)
    protected boolean filterable;

    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL, name = "resizableColumns")
    @Default(booleanValues = true)
    protected boolean resizableColumns;

    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL, name = "selectableRows")
    @Default(booleanValues = false)
    protected boolean selectableRows;

    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL, name = "refreshInterval")
    @Default(intValues = 0)
    protected int refreshInterval;

    @Override
    public String getFieldType() {
        return super.getFieldType(FieldType.DATA_TABLE);
    }

    @Override
    @Nullable
    public String getDataSource() {
        return dataSource;
    }

    @Override
    @Nullable
    public String getDataSourceType() {
        return dataSourceType;
    }

    @Override
    @Nullable
    public String getColumns() {
        return columns;
    }

    @Override
    @Nullable
    public String getViewConfig() {
        return viewConfig;
    }

    @Override
    @Nullable
    public String getHeight() {
        return height;
    }

    @Override
    public boolean isPagination() {
        return pagination;
    }

    @Override
    public int getPageSize() {
        return pageSize;
    }

    @Override
    public boolean isSortable() {
        return sortable;
    }

    @Override
    public boolean isFilterable() {
        return filterable;
    }

    @Override
    public boolean isResizableColumns() {
        return resizableColumns;
    }

    @Override
    public boolean isSelectableRows() {
        return selectableRows;
    }

    @Override
    public int getRefreshInterval() {
        return refreshInterval;
    }
}
