# Data Table Component Specification

**Component Name**: Data Table  
**Technical Name**: datatable  
**Version**: v1  
**Component Type**: [x] Field Component | [ ] Container Component | [ ] Button Component

---

## Overview

### Purpose
The Data Table component provides a powerful, configurable data table widget for Adaptive Forms. It uses the Tabulator.js library to render tabular data from JSON input with support for multiple configurable views, sorting, filtering, pagination, and interactive features.

### Use Cases
- Display form submission data in a table format
- Show searchable/filterable data sets
- Present data with multiple view configurations
- Display data with sorting and pagination capabilities
- Show data with column customization

### Target Users
- Form authors configuring data display
- End users viewing tabular data in forms
- Administrators managing form data views

---

## Technical Specifications

### Base Class
- [x] `AbstractFieldImpl` (for field components)
- [ ] `AbstractContainerImpl` (for container components)
- [ ] `AbstractBaseImpl` (for base components)

### Resource Type
```
/apps/core/fd/components/form/datatable/v1/datatable
```

### Field Type
```
data-table
```

### Sling Model
- **Interface**: `com.adobe.cq.forms.core.components.models.form.DataTable`
- **Implementation**: `com.adobe.cq.forms.core.components.internal.models.v1.form.DataTableImpl`

### External Library Dependency
- **Tabulator.js**: Version 5.x or later
- **CDN or Local**: Configurable via component properties

---

## Properties

### Required Properties

| Property | JCR Property | Type | Description | Validation Rules |
|----------|-------------|------|-------------|------------------|
| name | `./name` | String | Field name for form submission | Required, unique within form |
| dataSource | `./dataSource` | String | JSON data source (URL or inline JSON) | Required, valid JSON or URL |

### Optional Properties

| Property | JCR Property | Type | Description | Default Value |
|----------|-------------|------|-------------|---------------|
| label | `./jcr:title` | String | Field label displayed to users | Empty |
| description | `./description` | String | Help text or description | Empty |
| visible | `./visible` | Boolean | Whether field is visible | true |
| enabled | `./enabled` | Boolean | Whether field is enabled | true |
| height | `./height` | String | Table height (CSS value) | "300px" |
| pagination | `./pagination` | Boolean | Enable pagination | true |
| pageSize | `./pageSize` | Integer | Number of rows per page | 10 |
| sortable | `./sortable` | Boolean | Enable column sorting | true |
| filterable | `./filterable` | Boolean | Enable filtering | true |
| resizableColumns | `./resizableColumns` | Boolean | Enable column resizing | true |
| selectableRows | `./selectableRows` | Boolean | Enable row selection | false |
| columns | `./columns` | String | Column configuration (JSON) | Auto-detect from data |
| viewConfig | `./viewConfig` | String | View configuration (JSON) | Default view |
| dataSourceType | `./dataSourceType` | String | "url" or "inline" | "inline" |
| refreshInterval | `./refreshInterval` | Integer | Auto-refresh interval in seconds | 0 (disabled) |

---

## Constraints

### Supported Constraint Types
- [x] String Constraints (for JSON validation)
- [ ] Number Constraints
- [ ] Date Constraints
- [ ] File Constraints

### Validation Rules

1. **JSON Data Validation**
   - **Condition**: When dataSourceType is "inline"
   - **Validation**: Valid JSON format
   - **Error Message**: "Invalid JSON data format"

2. **URL Validation**
   - **Condition**: When dataSourceType is "url"
   - **Validation**: Valid URL format
   - **Error Message**: "Invalid data source URL"

3. **Columns Configuration**
   - **Condition**: When columns property is provided
   - **Validation**: Valid JSON array format
   - **Error Message**: "Invalid column configuration"

---

## Dialog Configuration

### Edit Dialog

- [x] Requires custom dialog
- **Tabs**: Basic, Data Source, Columns, Views, Advanced

#### Dialog Fields

**Basic Tab:**
- Label (textfield)
- Name (textfield, required)
- Description (textarea)
- Visible (checkbox)
- Enabled (checkbox)

