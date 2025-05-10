package com.NikhilProject.request;

import com.NikhilProject.Model.Address;
import lombok.Data;

import java.util.List;

@Data
public class OrderRequest {
    Long restaurantId;
    Address deliveryAddress;
}
