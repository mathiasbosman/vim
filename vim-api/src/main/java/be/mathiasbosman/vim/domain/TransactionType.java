package be.mathiasbosman.vim.domain;

import java.util.Collections;
import java.util.Set;
import lombok.Getter;

/**
 * Possible types of a {@link Transaction}.
 */
@Getter
public enum TransactionType {
  CHECK_IN(Set.of(ItemStatus.UNAVAILABLE, ItemStatus.CHECKED_OUT), ItemStatus.AVAILABLE),
  CHECK_OUT(Set.of(ItemStatus.AVAILABLE, ItemStatus.RESERVED), ItemStatus.CHECKED_OUT),
  MARK_DAMAGED(Set.of(ItemStatus.AVAILABLE, ItemStatus.UNAVAILABLE, ItemStatus.CHECKED_OUT,
      ItemStatus.RESERVED), ItemStatus.DAMAGED),
  MARK_REPAIRED(Collections.singleton(ItemStatus.DAMAGED), ItemStatus.AVAILABLE),
  REMOVE(Set.of(ItemStatus.AVAILABLE, ItemStatus.DAMAGED), ItemStatus.UNAVAILABLE);

  private final Set<ItemStatus> preItemStates;
  private final ItemStatus postItemStatus;

  TransactionType(Set<ItemStatus> preItemStatus,
      ItemStatus postItemStatus) {
    this.preItemStates = preItemStatus;
    this.postItemStatus = postItemStatus;
  }

  public boolean isValidForItemStatus(ItemStatus itemStatus) {
    return preItemStates.contains(itemStatus);
  }
}
