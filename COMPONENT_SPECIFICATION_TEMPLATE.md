# Component Specification Template

Use this template when creating specifications for new AEM Forms Core Components.

---

## Component Name

**Component Name**: [Component Display Name]  
**Technical Name**: [component-name]  
**Version**: v1  
**Component Type**: [ ] Field Component | [ ] Container Component | [ ] Button Component | [ ] Other: ___________

---

## Overview

### Purpose
[Brief description of what the component does and why it's needed]

### Use Cases
- [Use case 1]
- [Use case 2]
- [Use case 3]

### Target Users
- [Who will use this component?]

---

## Technical Specifications

### Base Class
- [ ] `AbstractFieldImpl` (for field components)
- [ ] `AbstractContainerImpl` (for container components)
- [ ] `AbstractBaseImpl` (for base components)
- [ ] Other: ___________

### Resource Type
```
/apps/core/fd/components/form/[component-name]/v1/[component-name]
```

### Field Type
```
[field-type-value]
```

### Sling Model
- **Interface**: `com.adobe.cq.forms.core.components.models.form.[ComponentName]`
- **Implementation**: `com.adobe.cq.forms.core.components.internal.models.v1.form.[ComponentName]Impl`

---

## Properties

### Required Properties

| Property | JCR Property | Type | Description | Validation Rules |
|----------|-------------|------|-------------|------------------|
| name | `./name` | String | Field name for form submission | Required, unique within form |
| [prop2] | `./prop2` | [Type] | [Description] | [Rules] |

### Optional Properties

| Property | JCR Property | Type | Description | Default Value |
|----------|-------------|------|-------------|---------------|
| label | `./jcr:title` | String | Field label displayed to users | Empty |
| description | `./description` | String | Help text or description | Empty |
| required | `./required` | Boolean | Whether field is required | false |
| readOnly | `./readOnly` | Boolean | Whether field is read-only | false |
| visible | `./visible` | Boolean | Whether field is visible | true |
| enabled | `./enabled` | Boolean | Whether field is enabled | true |
| default | `./default` | Object[] | Default value(s) | null |
| placeholder | `./placeholder` | String | Placeholder text | Empty |
| [custom-prop] | `./customProp` | [Type] | [Description] | [Default] |

---

## Constraints

### Supported Constraint Types
- [ ] String Constraints (minLength, maxLength, pattern)
- [ ] Number Constraints (minimum, maximum, exclusiveMinimum, exclusiveMaximum)
- [ ] Date Constraints (minimumDate, maximumDate)
- [ ] File Constraints (fileTypes, maxFileSize)
- [ ] Custom Constraints: ___________

### Validation Rules

1. **[Rule Name]**
   - **Condition**: [When this rule applies]
   - **Validation**: [What is validated]
   - **Error Message**: [Error message to display]

2. **[Rule Name]**
   - **Condition**: [When this rule applies]
   - **Validation**: [What is validated]
   - **Error Message**: [Error message to display]

---

## Dialog Configuration

### Edit Dialog

- [ ] Uses base component dialog (inherits common fields)
- [ ] Requires custom dialog
- [ ] Additional tabs needed: ___________

#### Custom Dialog Fields

| Field Label | Property Name | Type | Required | Description |
|-------------|---------------|------|----------|-------------|
| [Field 1] | `./prop1` | [Type] | [ ] Yes [ ] No | [Description] |
| [Field 2] | `./prop2` | [Type] | [ ] Yes [ ] No | [Description] |

### Design Dialog

- [ ] Required
- [ ] Not required
- **Purpose**: [If required, describe what can be configured]

---

## Client Libraries

### Runtime Client Library

- **Category**: `core.forms.components.[component-name].v1.runtime`
- **Dependencies**: 
  - `core.forms.components.runtime.base`
  - [Additional dependencies]
- **Files**:
  - JavaScript: `clientlibs/site/js/[component]view.js`
  - CSS: `clientlibs/site/css/[component]view.css`

### Editor Client Library

- **Category**: `core.forms.components.[component-name].v1.editor`
- **Dependencies**: 
  - `core.forms.components.base.v1.editor`
  - [Additional dependencies]
- **Files**:
  - JavaScript: `clientlibs/editor/js/editDialog.js`

---

## JavaScript Runtime

### Base Class
- [ ] Extends `FormView.FormFieldBase`
- [ ] Extends `FormView.FormContainer`
- [ ] Extends `FormView.FormButton`
- [ ] Custom implementation
- [ ] No JavaScript required

### Component Identifier
```javascript
static IS = "adaptiveForm[Component]";
```

### Selectors
```javascript
static selectors = {
    self: "[data-cmp-is='adaptiveForm[Component]']",
    widget: ".cmp-adaptiveform-[component]__widget",
    // Additional selectors
};
```

### Event Handlers

| Event | Handler Method | Purpose |
|-------|----------------|---------|
| change | `onChange()` | Handle value changes |
| focus | `onFocus()` | Handle focus events |
| blur | `onBlur()` | Handle blur events |
| [event] | `[method]()` | [Purpose] |

### Custom Methods

| Method | Purpose | Parameters | Returns |
|--------|---------|------------|---------|
| `[method1]()` | [Purpose] | [Params] | [Return] |
| `[method2]()` | [Purpose] | [Params] | [Return] |

---

## HTML Structure

### BEM Structure

```
BLOCK: cmp-adaptiveform-[component]
    ELEMENT: cmp-adaptiveform-[component]__label
    ELEMENT: cmp-adaptiveform-[component]__label-container
    ELEMENT: cmp-adaptiveform-[component]__widget
    ELEMENT: cmp-adaptiveform-[component]__questionmark
    ELEMENT: cmp-adaptiveform-[component]__shortdescription
    ELEMENT: cmp-adaptiveform-[component]__longdescription
    ELEMENT: cmp-adaptiveform-[component]__errormessage
    MODIFIER: cmp-adaptiveform-[component]--required
    MODIFIER: cmp-adaptiveform-[component]--disabled
```

### Required Data Attributes

```html
data-cmp-is="adaptiveForm[Component]"
data-cmp-adaptiveformcontainer-path="${formstructparser.formContainerPath}"
data-cmp-visible="${component.visible ? 'true' : 'false'}"
data-cmp-enabled="${component.enabled ? 'true' : 'false'}"
data-cmp-required="${component.required ? 'true' : 'false'}"
data-cmp-readonly="${component.readOnly ? 'true' : 'false'}"
```

### HTML Structure Example

```html
<div class="cmp-adaptiveform-[component]" 
     data-cmp-is="adaptiveForm[Component]"
     id="${component.id}">
    <div class="cmp-adaptiveform-[component]__label-container">
        <!-- Label -->
    </div>
    <div class="cmp-adaptiveform-[component]__widget">
        <!-- Widget markup -->
    </div>
    <div class="cmp-adaptiveform-[component]__errormessage">
        <!-- Error message -->
    </div>
</div>
```

---

## Dependencies

### Backend Dependencies
- [List any Java dependencies or OSGi services required]

### Frontend Dependencies
- [List any JavaScript libraries or frameworks required]

### AEM Dependencies
- AEM Forms Core Components version: ___________
- AEM Sites Core Components version: ___________
- AEM Version: ___________

---

## Accessibility

### WCAG Compliance
- [ ] WCAG 2.0 Level A
- [ ] WCAG 2.0 Level AA
- [ ] WCAG 2.0 Level AAA

### Accessibility Features
- [ ] Keyboard navigation support
- [ ] Screen reader support
- [ ] ARIA labels and roles
- [ ] Focus management
- [ ] High contrast mode support
- [ ] [Additional features]

### ARIA Attributes

| Element | ARIA Attribute | Value | Purpose |
|---------|----------------|-------|---------|
| [element] | `role` | [value] | [Purpose] |
| [element] | `aria-label` | [value] | [Purpose] |
| [element] | `aria-describedby` | [value] | [Purpose] |

---

## Internationalization

### i18n Requirements
- [ ] All user-facing text is internationalized
- [ ] Error messages are internationalized
- [ ] Placeholder text is internationalized
- [ ] Tooltip text is internationalized

### Locale-Specific Formatting
- [ ] Date formatting
- [ ] Number formatting
- [ ] Currency formatting
- [ ] [Other formatting]

### RTL Support
- [ ] Right-to-left language support
- [ ] Layout adjustments for RTL
- [ ] Text direction handling

---

## Performance Considerations

### Optimization Strategies
- [ ] Lazy loading of JavaScript
- [ ] Minimal dependencies
- [ ] Code splitting
- [ ] Tree shaking
- [ ] [Other optimizations]

### Caching
- [ ] Component output is cacheable
- [ ] Client libraries are cached
- [ ] Sling Models are cacheable

### Performance Metrics
- **Target Load Time**: ___________
- **Target Bundle Size**: ___________
- **Target Render Time**: ___________

---

## Security Considerations

### Security Features
- [ ] XSS protection (HTL context-aware escaping)
- [ ] CSRF protection
- [ ] Input validation (server-side)
- [ ] Input validation (client-side)
- [ ] Output sanitization
- [ ] [Additional security measures]

### Security Requirements
- [List any specific security requirements or considerations]

---

## Testing Requirements

### Unit Tests (Java)
- [ ] Sling Model interface tests
- [ ] Sling Model implementation tests
- [ ] Utility method tests
- [ ] Validation logic tests

### Integration Tests
- [ ] Component rendering tests
- [ ] Dialog functionality tests
- [ ] Form submission tests
- [ ] [Additional integration tests]

### UI Tests (Cypress)
- [ ] Component rendering tests
- [ ] User interaction tests
- [ ] Validation tests
- [ ] Accessibility tests
- [ ] [Additional UI tests]

### Test Coverage Target
- **Unit Tests**: _____%
- **Integration Tests**: _____%
- **UI Tests**: _____%

---

## Documentation Requirements

### Required Documentation
- [ ] README.md with component overview
- [ ] API documentation (JavaDoc)
- [ ] Usage examples
- [ ] Migration guide (if applicable)
- [ ] Known issues and limitations

### README.md Sections
- [ ] Overview and features
- [ ] Use object (Sling Model)
- [ ] Edit dialog properties
- [ ] Client libraries
- [ ] BEM description
- [ ] JavaScript data attribute bindings
- [ ] Replace feature (if applicable)

---

## Examples

### Basic Usage Example

```html
<!-- HTL template example -->
```

### JavaScript Usage Example

```javascript
// JavaScript initialization example
```

### Configuration Example

```xml
<!-- Dialog configuration example -->
```

---

## Migration Notes

### From Previous Version
- [ ] N/A (new component)
- [ ] Migration required from version: ___________

### Breaking Changes
- [List any breaking changes if migrating from previous version]

### Migration Steps
1. [Step 1]
2. [Step 2]
3. [Step 3]

---

## Known Issues and Limitations

### Known Issues
- [Issue 1]: [Description]
- [Issue 2]: [Description]

### Limitations
- [Limitation 1]: [Description]
- [Limitation 2]: [Description]

### Future Enhancements
- [Enhancement 1]: [Description]
- [Enhancement 2]: [Description]

---

## Related Components

### Similar Components
- [Component 1]: [Relationship]
- [Component 2]: [Relationship]

### Complementary Components
- [Component 1]: [How they work together]
- [Component 2]: [How they work together]

---

## Approval and Sign-off

### Technical Review
- **Reviewed By**: ___________
- **Date**: ___________
- **Approved**: [ ] Yes [ ] No
- **Comments**: ___________

### Product Review
- **Reviewed By**: ___________
- **Date**: ___________
- **Approved**: [ ] Yes [ ] No
- **Comments**: ___________

---

## Notes

[Any additional notes or considerations]

---

## References

- [Component Development Guide](COMPONENT_DEVELOPMENT_GUIDE.md)
- [Architecture Overview](ARCHITECTURE_OVERVIEW.md)
- [Quick Reference](QUICK_REFERENCE.md)
- [Guidelines](Guidelines.md)

