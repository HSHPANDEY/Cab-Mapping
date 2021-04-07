
class DriverComparator implements Comparator<Driver> {
  public int compare(Driver d1, Driver d2) {
    return d1.avgRating - d2.avgRating;
  }
}