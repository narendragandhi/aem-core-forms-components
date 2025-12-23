# AEM Forms Core Components - Documentation Index

This document provides an index to all available documentation for the AEM Forms Core Components repository.

---

## Getting Started

### For New Developers

1. **Start Here**: [Component Development Guide](COMPONENT_DEVELOPMENT_GUIDE.md)
   - Comprehensive guide covering all aspects of component development
   - Step-by-step instructions for creating new components
   - Code examples and templates

2. **Understand the Architecture**: [Architecture Overview](ARCHITECTURE_OVERVIEW.md)
   - System architecture and design patterns
   - Component lifecycle and data flow
   - Module structure and build process

3. **Quick Reference**: [Quick Reference](QUICK_REFERENCE.md)
   - Templates and code snippets
   - Common patterns and configurations
   - Quick lookup for common tasks

---

## Documentation Files

### Main Documentation

| Document | Purpose | Audience |
|----------|---------|----------|
| [Component Development Guide](COMPONENT_DEVELOPMENT_GUIDE.md) | Comprehensive guide for component development | Developers creating new components |
| [Architecture Overview](ARCHITECTURE_OVERVIEW.md) | System architecture and design patterns | Architects, senior developers |
| [Quick Reference](QUICK_REFERENCE.md) | Quick lookup for templates and patterns | All developers |
| [Component Specification Template](COMPONENT_SPECIFICATION_TEMPLATE.md) | Template for component specifications | Product managers, developers |

### Existing Documentation

| Document | Purpose | Audience |
|----------|---------|----------|
| [README.md](README.md) | Project overview and setup | All users |
| [Guidelines.md](Guidelines.md) | Component checklist and best practices | All developers |
| [CONTRIBUTING.md](CONTRIBUTING.md) | Contribution guidelines | Contributors |
| [VERSIONS.md](VERSIONS.md) | Version history and requirements | All users |

---

## Documentation by Use Case

### I want to...

#### Create a New Component
1. Read: [Component Development Guide](COMPONENT_DEVELOPMENT_GUIDE.md) - "Creating a New Component" section
2. Use: [Component Specification Template](COMPONENT_SPECIFICATION_TEMPLATE.md) to create your spec
3. Reference: [Quick Reference](QUICK_REFERENCE.md) for code templates
4. Follow: [Guidelines.md](Guidelines.md) for best practices

#### Understand the Architecture
1. Read: [Architecture Overview](ARCHITECTURE_OVERVIEW.md)
2. Review: [Component Development Guide](COMPONENT_DEVELOPMENT_GUIDE.md) - "Component Architecture" section

#### Customize an Existing Component
1. Read: [Component Development Guide](COMPONENT_DEVELOPMENT_GUIDE.md) - "Extension Points" section
2. Review: Examples in `examples/` directory
3. Reference: [Quick Reference](QUICK_REFERENCE.md) for patterns

#### Write Backend Code (Java)
1. Read: [Component Development Guide](COMPONENT_DEVELOPMENT_GUIDE.md) - "Backend Development (Java)" section
2. Reference: [Quick Reference](QUICK_REFERENCE.md) - "Java Sling Model Template"
3. Study: Existing implementations in `bundles/af-core/src/main/java/`

#### Write Frontend Code (HTL/JavaScript)
1. Read: [Component Development Guide](COMPONENT_DEVELOPMENT_GUIDE.md) - "Frontend Development" section
2. Reference: [Quick Reference](QUICK_REFERENCE.md) - "HTL Template Template" and "JavaScript Runtime Template"
3. Study: Existing components in `ui.af.apps/.../components/form/`

#### Configure Component Dialogs
1. Read: [Component Development Guide](COMPONENT_DEVELOPMENT_GUIDE.md) - "Component Configuration" section
2. Reference: [Quick Reference](QUICK_REFERENCE.md) - "Edit Dialog Template" and "Common Dialog Fields"
3. Study: Existing dialogs in component `_cq_dialog/` directories

#### Set Up Client Libraries
1. Read: [Component Development Guide](COMPONENT_DEVELOPMENT_GUIDE.md) - "Client Libraries" section
2. Reference: [Quick Reference](QUICK_REFERENCE.md) - "Client Library Configuration"
3. Review: [Architecture Overview](ARCHITECTURE_OVERVIEW.md) - "Client Library System" section

#### Write Tests
1. Read: [Component Development Guide](COMPONENT_DEVELOPMENT_GUIDE.md) - "Testing" section
2. Reference: [Quick Reference](QUICK_REFERENCE.md) - "Testing Templates"
3. Study: Existing tests in `bundles/af-core/src/test/java/` and `ui.tests/`

#### Understand Component Lifecycle
1. Read: [Architecture Overview](ARCHITECTURE_OVERVIEW.md) - "Component Lifecycle" section
2. Review: [Component Development Guide](COMPONENT_DEVELOPMENT_GUIDE.md) - "Component Architecture" section

#### Build and Deploy
1. Read: [README.md](README.md) - "Building" section
2. Review: [Architecture Overview](ARCHITECTURE_OVERVIEW.md) - "Build and Deployment" section
3. Reference: [Quick Reference](QUICK_REFERENCE.md) - "Maven Build Commands"

---

## Documentation Structure

