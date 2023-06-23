package com.example.demo1.API.admin;

import com.example.demo1.dto.AppUser;
import com.example.demo1.dto.DataTable;
import com.example.demo1.dto.NewDTO;
import com.example.demo1.dto.PrivilegeDTO;
import com.example.demo1.service.IPrivilegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@PreAuthorize("isAuthenticated() and hasRole('ROLE_ADMIN')")
@RestController(value = "privilegeAPIOfAdmin")
@RequestMapping("api/privilege")
public class PrivilegeAPI {
    @Autowired
    IPrivilegeService privilegeService;
    @PostMapping
    public PrivilegeDTO create(@RequestBody PrivilegeDTO dto) {
        return privilegeService.saveOrUpdate(dto);
    }
    @PutMapping
    public PrivilegeDTO update(@RequestBody PrivilegeDTO dto) {
        return privilegeService.saveOrUpdate(dto);
    }
    @DeleteMapping
    public String delete(@RequestBody long[] ids) {
        String s = new String("Default");
        if (ids.length == 0) {
            return s = "ids is null";
        }
        privilegeService.delete(ids);
        return s;
    }
    @GetMapping
    public ResponseEntity<?> findAll(@RequestParam Integer draw, @RequestParam Optional<Integer> start, @RequestParam Optional<Integer> length, @RequestParam(name = "orderCol") Optional<String> sort, @RequestParam(name = "sortDir") Optional<String> direction, @RequestParam Optional<String> search, Authentication authentication) {
        DataTable returnClient = new DataTable();
        Integer page = start.orElse(0) / length.orElse(3);
        if (search.isPresent() && search != null && !search.get().equals("")) {
            return ResponseEntity.ok(new DataTable(draw, 0, 0, privilegeService.findByNameContaining(search.get())));
        }
        Page<PrivilegeDTO> dataPerPage = privilegeService.findByPage(PageRequest.of(page, length.orElse(3), Sort.by(Sort.Direction.fromString(direction.orElse("DESC")), sort.orElse("id"))));
        returnClient = new DataTable(draw, (int) (dataPerPage.getTotalElements()), (int) (dataPerPage.getTotalElements()), dataPerPage.getContent());
        return ResponseEntity.ok(returnClient);
    }
}
