# AEM Forms Core Components - Component Development Guide

## Table of Contents

1. [Overview](#overview)
2. [Repository Structure](#repository-structure)
3. [Component Architecture](#component-architecture)
4. [Creating a New Component](#creating-a-new-component)
5. [Component Structure](#component-structure)
6. [Backend Development (Java)](#backend-development-java)
7. [Frontend Development (HTL/JavaScript)](#frontend-development-htljavascript)
8. [Component Configuration](#component-configuration)
9. [Client Libraries](#client-libraries)
10. [Testing](#testing)
11. [Best Practices](#best-practices)
12. [Component Specification Template](#component-specification-template)

---

## Overview

The AEM Forms Core Components repository provides a set of reusable, server-side rendered components for building Adaptive Forms in AEM. This guide provides comprehensive documentation for understanding the architecture and creating new form components.

### Key Technologies

- **Backend**: Java (OSGi), Sling Models, Apache Sling
- **Frontend**: HTL (Sightly), JavaScript (ES6+), CSS
- **Build**: Maven
- **Framework**: AEM Forms, AEM Sites Core Components

### Component Types

1. **Adaptive Form Components** (`ui.af.apps`): Form field and container components
2. **Forms Portal Components** (`ui.apps`): Components for forms portal functionality
3. **XFA Components**: Components for XFA-based forms

---

## Repository Structure

```
aem-core-forms-components/
├── bundles/                    # OSGi bundles (Java code)
│   ├── af-core/               # Adaptive Forms core bundle
│   │   ├── src/main/java/    # Java source code
│   │   └── src/test/java/    # Java tests
│   └── core/                  # Core utilities bundle
├── ui.af.apps/                # Adaptive Form components (JCR content)
│   └── src/main/content/jcr_root/apps/core/fd/components/form/
│       └── [component-name]/v[version]/
│           ├── _cq_dialog/    # Edit dialog configuration
│           ├── _cq_design_dialog/  # Design dialog
│           ├── _cq_template.xml   # Component template metadata
│           ├── clientlibs/    # Client libraries
│           ├── [component].html    # HTL template
│           ├── [component].js      # HTL use-object
│           └── README.md      # Component documentation
├── ui.apps/                   # Forms portal components
├── ui.frontend/               # Frontend JavaScript/CSS source
│   └── src/view/              # JavaScript view classes
├── examples/                  # Example proxy components
├── it/                        # Integration tests
└── ui.tests/                  # UI tests (Cypress)
```

### Key Directories

- **`bundles/af-core/src/main/java/`**: Contains Sling Model interfaces and implementations
- **`ui.af.apps/src/main/content/jcr_root/apps/core/fd/components/form/`**: Component definitions
- **`ui.frontend/src/view/`**: JavaScript runtime classes for form fields
- **`ui.af.apps/.../clientlibs/`**: Client library definitions

---

## Component Architecture

### Architecture Layers

```
┌─────────────────────────────────────────┐
│         Authoring Layer                  │
│  (Edit Dialogs, Design Dialogs)         │
└─────────────────────────────────────────┘
                    ↓
┌─────────────────────────────────────────┐
│         Rendering Layer                  │
│  (HTL Templates, Sling Models)          │
└─────────────────────────────────────────┘
                    ↓
┌─────────────────────────────────────────┐
│         Runtime Layer                    │
│  (JavaScript, Client Libraries)         │
└─────────────────────────────────────────┘
```

### Component Hierarchy

```
FormComponent (Interface)
    ├── Base (Abstract)
    ├── Container (Abstract)
    ├── Field (Abstract)
    │   ├── TextInput
    │   ├── NumberInput
    │   ├── DatePicker
    │   ├── DropDown
    │   ├── CheckBox
    │   ├── RadioButton
    │   └── ...
    └── Button
```

### Data Flow

1. **Authoring**: Author configures component via edit dialog → Properties stored in JCR
2. **Rendering**: HTL template uses Sling Model → Reads JCR properties → Generates HTML
3. **Runtime**: JavaScript initializes component → Binds to form container → Handles user interactions

---

## Creating a New Component

### Step-by-Step Process

1. **Plan the Component**
   - Define component purpose and functionality
   - Identify base class (Field, Container, or Button)
   - List required properties and constraints

2. **Create Component Structure**
   - Create component directory under `ui.af.apps/.../components/form/`
   - Set up version directory (e.g., `v1/`)
   - Create required files (HTL, dialogs, clientlibs)

3. **Implement Backend**
   - Create Sling Model interface
   - Create Sling Model implementation
   - Extend appropriate base class

4. **Implement Frontend**
   - Create HTL template
   - Create HTL use-object (`.js` file)
   - Create JavaScript runtime class (if needed)

5. **Configure Dialogs**
   - Create edit dialog (`_cq_dialog/.content.xml`)
   - Create design dialog if needed
   - Create component template (`_cq_template.xml`)

6. **Set Up Client Libraries**
   - Create client library structure
   - Configure CSS/JS files
   - Register in runtime clientlibs

7. **Documentation**
   - Create README.md
   - Document properties, BEM classes, data attributes

8. **Testing**
   - Write unit tests (Java)
   - Write integration tests
   - Write UI tests (Cypress)

---

## Component Structure

### Directory Structure

```
component-name/v1/
├── .content.xml                    # Component node definition
├── _cq_dialog/                     # Edit dialog
│   └── .content.xml
├── _cq_design_dialog/              # Design dialog (optional)
│   └── .content.xml
├── _cq_template.xml                # Component template metadata
├── clientlibs/
│   ├── editor/                     # Editor clientlib
│   │   ├── js/
│   │   │   └── editDialog.js
│   │   └── js.txt
│   └── site/                       # Runtime clientlib
│       ├── css/
│       │   └── [component]view.css
│       ├── css.txt
│       ├── js/
│       │   └── [component]view.js
│       └── js.txt
├── [component].html                # HTL template
├── [component].js                  # HTL use-object
└── README.md                       # Component documentation
```

### Required Files

#### 1. `.content.xml` (Component Definition)

```xml
<?xml version="1.0" encoding="UTF-8"?>
<jcr:root xmlns:sling="http://sling.apache.org/jcr/sling/1.0" 
          xmlns:jcr="http://www.jcp.org/jcr/1.0"
    jcr:primaryType="nt:unstructured"
    jcr:title="Component Name"
    sling:resourceSuperType="core/fd/components/form/base/v1/base"
    componentGroup=".hidden"
    fieldType="[field-type]"/>
```

#### 2. `_cq_template.xml` (Component Template)

```xml
<?xml version="1.0" encoding="UTF-8"?>
<jcr:root xmlns:jcr="http://www.jcp.org/jcr/1.0" 
          xmlns:nt="http://www.jcp.org/jcr/nt/1.0" 
          xmlns:cq="http://www.day.com/jcr/cq/1.0"
    jcr:primaryType="nt:unstructured"
    jcr:title="Component Display Name"
    fieldType="[field-type]"/>
```

#### 3. HTL Template (`[component].html`)

```html
<sly data-sly-use.component="com.adobe.cq.forms.core.components.models.form.ComponentName"
     data-sly-use.renderer="${'component.js'}"
     data-sly-use.clientlib="${'/libs/granite/sightly/templates/clientlib.html'}"
     data-sly-use.label="${renderer.labelPath}"
     data-sly-use.errorMessage="${renderer.errorMessagePath}"></sly>
<div data-sly-use.formstructparser="com.adobe.cq.forms.core.components.models.form.FormStructureParser"
     class="cmp-adaptiveform-[component]"
     data-cmp-is="adaptiveForm[Component]"
     data-cmp-visible="${component.visible ? 'true' : 'false'}"
     id="${component.id}"
     data-cmp-adaptiveformcontainer-path="${formstructparser.formContainerPath}">
    <!-- Component markup -->
</div>
```

#### 4. HTL Use-Object (`[component].js`)

```javascript
use(function () {
  var clientlibsArr = ['core.forms.components.base.v1.editor'];
  var labelPath = 'core/fd/components/af-commons/v1/fieldTemplates/label.html';
  var errorMessagePath = "core/fd/components/af-commons/v1/fieldTemplates/errorMessage.html";

  return {
    labelPath: labelPath,
    errorMessagePath: errorMessagePath,
    clientlibs: clientlibsArr
  }
});
```

---

## Backend Development (Java)

### Sling Model Interface

Location: `bundles/af-core/src/main/java/com/adobe/cq/forms/core/components/models/form/`

```java
package com.adobe.cq.forms.core.components.models.form;

import org.jetbrains.annotations.Nullable;
import org.osgi.annotation.versioning.ConsumerType;

/**
 * Defines the form ComponentName Sling Model.
 *
 * @since com.adobe.cq.forms.core.components.models.form 0.0.1
 */
@ConsumerType
public interface ComponentName extends Field {
    
    /**
     * Returns a custom property value.
     *
     * @return the custom property value
     * @since com.adobe.cq.forms.core.components.models.form 0.0.1
     */
    @Nullable
    default String getCustomProperty() {
        return null;
    }
}
```

### Sling Model Implementation

Location: `bundles/af-core/src/main/java/com/adobe/cq/forms/core/components/internal/models/v1/form/`

```java
package com.adobe.cq.forms.core.components.internal.models.v1.form;

import javax.annotation.PostConstruct;
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
import com.adobe.cq.forms.core.components.internal.form.ReservedProperties;
import com.adobe.cq.forms.core.components.models.form.ComponentName;
import com.adobe.cq.forms.core.components.util.AbstractFieldImpl;

@Model(
    adaptables = { SlingHttpServletRequest.class, Resource.class },
    adapters = { ComponentName.class, ComponentExporter.class },
    resourceType = { FormConstants.RT_FD_FORM_COMPONENT_V1 })
@Exporter(
    name = ExporterConstants.SLING_MODEL_EXPORTER_NAME,
    extensions = ExporterConstants.SLING_MODEL_EXTENSION)
public class ComponentNameImpl extends AbstractFieldImpl implements ComponentName {

    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL, 
                   name = ReservedProperties.PN_CUSTOM_PROPERTY)
    @Default(values = "defaultValue")
    protected String customProperty;

    @Override
    public String getFieldType() {
        return super.getFieldType(FieldType.COMPONENT_TYPE);
    }

    @Override
    public String getCustomProperty() {
        return customProperty;
    }

    @PostConstruct
    private void init() {
        // Initialization logic
    }
}
```

### Base Classes

#### AbstractFieldImpl
- Base class for all form field components
- Provides common field functionality (label, validation, constraints, etc.)
- Location: `bundles/af-core/src/main/java/com/adobe/cq/forms/core/components/util/AbstractFieldImpl.java`

#### AbstractContainerImpl
- Base class for container components (Panel, Container, etc.)
- Provides container functionality (children management, etc.)

#### AbstractBaseImpl
- Base class for all form components
- Provides core functionality (ID, visibility, enabled state, etc.)

### Key Interfaces

- **`FormComponent`**: Base interface for all form components
- **`Field`**: Interface for form field components
- **`Container`**: Interface for container components
- **`Button`**: Interface for button components

### Reserved Properties

Location: `bundles/af-core/src/main/java/com/adobe/cq/forms/core/components/internal/form/ReservedProperties.java`

Common properties:
- `PN_NAME`: Field name
- `PN_DEFAULT_VALUE`: Default value
- `PN_REQUIRED`: Required flag
- `PN_READ_ONLY`: Read-only flag
- `PN_VISIBLE`: Visibility flag
- `PN_ENABLED`: Enabled flag
- `PN_LABEL`: Label text
- `PN_DESCRIPTION`: Description text

---

## Frontend Development (HTL/JavaScript)

### HTL Template Patterns

#### Using Sling Models

```html
<sly data-sly-use.component="com.adobe.cq.forms.core.components.models.form.ComponentName"></sly>
```

#### Using Form Structure Parser

```html
<div data-sly-use.formstructparser="com.adobe.cq.forms.core.components.models.form.FormStructureParser"
     data-cmp-adaptiveformcontainer-path="${formstructparser.formContainerPath}">
```

#### Data Attributes

Required attributes:
- `data-cmp-is="adaptiveForm[Component]"`: Component identifier
- `data-cmp-adaptiveformcontainer-path`: Path to form container
- `data-cmp-visible`: Visibility state
- `data-cmp-enabled`: Enabled state
- `data-cmp-required`: Required state
- `data-cmp-readonly`: Read-only state

### JavaScript Runtime Class

Location: `ui.af.apps/.../clientlibs/site/js/[component]view.js`

```javascript
(function() {
    "use strict";
    class ComponentName extends FormView.FormFieldBase {
        
        static NS = FormView.Constants.NS;
        static IS = "adaptiveForm[Component]";
        static bemBlock = 'cmp-adaptiveform-[component]';
        
        static selectors = {
            self: "[data-" + this.NS + '-is="' + this.IS + '"]',
            widget: `.${ComponentName.bemBlock}__widget`,
            label: `.${ComponentName.bemBlock}__label`,
            errorDiv: `.${ComponentName.bemBlock}__errormessage`
        };

        constructor(params) {
            super(params);
        }

        getWidget() {
            return this.element.querySelector(ComponentName.selectors.widget);
        }

        getLabel() {
            return this.element.querySelector(ComponentName.selectors.label);
        }

        getErrorDiv() {
            return this.element.querySelector(ComponentName.selectors.errorDiv);
        }

        setModel(model) {
            super.setModel(model);
            // Component-specific initialization
            this.widget.addEventListener('change', (e) => {
                this.setModelValue(e.target.value);
            });
        }
    }

    FormView.Utils.setupField(({element, formContainer}) => {
        return new ComponentName({element, formContainer})
    }, ComponentName.selectors.self);

})();
```

### Base Classes (JavaScript)

#### FormFieldBase
- Base class for all form field JavaScript classes
- Provides common field functionality
- Location: `ui.frontend/src/view/FormFieldBase.js`

Key methods:
- `setModel(model)`: Initialize component with model
- `setModelValue(value)`: Set value in model
- `setWidgetValue(value)`: Set value in widget
- `setActive()` / `setInactive()`: Manage active state
- `setVisible(visible)`: Manage visibility
- `setEnabled(enabled)`: Manage enabled state

---

## Component Configuration

### Edit Dialog Structure

Location: `_cq_dialog/.content.xml`

```xml
<?xml version="1.0" encoding="UTF-8"?>
<jcr:root xmlns:sling="http://sling.apache.org/jcr/sling/1.0" 
          xmlns:granite="http://www.adobe.com/jcr/granite/1.0" 
          xmlns:cq="http://www.day.com/jcr/cq/1.0" 
          xmlns:jcr="http://www.jcp.org/jcr/1.0" 
          xmlns:nt="http://www.jcp.org/jcr/nt/1.0"
    jcr:primaryType="nt:unstructured"
    jcr:title="Edit Component Name"
    sling:resourceType="cq/gui/components/authoring/dialog"
    extraClientlibs="core.forms.components.base.v1.editor"
    trackingFeature="core-components:adaptiveform-[component]:v1">
    <content
        jcr:primaryType="nt:unstructured"
        granite:class="cmp-adaptiveform-[component]__editdialog"
        sling:resourceType="granite/ui/components/coral/foundation/container">
        <items jcr:primaryType="nt:unstructured">
            <tabs
                jcr:primaryType="nt:unstructured"
                sling:resourceType="granite/ui/components/coral/foundation/tabs"
                maximized="{Boolean}true">
                <items jcr:primaryType="nt:unstructured">
                    <basic
                        jcr:primaryType="nt:unstructured"
                        jcr:title="Basic"
                        sling:resourceType="granite/ui/components/coral/foundation/container">
                        <items jcr:primaryType="nt:unstructured">
                            <!-- Dialog fields -->
                        </items>
                    </basic>
                </items>
            </tabs>
        </items>
    </content>
</jcr:root>
```

### Common Dialog Fields

#### Text Field
```xml
<fieldName
    jcr:primaryType="nt:unstructured"
    sling:resourceType="granite/ui/components/coral/foundation/form/textfield"
    fieldLabel="Field Label"
    name="./fieldName"/>
```

#### Checkbox
```xml
<fieldName
    jcr:primaryType="nt:unstructured"
    sling:resourceType="granite/ui/components/coral/foundation/form/checkbox"
    fieldLabel="Field Label"
    name="./fieldName"
    checked="{Boolean}false"/>
```

#### Number Field
```xml
<fieldName
    jcr:primaryType="nt:unstructured"
    sling:resourceType="granite/ui/components/coral/foundation/form/numberfield"
    fieldLabel="Field Label"
    name="./fieldName"/>
```

### Using Shared Dialog Components

Many components inherit from the base component dialog:
- Location: `ui.af.apps/.../components/form/base/v1/base/_cq_dialog/`
- Common fields: Label, Name, Description, Required, Read-only, etc.

---

## Client Libraries

### Client Library Structure

```
clientlibs/
├── editor/                    # Editor clientlib (for dialogs)
│   ├── js/
│   │   └── editDialog.js
│   └── js.txt
└── site/                      # Runtime clientlib
    ├── css/
    │   └── [component]view.css
    ├── css.txt
    ├── js/
    │   └── [component]view.js
    └── js.txt
```

### Client Library Definition

Location: `clientlibs/site/.content.xml`

```xml
<?xml version="1.0" encoding="UTF-8"?>
<jcr:root xmlns:cq="http://www.day.com/jcr/cq/1.0" 
          xmlns:jcr="http://www.jcp.org/jcr/1.0"
    jcr:primaryType="cq:ClientLibraryFolder"
    allowProxy="{Boolean}true"
    categories="[core.forms.components.[component].v1.runtime]"
    dependencies="[core.forms.components.runtime.base]"/>
```

### Client Library Categories

Naming convention:
- Runtime: `core.forms.components.[component].v[version].runtime`
- Editor: `core.forms.components.[component].v[version].editor`

### Registering in Runtime All

Location: `ui.af.apps/.../af-clientlibs/core-forms-components-runtime-all/.content.xml`

Add your component's clientlib to the `embed` array:

```xml
embed="[...,core.forms.components.[component].v1.runtime]"
```

---

## Testing

### Unit Tests (Java)

Location: `bundles/af-core/src/test/java/`

```java
package com.adobe.cq.forms.core.components.internal.models.v1.form;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith(AemContextExtension.class)
class ComponentNameImplTest {
    
    private final AemContext context = new AemContext();
    
    @BeforeEach
    void setUp() {
        // Setup test context
    }
    
    @Test
    void testGetCustomProperty() {
        // Test implementation
    }
}
```

### Integration Tests

Location: `it/` directory
- Test component rendering
- Test dialog functionality
- Test form submission

### UI Tests (Cypress)

Location: `ui.tests/test-module/specs/`

```javascript
describe('Component Name', () => {
    it('should render correctly', () => {
        // Test implementation
    });
});
```

---

## Best Practices

### 1. Component Design

- **Single Responsibility**: Each component should have one clear purpose
- **Reusability**: Design components to be reusable across different forms
- **Extensibility**: Allow components to be extended/customized

### 2. Code Organization

- **Naming Conventions**: Follow existing naming patterns
- **Versioning**: Use version directories (v1, v2, etc.)
- **Documentation**: Always include README.md for each component

### 3. Performance

- **Lazy Loading**: Load JavaScript only when needed
- **Minimal Dependencies**: Keep dependencies to a minimum
- **Caching**: Ensure components are cacheable

### 4. Accessibility

- **ARIA Labels**: Include appropriate ARIA attributes
- **Keyboard Navigation**: Support keyboard navigation
- **Screen Readers**: Ensure compatibility with screen readers

### 5. Internationalization

- **i18n**: Use i18n for all user-facing text
- **RTL Support**: Consider right-to-left language support
- **Locale-specific Formatting**: Support locale-specific formatting

### 6. Security

- **XSS Protection**: Use HTL context-aware escaping
- **Input Validation**: Validate all user inputs
- **CSRF Protection**: Follow CSRF protection guidelines

### 7. BEM Naming

Follow BEM (Block Element Modifier) naming convention:

```
BLOCK: cmp-adaptiveform-[component]
ELEMENT: cmp-adaptiveform-[component]__[element]
MODIFIER: cmp-adaptiveform-[component]--[modifier]
```

Example:
```css
.cmp-adaptiveform-textinput { }
.cmp-adaptiveform-textinput__label { }
.cmp-adaptiveform-textinput__widget { }
.cmp-adaptiveform-textinput--required { }
```

---

## Component Specification Template

Use this template when creating specifications for new components:

```markdown
# Component Name Specification

## Overview
[Brief description of the component's purpose]

## Component Type
- [ ] Field Component
- [ ] Container Component
- [ ] Button Component
- [ ] Other: ___________

## Base Class
- [ ] AbstractFieldImpl
- [ ] AbstractContainerImpl
- [ ] AbstractBaseImpl
- [ ] Other: ___________

## Properties

### Required Properties
| Property | Type | Description | Default |
|----------|------|-------------|---------|
| name | String | Field name | - |

### Optional Properties
| Property | Type | Description | Default |
|----------|------|-------------|---------|
| label | String | Field label | - |
| required | Boolean | Required flag | false |

## Constraints
- [ ] String Constraints
- [ ] Number Constraints
- [ ] Date Constraints
- [ ] File Constraints
- [ ] Custom Constraints: ___________

## Validation Rules
1. [Validation rule 1]
2. [Validation rule 2]

## Dialog Configuration
- [ ] Uses base dialog
- [ ] Custom dialog required
- [ ] Additional tabs needed: ___________

## Client Libraries
- Runtime category: `core.forms.components.[component].v1.runtime`
- Editor category: `core.forms.components.[component].v1.editor`
- Dependencies: [List dependencies]

## JavaScript Runtime
- [ ] Extends FormFieldBase
- [ ] Extends FormContainer
- [ ] Custom implementation
- [ ] No JavaScript required

## BEM Structure
```
BLOCK cmp-adaptiveform-[component]
    ELEMENT cmp-adaptiveform-[component]__[element1]
    ELEMENT cmp-adaptiveform-[component]__[element2]
    MODIFIER cmp-adaptiveform-[component]--[modifier]
```

## Data Attributes
- `data-cmp-is="adaptiveForm[Component]"`
- `data-cmp-adaptiveformcontainer-path`
- [Additional attributes]

## Dependencies
- [List any dependencies on other components or libraries]

## Testing Requirements
- [ ] Unit tests (Java)
- [ ] Integration tests
- [ ] UI tests (Cypress)
- [ ] Accessibility tests

## Documentation Requirements
- [ ] README.md
- [ ] API documentation
- [ ] Usage examples
- [ ] Migration guide (if applicable)
```

---

## Additional Resources

### Key Files to Reference

1. **Base Component**: `ui.af.apps/.../components/form/base/v1/base/`
2. **Text Input Example**: `ui.af.apps/.../components/form/textinput/v1/textinput/`
3. **AbstractFieldImpl**: `bundles/af-core/.../util/AbstractFieldImpl.java`
4. **FormConstants**: `bundles/af-core/.../form/FormConstants.java`
5. **ReservedProperties**: `bundles/af-core/.../form/ReservedProperties.java`

### External Documentation

- [AEM Sites Core Components](https://github.com/adobe/aem-core-wcm-components)
- [AEM Forms Documentation](https://experienceleague.adobe.com/docs/experience-manager-forms.html)
- [HTL Documentation](https://experienceleague.adobe.com/docs/experience-manager-htl/content/overview.html)
- [Sling Models Documentation](https://sling.apache.org/documentation/bundles/models.html)

---

## Conclusion

This guide provides a comprehensive foundation for understanding and developing AEM Forms Core Components. When creating new components:

1. Study existing components (especially TextInput, Button, Container)
2. Follow established patterns and conventions
3. Write comprehensive tests
4. Document thoroughly
5. Follow the component checklist in `Guidelines.md`

For questions or contributions, refer to the [Contributing Guide](CONTRIBUTING.md).

