package shop.spring.dev.springshop.service.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.spring.dev.springshop.domain.item.Item;
import shop.spring.dev.springshop.domain.item.ItemImg;
import shop.spring.dev.springshop.domain.item.ItemImgRepository;
import shop.spring.dev.springshop.domain.item.ItemRepository;
import shop.spring.dev.springshop.dto.item.ItemImgDto;
import shop.spring.dev.springshop.dto.item.ItemResponseDto;
import shop.spring.dev.springshop.dto.item.ItemSaveRequestDto;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class ItemService {

    private final ItemRepository itemRepository;
    private final ItemImgRepository itemImgRepository;

    /**
     * item 정보 등록
     * @param itemSaveRequestDto
     * @return
     * @throws Exception
     */
    public Long saveItem(ItemSaveRequestDto itemSaveRequestDto) throws Exception {

        // 상품 등록
        Item item = itemSaveRequestDto.toEntity();
        itemRepository.save(item);
        return item.getId();
    }

    /**
     * 요청받은 id의 특정 아이템 정보와 이미지 리스트를 응답
     * @param itemId
     * @return
     */
    @Transactional(readOnly = true)
    public ItemResponseDto getItem(Long itemId) {

        List<ItemImg> itemImgList = itemImgRepository.findByItemIdOrderByIdAsc(itemId);
        List<ItemImgDto> itemImgDtoList = new ArrayList<>();
        for (ItemImg itemImg : itemImgList) {
            ItemImgDto itemImgDto = ItemImgDto.of(itemImg);
            itemImgDtoList.add(itemImgDto);
        }

        Item findItem = itemRepository.findById(itemId)
                .orElseThrow(EntityNotFoundException::new);

        return ItemResponseDto.of(findItem, itemImgDtoList);
    }
}
