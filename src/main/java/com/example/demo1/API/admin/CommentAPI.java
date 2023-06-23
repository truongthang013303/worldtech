package com.example.demo1.API.admin;

import com.example.demo1.dto.AppUser;
import com.example.demo1.dto.CommentDTO;
import com.example.demo1.dto.DataTable;
import com.example.demo1.dto.NewDTO;
import com.example.demo1.service.ICommentService;
import com.example.demo1.service.INewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController(value ="commentAPI")
@RequestMapping("api/comment")
public class CommentAPI {
    @Autowired
    ICommentService commentService;
    @Autowired
    INewService newService;

    @GetMapping("/page")
    public ResponseEntity<?> getCommentsForDatatable(@RequestParam Optional<Integer> draw, @RequestParam Optional<Integer> start, @RequestParam Optional<Integer> length, @RequestParam(name = "orderCol") Optional<String> sort, @RequestParam(name = "sortDir") Optional<String> direction, @RequestParam Optional<String> search){
        if(search.isPresent()&&search!=null&&!search.get().equals(""))
        {
            List<CommentDTO> commentsSearched = commentService.findByContentContaining(search.get());
            return ResponseEntity.ok(new DataTable(draw.orElse(1), 0,0, commentsSearched));
        }
        Integer page = start.orElse(0)/ length.orElse(3);

        Page<CommentDTO> dataPerPage = commentService.findByPage(PageRequest.of(page, length.orElse(3), Sort.by(Sort.Direction.fromString(direction.orElse("DESC")), sort.orElse("id"))));
        DataTable returnClient = new DataTable(draw.orElse(1), (int)(dataPerPage.getTotalElements()), (int)(dataPerPage.getTotalElements()), dataPerPage.getContent());
        return ResponseEntity.ok(returnClient);
    }
    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> postComment(@RequestBody @Valid CommentDTO commentDTO, Authentication authentication){
        if(authentication==null || commentDTO==null)
        {
            return ResponseEntity.badRequest().body("comment JSON is null OR user unauthen");
        }
        AppUser appUser = (AppUser) authentication.getPrincipal();
        commentDTO.setUserid(appUser.getId());
        CommentDTO dto = commentService.saveOrUpdate(commentDTO);
        if(dto==null)
        {
            return ResponseEntity.badRequest().body("userid or newsid not found or very large number of userid, postid");
        }
        return ResponseEntity.ok(dto);
    }
    @GetMapping
    public ResponseEntity<?> getCommentsOfAPost(@RequestParam Long id, @RequestParam Optional<Integer> page, @RequestParam Optional<Integer> limit) throws Exception {
        if(page.get()==null || page.isPresent()==false || page.get()==0|| page.get()<0)
        {
            throw new Exception("page number is invalid");
        }
        Integer pageResolve=page.orElse(1)-1;
        if(newService.existsById(id)==false)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("id of Post NotFound");
        }
        Page<CommentDTO> commentDTOS = commentService.findAllCommentsOfAPost(id, PageRequest.of(pageResolve, limit.orElse(2), Sort.by(Sort.Direction.DESC, "createdDate")));
/*        if(commentDTOS.isEmpty())
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("This post emty comments");
        }*/
        return ResponseEntity.ok(commentDTOS);
    }

    /*@DeleteMapping
    @PreAuthorize("#id.createdBy==authentication.principal.username OR hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> deleteACommentById(@RequestBody CommentDTO id){
        //return commentService.deleteACommentById(id);
        System.out.println(id);
        return null;
    }*/

    @DeleteMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> deleteACommentById(@RequestBody Long[] ids, HttpServletRequest request, Authentication authentication){
        if(ids.length!=0) {
            //Admin co the xoa moi comment cua bat ky ai
            if (authentication.getAuthorities().stream().anyMatch(autho -> autho.getAuthority().equalsIgnoreCase("ROLE_ADMIN"))) {
                System.out.println("isADMIN");
                for (Long i : ids) {
                    commentService.deleteACommentById(i);
                }
                return ResponseEntity.ok().body("Successed");
            } else {
                for (Long i : ids) {
                    //Ktra comment muon xoa co createdBy = authentication.username dang login
                    if (commentService.findById(i).getCreatedBy().equalsIgnoreCase(authentication.getName())) {
                        commentService.deleteACommentById(i);
                        return ResponseEntity.ok().body("Successed");
                    } else {
                        System.out.println("deleteACommentById()--CommentAPI.java--AccessDenied");
                        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You dont have authority for delete this comment");
                    }
                }
            }
        }
        return ResponseEntity.badRequest().body("Id is null");
        //return commentService.deleteACommentById(id);
        /*HttpSession session = request.getSession(false);
        System.out.println(authentication.getName());
        System.out.println(session);
        System.out.println(request.getUserPrincipal().getName());*/
    }
}