**Data Source Tab:**
- Data Source Type (select: "inline" or "url")
- Data Source (textarea for inline JSON, textfield for URL)
- Refresh Interval (numberfield)

**Columns Tab:**
- Column Configuration (textarea, JSON format)
- Auto-detect Columns (checkbox)

**Views Tab:**
- View Configuration (textarea, JSON format)
- Default View (textfield)

**Advanced Tab:**
- Height (textfield)
- Pagination (checkbox)
- Page Size (numberfield)
- Sortable (checkbox)
- Filterable (checkbox)
- Resizable Columns (checkbox)
- Selectable Rows (checkbox)

---

## Client Libraries

### Runtime Client Library

- **Category**: `core.forms.components.datatable.v1.runtime`
- **Dependencies**: 
  - `core.forms.components.runtime.base`
  - Tabulator.js (external library)
- **Files**:
  - JavaScript: `clientlibs/site/js/datatableview.js`
  - CSS: `clientlibs/site/css/datatableview.css`

### Editor Client Library

- **Category**: `core.forms.components.datatable.v1.editor`
- **Dependencies**: 
  - `core.forms.components.base.v1.editor`
- **Files**:
  - JavaScript: `clientlibs/editor/js/editDialog.js`

---

## JavaScript Runtime

### Base Class
- [x] Extends `FormView.FormFieldBase`

### Component Identifier
```javascript
static IS = "adaptiveFormDataTable";
```

### Tabulator Integration

The component will initialize Tabulator with:
- Data from JSON source
- Column configuration
- View configuration
- Event handlers for form integration

### Event Handlers

| Event | Handler Method | Purpose |
|-------|----------------|---------|
| rowClick | `onRowClick()` | Handle row selection |
| cellEdited | `onCellEdited()` | Handle cell editing |
| dataLoaded | `onDataLoaded()` | Update form model when data loads |
| rowSelected | `onRowSelected()` | Handle row selection for form submission |

---

## HTML Structure

### BEM Structure

```
BLOCK: cmp-adaptiveform-datatable
    ELEMENT: cmp-adaptiveform-datatable__label
    ELEMENT: cmp-adaptiveform-datatable__label-container
    ELEMENT: cmp-adaptiveform-datatable__container
    ELEMENT: cmp-adaptiveform-datatable__table
    ELEMENT: cmp-adaptiveform-datatable__questionmark
    ELEMENT: cmp-adaptiveform-datatable__shortdescription
    ELEMENT: cmp-adaptiveform-datatable__longdescription
    ELEMENT: cmp-adaptiveform-datatable__errormessage
    MODIFIER: cmp-adaptiveform-datatable--loading
    MODIFIER: cmp-adaptiveform-datatable--error
```

### Required Data Attributes

```html
data-cmp-is="adaptiveFormDataTable"
data-cmp-adaptiveformcontainer-path="${formstructparser.formContainerPath}"
data-cmp-visible="${component.visible ? 'true' : 'false'}"
data-cmp-enabled="${component.enabled ? 'true' : 'false'}"
data-cmp-datasource="${component.dataSource}"
data-cmp-datasourcetype="${component.dataSourceType}"
data-cmp-columns="${component.columns}"
data-cmp-viewconfig="${component.viewConfig}"
```

---

## Dependencies

### Backend Dependencies
- Standard AEM Forms Core Components dependencies
- JSON parsing utilities

### Frontend Dependencies
- **Tabulator.js**: Version 5.x or later
  - Can be loaded from CDN or bundled
  - CDN: `https://unpkg.com/tabulator-tables@5.x/dist/js/tabulator.min.js`
  - CSS: `https://unpkg.com/tabulator-tables@5.x/dist/css/tabulator.min.css`

### AEM Dependencies
- AEM Forms Core Components version: 3.0.135+
- AEM Sites Core Components version: 2.24.6+
- AEM Version: Cloud Service or 6.5+

---

