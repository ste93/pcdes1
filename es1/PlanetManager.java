package es1;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;

public class PlanetManager {
    es1.SynchronizationManager synchronizationManager;
    private ArrayList<es1.Planet> planetsList;
    public final static double G = 6.67408 * 10E-11;

    public PlanetManager(es1.SynchronizationManager synchronizationManager) {
        planetsList = new ArrayList<>();
        for (int i = 0; i < es1.Constants.PLANET_NUMBER; i++) {
            planetsList.add(
                    new es1.Planet(
                            (int) (Math.random() * es1.Constants.DRAWING_PANEL_SIZE_X),
                            (int) (Math.random() * es1.Constants.DRAWING_PANEL_SIZE_Y))
            );
        }
        this.synchronizationManager = synchronizationManager;
    }

    public ArrayList<es1.Planet> getPlanetsList() {
        return this.planetsList;
    }


    public void updatePlanetsAcceleration(int planet1Number, int planet2Number) {
        this.synchronizationManager.acquireAcceleration(planet1Number);
        this.synchronizationManager.acquireAcceleration(planet2Number);
        es1.Planet planetA = this.planetsList.get(planet1Number);
        es1.Planet planetB = this.planetsList.get(planet2Number);
        double deltaX = planetA.getPositionX() - planetB.getPositionX();
        double deltaY = planetA.getPositionY() - planetB.getPositionY();
        double lim = 5;
        if (deltaX > 0 && deltaX < lim) {
            deltaX = lim;
        }
        if (deltaY > 0 && deltaY < lim) {
            deltaY = lim;
        }
        if (deltaX < 0 && deltaX > -lim) {
            deltaX = -lim;
        }
        if (deltaY < 0 && deltaY > -lim) {
            deltaY = -lim;
        }
        double squaredDeltax = deltaX * deltaX;
        double squaredDeltay = deltaY * deltaY;
        double forceOverMassSquared = G / (squaredDeltay + squaredDeltax);
        double accelerationA = forceOverMassSquared * planetB.getMass();
        double accelerationB = forceOverMassSquared * planetA.getMass();
        if (deltaY != 0) {
            double var1 = Math.sqrt(1 / (1 + squaredDeltax / squaredDeltay));
            double var2 = Math.abs(deltaX / deltaY);
            planetA.updateAccelerationX(accelerationA * var1 * var2 * (deltaX > 0 ? -1 : 1));
            planetA.updateAccelerationY(accelerationA * var1 * (deltaY > 0 ? -1 : 1));
            planetB.updateAccelerationX(accelerationB * var1 * var2 * (deltaX < 0 ? -1 : 1));
            planetB.updateAccelerationY(accelerationB * var1 * (deltaY < 0 ? -1 : 1));
        } else {
            planetA.updateAccelerationX(accelerationA * (deltaX > 0 ? -1 : 1));
            planetB.updateAccelerationX(accelerationB * (deltaX < 0 ? -1 : 1));
        }
        this.synchronizationManager.releasePosition(planet1Number);
        this.synchronizationManager.releasePosition(planet2Number);
    }


    private int calculateNewPosition(double initialPosition, BigDecimal acceleration, BigDecimal initialSpeed) {
        //TODO delete
        System.out.println("calculate new position: " + initialPosition + " " + acceleration + " " + initialSpeed);
        BigDecimal newPosition = BigDecimal.valueOf(initialPosition).add(
                BigDecimal.valueOf(0.5).multiply(acceleration.multiply(
                        BigDecimal.valueOf(es1.Constants.DELTA_TIME).pow(2), MathContext.DECIMAL32), MathContext.DECIMAL32).add(
                        initialSpeed.multiply(BigDecimal.valueOf(es1.Constants.DELTA_TIME), MathContext.DECIMAL32)));
        return newPosition.intValue();
    }

    private BigDecimal calculateNewSpeed(BigDecimal acceleration, BigDecimal initialSpeed) {
        return acceleration.multiply(BigDecimal.valueOf(es1.Constants.DELTA_TIME), MathContext.DECIMAL32)
                .add(initialSpeed);
    }

