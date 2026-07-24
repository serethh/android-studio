# Rediseño visual de HospitalApp

## Alcance
Se conservaron sin cambios las clases Java, la lógica, SQLite, navegación, actividades, fragmentos y los IDs utilizados por `findViewById`. El trabajo se concentró en layouts y recursos visuales.

## Cambios por pantalla
- **Inicio de sesión:** encabezado degradado, identidad hospitalaria, tarjeta elevada, campos Material outlined con iconos y botón principal moderno.
- **Menú principal:** bienvenida jerarquizada, cabecera visual y accesos uniformes con iconografía Material.
- **Wi‑Fi:** panel informativo con encabezado temático y datos agrupados en una tarjeta legible.
- **Ubicación:** cabecera ilustrada, coordenadas separadas en bloques y estado mostrado como chip.
- **Registro de paciente:** formulario Material, tarjeta de información personal, campos redondeados y mejor jerarquía.
- **Consulta médica:** tarjeta independiente, selector estilizado, diagnóstico multilinea, chip de hora y acciones consistentes.
- **Lista de pacientes:** RecyclerView con tarjetas, avatar, jerarquía de datos y fecha destacada.
- **Administrador BD:** selector de tabla dentro de tarjeta y visor desplazable en un contenedor profesional.

## Recursos añadidos
Paleta hospitalaria uniforme, estilos globales, selector de borde para campos, fondos degradados y redondeados, chips, spinner personalizado y vectores para salud, usuario, contraseña, Wi‑Fi, ubicación, pacientes, historial, base de datos, médico, reloj y notas clínicas.

## Validación
- Todos los XML se analizaron correctamente.
- No hay referencias faltantes a drawables, colores, estilos, layouts, strings o mipmaps.
- Todos los IDs usados por las clases Java siguen presentes.
- El ZIP original no incluía `gradlew` ni `gradle-wrapper.jar`, y el entorno de ejecución no dispone de Gradle/Android SDK, por lo que no fue posible ejecutar `assembleDebug` aquí. El proyecto queda preparado para sincronizar y compilar en Android Studio.
