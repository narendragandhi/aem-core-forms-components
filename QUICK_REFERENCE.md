# AEM Forms Core Components - Quick Reference

## Component File Structure

```
component-name/v1/
├── .content.xml                    # Component definition
├── _cq_dialog/.content.xml         # Edit dialog
├── _cq_design_dialog/.content.xml  # Design dialog (optional)
├── _cq_template.xml                # Component template
├── component.html                  # HTL template
├── component.js                    # HTL use-object
├── clientlibs/
│   ├── editor/
│   │   ├── js/editDialog.js
│   │   └── js.txt
│   └── site/
│       ├── css/componentview.css
│       ├── css.txt
│       ├── js/componentview.js
│       └── js.txt
└── README.md                       # Documentation
```

---

## Java Sling Model Template

### Interface

```java
@ConsumerType
public interface ComponentName extends Field {
    @Nullable
    default String getCustomProperty() {
        return null;
    }
}
```

### Implementation

```java
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
    protected String customProperty;
    
    @Override
    public String getFieldType() {
        return super.getFieldType(FieldType.COMPONENT_TYPE);
    }
    
    @Override
    public String getCustomProperty() {
        return customProperty;
    }
}
```

---

## HTL Template Template

```html
<sly data-sly-use.component="com.adobe.cq.forms.core.components.models.form.ComponentName"
     data-sly-use.renderer="${'component.js'}"
     data-sly-use.clientlib="${'/libs/granite/sightly/templates/clientlib.html'}"
     data-sly-use.label="${renderer.labelPath}"
     data-sly-use.errorMessage="${renderer.errorMessagePath}"></sly>
<div data-sly-use.formstructparser="com.adobe.cq.forms.core.components.models.form.FormStructureParser"
     class="cmp-adaptiveform-component"
     data-cmp-is="adaptiveFormComponent"
     data-cmp-visible="${component.visible ? 'true' : 'false'}"
     data-cmp-enabled="${component.enabled ? 'true' : 'false'}"
     data-cmp-required="${component.required ? 'true' : 'false'}"
     data-cmp-readonly="${component.readOnly ? 'true' : 'false'}"
     id="${component.id}"
     data-cmp-adaptiveformcontainer-path="${formstructparser.formContainerPath}">
    <div class="cmp-adaptiveform-component__label-container">
        <div data-sly-call="${label.label @componentId=widgetId, labelValue=component.label.value, labelVisible=component.label.visible, bemBlock='cmp-adaptiveform-component'}" data-sly-unwrap></div>
    </div>
    <!-- Component-specific markup -->
    <div data-sly-call="${errorMessage.errorMessage @componentId=component.id, bemBlock='cmp-adaptiveform-component'}" data-sly-unwrap></div>
</div>
```

---

## JavaScript Runtime Template

```javascript
(function() {
    "use strict";
    class ComponentName extends FormView.FormFieldBase {
        
        static NS = FormView.Constants.NS;
        static IS = "adaptiveFormComponent";
        static bemBlock = 'cmp-adaptiveform-component';
        
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

        setModel(model) {
            super.setModel(model);
            // Component-specific initialization
        }
    }

    FormView.Utils.setupField(({element, formContainer}) => {
        return new ComponentName({element, formContainer})
    }, ComponentName.selectors.self);

})();
```

---

## Edit Dialog Template

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
    trackingFeature="core-components:adaptiveform-component:v1">
    <content
        jcr:primaryType="nt:unstructured"
        granite:class="cmp-adaptiveform-component__editdialog"
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
                            <!-- Dialog fields here -->
                        </items>
                    </basic>
                </items>
            </tabs>
        </items>
    </content>
</jcr:root>
```

---

## Common Dialog Fields

### Text Field
```xml
<fieldName
    jcr:primaryType="nt:unstructured"
    sling:resourceType="granite/ui/components/coral/foundation/form/textfield"
    fieldLabel="Field Label"
    name="./fieldName"/>
```

### Number Field
```xml
<fieldName
    jcr:primaryType="nt:unstructured"
    sling:resourceType="granite/ui/components/coral/foundation/form/numberfield"
    fieldLabel="Field Label"
    name="./fieldName"/>
```

### Checkbox
```xml
<fieldName
    jcr:primaryType="nt:unstructured"
    sling:resourceType="granite/ui/components/coral/foundation/form/checkbox"
    fieldLabel="Field Label"
    name="./fieldName"
    checked="{Boolean}false"/>
