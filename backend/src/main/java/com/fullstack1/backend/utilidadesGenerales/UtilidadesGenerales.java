package com.fullstack1.backend.utilidadesGenerales;

import java.math.BigDecimal;

public class UtilidadesGenerales {

    public static boolean valoresDePrecioValidos(BigDecimal precioMin, BigDecimal precioMax) {

        // Los valores son superiores a cero
        if (precioMin.compareTo(BigDecimal.ZERO) <= 0 || precioMax.compareTo(BigDecimal.ZERO) <= 0) {
            return false;
        }

        // Importe mínimo no sea superior al máximo
        if (precioMin.compareTo(precioMax) > 0) {
            return false;
        }

        return true;
    }
}
