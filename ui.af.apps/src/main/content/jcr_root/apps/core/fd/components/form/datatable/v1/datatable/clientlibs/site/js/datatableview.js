/*******************************************************************************
 * Copyright 2022 Adobe
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
(function() {

    "use strict";
    
    // Check if Tabulator is available
    if (typeof Tabulator === 'undefined') {
        console.error('Tabulator library is not loaded. Please include Tabulator.js before this component.');
        return;
    }

    class DataTable extends FormView.FormFieldBase {

        static NS = FormView.Constants.NS;
        static IS = "adaptiveFormDataTable";
        static bemBlock = 'cmp-adaptiveform-datatable';
        
        static selectors = {
            self: "[data-" + this.NS + '-is="' + this.IS + '"]',
            container: `.${DataTable.bemBlock}__container`,
            table: `.${DataTable.bemBlock}__table`,
            label: `.${DataTable.bemBlock}__label`,
            errorDiv: `.${DataTable.bemBlock}__errormessage`
        };

        constructor(params) {
            super(params);
            this.tabulatorInstance = null;
            this.refreshTimer = null;
            this.currentView = null;
        }

        getContainer() {
            return this.element.querySelector(DataTable.selectors.container);
        }

        getTableElement() {
            return this.element.querySelector(DataTable.selectors.table);
        }

        getErrorDiv() {
            return this.element.querySelector(DataTable.selectors.errorDiv);
        }

        /**
         * Parse JSON string safely
         */
        parseJSON(jsonString) {
            try {
                return jsonString ? JSON.parse(jsonString) : null;
            } catch (e) {
                console.error('Invalid JSON:', e);
                return null;
            }
        }

        /**
         * Load data from source
         */
        async loadData() {
            const dataSource = this.element.dataset.cmpDatasource;
            const dataSourceType = this.element.dataset.cmpDatasourcetype || 'inline';

            if (!dataSource) {
                this.showError('Data source is not configured');
                return null;
            }

            if (dataSourceType === 'inline') {
                return this.parseJSON(dataSource);
            } else if (dataSourceType === 'url') {
                try {
                    const response = await fetch(dataSource);
                    if (!response.ok) {
                        throw new Error(`HTTP error! status: ${response.status}`);
                    }
                    return await response.json();
                } catch (error) {
                    console.error('Error loading data from URL:', error);
                    this.showError('Failed to load data from URL: ' + error.message);
                    return null;
                }
            }

            return null;
        }

        /**
         * Initialize Tabulator table
         */
        async initializeTable() {
            const tableElement = this.getTableElement();
            if (!tableElement) {
                console.error('Table element not found');
                return;
            }

            // Get configuration from data attributes
            const config = {
                height: this.element.dataset.cmpHeight || '300px',
                pagination: this.element.dataset.cmpPagination === 'true',
                pageSize: parseInt(this.element.dataset.cmpPagesize) || 10,
                sortable: this.element.dataset.cmpSortable === 'true',
                resizableColumns: this.element.dataset.cmpResizablecolumns === 'true',
                selectableRows: this.element.dataset.cmpSelectablerows === 'true',
                layout: 'fitColumns',
                placeholder: 'No Data Available'
            };

            // Parse column configuration
            const columnsConfig = this.parseJSON(this.element.dataset.cmpColumns);
            if (columnsConfig && Array.isArray(columnsConfig)) {
                config.columns = columnsConfig;
            }

            // Load data
            const data = await this.loadData();
            if (!data) {
                return;
            }

            // Ensure data is an array
            const tableData = Array.isArray(data) ? data : [data];

            // Initialize Tabulator
            this.tabulatorInstance = new Tabulator(tableElement, {
                ...config,
                data: tableData,
                pagination: config.pagination,
                paginationSize: config.pageSize,
                paginationSizeSelector: config.pagination ? [10, 25, 50, 100] : false,
                movableColumns: config.resizableColumns,
                selectableRows: config.selectableRows ? 1 : false,
                // Event handlers
                rowClick: (e, row) => {
                    this.onRowClick(e, row);
                },
                cellEdited: (cell) => {
                    this.onCellEdited(cell);
                },
                dataLoaded: () => {
                    this.onDataLoaded();
                },
                rowSelected: (row) => {
                    this.onRowSelected(row);
                },
                rowDeselected: (row) => {
                    this.onRowDeselected(row);
                }
            });

            // Initialize views if configured
            this.initializeViews();

            // Set up auto-refresh if configured
            this.setupAutoRefresh();
        }

        /**
         * Initialize multiple views
         */
        initializeViews() {
            const viewConfig = this.parseJSON(this.element.dataset.cmpViewconfig);
            if (!viewConfig || typeof viewConfig !== 'object') {
                return;
            }

            // Store view configuration
            this.viewConfig = viewConfig;

            // Apply default view if specified
            if (viewConfig.default && Array.isArray(viewConfig.default.columns)) {
                this.applyView('default');
            }
        }

        /**
         * Apply a specific view
         */
        applyView(viewName) {
            if (!this.tabulatorInstance || !this.viewConfig || !this.viewConfig[viewName]) {
                return;
            }

            const view = this.viewConfig[viewName];
            if (Array.isArray(view.columns)) {
                // Show/hide columns based on view configuration
                const allColumns = this.tabulatorInstance.getColumns();
                allColumns.forEach(column => {
                    const field = column.getField();
                    const visible = view.columns.includes(field);
                    if (visible) {
                        column.show();
                    } else {
                        column.hide();
                    }
                });

                this.currentView = viewName;
            }
        }

        /**
         * Set up auto-refresh
         */
        setupAutoRefresh() {
            const refreshInterval = parseInt(this.element.dataset.cmpRefreshinterval) || 0;
            if (refreshInterval > 0 && this.element.dataset.cmpDatasourcetype === 'url') {
                this.refreshTimer = setInterval(() => {
                    this.refreshData();
                }, refreshInterval * 1000);
            }
        }

        /**
         * Refresh table data
         */
        async refreshData() {
            if (!this.tabulatorInstance) {
                return;
            }

            const data = await this.loadData();
            if (data) {
                const tableData = Array.isArray(data) ? data : [data];
                this.tabulatorInstance.replaceData(tableData);
            }
        }

        /**
         * Show error message
         */
        showError(message) {
            const errorDiv = this.getErrorDiv();
            if (errorDiv) {
                errorDiv.textContent = message;
                errorDiv.style.display = 'block';
            }
        }

        /**
         * Hide error message
         */
        hideError() {
            const errorDiv = this.getErrorDiv();
            if (errorDiv) {
                errorDiv.style.display = 'none';
            }
        }

        /**
         * Event handlers
         */
        onRowClick(e, row) {
            // Handle row click
            if (this._model) {
                const rowData = row.getData();
                this.setModelValue(rowData);
            }
        }

        onCellEdited(cell) {
            // Handle cell edit
            if (this._model) {
                const rowData = cell.getRow().getData();
                this.setModelValue(rowData);
            }
        }

        onDataLoaded() {
            // Update form model when data is loaded
            if (this._model && this.tabulatorInstance) {
                const data = this.tabulatorInstance.getData();
                this.setModelValue(data);
            }
        }

        onRowSelected(row) {
            // Handle row selection
            if (this._model) {
                const selectedRows = this.tabulatorInstance.getSelectedData();
                this.setModelValue(selectedRows);
            }
        }

        onRowDeselected(row) {
            // Handle row deselection
            if (this._model) {
                const selectedRows = this.tabulatorInstance.getSelectedData();
                this.setModelValue(selectedRows);
            }
        }

        /**
         * Set model and initialize table
         */
        setModel(model) {
            super.setModel(model);
            
            // Initialize table when model is set
            if (this.element && !this.tabulatorInstance) {
                this.initializeTable().catch(error => {
                    console.error('Error initializing table:', error);
                    this.showError('Failed to initialize table');
                });
            }
        }

        /**
         * Cleanup
         */
        destroy() {
            if (this.refreshTimer) {
                clearInterval(this.refreshTimer);
                this.refreshTimer = null;
            }

            if (this.tabulatorInstance) {
                this.tabulatorInstance.destroy();
                this.tabulatorInstance = null;
            }

            super.destroy();
        }
    }

    FormView.Utils.setupField(({element, formContainer}) => {
        return new DataTable({element, formContainer})
    }, DataTable.selectors.self);

})();

