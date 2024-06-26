package net.in.spacekart.backend.database.embeddable;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Location {

    private double latitude;

    private double longitude;
}
