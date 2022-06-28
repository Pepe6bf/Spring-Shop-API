package shop.spring.dev.springshop.service.item;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import shop.spring.dev.springshop.constant.ItemSellStatus;
import shop.spring.dev.springshop.domain.item.ItemImg;
import shop.spring.dev.springshop.domain.item.ItemImgRepository;
import shop.spring.dev.springshop.dto.item.ItemSaveRequestDto;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;


@Transactional
@SpringBootTest
class ItemImgServiceTest {

    @Autowired ItemImgService imgService;
    @Autowired ItemImgRepository itemImgRepository;
    @Autowired ItemService itemService;

    Long saveItem() throws Exception {
        ItemSaveRequestDto requestDto = ItemSaveRequestDto.builder()
                .itemName("test production")
                .price(100000)
                .stockNumber(3)
                .itemDetail("This is test Item")
                .itemSellStatus(ItemSellStatus.SELL)
                .build();

        return itemService.saveItem(requestDto);
    }

    List<MultipartFile> createMultipartFiles() throws Exception {

        List<MultipartFile> multipartFileList = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            String path = "/Users/kmo/Study/toy-project/spring-shop/project/storage/imgs";
            String imgName = "image" + i + ".jpg";
            MockMultipartFile multipartFile = new MockMultipartFile(path, imgName, "image/jpg", new byte[]{1, 2, 3, 4});
            multipartFileList.add(multipartFile);
        }

        return multipartFileList;
    }

    @Test
    @DisplayName("상품 이미지 등록 테스트")
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void saveItemImg() throws Exception {
        // given
        Long savedItemId = saveItem();  // 상품 정보를 먼저 저장하고 아이디 반환
        List<MultipartFile> multipartFiles = createMultipartFiles();

        // when
        Long itemId = imgService.saveItemImg(savedItemId, multipartFiles);

        // then
        List<ItemImg> itemImgList = itemImgRepository.findByItemIdOrderByIdAsc(itemId);

        assertThat(multipartFiles.get(0).getOriginalFilename()).isEqualTo(itemImgList.get(0).getOriginalImgName());
    }

}