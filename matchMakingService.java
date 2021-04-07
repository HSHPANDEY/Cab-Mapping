
class MatchMakingService {
  PriorityQueue<Driver> driversByRating;
  HashMap<String, Rider> ridersMap;
  MatchMakingService() {
    Riders rd = new Riders();
    HashMap<String, Rider> ridersMap = rd.getAllRiders();
    driversByRating = new PriorityQueue<>(new DriverComparator());
    Drivers d = new Drivers();
    for(Driver dr: d.getAllDrivers()) {
      driversByRating.add(dr);
    };
  }

  Driver getDriver(String riderId) {
    Driver d;
    if (driversByRating.size() == 0) {
      throw "No drivers there";
    };
    if (!ridersMap.contains(riderId)) {
      d = driversByRating.remove();
    };
    else {
      Driver dtemp = driversByRating.remove();
      Rider rd = ridersMap.get(riderId);
      ArrayList<Driver> removedDriver = new ArrayList<>();
        removedDriver.add(dtemp);

      while(dtemp!=null && rd.blacklistedDrivers.contains(dtemp)) {
        dtemp = driversByRating.remove();
        removedDriver.add(dtemp);
      }

      if (dtemp == null) {
        throw "All drivers all blackListed";
      };

      addAllRemovedDrivers(removedDriver);
    }
  }

  void UpdateEntities(RideEntity re) {
    handleDriverUpdate(re.driverId, re.driverRating);
    handleRiderUpdate(re.riderId, re.riderRating);
  }

  void handleDriverUpdate(String driverId, double rating) {
    // may handlle caase for new drivers

    Driver d = DriversMap.get(driverId);
    driversByRating.remove(d);
    
    d.avgRating = (d.avgRating + rating)/2;
    driversByRating.add(d);
  }

  void handleRiderUpdate(String riderId, double rating) {
    // may handlle caase for new riders

    Rider r = RidersMap.get(riderId);
    r.avgRating = (r.avgRating + rating)/2;
  }
}