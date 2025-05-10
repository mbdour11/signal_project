# UML Models and Design Documentation

This folder contains UML class diagrams and system design explanations for the CHMS (Cardiovascular Health Monitoring System) developed as part of SE Project Week 2.

## 1. Alert Generation System

### Classes
- `AlertGenerator`
- `Alert`
- `AlertManager`
- `ThresholdRule`
- `Patient`

### Key Relationships
- `AlertGenerator` uses `ThresholdRule` to check incoming data.
- If a rule is triggered, an `Alert` is created and passed to `AlertManager`.
- Each `Patient` has specific rules defined.

### Explanation
The alert generation system monitors patient data and compares it to predefined threshold rules. If a threshold is exceeded, an alert is created containing the patient ID, timestamp, and alert label. The alert is then passed to the `AlertManager`, which handles further processing. This modular design allows additional rule types or alert-handling logic to be added without changing existing components.

---

## 2. Data Storage System

### Classes
- `DataStorage`
- `PatientData`
- `DataRetriever`
- `AccessController`

### Key Relationships
- `DataStorage` maintains a history of patient `PatientData` entries.
- `DataRetriever` allows querying historical records.
- `AccessController` enforces role-based permissions for accessing data.

### Explanation
This system is responsible for managing and accessing patient health data. The `DataStorage` class stores records, while `DataRetriever` provides methods for querying specific entries. Access to sensitive data is controlled through the `AccessController` class to ensure privacy and support proper user authorization.

---

## 3. Patient Identification System

### Classes
- `PatientIdentifier`
- `HospitalPatient`
- `IdentityManager`

### Key Relationships
- `PatientIdentifier` maps internal IDs to hospital records.
- `HospitalPatient` stores real-world patient information.
- `IdentityManager` coordinates lookups and handles mismatches.

### Explanation
The identification system ensures that data produced by the simulation matches real hospital records. `PatientIdentifier` handles the mapping between simulator IDs and hospital patients, while `IdentityManager` handles validation and conflict resolution when mismatches occur.

---

## 4. Data Access Layer

### Classes
- `DataListener` (interface)
- `TCPDataListener`
- `WebSocketDataListener`
- `FileDataListener`
- `DataParser`
- `DataSourceAdapter`

### Key Relationships
- All data listeners implement the `DataListener` interface.
- Listeners receive raw data and pass it to `DataParser`.
- `DataSourceAdapter` standardizes the parsed data and sends it to storage.

### Explanation
This layer separates the system's input sources from the core data processing logic. Whether the data comes from a file, TCP, or WebSocket, the system uses a common interface and parser to handle it uniformly. This approach improves modularity and allows new input types to be added with minimal changes to the rest of the system.

---

## Integration Instructions

1. Place diagram images (e.g., `.drawio` or `.png`) in this `uml_models/` folder.
2. Ensure this file (`README.md`) is stored in the same directory.
3. Update the main `README.md` in the root of the repository to include a reference to this folder:

```markdown
## UML Models
See class diagrams and documentation in [uml_models](./uml_models/)
