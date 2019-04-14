package game.data.analyzer.web;

import game.data.analyzer.data.LocationRepository;
import game.data.analyzer.model.Item;
import game.data.analyzer.model.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class LocationService {

    @Autowired
    private LocationRepository locationRepository;

    public LocationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public Location get(long id) {
        Optional<Location> location = locationRepository.findById(id);
        if (!location.isPresent()) throw new ItemDoesntExist();
        else return location.get();
    }

    public List<Location> findAll() {
        return locationRepository.findAll();
    }

    public List<Location> findAllEager() { return locationRepository.findAllEager(); }

    public void save(Location location) {
        locationRepository.save(location);
    }

    public List<Location> retrieveByName(String name) {
        if (name == null || name.equals("")) return findAll();
        return locationRepository.findByNameContainingIgnoreCase(name);
    }

    public List<Location> retrieveByNameEager(String name) {
        if (name == null || name.equals("")) return locationRepository.findAllEager();
        return locationRepository.findByNameContainingIgnoreCaseEager(name);
    }
}
