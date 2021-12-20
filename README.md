# SofkaChallenge
Notas preliminares:

El reto lo realicé en Android Studio utilizando java. La pantalla que utilicé para el emulador es un Pixel 3a y la API es la 30. La base de datos se montó en sqlite, por lo que para acceder a la bd se necesita un programa que pueda abrir este tipo de datos, como db browser, para descargar la bd desde el android studio se sigue la ruta de View- > Tools Windows -> Device File Explorer -> /data/data/com.example.sofkachallenger/databases/SofkaChallenger.db , click derecho
Save as

El juego consta de 3 categorías, "cultura general", "matemáticas" e " historia. Estas 3 categorías son lineales, lo cual quiere decir, que siempre se seguirá este orden en 
las categorías mencionadas. Sin embargo, para cumplir de alguna manera con la funcionalidad que se pidió, traté de que por ejemplo, en a partir de la categoría de matemáticas,
se vaya subiendo la dificultad. Cada categoría consta de 5 preguntas con  4 opciones de respuesta

Las preguntas se aleatorizan en cada categoría y al final del juego te da un puntaje dependiendo de las respuestas buenas y malas .La persistencia de datos la realicé a través de
la clase Shared Preferences,  y lo utilicé para guardar el usuario del login, y que al salirse de la app, te guarde las respuestas buenas, malas y el puntaje.

Debido al tiempo, la funcionalidad de los premios, en este caso , el puntaje, lo mostré en un alertdialog que de muestra el puntaje total obtenido al final del juego. No obstante, no queda registrado en un histórico de puntajes.

De igual manera, traté de comentar casi todo el código para explicar con más detalle qué hace cada línea.

Decidí agregarle un splash art, login y una sencilla pero agradable UI como valor agregado al reto. En el login, si se clickea la opción de "Remember credentials", te guardará la sesión y el nombre digitado en el login.  Además, realicé un breve mockup al inicio de todo el reto, el cual diferió bastante de la propuesta final pero también quise añadirlo. 
Debajo dejo el link con pantallazos del mockup

![image](https://user-images.githubusercontent.com/85410941/146843687-15934d8c-3124-43f3-a9a3-66704bae5007.png)
