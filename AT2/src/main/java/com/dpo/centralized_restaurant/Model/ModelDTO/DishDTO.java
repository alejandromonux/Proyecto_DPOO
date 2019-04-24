package com.dpo.centralized_restaurant.Model.ModelDTO;

import java.io.Serializable;
import java.time.LocalTime;

public class DishDTO implements Serializable {
    private String name;
    private int units;
    private int timecost;               // En segundos
    private long id_mesa;
    private LocalTime fecha_pedido;
    private LocalTime fecha_iniciado;
}
