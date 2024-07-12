INSERT INTO Rutina (idRutina, nombre, objetivo)
SELECT 1, 'Rutina de Hipertrofia', 'GANANCIA_MUSCULAR'
    WHERE NOT EXISTS (SELECT 1 FROM Rutina WHERE idRutina = 1);

INSERT INTO Rutina (idRutina, nombre, objetivo)
SELECT 2, 'Rutina para Quemar Grasa', 'PERDIDA_DE_PESO'
    WHERE NOT EXISTS (SELECT 1 FROM Rutina WHERE idRutina = 2);

INSERT INTO Rutina (idRutina, nombre, objetivo)
SELECT 3, 'Rutina de Definición Muscular', 'DEFINICION'
    WHERE NOT EXISTS (SELECT 1 FROM Rutina WHERE idRutina = 3);

INSERT INTO Rutina (idRutina, nombre, objetivo)
SELECT 4, 'Rutina de Fuerza Explosiva', 'GANANCIA_MUSCULAR'
    WHERE NOT EXISTS (SELECT 1 FROM Rutina WHERE idRutina = 4);

INSERT INTO Rutina (idRutina, nombre, objetivo)
SELECT 5, 'Rutina de Resistencia Cardiovascular', 'PERDIDA_DE_PESO'
    WHERE NOT EXISTS (SELECT 1 FROM Rutina WHERE idRutina = 5);

INSERT INTO Rutina (idRutina, nombre, objetivo)
SELECT 6, 'Rutina de musculación', 'GANANCIA_MUSCULAR'
    WHERE NOT EXISTS (SELECT 1 FROM Rutina WHERE idRutina = 6);

INSERT INTO Rutina (idRutina, nombre, objetivo)
SELECT 7, 'Rutina para Hipopresión', 'DEFINICION'
    WHERE NOT EXISTS (SELECT 1 FROM Rutina WHERE idRutina = 7);

INSERT INTO Rutina (idRutina, nombre, objetivo)
SELECT 8, 'Rutina de Yoga', 'PERDIDA_DE_PESO'
    WHERE NOT EXISTS (SELECT 1 FROM Rutina WHERE idRutina = 8);

INSERT INTO Rutina (idRutina, nombre, objetivo)
SELECT 9, 'Rutina de Pilates para Fortalecer', 'GANANCIA_MUSCULAR'
    WHERE NOT EXISTS (SELECT 1 FROM Rutina WHERE idRutina = 9);

INSERT INTO Rutina (idRutina, nombre, objetivo)
SELECT 10, 'Rutina de Entrenamiento Funcional', 'DEFINICION'
    WHERE NOT EXISTS (SELECT 1 FROM Rutina WHERE idRutina = 10);