    public void updatePlanetPositionAndSpeed(int planetNumber) {
        this.synchronizationManager.acquirePosition(planetNumber);
        es1.Planet planet = this.planetsList.get(planetNumber);

        //newPositionX
        int newPositionX = calculateNewPosition(
                planet.getPositionX(),
                planet.getAccelerationX(),
                planet.getSpeedX()
        );
        //newPositionY
        int newPositionY = calculateNewPosition(
                planet.getPositionY(),
                planet.getAccelerationY(),
                planet.getSpeedY()
        );
/*
        if (newPositionX > es1.Constants.MAX_POSITION_X) {
            if (planet.getAccelerationX().compareTo(BigDecimal.ZERO) > 0)
                planet.invertAccelerationX();
            if (planet.getSpeedX().compareTo(BigDecimal.ZERO) > 0)
                planet.setSpeedX(planet.getSpeedX().negate());
            newPositionX = calculateNewPosition(planet.getPositionX(),
                    planet.getAccelerationX(),
                    planet.getSpeedX());
        } else if (newPositionX < 1) {
            if (planet.getAccelerationX().compareTo(BigDecimal.ZERO) < 0)
                planet.invertAccelerationX();
            if (planet.getSpeedX().compareTo(BigDecimal.ZERO) < 0)
                planet.setSpeedX(planet.getSpeedX().negate());
            newPositionX = calculateNewPosition(planet.getPositionX(),
                    planet.getAccelerationX(),
                    planet.getSpeedX());
        }
        if (newPositionY > es1.Constants.MAX_POSITION_Y) {
            if (planet.getAccelerationY().compareTo(BigDecimal.ZERO) > 0)
                planet.invertAccelerationY();
            if (planet.getSpeedY().compareTo(BigDecimal.ZERO) > 0)
                planet.setSpeedY(planet.getSpeedY().negate());
            newPositionY = calculateNewPosition(planet.getPositionY(),
                    planet.getAccelerationY(),
                    planet.getSpeedY());

        } else if (newPositionY < 1) {
            if (planet.getAccelerationY().compareTo(BigDecimal.ZERO) < 0)
                planet.invertAccelerationY();
            if (planet.getSpeedY().compareTo(BigDecimal.ZERO) < 0)
                planet.setSpeedY(planet.getSpeedY().negate());
            newPositionY = calculateNewPosition(planet.getPositionY(),
                    planet.getAccelerationY(),
                    planet.getSpeedY());
        }
*/
        this.checkDistances(newPositionX, newPositionY, planetNumber, planet);

        this.synchronizationManager.releaseWorker();
        this.synchronizationManager.releaseGraphicLock();
    }


    private void checkDistances(
            int newPositionX,
            int newPositionY,
            int planetNumber,
            es1.Planet planet
    ) {
        this.synchronizationManager.getLock();
        boolean updatePositions = true;
        ArrayList<Integer> updatedPlanets = this.synchronizationManager.getUpdatedPlanetList();
        //updatedPlanets.forEach((x) -> {System.out.println(x);});
        for (Integer i : updatedPlanets) {
            double distance = this.calculateDistance(newPositionX, newPositionY, i);
            if (distance < es1.Constants.MIN_DISTANCE_BETWEEN_PLANETS) {
                updatePositions = false;
                break;
            }
        }
        if (updatePositions) {
            planet.setPositionX(newPositionX);
            planet.setSpeedX(calculateNewSpeed(planet.getSpeedX(), planet.getAccelerationX()));
            planet.setPositionY(newPositionY);
            planet.setSpeedY(calculateNewSpeed(planet.getSpeedY(), planet.getAccelerationY()));
        }
        this.synchronizationManager.addPlanetToUpdatedList(planetNumber);
        if (updatedPlanets.size() == es1.Constants.PLANET_NUMBER) {
            this.synchronizationManager.resetUpdatedPlanetList();
        }
        this.synchronizationManager.releaseLock();
    }


    private double calculateDistance(int newPositionX, int newPositionY, int planetNumber) {
        double distance = Math.sqrt(
                Math.pow(this.planetsList.get(planetNumber).getPositionX() - newPositionX, 2) +
                        Math.pow(this.planetsList.get(planetNumber).getPositionY() - newPositionY, 2)
        );
        return distance;

    }
}