```

### Select/Dropdown
```xml
<fieldName
    jcr:primaryType="nt:unstructured"
    sling:resourceType="granite/ui/components/coral/foundation/form/select"
    fieldLabel="Field Label"
    name="./fieldName">
    <items jcr:primaryType="nt:unstructured">
        <option1
            jcr:primaryType="nt:unstructured"
            text="Option 1"
            value="value1"/>
    </items>
</fieldName>
```

---

## Client Library Configuration

### Runtime Client Library

Location: `clientlibs/site/.content.xml`

```xml
<?xml version="1.0" encoding="UTF-8"?>
<jcr:root xmlns:cq="http://www.day.com/jcr/cq/1.0" 
          xmlns:jcr="http://www.jcp.org/jcr/1.0"
    jcr:primaryType="cq:ClientLibraryFolder"
    allowProxy="{Boolean}true"
    categories="[core.forms.components.component.v1.runtime]"
    dependencies="[core.forms.components.runtime.base]"/>
```

### Editor Client Library

Location: `clientlibs/editor/.content.xml`

```xml
<?xml version="1.0" encoding="UTF-8"?>
<jcr:root xmlns:cq="http://www.day.com/jcr/cq/1.0" 
          xmlns:jcr="http://www.jcp.org/jcr/1.0"
    jcr:primaryType="cq:ClientLibraryFolder"
    allowProxy="{Boolean}true"
    categories="[core.forms.components.component.v1.editor]"
    dependencies="[core.forms.components.base.v1.editor]"/>
```

---

## Component Definition Files

### .content.xml (Component Node)

```xml
<?xml version="1.0" encoding="UTF-8"?>
<jcr:root xmlns:sling="http://sling.apache.org/jcr/sling/1.0" 
          xmlns:jcr="http://www.jcp.org/jcr/1.0"
    jcr:primaryType="nt:unstructured"
    jcr:title="Component Name"
    sling:resourceSuperType="core/fd/components/form/base/v1/base"
    componentGroup=".hidden"
    fieldType="component-type"/>
```

### _cq_template.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<jcr:root xmlns:jcr="http://www.jcp.org/jcr/1.0" 
          xmlns:nt="http://www.jcp.org/jcr/nt/1.0" 
          xmlns:cq="http://www.day.com/jcr/cq/1.0"
    jcr:primaryType="nt:unstructured"
    jcr:title="Component Display Name"
    fieldType="component-type"/>
```

---

## Common Reserved Properties

| Property | Constant | Type | Description |
|----------|----------|------|-------------|
| `./name` | `PN_NAME` | String | Field name |
| `./jcr:title` | `PN_TITLE` | String | Label |
| `./default` | `PN_DEFAULT_VALUE` | Object[] | Default value |
| `./required` | `PN_REQUIRED` | Boolean | Required flag |
| `./readOnly` | `PN_READ_ONLY` | Boolean | Read-only flag |
| `./visible` | `PN_VISIBLE` | Boolean | Visibility flag |
| `./enabled` | `PN_ENABLED` | Boolean | Enabled flag |
| `./description` | `PN_DESCRIPTION` | String | Description |
| `./placeholder` | `PN_PLACEHOLDER` | String | Placeholder text |

---

## BEM Naming Convention

```
BLOCK: cmp-adaptiveform-[component]
ELEMENT: cmp-adaptiveform-[component]__[element]
MODIFIER: cmp-adaptiveform-[component]--[modifier]
```

### Example
```css
.cmp-adaptiveform-textinput { }
.cmp-adaptiveform-textinput__label { }
.cmp-adaptiveform-textinput__widget { }
.cmp-adaptiveform-textinput__errormessage { }
.cmp-adaptiveform-textinput--required { }
```

---

## Required Data Attributes

```html
data-cmp-is="adaptiveForm[Component]"
data-cmp-adaptiveformcontainer-path="${formstructparser.formContainerPath}"
data-cmp-visible="${component.visible ? 'true' : 'false'}"
data-cmp-enabled="${component.enabled ? 'true' : 'false'}"
data-cmp-required="${component.required ? 'true' : 'false'}"
data-cmp-readonly="${component.readOnly ? 'true' : 'false'}"
```

---

## Common HTL Use-Objects

### Label
```html
data-sly-use.label="${renderer.labelPath}"
<div data-sly-call="${label.label @componentId=widgetId, labelValue=component.label.value, labelVisible=component.label.visible, bemBlock='cmp-adaptiveform-component'}" data-sly-unwrap></div>
```

### Error Message
```html
data-sly-use.errorMessage="${renderer.errorMessagePath}"
<div data-sly-call="${errorMessage.errorMessage @componentId=component.id, bemBlock='cmp-adaptiveform-component'}" data-sly-unwrap></div>
```

