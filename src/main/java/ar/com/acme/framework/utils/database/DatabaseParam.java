package ar.gov.posadas.mbe.framework.utils.database;

import jakarta.persistence.ParameterMode;

public record DatabaseParam<T>(String nombre, Class<T> clase, ParameterMode modo, T valor) { }

