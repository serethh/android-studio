# Hospital App (Android + Java + SQLite)

Proyecto de gestión médica/hospitalaria para Android Studio, con base de
datos local SQLite (`SQLiteOpenHelper`) y comunicación entre Activities
y Fragments mediante Intents/Bundles.

## Cómo abrir el proyecto

1. Abre Android Studio → **Open** → selecciona la carpeta `HospitalApp`.
2. Deja que Gradle sincronice (descargará las dependencias de Google/Maven).
   - Si Android Studio pide regenerar el Gradle Wrapper (`gradlew`), acepta:
     el proyecto no incluye el binario `gradle-wrapper.jar` (no hay acceso a
     internet fuera de este entorno para descargarlo), pero Android Studio lo
     genera automáticamente al sincronizar.
3. Ejecuta en un emulador o dispositivo con **API 23+** (Android 6.0+).

## Usuario de prueba (Login)

| Usuario   | Password | Rol            |
|-----------|----------|----------------|
| admin     | admin123 | Administrador  |
| doctor1   | doc123   | Medico         |
| recepcion | rec123   | Recepcionista  |

## Estructura de la base de datos (`hospital.db`)

- **Usuarios**: id, usuario, password, rol
- **Medicos**: id_medico, nombre, especialidad, cedula
- **Pacientes**: id_paciente, nombre, edad, diagnostico
- **Consultas**: id_consulta, id_paciente, **id_medico**, fecha_hora_atencion,
  hora_salida, diagnostico_consulta

> Nota: se agregó `id_medico` a `Consultas` (no estaba explícito en el
> enunciado original) porque sin esa llave foránea no es posible hacer el
> JOIN Pacientes–Consultas–Medicos que pide la pantalla de "Lista de
> Pacientes" para mostrar qué doctor atendió a cada paciente.

Las 4 tablas se cargan con registros de prueba automáticamente la primera
vez que se ejecuta la app (ver `cargarDatosDePrueba()` en `DatabaseHelper.java`).

## Activities incluidas (7 en total, mínimo pedido: 5)

1. **LoginActivity** — valida usuario/contraseña contra `Usuarios`.
2. **MainMenuActivity** — menú de navegación tras el login exitoso.
3. **WifiActivity** — permiso dinámico de ubicación + `WifiManager` para
   mostrar SSID, BSSID, velocidad de enlace e IP.
4. **LocationActivity** — `FusedLocationProviderClient` con permiso
   dinámico de `ACCESS_FINE_LOCATION`; muestra latitud/longitud en
   tiempo real.
5. **PacienteConsultaActivity** + **ConsultaFragment** — guarda un
   paciente y, en la misma pantalla, el fragment permite registrar la
   consulta (doctor, diagnóstico de consulta y hora de salida) asociada
   a ese paciente recién creado.
6. **ListaPacientesActivity** — `RecyclerView` con el resultado de un
   JOIN de 3 tablas (Pacientes + Consultas + Medicos).
7. **AdminBDActivity** — `Spinner` con las 4 tablas + `TableLayout`
   dinámico que muestra todas las filas/columnas de la tabla elegida.

## Permisos declarados (AndroidManifest.xml)

- `INTERNET`
- `ACCESS_WIFI_STATE`, `CHANGE_WIFI_STATE`, `ACCESS_NETWORK_STATE`
- `ACCESS_FINE_LOCATION`, `ACCESS_COARSE_LOCATION`

## Sobre las capturas de NetBeans

Las capturas que compartiste (Ingreso / Registro / Egreso / Vista) se usaron
como referencia de estilo (encabezados en azul, barra de navegación superior,
botón verde "Guardar") para el diseño visual de las pantallas Android, ya
que la estructura técnica exacta (tablas, Activities y Fragment) la define
tu especificación detallada, que es más específica para el entorno móvil.
