package shop.spring.dev.springshop.dto.item;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.spring.dev.springshop.constant.ItemSellStatus;
import shop.spring.dev.springshop.domain.item.Item;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemResponseDto {

    private Long id;

    private ItemSellStatus itemSellStatus;

    private String itemName;

    private Integer price;

    private Integer stockNumber;

    private String itemDetail;

    private List<ItemImgDto> itemImgDtoList;

    public static ItemResponseDto of(Item item, List<ItemImgDto> itemImgDtoList) {
        return ItemResponseDto.builder()
                .id(item.getId())
                .itemSellStatus(item.getItemSellStatus())
                .itemName(item.getItemName())
                .price(item.getPrice())
                .stockNumber(item.getStockNumber())
                .itemDetail(item.getItemDetail())
                .itemImgDtoList(itemImgDtoList)
                .build();
    }

}
