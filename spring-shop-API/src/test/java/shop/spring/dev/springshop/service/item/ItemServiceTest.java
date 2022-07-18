package shop.spring.dev.springshop.service.item;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import shop.spring.dev.springshop.constant.ItemSellStatus;
import shop.spring.dev.springshop.domain.item.Item;
import shop.spring.dev.springshop.domain.item.ItemImg;
import shop.spring.dev.springshop.domain.item.ItemImgRepository;
import shop.spring.dev.springshop.domain.item.ItemRepository;
import shop.spring.dev.springshop.dto.item.ItemImgDto;
import shop.spring.dev.springshop.dto.item.ItemResponseDto;
import shop.spring.dev.springshop.dto.item.ItemSaveRequestDto;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@Transactional
@SpringBootTest
class ItemServiceTest {

    @Autowired ItemService itemService;
    @Autowired ItemImgService itemImgService;
    @Autowired ItemRepository itemRepository;
    @Autowired ItemImgRepository itemImgRepository;

    @Value("${file.item_img-storage-location}")
    private String itemImgStorageLocation;

    List<MultipartFile> createMultipartFiles() throws Exception {

        List<MultipartFile> multipartFileList = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            String imgName = "image" + i + ".jpg";
            MockMultipartFile multipartFile = new MockMultipartFile(itemImgStorageLocation, imgName, "image/jpg", new byte[]{1, 2, 3, 4});
            multipartFileList.add(multipartFile);
        }

        return multipartFileList;
    }

    @Test
    @DisplayName("상품 정보 등록 테스트")
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void saveItem() throws Exception {
        // given
        ItemSaveRequestDto requestDto = ItemSaveRequestDto.builder()
                .itemName("test production")
                .price(100000)
                .stockNumber(3)
                .itemDetail("This is test Item")
                .itemSellStatus(ItemSellStatus.SELL)
                .build();

        Long savedItemId = itemService.saveItem(requestDto);

        // then
        Item findItem = itemRepository.findById(savedItemId).get();

        assertThat(findItem.getItemName()).isEqualTo(requestDto.getItemName());
        assertThat(findItem.getPrice()).isEqualTo(requestDto.getPrice());
        assertThat(findItem.getStockNumber()).isEqualTo(requestDto.getStockNumber());
        assertThat(findItem.getItemDetail()).isEqualTo(requestDto.getItemDetail());
        assertThat(findItem.getItemSellStatus()).isEqualTo(requestDto.getItemSellStatus());
    }

    @Test
    @DisplayName("특정 상품 정보 조회 테스트")
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void getItem() throws Exception {
        // given

        // 상품 정보 저장
        ItemSaveRequestDto requestDto = ItemSaveRequestDto.builder()
                .itemName("test production")
                .price(100000)
                .stockNumber(3)
                .itemDetail("This is test Item")
                .itemSellStatus(ItemSellStatus.SELL)
                .build();
        Long savedItemId = itemService.saveItem(requestDto);

        // 상품 이미지 저장
        itemImgService.saveItemImg(savedItemId, createMultipartFiles());
        List<ItemImg> savedItemImgList = itemImgRepository.findByItemIdOrderByIdAsc(savedItemId);

        // when

        ItemResponseDto itemResponseDto = itemService.getItem(savedItemId);

        // then
        // 상품 정보가 일치하는지 비교
        assertThat(itemResponseDto.getId()).isEqualTo(savedItemId);
        assertThat(itemResponseDto.getItemName()).isEqualTo(requestDto.getItemName());
        assertThat(itemResponseDto.getPrice()).isEqualTo(requestDto.getPrice());
        assertThat(itemResponseDto.getStockNumber()).isEqualTo(requestDto.getStockNumber());
        assertThat(itemResponseDto.getItemDetail()).isEqualTo(requestDto.getItemDetail());
        assertThat(itemResponseDto.getItemSellStatus()).isEqualTo(requestDto.getItemSellStatus());

        // 상품의 이미지 정보가 일치하는지 비교
        List<ItemImgDto> itemImgDtoList = itemResponseDto.getItemImgDtoList();
        for (int i = 0; i < itemImgDtoList.size(); i++) {

            ItemImgDto itemImgDto = itemImgDtoList.get(i);
            ItemImg itemImg = savedItemImgList.get(i);

            assertThat(itemImgDto.getId()).isEqualTo(itemImg.getId());
            assertThat(itemImgDto.getStoredImgName()).isEqualTo(itemImg.getStoredImgName());
            assertThat(itemImgDto.getOriginalImgName()).isEqualTo(itemImg.getOriginalImgName());
            assertThat(itemImgDto.getImgUrl()).isEqualTo(itemImg.getImgUrl());
            assertThat(itemImgDto.getIsThumbnail()).isEqualTo(itemImg.getIsThumbnail());
        }
    }
}