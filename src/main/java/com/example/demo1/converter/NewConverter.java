package com.example.demo1.converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo1.dto.NewDTO;
import com.example.demo1.entity.NewEntity;
import com.example.demo1.repository.CategoryRepository;
import com.example.demo1.utils.UpLoadFileUtils;

@Component
public class NewConverter 
{
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private UpLoadFileUtils upLoadFileUtils;
	public NewDTO toDto(NewEntity newEntity) {
		NewDTO newDTO = new NewDTO();
		newDTO.setId(newEntity.getId());
		newDTO.setTitle(newEntity.getTitle());
		newDTO.setShortDescription(newEntity.getShortDescription());
		newDTO.setContent(newEntity.getContent());
		newDTO.setThumbnail(newEntity.getThumbnail());
		/*entity.getCategory(); sẽ lấy về được 1 đối tượng CategoryEntity. Sau đó chúng ta sẽ dụng getter getCode() để lấy ra được code vd:'the-thao'
		và gán cho thuộc tính CategoryCode của NewDTO*/
		newDTO.setCategoryCode(newEntity.getCategory().getCode());
		
		newDTO.setCreatedDate(newEntity.getCreatedDate());
		newDTO.setCreatedBy(newEntity.getCreatedBy());
		newDTO.setModifiedBy(newEntity.getModifiedBy());
		newDTO.setModifiedDate(newEntity.getModifiedDate());
		return newDTO;
	}
	// hàm này dùng cho thêm mới bài viết. Truyền vào một bài viết đã có dữ liệu từ client dạng DTO sau đó chuyển nó vào một newEnity mới hoàn toàn
	public NewEntity toEntity(NewDTO newDTO) {
		NewEntity newEntity = new NewEntity();
		newEntity.setTitle(newDTO.getTitle());
		newEntity.setShortDescription(newDTO.getShortDescription());
		newEntity.setContent(newDTO.getContent());
		newEntity.setThumbnail(newDTO.getThumbnail());
		
		newEntity.setCategory(categoryRepository.findOneByCode(newDTO.getCategoryCode()));
		return newEntity;
	}
	/*hàm này dùng cho update chúng ta chuyền vào bài viết được lấy lên từ database theo id, truyền thêm 1 bài viết có dữ liệu muốn thay đổi của user nhập(newDTO)
	sau đó gán những thôg tin mà user muốn thay đổi có trong newDTO sang cho bài viết mà user muốn thay đổi: NewEntity newEntity = newRepository.findOne(id);
	bài viết newEntity này đã có sẵn được tìm lên bằng findOne(newDTO.getId()); chứ khôg phải 1 bài viết mới hoàng toàn như save()*/
	public NewEntity toEntity(NewEntity newEntity, NewDTO newDTO) 
	{
		newEntity.setTitle(newDTO.getTitle());
		newEntity.setShortDescription(newDTO.getShortDescription());
		newEntity.setContent(newDTO.getContent());
		
		if(newDTO.getThumbnail().endsWith(".jpg")==true)//nếu như client gửi lên cho chúng ta thumbnail: là một chuỗi kết thúc bằng .jpg tức là sửa đổi nhưng ko thay đổi ảnh 
			//cta sẽ lấy luôn thumbnail client gửi lên gán vào cho thumbnail của entity và tiến hành lưu luôn xuống database
		{	
			newEntity.setThumbnail(newDTO.getThumbnail());
		}
		else// còn nếu như client gửi lên 1 chuỗi ko kết thúc bằng .jpg tức là 1 chuỗi base64, tức là sửa đổi có sửa cả ảnh, cta sẽ tiến hành lưu ảnh return lại: tên ảnh, sau đó lấy tên ảnh trả về gán cho thumbnail và lưu xuống DB
		{
			NewDTO newDTOAfterSaveFile = upLoadFileUtils.uploadFile(newDTO);
			newEntity.setThumbnail(newDTOAfterSaveFile.getThumbnail());
		}
		newEntity.setCategory(categoryRepository.findOneByCode(newDTO.getCategoryCode()));
		return newEntity;
	}
}
