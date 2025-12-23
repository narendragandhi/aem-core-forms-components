# Data Table Component - Usage Examples

This document provides comprehensive usage examples for the Data Table component.

## Table of Contents

1. [Basic Usage](#basic-usage)
2. [Inline JSON Data Source](#inline-json-data-source)
3. [URL-based Data Source](#url-based-data-source)
4. [Column Configuration](#column-configuration)
5. [Multiple Views](#multiple-views)
6. [Advanced Configuration](#advanced-configuration)
7. [Integration with Forms](#integration-with-forms)
8. [JavaScript API Usage](#javascript-api-usage)

---

## Basic Usage

### Simple Table with Auto-detected Columns

**Configuration:**
- Data Source Type: Inline
- Data Source: JSON array
- Columns: Auto-detect

**Dialog Configuration:**
```
Name: userTable
Title: User Table
Data Source Type: inline
Data Source: 
[
  {"id": 1, "name": "John Doe", "email": "john@example.com", "age": 30},
  {"id": 2, "name": "Jane Smith", "email": "jane@example.com", "age": 25},
  {"id": 3, "name": "Bob Johnson", "email": "bob@example.com", "age": 35}
]
```

**Result:**
- Table automatically detects columns: id, name, email, age
- Default pagination (10 rows per page)
- Sorting enabled
- Filtering enabled

---

## Inline JSON Data Source

### Example 1: Simple Employee List

**Data Source:**
```json
[
  {"employeeId": "E001", "firstName": "Alice", "lastName": "Williams", "department": "Engineering", "salary": 95000},
  {"employeeId": "E002", "firstName": "Bob", "lastName": "Miller", "department": "Marketing", "salary": 75000},
  {"employeeId": "E003", "firstName": "Charlie", "lastName": "Brown", "department": "Sales", "salary": 70000},
  {"employeeId": "E004", "firstName": "Diana", "lastName": "Davis", "department": "Engineering", "salary": 100000}
]
```

**Configuration:**
- Height: 400px
- Pagination: Enabled
- Page Size: 5
- Sortable: Enabled
- Filterable: Enabled

### Example 2: Product Catalog

**Data Source:**
```json
[
  {"productId": "P101", "name": "Laptop", "category": "Electronics", "price": 999.99, "stock": 50},
  {"productId": "P102", "name": "Mouse", "category": "Electronics", "price": 29.99, "stock": 200},
  {"productId": "P103", "name": "Keyboard", "category": "Electronics", "price": 79.99, "stock": 150},
  {"productId": "P201", "name": "Desk Chair", "category": "Furniture", "price": 299.99, "stock": 30}
]
```

---

## URL-based Data Source

### Example: Loading Data from API

**Configuration:**
```
Name: apiDataTable
Title: API Data Table
Data Source Type: url
Data Source: https://api.example.com/users
Refresh Interval: 60 (seconds)
```

**API Response Format:**
The API should return JSON in the following format:
```json
[
  {"id": 1, "name": "User 1", "status": "active"},
  {"id": 2, "name": "User 2", "status": "inactive"}
]
```

**Features:**
- Auto-refreshes every 60 seconds
- Handles network errors gracefully
- Shows loading state during refresh

### Example: Loading from AEM Servlet

**Configuration:**
```
Data Source Type: url
Data Source: /bin/forms/datatable/data
Refresh Interval: 30
```

**Servlet Implementation:**
```java
@SlingServlet(
    paths = "/bin/forms/datatable/data",
    methods = "GET"
)
public class DataTableServlet extends SlingAllMethodsServlet {
    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) {
        // Fetch data from repository or external service
        List<Map<String, Object>> data = fetchData();
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getWriter(), data);
    }
}
```

---

## Column Configuration

### Example 1: Custom Column Definitions

**Column Configuration JSON:**
```json
[
  {
    "field": "id",
    "title": "ID",
    "width": 100,
    "formatter": "number",
    "align": "right"
  },
  {
    "field": "name",
    "title": "Full Name",
    "width": 200,
    "formatter": "plaintext"
  },
  {
    "field": "email",
    "title": "Email Address",
    "width": 250,
    "formatter": "link",
    "formatterParams": {
      "urlField": "email",
      "urlPrefix": "mailto:"
    }
  },
  {
    "field": "age",
    "title": "Age",
    "width": 80,
    "formatter": "number",
    "align": "center"
  },
  {
    "field": "status",
    "title": "Status",
    "width": 120,
    "formatter": "tickCross"
  }
]
```

### Example 2: Formatted Columns

**Column Configuration:**
```json
[
  {
    "field": "date",
    "title": "Date",
    "formatter": "datetime",
    "formatterParams": {
      "inputFormat": "yyyy-MM-dd",
      "outputFormat": "MMM dd, yyyy"
    }
  },
  {
    "field": "amount",
    "title": "Amount",
    "formatter": "money",
    "formatterParams": {
      "symbol": "$",
      "precision": 2
    },
    "align": "right"
  },
  {
    "field": "percentage",
    "title": "Progress",
    "formatter": "progress",
    "formatterParams": {
      "min": 0,
      "max": 100
    }
  }
]
```

### Example 3: Custom Cell Renderers

**Column Configuration:**
```json
[
  {
    "field": "avatar",
    "title": "Avatar",
    "formatter": "image",
    "formatterParams": {
      "height": "40px",
      "width": "40px"
    }
  },
  {
    "field": "actions",
    "title": "Actions",
    "formatter": "html",
    "formatterParams": {
      "html": "<button onclick='editRow({id})'>Edit</button>"
    }
  }
]
```

---

## Multiple Views

### Example: Different Views for Different User Roles

**View Configuration:**
```json
{
  "default": {
    "columns": ["id", "name", "email"]
  },
  "detailed": {
    "columns": ["id", "name", "email", "phone", "address", "department"]
  },
  "minimal": {
    "columns": ["name"]
  },
  "admin": {
    "columns": ["id", "name", "email", "phone", "address", "department", "salary", "status"]
  }
}
```

**Usage:**
```javascript
// Switch to detailed view
const component = document.querySelector('[data-cmp-is="adaptiveFormDataTable"]');
const instance = FormView.Utils.getFieldInstance(component);
instance.applyView('detailed');

// Switch to minimal view
instance.applyView('minimal');
```

### Example: View Based on Screen Size

**View Configuration:**
```json
{
  "desktop": {
    "columns": ["id", "name", "email", "phone", "department", "status"]
  },
  "tablet": {
    "columns": ["name", "email", "department"]
  },
  "mobile": {
    "columns": ["name", "status"]
  }
}
```

**JavaScript Implementation:**
```javascript
function applyResponsiveView() {
    const width = window.innerWidth;
    const component = document.querySelector('[data-cmp-is="adaptiveFormDataTable"]');
    const instance = FormView.Utils.getFieldInstance(component);
    
    if (width > 1024) {
        instance.applyView('desktop');
    } else if (width > 768) {
        instance.applyView('tablet');
    } else {
        instance.applyView('mobile');
    }
}

window.addEventListener('resize', applyResponsiveView);
applyResponsiveView(); // Initial call
```

---

## Advanced Configuration

### Example 1: Selectable Rows with Form Submission

**Configuration:**
```
Selectable Rows: Enabled
```

**JavaScript to Get Selected Rows:**
```javascript
const component = document.querySelector('[data-cmp-is="adaptiveFormDataTable"]');
const instance = FormView.Utils.getFieldInstance(component);
const selectedRows = instance.tabulatorInstance.getSelectedData();

// Selected rows are automatically included in form submission
console.log('Selected rows:', selectedRows);
```

### Example 2: Inline Editing

**Column Configuration:**
```json
[
  {
    "field": "name",
    "title": "Name",
    "editor": "input",
    "editable": true
  },
  {
    "field": "email",
    "title": "Email",
    "editor": "input",
    "editable": true,
    "validator": "email"
  },
  {
    "field": "age",
    "title": "Age",
    "editor": "number",
    "editable": true,
    "validator": "min:0,max:120"
  }
]
```

**Event Handling:**
```javascript
// The component automatically handles cellEdited events
// Edited data is updated in the form model
```

### Example 3: Custom Filtering

**Configuration:**
```
Filterable: Enabled
```

**JavaScript Custom Filter:**
```javascript
const component = document.querySelector('[data-cmp-is="adaptiveFormDataTable"]');
const instance = FormView.Utils.getFieldInstance(component);

// Apply custom filter
instance.tabulatorInstance.setFilter([
    {field: "status", type: "=", value: "active"},
    {field: "age", type: ">", value: 25}
]);

// Clear filter
instance.tabulatorInstance.clearFilter();
```

---

## Integration with Forms

### Example 1: Form Submission with Selected Rows

**Form Configuration:**
```
Form Container
  └── Data Table (name: selectedUsers)
  └── Submit Button
```

**Submission Handler:**
```javascript
// Selected rows are automatically included in form data
// Form submission includes:
{
  "selectedUsers": [
    {"id": 1, "name": "John"},
    {"id": 2, "name": "Jane"}
  ]
}
```

### Example 2: Dynamic Data Based on Form Field

**Use Case:** Load table data based on dropdown selection

**Form Structure:**
```
Form Container
  └── Dropdown (name: category)
  └── Data Table (name: products)
```

**JavaScript:**
```javascript
const categoryField = document.querySelector('[data-cmp-is="adaptiveFormDropdown"]');
const dataTable = document.querySelector('[data-cmp-is="adaptiveFormDataTable"]');
const dataTableInstance = FormView.Utils.getFieldInstance(dataTable);

categoryField.addEventListener('change', (e) => {
    const category = e.target.value;
    const newDataSource = `/bin/forms/products?category=${category}`;
    
    // Update data source and refresh
    dataTable.dataset.cmpDatasource = newDataSource;
    dataTableInstance.refreshData();
});
```

### Example 3: Pre-populate Table from Form Data

**Use Case:** Show form submission history

**Configuration:**
```
Data Source Type: url
Data Source: /bin/forms/submissions?userId={userId}
```

**Dynamic URL:**
```javascript
// Get user ID from form context or user session
const userId = getCurrentUserId();
const dataTable = document.querySelector('[data-cmp-is="adaptiveFormDataTable"]');
dataTable.dataset.cmpDatasource = `/bin/forms/submissions?userId=${userId}`;
```

---

## JavaScript API Usage

### Example 1: Programmatic View Switching

```javascript
// Get component instance
const component = document.querySelector('[data-cmp-is="adaptiveFormDataTable"]');
const instance = FormView.Utils.getFieldInstance(component);

// Switch views
instance.applyView('detailed');

// Get current view
console.log('Current view:', instance.currentView);
```

### Example 2: Data Manipulation

```javascript
const component = document.querySelector('[data-cmp-is="adaptiveFormDataTable"]');
const instance = FormView.Utils.getFieldInstance(component);

// Add row
instance.tabulatorInstance.addRow({
    id: 5,
    name: "New User",
    email: "new@example.com"
});

// Update row
instance.tabulatorInstance.updateData([{
    id: 1,
    name: "Updated Name"
}]);

// Delete row
instance.tabulatorInstance.deleteRow(1);

// Get all data
const allData = instance.tabulatorInstance.getData();
console.log('All data:', allData);
```

### Example 3: Export Data

```javascript
const component = document.querySelector('[data-cmp-is="adaptiveFormDataTable"]');
const instance = FormView.Utils.getFieldInstance(component);

// Export to CSV
instance.tabulatorInstance.download("csv", "data.csv");

// Export to JSON
const jsonData = JSON.stringify(instance.tabulatorInstance.getData());
const blob = new Blob([jsonData], { type: 'application/json' });
const url = URL.createObjectURL(blob);
const a = document.createElement('a');
a.href = url;
a.download = 'data.json';
a.click();
```

### Example 4: Event Handling

```javascript
const component = document.querySelector('[data-cmp-is="adaptiveFormDataTable"]');
const instance = FormView.Utils.getFieldInstance(component);

// Listen to row selection
instance.tabulatorInstance.on("rowSelected", (row) => {
    console.log('Row selected:', row.getData());
});

// Listen to cell edit
instance.tabulatorInstance.on("cellEdited", (cell) => {
    console.log('Cell edited:', cell.getField(), cell.getValue());
});

// Listen to data loaded
instance.tabulatorInstance.on("dataLoaded", () => {
    console.log('Data loaded');
});
```

---

## Complete Example: Employee Management System

### Form Structure
```
Form Container
  ├── Text Input (Employee Search)
  ├── Data Table (Employee List)
  │   ├── Columns: ID, Name, Department, Salary, Status
  │   ├── Views: default, detailed, admin
  │   ├── Selectable Rows: Enabled
  │   └── Filterable: Enabled
  ├── Button (Add Employee)
  ├── Button (Edit Selected)
  └── Submit Button
```

### Configuration

**Data Table:**
```
Name: employeeTable
Title: Employees
Data Source Type: url
Data Source: /bin/forms/employees
Height: 500px
Pagination: Enabled
Page Size: 20
Sortable: Enabled
Filterable: Enabled
Selectable Rows: Enabled
```

**Column Configuration:**
```json
[
  {"field": "id", "title": "ID", "width": 80},
  {"field": "name", "title": "Name", "width": 200},
  {"field": "department", "title": "Department", "width": 150},
  {"field": "salary", "title": "Salary", "width": 120, "formatter": "money"},
  {"field": "status", "title": "Status", "width": 100}
]
```

**View Configuration:**
```json
{
  "default": {
    "columns": ["id", "name", "department", "status"]
  },
  "detailed": {
    "columns": ["id", "name", "department", "salary", "status"]
  },
  "admin": {
    "columns": ["id", "name", "department", "salary", "status", "actions"]
  }
}
```

### JavaScript Implementation

```javascript
// Search functionality
const searchInput = document.querySelector('[name="employeeSearch"]');
const dataTable = document.querySelector('[data-cmp-is="adaptiveFormDataTable"]');
const instance = FormView.Utils.getFieldInstance(dataTable);

searchInput.addEventListener('input', (e) => {
    const searchTerm = e.target.value.toLowerCase();
    instance.tabulatorInstance.setFilter([
        {field: "name", type: "like", value: searchTerm}
    ]);
});

// Add employee button
document.querySelector('[name="addEmployee"]').addEventListener('click', () => {
    // Open add employee dialog
    openAddEmployeeDialog();
});

// Edit selected button
document.querySelector('[name="editSelected"]').addEventListener('click', () => {
    const selectedRows = instance.tabulatorInstance.getSelectedData();
    if (selectedRows.length > 0) {
        openEditDialog(selectedRows[0]);
    }
});

// Form submission
document.querySelector('form').addEventListener('submit', (e) => {
    const selectedEmployees = instance.tabulatorInstance.getSelectedData();
    // Selected employees are automatically included in form data
});
```

---

## Best Practices

1. **Performance:**
   - Use pagination for large datasets (> 100 rows)
   - Consider server-side pagination for very large datasets
   - Limit auto-refresh intervals to reasonable values

2. **Data Format:**
   - Ensure JSON data is properly formatted
   - Use consistent field names across rows
   - Handle null/undefined values gracefully

3. **User Experience:**
   - Provide clear column headers
   - Use appropriate column widths
   - Enable filtering and sorting for better UX
   - Consider responsive views for mobile devices

4. **Security:**
   - Validate data source URLs
   - Sanitize user input in filters
   - Use CSRF tokens for URL-based data sources

5. **Accessibility:**
   - Provide meaningful labels
   - Ensure keyboard navigation works
   - Test with screen readers

---

## Troubleshooting

### Table Not Rendering
- Check if Tabulator.js is loaded
- Verify JSON data format
- Check browser console for errors

### Data Not Loading from URL
- Verify URL is accessible
- Check CORS settings
- Verify response format is JSON array

### Views Not Switching
- Verify view configuration JSON format
- Check column names match data fields
- Ensure view is defined in viewConfig

### Selected Rows Not in Form Submission
- Verify selectableRows is enabled
- Check form container integration
- Verify JavaScript initialization

---

For more information, refer to the [Component README](ui.af.apps/src/main/content/jcr_root/apps/core/fd/components/form/datatable/v1/datatable/README.md) and [Tabulator Documentation](https://tabulator.info/docs/5.5).

