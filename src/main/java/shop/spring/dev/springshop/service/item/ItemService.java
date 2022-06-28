package shop.spring.dev.springshop.service.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.spring.dev.springshop.domain.item.Item;
import shop.spring.dev.springshop.domain.item.ItemRepository;
import shop.spring.dev.springshop.dto.item.ItemSaveRequestDto;

@RequiredArgsConstructor
@Transactional
@Service
public class ItemService {

    private final ItemRepository itemRepository;

    public Long saveItem(ItemSaveRequestDto itemSaveRequestDto) throws Exception {

        // 상품 등록
        Item item = itemSaveRequestDto.toEntity();
        itemRepository.save(item);
        return item.getId();
    }
}
