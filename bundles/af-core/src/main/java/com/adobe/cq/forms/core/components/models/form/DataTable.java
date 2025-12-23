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
package com.adobe.cq.forms.core.components.models.form;

import org.jetbrains.annotations.Nullable;
import org.osgi.annotation.versioning.ConsumerType;

/**
 * Defines the form {@code DataTable} Sling Model used for the {@code /apps/core/fd/components/form/datatable/v1/datatable} component.
 *
 * @since com.adobe.cq.forms.core.components.models.form 0.0.1
 */
@ConsumerType
public interface DataTable extends Field {

    /**
     * Returns the data source (JSON string or URL).
     *
     * @return the data source
     * @since com.adobe.cq.forms.core.components.models.form 0.0.1
     */
    @Nullable
    default String getDataSource() {
        return null;
    }

    /**
     * Returns the data source type ("inline" or "url").
     *
     * @return the data source type
     * @since com.adobe.cq.forms.core.components.models.form 0.0.1
     */
    @Nullable
    default String getDataSourceType() {
        return "inline";
    }

    /**
     * Returns the column configuration as JSON string.
     *
     * @return the column configuration
     * @since com.adobe.cq.forms.core.components.models.form 0.0.1
     */
    @Nullable
    default String getColumns() {
        return null;
    }

    /**
     * Returns the view configuration as JSON string.
     *
     * @return the view configuration
     * @since com.adobe.cq.forms.core.components.models.form 0.0.1
     */
    @Nullable
    default String getViewConfig() {
        return null;
    }

    /**
     * Returns the table height.
     *
     * @return the table height
     * @since com.adobe.cq.forms.core.components.models.form 0.0.1
     */
    @Nullable
    default String getHeight() {
        return "300px";
    }

    /**
     * Returns {@code true} if pagination is enabled, otherwise {@code false}.
     *
     * @return {@code true} if pagination is enabled, otherwise {@code false}
     * @since com.adobe.cq.forms.core.components.models.form 0.0.1
     */
    default boolean isPagination() {
        return true;
    }

    /**
     * Returns the page size for pagination.
     *
     * @return the page size
     * @since com.adobe.cq.forms.core.components.models.form 0.0.1
     */
    default int getPageSize() {
        return 10;
    }

    /**
     * Returns {@code true} if sorting is enabled, otherwise {@code false}.
     *
     * @return {@code true} if sorting is enabled, otherwise {@code false}
     * @since com.adobe.cq.forms.core.components.models.form 0.0.1
     */
    default boolean isSortable() {
        return true;
    }

    /**
     * Returns {@code true} if filtering is enabled, otherwise {@code false}.
     *
     * @return {@code true} if filtering is enabled, otherwise {@code false}
     * @since com.adobe.cq.forms.core.components.models.form 0.0.1
     */
    default boolean isFilterable() {
        return true;
    }

    /**
     * Returns {@code true} if columns are resizable, otherwise {@code false}.
     *
     * @return {@code true} if columns are resizable, otherwise {@code false}
     * @since com.adobe.cq.forms.core.components.models.form 0.0.1
     */
    default boolean isResizableColumns() {
        return true;
    }

    /**
     * Returns {@code true} if rows are selectable, otherwise {@code false}.
     *
     * @return {@code true} if rows are selectable, otherwise {@code false}
     * @since com.adobe.cq.forms.core.components.models.form 0.0.1
     */
    default boolean isSelectableRows() {
        return false;
    }

    /**
     * Returns the refresh interval in seconds (0 means disabled).
     *
     * @return the refresh interval
     * @since com.adobe.cq.forms.core.components.models.form 0.0.1
     */
    default int getRefreshInterval() {
        return 0;
    }
}

