package lombok;

import lombok.extern.java.Log;

@Log
class Main {
  static public void  main(String[] args) {
    log.info("App start");
    Person p = new Person();
    p.getFirst();
    p.setAge(10);
  }
}