## Accessibility

### WCAG Compliance
- [x] WCAG 2.0 Level A
- [x] WCAG 2.0 Level AA

### Accessibility Features
- [x] Keyboard navigation support
- [x] Screen reader support
- [x] ARIA labels and roles
- [x] Focus management
- [x] High contrast mode support

### ARIA Attributes

| Element | ARIA Attribute | Value | Purpose |
|---------|----------------|-------|---------|
| table | `role` | `table` | Identify as data table |
| table | `aria-label` | Component label | Describe table purpose |
| table | `aria-describedby` | Description ID | Link to description |

---

## Internationalization

### i18n Requirements
- [x] All user-facing text is internationalized
- [x] Error messages are internationalized
- [x] Column headers support i18n
- [x] Pagination controls are internationalized

### Locale-Specific Formatting
- [x] Date formatting (via Tabulator formatters)
- [x] Number formatting (via Tabulator formatters)
- [x] Currency formatting (via Tabulator formatters)

---

## Performance Considerations

### Optimization Strategies
- [x] Lazy loading of data
- [x] Virtual DOM rendering (Tabulator feature)
- [x] Pagination to limit rendered rows
- [x] Debounced filtering and sorting

### Caching
- [x] Data caching for URL sources
- [x] Client libraries are cached
- [x] Component output is cacheable

---

## Security Considerations

### Security Features
- [x] XSS protection (HTL context-aware escaping)
- [x] CSRF protection for data source URLs
- [x] Input validation (server-side)
- [x] Input validation (client-side)
- [x] JSON sanitization

### Security Requirements
- Validate JSON data before rendering
- Sanitize URLs for data sources
- Escape user-provided column configurations

---

## Testing Requirements

### Unit Tests (Java)
- [x] Sling Model interface tests
- [x] Sling Model implementation tests
- [x] JSON parsing tests
- [x] Validation logic tests

### Integration Tests
- [x] Component rendering tests
- [x] Dialog functionality tests
- [x] Data loading tests (inline and URL)
- [x] View switching tests

### UI Tests (Cypress)
- [x] Component rendering tests
- [x] Data loading tests
- [x] Sorting tests
- [x] Filtering tests
- [x] Pagination tests
- [x] Row selection tests
- [x] Accessibility tests

---

## Examples

### Basic Usage Example

**HTL Template:**
```html
<div class="cmp-adaptiveform-datatable"
     data-cmp-is="adaptiveFormDataTable"
     data-cmp-datasource='[{"id":1,"name":"John","age":30}]'
     data-cmp-datasourcetype="inline">
</div>
```

### Column Configuration Example

```json
[
  {"field": "id", "title": "ID", "width": 100},
  {"field": "name", "title": "Name", "width": 200},
  {"field": "age", "title": "Age", "width": 100, "formatter": "number"}
]
```

### View Configuration Example

```json
{
  "default": {
    "columns": ["id", "name", "age"]
  },
  "detailed": {
    "columns": ["id", "name", "age", "email", "phone"]
  }
}
```

---

## Known Issues and Limitations

### Known Issues
- None currently identified

### Limitations
- Maximum data size: Limited by browser memory (recommended: < 10,000 rows)
- Complex nested JSON: Flattening may be required
- Real-time updates: Requires refresh interval configuration

### Future Enhancements
- Export to CSV/Excel
- Inline editing support
- Custom cell renderers
- Advanced filtering UI
- Column grouping

---

## Related Components

### Similar Components
- None (this is a new component type)

### Complementary Components
- Form Container: Data table can be used within form containers
- Button: Can trigger data refresh or export actions

---

## Implementation Notes

1. **Tabulator Integration**: Use Tabulator's initialization API to create the table instance
2. **Data Loading**: Support both inline JSON and URL-based data sources
3. **View Management**: Implement view switching using Tabulator's column visibility API
4. **Form Integration**: Ensure selected rows/data integrate with form submission
5. **Error Handling**: Graceful error handling for invalid data or network failures

