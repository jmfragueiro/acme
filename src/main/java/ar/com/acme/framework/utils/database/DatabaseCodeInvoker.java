package ar.gov.posadas.mbe.framework.utils.database;

import org.springframework.stereotype.Component;

import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class DatabaseCodeInvoker {
    @PersistenceContext
    private final EntityManager entityManager;

    /**
     * Este metodo ejecuta un procedimiento almacenado en la base de datos
     * y devuelve los valores de salida obtenidos desde los parametros OUT.
     *
     * @param procedure el procedimiento a ejecutar
     * @param params los parametros a utilizar (de entrada y salida)
     * @return una lista con los valores de salida obtenidos
     */
    public List<?> executeDatabaseProcedure(String procedure, Set<DatabaseParam<?>> params) {
        StoredProcedureQuery spq = entityManager.createStoredProcedureQuery(procedure);

        // Registrar los parámetros de entrada y salida
        params.forEach(p -> spq.registerStoredProcedureParameter(p.nombre(), p.clase(), p.modo()));

        // Configuramos el valor de entrada
        params.stream()
              .filter(p -> p.modo().equals(ParameterMode.IN))
              .forEach(p -> spq.setParameter(p.nombre(), p.valor()));

        // Realizamos la llamada al procedimiento
        spq.execute();

        // Obtenemos y retornamos los valores de salida
        return params.stream()
                     .filter(p -> p.modo().equals(ParameterMode.OUT))
                     .map(p -> spq.getOutputParameterValue(p.nombre()))
                     .collect(Collectors.toList());
    }

    /**
     * La llamada al procedimiento devuelve el id del registro insertado en mp_caja.preimputado
     * Cualquier numero menor a 1 se lo debe considerar como que el proceso no fue exitoso
    */
    @SuppressWarnings("unchecked")
    public Integer preimputarPago(Long boletaId, String usuario) {
        try {
            var p1 = new DatabaseParam<>("P_COMPROBANTE", Integer.class, ParameterMode.IN, boletaId.intValue());
            var p2 = new DatabaseParam<>("P_USER", String.class, ParameterMode.IN, usuario);
            var p3 = new DatabaseParam<>("RESULT", Integer.class, ParameterMode.OUT, null);

            Set<DatabaseParam<?>> params = Set.of(p1, p2, p3);

            return ((List<Integer>)this.executeDatabaseProcedure("PK_PAGOS.PREIMPUTAR_PAGO", params)).get(0);
        } catch (Exception e) {
            /**
             * Ante cualquier error retorno 0
             * Cada llamada a este método deberá contemplar la posibilidad
             * de recibir un 0 como respuesta y saber que hacer al respecto
             */
            return 0;
        }
    }
}