### Short Description (Tooltip)
```html
data-sly-use.shortDescription="${renderer.shortDescriptionPath}"
<div data-sly-call="${shortDescription.shortDescription @componentId=component.id, shortDescriptionVisible=component.tooltipVisible, shortDescription=component.tooltip, bemBlock='cmp-adaptiveform-component'}" data-sly-unwrap></div>
```

### Long Description
```html
data-sly-use.longDescription="${renderer.longDescriptionPath}"
<div data-sly-call="${longDescription.longDescription @componentId=component.id, longDescription=component.description, bemBlock='cmp-adaptiveform-component'}" data-sly-unwrap></div>
```

### Question Mark
```html
data-sly-use.questionMark="${renderer.questionMarkPath}"
<div data-sly-call="${questionMark.questionMark @componentId=component.id, longDescription=component.description, bemBlock='cmp-adaptiveform-component'}" data-sly-unwrap></div>
```

---

## Maven Build Commands

```bash
# Build and install to author
mvn clean install -PautoInstallPackage

# Build and install to publish
mvn clean install -PautoInstallPackagePublish

# Build for cloud service
mvn clean install -PautoInstallPackage,cloud

# Format code
mvn clean install -Pformat-code

# Build specific module
mvn clean install -PautoInstallPackage -pl ui.af.apps -am
```

---

## Key File Locations

### Java Files
- **Interfaces**: `bundles/af-core/src/main/java/com/adobe/cq/forms/core/components/models/form/`
- **Implementations**: `bundles/af-core/src/main/java/com/adobe/cq/forms/core/components/internal/models/v1/form/`
- **Utilities**: `bundles/af-core/src/main/java/com/adobe/cq/forms/core/components/util/`
- **Constants**: `bundles/af-core/src/main/java/com/adobe/cq/forms/core/components/internal/form/`

### Component Files
- **Components**: `ui.af.apps/src/main/content/jcr_root/apps/core/fd/components/form/`
- **Base Component**: `ui.af.apps/.../components/form/base/v1/base/`
- **Client Libraries**: `ui.af.apps/.../af-clientlibs/`

### Frontend Files
- **JavaScript Source**: `ui.frontend/src/view/`
- **Client Library Config**: `ui.frontend/clientlib.config.cjs`

---

## Testing Templates

### Java Unit Test
```java
@ExtendWith(AemContextExtension.class)
class ComponentNameImplTest {
    private final AemContext context = new AemContext();
    
    @BeforeEach
    void setUp() {
        // Setup
    }
    
    @Test
    void testMethod() {
        // Test
    }
}
```

### Cypress Test
```javascript
describe('Component Name', () => {
    it('should render correctly', () => {
        // Test
    });
});
```

---

## Common Patterns

### Extending Base Dialog
```xml
<!-- Inherit from base component dialog -->
<sling:resourceSuperType>core/fd/components/form/base/v1/base</sling:resourceSuperType>
```

### Using Shared Templates
```html
<!-- Use shared field templates -->
data-sly-use.label="${renderer.labelPath}"
```

### Registering JavaScript
```javascript
FormView.Utils.setupField(({element, formContainer}) => {
    return new ComponentName({element, formContainer})
}, ComponentName.selectors.self);
```

---

## Useful Constants

### FormConstants
- `RT_FD_FORM_TEXT_V1`: Text input resource type
- `RT_FD_FORM_CONTAINER_V1`: Container resource type
- `RT_FD_FORM_BUTTON_V1`: Button resource type

### FieldType
- `TEXT_INPUT`: Text input field type
- `NUMBER_INPUT`: Number input field type
- `DATE_PICKER`: Date picker field type

---

## Checklist for New Component

- [ ] Create component directory structure
- [ ] Create `.content.xml` component definition
- [ ] Create `_cq_template.xml`
- [ ] Create Sling Model interface
- [ ] Create Sling Model implementation
- [ ] Create HTL template (`component.html`)
- [ ] Create HTL use-object (`component.js`)
- [ ] Create edit dialog (`_cq_dialog/.content.xml`)
- [ ] Create client library structure
- [ ] Create JavaScript runtime class
- [ ] Create CSS file
- [ ] Register client library in runtime-all
- [ ] Create README.md
- [ ] Write unit tests
- [ ] Write integration tests
- [ ] Update documentation

---

## Additional Resources

- **Component Development Guide**: [COMPONENT_DEVELOPMENT_GUIDE.md](COMPONENT_DEVELOPMENT_GUIDE.md)
- **Architecture Overview**: [ARCHITECTURE_OVERVIEW.md](ARCHITECTURE_OVERVIEW.md)
- **Guidelines**: [Guidelines.md](Guidelines.md)
- **Main README**: [README.md](README.md)

