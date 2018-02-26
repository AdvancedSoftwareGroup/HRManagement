package net.restapp.restcontroller;

import net.restapp.model.Position;
import net.restapp.servise.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PositionController {
    @Autowired
    PositionService positionService;
    @RequestMapping("positions")
    public List<Position> getAllPositions(){
       return positionService.getAll();
    }

    @GetMapping ("positions/{id}")
    public Position getPosition(@PathVariable long id){
        return positionService.getById(id);
    }

    @PostMapping("positions")
    public void addPosition(@RequestBody Position position){
        positionService.save(position);
    }

    @PutMapping("positions/{id}")
    public void updatePosition(@RequestBody Position position, @PathVariable long id){
        positionService.save(position);
    }
    @DeleteMapping("positions/{id}")
    public void deletePosition(@PathVariable long id){
        positionService.delete(id);
    }

}
