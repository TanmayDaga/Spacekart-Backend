package net.in.spacekart.backend.payloads.space;

import net.in.spacekart.backend.database.entities.Address;
import net.in.spacekart.backend.database.entities.Price;
import net.in.spacekart.backend.database.entities.SpaceType;

public class SpaceDto {

    private SpaceType type;

    private Price price;

    
    private Address address;

    private String features;

    
}