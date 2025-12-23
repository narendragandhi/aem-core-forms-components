<!--
Copyright 2022 Adobe

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
-->
Adaptive Form Data Table (v1)
====
Adaptive Form Data Table component written in HTL that uses Tabulator.js library to render interactive data tables.

## Features

* Renders tabular data from JSON input
* Supports multiple configurable views
* Column configuration (auto-detect or manual)
* Pagination support
* Sorting and filtering
* Resizable columns
* Selectable rows
* Auto-refresh for URL-based data sources
* Responsive design

### Use Object
The Form Data Table component uses the `com.adobe.cq.forms.core.components.models.form.DataTable` Sling Model for its Use-object.

### Edit Dialog Properties

The following properties are written to JCR for this Form Data Table component:

#### Basic Tab
1. `./name` - defines the name of the field, which will be submitted with the form data (required)
2. `./jcr:title` - defines the label to use for this field
3. `./description` - defines a help message that can be rendered in the field as a hint for the user
4. `./visible` - if set to `true`, the field will be visible
5. `./enabled` - if set to `true`, the field will be enabled

#### Data Source Tab
6. `./dataSourceType` - defines the data source type: "inline" (JSON string) or "url" (URL to JSON endpoint)
7. `./dataSource` - defines the data source (JSON string for inline, URL for url type) (required)
8. `./refreshInterval` - defines the auto-refresh interval in seconds (0 = disabled)

#### Columns Tab
9. `./columns` - defines the column configuration as JSON array (optional, auto-detects if not provided)

#### Views Tab
10. `./viewConfig` - defines the view configuration as JSON object (optional)

#### Advanced Tab
11. `./height` - defines the table height (CSS value, e.g., "300px", "50vh")
12. `./pagination` - if set to `true`, enables pagination
13. `./pageSize` - defines the number of rows per page
14. `./sortable` - if set to `true`, enables column sorting
15. `./filterable` - if set to `true`, enables filtering
16. `./resizableColumns` - if set to `true`, enables column resizing
17. `./selectableRows` - if set to `true`, enables row selection

## Data Source Configuration

### Inline JSON
Set `dataSourceType` to "inline" and provide JSON data in the `dataSource` field:

```json
[
  {"id": 1, "name": "John Doe", "age": 30, "email": "john@example.com"},
  {"id": 2, "name": "Jane Smith", "age": 25, "email": "jane@example.com"}
]
```

### URL-based Data Source
Set `dataSourceType` to "url" and provide a URL to a JSON endpoint:

```
https://api.example.com/data
```

The endpoint should return JSON data in the same format as inline JSON.

## Column Configuration

Column configuration is optional. If not provided, columns are auto-detected from the data. To configure columns manually, provide a JSON array:

```json
[
  {"field": "id", "title": "ID", "width": 100, "formatter": "number"},
  {"field": "name", "title": "Name", "width": 200},
  {"field": "age", "title": "Age", "width": 100, "formatter": "number"},
  {"field": "email", "title": "Email", "width": 250, "formatter": "link"}
]
```

