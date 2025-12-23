# AEM Forms Core Components - Architecture Overview

## Table of Contents

1. [System Architecture](#system-architecture)
2. [Component Lifecycle](#component-lifecycle)
3. [Data Flow](#data-flow)
4. [Key Components and Interfaces](#key-components-and-interfaces)
5. [Module Structure](#module-structure)
6. [Build and Deployment](#build-and-deployment)

---

## System Architecture

### High-Level Architecture

```
┌─────────────────────────────────────────────────────────────┐
│                    AEM Forms Core Components                 │
├─────────────────────────────────────────────────────────────┤
│                                                               │
│  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐      │
│  │   Authoring  │  │   Rendering  │  │   Runtime    │      │
│  │    Layer     │  │     Layer    │  │    Layer     │      │
│  └──────────────┘  └──────────────┘  └──────────────┘      │
│         │                 │                  │               │
│         │                 │                  │               │
│  ┌──────────────────────────────────────────────────────┐  │
│  │              JCR Repository (Content)                 │  │
│  └──────────────────────────────────────────────────────┘  │
│                                                               │
│  ┌──────────────────────────────────────────────────────┐  │
│  │         OSGi Bundles (Java/Sling Models)              │  │
│  └──────────────────────────────────────────────────────┘  │
│                                                               │
│  ┌──────────────────────────────────────────────────────┐  │
│  │      Client Libraries (JavaScript/CSS)                │  │
│  └──────────────────────────────────────────────────────┘  │
└─────────────────────────────────────────────────────────────┘
```

### Layer Responsibilities

#### 1. Authoring Layer
- **Purpose**: Component configuration in AEM Author
- **Technologies**: Granite UI, Coral UI, Touch UI Dialogs
- **Components**: Edit Dialogs, Design Dialogs, Component Toolbar
- **Output**: JCR properties stored in component nodes

#### 2. Rendering Layer
- **Purpose**: Server-side rendering of components
- **Technologies**: HTL (Sightly), Sling Models, Sling Resource Resolution
- **Components**: HTL Templates, Sling Model Interfaces/Implementations
- **Output**: HTML markup with data attributes

#### 3. Runtime Layer
- **Purpose**: Client-side interactivity and form behavior
- **Technologies**: JavaScript (ES6+), CSS, Adaptive Forms Runtime
- **Components**: JavaScript View Classes, Client Libraries
- **Output**: Interactive form behavior, validation, submission

---

## Component Lifecycle

### 1. Authoring Phase

```
Author Opens Dialog
    ↓
Dialog Configuration (Granite UI)
    ↓
Properties Saved to JCR
    ↓
Component Node Created/Updated
```

**Key Files:**
- `_cq_dialog/.content.xml`: Dialog definition
- `_cq_design_dialog/.content.xml`: Design dialog (optional)
- `clientlibs/editor/`: Editor client libraries

### 2. Rendering Phase

```
HTTP Request to Component
    ↓
Sling Resource Resolution
    ↓
HTL Template Execution
    ↓
Sling Model Adaptation
    ↓
HTML Generation
    ↓
Response to Client
```

**Key Files:**
- `[component].html`: HTL template
- `[component].js`: HTL use-object
- Sling Model Interface/Implementation

### 3. Runtime Phase

```
Page Load
    ↓
Client Libraries Loaded
    ↓
JavaScript Initialization
    ↓
Component Registration
    ↓
Form Container Binding
    ↓
Event Handlers Attached
    ↓
User Interaction
```

**Key Files:**
- `clientlibs/site/js/[component]view.js`: JavaScript runtime class
- `clientlibs/site/css/[component]view.css`: Component styles

---

## Data Flow

### Authoring to Rendering

```
┌─────────────┐
│   Author    │
│  Configures │
│  Component  │
└──────┬──────┘
       │
       │ Properties
       ↓
┌─────────────┐
│     JCR     │
│  Repository │
└──────┬──────┘
       │
       │ Resource Resolution
       ↓
┌─────────────┐
│ Sling Model │
│  Adaptation │
└──────┬──────┘
       │
       │ Model Data
       ↓
┌─────────────┐
│ HTL Template│
│  Rendering  │
└──────┬──────┘
       │
       │ HTML + Data Attributes
       ↓
┌─────────────┐
│   Browser   │
└─────────────┘
```

### Runtime Data Flow

```
┌─────────────┐
│   Browser   │
│   Event     │
└──────┬──────┘
       │
       │ Event
       ↓
┌─────────────┐
│  JavaScript │
│   Handler   │
└──────┬──────┘
       │
       │ Update Model
       ↓
┌─────────────┐
│ Form Model  │
│  (Runtime)  │
└──────┬──────┘
       │
       │ State Change
       ↓
┌─────────────┐
│   UI Update │
└─────────────┘
```

---

## Key Components and Interfaces

### Backend (Java) Hierarchy

```
FormComponent (Interface)
    │
    ├── Base (Interface)
    │   └── AbstractBaseImpl (Abstract Class)
    │       ├── AbstractFieldImpl
    │       │   ├── TextInputImpl
    │       │   ├── NumberInputImpl
    │       │   ├── DatePickerImpl
    │       │   ├── DropDownImpl
    │       │   └── ...
    │       │
    │       ├── AbstractContainerImpl
    │       │   ├── ContainerImpl
    │       │   ├── PanelImpl
    │       │   └── ...
    │       │
    │       └── ButtonImpl
    │
    └── Field (Interface)
        └── [Field implementations extend AbstractFieldImpl]
```

### Frontend (JavaScript) Hierarchy

```
FormView
    │
    ├── FormFieldBase
    │   ├── TextInput
    │   ├── NumberInput
    │   ├── DatePicker
    │   └── ...
    │
    ├── FormContainer
    │   ├── FormPanel
    │   ├── FormTabs
    │   └── ...
    │
    └── FormButton
```

### Key Interfaces

#### FormComponent
- Base interface for all form components
- Provides: `getFieldType()`, `isVisible()`, `getName()`, `getRules()`, `getEvents()`

#### Field
- Interface for form field components
- Extends: `FormComponent`
- Provides: `getValue()`, `getLabel()`, `getDescription()`, validation methods

#### Container
- Interface for container components
- Extends: `FormComponent`
- Provides: `getItems()`, child management methods

#### Button
- Interface for button components
- Extends: `FormComponent`
- Provides: button-specific methods

---

## Module Structure

### Maven Modules

```
core-forms-components-reactor (POM)
├── parent (POM)
├── bundles/
│   ├── af-core (OSGi Bundle)
│   └── core (OSGi Bundle)
├── ui.af.apps (Content Package)
├── ui.apps (Content Package)
├── ui.frontend (Frontend Build)
├── examples (Content Package)
├── all (Content Package)
└── jsdocs (Documentation)
```

### Module Responsibilities

#### bundles/af-core
- **Type**: OSGi Bundle
- **Purpose**: Java/Sling Model implementations
- **Output**: JAR file deployed to AEM
- **Key Packages**:
  - `com.adobe.cq.forms.core.components.models.form`: Sling Model interfaces
  - `com.adobe.cq.forms.core.components.internal.models`: Sling Model implementations
  - `com.adobe.cq.forms.core.components.util`: Utility classes

#### ui.af.apps
- **Type**: Content Package
- **Purpose**: Adaptive Form component definitions
- **Output**: ZIP package with JCR content
- **Contains**:
  - Component definitions (HTL, dialogs, clientlibs)
  - Component templates
  - Client library definitions

#### ui.apps
- **Type**: Content Package
- **Purpose**: Forms Portal components
- **Output**: ZIP package with JCR content

#### ui.frontend
- **Type**: Frontend Build Module
- **Purpose**: JavaScript/CSS source compilation
- **Output**: Compiled client libraries
- **Technologies**: Webpack, Babel, aem-clientlib-generator

#### examples
- **Type**: Content Package
- **Purpose**: Example proxy components
- **Output**: ZIP package demonstrating customization

---

## Build and Deployment

### Build Process

```
1. Maven Compilation
   ├── Java compilation (bundles)
   ├── HTL compilation (ui.af.apps, ui.apps)
   └── Frontend build (ui.frontend)
       │
2. Package Creation
   ├── OSGi bundles → JAR files
   ├── Content packages → ZIP files
   └── Client libraries → JCR content
       │
3. Installation
   └── Deploy to AEM instance
```

### Build Profiles

- **`autoInstallPackage`**: Install to AEM author instance
- **`autoInstallPackagePublish`**: Install to AEM publish instance
- **`cloud`**: Generate cloud-ready artifacts (components in `/libs`)
- **`format-code`**: Format Java code
- **`include-wcm-components-examples`**: Include WCM Core Components examples

### Deployment Locations

#### Components
- **Development**: `/apps/core/fd/components/`
- **Cloud Service**: `/libs/core/fd/components/` (when using `cloud` profile)

#### Client Libraries
- **Location**: `/apps/core/fd/af-clientlibs/` or `/libs/core/fd/af-clientlibs/`
- **Categories**: `core.forms.components.*`

#### OSGi Bundles
- **Location**: OSGi container
- **Symbolic Names**:
  - `com.adobe.cq.forms.core.components.af-core`
  - `com.adobe.cq.forms.core.components.core`

---

## Component Registration

### Resource Type Mapping

Components are registered via resource types:

```
Resource Type → Sling Model
/apps/core/fd/components/form/textinput/v1/textinput
    ↓
FormConstants.RT_FD_FORM_TEXT_V1
    ↓
TextInputImpl (Sling Model)
```

### Sling Model Registration

```java
@Model(
    adaptables = { SlingHttpServletRequest.class, Resource.class },
    adapters = { TextInput.class, ComponentExporter.class },
    resourceType = { FormConstants.RT_FD_FORM_TEXT_V1 })
```

### JavaScript Registration

```javascript
FormView.Utils.setupField(({element, formContainer}) => {
    return new TextInput({element, formContainer})
}, TextInput.selectors.self);
```

---

## Client Library System

### Client Library Categories

```
core.forms.components.runtime.base
    ├── core.forms.components.[component].v1.runtime
    │   └── [Component-specific JS/CSS]
    └── ...
    │
core.forms.components.runtime.all
    └── Embeds all component runtime libraries
```

### Client Library Structure

```
af-clientlibs/
├── core-forms-components-runtime-base/
│   └── .content.xml
├── core-forms-components-runtime-all/
│   └── .content.xml (embeds all components)
└── [component-specific libraries]/
    └── .content.xml
```

### Loading Strategy

1. **Base Library**: Loaded first, contains common functionality
2. **Component Libraries**: Loaded on demand or embedded in "all" library
3. **Editor Libraries**: Loaded only in authoring mode

---

## Form Container Integration

### Form Structure Parser

The `FormStructureParser` provides access to form-level information:

```html
<div data-sly-use.formstructparser="com.adobe.cq.forms.core.components.models.form.FormStructureParser"
     data-cmp-adaptiveformcontainer-path="${formstructparser.formContainerPath}">
```

### Component Registration with Container

```javascript
// Component registers itself with form container
FormView.Utils.setupField(({element, formContainer}) => {
    return new ComponentName({element, formContainer})
}, ComponentName.selectors.self);
```

### Form Model Integration

Components interact with the form's runtime model:
- Value changes propagate to form model
- Validation rules are evaluated
- Form state is maintained

---

## Extension Points

### Customizing Components

1. **Proxy Components**: Create proxy components in `/apps/` that extend core components
2. **Sling Model Override**: Override Sling Model implementations
3. **HTL Override**: Override HTL templates
4. **JavaScript Extension**: Extend JavaScript classes

### Example: Proxy Component

```
/apps/[project]/components/form/textinput
    ├── .content.xml (sling:resourceSuperType points to core component)
    └── textinput.html (custom HTL template)
```

---

## Performance Considerations

### Caching

- **Component Output**: Components should be cacheable
- **Client Libraries**: Cached by browser and AEM
- **Sling Models**: Can be cached using `@Cacheable` annotation

### Optimization

- **Lazy Loading**: Load JavaScript only when needed
- **Minimal Dependencies**: Keep client library dependencies minimal
- **Code Splitting**: Split large JavaScript files
- **Tree Shaking**: Remove unused code

---

## Security Considerations

### XSS Protection

- **HTL Context-Aware Escaping**: HTL automatically escapes based on context
- **DOMPurify**: Used for sanitizing user input in JavaScript

### CSRF Protection

- **Granite CSRF**: Client libraries include CSRF token support
- **Form Submission**: CSRF tokens validated on form submission

### Input Validation

- **Server-Side**: Sling Models validate input
- **Client-Side**: JavaScript validates before submission
- **Constraint Validation**: HTML5 constraint validation

---

## Conclusion

This architecture overview provides a comprehensive understanding of how AEM Forms Core Components are structured and how they interact. Key takeaways:

1. **Layered Architecture**: Clear separation between authoring, rendering, and runtime
2. **Sling Models**: Backend logic encapsulated in Sling Models
3. **HTL Templates**: Server-side rendering using HTL
4. **JavaScript Runtime**: Client-side interactivity through JavaScript classes
5. **Client Libraries**: Organized client library system for CSS/JS
6. **Extension Points**: Multiple ways to customize and extend components

For detailed implementation guidance, refer to the [Component Development Guide](COMPONENT_DEVELOPMENT_GUIDE.md).

