package be.mathiasbosman.vim.domain;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.UUID;

@UtilityClass
public class ItemMother {

    public static Item newItem() {
        return Item.builder()
                   .name(RandomStringUtils.random(255))
                   .build();
    }

    public static Item newItem(ItemStatus status) {
        return newItem().toBuilder().status(status).build();
    }

    public static Item newItem(Category category) {
        return newItem().toBuilder().category(category).build();
    }

    public static Item random() {
        return newItem().toBuilder().id(UUID.randomUUID()).build();
    }

    public static Item random(ItemStatus status) {
        return random().toBuilder().status(status).build();
    }

}