For more column options, refer to [Tabulator Column Documentation](https://tabulator.info/docs/5.5/columns).

## View Configuration

View configuration allows you to define multiple views with different column visibility:

```json
{
  "default": {
    "columns": ["id", "name", "age"]
  },
  "detailed": {
    "columns": ["id", "name", "age", "email", "phone"]
  },
  "minimal": {
    "columns": ["name"]
  }
}
```

Views can be switched programmatically using the JavaScript API.

## Client Libraries

The component provides a `core.forms.components.datatable.v1.runtime` client library category that contains the Javascript runtime for the component. 
It should be added to a relevant site client library using the `embed` property.

**Important**: The component requires Tabulator.js library to be loaded. Include it in your page:

```html
<link href="https://unpkg.com/tabulator-tables@5.5.2/dist/css/tabulator.min.css" rel="stylesheet">
<script type="text/javascript" src="https://unpkg.com/tabulator-tables@5.5.2/dist/js/tabulator.min.js"></script>
```

It also provides a `core.forms.components.datatable.v1.editor` editor client library category that includes
JavaScript handling for dialog interaction. It is already included by its edit dialog.

## BEM Description
```
BLOCK cmp-adaptiveform-datatable
    ELEMENT cmp-adaptiveform-datatable__label
    ELEMENT cmp-adaptiveform-datatable__label-container
    ELEMENT cmp-adaptiveform-datatable__container
    ELEMENT cmp-adaptiveform-datatable__table
    ELEMENT cmp-adaptiveform-datatable__questionmark
    ELEMENT cmp-adaptiveform-datatable__shortdescription
    ELEMENT cmp-adaptiveform-datatable__longdescription
    ELEMENT cmp-adaptiveform-datatable__errormessage
    MODIFIER cmp-adaptiveform-datatable--loading
    MODIFIER cmp-adaptiveform-datatable--error
```

## JavaScript Data Attribute Bindings

The following attributes must be added for the initialization of the data-table component in the form view:  
1. `data-cmp-is="adaptiveFormDataTable"`
2. `data-cmp-adaptiveformcontainer-path="${formstructparser.formContainerPath}"`
3. `data-cmp-datasource` - JSON data or URL
4. `data-cmp-datasourcetype` - "inline" or "url"
5. `data-cmp-columns` - Column configuration JSON (optional)
6. `data-cmp-viewconfig` - View configuration JSON (optional)
7. `data-cmp-height` - Table height
8. `data-cmp-pagination` - Enable pagination (true/false)
9. `data-cmp-pagesize` - Page size for pagination
10. `data-cmp-sortable` - Enable sorting (true/false)
11. `data-cmp-filterable` - Enable filtering (true/false)
12. `data-cmp-resizablecolumns` - Enable column resizing (true/false)
13. `data-cmp-selectablerows` - Enable row selection (true/false)
14. `data-cmp-refreshinterval` - Auto-refresh interval in seconds

## JavaScript API

The component exposes a JavaScript API for programmatic control:

```javascript
// Get the component instance
const component = element.querySelector('[data-cmp-is="adaptiveFormDataTable"]');
const instance = FormView.Utils.getFieldInstance(component);

// Apply a view
instance.applyView('detailed');

// Refresh data
instance.refreshData();

// Get selected rows
const selectedRows = instance.tabulatorInstance.getSelectedData();

// Get all data
const allData = instance.tabulatorInstance.getData();
```

## Examples

### Basic Usage

```html
<div class="cmp-adaptiveform-datatable"
     data-cmp-is="adaptiveFormDataTable"
     data-cmp-datasource='[{"id":1,"name":"John","age":30}]'
     data-cmp-datasourcetype="inline">
</div>
```

### With Column Configuration

```html
<div class="cmp-adaptiveform-datatable"
     data-cmp-is="adaptiveFormDataTable"
     data-cmp-datasource='[{"id":1,"name":"John","age":30}]'
     data-cmp-datasourcetype="inline"
     data-cmp-columns='[{"field":"id","title":"ID"},{"field":"name","title":"Name"}]'>
</div>
```

### With URL Data Source and Auto-refresh

```html
<div class="cmp-adaptiveform-datatable"
     data-cmp-is="adaptiveFormDataTable"
     data-cmp-datasource="https://api.example.com/data"
     data-cmp-datasourcetype="url"
     data-cmp-refreshinterval="60">
</div>
```

## Dependencies

- Tabulator.js 5.x or later
- AEM Forms Core Components Runtime Base
- Form Container component (parent)

## Browser Support

- Chrome (latest)
- Firefox (latest)
- Safari (latest)
- Edge (latest)

## Limitations

- Maximum recommended data size: 10,000 rows (for optimal performance)
- Complex nested JSON structures may require flattening
- Real-time updates require refresh interval configuration

## Future Enhancements

- Export to CSV/Excel
- Inline cell editing
- Custom cell renderers
- Advanced filtering UI
- Column grouping
- Server-side pagination support

