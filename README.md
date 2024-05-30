# Pokemon
Importar como proyecto de Gradle
A [libGDX](https://libgdx.com/) project generated with [gdx-liftoff](https://github.com/tommyettinger/gdx-liftoff).
This project was generated with a template including simple application launchers and an ApplicationAdapter extension that draws libGDX logo.

## Platforms
- core: Main module with the application logic shared by all platforms.
- lwjgl3: Primary desktop platform using LWJGL3.

## Gradle
This project uses [Gradle](http://gradle.org/) to manage dependencies.
The Gradle wrapper was included, so you can run Gradle tasks using gradlew.bat or ./gradlew commands.
Useful Gradle tasks and flags:

- --continue: when using this flag, errors will not stop the tasks from running.
- --daemon: thanks to this flag, Gradle daemon will be used to run chosen task# Pokemon

## En caso de no funcionar, pruebe cualquiera de estas dos opciones
(Primera opcion)
Ve a las "Preferencias" en Eclipse (en el menú "Eclipse" en macOS o "Window" en Windows/Linux).
Busca la configuración "Java" o "Java > Installed JREs".
Selecciona la versión de Java que estás utilizando en tu proyecto.
Haz clic en "Edit...".
En la ventana emergente, agrega -XstartOnFirstThread en el campo "Default VM arguments".
Haz clic en "Finish" para guardar los cambios.

(Segunda opcion)
Haz clic derecho en tu proyecto en la pestaña "Package Explorer" o "Project Explorer".
Selecciona "Run As" o "Debug As", dependiendo de si quieres ejecutar o depurar tu aplicación.
Selecciona "Run Configurations" o "Debug Configurations".
En la ventana que aparece, busca tu configuración de ejecución actual en la lista de configuraciones.
Haz clic en tu configuración para editarla.
Ve a la pestaña "Arguments" (o "Argumentos" si tu Eclipse está en español).
En el campo "VM arguments" (o "Argumentos de la VM"), añade -XstartOnFirstThread al principio o al final de la lista, separado por espacios, dependiendo de si hay otros argumentos allí.
Haz clic en "Apply" (o "Aplicar") y luego en "Run" (o "Debug") para ejecutar o depurar tu aplicación con la nueva configuración.
