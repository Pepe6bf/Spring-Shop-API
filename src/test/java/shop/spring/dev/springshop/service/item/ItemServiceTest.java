package shop.spring.dev.springshop.service.item;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.transaction.annotation.Transactional;
import shop.spring.dev.springshop.constant.ItemSellStatus;
import shop.spring.dev.springshop.controller.item.ItemController;
import shop.spring.dev.springshop.domain.item.Item;
import shop.spring.dev.springshop.domain.item.ItemRepository;
import shop.spring.dev.springshop.dto.item.ItemSaveRequestDto;

import static org.assertj.core.api.Assertions.*;

@Transactional
@SpringBootTest
class ItemServiceTest {

    @Autowired ItemService itemService;
    @Autowired ItemRepository itemRepository;

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

        // when
        Long savedItemId = itemService.saveItem(requestDto);

        // then
        Item findItem = itemRepository.findById(savedItemId).get();

        assertThat(findItem.getItemName()).isEqualTo(requestDto.getItemName());
        assertThat(findItem.getPrice()).isEqualTo(requestDto.getPrice());
        assertThat(findItem.getStockNumber()).isEqualTo(requestDto.getStockNumber());
        assertThat(findItem.getItemDetail()).isEqualTo(requestDto.getItemDetail());
        assertThat(findItem.getItemSellStatus()).isEqualTo(requestDto.getItemSellStatus());
    }
}