package com.example.demo1.API.admin;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.example.demo1.dto.*;
import com.example.demo1.repository.NewRepository;
import org.apache.commons.lang3.EnumUtils;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import com.example.demo1.service.INewService;

import java.util.List;
import java.util.Optional;
@CrossOrigin(origins = "*")
@RestController(value ="newAPIOfAdmin")
@RequestMapping("api/new")
//@PreAuthorize("isAuthenticated()")
public class NewAPI 
{
	@Autowired
	private INewService newService;
	@Autowired
	private NewRepository newRepository;
//	@Autowired
//	private ApplicationContext applicationContext;

	@PostMapping("/rating")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<?> rating(Authentication authentication, @RequestBody @Valid RatingDTO ratingDTO){
		if(authentication!=null)
		{
			AppUser appUser = (AppUser)authentication.getPrincipal();
			ratingDTO.setUserid(appUser.getId());
			return newService.rating(ratingDTO);
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("To rate the post you must authen");
	}

	//api duoc goi tu ben trang admin tra ve du lieu cho dataTable render
	@GetMapping
	@PreAuthorize("isAuthenticated() AND hasAnyAuthority('VIEW_POST','VIEW_SELF_POST','PUBLISH_POST','UPDATE_POST','UPDATE_SELF_POST','CREATE_POST','DELETE_POST')")
	public ResponseEntity<?> findAll(@RequestParam Integer draw, @RequestParam Optional<Integer> start, @RequestParam Optional<Integer> length, @RequestParam(name = "orderCol") Optional<String> sort, @RequestParam(name = "sortDir") Optional<String> direction, @RequestParam Optional<String> search, Authentication authentication, @RequestParam Optional<Long> categoryid) {
		DataTable returnClient = new DataTable();
		//chac chan != anonymouse vi co @PreAuthorize
		AppUser appUser = (AppUser) authentication.getPrincipal();
		Long count = authentication.getAuthorities().stream()
				.filter(a->a.getAuthority().equals("VIEW_POST")|| a.getAuthority().equals("ROLE_ADMIN"))
				.count();
		Integer page = start.orElse(0)/ length.orElse(3);
		Pageable pageable = PageRequest.of(page, length.orElse(3), Sort.by(Sort.Direction.fromString(direction.orElse("DESC")), sort.orElse("id")));
		Page<NewDTO> dataPerPage;
		//neu la ROLE_ADMIN hoac co privilege VIEW_POST[xem tat ca cac bai viet] se co the tim kiem va hien thi toan bo
		if (count>=1)
		{
			if(search.isPresent()&&search!=null&&!search.get().equals(""))
			{
				return ResponseEntity.ok(new DataTable(draw, 0,0, newService.findByTitleContaining(search.get())));

			}
			//admin + loc theo categoryid
			if(categoryid.isPresent()){
				dataPerPage = newService.findAllByCategoryId(categoryid.orElse(1L), pageable);
				returnClient = new DataTable(draw, (int)(dataPerPage.getTotalElements()), (int)(dataPerPage.getTotalElements()), dataPerPage.getContent());
			}
			else//admin + khong loc
			{
				dataPerPage = newService.findByPage(pageable);
				returnClient = new DataTable(draw, (int)(dataPerPage.getTotalElements()), (int)(dataPerPage.getTotalElements()), dataPerPage.getContent());
			}
		}else{
			if(search.isPresent()&&search!=null&&!search.get().equals(""))
			{
				return ResponseEntity.ok(new DataTable(draw, 0,0, newService.findByTitleContainingAndCreatedBy(search.get(), appUser.getUsername())));

			}
			dataPerPage = newService.findAllByCreatedBy(appUser.getUsername(),pageable);
			returnClient = new DataTable(draw, (int)(dataPerPage.getTotalElements()), (int)(dataPerPage.getTotalElements()), dataPerPage.getContent());
		}
		return ResponseEntity.ok(returnClient);
	}

	@GetMapping("/dashboard")
	public ModelMap dashboardNewAPI()
	{
		ModelMap mm = new ModelMap();
		mm.addAttribute("totalNews",newService.getTotalItem());
		mm.addAttribute("choDuyet",newService.countByStatus(0));
		mm.addAttribute("daDuyet",newService.countByStatus(1));
		mm.addAttribute("tuChoi",newService.countByStatus(2));
		mm.addAttribute("default",newService.countByStatus(null));
		return mm;
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> findById(@PathVariable Long id)
	{
		if(id<=0 || id==null){
			return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.ok(newService.findById(id));
	}

	//api se duoc user normal page goi de lay cac bai viet
	@GetMapping("/page")
	public Page<NewDTO> findNewsByCategoryAndStatusIsAccepted(@RequestParam Optional<String> cateCode, @RequestParam Integer page, @RequestParam Optional<Integer> limit, @RequestParam Optional<String> sort, @RequestParam Optional<String> direction)
	{
		if(page<=0){
			page = 1;
		}
		int pageResolve=page-1;
		//Tìm tất cả bài viết không phân biệt thể loại theo từng page client yêu cầu
        if(cateCode.orElse(null)==null || cateCode.get().equalsIgnoreCase("")||cateCode.get().equalsIgnoreCase("undefined")||cateCode.get().equalsIgnoreCase("all"))
        {
            return newService.findByPage(1, PageRequest.of(pageResolve, 4, Sort.by(Sort.Direction.fromString(direction.orElse("DESC")), sort.orElse("createdDate"))));
        }
		//Tìm tất cả bài viết theo thể loại client gửi lên và theo từng page client yêu cầu
		return newService.findAllByCategoryCodePage(cateCode.orElse("giai-tri"), 1, PageRequest.of(pageResolve, 4, Sort.by(Sort.Direction.fromString(direction.orElse("DESC")), sort.orElse("createdDate"))));
	}

	@GetMapping("/status")
//	@PostFilter("hasRole('ROLE_ADMIN') OR filterObject.createdBy == authentication.principal.username")
	public Page<NewDTO> findNewsByStatus(@RequestParam Optional<PostStatus> status, @RequestParam Integer page, @RequestParam Integer limit, @RequestParam Optional<String> sort, @RequestParam Optional<String> direction)
	{
		if(page==null||page<=0){page=1;}
		if(limit==null || limit<=0){limit=4;}
		Integer pageResolve = page-1;
		if(status==null || !status.isPresent() || !EnumUtils.isValidEnum(PostStatus.class, status.get().name())){
			return newService.findByPage(PageRequest.of(pageResolve, 4, Sort.by(Sort.Direction.fromString(direction.orElse("DESC")), sort.orElse("createdDate"))));
		}
		return newService.findByPage(status.get().getPostStatusCode(), PageRequest.of(pageResolve, 4, Sort.by(Sort.Direction.fromString(direction.orElse("DESC")), sort.orElse("createdDate")) ));
	}

	public Page<NewDTO> findNewsByAnyCondition(@RequestParam PostStatus status, @RequestParam String title, @RequestParam Integer page, @RequestParam Integer limit, @RequestParam String sort, @RequestParam String direction){
		return null;
	};

	public Page<NewDTO> findNewsByTitle(@RequestParam String title, @RequestParam Integer page, @RequestParam Integer limit, @RequestParam String sort, @RequestParam String direction){
		if(page==null){page=1;}
		if(limit==null){limit=4;}
		return null;
	};
	@PreAuthorize("hasAuthority('CREATE_POST') OR hasRole('ROLE_ADMIN')")
	@PostMapping
	public ResponseEntity<?> createNews(@RequestBody @Valid NewDTO newDTO) throws Exception {
		NewDTO saved = newService.saveOrUpdate(newDTO);
		if(saved==null){
			return ResponseEntity.badRequest().body("title duplicated or category is null");
		}
		return ResponseEntity.ok(saved);
	}
	@PreAuthorize("hasAnyAuthority('UPDATE_POST','UPDATE_SELF_POST') OR hasRole('ROLE_ADMIN')")
	@PutMapping
	public ResponseEntity<?> updateNews(@RequestBody @Valid NewDTO newDTO) throws Exception {
		NewDTO updated = newService.saveOrUpdate(newDTO);
		if(updated==null){
			return ResponseEntity.badRequest().body("title duplicated or category is null");
		}
		return ResponseEntity.ok(updated);
	}

	@PreAuthorize("hasAuthority('PUBLISH_POST')")
	@PutMapping("/publish")
	public NewDTO publishNews(@RequestBody @Valid NewDTO newDTO)
	{
		return newService.publishPost(newDTO);
	}

	@PreAuthorize("hasAuthority('DELETE_POST')")
	@DeleteMapping
	public void deleteNews(@RequestBody long[] ids)
	{
		newService.delete(ids);
	}
}