### Component Development Guide
- Overview and repository structure
- Component architecture
- Step-by-step component creation
- Backend development (Java)
- Frontend development (HTL/JavaScript)
- Component configuration
- Client libraries
- Testing
- Best practices
- Component specification template

### Architecture Overview
- System architecture
- Component lifecycle
- Data flow
- Key components and interfaces
- Module structure
- Build and deployment
- Component registration
- Client library system
- Form container integration
- Extension points
- Performance considerations
- Security considerations

### Quick Reference
- Component file structure
- Java Sling Model templates
- HTL template templates
- JavaScript runtime templates
- Edit dialog templates
- Common dialog fields
- Client library configuration
- Component definition files
- Common reserved properties
- BEM naming convention
- Required data attributes
- Common HTL use-objects
- Maven build commands
- Key file locations
- Testing templates
- Common patterns
- Useful constants
- Checklist for new component

### Component Specification Template
- Component overview
- Technical specifications
- Properties (required and optional)
- Constraints
- Dialog configuration
- Client libraries
- JavaScript runtime
- HTML structure
- Dependencies
- Accessibility
- Internationalization
- Performance considerations
- Security considerations
- Testing requirements
- Documentation requirements
- Examples
- Migration notes
- Known issues and limitations

---

## Key Concepts

### Component Types
- **Field Components**: Input fields (TextInput, NumberInput, etc.)
- **Container Components**: Containers for other components (Container, Panel, etc.)
- **Button Components**: Action buttons (Submit, Reset, etc.)

### Base Classes
- **AbstractFieldImpl**: Base for all field components
- **AbstractContainerImpl**: Base for container components
- **AbstractBaseImpl**: Base for all form components

### Technologies
- **Backend**: Java, OSGi, Sling Models
- **Frontend**: HTL (Sightly), JavaScript (ES6+), CSS
- **Build**: Maven

### Key Directories
- `bundles/af-core/`: Java/Sling Model code
- `ui.af.apps/`: Component definitions (HTL, dialogs, clientlibs)
- `ui.frontend/`: Frontend JavaScript/CSS source
- `examples/`: Example proxy components

---

## Examples and References

### Example Components to Study

1. **Text Input** (`ui.af.apps/.../form/textinput/v1/textinput/`)
   - Simple field component
   - Good starting point for field components

2. **Button** (`ui.af.apps/.../form/button/v1/button/`)
   - Action component
   - Good example for button components

3. **Container** (`ui.af.apps/.../form/container/v2/container/`)
   - Container component
   - Good example for container components

4. **Base Component** (`ui.af.apps/.../form/base/v1/base/`)
   - Base component with common functionality
   - Reference for shared patterns

### Key Files to Reference

- **AbstractFieldImpl**: `bundles/af-core/.../util/AbstractFieldImpl.java`
- **FormConstants**: `bundles/af-core/.../form/FormConstants.java`
- **ReservedProperties**: `bundles/af-core/.../form/ReservedProperties.java`
- **FormFieldBase**: `ui.frontend/src/view/FormFieldBase.js`

---

## Best Practices Checklist

When creating a new component, ensure:

- [ ] Component follows single responsibility principle
- [ ] Code follows existing naming conventions
- [ ] Component is properly documented (README.md)
- [ ] Unit tests are written and passing
- [ ] Integration tests are written and passing
- [ ] UI tests are written (if applicable)
- [ ] Component follows BEM naming convention
- [ ] Accessibility requirements are met
- [ ] Internationalization is implemented
- [ ] Security best practices are followed
- [ ] Performance is optimized
- [ ] Component checklist from Guidelines.md is followed

---

## Getting Help

### Documentation Issues
If you find issues with the documentation or have suggestions for improvement:
1. Review existing documentation first
2. Check if your question is answered in the documentation
3. Create an issue or pull request with improvements

### Development Questions
For questions about component development:
1. Start with the [Component Development Guide](COMPONENT_DEVELOPMENT_GUIDE.md)
2. Review the [Quick Reference](QUICK_REFERENCE.md)
3. Study existing component implementations
4. Check the [Architecture Overview](ARCHITECTURE_OVERVIEW.md) for system-level questions

### Contribution
See [CONTRIBUTING.md](CONTRIBUTING.md) for guidelines on contributing to the project.

---

## Version Information

This documentation is current as of:
- **Repository Version**: 3.0.135-SNAPSHOT
- **Documentation Version**: 1.0

For version-specific information, see [VERSIONS.md](VERSIONS.md).

---

## Additional Resources

### External Documentation
- [AEM Sites Core Components](https://github.com/adobe/aem-core-wcm-components)
- [AEM Forms Documentation](https://experienceleague.adobe.com/docs/experience-manager-forms.html)
- [HTL Documentation](https://experienceleague.adobe.com/docs/experience-manager-htl/content/overview.html)
- [Sling Models Documentation](https://sling.apache.org/documentation/bundles/models.html)

### JavaScript Documentation
- [AEM Forms Core Components JS Docs](https://opensource.adobe.com/aem-core-forms-components/)

---

## Summary

This documentation suite provides comprehensive coverage of:
- Component development process
- System architecture and design
- Code templates and patterns
- Best practices and guidelines
- Component specification process

Use this index to navigate to the documentation you need based on your specific task or question.

