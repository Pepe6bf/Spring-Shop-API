package shop.spring.dev.springshop.dto.item;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.spring.dev.springshop.constant.ItemSellStatus;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemUpdateRequestDto {

    private String itemName;

    private Integer price;

    private String itemDetail;

    private Integer stockNumber;

    private ItemSellStatus itemSellStatus;

}
