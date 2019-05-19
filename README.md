# Proyecto_DPOO

Consideracions:

- Esmentar que si es crea una nova taula, plat o configuració, amb un nom o id que ja existeix a la base de dades, el objecte ja existent a la base de dades s'actualitza, és a dir, no es crea un nou objecte duplicat.
- Per mostrar la contrassenya entrada ha d'estar actiu, així doncs si entrada no esta actiu, la contrassenya no es mostrara.

Instruccions per utilitzar el nostre programa:

- Crear la base de dades amb l'arxiu .sql aquest esta fet amb el gestor mySQL. 
- Editar el config.json per utilitzar una base de dades SQL amb la que desar les dades del model, usuari, contrasenya, port des del que es conectara, tambe editar els ports pels cuals conectarse a les aplicacions externes
- editar els 2 fitxer config.json de entrada i taula per conectar-se al servidor.
- el programa s'haria de poder executar correctament, si dona algun problema podria ser de llibreries, en cas de que el maven no funciones (cas molt extrany), demanariem que s'instalecin les llibreries manualment (gson)
- recordar que en el servidor t'has de crear un compte "d'usuari" per poder accedir al pre servei, servei i post servei

