INSERT INTO rutina_ejercicio (Rutina_idRutina, ejercicios_idEjercicio)
SELECT * FROM (
                  SELECT 1, 1 UNION ALL
                  SELECT 1, 2 UNION ALL
                  SELECT 1, 3 UNION ALL
                  SELECT 2, 4 UNION ALL
                  SELECT 2, 5 UNION ALL
                  SELECT 2, 6 UNION ALL
                  SELECT 3, 2 UNION ALL
                  SELECT 3, 3 UNION ALL
                  SELECT 3, 4 UNION ALL
                  SELECT 4, 1 UNION ALL
                  SELECT 4, 6 UNION ALL
                  SELECT 4, 4 UNION ALL
                  SELECT 5, 5 UNION ALL
                  SELECT 5, 6 UNION ALL
                  SELECT 5, 4 UNION ALL
                  SELECT 6, 1 UNION ALL
                  SELECT 6, 2 UNION ALL
                  SELECT 6, 3 UNION ALL
                  SELECT 6, 5 UNION ALL
                  SELECT 10, 4 UNION ALL
                  SELECT 10, 1 UNION ALL
                  SELECT 3, 17 UNION ALL
                  SELECT 3, 18 UNION ALL
                  SELECT 3, 19 UNION ALL
                  SELECT 3, 20 UNION ALL
                  SELECT 2, 12 UNION ALL
                  SELECT 2, 13 UNION ALL
                  SELECT 7, 22 UNION ALL
                  SELECT 7, 23 UNION ALL
                  SELECT 7, 24 UNION ALL
                  SELECT 7, 19 UNION ALL
                  SELECT 10, 21 UNION ALL
                  SELECT 10, 22 UNION ALL
                  SELECT 4, 24 UNION ALL
                  SELECT 9, 1 UNION ALL
                  SELECT 9, 22 UNION ALL
                  SELECT 9, 20 UNION ALL
                  SELECT 5, 15 UNION ALL
                  SELECT 5, 14 UNION ALL
                  SELECT 8, 8 UNION ALL
                  SELECT 8, 12 UNION ALL
                  SELECT 8, 13
              ) AS tmp
WHERE NOT EXISTS (
    SELECT 1
    FROM rutina_ejercicio
    WHERE rutina_ejercicio.Rutina_idRutina = tmp.column1
      AND rutina_ejercicio.ejercicios_idEjercicio = tmp.column2
);