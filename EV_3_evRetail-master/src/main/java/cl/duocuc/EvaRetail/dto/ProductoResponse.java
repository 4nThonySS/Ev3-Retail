package cl.duocuc.EvaRetail.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductoResponse {

    private Long id;
    private String nombre;
    private Double precio;
    private Integer stock;
}
