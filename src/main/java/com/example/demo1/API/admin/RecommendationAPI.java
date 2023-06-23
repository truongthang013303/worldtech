package com.example.demo1.API.admin;

import com.example.demo1.dto.NewDTO;
import com.example.demo1.recommender.ItemService;
import com.example.demo1.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("api/red")
@CrossOrigin(origins = "*")
public class RecommendationAPI {
    @Autowired
    private ItemService itemService;
    @Autowired
    private IUserService userService;

    /*@Autowired
    private MySQLRecommender mySQLRecommender;*/

    @GetMapping("/ub")
    public ResponseEntity<?> fillItemsUserBased(@NotNull Long userid){
        if(!checkUserid(userid)){
            return ResponseEntity.badRequest().body("userid must !=null and >0 and <1000");
        }
        return ResponseEntity.ok(itemService.fillItemsUserBased(userid));
    }
    @GetMapping("/ib")
    public ResponseEntity<?> fillItemsItemBased(@NotNull Long userid){
        return ResponseEntity.ok(itemService.fillItemsItemBased(userid));
    }
    public Boolean checkUserid(Long userid){
        if(userid!=null&&userid>0&&userid<=1000){
            return true;
        }
        return false;
    }

    /*@GetMapping("/ra")
    public ResponseEntity<?> getRecommendationsInject(@RequestParam Long userid) throws Exception {
        return ResponseEntity.ok(mySQLRecommender.getRecommendationsInject(userid, 2));
    }
    @GetMapping("/rating/itemids")
    public ResponseEntity<?> getItemIDsFromUser(@RequestParam Long userid) throws Exception {
        return ResponseEntity.ok(mySQLRecommender.getItemIDsFromUser(userid));
    }
    @GetMapping("/rating/simi")
    public ResponseEntity<?> getSimilarity(@RequestParam Long id1, @RequestParam Long id2) throws Exception {
        return ResponseEntity.ok(mySQLRecommender.getSimilarity(id1, id2));
    }
    @GetMapping("/rating/nei")
    public ResponseEntity<?> getNeighbors(@RequestParam Long userid) throws Exception {
        return ResponseEntity.ok(mySQLRecommender.getNeighbors(userid));
    }*/
}
